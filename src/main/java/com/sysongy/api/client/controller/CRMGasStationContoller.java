package com.sysongy.api.client.controller;

import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/crmInterface/crmGasService")
public class CRMGasStationContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GastationService gastationService;

    /**
     *
     * @param map
     * @param gastation
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryGastationInfo")
    @ResponseBody
    public AjaxJson queryGastationInfo(ModelMap map, Gastation gastation) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        try{
            if(StringUtils.isEmpty(gastation.getSys_gas_station_id())){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("气站编号为空！！！");
                return ajaxJson;
            }
            Gastation gasStationInfo = gastationService.queryGastationByPK(gastation.getSys_gas_station_id());
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("gasStationInfo", gasStationInfo);
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_STSTION_ERROR + e.getMessage());
            logger.error("查询失败：" + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }
}
