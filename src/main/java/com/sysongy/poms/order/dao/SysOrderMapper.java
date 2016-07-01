package com.sysongy.poms.order.dao;

import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.order.model.SysOrder;

import java.util.List;

public interface SysOrderMapper {

    List<SysOrder> queryForPage(SysOrder record);

    int deleteByPrimaryKey(String orderId);

    int insert(SysOrder record);

    int insertSelective(SysOrder record);

    SysOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(SysOrder record);

    int updateByPrimaryKey(SysOrder record);
}