package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysRoleFunction;

import java.util.List;

public interface SysRoleFunctionMapper {
    int deleteFunctionList(String sysRoleId);

    int insertSelective(SysRoleFunction record);

    int addRoleFunctionList(List<SysRoleFunction> sysRoleFunctionList);

    List<SysRoleFunction> queryRoleFunctionList(String sysRoleId);

    int updateByPrimaryKeySelective(SysRoleFunction record);

    int updateByPrimaryKey(SysRoleFunction record);
}