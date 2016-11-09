package com.sysongy.poms.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;
import com.sysongy.poms.system.model.SysCashBack;

public interface SysCashBackService {
	
	public PageInfo<SysCashBack> queryCashBack(SysCashBack obj) throws Exception;

	/**
	 * 查询司机返现规则列表
	 * @return
	 * @throws Exception
     */
	public List<Map<String, Object>> queryCashBackList() throws Exception;

	public PageInfo<SysCashBack> queryCashBackForCRM(SysCashBack obj) throws Exception;
	
	public SysCashBack queryCashBackByPK(String cashBackid) throws Exception;
	
	public String saveCashBack(SysCashBack obj,  String operation) throws Exception;
	
	public Integer delCashBack(String sysCashBackId) throws Exception;
	
	public Integer delCashBack(String sysCashBackNo, String level) throws Exception;
	public List<SysCashBack> queryForBreak(String cashbackno);
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
	public String cashToAccount(SysOrder order, List<SysCashBack> cashBackList,String accountId,String accountUserName, String orderDealType) throws Exception;
	
	/**
	 * 冲红返现给账户
	 * 算法： 读出sysOrderDeal对象里面的cashback，判断run_success字段，如果是成功，则冲红，否则不执行。
	 * @param order 冲红订单对象
	 * @param cashBackRecord 订单处理流程对象
	 * @param accountId
	 * @param accountUserName
	 * @param orderDealType 订单处理类型
	 * @return
	 */
	public String disCashBackToAccount(SysOrder order, SysOrderDeal orderDealRecord,String accountId,String accountUserName, String orderDealType) throws Exception;
	
	public List<SysCashBack> gainProp(@RequestParam String sys_cash_back_no, @RequestParam String level);
	
	/**
	 * 最高返现规则列表
	 */
	public List<SysCashBack> queryMaxCashBack()throws Exception;
}
