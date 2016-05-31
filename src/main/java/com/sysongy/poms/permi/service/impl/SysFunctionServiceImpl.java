package com.sysongy.poms.permi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.dao.SysFunctionMapper;
import com.sysongy.poms.permi.model.SysFunction;
import com.sysongy.poms.permi.service.SysFunctionService;
import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @FileName: SysFunctionServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月30日, 15:14
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public class SysFunctionServiceImpl implements SysFunctionService{

    @Autowired
    SysFunctionMapper sysFunctionMapper;
    /**
     * 查询功能列表(分页)
     * @param conditionMap 查询条件
     * @return
     */
    @Override
    public PageInfo<SysFunction> queryFunctionListPage(Map<String, Object> conditionMap) {
        Integer pageSize = GlobalConstant.PAGE_SIZE;
        Integer pageNum = GlobalConstant.PAGE_NUM;
        PageHelper.startPage(pageNum, pageSize);
        List<SysFunction> functionList = sysFunctionMapper.queryFunctionList();
        PageInfo<SysFunction> functionPageInfo = new PageInfo<SysFunction>(functionList);
        return functionPageInfo;
    }
    /**
     * 根据功能ID查询功能详情
     * @param functionId 功能编号
     * @return
     */
    @Override
    public SysFunction queryFunctionByFunctionId(String functionId) {
        return sysFunctionMapper.queryFunctionById(functionId);
    }
    /**
     * 添加功能
     * @param function 功能信息
     * @return
     */
    @Override
    public int addFunction(SysFunction function) {
        return sysFunctionMapper.addFunction(function);
    }
    /**
     * 更新功能
     * @param function 功能信息
     * @return
     */
    @Override
    public int updateFunction(SysFunction function) {
        return sysFunctionMapper.updateFunction(function);
    }
    /**
     * 根据功能编号删除功能
     * @param functionId 功能编号
     * @return
     */
    @Override
    public int deleteFunctionById(String functionId) {
        return sysFunctionMapper.deleteFunctionById(functionId);
    }
    /**
     * 根据父级编号删除父级功能下的所有功能
     * @param parentId 父级功能
     * @return
     */
    @Override
    public int deleteFunctionByParentId(String parentId) {
        return sysFunctionMapper.deleteFunctionByParentId(parentId);
    }
}
