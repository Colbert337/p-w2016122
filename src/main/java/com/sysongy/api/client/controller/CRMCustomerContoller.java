package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes({"currUser","systemId","userId","menuCode","menuIndex"})
@RequestMapping("/crmUserService")
public class CRMCustomerContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DriverService driverService;

    @RequestMapping(value = {"/web/queryCustomerInfo"})
    @ResponseBody
    public AjaxJson queryCustomerInfo(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            PageInfo<SysDriver> drivers = driverService.queryDrivers(sysDriver);
            attributes.put("PageInfo", drivers);
            attributes.put("drivers", drivers.getList());
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_USER_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
        ajaxJson.setAttributes(attributes);
    	return ajaxJson;
    }

    @RequestMapping(value = {"/web/sendMsg"})
    @ResponseBody
    public AjaxJson sendMsg(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver){
        AjaxJson ajaxJson = new AjaxJson();
        try
        {
            PageInfo<SysDriver> drivers = driverService.queryDrivers(sysDriver);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_SEND_MSG_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/addCustomer"})
    @ResponseBody
    public AjaxJson addCustomer(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver){
        AjaxJson ajaxJson = new AjaxJson();
        try
        {
            PageInfo<SysDriver> drivers = driverService.queryDrivers(sysDriver);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_USER_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

}
