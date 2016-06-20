package com.sysongy.poms.driver.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;

public interface DriverService {
	
	public PageInfo<SysDriver> queryDrivers(SysDriver obj) throws Exception;
	
	public SysDriver queryDriverByPK(String sysDriverId) throws Exception;
	
	public Integer saveDriver(SysDriver obj,  String operation) throws Exception;
	
	public Integer delDriver(String sysDriverId) throws Exception;

	public Integer isExists(SysDriver obj) throws Exception;

	public Integer distributeCard(SysDriver record) throws Exception;

	public SysDriver queryDriverByMobilePhone(SysDriver record) throws Exception;
	
	public Integer review(String driverid, String type) throws Exception;
}
