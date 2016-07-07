package com.sysongy.tcms.advance.service.impl;

import com.sysongy.tcms.advance.dao.TcVehicleCardMapper;
import com.sysongy.tcms.advance.model.TcVehicleCard;
import com.sysongy.tcms.advance.service.TcVehicleCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @FileName: TcVehicleCardServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月06日, 17:29
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class TcVehicleCardServiceImpl implements TcVehicleCardService {
    @Autowired
    TcVehicleCardMapper tcVehicleCardMapper;

    @Override
    public TcVehicleCard queryTcVehicleCardByVecId(TcVehicleCard tcVehicleCard) {
        return tcVehicleCardMapper.queryTcVehicleCardByVecId(tcVehicleCard);
    }
}
