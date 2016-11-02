package com.sysongy.poms.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.dao.SysOperationLogMapper;
import com.sysongy.poms.system.model.SysOperationLog;
import com.sysongy.poms.system.service.SysOperationLogService;
import com.sysongy.util.UUIDGenerator;

@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {
	
	@Autowired
	private SysOperationLogMapper operationLogMapper;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public Integer saveOperationLog(SysOperationLog sysOperationLog, String operatorID) throws Exception {
		sysOperationLog.setSysOperationLogId(UUIDGenerator.getUUID());
		Map<String, Object> userMap = sysUserService.queryUserMapByUserId(operatorID);
		sysOperationLog.setOperator_id(operatorID);
		//根据userID
		if(!userMap.isEmpty()){
			sysOperationLog.setSys_role_id(userMap.get("sys_role_id").toString());
			sysOperationLog.setUser_name(userMap.get("user_name").toString());	
		}
		sysOperationLog.setCreated_date(new Date());
		return operationLogMapper.insert(sysOperationLog);
	}

	@Override
	public PageInfo<SysOperationLog> queryOperationLog(SysOperationLog obj) throws Exception {
		PageHelper.startPage(obj.getPageNum(), obj.getPageSize(), obj.getOrderby());
		List<SysOperationLog> list = operationLogMapper.queryForPage(obj);
		PageInfo<SysOperationLog> pageInfo = new PageInfo<SysOperationLog>(list);
		
		return pageInfo;
	}

}
