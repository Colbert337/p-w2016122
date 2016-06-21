package com.sysongy.tcms.advance.dao;

import com.sysongy.tcms.advance.model.TcFleetVehicle;

public interface TcFleetVehicleMapper {
    int deleteByPrimaryKey(String tcFleetVehicleId);

    int insert(TcFleetVehicle record);

    int insertSelective(TcFleetVehicle record);

    TcFleetVehicle selectByPrimaryKey(String tcFleetVehicleId);

    int updateByPrimaryKeySelective(TcFleetVehicle record);

    int updateByPrimaryKey(TcFleetVehicle record);
}