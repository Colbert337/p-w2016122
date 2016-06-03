package com.sysongy.poms.permi.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.permi.model.SysFunction;
import com.sysongy.poms.permi.service.SysFunctionService;
import com.sysongy.poms.permi.service.SysFunctionService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String queryFunctionListPage(SysFunction function, ModelMap map){
		if(function.getPageNum() == null){
			function.setPageNum(GlobalConstant.PAGE_NUM);
			function.setPageSize(GlobalConstant.PAGE_SIZE);
		}

		function.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
		//封装分页参数，用于查询分页内容
		PageInfo<SysFunction> functionPageInfo = new PageInfo<SysFunction>();
		functionPageInfo = sysFunctionService.queryFunctionListPage(function);
		map.addAttribute("functionList",functionPageInfo.getList());
		map.addAttribute("pageInfo",functionPageInfo);
		return "webpage/poms/permi/function_list";
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
	public String saveFunction(SysFunction function, ModelMap map){
		if(function != null && function.getSysFunctionId() != null && !"".equals(function.getSysFunctionId())){
			//修改功能
			sysFunctionService.updateFunction(function);
		}else if(function != null){//添加
			function.setSysFunctionId(UUIDGenerator.getUUID());
			function.setFunctionStatus(GlobalConstant.STATUS_ENABLE);
			function.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
			sysFunctionService.addFunction(function);
		}

		return "redirect:/web/permi/function/list/page";
	}

	/**
	 * 删除功能
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteFunctionByFunctionId(@RequestParam String functionId, ModelMap map){
		SysFunction function = new SysFunction();
		if (functionId != null) {
			function.setSysFunctionId(functionId);
			function.setIsDeleted(GlobalConstant.STATUS_DELETE);//
			sysFunctionService.updateFunction(function);
		}
		return "redirect:/web/permi/function/list/page";
	}
}
