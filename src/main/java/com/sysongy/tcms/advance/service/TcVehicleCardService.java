package com.sysongy.tcms.advance.service;

import com.sysongy.tcms.advance.dao.TcVehicleCardMapper;
import com.sysongy.tcms.advance.model.TcVehicleCard;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @FileName: TcVehicleCardService
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月06日, 17:29
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface TcVehicleCardService {

    /**
     * 根据车辆ID查询车辆卡关系
     * @param tcVehicleCard
     * @return
     */
    TcVehicleCard queryTcVehicleCardByVecId(TcVehicleCard tcVehicleCard);
}
