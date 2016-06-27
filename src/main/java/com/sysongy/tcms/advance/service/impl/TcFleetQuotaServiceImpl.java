package com.sysongy.tcms.advance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.tcms.advance.dao.TcFleetQuotaMapper;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import com.sysongy.tcms.advance.service.TcFleetQuotaService;
import com.sysongy.util.BigDecimalArith;
import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: TcFleetQuotaServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月22日, 12:10
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class TcFleetQuotaServiceImpl implements TcFleetQuotaService{
    @Autowired
    TcFleetQuotaMapper tcFleetQuotaMapper;
    @Autowired
    SysUserAccountService sysUserAccountService;

    @Override
    public TcFleetQuota queryFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.queryFleetQuota(tcFleetQuota);
        }else{
            return null;
        }

    }

    @Override
    public PageInfo<TcFleetQuota> queryFleetQuotaList(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            PageHelper.startPage(GlobalConstant.PAGE_NUM,GlobalConstant.PAGE_SIZE);

            List<TcFleetQuota> FleetQuotaList = tcFleetQuotaMapper.queryFleetQuotaList(tcFleetQuota);
            PageInfo<TcFleetQuota> FleetQuotaPageInfo = new PageInfo<>(FleetQuotaList);
            return FleetQuotaPageInfo;
        }else{
            return null;
        }
    }

    @Override
    public Map<String, Object> queryFleetQuotaMapList(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            Map<String, Object> fleetQuotaMap = new HashMap<>();
            List<Map<String, Object>> fleetQuotaList = tcFleetQuotaMapper.queryFleetQuotaMapList(tcFleetQuota);
            String stationId = tcFleetQuota.getStationId();
            SysUserAccount userAccount = sysUserAccountService.queryUserAccountByStationId(stationId);
            fleetQuotaMap.put("userAccount",userAccount);
            fleetQuotaMap.put("fleetQuotaList",fleetQuotaList);

            //已分配金额
            BigDecimal yifenpei = BigDecimal.ZERO;
            List<Map<String, Object>> allList = new ArrayList<>();
            if(fleetQuotaList != null && fleetQuotaList.size() > 0){
                List<Map<String, Object>> weifenpeiList = new ArrayList<>();
                List<Map<String, Object>> yifenpeiList = new ArrayList<>();
                for (Map<String, Object> fleetQuota:fleetQuotaList){
                    if(fleetQuota.get("isAllot") != null && fleetQuota.get("isAllot").equals("1")){
                        BigDecimal quota = new BigDecimal(fleetQuota.get("quota").toString());
                        yifenpei = BigDecimalArith.add(yifenpei,quota);
                        yifenpeiList.add(fleetQuota);
                    }else{
                        weifenpeiList.add(fleetQuota);
                    }
                }

                //未分配金额
                BigDecimal weifenpeiVal = BigDecimal.ZERO;
                weifenpeiVal = BigDecimalArith.sub(new BigDecimal(userAccount.getAccountBalance()),yifenpei);
                if(weifenpeiList != null && weifenpeiList.size() > 0){
                    for(Map<String, Object> weifenpei:weifenpeiList){
                        weifenpei.put("quota",weifenpeiVal.toString());
                    }
                }
                allList.addAll(weifenpeiList);
                allList.addAll(yifenpeiList);
            }
            fleetQuotaMap.put("fleetQuotaList",allList);
            return fleetQuotaMap;
        }else{
            return null;
        }
    }

    @Override
    public int addFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.addFleetQuota(tcFleetQuota);
        }else{
            return 0;
        }
    }

    @Override
    public int deleteFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.deleteFleetQuota(tcFleetQuota);
        }else{
            return 0;
        }
    }

    @Override
    public int updateFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.updateFleetQuota(tcFleetQuota);
        }else{
            return 0;
        }
    }
}
