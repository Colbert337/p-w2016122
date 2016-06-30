package com.sysongy.tcms.advance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.dao.TcFleetMapper;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class TcFleetServiceImpl implements TcFleetService{

    @Autowired
    TcFleetMapper tcFleetMapper;

    @Override
    public TcFleet queryFleet(TcFleet tcFleet) {
        if(tcFleet != null){
            return tcFleetMapper.queryFleet(tcFleet);
        }else{
            return null;
        }

    }

    @Override
    public PageInfo<TcFleet> queryFleetList(TcFleet tcFleet) {
        if(tcFleet != null){
            PageHelper.startPage(tcFleet.getPageNum(),tcFleet.getPageSize());

            List<TcFleet> FleetList = tcFleetMapper.queryFleetList(tcFleet);
            PageInfo<TcFleet> FleetPageInfo = new PageInfo<>(FleetList);
            return FleetPageInfo;
        }else{
            return null;
        }
    }

    @Override
    public PageInfo<Map<String, Object>> queryFleetMapList(TcFleet tcFleet) {
        if(tcFleet != null){
            PageHelper.startPage(tcFleet.getPageNum(),tcFleet.getPageSize());

            List<Map<String, Object>> fleetList = tcFleetMapper.queryFleetMapList(tcFleet);
            if(fleetList != null && fleetList.size() > 0){
                for (Map<String, Object> fleetMap:fleetList) {
                    TcFleet fleetTemp = new TcFleet();
                    fleetTemp.setStationId(tcFleet.getStationId());
                    fleetTemp.setTcFleetId(fleetMap.get("tcFleetId").toString());
                    List<Map<String, Object>> vehicleList = tcFleetMapper.queryFleetListByType(fleetTemp);

                    fleetMap.put("vCount",vehicleList.size());
                }
            }
            PageInfo<Map<String, Object>> fleetPageInfo = new PageInfo<>(fleetList);
            return fleetPageInfo;
        }else{
            return null;
        }
    }

    @Override
    public int addFleet(TcFleet tcFleet) {
        if(tcFleet != null){
            return tcFleetMapper.addFleet(tcFleet);
        }else{
            return 0;
        }
    }

    @Override
    public int deleteFleet(TcFleet tcFleet) {
        if(tcFleet != null){
            return tcFleetMapper.deleteFleet(tcFleet);
        }else{
            return 0;
        }
    }

    @Override
    public int updateFleet(TcFleet tcFleet) {
        if(tcFleet != null){
            return tcFleetMapper.updateFleet(tcFleet);
        }else{
            return 0;
        }
    }
}
