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
                }else{
                    vehicleMap.put("selected","false");
                }
                fleetVehicleList.add(vehicleMap);

            }
        }
        return fleetVehicleList;
    }

    @Override
    public int addFleetVehicleList(List<TcFleetVehicle> fleetVehicleList, String fleetId) {
        int result = 0;
        TcFleetVehicle fleetVehicle = new TcFleetVehicle();
        fleetVehicle.setTcFleetId(fleetId);
        //删除当前车队车辆关系
        tcFleetVehicleMapper.deleteFleetVehicle(fleetVehicle);
        //添加车队车辆关系
        result = tcFleetVehicleMapper.addFleetVehicleList(fleetVehicleList);
        return result;
    }

    @Override
    public int deleteFleetVehicle(TcFleetVehicle tFleetVehicle) {
        return tcFleetVehicleMapper.deleteFleetVehicle(tFleetVehicle);
    }
}
