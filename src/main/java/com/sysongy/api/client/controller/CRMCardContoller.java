package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.api.client.controller.model.CRMCardUpdateInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/crmCardService")
public class CRMCardContoller {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    private GasCardService gasCardService;

    @Autowired
    DriverService driverService;

    @RequestMapping(value = {"/web/queryCardInfo"})
    @ResponseBody
    public AjaxJson queryCardInfo(HttpServletRequest request, HttpServletResponse response, GasCard gascard){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        if(StringUtils.isEmpty(gascard.getWorkstation())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("气站ID为空！！！");
            return ajaxJson;
        }
        try
        {
            PageInfo<GasCard> pageinfo = gasCardService.queryGasCard(gascard);
            if((pageinfo == null) || (pageinfo.getList().size() == 0)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("查询数据为空！！！");
                return ajaxJson;
            }
            attributes.put("pageInfo", pageinfo);
            attributes.put("gascard",pageinfo.getList());
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CARD_ERROR);
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
    	return ajaxJson;
    }

    @RequestMapping(value = {"/web/querySingleCardInfo"})
    @ResponseBody
    public AjaxJson querySingleCardInfo(HttpServletRequest request, HttpServletResponse response, GasCard gascard){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        if((gascard == null) || (StringUtils.isEmpty(gascard.getCard_no()))){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("卡号不能为空！！！");
            return ajaxJson;
        }
        try
        {
            PageInfo<GasCard> pageinfo = gasCardService.queryGasCard(gascard);
            if((pageinfo == null) || (pageinfo.getList().size() == 0)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("查询数据为空！！！");
                return ajaxJson;
            }
            attributes.put("pageInfo", pageinfo);
            attributes.put("gascard",pageinfo.getList());
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CARD_ERROR);
            logger.error("queryCardInfo error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/queryCardFor2StatusInfo"})
    @ResponseBody
    public AjaxJson queryCardFor2StatusInfo(HttpServletRequest request, HttpServletResponse response, GasCard gascard){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        if(StringUtils.isEmpty(gascard.getWorkstation())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("气站ID为空！！！");
            return ajaxJson;
        }
        try
        {
            String startTime = gascard.getStorage_time_before();
            String endTime = gascard.getStorage_time_after();
            gascard.setStorage_time_after(startTime);
            gascard.setStorage_time_before(endTime);
            PageInfo<GasCard> pageinfo = gasCardService.queryCardFor2StatusInfo(gascard);
            if((pageinfo == null) || (pageinfo.getList().size() == 0)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("查询数据为空！！！");
                return ajaxJson;
            }
            attributes.put("pageInfo", pageinfo);
            attributes.put("gascard",pageinfo.getList());
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CARD_ERROR);
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/distributeCard"})
    @ResponseBody
    public AjaxJson distributeCard(HttpServletRequest request, HttpServletResponse response, GasCard gascard){
        AjaxJson ajaxJson = new AjaxJson();
        if((gascard == null) || (!StringUtils.isNotEmpty(gascard.getCard_no()))){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("用户卡号为空！！！");
            return ajaxJson;
        }
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            GasCard gasCardNew = gasCardService.queryGasCardInfo(gascard.getCard_no());
            if(!gasCardNew.getWorkstation().equalsIgnoreCase(gascard.getWorkstation())){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该卡不属于当前气站，无法发放给司机！");
                return ajaxJson;
            }

            if(!gasCardNew.getCard_status().equalsIgnoreCase(InterfaceConstants.CARD_STSTUS_ALREADY_SEND)){
                ajaxJson.setSuccess(false);
                if(gasCardNew.getCard_status().equalsIgnoreCase(InterfaceConstants.CARD_STSTUS_IN_USE)){
                    ajaxJson.setMsg("当前卡已被别人使用！");
                }else if(gasCardNew.getCard_status().equalsIgnoreCase(InterfaceConstants.CARD_STSTUS_LOCK)){
                    ajaxJson.setMsg("当前卡已冻结！");
                }
                ajaxJson.setMsg("当前卡状态无法使用，错误号：" + gasCardNew.getCard_status());
                return ajaxJson;
            }
            gasCardNew.setCard_status(InterfaceConstants.CARD_STSTUS_IN_USE);
            Integer nRet = gasCardService.updateGasCardInfo(gasCardNew);
            if(nRet < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无卡信息修改！！！");
                return ajaxJson;
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.DISTUBUTE_CARD_ERROR);
            logger.error("distributeCard error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/putCardToCRMStore"})
    @ResponseBody
    public AjaxJson putCardToCRMStore(HttpServletRequest request, HttpServletResponse response,CRMCardUpdateInfo crmCardUpdateInfo) {
        AjaxJson ajaxJson = new AjaxJson();
        if((crmCardUpdateInfo == null) || (crmCardUpdateInfo.getEndID() == null)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("要入库卡片为空！！！");
            return ajaxJson;
        }
        if(StringUtils.isEmpty(crmCardUpdateInfo.getSys_gas_station_id())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("气站ID为空！！！");
            return ajaxJson;
        }
        try
        {
            Map<String, Object> attributes = new HashMap<String, Object>();
            crmCardUpdateInfo.setStatusType(InterfaceConstants.CARD_STSTUS_ALREADY_SEND);
            crmCardUpdateInfo.setStation_receive_time(new Date());
            Integer nRet = gasCardService.updateGasCardStatus(crmCardUpdateInfo);
            if(nRet < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无卡信息修改！！！");
                return ajaxJson;
            }
            CRMCardUpdateInfo gascard = new CRMCardUpdateInfo();
            gascard.setPageNum(crmCardUpdateInfo.getPageNum());
            gascard.setPageSize(crmCardUpdateInfo.getPageSize());
            gascard.setStartID(crmCardUpdateInfo.getStartID());
            gascard.setEndID(crmCardUpdateInfo.getEndID());
            gascard.setStatusType(crmCardUpdateInfo.getStatusType());
            PageInfo<GasCard> pageinfo = gasCardService.queryGasCardForUpdate(gascard);
            attributes.put("pageInfo", pageinfo);
            attributes.put("gascard",pageinfo.getList());
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.PUT_CARD_STORAGE_ERROR);
            logger.error("putCardToCRMStore error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/queryCardInfoByCardNo"})
    @ResponseBody
    public AjaxJson queryCardInfoByCardNo(HttpServletRequest request, HttpServletResponse response,
                                          CRMCardUpdateInfo crmCardUpdateInfo){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        if((crmCardUpdateInfo.getStartID() == null) || (crmCardUpdateInfo.getEndID() == null)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("输入起始ID及终止ID为空！！！");
            return ajaxJson;
        }

        if((crmCardUpdateInfo.getStartID().toString().length() != 9) || (crmCardUpdateInfo.getEndID().toString().length() != 9)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("输入起始ID或终止ID不是9位！！！");
            return ajaxJson;
        }

        if(StringUtils.isEmpty(crmCardUpdateInfo.getSys_gas_station_id())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("气站ID为空！！！");
            return ajaxJson;
        }
        try
        {
            PageInfo<GasCard> pageinfo = gasCardService.queryGasCardForCRM(crmCardUpdateInfo);
            if(pageinfo.getList().size() == 0){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("查询数据为空！！！");
                return ajaxJson;
            }
            attributes.put("pageInfo", pageinfo);
            attributes.put("gascard",pageinfo.getList());
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CARD_PERIOD_ERROR);
            logger.error("queryCardInfo error： " + e);
        }
        return ajaxJson;
    }
}
