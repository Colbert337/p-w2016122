package com.sysongy.poms.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.crm.dao.CrmHelpMapper;
import com.sysongy.poms.crm.model.CrmHelp;
import com.sysongy.poms.crm.service.CrmHelpService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @FileName: CrmHelpServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.crm.help.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月25日, 10:24
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class CrmHelpServiceImpl implements CrmHelpService{
	
	@Autowired
    private CrmHelpMapper crmHelpMapper;

	/**
	 * 列表查询
	 */
	@Override
	public List<CrmHelp> queryCrmHelpServiceList(CrmHelp obj) throws Exception {
		List<CrmHelp> list = crmHelpMapper.queryCrmHelp(obj);
		return list;
	}
    
	/**
	 * 删除
	 */
	@Override
	public void delete(String crmHelpId) throws Exception {
		crmHelpMapper.deleteCrmHelp(crmHelpId);
		
	}
	
    /**
     * 更新
     */
	@Override
	public Integer update(CrmHelp obj) throws Exception {
		return crmHelpMapper.updateCrmHelp(obj);
		
	}
    
	/**
	 * 回显信息
	 */
	@Override
	public CrmHelp queryCrmHelp(String crmHelpId) throws Exception {
		return crmHelpMapper.queryCrmHelpValue(crmHelpId);
		
	}

	/**
	 * 保存
	 */
	@Override
	public Integer save(CrmHelp obj) throws Exception {
		return crmHelpMapper.add(obj);
		
	}
	
	/**
	 * 分页
	 */
	@Override
	public PageInfo<CrmHelp> queryCrmHelpPage(CrmHelp obj) throws Exception {
		PageHelper.startPage(obj.getPageNum(), obj.getPageSize(), obj.getOrderby());
		List<CrmHelp> list = crmHelpMapper.queryForPage(obj);
		PageInfo<CrmHelp> pageInfo = new PageInfo<CrmHelp>(list);
		return pageInfo;
	}
	
    /**
     * 问题类型信息
     */
	@Override
	public List<CrmHelp> queryQuestionListById(CrmHelp obj) throws Exception {
		return crmHelpMapper.queryQuestiontypeList(obj);
		
	}

}
