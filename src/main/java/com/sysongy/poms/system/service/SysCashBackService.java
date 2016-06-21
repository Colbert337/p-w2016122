package com.sysongy.poms.system.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.system.model.SysCashBack;

public interface SysCashBackService {
	
	public PageInfo<SysCashBack> queryCashBack(SysCashBack obj) throws Exception;
	
	public SysCashBack queryCashBackByPK(String cashBackid) throws Exception;
	
	public String saveCashBack(SysCashBack obj,  String operation) throws Exception;
	
	public Integer delCashBack(String sysCashBackId) throws Exception;
	
	/**
	 * 通过cashBack的number得到对象
	 * @param cashBackid
	 * @return
	 * @throws Exception
	 */
	public List<SysCashBack> queryCashBackByNumber(String cashBackNumber) throws Exception ;
	
	/**
	 * 给指定账户返现
	 * @param order
	 * @param cashBackList
	 * @param accountId
	 * @param accountUserName
	 * @return
	 */
	public String cashToAccount(SysOrder order, List<SysCashBack> cashBackList,String accountId,String accountUserName, String orderDealType);
	
}
