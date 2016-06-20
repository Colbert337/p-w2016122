package com.sysongy.api.client.controller.model;

import com.alibaba.fastjson.JSON;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/16.
 */

@Controller
@RequestMapping("/crmSystemDictionary")
public class CRMSystemDictionary {

    @Autowired
    private UsysparamService service;

    @ResponseBody
    @RequestMapping("/queryDict")
    public AjaxJson queryDict(HttpServletRequest request, ModelMap map, @RequestParam String gcode, @RequestParam(required = false) String scode) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        if((gcode == null) || (!StringUtils.isNotEmpty(gcode))){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("输入code为空！！！");
            return ajaxJson;
        }
        Map<String, Object> attributes = new HashMap<String, Object>();
        List<Usysparam> list = service.query(gcode, scode);
        attributes.put("usysparams", list);
        return ajaxJson;
    }
}
