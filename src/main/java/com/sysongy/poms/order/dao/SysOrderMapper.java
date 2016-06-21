package com.sysongy.poms.order.dao;

import com.sysongy.poms.order.model.SysOrder;

public interface SysOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(SysOrder record);

    int insertSelective(SysOrder record);

    SysOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(SysOrder record);

    int updateByPrimaryKey(SysOrder record);
}