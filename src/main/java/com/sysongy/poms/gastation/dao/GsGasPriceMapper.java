package com.sysongy.poms.gastation.dao;

import com.sysongy.poms.gastation.model.GsGasPrice;

public interface GsGasPriceMapper {
    int deleteByPrimaryKey(String gsGasPriceId);

    int insert(GsGasPrice record);

    int insertSelective(GsGasPrice record);

    GsGasPrice selectByPrimaryKey(String gsGasPriceId);

    int updateByPrimaryKeySelective(GsGasPrice record);

    int updateByPrimaryKey(GsGasPrice record);
}