package com.sysongy.poms.card.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.doc.model.BossDoc;
import com.sysongy.poms.doc.service.BossDocService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;


@RequestMapping("/web/card")
@Controller
public class CardController extends BaseContoller{

	@Autowired
	private GasCardService service;
	@Autowired
	private BossDocService bossDocService;
	
	/**
	 * 表单查询
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cardList")
	public String queryAllCardList(ModelMap map, GasCard gascard) throws Exception{
		
		if(gascard.getPageNum() == null){
			gascard.setPageNum(1);
			gascard.setPageSize(10);
		}
		if(gascard.getOrderby() == null){
			//gascard.setOrderby("card_id asc");
		}
		if(!StringUtils.isEmpty(gascard.getRelease_time_range())){
			String []tmpRange = gascard.getRelease_time_range().split("-");
			if(tmpRange.length==2){
				gascard.setRelease_time_after(tmpRange[0].trim());
				gascard.setRelease_time_before(tmpRange[1]);
			}
		}
		
		PageInfo<GasCard> pageinfo = service.queryGasCard(gascard);
		List<GasCard> list = pageinfo.getList();
		map.addAttribute("pageinfo", pageinfo);
		map.addAttribute("gascard",gascard);
	    map.addAttribute("current_module", "page/card/card_list");
	    return "page/card/card_list";
	}

	@RequestMapping("/saveCard")
	public String saveCard(ModelMap map, GasCard gascard) throws Exception{
		
		Integer ret = service.saveGasCard(gascard);
		map.addAttribute("ret", ret);
		
		return  "page/card/new_card";
	}
	
	@RequestMapping("/deleteCard")
	public String deleteCard(ModelMap map, @RequestParam String cardid){
		
		PageBean bean = new PageBean();
		String ret = "page/card/card_list";
		Integer rowcount = null;
		
		try {
				if(cardid != null && !"".equals(cardid)){
					rowcount = service.delGasCard(cardid);
				}
				
				ret = this.queryAllCardList(map, new GasCard());
				
				bean.setRetCode(100);
				bean.setRetMsg("["+cardid+"]删除成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);
				
				map.addAttribute("ret", JSON.toJSONString(bean));
				
				
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			
			ret = this.queryAllCardList(map, new GasCard());
			
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
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
