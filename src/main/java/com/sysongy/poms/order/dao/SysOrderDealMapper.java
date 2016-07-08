package com.sysongy.poms.order.dao;

import java.util.List;
import java.util.Map;

import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;

public interface SysOrderDealMapper {
    int deleteByPrimaryKey(String dealId);

    int insert(SysOrderDeal record);

    int insertSelective(SysOrderDeal record);

    SysOrderDeal selectByPrimaryKey(String dealId);

    int updateByPrimaryKeySelective(SysOrderDeal record);

    int updateSysOrderDeal(SysOrderDeal record);
    
    List<SysOrderDeal> queryOrderDealByOrderId(String orderId);

    String selectCashBackByOrderID(String orderId);

    /**
     * 查询运输公司充值列表
     * @param stationId
     * @return
     */
    List<Map<String, Object>> queryRechargeList(String stationId);
 }