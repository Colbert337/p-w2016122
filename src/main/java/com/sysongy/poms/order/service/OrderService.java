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
     * 充值
     * @param record
     * @return 1--成功。 0 --失败
     */
    int charge(SysOrder record);
}
