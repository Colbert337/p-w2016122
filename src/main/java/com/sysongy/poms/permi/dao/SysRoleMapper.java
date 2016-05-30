package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(String sysRoleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String sysRoleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}