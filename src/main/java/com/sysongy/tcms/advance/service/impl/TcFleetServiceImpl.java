package com.sysongy.tcms.advance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.dao.TcFleetMapper;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.util.BigDecimalArith;
import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
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

    @Override
    public int updateFleetMap(Map<String, Object> fleetMap) {
        return tcFleetMapper.updateFleetMap(fleetMap);
    }

    /**
     * 修改车队额度
     * @param transportionId 运输公司ID
     * @param fleetId 车队ID
     * @param cash 金额（正负）
     * @return
     */
    @Override
    public synchronized int updateFleetQuota(String transportionId, String fleetId, BigDecimal cash) throws Exception{
        int result = 0;
        if(transportionId != null && !"".equals(transportionId) && fleetId != null && !"".equals(fleetId)){
            TcFleet tcFleetTemp = new TcFleet();
            tcFleetTemp.setTcFleetId(fleetId);
            try {
                TcFleet tcFleet = tcFleetMapper.queryFleet(tcFleetTemp);
                BigDecimal quota = new BigDecimal(BigInteger.ZERO);
                if(tcFleet != null){
                    quota = tcFleet.getQuota();
                    BigDecimal newQuota = BigDecimalArith.add(quota,cash);
                    int resultVal = newQuota.compareTo(BigDecimal.ZERO);
                    if(resultVal >= 0){
                        result = tcFleetMapper.updateFleetQuota(transportionId,fleetId,newQuota);
                    }else{
                        throw new Exception("计算额度结果为负数！");
                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return result;
    }

    @Override
    public TcFleet queryFleetByName(String stationId, String fleetName) {
        return tcFleetMapper.queryFleetByName(stationId,fleetName);
    }

    /**
     * 根据车辆编号查询车队信息
     * @param stationId 站点编号
     * @param vehicleId 车辆编号
     * @return
     */
    @Override
    public List<TcFleet> queryFleetByVehicleId(String stationId, String vehicleId) {
        return tcFleetMapper.queryFleetByVehicleId(stationId,vehicleId);
    }
}
