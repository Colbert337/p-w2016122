package com.sysongy.poms.transportion.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.transportion.model.Transportion;

public interface TransportionService {
	
	public PageInfo<Transportion> queryTransportion(Transportion obj) throws Exception;
	
	public Transportion queryTransportionByPK(String transportionid) throws Exception;
	
	public String saveTransportion(Transportion obj,  String operation) throws Exception;
	
	public Integer delTransportion(String transportionid) throws Exception;
	
	public List<Transportion> getAllTransportionByArea(String areacode) throws Exception;
	
	public int updatedeposiTransportion(SysDepositLog log, String operation) throws Exception;

	public int updatedeposiTransport(Transportion transportion) throws Exception;

	public int updateDeposit(Transportion transportion) throws Exception;
	/**
	 * 给运输公司充值(无充红,不返现)
	 * @param order
	 * @return
     * @throws Exception 
	 */
	public String chargeCashToTransportion(SysOrder order) throws Exception;
	
	/**
	 * 转账的时候，扣除运输公司账户金额
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public String transferTransportionToDriverDeductCash(SysOrder order,Transportion tran) throws Exception;

	/**
	 * 运输公司消费
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public String consumeTransportion(SysOrder order) throws Exception;

	/**
	 * 修改运输公司的额度
	 * @param transportion
	 * @return
	 * @throws Exception
	 */
	public int modifyDeposit(Transportion transportion, BigDecimal increment) throws Exception ;

	public Integer alertPrepayBalance() throws Exception;
	
	public PageInfo<Map<String, Object>> transportionRechargeReport(SysDepositLog record);
	
	public PageInfo<Map<String, Object>> transportionRechargeReportTotal(SysDepositLog record);
}
