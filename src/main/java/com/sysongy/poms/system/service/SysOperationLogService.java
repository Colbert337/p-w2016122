package com.sysongy.poms.system.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.system.model.SysOperationLog;

public interface SysOperationLogService {
	
	public Integer saveOperationLog(SysOperationLog obj) throws Exception;

	public PageInfo<SysOperationLog> queryOperationLog(SysOperationLog obj) throws Exception;
}
