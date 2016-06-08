package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysUserRole;

public interface SysUserRoleMapper {
    int deleteUserRoleByUserId(String userId);

    int insert(SysUserRole record);

    int addUserRole(SysUserRole record);

    SysUserRole selectByPrimaryKey(String sysUserRoleId);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
}