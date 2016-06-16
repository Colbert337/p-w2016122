package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.api.client.controller.model.CRMCardUpdateInfo;
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
@RequestMapping("/crmCardService")
public class CRMCardContoller {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    private GasCardService gasCardService;

    @RequestMapping(value = {"/web/queryCardInfo"})
    @ResponseBody
    public AjaxJson queryCardInfo(HttpServletRequest request, HttpServletResponse response, GasCard gascard){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            if(!StringUtils.isEmpty(gascard.getStorage_time_range())){
                String []tmpRange = gascard.getStorage_time_range().split("-");
                if(tmpRange.length == 2){
                    gascard.setStorage_time_after(tmpRange[0].trim()+" 00:00:00");
                    gascard.setStorage_time_before(tmpRange[1]+" 23:59:59");
                }
            }
            PageInfo<GasCard> pageinfo = gasCardService.queryGasCard(gascard);
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
            GasCard gasCard = gasCardService.queryGasCardInfo(gascard.getCard_no());
            if(!gasCard.getCard_status().equalsIgnoreCase(InterfaceConstants.CARD_STSTUS_ALREADY_SEND)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("当前卡状态无法使用，错误号：" + gasCard.getCard_status());
                return ajaxJson;
            }
            gasCard.setCard_status(InterfaceConstants.CARD_STSTUS_IN_USE);
            Integer nRet = gasCardService.updateGasCardInfo(gasCard);
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
    public AjaxJson putCardToCRMStore(HttpServletRequest request, HttpServletResponse response, CRMCardUpdateInfo crmCardUpdateInfo) {
        AjaxJson ajaxJson = new AjaxJson();
        if((crmCardUpdateInfo == null) || (crmCardUpdateInfo.getEndID() != null)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("要入库卡片为空！！！");
            return ajaxJson;
        }
        try
        {
            crmCardUpdateInfo.setStatusType(InterfaceConstants.CARD_STSTUS_ALREADY_SEND);
            Integer nRet = gasCardService.updateGasCardStatus(crmCardUpdateInfo);
            if(nRet < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无卡信息修改！！！");
                return ajaxJson;
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.PUT_CARD_STORAGE_ERROR);
            logger.error("putCardToCRMStore error： " + e);
        }

        return ajaxJson;
    }
}
