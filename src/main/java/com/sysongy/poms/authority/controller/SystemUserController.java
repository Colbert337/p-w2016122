package com.sysongy.poms.authority.controller;

import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.doc.model.BossDoc;
import com.sysongy.poms.doc.service.BossDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: SystemUserController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.authority.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月27日, 9:51
 * @Author: Dongqiang.Wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */

@RequestMapping("/web/authority/user")
@Controller
public class SystemUserController extends BaseContoller{

	@Autowired
	private BossDocService bossDocService;
	
	/**
	 * 查询用户列表
	 * @return
	 */
	@RequestMapping("/list/page")
	public String queryUserListPage(ModelMap map){
		List<BossDoc> bossList = new ArrayList<BossDoc>();
	    map.addAttribute("current_module", "webpage/doc/boss_doc_list");
	    return "webpage/authority/demo";
	}

	/**
	 * 查询用户详情
	 * @return
	 */
	@RequestMapping("/info")
	public String queryUserInfoByUserId(ModelMap map){
		List<BossDoc> bossList = new ArrayList<BossDoc>();
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/authority/demo";
	}

	/**
	 * 添加用户
	 * @return
	 */
	@RequestMapping("/add")
	public String addUser(ModelMap map){
		List<BossDoc> bossList = new ArrayList<BossDoc>();
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/authority/demo";
	}

	/**
	 * 修改用户
	 * @return
	 */
	@RequestMapping("/update")
	public String updateUserByUserId(ModelMap map){
		List<BossDoc> bossList = new ArrayList<BossDoc>();
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/authority/demo";
	}

	/**
	 * 删除用户
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteUserByUserId(ModelMap map){
		List<BossDoc> bossList = new ArrayList<BossDoc>();
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/authority/demo";
	}
}
