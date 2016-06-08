package com.sysongy.poms.permi.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.poms.permi.model.SysRoleFunction;

import java.util.List;
import java.util.Map;

/**
 * @FileName: SystemRoleService
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月30日, 14:54
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface SysRoleService {
    /**
     * 查询角色列表（分页）
     * @return
     */
    PageInfo<SysRole> queryRoleListPage(SysRole sysRole);

    /**
     * 根据角色ID查询角色详情
     * @param roleId 角色编号
     * @return
     */
    Map<String, Object> queryRoleByRoleId(String roleId);

    /**
     * 根据用户类型查询用户角色列表
     * @param userType 用户类型
     * @return
     */
    List<SysRole> queryRoleListByUserType(String userType);
    /**
     * 添加角色
     * @param role
     * @return
     */
    int addRole(SysRole role);

    /**
     * 修改角色
     * @param role 角色信息
     * @return
     */
    int updateRole(SysRole role);

    /**
     * 根据角色编号删除用户角色关系
     * @param roleId 角色编号
     * @return
     */
    int deleteUserRole(String roleId);

    /**
     * 根据角色编号删除角色功能关系
     * @param roleId 角色编号
     * @return
     */
    int deleteRoleFunction(String roleId);

    /**
     * 批量添加角色功能关系
     * @param roleFunctionList 角色功能关系集合
     * @return
     */
    int addRoleFunction(List<SysRoleFunction> roleFunctionList);

}
