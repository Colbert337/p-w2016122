package com.sysongy.poms.permi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.dao.*;
import com.sysongy.poms.permi.model.*;
import com.sysongy.poms.permi.service.SysFunctionService;
import com.sysongy.poms.permi.service.SysRoleService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.GroupUtil;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: SystemUserServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月30日, 11:23
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class SysUserServiceImpl implements SysUserService{

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    SysFunctionService sysFunctionService;
    @Autowired
    SysFunctionMapper sysFunctionMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysRoleFunctionMapper sysRoleFunctionMapper;
    /**
     * 查询用户列表（分页）
     * @param sysUser
     * @return
     */
    @Override
    public PageInfo<SysUser> queryUserListPage(SysUser sysUser) {
        /**
         * 获取分页参数
         */

        PageHelper.startPage(sysUser.getPageNum(), sysUser.getPageSize());
        List<SysUser> userList = sysUserMapper.queryUserList(sysUser);
        PageInfo<SysUser> userPageInfo = new PageInfo<SysUser>(userList);
        return userPageInfo;
    }
    /**
     * 查询用户列表
     * @param userType
     * @return
     */
    @Override
    public List<SysUser> queryUserListByUserType(int userType) {
        SysUser sysUser = new SysUser();
        sysUser.setUserType(userType);
        sysUser.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
        List<SysUser> userList = sysUserMapper.queryUserList(sysUser);
        return userList;
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public SysUser queryUserByAccount(SysUser user) {
        SysUser sysUser = null;
        if (user != null) {
            String password = user.getPassword();
            String userName = user.getUserName();
            try {
                password = Encoder.MD5Encode(password.getBytes());
                user.setPassword(password);
                sysUser = sysUserMapper.queryUserByAccount(userName, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sysUser;
    }

    /**
     * 查询用户
     * @param user
     * @return
     */
    @Override
    public SysUser queryUser(SysUser user) {
        return sysUserMapper.queryUser(user);
    }

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户编号
     * @return
     */
    @Override
    public SysUser queryUserByUserId(String userId) {
        return sysUserMapper.queryUserById(userId);
    }

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户编号
     * @return
     */
    @Override
    public Map<String, Object> queryUserMapByUserId(String userId) {
        return sysUserMapper.queryUserMapByUserId(userId);
    }

    /**
     * 添加用户
     * @param user 用户信息实体类
     * @return
     */
    @Override
    public int addUser(SysUser user) {
        //添加用户对应角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setSysUserRoleId(UUIDGenerator.getUUID());
        sysUserRole.setSysUserId(user.getSysUserId());
        sysUserRole.setSysRoleId(user.getAvatarB());
        sysUserRoleMapper.addUserRole(sysUserRole);
        if(user.getPassword() != null){
            String passwordStr = user.getPassword();
            passwordStr = Encoder.MD5Encode(passwordStr.getBytes());
            user.setPassword(passwordStr);
        }
        return sysUserMapper.addUser(user);
    }
    /**
     * 修改用户信息
     * @param user 用户信息
     * @return
     */
    @Override
    public int updateUser(SysUser user) {
        //删除用户对应角色
        sysUserRoleMapper.deleteUserRoleByUserId(user.getSysUserId());
        //添加用户对应角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setSysUserRoleId(UUIDGenerator.getUUID());
        sysUserRole.setSysUserId(user.getSysUserId());
        sysUserRole.setSysRoleId(user.getAvatarB());
        sysUserRoleMapper.addUserRole(sysUserRole);
        return sysUserMapper.updateUser(user);
    }

    /**
     * 修改用户状态
     * @param user 用户信息
     * @return
     */
    @Override
    public int updateStatus(SysUser user) {
        return sysUserMapper.updateUser(user);
    }

    /**
     * 根据用户编号删除用户角色关系
     * @param userId 用户编号
     * @return
     */
    @Override
    public int deleteUserRole(String userId) {
        return 0;
    }
    /**
     * 批量添加用户角色关系
     * @param userRoleList 用户角色关系集合
     * @return
     */
    @Override
    public int addUserRole(List<SysUserRole> userRoleList) {
        return 0;
    }

    /**
     * 根据用户名和用户类型删除用户
     * @param record
     * @return
     */
    @Override
    public int updateUserByName(SysUser record)  throws Exception{
        return sysUserMapper.updateUserByName(record);
    }

    /*************************************CRM客户端接口*************************************/
    /**
     * 用户登录
     * @param user
     * @return map
     */
    @Override
    public SysUser queryUserMapByAccount(SysUser user) {
//        判断用户是否有效
        SysUser sysUser = null;
        if (user != null) {
            String password = user.getPassword();
            String userName = user.getUserName();
            try {
                sysUser = sysUserMapper.queryUserByAccount(userName, password);
                /*用户有效*/
                if(sysUser != null){
                    /*查询用户菜单列表*/
                    int userType = 1;
                    List<Map<String,Object>> sysFunctionList = sysFunctionService.queryFunctionAllList(userType);
                    //将数据做分组处理，需要优化分组函数
                    Map group = GroupUtil.group(sysFunctionList, new GroupUtil.GroupBy<String>() {
                        @Override
                        public String groupby(Object obj) {
                            Map m = (Map) obj;
                            return m.get("parentId").toString();    // 分组依据为parent
                        }
                    });

                    List childL = new ArrayList();
                    for (Map<String, Object> map : sysFunctionList) {
                        String groupKey = map.get("sysFunctionId").toString();
                        //groupkey 包含id时，当前id对象有一个子集
                        if (group.containsKey(groupKey)) {
                            List childList = (List) group.get(groupKey);
                            childL.addAll(childList);
                            map.put("children", childList);
                        }
                    }

                    sysFunctionList.removeAll(childL);
                    sysUser.setSysFunctionList(sysFunctionList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sysUser;
    }

    /**
     * 添加业务系统管理员
     * @param user
     * @return
     */
    @Override
    public int addAdminUser(SysUser user) throws Exception{
        int result = 0;
        //添加用户信息
        String userId = UUIDGenerator.getUUID();
        int userType = user.getUserType();
        user.setSysUserId(userId);
        user.setMobilePhone(user.getUserName());
        user.setIsAdmin(GlobalConstant.ADMIN_YES);
        result = sysUserMapper.addUser(user);

        //创建管理员角色
        SysRole sysRole = new SysRole();
        String roleId = UUIDGenerator.getUUID();
        sysRole.setSysRoleId(roleId);
        sysRole.setRoleType(userType);
        sysRole.setIsAdmin(GlobalConstant.ADMIN_YES);
        sysRole.setRoleName("管理员");
        result = sysRoleMapper.addRole(sysRole);

        //创建管理员角色用户关系
        SysUserRole sysUserRole = new SysUserRole();
        String userRoleId = UUIDGenerator.getUUID();
        sysUserRole.setSysUserRoleId(userRoleId);
        sysUserRole.setSysRoleId(roleId);
        sysUserRole.setSysUserId(userId);
        result = sysUserRoleMapper.addUserRole(sysUserRole);

        //查询当前业务系统功能菜单列表
        List<SysFunction> functionList = new ArrayList<>();
        SysFunction sysFunction = new SysFunction();
        sysFunction.setFunctionType(userType);
        sysFunction.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
        sysFunction.setFunctionStatus(GlobalConstant.STATUS_DELETE);
        functionList = sysFunctionMapper.queryFunctionList(sysFunction);

        //创建管理员角色与功能菜单关系
        List<SysRoleFunction> sysRoleFunctionList = new ArrayList<>();
        if(functionList != null && functionList.size() > 0){
            for (SysFunction function:functionList) {
                SysRoleFunction sysRoleFunction = new SysRoleFunction();
                String roleFunctionId = UUIDGenerator.getUUID();
                sysRoleFunction.setSysRoleFunctionId(roleFunctionId);
                sysRoleFunction.setSysFunctionId(function.getSysFunctionId());
                sysRoleFunction.setSysRoleId(roleId);

                sysRoleFunctionList.add(sysRoleFunction);
            }

            result = sysRoleFunctionMapper.addRoleFunctionList(sysRoleFunctionList);
        }

        return result;
    }


    @Override
    public int updateCRMUser(SysUser user) {
        return sysUserMapper.updateUser(user);
    }
}
