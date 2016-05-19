package com.sysongy.poms.health.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.health.model.Health;
import com.sysongy.poms.health.model.Healthsub;
import com.sysongy.poms.health.service.HealthService;
import com.sysongy.poms.health.service.HealthsubService;
import com.sysongy.util.UUIDGenerator;
import com.sysongy.util.XTree;
import com.sysongy.util.XTreeNode;
/**
 * 
 * @FileName     :  HealthController.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.physique.controller
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年1月5日, 下午5:25:24
 * @Author       :  Maloo
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@RequestMapping("/web/health")
@Controller
public class HealthController extends BaseContoller{
	@Autowired
	private  HttpServletRequest request;
	@Autowired
	private  HttpServletResponse response;
	@Autowired
	private HealthService healthService ;
	@Autowired
	private HealthsubService healthsubService; 
	
	/**
	 * 展开保健知识库
	 * @param currUser
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("showcategory")
	public String showcategory(@RequestParam(required=false) String categoryId,ModelMap map){
		/*在跳转页面之前，检查数据库表中是否有根节点  ID为  32个1 ，如果没有，则创建该根节点*/
		List<Health> hlist = new ArrayList<Health>();
		String rootid = "11111111111111111111111111111111" ; //根节点的ID与父ID
		hlist = healthService.queryROOTlist(rootid);
		if(hlist.isEmpty()){
			Health health = new Health();
			health.setCategoryId(rootid);
			health.setPcategoryId(rootid);
			health.setCategoryName("分类");
			health.setListorder(0);
			healthService.addHealth(health);
		}
		request.setAttribute("categoryId", categoryId);
		map.addAttribute("current_module", "health/category_show");
		return "common/g_main";
	}
	
	/**
	 * 创建保健知识分类
	 * @param currUser
	 * @param pcategoryId
	 * @param categoryName
	 * @param sm
	 * @param wh
	 * @param listorder
	 * @return
	 */
	@RequestMapping("saveHealth")
	@ResponseBody
	public String saveHealth(
			@RequestParam String pcategoryId,
			@RequestParam String categoryName,
			@RequestParam String sm,
			@RequestParam String wh,@RequestParam(required=false) String listorder){
		Health health = new Health();
		health.setCategoryId(UUIDGenerator.getUUID());
		health.setPcategoryId(pcategoryId);
		health.setCategoryName(categoryName);
		health.setSm(sm);
		health.setWh(wh);
		health.setListorder(Integer.parseInt(listorder));
		health.setCreateDate(new Date());
		
		healthService.addHealth(health);
		
		return "" ;
	}
	
	/**
	 * 更新保健知识分类
	 * @param currUser
	 * @param categoryId
	 * @param pcategoryId
	 * @param categoryName
	 * @param sm
	 * @param wh
	 * @param listorder
	 * @return
	 */
	@RequestMapping("updateHealth")
	@ResponseBody
	public String updateHealth(
			@RequestParam String categoryId,
			@RequestParam String pcategoryId,
			@RequestParam String categoryName,
			@RequestParam String sm,
			@RequestParam String wh,@RequestParam(required=false) String listorder){
		Health health = healthService.queryHealthByCategoryId(categoryId);
		health.setPcategoryId(pcategoryId);
		health.setCategoryName(categoryName);
		health.setSm(sm);
		health.setWh(wh);
		health.setListorder(Integer.parseInt(listorder));
		health.setModifyDate(new Date());
		healthService.updateHealth(health);
		request.setAttribute("categoryId", categoryId);
		return categoryId ;
		
		//return  "redirect:/web/health/showcategory";
	}
	/**
	 * 删除分类之前，检查该分类下是否还有孩子节点
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("checkHasChild")
	@ResponseBody
	public String checkHasChild(@RequestParam String categoryId){
		String val = "0" ;
		List<Health> hlist = new ArrayList<Health>();
		hlist = healthService.queryHealthListByPcategoryId(categoryId);
		if(!hlist.isEmpty()){
			if(hlist.size() >= 1){
				val = "1" ;
			}
		}else{//删除该条分类以及子表记录
			healthService.deletehealthByCategoryId(categoryId);
			healthsubService.deleteSubByPcategoryId(categoryId);
			val = "2" ;
		}
		return val ;
	}
	
	/**
	 * 打开分类信息
	 * @param currUser
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("openHealth")
	public String openHealth(@RequestParam(required=false) String categoryId,ModelMap map){
		Health health = new Health();
		List<Healthsub> hslist = new ArrayList<Healthsub>();
		if(!"".equals(categoryId) && !"null".equals(categoryId) && categoryId != null){//默认展开一个
			
		}else{
			categoryId = "11111111111111111111111111111111" ;
		}
		health = healthService.queryHealthByCategoryId(categoryId);
		
		hslist = healthsubService.querySubListByCategoryId(categoryId);
		
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("health", health);
		request.setAttribute("hslist", hslist);
		
		//map.addAttribute("current_module", "health/categoryinfo");
		return "health/categoryinfo" ;
	}
	
	/**
	 * 加载保健分类树
	 * @param currUser
	 * @param rootid
	 * @return
	 */
	@RequestMapping("loadCategoryTree")
	@ResponseBody
	public String loadCategoryTree(@RequestParam(required=false) String rootid,ModelMap map){
		PrintWriter out = null;
		try {
			response.setContentType("text/html; charset=UTF-8");
			String id = request.getParameter("id");
			out = response.getWriter();
			XTree xtree = new XTree();
//			String categoryId = request.getParameter("categoryId");
			Iterator it = null;
			if (id != null && !id.equals("-1")) {// 生成下级子节点
				xtree.setId(id);
				id = id.split(":")[1];
				List<Health> hlist = new ArrayList<Health>();
				hlist = healthService.queryHealthListByPcategoryId(id);
				it = hlist.iterator();
				while (it.hasNext()) {
					Health health = (Health)it.next();
					XTreeNode treeNode = new XTreeNode();// 定义一个树节点\
					treeNode.setId("Z:"+ health.getCategoryId());
					treeNode.setText(health.getCategoryName());
					if(healthService.queryHealthListByPcategoryId(health.getCategoryId()).isEmpty()){
						treeNode.setHaschild(false);
					}else{
						treeNode.setHaschild(true);
					}
					treeNode.setIm0("folderClosed.gif");
					treeNode.setIm1("folderOpen.gif");
					treeNode.setIm2("folderClosed.gif");
					xtree.addTreeNode(treeNode);
				}
			} else {

				xtree.setId("-1");// 设定树的根ID是-1，
				XTreeNode treeNode = new XTreeNode();// 定义一个树节点\
				Health health = healthService.queryHealthByCategoryId("11111111111111111111111111111111");
				
				treeNode.setId("R:"+health.getCategoryId());
				treeNode.setText(health.getCategoryName());// 设置节点的名称
				treeNode.setHaschild(true);
				treeNode.setOpen(true);
				
				treeNode.setIm0("RootIcon.gif");
				treeNode.setIm2("RootIcon.gif");
				treeNode.setIm1("RootIcon.gif");
				xtree.addTreeNode(treeNode);
			}
			
			System.out.println(xtree.toXML());
			out.print(xtree.toXML());
			out.flush();
			out.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception ex) {
					
				}
			}	
		}
		return null ;
	}
}
