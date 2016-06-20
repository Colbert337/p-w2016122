package com.sysongy.api.client.controller;

import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.gastation.service.GastationService;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/crmBaseService")
public class CRMBaseContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    private UsysparamService service;

    @ResponseBody
    @RequestMapping("/web/dictInfo")
    public AjaxJson queryParamList(HttpServletRequest request, HttpServletResponse response, Usysparam usysparam) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        if((usysparam == null) || (!StringUtils.isNotEmpty(usysparam.getGcode()))){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("Gcode为空！！！");
            return ajaxJson;
        }
        List<Usysparam> usysparamInfo = service.queryUsysparamByGcode(usysparam.getGcode());
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("usysparamInfos", usysparamInfo);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }
}
