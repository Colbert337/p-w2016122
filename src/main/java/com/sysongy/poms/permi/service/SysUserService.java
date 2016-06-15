package com.sysongy.poms.permi.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserRole;

import java.util.List;
import java.util.Map;

/**
 * @FileName: SystemUserService
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月30日, 11:23
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface SysUserService {

    /**
     * 查询用户列表（分页）
     * @param sysUser
     * @return
     */
    PageInfo<SysUser> queryUserListPage(SysUser sysUser);

    /**
     * 查询用户列表
     * @param userType
     * @return
     */
    List<SysUser> queryUserListByUserType(int userType);

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户编号
     * @return
     */
    SysUser queryUserByUserId(String userId);

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户编号
     * @return
     */
    Map<String,Object> queryUserMapByUserId(String userId);

    /**
     * 用户登录
     * @param user
     * @return
     */
    SysUser queryUserByAccount(SysUser user);

    /**
     * 查询用户
     * @param user
     * @return
     */
    SysUser queryUser(SysUser user);

    /**
     * 用户登录
     * @param user
     * @return map
     */
    SysUser queryUserMapByAccount(SysUser user);

    /**
     * 添加用户
     * @param user 用户信息实体类
     * @return
     */
    int addUser(SysUser user);

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return
     */
    int updateUser(SysUser user);

    /**
     * 修改用户状态
     * @param user 用户信息
     * @return
     */
    int updateStatus(SysUser user);

    /**
     * 根据用户编号删除用户角色关系
     * @param userId 用户编号
     * @return
     */
    int deleteUserRole(String userId);

    /**
     * 批量添加用户角色关系
     * @param userRoleList 用户角色关系集合
     * @return
     */
    int addUserRole(List<SysUserRole> userRoleList);

    /**
     * 添加业务系统管理员
     * @param user
     * @return
     */
    int addAdminUser(SysUser user) throws Exception;
}
