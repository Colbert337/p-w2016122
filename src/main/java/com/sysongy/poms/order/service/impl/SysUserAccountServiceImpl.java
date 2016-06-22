package com.sysongy.poms.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.sysongy.poms.order.service.SysUserAccountService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.util.GlobalConstant;
import org.springframework.stereotype.Service;

@Service
public class SysUserAccountServiceImpl implements SysUserAccountService {

	@Autowired
	private SysUserAccountMapper sysUserAccountMapper;
	 
	@Override
	public int deleteByPrimaryKey(String sysUserAccountId) {
		return sysUserAccountMapper.deleteByPrimaryKey(sysUserAccountId);
	}

	@Override
	public int insert(SysUserAccount record) {
		return sysUserAccountMapper.insert(record);
	}
	
	@Override
	public SysUserAccount selectByPrimaryKey(String sysUserAccountId) {
		return sysUserAccountMapper.selectByPrimaryKey(sysUserAccountId);
	}

	@Override
	public int updateAccount(SysUserAccount record) {
		return sysUserAccountMapper.updateAccount(record);
	}

	@Override
	/**
	 * 更新现金到此账户，实现乐观锁
	 * Cash -- 正值 则是增加，负值则是减少
	 */
	public synchronized String addCashToAccount(String accountId, BigDecimal cash) {
		SysUserAccount sysUserAccount = sysUserAccountMapper.selectByPrimaryKey(accountId);
		BigDecimal balance = new BigDecimal(sysUserAccount.getAccountBalance()) ;
		//在此增加金额，如果是负值则是充红或者消费,仍然用add。
		BigDecimal balance_result = balance.add(cash);
		sysUserAccount.setAccountBalance(balance_result.toPlainString());
		sysUserAccount.setUpdatedDate(new Date());
		//更新此account对象则保存到db中
		sysUserAccountMapper.updateAccount(sysUserAccount);
		return GlobalConstant.OrderProcessResult.SUCCESS;
	}

}
