package com.sysongy.tcms.advance.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.model.TcVehicleCard;

import java.util.List;
import java.util.Map;

/**
 * @FileName: TcFleetService
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月22日, 12:10
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface TcVehicleService {

    /**
     * 查询车辆信息
     * @param tcVehicle
     * @return
     */
    TcVehicle queryVehicle(TcVehicle tcVehicle);

    /**
     * 根据车牌号查询车辆信息
     * @param tcVehicle
     * @return
     */
    TcVehicle queryVehicleByNumber(TcVehicle tcVehicle);

    /**
     * 查询车辆信息列表
     * @param tcVehicle
     * @return
     */
    PageInfo<TcVehicle> queryVehicleList(TcVehicle tcVehicle);

    /**
     * 查询车辆信息列表
     * @param tcVehicle
     * @return
     */
    PageInfo<Map<String,Object>> queryVehicleMapList(TcVehicle tcVehicle);

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

    /**
     * 添加车辆与卡关系
     * @param tcVehicleCard
     * @return
     */
    int addVehicleCard(TcVehicleCard tcVehicleCard);

    /**
     * 根据卡号查询车辆信息
     * @param cardNo
     * @return
     */
    List<TcVehicle> queryVehicleByCardNo(String cardNo);

    /**
     * 根据运输公司编号查询车辆信息
     * @param stationId
     * @return
     */
    List<TcVehicle> queryVehicleByStationId(String stationId);

    /**
     * 查询当前区域下的最大下标
     * @param provinceId
     * @return
     */
    TcVehicle queryMaxIndex(String provinceId);

    /**
     * 查询当前运输公司系统中的车牌号是否重复
     * @param stationId
     * @param platesNumber
     * @return
     */
    int queryVehicleCount(String stationId,String platesNumber);

    Integer updateAndchangeCard(String tcVehicleId, String newcardno) throws Exception;
}
