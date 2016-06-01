package com.sysongy.poms.permi.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class SysUserController extends BaseContoller{

	@Autowired
	SysUserService sysUserService;
	/**
	 * 查询用户列表
	 * @return
	 */
	@RequestMapping("/list/page")
	public String queryUserListPage(SysUser sysUser, ModelMap map){
		if(sysUser.getPageNum() == null){
			sysUser.setPageNum(GlobalConstant.PAGE_NUM);
			sysUser.setPageSize(GlobalConstant.PAGE_SIZE);
		}

		//封装分页参数，用于查询分页内容
		PageInfo<SysUser> userPageInfo = new PageInfo<SysUser>();
		userPageInfo = sysUserService.queryUserListPage(sysUser);
		map.addAttribute("userList",userPageInfo.getList());
		map.addAttribute("pageInfo",userPageInfo);

	    return "webpage/poms/permi/user_list";
	}

	/**
	 * 查询用户详情
	 * @return
	 */
	@RequestMapping("/info")
	public String queryUserInfoByUserId(ModelMap map){
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/poms/permi/demo";
	}

	/**
	 * 添加用户
	 * @return
	 */
	@RequestMapping("/add")
	public String addUser(ModelMap map){
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		return "webpage/poms/permi/user_edit";
	}

	/**
	 * 修改用户
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public SysUser updateUserByUserId(@RequestParam String sysUserId, ModelMap map){
		SysUser user = sysUserService.queryUserByUserId(sysUserId);

		return user;
	}

	/**
	 * 保存用户
	 * @return
	 */
	@RequestMapping("/save")
	public String saveUser(SysUser user, ModelMap map){
		if(user != null){
			user.setSysUserId(UUIDGenerator.getUUID());
			user.setUserType(GlobalConstant.USER_TYPE_MANAGE);
			String passwordStr = user.getPassword();
			passwordStr = Encoder.MD5Encode(passwordStr.getBytes());
			user.setPassword(passwordStr);
			sysUserService.addUser(user);
		}

		return "redirect:/web/permi/user/list/page";
	}

	/**
	 * 删除用户
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteUserByUserId(ModelMap map){
		return "webpage/poms/permi/demo";
	}
}
