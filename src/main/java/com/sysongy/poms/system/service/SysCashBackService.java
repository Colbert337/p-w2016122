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
	 * 向司机返现
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public String cashBackToDriver(SysOrder order) throws Exception;
	
	/**
	 * 向运输公司返现
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public String cashBackToTransportion(SysOrder order) throws Exception;
}
