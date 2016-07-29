package com.sysongy.poms.crm.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.crm.model.CrmHelpType;


public interface CrmHelpTypeService {
	
	public PageInfo<CrmHelpType> queryCrmHelpTypePage(CrmHelpType obj) throws Exception;//分页查询
	
	public Integer save(CrmHelpType obj)throws Exception;//保存

}
