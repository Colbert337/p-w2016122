package com.sysongy.poms.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.crm.dao.CrmHelpTypeMapper;
import com.sysongy.poms.crm.model.CrmHelpType;
import com.sysongy.poms.crm.service.CrmHelpTypeService;

@Service
public class CrmHelpTypeServiceImpl implements CrmHelpTypeService{
	
	@Autowired
	private CrmHelpTypeMapper crmHelpTypeMapper;
    
	/**
	 * 分页列表
	 */
	@Override
	public PageInfo<CrmHelpType> queryCrmHelpTypePage(CrmHelpType obj) throws Exception {
		PageHelper.startPage(obj.getPageNum(), obj.getPageSize(), obj.getOrderby());
		List<CrmHelpType> list = crmHelpTypeMapper.queryForPageList(obj);
		PageInfo<CrmHelpType> pageInfo = new PageInfo<CrmHelpType>(list);
		return pageInfo;
	}
	
    /**
     * 类型保存
     */
	@Override
	public Integer save(CrmHelpType obj) throws Exception {
		return crmHelpTypeMapper.add(obj);
	}

}
