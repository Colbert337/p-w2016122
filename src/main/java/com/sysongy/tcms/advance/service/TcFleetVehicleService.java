package com.sysongy.tcms.advance.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcFleetVehicle;

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
public interface TcFleetVehicleService {
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
    List<Map<String,Object>> queryFleetVehicleMapList(TcFleetVehicle tFleetVehicle);

    /**
     * 添加车队车辆关系
     * @param fleetVehicleList
     * @return
     */
    int addFleetVehicleList(List<TcFleetVehicle> fleetVehicleList, String fleetId);

    /**
     * 删除车队车辆关系
     * @param tFleetVehicle
     * @return
     */
    int deleteFleetVehicle(TcFleetVehicle tFleetVehicle);

}
