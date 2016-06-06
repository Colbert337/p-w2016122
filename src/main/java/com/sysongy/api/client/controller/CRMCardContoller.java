package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Controller
@SessionAttributes({"currUser","systemId","userId","menuCode","menuIndex"})
@RequestMapping("/crmCardService")
public class CRMCardContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    private GasCardService service;

    @RequestMapping(value = {"/web/queryCardInfo"})
    @ResponseBody
    public PageInfo<GasCard> queryCardInfo(HttpServletRequest request, HttpServletResponse response, ModelMap map, GasCard gascard){
        AjaxJson ajaxJson = new AjaxJson();
        PageInfo<GasCard> pageinfo = null;
        Map<String, Object> attributes = new HashMap<String, Object>();
        PageBean bean = new PageBean();
        try
        {
            if(gascard.getPageNum() == null){
                gascard.setPageNum(1);
                gascard.setPageSize(10);
            }
            if(!StringUtils.isEmpty(gascard.getStorage_time_range())){
                String []tmpRange = gascard.getStorage_time_range().split("-");
                if(tmpRange.length == 2){
                    gascard.setStorage_time_after(tmpRange[0].trim()+" 00:00:00");
                    gascard.setStorage_time_before(tmpRange[1]+" 23:59:59");
                }
            }
            pageinfo = service.queryGasCard(gascard);
            map.addAttribute("ret", bean);
            map.addAttribute("pageInfo", pageinfo);
            map.addAttribute("gascard",gascard);
            map.addAttribute("current_module", "webpage/poms/card/card_list");
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CARD_ERROR);
            logger.error("queryCardInfo error： " + e);
        }
        ajaxJson.setAttributes(map);
    	return pageinfo;
    }

    //单文件上传
    @RequestMapping(value = "/web/upload")
    @ResponseBody
    public AjaxJson queryFileData(@RequestParam("uploadfile") CommonsMultipartFile file, HttpServletRequest request){
        AjaxJson ajaxJson = new AjaxJson();
        //MultipartFile是对当前上传的文件的封装，当要同时上传多个文件时，可以给定多个MultipartFile参数(数组)
        if (file.isEmpty()) {
            ajaxJson.setMsg("上传文件为空！！！");
            ajaxJson.setSuccess(false);
            return ajaxJson;
        }
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
        String path = request.getSession().getServletContext().getRealPath("/upload/" + filename);// 存放位置
        File destFile = new File(path);
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            String type = files[i].getOriginalFilename().substring(
                    files[i].getOriginalFilename().indexOf("."));// 取文件格式后缀名
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = request.getSession().getServletContext()
                    .getRealPath("/upload/" + filename);// 存放位置
            File destFile = new File(path);
            try {
                FileUtils.copyInputStreamToFile(files[i].getInputStream(),
                        destFile);// 复制临时文件到指定目录下
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ajaxJson;
    }
}
