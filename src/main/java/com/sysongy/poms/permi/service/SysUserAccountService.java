package com.sysongy.poms.permi.service;

import java.math.BigDecimal;

import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.permi.model.SysUserAccount;

public interface SysUserAccountService {

	/**
	 * 冻结账户和卡号
	 * @param accountid
	 * @param status 状态 0 冻结账户 1 冻结卡 2 解冻(全部解冻)
	 * @param cardno
	 * @return
	 * @throws Exception
	 */
	int changeStatus(String accountid, String status, String cardno) throws Exception;

	int deleteByPrimaryKey(String sysUserAccountId);

	int insert(SysUserAccount record);

	SysUserAccount selectByPrimaryKey(String sysUserAccountId);

	int updateAccount(SysUserAccount record);

	/**
	 * 更新最新余额到账户
	 * @return
	 */
	String addCashToAccount(String accountId, BigDecimal cash, String order_type) throws Exception;
	
	/**
	 * 根据站点ID查询站点账户信息
	 * @param sysTransportionId 运输公司ID
	 * @return
     */
	SysUserAccount queryUserAccountByStationId(String sysTransportionId);

	/**
	 * 根据司机编号查询司机账户信息
	 * @param sysDriverId 运输公司ID
	 * @return
	 */
	SysUserAccount queryUserAccountByDriverId(String sysDriverId);

	SysUserAccount queryUserAccountByGas(String channelNumber);
}
