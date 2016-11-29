package com.sysongy.poms.permi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysRoleService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;

import net.sf.json.JSONObject;

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
	public String queryUserListPage(@ModelAttribute CurrUser currUser,SysUser sysUser, @RequestParam(required = false) Integer resultInt,
									@RequestParam(required = false) Integer userType, ModelMap map){
		String stationId = currUser.getStationId();
		sysUser.setStationId(stationId);
		if(sysUser.getPageNum() == null){
			sysUser.setPageNum(GlobalConstant.PAGE_NUM);
			sysUser.setPageSize(GlobalConstant.PAGE_SIZE);
		}
		if(sysUser.getConvertPageNum() != null){
			if(sysUser.getConvertPageNum() > sysUser.getPageNumMax()){
				sysUser.setPageNum(sysUser.getPageNumMax());
			}else if(sysUser.getConvertPageNum() < 1){
				sysUser.setPageNum(1);
			}else{
				sysUser.setPageNum(sysUser.getConvertPageNum());
			}
		}
		sysUser.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);

		if(userType != null && !"".equals(userType)){
			sysUser.setUserType(userType);
			currUser.setUserType(userType);
			map.addAttribute("userType",userType);//将用户类型传递到页面
		}else{//当前用户类型
			SysUser userTemp = currUser.getUser();//将当前系统的登录用户类型设置为用户类型
			Integer sysType = userTemp.getUserType();
			sysUser.setUserType(sysType);
			currUser.setUserType(sysType);
			map.addAttribute("userType",sysType);
		}

		//封装分页参数，用于查询分页内容
		PageInfo<SysUser> userPageInfo = new PageInfo<SysUser>();
		userPageInfo = sysUserService.queryUserListPage(sysUser);
		map.addAttribute("userList",userPageInfo.getList());
		map.addAttribute("pageInfo",userPageInfo);
		map.addAttribute("sysUser",sysUser);

		if(resultInt != null && resultInt > 0){
			Map<String, Object> resultMap = new HashMap<>();

			if(resultInt == 1){
				resultMap.put("retMsg","新建成功！");
			}else if(resultInt == 2){
				resultMap.put("retMsg","修改成功！");
			}
			map.addAttribute("ret",resultMap);
		}

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
	public String updateStatusByUserId(@ModelAttribute CurrUser currUser,SysUser sysUser, ModelMap map){
		sysUser.setUserName(null);
		sysUserService.updateStatus(sysUser);
		String resultPath = "redirect:/web/permi/user/list/page";
		if(currUser.getUserType() == GlobalConstant.USER_TYPE_CRM){
			resultPath = resultPath + "?userType="+currUser.getUserType();
		}
		return resultPath;
	}
	/**
	 * 保存用户
	 * @return
	 */
	@RequestMapping("/save")
	public String saveUser(@ModelAttribute CurrUser currUser,SysUser user, ModelMap map){
		int resultInt = 0;
		String stationId = currUser.getStationId();
		if(user != null && user.getSysUserId() != null && !"".equals(user.getSysUserId())){
			//修改用户
			user.setUpdatedDate(new Date());
			String password = user.getPassword().trim();
			SysUser userParam = new SysUser();
			userParam.setSysUserId(user.getSysUserId());
			SysUser userTemp = sysUserService.queryUser(userParam);
			if(password.equals(userTemp.getPassword())){
				user.setPassword(null);
			}else{
				password = Encoder.MD5Encode(password.getBytes());
				user.setPassword(password);
			}
			sysUserService.updateUser(user);

			resultInt = 2;
		}else if(user != null){//添加

			/*String newid;
			SysUser userTemp = sysUserService.queryMaxIndex();
			if(userTemp == null || StringUtils.isEmpty(userTemp.getSysUserId()) || userTemp.getSysUserId().length() > 7){
				newid = "S" + "000001";
			}else{
				Integer tmp = Integer.valueOf(userTemp.getSysUserId().substring(1, 7)) + 1;
				newid = "S" + StringUtils.leftPad(tmp.toString() , 6, "0");
			}*/

			user.setSysUserId(UUIDGenerator.getUUID());
			user.setStationId(stationId);
			sysUserService.addUser(user);
			resultInt = 1;
		}

		String resultPath = "redirect:/web/permi/user/list/page?resultInt="+resultInt;
		if(currUser.getUserType() == GlobalConstant.USER_TYPE_CRM){
			resultPath = resultPath + "&userType="+currUser.getUserType();
		}

		return resultPath;
	}

	/**
	 * 删除用户
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteUserByUserId(@ModelAttribute CurrUser currUser,@RequestParam String userId, ModelMap map){
		SysUser user = new SysUser();
		if (userId != null) {
			user.setSysUserId(userId);
			user.setIsDeleted(GlobalConstant.STATUS_DELETE);
			sysUserService.updateStatus(user);
		}
		String resultPath = "redirect:/web/permi/user/list/page";
		if(currUser.getUserType() == GlobalConstant.USER_TYPE_CRM){
			resultPath = resultPath + "?userType="+currUser.getUserType();
		}
		return resultPath;
	}

	/**
	 * 根据当前用户ID获取角色列表
	 * @return
	 */
	@RequestMapping("/list/role")
	@ResponseBody
	public List<SysRole> queryRoleList(@ModelAttribute("currUser") CurrUser currUser,@RequestParam(required = false) Integer userType, ModelMap map){
		String stationId = currUser.getStationId();
		List<SysRole> roleList = sysRoleService.queryRoleListByUserType(userType+"",stationId);

		return roleList;
	}

	/**
	 * 获取气站用户列表
	 * @return
	 */
	@RequestMapping("/list/userType")
	@ResponseBody
	public String queryStationList(@ModelAttribute CurrUser currUser,@RequestParam int userType){
		List<SysUser> sysUserList = new ArrayList<>();
		SysUser sysUser = new SysUser();
		String stationId = currUser.getStationId();
		sysUser.setStationId(stationId);
		sysUser.setUserType(userType);

		sysUserList = sysUserService.queryUserListByUserType(sysUser);
		String resultStr = JSON.toJSONString(sysUserList);
		return resultStr;
	}

	/**
	 * 根据用户名称和用户类型判断用户名称是否存在
	 * @param admin_username
	 * @param userType
     * @return
     */
	@RequestMapping("/info/isExist")
	@ResponseBody
	public JSONObject queryUserByNameAndType(@RequestParam String admin_username,@RequestParam String userType){
		JSONObject json = new JSONObject();

		SysUser sysUser = new SysUser();
		sysUser.setUserName(admin_username);
		/*sysUser.setUserType(Integer.valueOf(userType));*/
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
	public JSONObject queryUserByName(HttpServletRequest request, @ModelAttribute CurrUser currUser, @RequestParam String userName, @RequestParam(required = false) String userType, ModelMap map){
		String validateId = request.getParameter("fieldId");
		String validateValue = request.getParameter("fieldValue");

		JSONObject json = new JSONObject();


		List<SysUser> userList = new ArrayList<>();
		if(userName != null && !"".equals(userName)){
			SysUser sysUser = new SysUser();
			sysUser.setUserName(userName.trim());
			userList = sysUserService.queryUserListByUserName(sysUser);
		}

		if(userList != null && userList.size() > 0){
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
		SysUser sysUser = new SysUser();
		int userType = currUser.getUserType();
		String stationId = currUser.getStationId();
		sysUser.setStationId(stationId);
		sysUser.setUserType(userType);

		List<SysUser> userList = sysUserService.queryUserListByUserType(sysUser);

		return userList;
	}

	/**
	 * 判断用户密码是否正确
	 * @return
	 */
	@RequestMapping("/info/isPassword")
	@ResponseBody
	public JSONObject queryUserByPassword(@ModelAttribute CurrUser currUser,@RequestParam String password, ModelMap map){
		String sysUserId = currUser.getUserId();
		JSONObject json = new JSONObject();
		Map<String, Object> userMap = sysUserService.queryUserMapByUserId(sysUserId);
		password = Encoder.MD5Encode(password.getBytes());

		if(userMap.get("password") != null && password.equals(userMap.get("password").toString())){
			json.put("valid",true);
		}else{
			json.put("valid",false);
		}
		return json;
	}

	/**
	 * 修改用户密码
	 * @return
	 */
	@RequestMapping("/update/password")
	@ResponseBody
	public String updateUserPasswordByUserId(@ModelAttribute CurrUser currUser,@RequestParam String password, ModelMap map){
		String sysUserId = currUser.getUserId();
		password = Encoder.MD5Encode(password.getBytes());

		SysUser user = new SysUser();
		user.setSysUserId(sysUserId);
		user.setPassword(password);

		int resultVal = sysUserService.updateUserInfo(user);

		return resultVal+"";
	}
}
