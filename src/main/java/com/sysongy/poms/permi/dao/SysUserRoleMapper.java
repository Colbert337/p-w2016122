package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysUserRole;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(String sysUserRoleId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(String sysUserRoleId);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
}