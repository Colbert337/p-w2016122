package com.sysongy.poms.gastation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.dao.GsGasPriceMapper;
import com.sysongy.poms.gastation.dao.ProductPriceMapper;
import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.model.ProductPrice;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.gastation.service.ProductPriceService;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ProductPriceServiceImpl implements ProductPriceService {

	@Autowired
	private ProductPriceMapper productPriceMapper;

	@Override
	public PageInfo<ProductPrice> queryProductPrice(ProductPrice record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<ProductPrice> list = productPriceMapper.queryForPage(record);
		PageInfo<ProductPrice> pageInfo = new PageInfo<ProductPrice>(list);
		return pageInfo;
	}

	@Override
	public ProductPrice queryProductPriceByPK(String productPriceID) throws Exception {
		ProductPrice productPrice =  productPriceMapper.selectByPrimaryKey(productPriceID);
		return productPrice;
	}

	@Override
	public Integer saveProductPrice(ProductPrice record, String operation) throws Exception {
		int ret = 0;
		if("insert".equals(operation)){
			record.setCreateTime(new Date());
			ret = productPriceMapper.insert(record);
		}else{
			ret = productPriceMapper.updateByPrimaryKeySelective(record);
		}
		return ret;
	}

	@Override
	public Integer delProductPrice(String productPriceID) throws Exception {
		return productPriceMapper.deleteByPrimaryKey(productPriceID);
	}

	public Integer isExists(ProductPrice obj) throws Exception {
		return productPriceMapper.isExists(obj);
	}

	public Integer updatePriceStatus(ProductPrice obj) throws Exception {
		return productPriceMapper.updatePriceStatus(obj);
	}

	public Integer compareStartTime(ProductPrice obj) throws Exception{
		return productPriceMapper.compareStartTime(obj);
	}
}
