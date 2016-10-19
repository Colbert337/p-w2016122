package com.sysongy.poms.gastation.dao;

import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.model.GsGasPrice;

import java.util.List;
import java.util.Map;

public interface GsGasPriceMapper {
    int deleteByPrimaryKey(String gsGasPriceId);

    int insert(GsGasPrice record);

    int insertSelective(GsGasPrice record);

    GsGasPrice selectByPrimaryKey(String gsGasPriceId);

    GsGasPrice queryGsPriceByStationId(String gastationId,String goodsType);

    int updateByPrimaryKeySelective(GsGasPrice record);

    int updateByPrimaryKey(GsGasPrice record);
    
    int updateSysGasStation(GsGasPrice record);

    List<GsGasPrice> queryForPage(GsGasPrice record);

    int isExists(GsGasPrice record);

    /**
     * 查询加注站价格列表
     * @param stationId 加注站编号
     * @return
     */
    List<Map<String, Object>> queryPriceList(String stationId);
    
    /**
     * 获取折扣信息
     * @param stationId 加注站编号
     * @return
     */
    List<Map<String, Object>> queryDiscount(String stationId);
}