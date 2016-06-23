package com.sysongy.poms.permi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;

@Service
public class SysUserAccountServiceImpl implements SysUserAccountService {
	
	@Autowired
	SysUserAccountMapper sysUserAccountMapper;
	
	@Override
	public int changeStatus(String accountid,String status) {
		
		SysUserAccount record = new SysUserAccount();
		record.setSysUserAccountId(accountid);
		record.setAccount_status(status);
		
		return sysUserAccountMapper.updateByPrimaryKeySelective(record);
	}
}
