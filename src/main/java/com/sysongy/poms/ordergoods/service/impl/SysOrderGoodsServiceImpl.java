package com.sysongy.poms.ordergoods.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.ordergoods.dao.SysOrderGoodsMapper;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;
import com.sysongy.poms.ordergoods.service.SysOrderGoodsService;
import com.sysongy.util.BigDecimalArith;
import com.sysongy.util.GlobalConstant;

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
			String gsPriceId = good.getOrderGoodsId();
			
			GsGasPrice gsGasPrice = gsGasPriceService.queryGsPriceByGsPriceId(stationid, gsPriceId);

			price = gsGasPrice;
			//记录该订单的打折方式与打折金额
			if(price != null && price.getPreferential_type() != null){
				good.setPreferential_type(price.getPreferential_type());
			}

			BigDecimal tmp = good.getPrice().subtract(this.getGsGasPrice(stationid, gsPriceId,good.getPrice()));//优惠单价
			
			good.setPreferential_cash(tmp.multiply(BigDecimal.valueOf(good.getNumber())));//这里自己算
		}
		
		return goods;
	}
	
	 /**
     * 获取气品价格信息
     * @param gastationId 气站ID
     * @param gsPriceId 气品ID
     * @return 优惠后单价
     */
    public BigDecimal getGsGasPrice(String gastationId, String gsPriceId, BigDecimal cash) throws Exception{
        //获取气品价格
        GsGasPrice gsGasPrice = gsGasPriceService.queryGsPriceByGsPriceId(gastationId, gsPriceId);
        
        if(gsGasPrice != null && gsGasPrice.getPreferential_type() != null){
        	
            String preferentialType = gsGasPrice.getPreferential_type();
            
            if(GlobalConstant.PREFERENTIAL_TYPE.MINUS.equals(preferentialType)){//立减
                
                String minusMoney = gsGasPrice.getMinus_money();//获取立减金额
                
                if(StringUtils.isEmpty(minusMoney)){
                    minusMoney = "0";
                }

                //计算立减后价格
                cash = BigDecimalArith.sub(cash, new BigDecimal(minusMoney));
            }else if(GlobalConstant.PREFERENTIAL_TYPE.DISCOUNT.equals(preferentialType)){//折扣
                float fixedDiscount = gsGasPrice.getFixed_discount();//获取折扣
                cash = BigDecimalArith.mul(cash, new BigDecimal(fixedDiscount+""));
            }
        }
        return cash;
    }

}
