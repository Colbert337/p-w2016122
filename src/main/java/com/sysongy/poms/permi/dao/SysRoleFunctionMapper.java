package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysRoleFunction;

import java.util.List;

public interface SysRoleFunctionMapper {
    int deleteByPrimaryKey(String sysRoleFunctionId);

    int insert(SysRoleFunction record);

    int addRoleFunctionList(List<SysRoleFunction> sysRoleFunctionList);

    SysRoleFunction selectByPrimaryKey(String sysRoleFunctionId);

    int updateByPrimaryKeySelective(SysRoleFunction record);

    int updateByPrimaryKey(SysRoleFunction record);
}