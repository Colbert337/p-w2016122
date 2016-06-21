package com.sysongy.poms.order.service;

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
    String createDealNumber(SysOrder record);

    /**
     * 创建订单流水
     * @param record
     * @return
     */
    String createOrderDeal(SysOrder order, String deal_type, String run_success);

}
