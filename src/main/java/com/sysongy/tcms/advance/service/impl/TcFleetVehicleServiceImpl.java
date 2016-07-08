package com.sysongy.tcms.advance.service.impl;

import com.sysongy.tcms.advance.dao.TcFleetVehicleMapper;
import com.sysongy.tcms.advance.dao.TcVehicleMapper;
import com.sysongy.tcms.advance.model.TcFleetVehicle;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.tcms.advance.service.TcFleetVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @FileName: TcFleetServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月22日, 12:10
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class TcFleetVehicleServiceImpl implements TcFleetVehicleService{

    @Autowired
    TcFleetVehicleMapper tcFleetVehicleMapper;
    @Autowired
    TcVehicleMapper tcVehicleMapper;

    @Override
    public List<TcFleetVehicle> queryFleetVehicleList(TcFleetVehicle tFleetVehicle) {
        return tcFleetVehicleMapper.queryFleetVehicleList(tFleetVehicle);
    }

    /**
     * 查询车队车辆关系列表
     * @param tFleetVehicle
     * @return
     */
    @Override
    public List<Map<String, Object>> queryFleetVehicleMapList(TcFleetVehicle tFleetVehicle) {
        //最终结果集
        List<Map<String, Object>> fleetVehicleList = new ArrayList<>();
        //查询当前车队关联车辆列表
        List<Map<String, Object>> fleetVehicleMapList = tcFleetVehicleMapper.queryFleetVehicleMapList(tFleetVehicle);
        TcVehicle tcVehicle = new TcVehicle();
        tcVehicle.setStationId(tFleetVehicle.getStationId());

        //查询当前运输公司所有车辆列表
        List<Map<String, Object>> vehicleMapList = tcVehicleMapper.queryVehicleMapArray(tcVehicle);

        if(vehicleMapList != null && vehicleMapList.size() > 0){
            for (Map<String, Object> vehicleMap:vehicleMapList) {
                int count = 0;
                if(fleetVehicleMapList != null && fleetVehicleMapList.size() > 0 ){
                    for (Map<String, Object> fleetVehicleMap:fleetVehicleMapList) {
                        if(vehicleMap.get("tcVehicleId").equals(fleetVehicleMap.get("tcVehicleId"))){
                            count++;
                        }
                    }
                }
                if(count > 0){
                    vehicleMap.put("selected","true");
                    fleetVehicleList.add(vehicleMap);
                }else{
                    if(vehicleMap.get("isAllot") != null && vehicleMap.get("isAllot").toString().equals("0")){
                        vehicleMap.put("selected","false");
                        fleetVehicleList.add(vehicleMap);
                    }
                }

            }
        }
        return fleetVehicleList;
    }

    @Override
    public int addFleetVehicleList(List<TcFleetVehicle> fleetVehicleList, String fleetId, String stationId) throws Exception {
        int result = 0;
        TcFleetVehicle fleetVehicle = new TcFleetVehicle();
        fleetVehicle.setTcFleetId(fleetId);
        try {
            //删除当前车队车辆关系
            tcFleetVehicleMapper.deleteFleetVehicle(fleetVehicle);
            //添加车队车辆关系
            result = tcFleetVehicleMapper.addFleetVehicleList(fleetVehicleList);
            //更新状态
            //查询当前运输公司未分配车辆
            List<TcVehicle> vehicleList = new ArrayList<>();
            fleetVehicle.setStationId(stationId);
            List<TcVehicle> tcVehicleList = tcVehicleMapper.queryVehicleByStationId(stationId);
            if(tcVehicleList != null && tcVehicleList.size() > 0){
                for (TcVehicle vehicleTemp:tcVehicleList) {
                    TcVehicle vehicle = new TcVehicle();
                    vehicle.setTcVehicleId(vehicleTemp.getTcVehicleId());
                    vehicle.setIsAllot(0);//是否分配 0 不分配 1 分配
                    vehicleList.add(vehicle);
                    tcVehicleMapper.updateVehicle(vehicle);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteFleetVehicle(TcFleetVehicle tFleetVehicle) {
        return tcFleetVehicleMapper.deleteFleetVehicle(tFleetVehicle);
    }

}
