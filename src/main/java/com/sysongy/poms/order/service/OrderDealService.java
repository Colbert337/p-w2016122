package com.sysongy.poms.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;

/**
 * 
 * @author zhangyt 2016-06-20
 *
 */
public interface OrderDealService {

    List<SysOrderDeal> queryOrderDeals(SysOrderDeal record) throws Exception;

    List<SysOrderDeal> queryOrderDealCRMs(SysOrderDeal record) throws Exception;
	
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
     * 根据订单来查询订单操作流水
     * @param record
     * @return
     */
    List<SysOrderDeal> queryOrderDealByOrderId(String orderId);

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

    String selectCashBackByOrderID(String orderId);

    /**
     * 查询运输公司充值列表
     * @param record
     * @return
     */
    PageInfo<Map<String, Object>> queryRechargeList(SysOrder record);
    /**
     * 查询运输公司充值列表
     * @param record
     * @return
     */
    List<Map<String, Object>> queryRechargeListCount(SysOrder record);
}
