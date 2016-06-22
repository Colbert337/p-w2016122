package com.sysongy.tcms.advance.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.model.TcVehicle;
import org.omg.CORBA.Object;

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

}
