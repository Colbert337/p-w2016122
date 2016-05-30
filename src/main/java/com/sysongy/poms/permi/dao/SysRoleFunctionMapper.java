package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysRoleFunction;

public interface SysRoleFunctionMapper {
    int deleteByPrimaryKey(String sysRoleFunctionId);

    int insert(SysRoleFunction record);

    int insertSelective(SysRoleFunction record);

    SysRoleFunction selectByPrimaryKey(String sysRoleFunctionId);

    int updateByPrimaryKeySelective(SysRoleFunction record);

    int updateByPrimaryKey(SysRoleFunction record);
}