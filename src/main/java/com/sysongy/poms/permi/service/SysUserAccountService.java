package com.sysongy.poms.permi.service;

import java.math.BigDecimal;

import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.permi.model.SysUserAccount;

public interface SysUserAccountService {
	
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
}
