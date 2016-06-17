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
     * 根据类型查询功能列表
     * @param userType 用户类型
     * @return
     */
    List<Map<String, Object>> queryFunctionListByType(int userType);

    /**
     * 根据当前用户ID查询功能菜单列表
     * @param userId 用户编号
     * @return
     */
    List<Map<String, Object>> queryFunctionListByUserId(String userId,int userType);

    /**
     * 根据父级ID查询功能列表
     * @param userType 用户类型
     * @param parentId 父级节点编号
     * @return
     */
    List<Map<String, Object>> queryFunctionListByParentId(int userType, String parentId);

    SysFunction queryFunctionById(String sysFunctionId);

    /**
     * 获取当前排序序号
     * @return
     */
    SysFunction queryFunctionSort();

    /**
     * 根据角色ID查询功能列表
     * @param sysRoleId
     * @return
     */
    List<SysFunction> queryFunctionListByRoleId(String sysRoleId);

    int deleteFunctionById(String sysFunctionId);

    int addFunction(SysFunction record);

    int updateFunction(SysFunction record);

    int deleteFunctionByParentId(String parentId);
}