package com.sysongy.poms.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.system.dao.SysDepositLogMapper;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.system.service.SysDepositLogService;

@Service
public class SysDepositLogServiceImpl implements SysDepositLogService {
	
	@Autowired
	private SysDepositLogMapper depositLogMapper;

	@Override
	public String saveDepositLog(SysDepositLog obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<SysDepositLog> queryDepositLog(SysDepositLog obj) throws Exception {
		PageHelper.startPage(obj.getPageNum(), obj.getPageSize(), obj.getOrderby());
		List<SysDepositLog> list = depositLogMapper.queryForPage(obj);
		PageInfo<SysDepositLog> pageInfo = new PageInfo<SysDepositLog>(list);
		
		return pageInfo;
	}

}
