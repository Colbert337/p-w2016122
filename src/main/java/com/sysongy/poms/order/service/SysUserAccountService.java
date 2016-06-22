package com.sysongy.poms.order.service;

import java.math.BigDecimal;

import com.sysongy.poms.permi.model.SysUserAccount;

public interface SysUserAccountService {

	int deleteByPrimaryKey(String sysUserAccountId);

    int insert(SysUserAccount record);

    SysUserAccount selectByPrimaryKey(String sysUserAccountId);

    int updateAccount(SysUserAccount record);
    
    /**
     * 更新最新余额到账户
     * @return
     */
    String addCashToAccount(String accountId, BigDecimal cash);
}
