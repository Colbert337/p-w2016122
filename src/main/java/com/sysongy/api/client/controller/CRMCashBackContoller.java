package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.model.SysCashBackCRM;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

@Controller
@RequestMapping("/crmCashBackContoller")
public class CRMCashBackContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    private SysCashBackService service;

    @ResponseBody
    @RequestMapping("/web/queryCashBackList")
    public AjaxJson queryCashBackList(HttpServletRequest request, HttpServletResponse response, SysCashBack record) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = getSysCashBack(record);
        if(attributes == null){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("无法查询到对应数据！！！");
            return ajaxJson;
        }
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    /**
     * 分拣List
     * @paramcashBacks
     * @return
     */
    private Map<String, Object> getSysCashBack(SysCashBack record){
        Map<String, Object> attributes = new HashMap<String, Object>();
        SysCashBackCRM sysCashBackCRM = new SysCashBackCRM();
        try {
            record.setStart_date(new Date());
            record.setSys_cash_back_no(InterfaceConstants.RECHARGE_TYPE_POS);
            PageInfo<SysCashBack> sysCashBacksForPosPage = service.queryCashBackForCRM(record);
            if((sysCashBacksForPosPage != null) && (sysCashBacksForPosPage.getList().size() > 0)){
                sysCashBackCRM.setSysCashBackForPOS(sysCashBacksForPosPage.getList());
            }
            record.setSys_cash_back_no(InterfaceConstants.RECHARGE_TYPE_CARD);
            PageInfo<SysCashBack> sysCashBacksForCardPage = service.queryCashBackForCRM(record);
            if((sysCashBacksForCardPage != null) && (sysCashBacksForCardPage.getList().size() > 0)){
                sysCashBackCRM.setSysCashBackForCard(sysCashBacksForCardPage.getList());
            }
            record.setSys_cash_back_no(InterfaceConstants.RECHARGE_TYPE_CASH);
            PageInfo<SysCashBack> sysCashBacksForCashPage = service.queryCashBackForCRM(record);
            if((sysCashBacksForCashPage != null) && (sysCashBacksForCashPage.getList().size() > 0)){
                sysCashBackCRM.setSysCashBackForCash(sysCashBacksForCashPage.getList());
            }
            attributes.put("sysCashBackCRM", sysCashBackCRM);
            return attributes;
        } catch (Exception e) {
            logger.error("getSysCashBack error:" + e + "orderid:" + record.getStart_date());
            e.printStackTrace();
        }
        return null;
    }
}
