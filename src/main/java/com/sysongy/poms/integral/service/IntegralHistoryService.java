package com.sysongy.poms.integral.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.integral.model.IntegralHistory;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.order.model.SysOrder;

public interface IntegralHistoryService {

	public PageInfo<IntegralHistory> queryIntegralHistory(IntegralHistory integralHistory) throws Exception;

	public String modifyIntegralHistory(IntegralHistory integralHistory, String userID) throws Exception;

	public String addIntegralHistory(IntegralHistory integralHistory, String userID) throws Exception;

	public Integer delIntegralHistory(String integral_history_id) throws Exception;

	public IntegralHistory queryIntegralHistoryByPK(String integral_history_id) throws Exception;
	
	public void addsmrzIntegralHistory(SysDriver sysDriver,String operator_id) throws Exception;
	
	public void addszmbsjIntegralHistory(SysDriver sysDriver) throws Exception;
	
	public void addIntegralHistory(SysOrder sysOrder,String type) throws Exception;
	
	public void addsblkIntegralHistory(SysRoadCondition road,String userID) throws Exception;
	
	public void addyqcgIntegralHistory(SysDriver record,String invitationCode, String operator_id) throws Exception;

}
