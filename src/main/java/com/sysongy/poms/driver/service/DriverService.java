package com.sysongy.poms.driver.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.gastation.model.Gastation;

public interface DriverService {
	
	public PageInfo<SysDriver> queryDrivers(SysDriver obj) throws Exception;
	
	public SysDriver queryDriverByPK(String sysDriverId) throws Exception;
	
	public Integer saveDriver(SysDriver obj,  String operation) throws Exception;
	
	public Integer delDriver(String sysDriverId) throws Exception;

}
