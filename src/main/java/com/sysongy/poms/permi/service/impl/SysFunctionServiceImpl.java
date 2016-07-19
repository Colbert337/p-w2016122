package com.sysongy.poms.permi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.dao.SysFunctionMapper;
import com.sysongy.poms.permi.dao.SysRoleFunctionMapper;
import com.sysongy.poms.permi.dao.SysRoleMapper;
import com.sysongy.poms.permi.model.SysFunction;
import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.poms.permi.model.SysRoleFunction;
import com.sysongy.poms.permi.service.SysFunctionService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.GroupUtil;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysRoleFunctionMapper sysRoleFunctionMapper;
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
     * 根据类型查询功能列表
     * @param userType 用户类型
     * @return
     */
    @Override
    public List<Map<String, Object>> queryFunctionListByType(int userType) {
        return sysFunctionMapper.queryFunctionListByType(userType);
    }

    /**
     * 根据当前用户查询功能列表
     * @param userId 用户类型
     * @return
     */
    @Override
    public List<Map<String,Object>> queryFunctionListByUserId(String userId,int userType) {

        List<Map<String,Object>> sysFunctionList = sysFunctionMapper.queryFunctionListByUserId(userId,userType);
        List<Map<String,Object>> functionListTree = new ArrayList<>();
        for (Map<String,Object> function:sysFunctionList) {
            Map<String,Object> functionTree = new HashMap<>();
            functionTree.put("id",function.get("sysFunctionId"));
            functionTree.put("pId",function.get("parentId"));
            functionTree.put("name",function.get("functionName"));
            functionTree.put("icon",function.get("functionIcon"));
            functionTree.put("path",function.get("functionPath"));

            functionListTree.add(functionTree);
        }
        //将数据做分组处理，需要优化分组函数
		Map group = GroupUtil.group(sysFunctionList, new GroupUtil.GroupBy<String>() {
			@Override
			public String groupby(Object obj) {
				Map m = (Map) obj;
				return m.get("parentId").toString();    // 分组依据为parent
			}
		});

		List childL = new ArrayList();
		for (Map<String, Object> map : sysFunctionList) {
			String groupKey = map.get("sysFunctionId").toString();
			//groupkey 包含id时，当前id对象有一个子集
			//移除子集中的对象
			//{}
			if (group.containsKey(groupKey)) {
				List childList = (List) group.get(groupKey);
				childL.addAll(childList);
				map.put("children", childList);
			}
		}

		sysFunctionList.removeAll(childL);

		//combination data to response
		Map<String,Object> m = new HashMap<>();
		m.put("resMenu", sysFunctionList);
		/*functionMap.put("sysFunctionList",sysFunctionList);*/
        return sysFunctionList;
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
     * 获取当前排序序号
     * @return
     */
    @Override
    public SysFunction queryFunctionSort() {
        return sysFunctionMapper.queryFunctionSort();
    }

    /**
     * 添加功能
     * @param function 功能信息
     * @return
     */
    @Override
    public int addFunction(SysFunction function) throws Exception {
       int resultVal = sysFunctionMapper.addFunction(function);
        //将菜单添加到已经创建的管理员角色上
        String functionId = function.getSysFunctionId();
        SysRole sysRole = new SysRole();
        sysRole.setRoleType(function.getFunctionType());
        List<SysRole> roleList = sysRoleMapper.queryAdminRoleList(sysRole);
        if(roleList != null && roleList.size() > 0){
            List<SysRoleFunction> roleFunctionList = new ArrayList<>();
            for (SysRole role:roleList){
                SysRoleFunction sysRoleFunction = new SysRoleFunction();
                sysRoleFunction.setSysRoleFunctionId(UUIDGenerator.getUUID());
                sysRoleFunction.setSysRoleId(role.getSysRoleId());
                sysRoleFunction.setSysFunctionId(functionId);

                roleFunctionList.add(sysRoleFunction);
            }
            sysRoleFunctionMapper.addRoleFunctionList(roleFunctionList);
        }
        return resultVal;
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
