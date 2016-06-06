package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/crmGasService")
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
            Gastation gasStationInfo = gastationService.queryGastationByPK(gastation.getSys_gas_station_id());
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("gasStationInfo", gasStationInfo);
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(e.getMessage());
        }
        return ajaxJson;
    }
}
