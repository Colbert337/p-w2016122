package com.sysongy.poms.gastation.dao;

import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.model.ProductPrice;

import java.util.List;

public interface ProductPriceMapper {

    int deleteByPrimaryKey(String id);

    int insert(ProductPrice record);

    int insertSelective(ProductPrice record);

    ProductPrice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductPrice record);

    int updateByPrimaryKey(ProductPrice record);

    List<ProductPrice> queryForPage(ProductPrice record);

    int isExists(ProductPrice record);

    int compareStartTime(ProductPrice obj);

    int updatePriceStatus(ProductPrice obj);
}