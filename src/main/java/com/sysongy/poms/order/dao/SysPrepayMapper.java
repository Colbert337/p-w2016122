package com.sysongy.poms.order.dao;

import com.sysongy.poms.order.model.SysPrepay;

public interface SysPrepayMapper {
    int deleteByPrimaryKey(String prepayId);

    int insert(SysPrepay record);

    int insertSelective(SysPrepay record);

    SysPrepay selectByPrimaryKey(String prepayId);

    int updateByPrimaryKeySelective(SysPrepay record);

    int updateByPrimaryKey(SysPrepay record);
}