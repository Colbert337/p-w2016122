package com.sysongy.poms.order.service;

import com.sysongy.poms.order.model.SysOrder;

/**
 * 
 * @author zhangyt 2016-06-16
 *
 */
public interface OrderService {

    int deleteByPrimaryKey(String orderId);

    int insert(SysOrder record);

    SysOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKey(SysOrder record);

    /**
     * 给司机充值
     * @param order
     * @return
     */
    String chargeToDriver(SysOrder record) throws Exception;
    
    /**
     * 给加注站充值
     * @param order
     * @return
     */
    String chargeToTransportion(SysOrder record) throws Exception;
	
    /**
     * 给加注站充值
     * 1.如果现金充值，不能超过预付款
     * 2.充值
     * 3.不返现
     * @paramorder
     * @return
     */
    String chargeToGasStation(SysOrder order) throws Exception;  	
    
    /**
	 * 运输公司给个人转账
	 * 1.扣除运输公司账户
	 * 2.个人账户增加金额
	 * 3.给运输公司返现。
	 * @return
	 */
	String transferTransportionToDriver(SysOrder order) throws Exception;
	
	/**
	 * 个人给个人转账
	 * 1.扣除个人账户credit_account
	 * 2.增加个人账户debit_account
	 * 3.不返现
	 * @return
	 */
	public String transferDriverToDriver(SysOrder order) throws Exception;
	
		
    /**
     * 判断订单能否充红
     * @param order
     * @return
     * @throws Exception
     */
    String checkCanDischarge(SysOrder order) throws Exception;

    /**
     * 充红订单
     * @param order
     * @return
     * @throws Exception
     */
    String dischargeOrder(SysOrder order) throws Exception;

    /**
     * 消费
     */
    String consumeMoney(SysOrder record) throws Exception;

    /**
     * 验证订单
     */
    String validAccount(SysOrder record) throws Exception;
}
