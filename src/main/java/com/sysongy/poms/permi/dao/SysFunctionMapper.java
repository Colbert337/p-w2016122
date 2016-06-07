package com.sysongy.poms.permi.dao;

import com.alibaba.druid.sql.visitor.functions.Function;
import com.sysongy.poms.permi.model.SysFunction;

import java.util.List;
import java.util.Map;

public interface SysFunctionMapper {
    List<SysFunction> queryFunctionList(SysFunction function);

    /**
     * 查询功能列表
     * @param userType 用户类型
     * @return
     */
    List<Map<String, Object>> queryFunctionAllList(int userType);

    /**
     * 根据父级ID查询功能列表
     * @param userType 用户类型
     * @param parentId 父级节点编号
     * @return
     */
    List<Map<String,Object>> queryFunctionListByParentId(int userType, String parentId);

    SysFunction queryFunctionById(String sysFunctionId);

    int deleteFunctionById(String sysFunctionId);

    int addFunction(SysFunction record);

    int updateFunction(SysFunction record);

    int deleteFunctionByParentId(String parentId);
}