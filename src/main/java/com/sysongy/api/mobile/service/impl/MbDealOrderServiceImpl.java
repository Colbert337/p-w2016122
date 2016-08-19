package com.sysongy.api.mobile.service.impl;

import com.sysongy.api.mobile.service.MbDealOrderService;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.model.SysDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @FileName: DealOrderService
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.mobile.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月19日, 16:32
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class MbDealOrderServiceImpl implements MbDealOrderService{
    @Autowired
    SysDriverMapper sysDriverMapper;

    @Override
    public int transferDriverToDriver(Map<String, Object> driverMap) {
        /*driverMap.put("token",mainObj.optString("token"));
        driverMap.put("account",mainObj.optString("account"));
        driverMap.put("name",mainObj.optString("name"));
        driverMap.put("amount",mainObj.optString("amount"));
        driverMap.put("remark",mainObj.optString("remark"));
        driverMap.put("paycode",mainObj.optString("paycode"));*/
        String driverId = "";
        if(driverMap.get("token") != null && !"".equals(driverMap.get("token").toString())){
            driverId = driverMap.get("token").toString();
            /*SysDriver driver = sysDriverMapper.q*/
        }
        return 0;
    }
}
