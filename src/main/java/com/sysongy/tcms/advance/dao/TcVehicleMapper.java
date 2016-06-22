package com.sysongy.tcms.advance.dao;

import com.sysongy.tcms.advance.model.TcVehicle;

public interface TcVehicleMapper {
    int deleteByPrimaryKey(String tcVehicleId);

    int insert(TcVehicle record);

    int insertSelective(TcVehicle record);

    TcVehicle selectByPrimaryKey(String tcVehicleId);

    int updateByPrimaryKeySelective(TcVehicle record);

    int updateByPrimaryKey(TcVehicle record);
}