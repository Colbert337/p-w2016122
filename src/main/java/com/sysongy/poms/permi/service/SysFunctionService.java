package com.sysongy.poms.permi.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.model.SysFunction;

import java.util.Map;

/**
 * @FileName: SystemFunctionService
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月30日, 14:55
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface SysFunctionService {
    /**
     * 查询功能列表(分页)
     * @param conditionMap 查询条件
     * @return
     */
    PageInfo<SysFunction> queryFunctionListPage(Map<String, Object> conditionMap);

    /**
     * 根据功能ID查询功能详情
     * @param functionId 功能编号
     * @return
     */
    SysFunction queryFunctionByFunctionId(String functionId);

    /**
     * 添加功能
     * @param function 功能信息
     * @return
     */
    int addFunction(SysFunction function);

    /**
     * 更新功能
     * @param function 功能信息
     * @return
     */
    int updateFunction(SysFunction function);

    /**
     * 根据功能编号删除功能
     * @param functionId 功能编号
     * @return
     */
    int deleteFunctionById(String functionId);

    /**
     * 根据父级编号删除父级功能下的所有功能
     * @param parentId 父级功能
     * @return
     */
    int deleteFunctionByParentId(String parentId);
}
