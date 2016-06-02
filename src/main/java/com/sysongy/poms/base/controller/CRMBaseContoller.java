package com.sysongy.poms.base.controller;

import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Properties;

/**
 * @FileName     :  BaseContoller.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.base.controller
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年10月20日, 下午5:53:21
 * @Author       :  Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Controller
@SessionAttributes({"currUser","systemId","userId","menuCode","menuIndex"})
@RequestMapping("/crmService")
public class CRMBaseContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @RequestMapping(value = {"","/","/test"})  
    public String index(ModelMap map){  
    	/*ModelAndView result = new ModelAndView();
    	String pmcName = "登录测试成功！";
    	result.addObject("pmcName", pmcName);
    	result.addObject("current_module", "webpage/test");
        result.setViewName("comm/g_main");*/
        
        map.addAttribute("current_module", "webpage/demo/panel");
        
        return "login";
    }

    @RequestMapping(value = {"main"})
    public String main(ModelMap map){
    	/*ModelAndView result = new ModelAndView();
    	String pmcName = "登录测试成功！";
    	result.addObject("pmcName", pmcName);
    	result.addObject("current_module", "webpage/test");
        result.setViewName("comm/g_main");*/

        map.addAttribute("current_module", "webpage/demo/panel");

        return "common/g_main";
    }
    @RequestMapping(value = {"/web/login"})
    @ResponseBody
    public AjaxJson login( HttpServletRequest request,HttpServletResponse response,ModelMap map){
        AjaxJson ajaxJson = new AjaxJson();
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

    /**
     * 退出登录
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = {"/web/loginOut"})  
    public String loginOut( HttpServletRequest request,HttpServletResponse response, SessionStatus sessionStatus, ModelMap map ){  
    	sessionStatus.setComplete();
    	return "redirect:/";
    }

    
}
