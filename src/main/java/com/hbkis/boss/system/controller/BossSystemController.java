package com.hbkis.boss.system.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hbkis.boss.base.controller.BaseContoller;
import com.hbkis.boss.menu.model.BossMenu;
import com.hbkis.boss.menu.service.BossMenuService;
import com.hbkis.boss.role.model.Role;
import com.hbkis.boss.role.service.RoleService;
import com.hbkis.boss.system.model.BossSystem;
import com.hbkis.boss.system.model.SystemMenu;
import com.hbkis.boss.system.model.SystemMenuRole;
import com.hbkis.boss.system.service.BossSystemService;
import com.hbkis.boss.system.service.SystemMenuRoleService;
import com.hbkis.boss.system.service.SystemMenuService;
import com.hbkis.util.GlobalConstant;
import com.hbkis.util.UUIDGenerator;

/**
 * @FileName     :  BossSystemController.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.system.controller
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月17日, 下午4:26:47
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@RequestMapping("/web/bossSystem/")
@Controller
public class BossSystemController extends BaseContoller{

	
	@Autowired
	private BossSystemService bossSystemService;
	
	@Autowired
	private BossMenuService bossMenuService;
	
	@Autowired
	private SystemMenuService systemMenuService;
	
	@Autowired
	private SystemMenuRoleService systemMenuRoleService;
	
	@Autowired
	private RoleService roleService;
	
	
	/**
	 * 查询所有系统
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryAllSystem")
	public String queryAllSystem(ModelMap map){
		
		List<BossSystem> sysList = new ArrayList<BossSystem>();
		sysList = bossSystemService.queryAllBossSystem();
		map.addAttribute("sysList", sysList);
	    map.addAttribute("current_module", "webpage/system/boss_system_list");
	    return "comm/g_main";
	}
	
	/**
	 * 点击添加按钮  跳转的页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/addSystem")
	public String addSystem(ModelMap map){
		List<BossMenu> bosslist = new ArrayList<BossMenu>();
		bosslist = bossMenuService.queryAllBossMenu();
		map.addAttribute("bosslist", bosslist);
		 map.addAttribute("current_module", "webpage/system/boss_system_input");
		 return "comm/g_main";
	}
	
	/**
	 * 保存 系统信息
	 * @param bossSystem
	 * @param map
	 * @return
	 */
	@RequestMapping("/saveSystem")
	public String saveSystem(BossSystem bossSystem,ModelMap map){
		
		String httpPath  = prop.getProperty("http_pems_path");
		
		if(bossSystem != null && !"".equals(bossSystem)){
			if(bossSystem.getSystemId() != null && !"".equals(bossSystem.getSystemId())){
				//修改
				bossSystemService.updateBossSystem(bossSystem,httpPath);
			}else{
				//新增
				bossSystem.setSystemId(UUIDGenerator.getUUID());
				bossSystemService.addBossSystem(bossSystem,httpPath);
			}
		}
		return  "redirect:/web/bossSystem/queryAllSystem";
	}
	
	
	/**
	 * 修改时  查询对象
	 * @param systemId
	 * @param map
	 * @return
	 */
	@RequestMapping("/update")
	public String querySystemBySystemId(@RequestParam String systemId,ModelMap map){
		BossSystem bossSystem = null;
		if(systemId != null && !"".equals(systemId)){
			bossSystem = bossSystemService.queryByBossSystemId(systemId);
		}
		map.addAttribute("bossSystem", bossSystem);
		//根据system  查询系统菜单
		List<SystemMenu> sysmenuList = new ArrayList<SystemMenu>();
		if(systemId != null && !"".equals(systemId)){
			sysmenuList = systemMenuService.querySystemBySystemId(systemId);
		}
		map.addAttribute("bosslist", sysmenuList);
		
		map.addAttribute("current_module", "webpage/system/boss_system_input");
		return "comm/g_main";
	}
	
	/**
	 * 根据boss  和 systemName  查询  grade 表  看是否有重复的  系统信息
	 * @param systemId
	 * @param systemName
	 * @return
	 */
	@RequestMapping("/check")
	@ResponseBody
	public Map<String,String> checkByName(@RequestParam String systemName,@RequestParam String systemId){ 
		List<BossSystem> bossList = new ArrayList<BossSystem>();
		Map<String, String> map = new HashMap<String, String>();
		try {
			systemName= new String( systemName.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if((systemName != null && !"".equals(systemName))){
			bossList = bossSystemService.checkBySystemName(systemName);
			if(bossList.size() > 0){
				if(systemId != null && !"".equals(systemId)){
					map.put("isHave","1");
					map.put("update", "yes");//说明是修改
				}else{
					map.put("isHave","1");
					map.put("update","no");
				}
			}else{
				map.put("isHave","0");
			}
		}
		return map;
	}
	
	
	/**
	 * 禁用  系统信息
	 * @param systemId
	 * @return
	 */
	@RequestMapping("/deleteSystem")
	public String deleteSystem(@RequestParam String systemId){
		BossSystem bossSystem = null;
		if(systemId != null && !"".equals(systemId)){
			bossSystem = bossSystemService.queryByBossSystemId(systemId);
			bossSystem.setSystemStatus(0);
			bossSystemService.updateBossSystem(bossSystem,null);
		}
		return  "redirect:/web/bossSystem/queryAllSystem";
	}
	
	/**
	 * 启用  系统信息
	 * @param systemId
	 * @return
	 */
	@RequestMapping("/qiyongSystem")
	public String qiyongSystem(@RequestParam String systemId){
		BossSystem bossSystem = null;
		if(systemId != null && !"".equals(systemId)){
			bossSystem = bossSystemService.queryByBossSystemId(systemId);
			bossSystem.setSystemStatus(1);
			bossSystemService.updateBossSystem(bossSystem,null);
		}
		return  "redirect:/web/bossSystem/queryAllSystem";
	}
	
	
	
	/**
	 * 查询所有系统权限
	 * @param map
	 * @return
	 */
	@RequestMapping("/querySystemPower")
	public String querySystemPower(ModelMap map){
		
		List<BossSystem> sysList = new ArrayList<BossSystem>();
		sysList = bossSystemService.queryAllBossSystem();
		map.addAttribute("sysList", sysList);
	    map.addAttribute("current_module", "webpage/system/system_power_list");
	    return "comm/g_main";
	}
	
	
	/**
	 * 修改系统权限   （展示权限菜单）
	 * @param systemId
	 * @param map
	 * @return
	 */
	@RequestMapping("/updateMenuRole")
	public String updateMenuRole(@RequestParam String systemId,ModelMap map){
		//根据systemId 查询系统名称
		BossSystem bossSystem = null;
		if(systemId != null && !"".equals(systemId)){
			bossSystem = bossSystemService.querySystemBySystemId(systemId);
		}
		map.addAttribute("bossSystem", bossSystem);
		

		map.addAttribute("systemId", systemId);
		
        map.addAttribute("current_module", "webpage/system/system_power_update");
        return "comm/g_main";
	}
	
	
	/**
	 * 查询所有菜单
	 * @return
	 */
	@RequestMapping("/querySystemTree")
	@ResponseBody
	//根据systemId查询学校信息  用于树的展示
	public Map<String, Object> querySystemTree(ModelMap map,@RequestParam String systemId){
		
		//根据systemId 查询此系统具有那些菜单权限  查看系统菜单表   systemMenu
		List<SystemMenu> menuList = new ArrayList<SystemMenu>();
		if(systemId != null && !"".equals(systemId)){
			menuList = systemMenuService.querySystemBySystemId(systemId);
		}
		map.addAttribute("menuList", menuList);
		
		
		Map<String, Object> treeMap = new HashMap<String, Object>(); 
		JSONArray array = new JSONArray();
		List<BossMenu> bosslist = new ArrayList<BossMenu>();
		bosslist = bossMenuService.queryAllBossMenu();
		map.addAttribute("rootId", 1);
		//设置根节点
		JSONObject rootjson = new JSONObject();
		rootjson.put("rootId", 1);
		rootjson.put("name","所有菜单");
		rootjson.put("id","1");
		rootjson.put("open", true);
		JSONArray bossArray = new JSONArray();
		
		if(bosslist != null && bosslist.size() > 0){
			for (BossMenu bossMenu : bosslist) {
				JSONObject bossjson = new JSONObject();
				String menuId = bossMenu.getMenuId();	//获取菜单id
				String menuName = bossMenu.getMenuName();
				
				for (SystemMenu systemMenu : menuList) {
					if(systemMenu.getMenuId().equals(menuId)){
						bossjson.put("checked", true);
					}
				}
				
				bossjson.put("id", menuId);
				bossjson.put("name", menuName);
				bossjson.put("pId", bossMenu.getParentId());
				bossArray.add(bossjson);
				
				String parentId = bossMenu.getMenuId();
				if(parentId != null && !"".equals(parentId) && !parentId.equals("0")){
					JSONArray nextArray = queryTreeNode(parentId,menuList);
					bossjson.put("children", nextArray);
				}
			}
			rootjson.put("children", bossArray);
			array.add(rootjson);
			treeMap.put("tree", array);
		}
		
		return treeMap;
	}
	
	
	
	/**
	 * 递归调用的方法   用来查看树节点
	 * @param docCatgId
	 * @param parentId
	 * @return
	 */

	
	public JSONArray queryTreeNode(String parentId,List<SystemMenu> menuList){
		
		List<BossMenu> bossMenuList = bossMenuService.queryMenuByParentId(parentId);	//根据id 查找
		JSONArray menuArray = new JSONArray();
		if(bossMenuList != null && bossMenuList.size() >0){
			for (BossMenu boss : bossMenuList) {
				String bosParentId = boss.getParentId();
				JSONObject bossjson = new JSONObject();
				
				for (SystemMenu systemMenu : menuList) {
					if(systemMenu.getMenuId().equals(boss.getMenuId())){
						bossjson.put("checked", true);
					}
				}
				bossjson.put("pId", boss.getParentId());
				bossjson.put("name", boss.getMenuName());
				bossjson.put("id", boss.getMenuId());
				menuArray.add(bossjson);
				 if (boss.getMenuId() != null && !boss.getMenuId().equals("0") && !"".equals(boss.getMenuId())){
					 List<BossMenu> bossMenuList2 = bossMenuService.queryMenuByParentId(boss.getMenuId());	//根据id 查找
						if(bossMenuList2 != null && bossMenuList2.size() >0){
							JSONArray menuArray2 = new JSONArray();//年级 数组
							for (BossMenu menu2 : bossMenuList2) {
								
								JSONObject bossjson2 = new JSONObject();
								for (SystemMenu systemMenu : menuList) {
									if(systemMenu.getMenuId().equals(menu2.getMenuId())){
										bossjson2.put("checked", true);
									}
								}
								bossjson2.put("id", menu2.getMenuId());
								bossjson2.put("name", menu2.getMenuName());
								bossjson2.put("pId", menu2.getParentId());
								menuArray2.add(bossjson2);
								bossjson.put("children", menuArray2);
								
								String menuId2 = menu2.getMenuId();
								if(!"".equals(menuId2) && menuId2 != null && !menuId2.equals("0")){
									List<BossMenu> bossMenuList3 = bossMenuService.queryMenuByParentId(menuId2);	//根据id 查找
									if(bossMenuList3 != null && bossMenuList3.size() >0){
										JSONArray menuArray3 = new JSONArray();
										for (BossMenu menu3 : bossMenuList3) {
											
											JSONObject bossjson3 = new JSONObject();
											for (SystemMenu systemMenu : menuList) {
												if(systemMenu.getMenuId().equals(menu3.getMenuId())){
													bossjson3.put("checked", true);
												}
											}
											bossjson3.put("id", menu3.getMenuId());
											bossjson3.put("name", menu3.getMenuName());
											bossjson3.put("pId", menu3.getParentId());
											menuArray3.add(bossjson3);
											bossjson2.put("children", menuArray3);
											
											String menuId3 = menu3.getMenuId();
											if(!"".equals(menuId3) && menuId3 != null && !menuId3.equals("0")){
												List<BossMenu> bossMenuList4 = bossMenuService.queryMenuByParentId(menuId3);	//根据id 查找
												if(bossMenuList4 != null && bossMenuList4.size() >0){
													JSONArray menuArray4 = new JSONArray();//年级 数组
													for (BossMenu menu4 : bossMenuList4) {
														
														JSONObject bossjson4 = new JSONObject();
														for (SystemMenu systemMenu : menuList) {
															if(systemMenu.getMenuId().equals(menu4.getMenuId())){
																bossjson4.put("checked", true);
															}
														}
														bossjson4.put("id", menu4.getMenuId());
														bossjson4.put("name", menu4.getMenuName());
														bossjson4.put("pId", menu4.getParentId());
														menuArray4.add(bossjson4);
														bossjson3.put("children", menuArray4);
													}
												}
											
											}
											
											
										}
									}
								
								}
							}
						}
				 
				 }
			}
		}
		return menuArray;
	}
	
	
	/**
	 * 给  系统修改权限
	 * @param menuIds
	 * @param map
	 * @param systemId
	 * @return
	 */
	@RequestMapping("/addSystemMenu")
	public String addSystemMenu(@RequestParam String menuIds,ModelMap map,@RequestParam String systemId){
		//获取之前的  权限  根据systemId  查找systemMENU表
		List<SystemMenu> list = systemMenuService.querySystemBySystemId(systemId);
		if(list != null && list.size()>0){
			for (SystemMenu systemMenu : list) {
				String systemMenuId = systemMenu.getSystemMenuId();
				//根据systemMenuId 去查询  system_menu_role表
				List<SystemMenuRole> menuroleList = systemMenuRoleService.querySystemMenuRoleListBySystemMenuId(systemMenuId);
				if(menuroleList != null && menuroleList.size()>0){
					for (SystemMenuRole systemMenuRole : menuroleList) {
						systemMenuRoleService.deleteBySystemMenuRoleId(systemMenuRole.getSystemMenuRoleId());
					}
				}
				
				//先删除之前的权限
				systemMenuService.deleteBySystemMenuId(systemMenu.getSystemMenuId());
			}
		}
		
		
		//开始添加新的权限
		
		if(menuIds != null && !"".equals(menuIds)){
			String menus[] = menuIds.split(",");
			for (int i = 0; i < menus.length; i++) {
				SystemMenu systemMenu = new SystemMenu();
				String menuId = menus[i];
				systemMenu.setSystemMenuId(UUIDGenerator.getUUID());
				systemMenu.setMenuId(menuId);
				//根据菜单id 查询菜单名称
				BossMenu bossMenu = bossMenuService.queryByMenuId(menuId);
				if(bossMenu != null && !"".equals(bossMenu)){
					String menuName = bossMenu.getMenuName(); 
					systemMenu.setMenuName(menuName);
					systemMenu.setMenuSort(i);
				}
				systemMenu.setSystemId(systemId);
				systemMenuService.addSystemMenu(systemMenu);
				
				
				//然后 给 systemMenuRole  添加
				String systemMenuId = systemMenu.getSystemMenuId();
				//根据systemId  查询 roleId
				
				Role role = roleService.queryRoleBySystemId(systemId);
				String roleId = role.getRoleId();
				
				SystemMenuRole systemMenuRole = new SystemMenuRole();
				systemMenuRole.setSystemMenuRoleId(UUIDGenerator.getUUID());
				systemMenuRole.setSystemMenuId(systemMenuId);
				systemMenuRole.setRoleId(roleId);
				systemMenuRoleService.addSystemMenuRole(systemMenuRole);
				
			}
			
		}
		return  "redirect:/web/bossSystem/querySystemPower";
	}
	
}
