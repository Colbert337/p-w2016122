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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Controller
@SessionAttributes({"currUser","systemId","userId","menuCode","menuIndex"})
@RequestMapping("/crmBaseService")
public class CRMBaseContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

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
}
