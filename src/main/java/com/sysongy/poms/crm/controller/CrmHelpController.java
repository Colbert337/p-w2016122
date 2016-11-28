package com.sysongy.poms.crm.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    	if(crmHelp.getConvertPageNum() != null){
			if(crmHelp.getConvertPageNum() > crmHelp.getPageNumMax()){
				crmHelp.setPageNum(crmHelp.getPageNumMax());
			}else if(crmHelp.getConvertPageNum() < 1){
				crmHelp.setPageNum(1);
			}else{
				crmHelp.setPageNum(crmHelp.getConvertPageNum());
			}
		}
		if(StringUtils.isEmpty(crmHelp.getOrderby())){
			crmHelp.setOrderby("created_date desc");
		}
		
    	PageInfo<Map<String, Object>> crmHelpList = crmHelpService.queryCrmHelpPage(crmHelp);  	
    	model.addAttribute("pageInfo", crmHelpList);
    	model.addAttribute("crmHelp", crmHelp);
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
    	crmHelp.setCreatedDate(new Date());
    	model.addAttribute("crmHelp", crmHelp);//用于条件回显
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
    	obj.setModel(new Date());
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
    	obj.setCreatedDate(new Date());
    	
    	crmHelpService.save(obj);
    	return "redirect:/web/crm/help/list";    
    }
    
    /**
     * 查询问题类型信息
     * @param obj
     * @return
     */
    @RequestMapping("/question/type/query")
    @ResponseBody
    public List<CrmHelpType> queryHelpType(CrmHelpType obj)throws Exception{
    	List<CrmHelpType> typeList = crmHelpTypeService.queryTypeList(obj);
		return typeList;   	
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
    	if(crmHelpType.getPageNum() == null){
    		crmHelpType.setPageNum(1);
    		crmHelpType.setPageSize(10);
		}
    	if(crmHelpType.getConvertPageNum() != null){
			if(crmHelpType.getConvertPageNum() > crmHelpType.getPageNumMax()){
				crmHelpType.setPageNum(crmHelpType.getPageNumMax());
			}else if(crmHelpType.getConvertPageNum() < 1){
				crmHelpType.setPageNum(1);
			}else{
				crmHelpType.setPageNum(crmHelpType.getConvertPageNum());
			}
		}
		if(StringUtils.isEmpty(crmHelpType.getOrderby())){
			crmHelpType.setOrderby("created_date desc");
		}

    	PageInfo<CrmHelpType> crmHelpTypeList = crmHelpTypeService.queryCrmHelpTypePage(crmHelpType);
    	model.addAttribute("pageInfo", crmHelpTypeList);
    	model.addAttribute("crmHelpType", crmHelpType);
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
     * 问题分类删除
     * @param crmHelpId
     * @return
     * @throws Exception
     */
    @RequestMapping("/type/delete")
    public String deleteType(@RequestParam String crmHelpTypeId) throws Exception{
    	crmHelpTypeService.delete(crmHelpTypeId);
        return "redirect:/web/crm/help/type/list";    
    }
    
    /**
     * 类型编辑
     * @return
     * @throws Exception
     */
    @RequestMapping("/type/edit")
    public String editType(Model model, @RequestParam String crmHelpTypeIdvalue)throws Exception{
    	CrmHelpType crmHelpType = crmHelpTypeService.editQuery(crmHelpTypeIdvalue);
    	model.addAttribute("crmHelpType", crmHelpType);
    	return "webpage/poms/crm/help_type_edit";       	
    }
    
    /**
     * 类型修改
     * @param model
     * @param obj
     * @return
     * @throws Exception
     */
    @RequestMapping("/type/update")
    public String updateType(Model model,CrmHelpType obj) throws Exception{
    	obj.setModel(new Date());
    	obj.setCreatedDate(new Date());
    	obj.setUpdatedDate(new Date());
    	crmHelpTypeService.update(obj);
    	return "redirect:/web/crm/help/type/list";              
    }
}
