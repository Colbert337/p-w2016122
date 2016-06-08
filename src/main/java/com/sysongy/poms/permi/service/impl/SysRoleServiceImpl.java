package com.sysongy.poms.permi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.dao.SysRoleMapper;
import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.poms.permi.model.SysRoleFunction;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysRoleService;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public SysRole queryRoleByRoleId(String roleId) {
        return sysRoleMapper.queryRoleById(roleId);
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

        }
        return sysRoleMapper.addRole(role);
    }
    /**
     * 修改角色
     * @param role 角色信息
     * @return
     */
    @Override
    public int updateRole(SysRole role) {
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
