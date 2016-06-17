package com.sysongy.poms.order.dao;

import com.sysongy.poms.order.model.SysOrderDeal;

public interface SysOrderDealMapper {
    int deleteByPrimaryKey(String dealId);

    int insert(SysOrderDeal record);

    int insertSelective(SysOrderDeal record);

    SysOrderDeal selectByPrimaryKey(String dealId);

    int updateByPrimaryKeySelective(SysOrderDeal record);

    int updateSysOrderDeal(SysOrderDeal record);
    
    /**
     * 创建流水单编码
     * @param record
     * @return
     */
    String createDealNumber(SysOrderDeal record);
}