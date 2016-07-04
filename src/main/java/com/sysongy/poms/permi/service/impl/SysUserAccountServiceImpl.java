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
	public synchronized String addCashToAccount(String accountId, BigDecimal cash) throws Exception {
		SysUserAccount sysUserAccount = sysUserAccountMapper.selectByPrimaryKey(accountId);
		BigDecimal balance = new BigDecimal(sysUserAccount.getAccountBalance()) ;
		//在此增加金额，如果是负值则是充红或者消费,仍然用add。
		BigDecimal balance_result = balance.add(cash);
		//如果余额变成负值，则抛出错误:余额不足
		if(balance_result.compareTo(new BigDecimal(0))<0){
			throw new Exception( GlobalConstant.OrderProcessResult.ORDER_ERROR_BALANCE_IS_NOT_ENOUGH);
		}
		sysUserAccount.setAccountBalance(balance_result.toString());
		sysUserAccount.setUpdatedDate(new Date());
		//对version加1
		int ver = sysUserAccount.getVersion();
		sysUserAccount.setVersion(ver+1);
		//更新此account对象则保存到db中
		int upRow = sysUserAccountMapper.updateAccountBalance(sysUserAccount);
		if(upRow==1){
			return GlobalConstant.OrderProcessResult.SUCCESS;	
		}else{
			//upRow不是1就是0,0条的原因是version已经改变
			//TODO 乐观所处理
			throw new Exception( GlobalConstant.OrderProcessResult.ORDER_ACCOUNT_VERSION_HAVE_CHANGED);
		}
	}

	@Override
	public SysUserAccount queryUserAccountByStationId(String sysTransportionId) {
		return sysUserAccountMapper.queryUserAccountByStationId(sysTransportionId);
	}
}
