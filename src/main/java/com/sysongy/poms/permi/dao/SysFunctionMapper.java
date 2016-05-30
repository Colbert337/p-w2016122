package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysFunction;

public interface SysFunctionMapper {
    int deleteByPrimaryKey(String plFunctionId);

    int insert(SysFunction record);

    int insertSelective(SysFunction record);

    SysFunction selectByPrimaryKey(String plFunctionId);

    int updateByPrimaryKeySelective(SysFunction record);

    int updateByPrimaryKey(SysFunction record);
}