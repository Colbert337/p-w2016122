package com.sysongy.poms.permi.controller;

import com.sysongy.poms.base.controller.BaseContoller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @FileName: SysRoleController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月27日, 9:51
 * @Author: Dongqiang.Wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */

@RequestMapping("/web/permi/role")
@Controller
public class SysRoleController extends BaseContoller{

	/**
	 * 查询角色列表(分页)
	 * @return
	 */
	@RequestMapping("/list/page")
	public String queryRoleListPage(ModelMap map){
	    map.addAttribute("current_module", "webpage/doc/boss_doc_list");
	    return "webpage/poms/permi/role_list";
	}

	/**
	 * 查询角色列表
	 * @return
	 */
	@RequestMapping("/list")
	public String queryRoleList(ModelMap map){
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/poms/permi/role_list";
	}

	/**
	 * 查询角色列表
	 * @return
	 */
	@RequestMapping("/add")
	public String addRole(ModelMap map){
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/poms/permi/role_list";
	}

	/**
	 * 查询角色列表
	 * @return
	 */
	@RequestMapping("/update")
	public String updateRole(ModelMap map){
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/poms/permi/role_list";
	}

	/**
	 * 查询角色列表
	 * @return
	 */
	@RequestMapping("/save")
	public String saveRole(ModelMap map){
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/poms/permi/role_list";
	}
}
