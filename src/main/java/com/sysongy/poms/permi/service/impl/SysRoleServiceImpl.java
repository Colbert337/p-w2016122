package com.sysongy.poms.permi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.dao.SysFunctionMapper;
import com.sysongy.poms.permi.dao.SysRoleFunctionMapper;
import com.sysongy.poms.permi.dao.SysRoleMapper;
import com.sysongy.poms.permi.model.SysFunction;
import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.poms.permi.model.SysRoleFunction;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysRoleService;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: SysRoleServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月30日, 15:14
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class SysRoleServiceImpl implements SysRoleService{

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysRoleFunctionMapper sysRoleFunctionMapper;
    @Autowired
    SysFunctionMapper sysFunctionMapper;

    /**
     * 查询角色列表（分页）
     * @return
     */
    @Override
    public PageInfo<SysRole> queryRoleListPage(SysRole sysRole) {
        PageHelper.startPage(sysRole.getPageNum(), sysRole.getPageSize());
        List<SysRole> roleList = sysRoleMapper.queryRoleList(sysRole);
        PageInfo<SysRole> rolePageInfo = new PageInfo<SysRole>(roleList);
        return rolePageInfo;
    }

    /**
     * 根据角色ID查询角色详情
     * @param roleId 角色编号
     * @return
     */
    @Override
    public Map<String, Object> queryRoleByRoleId(String roleId) {
        Map<String, Object> roleMap = new HashMap<>();//角色信息
        List<Map<String, Object>> functionMapList = new ArrayList<>();//菜单树，已标记是否选中
        List<String> functionIdList = new ArrayList<>();//已选中菜单ID集合
        String functionStr = "";//已选中菜单字符串
        //查询角色信息
        SysRole sysRole = sysRoleMapper.queryRoleById(roleId);

        //查询角色功能列表
        List<SysFunction> functionList = sysFunctionMapper.queryFunctionListByRoleId(roleId);
        int userType = 1;//用户类型（暂时用作删除状态）
        List<Map<String, Object>> functionAllList = sysFunctionMapper.queryFunctionAllList(userType);
        if(functionAllList != null && functionAllList.size() > 0){
            for(int i=0;i<functionAllList.size();i++){
                Map<String, Object> functionMap = functionAllList.get(i);
                int count = 0;//计数器
                if(functionList != null && functionList.size() > 0){
                    for(int j=0;j<functionList.size();j++){
                        SysFunction sysFunction = functionList.get(j);
                        //判断当前功能节点是否被选中
                        if(sysFunction.getSysFunctionId().equals(functionMap.get("sysFunctionId").toString())){
                            count++;
                        }
                    }
                }

                //封装功能树中的属性
                Map<String, Object> functionResultMap = new HashMap<>();
                functionResultMap.put("id",functionMap.get("sysFunctionId"));
                functionResultMap.put("name",functionMap.get("functionName"));
                functionResultMap.put("pId",functionMap.get("parentId"));

                //添加功能节点是否选中判断
                if(count > 0){
                    functionResultMap.put("checked","true");
                    String functionId = functionMap.get("sysFunctionId").toString();
                    functionIdList.add(functionId);
                }else{
                    functionResultMap.put("checked","false");
                }

                functionMapList.add(functionResultMap);
            }

            //将选中菜单ID集合拼接为字符串
            if(functionIdList != null && functionIdList.size() > 0){
                for (int i=0;i<functionIdList.size();i++){
                    functionStr += functionIdList.get(i);
                    if(i < functionIdList.size() - 1){
                        functionStr += ",";
                    }
                }
            }
        }else{
            //返回完整树，不选中任何一个功能节点
            functionMapList = functionAllList;
        }

        roleMap.put("sysRole", sysRole);
        roleMap.put("functionList",functionMapList);
        roleMap.put("functionStr",functionStr);
        return roleMap;
    }

    /**
     * 根据用户类型查询用户角色列表
     * @param userType 用户类型
     * @return
     */
    @Override
    public List<SysRole> queryRoleListByUserType(String userType) {
        return sysRoleMapper.queryRoleListByUserType(userType);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @Override
    public int addRole(SysRole role) {
        String function = role.getRoleCode();
        role.setRoleCode(null);
        /**
         * 添加角色功能关系
         */
        if(function != null && !"".equals(function)){
            List<SysRoleFunction> roleFunctionList = new ArrayList<>();
            String[] functionArray = function.split(",");
            if(functionArray != null && functionArray.length > 0){
                for (String functionId: functionArray) {
                    SysRoleFunction sysRoleFunction = new SysRoleFunction();
                    sysRoleFunction.setSysRoleFunctionId(UUIDGenerator.getUUID());
                    sysRoleFunction.setSysRoleId(role.getSysRoleId());
                    sysRoleFunction.setSysFunctionId(functionId);

                    roleFunctionList.add(sysRoleFunction);
                }
            }
            sysRoleFunctionMapper.addRoleFunctionList(roleFunctionList);
        }
        //添加角色
        return sysRoleMapper.addRole(role);
    }
    /**
     * 修改角色
     * @param role 角色信息
     * @return
     */
    @Override
    public int updateRole(SysRole role) {
        String function = role.getRoleCode();
        role.setRoleCode(null);
        /**
         * 添加角色功能关系
         */
        if(function != null && !"".equals(function)){
            //删除当前角色关联功能
            sysRoleFunctionMapper.deleteFunctionList(role.getSysRoleId());
            /*重新添加角色功能关联*/
            List<SysRoleFunction> roleFunctionList = new ArrayList<>();
            String[] functionArray = function.split(",");
            if(functionArray != null && functionArray.length > 0){
                for (String functionId: functionArray) {
                    SysRoleFunction sysRoleFunction = new SysRoleFunction();
                    sysRoleFunction.setSysRoleFunctionId(UUIDGenerator.getUUID());
                    sysRoleFunction.setSysRoleId(role.getSysRoleId());
                    sysRoleFunction.setSysFunctionId(functionId);

                    roleFunctionList.add(sysRoleFunction);
                }
            }
            sysRoleFunctionMapper.addRoleFunctionList(roleFunctionList);
        }
        return sysRoleMapper.updateRole(role);
    }
    /**
     * 根据角色编号删除用户角色关系
     * @param roleId 角色编号
     * @return
     */
    @Override
    public int deleteUserRole(String roleId) {
        return 0;
    }
    /**
     * 根据角色编号删除角色功能关系
     * @param roleId 角色编号
     * @return
     */
    @Override
    public int deleteRoleFunction(String roleId) {
        return 0;
    }
    /**
     * 批量添加角色功能关系
     * @param roleFunctionList 角色功能关系集合
     * @return
     */
    @Override
    public int addRoleFunction(List<SysRoleFunction> roleFunctionList) {
        return 0;
    }
}
