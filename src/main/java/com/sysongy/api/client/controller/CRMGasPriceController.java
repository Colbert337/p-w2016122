package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.permi.model.SysUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/14.
 */

@Controller
@RequestMapping("/crmGasPriceService")
public class CRMGasPriceController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GsGasPriceService gsGasPriceService;

    @RequestMapping(value = {"/web/queryGsPriceInfo"})
    @ResponseBody
    public AjaxJson queryGsPriceInfo(HttpServletRequest request, HttpServletResponse response, GsGasPrice gsGasPrice) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            PageInfo<GsGasPrice> gsGasPrices = gsGasPriceService.queryGsPrice(gsGasPrice);
            attributes.put("gsGasPricePageInfo", gsGasPrices);
            attributes.put("gsGasPrices", gsGasPrices.getList());
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_GAS_PRICE_ERROR + e.getMessage());
            logger.error("queryGsPriceInfo error： " + e);
            e.printStackTrace();
        }
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/addGsGasPrice"})
    @ResponseBody
    public AjaxJson addGsGasPrice(HttpServletRequest request, HttpServletResponse response, GsGasPrice gsGasPrice) {
        AjaxJson ajaxJson = new AjaxJson();
        if(gsGasPrice == null){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("录入数据为空！！！");
            return ajaxJson;
        }
        try {
            int renum = gsGasPriceService.saveGsPrice(gsGasPrice, "insert");
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无用户添加！！！");
                return ajaxJson;
            }
        } catch (Exception e){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_ADD_GAS_PRICE_ERROR + e.getMessage());
            logger.error("addGsGasPrice error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/updateGsGasPrice"})
    @ResponseBody
    public AjaxJson updateGsGasPrice(HttpServletRequest request, HttpServletResponse response, GsGasPrice gsGasPrice) {
        AjaxJson ajaxJson = new AjaxJson();
        if(!StringUtils.isNotEmpty(gsGasPrice.getGsGasPriceId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("未输入要更新的数据！！！");
            return ajaxJson;
        }

        try {
            int renum = gsGasPriceService.saveGsPrice(gsGasPrice, "update");
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无气品更新 ！！！");
                return ajaxJson;
            }
        }catch (Exception e){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.UPDATE_CRM_GAS_PRICE_ERROR + e.getMessage());
            logger.error("update gsGasPrice error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/delGsGasPrice"})
    @ResponseBody
    public AjaxJson delGsGasPrice(HttpServletRequest request, HttpServletResponse response, GsGasPrice gsGasPrice){
        AjaxJson ajaxJson = new AjaxJson();
        if(!StringUtils.isNotEmpty(gsGasPrice.getGsGasPriceId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("未输入要删除的数据！！！");
            return ajaxJson;
        }
        try
        {
            int renum = gsGasPriceService.delGsPrice(gsGasPrice.getGsGasPriceId());
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无气品删除 ！！！");
                return ajaxJson;
            }
        }catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.DELETE_CRM_GAS_PRICE_ERROR + e.getMessage());
            logger.error("delGsGasPrice error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }
}