package com.sysongy.tcms.advance.dao;

import com.sysongy.tcms.advance.model.TcVehicleCard;

public interface TcVehicleCardMapper {
    int deleteByPrimaryKey(String tcVehicleCard);

    int insert(TcVehicleCard record);

    /**
     * 添加车辆与卡关系
     * @param tcVehicleCard
     * @return
     */
    int addVehicleCard(TcVehicleCard tcVehicleCard);

    /**
     * 根据车辆ID查询车辆卡关系
     * @param tcVehicleCard
     * @return
     */
    TcVehicleCard queryTcVehicleCardByVecId(TcVehicleCard tcVehicleCard);

    int updateByPrimaryKeySelective(TcVehicleCard record);

    int updateByPrimaryKey(TcVehicleCard record);
}