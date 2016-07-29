package com.sysongy.poms.crm.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.crm.model.CrmHelpType;

import java.util.List;


public interface CrmHelpTypeService {
	
	public PageInfo<CrmHelpType> queryCrmHelpTypePage(CrmHelpType obj) throws Exception;//分页查询
	
	public Integer save(CrmHelpType obj)throws Exception;//保存

	/**
	 * 查询问题分类列表
	 * @return
	 * @throws Exception
     */
	List<CrmHelpType> queryCrmHelpTypeList() throws Exception;

}
