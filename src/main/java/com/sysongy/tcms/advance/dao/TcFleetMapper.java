package com.sysongy.tcms.advance.dao;

import com.sysongy.tcms.advance.model.TcFleet;

public interface TcFleetMapper {
    int deleteByPrimaryKey(String tcFleetId);

    int insert(TcFleet record);

    int insertSelective(TcFleet record);

    TcFleet selectByPrimaryKey(String tcFleetId);

    int updateByPrimaryKeySelective(TcFleet record);

    int updateByPrimaryKey(TcFleet record);
}