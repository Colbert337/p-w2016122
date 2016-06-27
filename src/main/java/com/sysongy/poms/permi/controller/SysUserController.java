package com.sysongy.poms.permi.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysRoleService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	@Autowired
	SysRoleService sysRoleService;
	/**
	 * 查询用户列表
	 * @return
	 */
	@RequestMapping("/list/page")
	public String queryUserListPage(@ModelAttribute CurrUser currUser,SysUser sysUser, ModelMap map){
		if(sysUser.getPageNum() == null){
			sysUser.setPageNum(GlobalConstant.PAGE_NUM);
			sysUser.setPageSize(GlobalConstant.PAGE_SIZE);
		}
		sysUser.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
		sysUser.setUserType(currUser.getUserType());
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
	public Map<String, Object> updateUserByUserId(@RequestParam String sysUserId, ModelMap map){
		Map<String, Object> userMap = sysUserService.queryUserMapByUserId(sysUserId);
		return userMap;
	}

	/**
	 * 修改状态
	 * @return
	 */
	@RequestMapping("/update/staruts")
	public String updateStatusByUserId(SysUser sysUser, ModelMap map){
		sysUserService.updateStatus(sysUser);
		return "redirect:/web/permi/user/list/page";
	}
	/**
	 * 保存用户
	 * @return
	 */
	@RequestMapping("/save")
	public String saveUser(SysUser user, ModelMap map){
		if(user != null && user.getSysUserId() != null && !"".equals(user.getSysUserId())){
			//修改用户
			user.setPassword(null);//不修改用户密码
			sysUserService.updateUser(user);
		}else if(user != null){//添加
			user.setSysUserId(UUIDGenerator.getUUID());
			user.setUserType(GlobalConstant.USER_TYPE_MANAGE);

			sysUserService.addUser(user);
		}

		return "redirect:/web/permi/user/list/page";
	}

	/**
	 * 删除用户
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteUserByUserId(@RequestParam String userId, ModelMap map){
		SysUser user = new SysUser();
		if (userId != null) {
			user.setSysUserId(userId);
			user.setIsDeleted(GlobalConstant.STATUS_DELETE);
			sysUserService.updateStatus(user);
		}
		return "redirect:/web/permi/user/list/page";
	}

	/**
	 * 根据当前用户ID获取角色列表
	 * @return
	 */
	@RequestMapping("/list/role")
	@ResponseBody
	public List<SysRole> queryRoleList(@ModelAttribute("currUser") CurrUser currUser, ModelMap map){
		int userType = currUser.getUser().getUserType();
		List<SysRole> roleList = sysRoleService.queryRoleListByUserType(userType+"");

		return roleList;
	}

	/**
	 * 获取气站用户列表
	 * @return
	 */
	@RequestMapping("/list/userType")
	@ResponseBody
	public String queryStationList(@RequestParam int userType){
		List<SysUser> sysUserList = new ArrayList<>();
		sysUserList = sysUserService.queryUserListByUserType(userType);
		String resultStr = JSON.toJSONString(sysUserList);
		return resultStr;
	}

	/**
	 * 根据用户名称和用户类型判断用户名称是否存在
	 * @param currUser
	 * @param admin_username
	 * @param userType
	 * @param map
     * @return
     */
	@RequestMapping("/info/isExist")
	@ResponseBody
	public JSONObject queryUserByNameAndType(@RequestParam String admin_username,@RequestParam String userType){
		JSONObject json = new JSONObject();

		SysUser sysUser = new SysUser();
		sysUser.setUserName(admin_username);
		sysUser.setUserType(Integer.valueOf(userType));
		SysUser user = sysUserService.queryUser(sysUser);

		if(user == null){
			json.put("valid",true);
		}else{
			json.put("valid",false); 
		}
		
		return json;
	}

	/**
	 * 根据用户名称判断用户名称是否存在
	 * @param currUser
	 * @param userName
	 * @param userType
	 * @param map
     * @return
     */
	@RequestMapping("/info/isUserName")
	@ResponseBody
	public JSONObject queryUserByName(HttpServletRequest request, @ModelAttribute CurrUser currUser, @RequestParam String userName, @RequestParam String userType, ModelMap map){
		String validateId = request.getParameter("fieldId");
		String validateValue = request.getParameter("fieldValue");

		JSONObject json = new JSONObject();

		SysUser sysUser = new SysUser();
		sysUser.setUserName(userName);
		SysUser user = sysUserService.queryUser(sysUser);

		if(user == null){
			json.put("valid",true);
		}else{
			json.put("valid",false);
		}

		return json;
	}


	/**
	 * 根据用户类型查询用户列表
	 * @param currUser
	 * @param map
     * @return
     */
	@RequestMapping("/list/info")
	@ResponseBody
	public List<SysUser> queryUserList(@ModelAttribute CurrUser currUser, ModelMap map){
		int userType = currUser.getUserType();
		List<SysUser> userList = sysUserService.queryUserListByUserType(userType);

		return userList;
	}

}
