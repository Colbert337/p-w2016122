package com.sysongy.poms.crm.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.crm.model.CrmHelp;
import com.sysongy.poms.crm.model.CrmHelpType;

import java.util.List;


public interface CrmHelpTypeService {
	
	public PageInfo<CrmHelpType> queryCrmHelpTypePage(CrmHelpType obj) throws Exception;//分页查询
	
	public Integer save(CrmHelpType obj)throws Exception;//保存
	
	public List<CrmHelpType> queryTypeList(CrmHelpType obj) throws Exception;//查询问题类型信息

	/**
	 * 查询问题分类列表
	 * @return
	 * @throws Exception
     */
	List<CrmHelpType> queryCrmHelpTypeList() throws Exception;
	
	public void delete(String crmHelpTypeId)throws Exception;//删除
	
	public CrmHelpType editQuery(String crmHelpTypeId) throws Exception;//编辑回显信息

	public Integer update(CrmHelpType obj)throws Exception;//更新
}
