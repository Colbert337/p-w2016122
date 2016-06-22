package com.sysongy.poms.order.service;

import java.math.BigDecimal;

import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;

/**
 * 
 * @author zhangyt 2016-06-20
 *
 */
public interface OrderDealService {
	
	
	int deleteByPrimaryKey(String dealId);

    int insert(SysOrderDeal record);

    SysOrderDeal selectByPrimaryKey(String dealId);

    int updateSysOrderDeal(SysOrderDeal record);
    
    /**
     * 创建流水单编码
     * @param record
     * @return
     */
    String createDealNumber(String deal_type);

    /**
     * 创建订单流水
     * @param record
     * @return
     */
    String createOrderDeal(String orderId, String deal_type,String remark, String run_success);
    
    /**
     * 使用返现创建订单流水单
     * @param record
     * @return
     */
    public String createOrderDealWithCashBack(String orderId, String deal_type, String remark,String cash_back_per,BigDecimal cash_back, String run_success);
 	

}