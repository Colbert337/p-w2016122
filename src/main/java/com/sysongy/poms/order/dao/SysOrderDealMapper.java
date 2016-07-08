package com.sysongy.poms.order.dao;

import java.util.List;

import com.sysongy.poms.card.model.GasCard;
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

    List<SysOrderDeal> queryForPage(SysOrderDeal record);
 }