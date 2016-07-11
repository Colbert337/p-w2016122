package com.sysongy.tcms.advance.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.sysongy.tcms.advance.model.TcFleet;

public interface TcFleetMapper {

    /*********************************基础方法 start*************************************/

    /**
     * 查询车队信息
     *
     * @param tcFleet
     * @return
     */
    TcFleet queryFleet(TcFleet tcFleet);

    /**
     * 查询车队信息列表
     *
     * @param tcFleet
     * @return
     */
    List<TcFleet> queryFleetList(TcFleet tcFleet);

    /**
     * 查询车队信息列表
     *
     * @param tcFleet
     * @return
     */
    List<Map<String, Object>> queryFleetMapList(TcFleet tcFleet);

    /**
     * 添加车队
     *
     * @param tcFleet
     * @return
     */
    int addFleet(TcFleet tcFleet);

    /**
     * 删除车队
     *
     * @param tcFleet
     * @return
     */
    int deleteFleet(TcFleet tcFleet);


    /**
     * 修改车队信息
     *
     * @param tcFleet
     * @return
     */
    int updateFleet(TcFleet tcFleet);

    /**
     * 修改分配资金信息
     * @param fleetMap
     * @return
     */
    int updateFleetMap(Map<String, Object> fleetMap);

/*********************************基础方法 end*************************************/
    /**
     * 根据车队查询车辆列表
     * @param fleet
     * @return
     */
    List<Map<String, Object>> queryFleetListByType(TcFleet fleet);
    /**
     * 修改车队额度
     * @return
     */
    int updateFleetQuota(String transportionId, String fleet, BigDecimal cash);
    /**
     * 根据用户名称查询车队信息
     *
     * @param fleetName
     * @return
     */
    TcFleet queryFleetByName(String stationId, String fleetName);

    /**
     * 根据车辆编号查询车队信息
     * @param stationId 站点编号
     * @param vehicleId 车辆编号
     * @return
     */
    List<TcFleet> queryFleetByVehicleId(String stationId,String vehicleId);

    /**
     * 查询当前区域下的最大下标
     * @param provinceId
     * @return
     */
    TcFleet queryMaxIndex(String provinceId);

    TcFleet selectByFleetId(String tcFleetId);
}