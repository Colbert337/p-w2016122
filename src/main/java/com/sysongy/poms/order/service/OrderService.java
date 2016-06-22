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
