package com.sysongy.poms.gastation.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.model.GsGasPrice;

import java.util.List;
import java.util.Map;

public interface GsGasPriceService {

    public PageInfo<GsGasPrice> queryGsPrice(GsGasPrice gsGasPrice) throws Exception;

    public List<GsGasPrice> queryGsPriceList(GsGasPrice gsGasPrice) throws Exception;

    public GsGasPrice queryGsPriceByPK(String gsGasPriceID) throws Exception;

    /**
     * 根据气站ID和商品类型查询气品价格信息
     * @param goodsType 气站ID
     * @return
     * @throws Exception
     */
    public GsGasPrice queryGsPriceByStationId(String gastationId,String goodsType) throws Exception;

    public GsGasPrice queryGsPriceByGsPriceId(String gastationId,String gsPriceId) throws Exception;

    public Integer saveGsPrice(GsGasPrice gsPriceID, String operation) throws Exception;

    public Integer delGsPrice(String gsPriceID) throws Exception;

    public Integer isExists(GsGasPrice obj) throws Exception;

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
    
    public GsGasPrice queryGsPrice(String gastationId,String gasPrice) throws Exception;
}
