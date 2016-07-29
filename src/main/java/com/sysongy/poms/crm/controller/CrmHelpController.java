package com.sysongy.poms.crm.controller;

import java.util.Date;
import java.util.List;

import com.sysongy.util.GlobalConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.crm.model.CrmHelp;
import com.sysongy.poms.crm.model.CrmHelpType;
import com.sysongy.poms.crm.service.CrmHelpService;
import com.sysongy.poms.crm.service.CrmHelpTypeService;
import com.sysongy.util.UUIDGenerator;


/**
 * @FileName: CrmHelpController
 * @Encoding: UTF-8
 * @Package: com.sysongy.crm.help.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月25日, 10:24
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@RequestMapping("/web/crm/help")
@Controller
public class CrmHelpController extends BaseContoller{
	
	@Autowired
	private CrmHelpService crmHelpService;
	@Autowired
	private CrmHelpTypeService crmHelpTypeService;
    
	/**
	 * 列表
	 * @param model
	 * @param crmHelp
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/list")
    public String queryAllList(Model model, CrmHelp crmHelp) throws Exception{
    	if(crmHelp.getPageNum() == null){
    		crmHelp.setPageNum(1);
    		crmHelp.setPageSize(10);
		}
		if(StringUtils.isEmpty(crmHelp.getOrderby())){
			crmHelp.setOrderby("created_date desc");
		}

    	PageInfo<CrmHelp> crmHelpList = crmHelpService.queryCrmHelpPage(crmHelp);
    	model.addAttribute("crmHelpList", crmHelpList);
        return "webpage/poms/crm/help_list";
    }
    
    /**
     * 删除
     * @param crmHelpId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam String crmHelpId) throws Exception{
    	crmHelpService.delete(crmHelpId);
        return "redirect:/web/crm/help/list";    
    }
    /**
     * 编辑
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam String crmHelpIdvalue)throws Exception{
    	CrmHelp crmHelp = crmHelpService.queryCrmHelp(crmHelpIdvalue);
    	model.addAttribute("crmHelp", crmHelp);
        return "webpage/poms/crm/help_edit";           	
    }
    
    /**
     * 修改
     * @param model
     * @param obj
     * @return
     * @throws Exception
     */
    @RequestMapping("/update")
    public String update(Model model,CrmHelp obj) throws Exception{
    	obj.setUpdatedDate(new Date());
    	crmHelpService.update(obj);
        return "redirect:/web/crm/help/list";        
    }
    /**
     * 保存
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(CrmHelp obj)throws Exception{
    	obj.setCrmHelpId(UUIDGenerator.getUUID());
    	crmHelpService.save(obj);
    	return "redirect:/web/crm/help/list";    	
    }
    
    /**
     * 查询问题类型信息
     * @param obj
     * @return
     */
    @RequestMapping("/type/query")
    @ResponseBody
    public List<CrmHelp> queryQuestionType(CrmHelp obj)throws Exception{
    	List<CrmHelp> list = crmHelpService.queryQuestionListById(obj);
    	 CrmHelp crmHelp = new CrmHelp();
    	 crmHelp.setCrmHelpId("other");
    	 crmHelp.setCrmHelpTypeId("其他");
    	 list.add(crmHelp);
		return list;   	
    }
    
    /**
     * 类型列表
     * @param model
     * @param crmHelpType
     * @return
     * @throws Exception
     */
    @RequestMapping("/type/list")
    public String queryHelpTypeList(Model model, CrmHelpType crmHelpType) throws Exception{
    	PageInfo<CrmHelpType> crmHelpTypeList = crmHelpTypeService.queryCrmHelpTypePage(crmHelpType);
    	model.addAttribute("crmHelpTypeList", crmHelpTypeList);
    	return "webpage/poms/crm/help_type_list";
    }
    
    /**
     * 类型保存
     * @return
     * @throws Exception
     */
    @RequestMapping("/type/save")
    public String typesave(CrmHelpType obj)throws Exception{
    	obj.setCrmHelpTypeId(UUIDGenerator.getUUID());
    	crmHelpTypeService.save(obj);
    	return "redirect:/web/crm/help/type/list";    	
    }


	/**
	 * 查询所有分类列表
	 * @param obj
	 * @return
	 * @throws Exception
     */
	@RequestMapping("/type/list/all")
	public String queryTypeList(CrmHelpType obj,ModelMap map)throws Exception{
		List<CrmHelpType> crmHelpTypeList = crmHelpTypeService.queryCrmHelpTypeList();
		map.addAttribute("crmHelpTypeList",crmHelpTypeList);
		return "redirect:/web/crm/help/type/list";
	}

	/**
	 * 查询问题列表
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/all")
	public String queryQuestionListAll(CrmHelp obj,ModelMap map)throws Exception{
		obj.setIsNotice(GlobalConstant.QuestionNotice.QUESTION);
		List<CrmHelpType> crmHelpTypeList = crmHelpTypeService.queryCrmHelpTypeList();
		List<CrmHelp> crmHelpList = crmHelpService.queryQuestionListByName(obj);

		map.addAttribute("crmHelpTypeList",crmHelpTypeList);
		map.addAttribute("crmHelpList",crmHelpList);
		return "webpage/crm/hp_queston";
	}

	/**
	 * 查询公告列表
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/notice")
	public String queryNoticeListAll(CrmHelp obj,ModelMap map)throws Exception{
		obj.setIsNotice(GlobalConstant.QuestionNotice.NOTICE);
		List<CrmHelp> crmHelpList = crmHelpService.queryQuestionListByName(obj);
		map.addAttribute("crmHelpList",crmHelpList);
		return "webpage/crm/hp_notice";
	}

}
