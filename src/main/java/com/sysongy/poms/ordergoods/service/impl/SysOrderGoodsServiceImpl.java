package com.sysongy.poms.ordergoods.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.ordergoods.dao.SysOrderGoodsMapper;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;
import com.sysongy.poms.ordergoods.service.SysOrderGoodsService;

@Service
public class SysOrderGoodsServiceImpl implements SysOrderGoodsService {
	
	@Autowired
	private SysOrderGoodsMapper sysOrderGoodsMapper;
	@Autowired
    private GsGasPriceService gsGasPriceService;
	
	@Override
	public List<SysOrderGoods> selectByOrderID(String orderID) {
		return sysOrderGoodsMapper.selectByOrderID(orderID);
	}
	
	public List<SysOrderGoods> setGoodsDiscountInfo(List<SysOrderGoods> goods, String stationid) throws Exception{
		for(int i=0;i<goods.size();i++){
			SysOrderGoods good = goods.get(i);
			
			GsGasPrice price = new GsGasPrice();
			price.setSysGasStationId(stationid);
			price.setGasName(good.getGoodsType());
			
			List<GsGasPrice> pricelist = gsGasPriceService.queryGsPrice(price).getList();
			
			if(pricelist.size() != 1){
				throw new Exception("找不对对应的气品价格，STATION="+stationid+"GOODSTYPE="+good.getGoodsType());
			}
			
			price = pricelist.get(0);
			//记录该订单的打折方式与打折金额
			good.setPreferential_type(price.getPreferential_type());
			good.setPreferential_cash(BigDecimal.valueOf(9999.0d));//这里自己算
		}
		
		return goods;
	}

}
