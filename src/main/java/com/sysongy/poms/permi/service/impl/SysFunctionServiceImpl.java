package com.sysongy.poms.permi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.dao.SysFunctionMapper;
import com.sysongy.poms.permi.model.SysFunction;
import com.sysongy.poms.permi.service.SysFunctionService;
import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class SysFunctionServiceImpl implements SysFunctionService{

    @Autowired
    SysFunctionMapper sysFunctionMapper;
    /**
     * 查询功能列表(分页)
     * @return
     */
    @Override
    public List<SysFunction> queryFunctionListPage(SysFunction function) {
        if (function.getParentId() != null && function.getParentId().equals("1")) {
            int userType = 1;
            List<Map<String,Object>> functionMapList = sysFunctionMapper.queryFunctionListByParentId(userType,function.getParentId());
            if (functionMapList != null && functionMapList.size() > 0) {
                Map<String,Object> functionMap = functionMapList.get(0);
                function.setParentId(functionMap.get("sysFunctionId").toString());
            }
        }
        List<SysFunction> functionList = sysFunctionMapper.queryFunctionList(function);
        return functionList;
    }

    /**
     * 查询功能列表
     * @param userType 用户类型
     * @return
     */
    @Override
    public List<Map<String, Object>> queryFunctionAllList(int userType) {
        return sysFunctionMapper.queryFunctionAllList(userType);
    }

    /**
     * 根据父级ID查询功能列表
     * @param userType 用户类型
     * @param parentId 父级节点编号
     * @return
     */
    @Override
    public List<Map<String,Object>> queryFunctionListByParentId(int userType, String parentId) {
        return sysFunctionMapper.queryFunctionListByParentId(userType, parentId);
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
