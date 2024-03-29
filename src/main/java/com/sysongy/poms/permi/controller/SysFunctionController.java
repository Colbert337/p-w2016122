package com.sysongy.poms.permi.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.permi.model.SysFunction;
import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.poms.permi.service.SysFunctionService;
import com.sysongy.poms.permi.service.SysFunctionService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.GroupUtil;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @FileName: SysFunctionController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月27日, 9:51
 * @Author: Dongqiang.Wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */

@RequestMapping("/web/permi/function")
@Controller
public class SysFunctionController extends BaseContoller{
	@Autowired
	SysFunctionService sysFunctionService;
	/**
	 * 查询功能列表(分页)
	 * @return
	 */
	@RequestMapping("/list/page")
	public String queryFunctionListPage(SysFunction function,@RequestParam(required = false) String parentIdTemp,
										@RequestParam(required = false) Integer resultInt, ModelMap map){
		function.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
		SysFunction sysFunction = new SysFunction();
		String parentId = "1";//根节点父ID
		String currName = "功能列表";

		if(parentIdTemp != null && !"".equals(parentIdTemp)){
			function.setParentId(parentIdTemp);
		}
		if (function != null && function.getParentId() != null) {
			parentId = function.getParentId();
			sysFunction = sysFunctionService.queryFunctionByFunctionId(parentId);
			if(sysFunction != null){
				currName = sysFunction.getFunctionName();
				parentId = sysFunction.getSysFunctionId();
			}
		}else{
			function.setParentId(parentId);
		}

		List<SysFunction> functionList = new ArrayList<>();
		functionList = sysFunctionService.queryFunctionListPage(function);
		map.addAttribute("functionList",functionList);
		map.addAttribute("parentId",parentId);
		map.addAttribute("parentName",currName);

		if(resultInt != null && resultInt > 0){
			Map<String, Object> resultMap = new HashMap<>();

			if(resultInt == 1){
				resultMap.put("retMsg","新建功能成功！");
			}else if(resultInt == 2){
				resultMap.put("retMsg","修改功能成功！");
			}
			map.addAttribute("ret",resultMap);
		}
		return "webpage/poms/permi/function_list";
	}

	/**
	 * 根据父级ID查询功能列表
	 * @param currUser 当前登录用户
	 * @param mapTemp
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<Map<String,Object>> queryFunctionAllList(@ModelAttribute("currUser") CurrUser currUser, ModelMap mapTemp){
		Map<String,Object> functionMap = new HashMap<>();
		int userType = currUser.getUser().getUserType();

		userType = 1;
		List<Map<String,Object>> sysFunctionList = sysFunctionService.queryFunctionAllList(userType);
		List<Map<String,Object>> functionListTree = new ArrayList<>();
		for (Map<String,Object> function:sysFunctionList) {
			Map<String,Object> functionTree = new HashMap<>();
			functionTree.put("id",function.get("sysFunctionId"));
			functionTree.put("pId",function.get("parentId"));
			functionTree.put("name",function.get("functionName"));

			functionListTree.add(functionTree);
		}
		//将数据做分组处理，需要优化分组函数
		/*Map group = GroupUtil.group(sysFunctionList, new GroupUtil.GroupBy<String>() {
			@Override
			public String groupby(Object obj) {
				Map m = (Map) obj;
				return m.get("pId").toString();    // 分组依据为parent
			}
		});

		List childL = new ArrayList();
		for (Map<String, Object> map : sysFunctionList) {
			String groupKey = map.get("id").toString();
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
		functionMap.put("sysFunctionList",sysFunctionList);*/

		return functionListTree;
	}

	/**
	 * 根据父级ID查询功能列表
	 * @param currUser 当前登录用户
	 * @param mapTemp
	 * @return
	 */
	@RequestMapping("/list/type")
	@ResponseBody
	public List<Map<String,Object>> queryFunctionListByType(@ModelAttribute("currUser") CurrUser currUser,@RequestParam(required = false) Integer roleType, ModelMap mapTemp){
		int userType = currUser.getUser().getUserType();
		if(roleType != null && !"".equals(roleType)){//获取页面传递的角色类型
			userType = roleType;
		}
		List<Map<String,Object>> sysFunctionList = sysFunctionService.queryFunctionListByType(userType);
		List<Map<String,Object>> functionListTree = new ArrayList<>();
		for (Map<String,Object> function:sysFunctionList) {
			Date date = new Date();
			String fomt = function.get("createdDate").toString();

			Map<String,Object> functionTree = new HashMap<>();
			functionTree.put("id",function.get("sysFunctionId"));
			functionTree.put("pId",function.get("parentId"));
			functionTree.put("name",function.get("functionName"));

			functionListTree.add(functionTree);
		}

		return functionListTree;
	}
	/**
	 * 根据父级ID查询功能列表
	 * @param currUser 当前登录用户
	 * @param parentId 父级功能ID
	 * @param mapTemp
     * @return
     */
	@RequestMapping("/list/parentId")
	@ResponseBody
	public Map<String,Object> queryFunctionListByParentId(@ModelAttribute("currUser") CurrUser currUser, @RequestParam String parentId, ModelMap mapTemp){
		Map<String,Object> functionMap = new HashMap<>();
		int userType = currUser.getUser().getUserType();
		if(parentId == null || "".equals(parentId)){
			parentId = "1";
			userType = 1;
		}
		if(parentId != null && !"".equals(parentId)){
			List<Map<String,Object>> sysFunctionList = sysFunctionService.queryFunctionListByParentId(userType,parentId);
			//将数据做分组处理，需要优化分组函数
			Map group = GroupUtil.group(sysFunctionList, new GroupUtil.GroupBy<String>() {
				@Override
				public String groupby(Object obj) {
					Map m = (Map) obj;
					return m.get("pid").toString();    // 分组依据为parent
				}
			});

			List childL = new ArrayList();
			for (Map<String, Object> map : sysFunctionList) {
				String groupKey = map.get("id").toString();
				//groupkey 包含id时，当前id对象有一个子集
				//移除子集中的对象
				//{}
				if (group.containsKey(groupKey)) {
					List childList = (List) group.get(groupKey);
					childL.addAll(childList);
					map.put("child", childList);
				}
			}

			sysFunctionList.removeAll(childL);

			//combination data to response
			Map<String,Object> m = new HashMap<>();
			m.put("resMenu", sysFunctionList);
			functionMap.put("sysFunctionList",sysFunctionList);
		}

		return functionMap;
	}
	/**
	 * 修改用户
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public SysFunction updateFunctionByFunctionId(@RequestParam String sysFunctionId, ModelMap map){
		SysFunction function = sysFunctionService.queryFunctionByFunctionId(sysFunctionId);

		return function;
	}
	/**
	 * 保存功能
	 * @return
	 */
	@RequestMapping("/save")
	public String saveFunction(SysFunction function, ModelMap map) throws Exception{
		int resultInt = 0;
		if(function != null && function.getSysFunctionId() != null && !"".equals(function.getSysFunctionId())){
			//修改功能
			sysFunctionService.updateFunction(function);
			resultInt = 2;
		}else if(function != null){//添加
			String functionId = UUIDGenerator.getUUID();
			function.setSysFunctionId(functionId);
			function.setFunctionStatus(GlobalConstant.STATUS_ENABLE);
			function.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
			sysFunctionService.addFunction(function);

			resultInt = 1;
		}

		return "redirect:/web/permi/function/list/page?parentIdTemp="+function.getParentId()+"&resultInt="+resultInt;
	}

	/**
	 * 删除功能
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteFunctionByFunctionId(@RequestParam String functionId, ModelMap map){
		SysFunction function = new SysFunction();
		String parentId = "1";
		if (functionId != null) {
			function.setSysFunctionId(functionId);
			function.setIsDeleted(GlobalConstant.STATUS_DELETE);//
			sysFunctionService.updateFunction(function);

			SysFunction sysFunction = sysFunctionService.queryFunctionByFunctionId(functionId);
			parentId = sysFunction.getParentId();
		}
		return "redirect:/web/permi/function/list/page?parentIdTemp="+parentId;
	}

	/**
	 * 获取当前排序序号
	 * @return
	 */
	@RequestMapping("/info/sort")
	@ResponseBody
	public int queryFunctionSort(ModelMap map){
		int sortIndex = 0;
		SysFunction function = sysFunctionService.queryFunctionSort();
		if(function != null){
			sortIndex = function.getFunctionSort();
			sortIndex ++;
		}
		return sortIndex;
	}
}
