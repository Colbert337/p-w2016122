package com.sysongy.poms.crm.controller;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sysongy.poms.mobile.dao.MbAppVersionMapper;
import com.sysongy.poms.mobile.model.MbAppVersion;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;

/**
 * @FileName: CrmPortalController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.crm.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月05日, 9:53
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@RequestMapping("/webpage/crm")
@Controller
public class CrmDownAppController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
    @Autowired
    MbAppVersionMapper mbAppVersionMapper;

	@RequestMapping("/download-app")
	public String download(ModelMap map) throws Exception {
		MbAppVersion mbAppVersion = mbAppVersionMapper.queryNewest();
		map.addAttribute("img", "images/down-app.png");
		map.addAttribute("version", mbAppVersion.getCode());//整数
		map.addAttribute("url", mbAppVersion.getUrl());
		return "/webpage/crm/download-app";
	}
	@RequestMapping("/webapp-download-app")
	public String webappDownloadApp(ModelMap map) throws Exception {
		MbAppVersion mbAppVersion = mbAppVersionMapper.queryNewest();
		map.addAttribute("img", "images/down-app.png");
		map.addAttribute("version", mbAppVersion.getCode());//整数
		map.addAttribute("url", mbAppVersion.getUrl());
		return "/webpage/crm/webapp-download-app";
	}
}
