package com.sysongy.poms.permi.controller;

import com.sysongy.poms.base.controller.BaseContoller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @FileName: SystemUserController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月27日, 9:51
 * @Author: Dongqiang.Wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */

@RequestMapping("/web/permi/user")
@Controller
public class SystemUserController extends BaseContoller{

	/**
	 * 查询用户列表
	 * @return
	 */
	@RequestMapping("/list/page")
	public String queryUserListPage(ModelMap map){
	    map.addAttribute("current_module", "webpage/doc/boss_doc_list");
	    return "webpage/permi/demo";
	}

	/**
	 * 查询用户详情
	 * @return
	 */
	@RequestMapping("/info")
	public String queryUserInfoByUserId(ModelMap map){
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/permi/demo";
	}

	/**
	 * 添加用户
	 * @return
	 */
	@RequestMapping("/add")
	public String addUser(ModelMap map){
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/permi/demo";
	}

	/**
	 * 修改用户
	 * @return
	 */
	@RequestMapping("/update")
	public String updateUserByUserId(ModelMap map){
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/permi/demo";
	}

	/**
	 * 删除用户
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteUserByUserId(ModelMap map){
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/permi/demo";
	}
}
