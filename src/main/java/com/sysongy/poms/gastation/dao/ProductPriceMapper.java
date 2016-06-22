package com.sysongy.poms.gastation.dao;

import com.sysongy.poms.gastation.model.ProductPrice;

public interface ProductPriceMapper {

    int deleteByPrimaryKey(String id);

    int insert(ProductPrice record);

    int insertSelective(ProductPrice record);

    ProductPrice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductPrice record);

    int updateByPrimaryKey(ProductPrice record);
}