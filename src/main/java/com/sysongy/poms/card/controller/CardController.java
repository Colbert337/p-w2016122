package com.sysongy.poms.card.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.doc.model.BossDoc;
import com.sysongy.poms.doc.service.BossDocService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;

/**
 * @FileName     :  BossDocController.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.doc.controller
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月16日, 下午4:06:47
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */

@RequestMapping("/web/card")
@Controller
public class CardController extends BaseContoller{

	@Autowired
	private BossDocService bossDocService;
	
	/**
	 * 查询所以系统文件夹
	 * @return
	 */
	@RequestMapping("/cardList")
	public String queryAllCardList(ModelMap map){
		List<BossDoc> bossList = new ArrayList<BossDoc>();
		bossList = bossDocService.queryAllBossDoc();
		map.addAttribute("bossList", bossList);
	    map.addAttribute("current_module", "webpage/poms/card/card_list");
	    return "common/g_main";
	}
	
	
	/**
	 * 查询所有系统文档  （树形结构）
	 * @return
	 */
	@RequestMapping("/queryBossDocTree")
	@ResponseBody
	//根据systemId查询学校信息  用于树的展示
	public Map<String, Object> queryBossDocTree(ModelMap map){
		Map<String, Object> treeMap = new HashMap<String, Object>(); 
		JSONArray array = new JSONArray();
		List<BossDoc> bosslist = new ArrayList<BossDoc>();
		bosslist = bossDocService.queryAllBossDoc();
		map.addAttribute("rootId", 1);
		//设置根节点
		JSONObject rootjson = new JSONObject();
		rootjson.put("rootId", 1);
		rootjson.put("name","所有文档");
		rootjson.put("open", true);
		JSONArray bossArray = new JSONArray();
		
		if(bosslist != null && bosslist.size() > 0){
			for (BossDoc bossDoc : bosslist) {
				JSONObject bossjson = new JSONObject();
				String bossDocId = bossDoc.getBossDocId();	//获取文档id
				String bossDocName = bossDoc.getDocumentName();
				bossjson.put("pId", bossDocId);
				bossjson.put("name", bossDocName);
				bossArray.add(bossjson);
				
				String parentId = bossDoc.getBossDocId();
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
		
		List<BossDoc> bossDocList = bossDocService.queryBossDocByParentId(parentId);	//根据id 查找
		JSONArray menuArray = new JSONArray();//年级 数组
		if(bossDocList != null && bossDocList.size() >0){
			for (BossDoc boss : bossDocList) {
				JSONObject bossjson = new JSONObject();
				bossjson.put("id", boss.getBossDocId());
				bossjson.put("name", boss.getDocumentName());
				menuArray.add(bossjson);
				String docId = boss.getBossDocId();
				if(!"".equals(docId) && docId != null && !docId.equals("0")){
					List<BossDoc> bossDocList2 = bossDocService.queryBossDocByParentId(docId);	//根据id 查找
					if(bossDocList2 != null && bossDocList2.size() >0){
						JSONArray menuArray2 = new JSONArray();//年级 数组
						for (BossDoc boss2 : bossDocList2) {
							
							JSONObject bossjson2 = new JSONObject();
							bossjson2.put("id", boss2.getBossDocId());
							bossjson2.put("name", boss2.getDocumentName());
							menuArray2.add(bossjson2);
							bossjson.put("children", menuArray2);
							
							String docId2 = boss2.getBossDocId();
							if(!"".equals(docId2) && docId2 != null && !docId2.equals("0")){
								List<BossDoc> bossDocList3 = bossDocService.queryBossDocByParentId(docId2);	//根据id 查找
								if(bossDocList3 != null && bossDocList3.size() >0){
									JSONArray menuArray3 = new JSONArray();
									for (BossDoc boss3 : bossDocList3) {
										
										JSONObject bossjson3 = new JSONObject();
										bossjson3.put("id", boss3.getBossDocId());
										bossjson3.put("name", boss3.getDocumentName());
										menuArray3.add(bossjson3);
										bossjson2.put("children", menuArray3);
										
										String docId3 = boss3.getBossDocId();
										if(!"".equals(docId3) && docId3 != null && !docId3.equals("0")){
											List<BossDoc> bossDocList4 = bossDocService.queryBossDocByParentId(docId3);	//根据id 查找
											if(bossDocList4 != null && bossDocList4.size() >0){
												JSONArray menuArray4 = new JSONArray();//年级 数组
												for (BossDoc boss4 : bossDocList4) {
													
													JSONObject bossjson4 = new JSONObject();
													bossjson4.put("id", boss4.getBossDocId());
													bossjson4.put("name", boss4.getDocumentName());
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
	 * 获取某个文档下面的文档
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryNextDoc")
	public String queryNextDoc(ModelMap map,@RequestParam String parentId){
		
		List<BossDoc> bosslist = new ArrayList<BossDoc>();
		if(parentId != null && !"".equals(parentId)){
			bosslist = bossDocService.queryBossDocByParentId(parentId);
		}
		map.addAttribute("bossList", bosslist);
		map.addAttribute("current_module", "webpage/doc/boss_doc_list");
		map.addAttribute("menuId", parentId);
        return "common/g_main";
	}
	
	
	/**
	 * 查询此文件夹是否有重复文件夹
	 * @param menuName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/check")
	@ResponseBody
	public Map<String,String> checkDocumentByDocumentName(@RequestParam String documentName,@RequestParam String bossDocId) throws UnsupportedEncodingException{
		
		documentName= new String( documentName.getBytes("iso-8859-1"), "UTF-8");
		List<BossDoc> docList = new ArrayList<BossDoc>();
		Map<String, String> map = new HashMap<String, String>();  
		if((documentName != null && !"".equals(documentName))){
			
			docList = bossDocService.checkBossDocBydocumentName(documentName);
			if(docList.size() > 0 && docList != null){
				if(bossDocId != null && !"".equals(bossDocId)){
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
	 * 保存文件夹信息
	 * @param bossDoc
	 * @return
	 */
	@RequestMapping("/saveFolder")
	public String saveFolder(BossDoc bossDoc){
		if(bossDoc != null && !"".equals(bossDoc)){
			if(bossDoc.getBossDocId() != null && !"".equals(bossDoc.getBossDocId())){
				//修改
				bossDocService.updateBossDoc(bossDoc);
			}else{
				//新增
				bossDoc.setBossDocId(UUIDGenerator.getUUID());
				if(bossDoc.getParentId().equals("")){
					bossDoc.setParentId(null);
				}
				bossDoc.setDocumentType(1);   //设置为文件夹
				bossDocService.addBossDoc(bossDoc);
			}
		}
		
		if(bossDoc.getParentId() != null && !bossDoc.getParentId().equals("0") && !"".equals(bossDoc.getParentId())){
			return  "redirect:/web/bossDoc/queryNextDoc?parentId="+bossDoc.getParentId();
		}else{
			return  "redirect:/web/bossDoc/queryAllBossDoc";
		}
	}
	
	
	/**
	 * 上传文档、文件
	 * @param systemId
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	/*@RequestMapping("/uploadBossDoc")
	public String uploadBossDoc(HttpServletRequest request,HttpServletResponse response ,
			@RequestParam("file") MultipartFile[] files) throws IOException{
			//获取参数   参数有 schoolId   gradeId   classId
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		String parentId=request.getParameter("parentId");
		MultipartFile file = null;
		if(files!=null && files.length>0){  
            //循环获取file数组中得文件  
            for(int i = 0;i<files.length;i++){  
                file = files[i];  
                //保存文件  
            }  
        }  
		String path = "";
		String suffix = ""; //文件后缀
		String fileName ="";//文件名
//		  if (!file.isEmpty()) {
			  if (path != null) {
			  File newFile = null;
            fileName = file.getOriginalFilename();
            byte[] fileBytes = file.getBytes();
            //获取文件后缀
            String[] suffixs = fileName.split("\\.");
            suffix = "." + suffixs[suffixs.length - 1];
            
            String folder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] folders = folder.split("-");
            String year = folders[0];
            String month = folders[1];
            String day = folders[2];
            String folderYearPath = "";
            folderYearPath = GlobalConstant.DOCUMENT_SYS_PATH + year;
            //文件开始上传到服务器
            //判断文件路径是否存在，如果不存在就创建
            if (!"".equals(folderYearPath)) {
                String realYearPath = request.getSession().getServletContext().getRealPath(folderYearPath);
                File fileYearPath = new File(realYearPath);
                if (!fileYearPath.exists()) {
                    fileYearPath.mkdirs();
                }
                String realMonthPath = request.getSession().getServletContext().getRealPath(folderYearPath + "/" + month);
                File fileMonthPath = new File(realMonthPath);
                if (!fileMonthPath.exists()) {
                    fileMonthPath.mkdirs();
                }
                String realDayPath = request.getSession().getServletContext().getRealPath(folderYearPath + "/" + month + "/" + day);
                File fileDayPath = new File(realDayPath);
                if (!fileDayPath.exists()) {
                    fileDayPath.mkdirs();
                }
                //判断文件是否存在，如果不存在就创建新文件
                Calendar cal = Calendar.getInstance();// 使用日历类
                String imgName=fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
				  String s = imgName.substring(imgName.lastIndexOf("."),imgName.length());
                
                path = folderYearPath + "/"+ month + "/"+ day + "/"+ cal.getTime().getTime()+s;
                newFile = new File(realDayPath + "/"+ cal.getTime().getTime()+s);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                //上传文件
                FileCopyUtils.copy(fileBytes, file);
                	
                //上传转换文件格式(暂时不用转换)
//                DocCoverterUtil docCoverterUtil = new DocCoverterUtil();
//                docCoverterUtil.DocConverter(realDayPath + "/"+ cal.getTime().getTime()+s);
//                docCoverterUtil.conver();
            }
		  }
		BossDoc bossDoc = new BossDoc();
		bossDoc.setBossDocId(UUIDGenerator.getUUID());
		bossDoc.setFilePath(path);
		bossDoc.setDocumentName(fileName);
		if(parentId != null && !"".equals(parentId)){
			bossDoc.setParentId(parentId);
		}
		if(suffix.equals(".png") || suffix.equals(".gif") || suffix.equals(".bmp") ||suffix.equals(".jpeg") || 
				suffix.equals(".jpg") || suffix.equals(".PNG") || suffix.equals(".GIF") || 
				suffix.equals(".BMP") ||suffix.equals(".JPEG") || suffix.equals(".JPG")){
			bossDoc.setDocumentType(4);
		}else if(suffix.equals(".xlsx") || suffix.equals(".xls") || suffix.equals(".XLSX") || suffix.equals(".XLS")){
			bossDoc.setDocumentType(3);
		}else if(suffix.equals(".pdf") || suffix.equals(".PDF")){
			bossDoc.setDocumentType(5);
		}else if(suffix.equals(".txt") || suffix.equals(".TXT")){
			bossDoc.setDocumentType(6);
		}else if(suffix.equals(".doc") || suffix.equals(".docx") || suffix.equals(".DOC") || suffix.equals(".DOCX")){
			bossDoc.setDocumentType(2);
		}else if(suffix.equals(".ppt") || suffix.equals(".pptx") || suffix.equals(".PPT") || suffix.equals(".PPTX")){
			bossDoc.setDocumentType(7);
		}
		bossDocService.addBossDoc(bossDoc);
		//一下这个链接没有实际意义
		return  null;
	}*/
	
	@RequestMapping("/uploadBossDoc")
	public String uploadBossDoc(HttpServletRequest request,HttpServletResponse response ,
			@RequestParam(value = "file") MultipartFile file ) throws IOException{
			//获取参数   参数有 schoolId   gradeId   classId
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		String parentId=request.getParameter("parentId");
		
		String path = "";
		String suffix = ""; //文件后缀
		String fileName ="";//文件名
		  if (!file.isEmpty()) {
			  File newFile = null;
            fileName = file.getOriginalFilename();
            byte[] fileBytes = file.getBytes();
            //获取文件后缀
            String[] suffixs = fileName.split("\\.");
            suffix = "." + suffixs[suffixs.length - 1];
            
            String folder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] folders = folder.split("-");
            String year = folders[0];
            String month = folders[1];
            String day = folders[2];
            String folderYearPath = "";
            folderYearPath = GlobalConstant.DOCUMENT_SYS_PATH + year;
            //文件开始上传到服务器
            //判断文件路径是否存在，如果不存在就创建
            if (!"".equals(folderYearPath)) {
                String realYearPath = request.getSession().getServletContext().getRealPath(folderYearPath);
                File fileYearPath = new File(realYearPath);
                if (!fileYearPath.exists()) {
                    fileYearPath.mkdirs();
                }
                String realMonthPath = request.getSession().getServletContext().getRealPath(folderYearPath + "/" + month);
                File fileMonthPath = new File(realMonthPath);
                if (!fileMonthPath.exists()) {
                    fileMonthPath.mkdirs();
                }
                String realDayPath = request.getSession().getServletContext().getRealPath(folderYearPath + "/" + month + "/" + day);
                File fileDayPath = new File(realDayPath);
                if (!fileDayPath.exists()) {
                    fileDayPath.mkdirs();
                }
                //判断文件是否存在，如果不存在就创建新文件
                Calendar cal = Calendar.getInstance();// 使用日历类
                String imgName=fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
                String docPrefix = imgName.substring(0,imgName.lastIndexOf("."));
                String docSuffix = imgName.substring(imgName.lastIndexOf("."),imgName.length());
                
                path = folderYearPath + "/"+ month + "/"+ day + "/"+ cal.getTime().getTime()+docSuffix;
                newFile = new File(realDayPath + "/"+ cal.getTime().getTime()+docSuffix);
                /*if (!newFile.exists()) {
                    newFile.createNewFile();
                }*/
                //上传文件
                FileCopyUtils.copy(fileBytes, newFile);
                	
                //上传转换文件格式(暂时不用转换)
//                DocCoverterUtil docCoverterUtil = new DocCoverterUtil();
//                docCoverterUtil.DocConverter(realDayPath + "/"+ cal.getTime().getTime()+s);
//                docCoverterUtil.conver();
            }
		  }
		BossDoc bossDoc = new BossDoc();
		bossDoc.setBossDocId(UUIDGenerator.getUUID());
		bossDoc.setFilePath(path);
		bossDoc.setDocumentName(fileName);
		if(parentId != null && !"".equals(parentId)){
			bossDoc.setParentId(parentId);
		}
		if(suffix.equals(".png") || suffix.equals(".gif") || suffix.equals(".bmp") ||suffix.equals(".jpeg") || 
				suffix.equals(".jpg") || suffix.equals(".PNG") || suffix.equals(".GIF") || 
				suffix.equals(".BMP") ||suffix.equals(".JPEG") || suffix.equals(".JPG")){
			bossDoc.setDocumentType(4);
		}else if(suffix.equals(".xlsx") || suffix.equals(".xls") || suffix.equals(".XLSX") || suffix.equals(".XLS")){
			bossDoc.setDocumentType(3);
		}else if(suffix.equals(".pdf") || suffix.equals(".PDF")){
			bossDoc.setDocumentType(5);
		}else if(suffix.equals(".txt") || suffix.equals(".TXT")){
			bossDoc.setDocumentType(6);
		}else if(suffix.equals(".doc") || suffix.equals(".docx") || suffix.equals(".DOC") || suffix.equals(".DOCX")){
			bossDoc.setDocumentType(2);
		}else if(suffix.equals(".ppt") || suffix.equals(".pptx") || suffix.equals(".PPT") || suffix.equals(".PPTX")){
			bossDoc.setDocumentType(7);
		}
		bossDocService.addBossDoc(bossDoc);
		//一下这个链接没有实际意义
		return  null;
	}
	/**
	 * 删除文件
	 * @param bossDocId
	 * @return
	 */
	@RequestMapping("/deleteBossDoc")
	public String deleteBossDoc(@RequestParam String bossDocId){
		if(bossDocId != null && !"".equals(bossDocId)){
			List<BossDoc> list = new ArrayList<BossDoc>();
			list = bossDocService.queryBossDocByParentId(bossDocId);//查看下面有没有子集
			if(list != null && list.size() >0){
				for (BossDoc bossDoc : list) {
					deleteBossDoc(bossDoc.getBossDocId());
				}
			}
			bossDocService.deleteByBossDocId(bossDocId);
		}
		
		return  "redirect:/web/bossDoc/queryAllBossDoc";
		
	}
	
	
	/**
	 * 修改文件夹  或者 文档  名称
	 * @param bossDocId
	 * @param newDocumentName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/reDocumentNameBossDoc")
	@ResponseBody
	public String reDocumentNameBossDoc(@RequestParam String bossDocId,@RequestParam String newDocumentName) throws UnsupportedEncodingException{
		newDocumentName= new String( newDocumentName.getBytes("iso-8859-1"), "UTF-8");
		BossDoc bossDoc = bossDocService.queryByBossDocId(bossDocId);
		bossDoc.setDocumentName(newDocumentName);
		bossDocService.updateBossDoc(bossDoc);
		String parentId = bossDoc.getParentId();
		return parentId;
	}
}
