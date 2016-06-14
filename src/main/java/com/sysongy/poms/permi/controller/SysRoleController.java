package com.sysongy.poms.permi.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.poms.permi.service.SysRoleService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	GastationService gastationService;
	/**
	 * 查询角色列表(分页)
	 * @return
	 */
	@RequestMapping("/list/page")
	public String queryRoleListPage(@ModelAttribute CurrUser currUser, SysRole role, ModelMap map){
		if(role.getPageNum() == null){
			role.setPageNum(GlobalConstant.PAGE_NUM);
			role.setPageSize(GlobalConstant.PAGE_SIZE);
		}

		role.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
		int roleType = currUser.getUserType();
		role.setRoleType(roleType);
		//封装分页参数，用于查询分页内容
		PageInfo<SysRole> rolePageInfo = new PageInfo<SysRole>();
		rolePageInfo = sysRoleService.queryRoleListPage(role);
		map.addAttribute("roleList",rolePageInfo.getList());
		map.addAttribute("pageInfo",rolePageInfo);
	    return "webpage/poms/permi/role_list";
	}

	/**
	 * 修改用户
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> updateRoleByRoleId(@RequestParam String sysRoleId, ModelMap map){
		Map<String, Object> roleMap = new HashMap<>();
		roleMap = sysRoleService.queryRoleByRoleId(sysRoleId);
		return roleMap;
	}

	/**
	 * 获取气站列表
	 * @return
	 */
	@RequestMapping("/list/station")
	@ResponseBody
	public Map<String, Object> queryStationList(ModelMap map){
		Map<String, Object> stationMap = new HashMap<>();
		stationMap.put("","");
		List<Gastation> gastationList = null;
		try {
			gastationList = gastationService.getAllStationByArea("");
			stationMap.put("succ","100");//请求成功
			stationMap.put("gastationList",gastationList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stationMap;
	}
	/**
	 * 保存角色
	 * @return
	 */
	@RequestMapping("/save")
	public String saveRole(SysRole role, ModelMap map){
		if(role != null && role.getSysRoleId() != null && !"".equals(role.getSysRoleId())){
			//修改角色
			sysRoleService.updateRole(role);
		}else if(role != null){//添加
			role.setSysRoleId(UUIDGenerator.getUUID());
			role.setIsAdmin(GlobalConstant.ADMIN_NO);
			role.setRoleStatus(GlobalConstant.STATUS_ENABLE);
			role.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
			sysRoleService.addRole(role);
		}

		return "redirect:/web/permi/role/list/page";
	}

	/**
	 * 删除角色
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteRoleByRoleId(@RequestParam String roleId, ModelMap map){
		SysRole role = new SysRole();
		if (roleId != null) {
			role.setSysRoleId(roleId);
			role.setIsDeleted(GlobalConstant.STATUS_DELETE);//
			sysRoleService.updateRole(role);
		}
		return "redirect:/web/permi/role/list/page";
	}
}
