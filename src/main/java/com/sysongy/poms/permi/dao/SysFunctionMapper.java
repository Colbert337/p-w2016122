package com.sysongy.poms.permi.dao;

import com.alibaba.druid.sql.visitor.functions.Function;
import com.sysongy.poms.permi.model.SysFunction;

import java.util.List;

public interface SysFunctionMapper {
    List<SysFunction> queryFunctionList();

    SysFunction queryFunctionById(String sysFunctionId);

    int deleteFunctionById(String sysFunctionId);

    int addFunction(SysFunction record);

    int updateFunction(SysFunction record);

    int deleteFunctionByParentId(String parentId);
}