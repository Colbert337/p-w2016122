package com.sysongy.tcms.advance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.dao.TcVehicleMapper;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.tcms.advance.service.TcVehicleService;
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
public class TcVehicleServiceImpl implements TcVehicleService{
    @Autowired
    TcVehicleMapper tcVehicleMapper;

    @Override
    public TcVehicle queryVehicle(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            return tcVehicleMapper.queryVehicle(tcVehicle);
        }else{
            return null;
        }

    }

    @Override
    public PageInfo<TcVehicle> queryVehicleList(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            PageHelper.startPage(tcVehicle.getPageNum(),tcVehicle.getPageSize());

            List<TcVehicle> vehicleList = tcVehicleMapper.queryVehicleList(tcVehicle);
            PageInfo<TcVehicle> vehiclePageInfo = new PageInfo<>(vehicleList);
            return vehiclePageInfo;
        }else{
            return null;
        }
    }

    @Override
    public PageInfo<Map<String, Object>> queryVehicleMapList(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            PageHelper.startPage(tcVehicle.getPageNum(),tcVehicle.getPageSize());

            List<Map<String, Object>> vehicleList = tcVehicleMapper.queryVehicleMapList(tcVehicle);
            PageInfo<Map<String, Object>> vehiclePageInfo = new PageInfo<>(vehicleList);
            return vehiclePageInfo;
        }else{
            return null;
        }
    }

    @Override
    public int addVehicle(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            return tcVehicleMapper.addVehicle(tcVehicle);
        }else{
            return 0;
        }
    }

    @Override
    public int addVehicleList(List<TcVehicle> tcVehicleList) {
        if(tcVehicleList != null && tcVehicleList.size() > 0){
            return tcVehicleMapper.addVehicleList(tcVehicleList);
        }else{
            return 0;
        }
    }

    @Override
    public int deleteVehicle(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            return tcVehicleMapper.deleteVehicle(tcVehicle);
        }else{
            return 0;
        }
    }

    @Override
    public int updateVehicle(TcVehicle tcVehicle) {
        if(tcVehicle != null){
            return tcVehicleMapper.updateVehicle(tcVehicle);
        }else{
            return 0;
        }
    }
}
