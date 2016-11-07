package com.sysongy.poms.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.mobile.model.MbAppVersion;
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
	@Autowired
	private DriverService driverService;
	
	@Override
	public Integer saveOperationLog(SysOperationLog sysOperationLog, String operatorID) throws Exception {
		sysOperationLog.setSys_operation_log_id(UUIDGenerator.getUUID());
		Map<String, Object> userMap = sysUserService.queryUserMapByUserId(operatorID);
		SysDriver sysDriver = driverService.queryDriverByPK(operatorID);
		SysDriver aSysDriver = driverService.selectByAccount(operatorID);
		sysOperationLog.setOperator_id(operatorID);
		if(userMap!=null){
			//根据userID
			if(!userMap.isEmpty()){
				Object sys_role_id = userMap.get("sys_role_id");
				if(sys_role_id!=null){
					sysOperationLog.setSys_role_id(sys_role_id.toString());
				}
				Object realName = userMap.get("realName");
				if(realName!=null){
					sysOperationLog.setUser_name(realName.toString());	
				}
			}		
		}else if(sysDriver!=null){
			sysOperationLog.setSys_role_id("");
			sysOperationLog.setUser_name(sysDriver.getUserName());	
		}else if(aSysDriver!=null){
			sysOperationLog.setSys_role_id("");
			sysOperationLog.setUser_name(aSysDriver.getUserName());			
		}
		sysOperationLog.setCreated_date(new Date());
		return operationLogMapper.insert(sysOperationLog);
	}

	@Override
	public PageInfo<SysOperationLog> queryOperationLog(SysOperationLog obj) throws Exception {
		PageHelper.startPage(obj.getPageNum(), obj.getPageSize(), obj.getOrderby());
		List<SysOperationLog> list = operationLogMapper.queryForPage(obj);
        for(SysOperationLog log:list){
        	log.setCreatedDateStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log.getCreated_date()));
        }
		PageInfo<SysOperationLog> pageInfo = new PageInfo<SysOperationLog>(list);
		return pageInfo;
	}

}
