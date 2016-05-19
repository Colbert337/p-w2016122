package com.sysongy.poms.menu.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.menu.model.BossMenu;
import com.sysongy.poms.menu.service.BossMenuService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PinyinUtil;
import com.sysongy.util.UUIDGenerator;

/**
 * @FileName     :  BossMenuController.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.menu.controller
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月15日, 下午6:43:34
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@RequestMapping("/web/bossMenu")
@Controller
public class BossMenuController extends BaseContoller{
	
	@Autowired
	private BossMenuService bossMenuService;
	
	
	/**
	 * 进入 树形菜单的页面
	 */
  @RequestMapping("/goBossMenuList")  
    public String index(ModelMap map){  
	  	List<BossMenu> bosslist = new ArrayList<BossMenu>();
		bosslist = bossMenuService.queryAllBossMenu();
		map.addAttribute("bosslist", bosslist);
        map.addAttribute("current_module", "webpage/menu/boss_menu_list");
        return "common/g_main";
    }
	

	/**
	 * 查询所有菜单
	 * @return
	 */
	@RequestMapping("/queryMenuTree")
	@ResponseBody
	//根据systemId查询学校信息  用于树的展示
	public Map<String, Object> queryMenuTree(ModelMap map){
		Map<String, Object> treeMap = new HashMap<String, Object>(); 
		JSONArray array = new JSONArray();
		List<BossMenu> bosslist = new ArrayList<BossMenu>();
		bosslist = bossMenuService.queryAllBossMenu();
		map.addAttribute("rootId", 1);
		//设置根节点
		JSONObject rootjson = new JSONObject();
		rootjson.put("rootId", 1);
		rootjson.put("name","所有菜单");
		rootjson.put("open", true);
		//rootjson.put("id","1");
		JSONArray bossArray = new JSONArray();
		
		if(bosslist != null && bosslist.size() > 0){
			for (BossMenu bossMenu : bosslist) {
				JSONObject bossjson = new JSONObject();
				String menuId = bossMenu.getMenuId();	//获取菜单id
				String menuName = bossMenu.getMenuName();
				bossjson.put("pId", menuId);
				bossjson.put("name", menuName);
				//bossjson.put("id", menuId);
				bossArray.add(bossjson);
				
				String parentId = bossMenu.getMenuId();
				if(parentId != null && !"".equals(parentId) && !parentId.equals("0")){
					JSONArray nextArray = queryTreeNode(parentId);
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

	
	public JSONArray queryTreeNode(String parentId){
		
		List<BossMenu> bossMenuList = bossMenuService.queryMenuByParentId(parentId);	//根据id 查找
		JSONArray menuArray = new JSONArray();
		if(bossMenuList != null && bossMenuList.size() >0){
			for (BossMenu boss : bossMenuList) {
				String bosParentId = boss.getParentId();
				JSONObject bossjson = new JSONObject();
				bossjson.put("id", boss.getMenuId());
				bossjson.put("name", boss.getMenuName());
				menuArray.add(bossjson);
				 if (boss.getMenuId() != null && !"".equals(boss.getMenuId())){
					 List<BossMenu> bossMenuList2 = bossMenuService.queryMenuByParentId(boss.getMenuId());	//根据id 查找
						if(bossMenuList2 != null && bossMenuList2.size() >0){
							JSONArray menuArray2 = new JSONArray();//年级 数组
							for (BossMenu menu2 : bossMenuList2) {
								
								JSONObject bossjson2 = new JSONObject();
								bossjson2.put("id", menu2.getMenuId());
								bossjson2.put("name", menu2.getMenuName());
								//bossjson2.put("pId", menu2.getParentId());
								menuArray2.add(bossjson2);
								bossjson.put("children", menuArray2);
								
								String menuId2 = menu2.getMenuId();
								if(!"".equals(menuId2) && menuId2 != null && !menuId2.equals("0")){
									List<BossMenu> bossMenuList3 = bossMenuService.queryMenuByParentId(menuId2);	//根据id 查找
									if(bossMenuList3 != null && bossMenuList3.size() >0){
										JSONArray menuArray3 = new JSONArray();
										for (BossMenu menu3 : bossMenuList3) {
											
											JSONObject bossjson3 = new JSONObject();
											bossjson3.put("id", menu3.getMenuId());
											bossjson3.put("name", menu3.getMenuName());
											//bossjson3.put("pId", menu3.getParentId());
											menuArray3.add(bossjson3);
											bossjson2.put("children", menuArray3);
											
											String menuId3 = menu3.getMenuId();
											if(!"".equals(menuId3) && menuId3 != null && !menuId3.equals("0")){
												List<BossMenu> bossMenuList4 = bossMenuService.queryMenuByParentId(menuId3);	//根据id 查找
												if(bossMenuList4 != null && bossMenuList4.size() >0){
													JSONArray menuArray4 = new JSONArray();//年级 数组
													for (BossMenu menu4 : bossMenuList4) {
														
														JSONObject bossjson4 = new JSONObject();
														bossjson4.put("id", menu4.getMenuId());
														bossjson4.put("name", menu4.getMenuName());
														//bossjson4.put("pId", menu4.getParentId());
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
	 * 获取某个菜单下面的菜单
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryNextMenu")
	public String queryNextMenu(ModelMap map,@RequestParam String parentId){
		
		List<BossMenu> bosslist = new ArrayList<BossMenu>();
		if(parentId != null && !"".equals(parentId)){
			bosslist = bossMenuService.queryMenuByParentId(parentId);
		}
		map.addAttribute("bosslist", bosslist);
		map.addAttribute("current_module", "webpage/menu/boss_menu_list");
		map.addAttribute("menuId", parentId);
        return "common/g_main";
	}
	
	
	/**
	 * 添加系统菜单
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/saveMenu")
	public String saveMenu(BossMenu bossMenu,HttpServletRequest request,HttpServletResponse response ,
			@RequestParam("file") MultipartFile file) throws IOException{
		
		String path = "";
		  if (!file.isEmpty()) {
			  File newFile = null;
            String fileName = file.getOriginalFilename();
            byte[] fileBytes = file.getBytes();
            //获取文件后缀
            String[] suffixs = fileName.split("\\.");
            String suffix = "." + suffixs[suffixs.length - 1];
            
            String folder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] folders = folder.split("-");
            String year = folders[0];
            String month = folders[1];
            String day = folders[2];
            String folderYearPath = "";
            folderYearPath = GlobalConstant.MENU_PATH;
            //文件开始上传到服务器
            //判断文件路径是否存在，如果不存在就创建
            if (!"".equals(folderYearPath)) {
                String realYearPath = request.getSession().getServletContext().getRealPath(folderYearPath);
                File fileYearPath = new File(realYearPath);
                if (!fileYearPath.exists()) {
                    fileYearPath.mkdirs();
                }
                //判断文件是否存在，如果不存在就创建新文件
                Calendar cal = Calendar.getInstance();// 使用日历类
                String imgName=fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
				  String s = imgName.substring(imgName.lastIndexOf("."),imgName.length());
                
                path = folderYearPath +  "/"+ fileName;
                newFile = new File(realYearPath + "/"+ fileName);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                //上传文件
                FileCopyUtils.copy(fileBytes, newFile);
            }
		  }
		
		
		
		if(bossMenu != null && !"".equals(bossMenu)){
			if(bossMenu.getMenuId() != null && !"".equals(bossMenu.getMenuId())){
				//说明是修改
				if(bossMenu.getParentId().equals("0") || bossMenu.getParentId().equals("")){
					bossMenu.setParentId("0");
				}
				bossMenu.setMenuIcon(path);//图片di
				bossMenuService.updateBossMenu(bossMenu);
			}else{
				//说明是新增
				bossMenu.setMenuId(UUIDGenerator.getUUID());
				if(bossMenu.getParentId().equals("") || bossMenu.getParentId() == null){
					bossMenu.setParentId("0");
					List<BossMenu> list = bossMenuService.queryAllBossMenu();
					bossMenu.setMenuSort(list.size() + 1);
					bossMenu.setMenuIcon(path);//图片di
				}
				bossMenuService.addBossMenu(bossMenu);
			}
		}
		if(bossMenu.getParentId() != null && !bossMenu.getParentId().equals("0") && !"".equals(bossMenu.getParentId())){
			return  "redirect:/web/bossMenu/queryNextMenu?parentId="+bossMenu.getParentId();
		}else{
			return  "redirect:/web/bossMenu/goBossMenuList";
		}
	}
	
	
	/**
	 * 删除系统菜单
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/deleteMenu")
	public String deleteMenu(@RequestParam String menuId){
		if(menuId != null && !"".equals(menuId)){
			BossMenu bossMenu = bossMenuService.queryByMenuId(menuId);
			bossMenu.setIsDeleted(1);		//设置为不可见
			bossMenuService.updateBossMenu(bossMenu);
		}
		BossMenu bossMenu = bossMenuService.queryByMenuId(menuId);
		if(bossMenu.getParentId() != null && !bossMenu.getParentId().equals("0")){
			return  "redirect:/web/bossMenu/queryNextMenu?parentId="+menuId;
		}else{
			return  "redirect:/web/bossMenu/goBossMenuList";
		}
		
	}
	
	
	/**
	 * 查询此菜单是否有重复菜单
	 * @param menuName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/check")
	@ResponseBody
	public Map<String,String> checkClassesByClassName(@RequestParam String menuName,@RequestParam String menuId) throws UnsupportedEncodingException{
		
		List<BossMenu> menuList = new ArrayList<BossMenu>();
		Map<String, String> map = new HashMap<String, String>();  
		if((menuName != null && !"".equals(menuName))){
			
			menuList = bossMenuService.checkBossMenuByMenuName(menuName);
			if(menuList.size() > 0){
				if(menuId != null && !"".equals(menuId)){
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
	 * 根据 menuId 查询菜单
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public BossMenu update(@RequestParam String menuId){
		BossMenu bossMenu = null;
		if(menuId != null && !"".equals(menuId)){
			bossMenu = bossMenuService.queryByMenuId(menuId);
			String parentId = bossMenu.getParentId();
			if(parentId != null && !"".equals(parentId) && !parentId.equals("0")){
				BossMenu bo = bossMenuService.queryByMenuId(parentId);
				bossMenu.setParentName(bo.getMenuName());
			}
			
		}
		return bossMenu;
	}
	
	
	/**
	 * 根据 菜单名称 生成菜单的简写
	 * @param menuId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/getMenuCode")
	@ResponseBody
	public String getMenuCode (@RequestParam String menuName) throws UnsupportedEncodingException{
		String menuCode = "";
		menuName = URLDecoder.decode(menuName,"UTF-8");
		if(menuName != null && !"".equals(menuName)){
			menuCode = PinyinUtil.getPYIndexStr(menuName, false);
		}
		return menuCode;
	} 
	
	

	/**
	 * 查询此菜单简写   是否有重复简写
	 * @param menuName
	 * @return
	 * @throws 
	 */
	@RequestMapping("/checkMenuCode")
	@ResponseBody
	public Map<String,String> checkMenuCode(@RequestParam String menuCode,@RequestParam String menuId) throws UnsupportedEncodingException{
		
		menuCode= new String( menuCode.getBytes("iso-8859-1"), "UTF-8");
		List<BossMenu> menuList = new ArrayList<BossMenu>();
		Map<String, String> map = new HashMap<String, String>();  
		if((menuCode != null && !"".equals(menuCode))){
			
			menuList = bossMenuService.checkBossMenuByMenuCode(menuCode);
			if(menuList.size() > 0){
				if(menuId != null && !"".equals(menuId)){
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
	
	
}
