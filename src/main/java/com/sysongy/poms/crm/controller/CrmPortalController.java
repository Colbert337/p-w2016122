package com.sysongy.poms.crm.controller;

import com.sysongy.poms.crm.model.CrmHelp;
import com.sysongy.poms.crm.model.CrmHelpType;
import com.sysongy.poms.crm.service.CrmHelpService;
import com.sysongy.poms.crm.service.CrmHelpTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @FileName: CrmPortalController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.crm.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月05日, 9:53
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@RequestMapping("/portal/crm/help")
@Controller
public class CrmPortalController {
    @Autowired
    private CrmHelpService crmHelpService;
    @Autowired
    private CrmHelpTypeService crmHelpTypeService;

    /**
     * 问题列表和分类查询
     * @param model
     * @param crmHelp
     * @param crmHelpType
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/list/all")
    public String queryAllList(Model model, CrmHelp crmHelp, CrmHelpType crmHelpType) throws Exception{
        //问题列表
        List<Map<String, Object>> crmHelpList = crmHelpService.queryCrmHelpList(crmHelp);
        model.addAttribute("crmHelpList", crmHelpList);                
        //问题分类列表
        List<CrmHelpType> crmHelpTypeList = crmHelpTypeService.queryCrmHelpTypeList(crmHelpType);
        model.addAttribute("crmHelpTypeList", crmHelpTypeList);             
        
        return "webpage/crm/hp_queston";
    }
    
    /**
     * 根据问题分类查询相应的问题
     * @param model
     * @param crmHelpTypeId 
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/type/list")
    public String queryHelpQuestionType(Model model,@RequestParam(value="crmHelpTypeId",required = false) String crmHelpTypeId )throws Exception{
    	//问题列表
    	List<CrmHelpType> crmHelpTypeList = crmHelpTypeService.queryCrmHelpTypeList(null);
        model.addAttribute("crmHelpTypeList", crmHelpTypeList);
    	//根据不同的问题分类查询问题列表
   	    List<CrmHelp> crmHelpList = crmHelpService.queryCrmHelpServiceList(crmHelpTypeId);
    	model.addAttribute("crmHelpList", crmHelpList);
    	
		return "webpage/crm/hp_queston";		
   }    
}
