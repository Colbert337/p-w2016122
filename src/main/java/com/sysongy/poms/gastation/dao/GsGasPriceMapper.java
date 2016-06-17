package com.sysongy.poms.gastation.dao;

import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.model.GsGasPrice;

import java.util.List;

public interface GsGasPriceMapper {
    int deleteByPrimaryKey(String gsGasPriceId);

    int insert(GsGasPrice record);

    int insertSelective(GsGasPrice record);

    GsGasPrice selectByPrimaryKey(String gsGasPriceId);

    int updateByPrimaryKeySelective(GsGasPrice record);

    int updateByPrimaryKey(GsGasPrice record);

    List<GsGasPrice> queryForPage(GsGasPrice record);
}