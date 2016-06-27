package com.sysongy.poms.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.system.dao.SysOperationLogMapper;
import com.sysongy.poms.system.model.SysOperationLog;
import com.sysongy.poms.system.service.SysOperationLogService;

@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {
	
	@Autowired
	private SysOperationLogMapper operationLogMapper;
	
	@Override
	public Integer saveOperationLog(SysOperationLog obj) throws Exception {
		return operationLogMapper.insert(obj);
	}

	@Override
	public PageInfo<SysOperationLog> queryOperationLog(SysOperationLog obj) throws Exception {
		PageHelper.startPage(obj.getPageNum(), obj.getPageSize(), obj.getOrderby());
		List<SysOperationLog> list = operationLogMapper.queryForPage(obj);
		PageInfo<SysOperationLog> pageInfo = new PageInfo<SysOperationLog>(list);
		
		return pageInfo;
	}

}
