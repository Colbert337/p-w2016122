package com.sysongy.tcms.advance.dao;

import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.model.TcVehicle;

import java.util.List;
import java.util.Map;

public interface TcVehicleMapper {

    /*********************************基础方法 start*************************************/

    /**
     * 查询车辆信息
     * @param tcVehicle
     * @return
     */
    TcVehicle queryVehicle(TcVehicle tcVehicle);

    /**
     * 查询车辆信息列表
     * @param tcVehicle
     * @return
     */
    List<TcVehicle> queryVehicleList(TcVehicle tcVehicle);

    /**
     * 查询车辆信息列表
     * @param tcVehicle
     * @return
     */
    List<Map<String,Object>> queryVehicleMapList(TcVehicle tcVehicle);

    /**
     * 查询车辆信息列表
     * @param tcVehicle
     * @return
     */
    List<Map<String,Object>> queryVehicleMapArray(TcVehicle tcVehicle);

    /**
     * 添加车辆
     * @param tcVehicle
     * @return
     */
    int addVehicle(TcVehicle tcVehicle);

    /**
     * 批量添加车辆
     * @param tcVehicleList
     * @return
     */
    int addVehicleList(List<TcVehicle> tcVehicleList);

    /**
     * 删除车辆
     * @param tcVehicle
     * @return
     */
    int deleteVehicle(TcVehicle tcVehicle);


    /**
     * 修改车辆信息
     * @param tcVehicle
     * @return
     */
    int updateVehicle(TcVehicle tcVehicle);

    /*********************************基础方法 end*************************************/

}