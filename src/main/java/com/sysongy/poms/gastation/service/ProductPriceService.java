package com.sysongy.poms.gastation.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.model.ProductPrice;

public interface ProductPriceService {

    public PageInfo<ProductPrice> queryProductPrice(ProductPrice record) throws Exception;

    public ProductPrice queryProductPriceByPK(String productPriceID) throws Exception;

    public Integer saveProductPrice(ProductPrice record, String operation) throws Exception;

    public Integer delProductPrice(String productPriceID) throws Exception;

    public Integer isExists(ProductPrice obj) throws Exception;

    public Integer updatePriceStatus(ProductPrice obj) throws Exception;

    public Integer compareStartTime(ProductPrice obj) throws Exception;
}
