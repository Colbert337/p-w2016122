package com.sysongy.api.client.controller;

import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

@Controller
@RequestMapping("/crmGasService")
public class GasStationContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @RequestMapping(value = {"","/","/test"})  
    public String index(ModelMap map){
        map.addAttribute("current_module", "webpage/demo/panel");
        return "login";
    }

    @RequestMapping(value = {"main"})
    public String main(ModelMap map){
        map.addAttribute("current_module", "webpage/demo/panel");
        return "common/g_main";
    }

    @RequestMapping(value = {"/web/login"})
    @ResponseBody
    public AjaxJson login( HttpServletRequest request,HttpServletResponse response, ModelMap map){
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
