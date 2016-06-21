package com.sysongy.poms.ordergoods.dao;

import com.sysongy.poms.ordergoods.model.SysOrderGoods;

public interface SysOrderGoodsMapper {

    int deleteByPrimaryKey(String orderGoodsId);

    int insert(SysOrderGoods record);

    int insertSelective(SysOrderGoods record);

    SysOrderGoods selectByPrimaryKey(String orderGoodsId);

    int updateByPrimaryKeySelective(SysOrderGoods record);

    int updateByPrimaryKey(SysOrderGoods record);
}