package com.sysongy.api.client.controller;

import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/crmBaseService")
public class CRMBaseContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
	
	@Autowired
	private GastationService service;

    @RequestMapping(value = {"/web/login"})
    @ResponseBody
    public AjaxJson login(HttpServletRequest request,HttpServletResponse response, ModelMap map){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("123", "321");
        ajaxJson.setAttributes(attributes);
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if(userName.equalsIgnoreCase("test")){
            return ajaxJson;
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.ERROR_AUTHORITY);
        }
    	return ajaxJson;
    }

    @RequestMapping(value = {"/web/queryGasStationInfo"})
    @ResponseBody
    public AjaxJson queryGasStationInfo(HttpServletRequest request,HttpServletResponse response, ModelMap map){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("123", "321");
        ajaxJson.setAttributes(attributes);
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if(userName.equalsIgnoreCase("test")){
            return ajaxJson;
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.ERROR_AUTHORITY);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/queryCardInfo"})
    @ResponseBody
    public AjaxJson queryCardInfo(HttpServletRequest request,HttpServletResponse response, ModelMap map){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("123", "321");
        ajaxJson.setAttributes(attributes);
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if(userName.equalsIgnoreCase("test")){
            return ajaxJson;
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.ERROR_AUTHORITY);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/importCard"})
    @ResponseBody
    public AjaxJson importCard(HttpServletRequest request, HttpServletResponse response, ModelMap map){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("123", "321");
        ajaxJson.setAttributes(attributes);
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if(userName.equalsIgnoreCase("test")){
            return ajaxJson;
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.ERROR_AUTHORITY);
        }
        return ajaxJson;
    }

    //单文件上传
    @RequestMapping(value = "/web/upload")
    @ResponseBody
    public AjaxJson queryFileData(@RequestParam("uploadfile") CommonsMultipartFile file, HttpServletRequest request){
        AjaxJson ajaxJson = new AjaxJson();
        String gasstationid = request.getParameter("gasstationid");
        //MultipartFile是对当前上传的文件的封装，当要同时上传多个文件时，可以给定多个MultipartFile参数(数组)
        if (file.isEmpty()) {
            ajaxJson.setMsg("上传文件为空！！！");
            ajaxJson.setSuccess(false);
            return ajaxJson;
        }
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
//        String path = request.getSession().getServletContext().getRealPath("/upload/" + filename);// 存放位置
//        path = request.getContextPath()+"/upload/"+ filename;
        String path = (String) prop.get("images_upload_path");
        if(!StringUtils.isEmpty(gasstationid)){
        	path = path + "/"+gasstationid+"/";
        }
        path+= filename;
        File destFile = new File(path);
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ajaxJson.setObj(path);
        return ajaxJson;
    }

    //多文件上传
    @RequestMapping(value = "/web/uploads")
    @ResponseBody
    public AjaxJson queryFileDatas(@RequestParam("uploadfile") CommonsMultipartFile[] files, HttpServletRequest request) {
        AjaxJson ajaxJson = new AjaxJson();
        if(files == null){
            ajaxJson.setMsg("上传文件为空！！！");
            ajaxJson.setSuccess(false);
            return ajaxJson;
        }
        for (int i = 0; i < files.length; i++) {
            String type = files[i].getOriginalFilename().substring(files[i].getOriginalFilename().indexOf("."));// 取文件格式后缀名
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = request.getSession().getServletContext().getRealPath("/upload/" + filename);// 存放位置
            File destFile = new File(path);
            try {
                FileUtils.copyInputStreamToFile(files[i].getInputStream(),destFile);// 复制临时文件到指定目录下
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        ajaxJson.setObj(path);
        return ajaxJson;
    }
}
