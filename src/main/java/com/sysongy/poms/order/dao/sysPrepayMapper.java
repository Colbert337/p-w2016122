package com.sysongy.poms.order.dao;

import com.sysongy.poms.order.model.sysPrepay;

public interface sysPrepayMapper {
    int deleteByPrimaryKey(String prepayId);

    int insert(sysPrepay record);

    int insertSelective(sysPrepay record);

    sysPrepay selectByPrimaryKey(String prepayId);

    int updateByPrimaryKeySelective(sysPrepay record);

    int updateByPrimaryKey(sysPrepay record);
}