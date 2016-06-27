package com.sysongy.tcms.advance.dao;

import com.sysongy.tcms.advance.model.TcFleetVehicle;

import java.util.List;
import java.util.Map;

public interface TcFleetVehicleMapper {
    /**
     * 查询车队车辆关系信息列表
     * @param tFleetVehicle
     * @return
     */
    List<TcFleetVehicle> queryFleetVehicleList(TcFleetVehicle tFleetVehicle);

    /**
     * 查询车队车辆关系信息列表
     * @param tFleetVehicle
     * @return
     */

    List<Map<String, Object>> queryFleetVehicleMapList(TcFleetVehicle tFleetVehicle);
    /**
     * 添加车队车辆关系
     * @param fleetVehicle
     * @return
     */
    int addFleetVehicle(TcFleetVehicle fleetVehicle);

    /**
     * 添加车队车辆关系列表
     * @param fleetVehicleList
     * @return
     */
    int addFleetVehicleList(List<TcFleetVehicle> fleetVehicleList);
    /**
     * 删除车队车辆关系
     * @param tFleetVehicle
     * @return
     */
    int deleteFleetVehicle(TcFleetVehicle tFleetVehicle);

}