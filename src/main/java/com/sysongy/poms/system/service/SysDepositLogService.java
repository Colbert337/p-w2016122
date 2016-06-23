package com.sysongy.poms.system.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.system.model.SysDepositLog;

public interface SysDepositLogService {
	
	public String saveDepositLog(SysDepositLog obj) throws Exception;

	public PageInfo<SysDepositLog> queryDepositLog(SysDepositLog obj) throws Exception;
}
