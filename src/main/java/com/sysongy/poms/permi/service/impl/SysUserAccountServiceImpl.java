package com.sysongy.poms.permi.service.impl;

import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class SysUserAccountServiceImpl implements SysUserAccountService {
	
	@Autowired
	private SysUserAccountMapper sysUserAccountMapper;
	
	@Override
	public int changeStatus(String accountid,String status) {
		
		SysUserAccount record = new SysUserAccount();
		record.setSysUserAccountId(accountid);
		record.setAccount_status(status);
		
		return sysUserAccountMapper.updateByPrimaryKeySelective(record);
	}

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

	/**
	 * 更新现金到此账户，实现乐观锁
	 * Cash -- 正值 则是增加，负值则是减少
	 */
	@Override
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
