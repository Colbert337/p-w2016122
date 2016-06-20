package com.sysongy.poms.driver.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.order.model.SysOrder;

public interface DriverService {
	
	public PageInfo<SysDriver> queryDrivers(SysDriver obj) throws Exception;
	
	public SysDriver queryDriverByPK(String sysDriverId) throws Exception;
	
	public Integer saveDriver(SysDriver obj,  String operation) throws Exception;
	
	public Integer delDriver(String sysDriverId) throws Exception;
	
	/**
	 * 给司机充钱
	 * @param order
	 * @return
	 */
	public String chargeCashToDriver(SysOrder order) throws Exception;

	/**
	 * 给司机返现
	 * @param order
	 * @return
	 */
	public String cashBackToDriver(SysOrder order) throws Exception;

	public Integer isExists(SysDriver obj) throws Exception;

	public Integer distributeCard(SysDriver record) throws Exception;

	public SysDriver queryDriverByMobilePhone(SysDriver record) throws Exception;
}
