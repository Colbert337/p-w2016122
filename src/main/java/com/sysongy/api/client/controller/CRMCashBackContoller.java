package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.system.dao.SysCashBackMapper;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/crmCashBackContoller")
public class CRMCashBackContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    private SysCashBackService service;

    @ResponseBody
    @RequestMapping("/web/crmCashBackContoller")
    public AjaxJson queryCashBackList(HttpServletRequest request, HttpServletResponse response, SysCashBack record) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        if((record == null) || (record.getStart_date() != null)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("当天日期为空！！！");
            return ajaxJson;
        }

        PageInfo<SysCashBack> sysCashBacks = service.queryCashBack(record);
        if((sysCashBacks == null) || (sysCashBacks.getList().size() == 0)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("无法查询到对应数据！！！");
            return ajaxJson;
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysCashBacks", sysCashBacks);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    /**
     * 分拣List
     * @param cashBacks
     * @return
     */
    private Map<String, Object> getSysCashBack(List<SysCashBack> cashBacks){

        Map<String, Object> attributes = new HashMap<String, Object>();
        List<SysCashBack> sysCashBacksForPos = new ArrayList<SysCashBack>();
        List<SysCashBack> sysCashBacksForCard = new ArrayList<SysCashBack>();
        List<SysCashBack> sysCashBacksForCash = new ArrayList<SysCashBack>();

        for(SysCashBack sysCashBack : cashBacks){
            if(sysCashBack.getSys_cash_back_no().equalsIgnoreCase
                    (InterfaceConstants.RECHARGE_TYPE_CARD)){
                sysCashBacksForCard.add(sysCashBack);
                continue;
            }
            if(sysCashBack.getSys_cash_back_no().equalsIgnoreCase
                    (InterfaceConstants.RECHARGE_TYPE_POS)){
                sysCashBacksForPos.add(sysCashBack);
                continue;
            }
            if(sysCashBack.getSys_cash_back_no().equalsIgnoreCase
                    (InterfaceConstants.RECHARGE_TYPE_CASH)){
                sysCashBacksForCash.add(sysCashBack);
                continue;
            }
        }
        return attributes;
    }

    /**
     * 将传入的List过滤优选生效的一条
     * @param cashBacks
     * @return
     */
    private List<SysCashBack> reFilter(List<SysCashBack> cashBacks){

        return cashBacks;
    }

}
