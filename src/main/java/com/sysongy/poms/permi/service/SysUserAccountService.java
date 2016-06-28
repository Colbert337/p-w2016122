package com.sysongy.poms.permi.service;

import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public interface SysUserAccountService {
	
	int changeStatus(String accountid, String status);

	int deleteByPrimaryKey(String sysUserAccountId);

	int insert(SysUserAccount record);

	SysUserAccount selectByPrimaryKey(String sysUserAccountId);

	int updateAccount(SysUserAccount record);

	/**
	 * 更新最新余额到账户
	 * @return
	 */
	String addCashToAccount(String accountId, BigDecimal cash) throws Exception;

	/**
	 * 根据站点ID查询站点账户信息
	 * @param sysTransportionId 运输公司ID
	 * @return
     */
	SysUserAccount queryUserAccountByStationId(String sysTransportionId);
}
