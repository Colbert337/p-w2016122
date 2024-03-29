package com.sysongy.poms.driver.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.model.SysDriverReviewStr;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.tcms.advance.model.TcFleet;

public interface DriverService {
	
	public PageInfo<SysDriver> queryDrivers(SysDriver obj) throws Exception;
	
	public List<SysDriver> queryeSingleList(SysDriver record) throws Exception;
	/**
	 * 查询司机信息列表，关联公司
	 * @param record
	 * @return
	 */
	public List<SysDriver> queryForPageList(SysDriver record) throws Exception;

	public PageInfo<SysDriver> querySingleDriver(SysDriver obj) throws Exception;

	public PageInfo<SysDriver> ifExistDriver(SysDriver obj) throws Exception;

	public PageInfo<SysDriver> queryForPageSingleList(SysDriver obj) throws Exception;
	
	public SysDriver queryDriverByPK(String sysDriverId) throws Exception;
		
	public Integer delDriver(String sysDriverId) throws Exception;
	
	public Integer saveDriver(SysDriver record, String operation, String invitationCode, String operator_id) throws Exception;

	/**
	 * 给司机充钱
	 * @param order
	 * @return
	 */
	public String chargeCashToDriver(SysOrder order,String is_discharge) throws Exception;
	
	/**
	 * 给司机转账的时候扣除司机账户
	 * @param order
	 * @return
     * @throws Exception 
	 */
	public String deductCashToDriver(SysOrder order, String is_discharge) throws Exception;

	/**
	 * 给司机返现
	 * @param order
	 * @return
	 */
	public String cashBackToDriver(SysOrder order) throws Exception;

	public Integer isExists(SysDriver obj) throws Exception;

	public Integer distributeCard(SysDriver record) throws Exception;

	public SysDriver queryDriverByMobilePhone(SysDriver record) throws Exception;

	public SysDriver queryDriverBySecurityPhone(SysDriver record) throws Exception;

	public Integer updateAndReview(String driverid, String type, String memo, CurrUser currUser) throws Exception;
	/**
	 * 条件查询司机列表
	 * @param record
	 * @return
	 */
	PageInfo<SysDriver> querySearchDriverList(SysDriver record);

	/**
	 * 批量离职司机
	 * @param idList
	 * @return
     */
	int deleteDriverByIds(List<String> idList);
	
	PageInfo<SysDriverReviewStr> queryDriversLog(SysDriverReviewStr record) throws Exception;

	/**
	 * 查询当前区域下的最大下标
	 * @return
	 */
	SysDriver queryMaxIndex();
	
	/**
     * 根據邀請碼查詢用戶  
     */
	public SysDriver queryByInvitationCode(String invitationCode) throws Exception;
	
	/**
	 * 用户登录
	 */
	public SysDriver queryByUserNameAndPassword(SysDriver record) throws Exception;
	
	public SysDriver queryByUserName(SysDriver record) throws Exception;
	
	public SysDriver queryByDeviceToken(String deviceToken) throws Exception;
	
	public List<SysDriver> queryAll() throws Exception;

	public void cashBackForRegister(SysDriver driver, String invitationCode, String operator_id) throws Exception;

	public SysDriver selectByAccount(String sys_user_account_id) throws Exception ;
	
	 public int updateDriverByIntegral(SysDriver sysDriver) throws Exception ;
}
