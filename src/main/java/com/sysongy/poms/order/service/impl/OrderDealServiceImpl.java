package com.sysongy.poms.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.order.dao.SysOrderDealMapper;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;

/**
 * 
 * @author zhangyt 2016-06-16
 *
 */
@Service
public class OrderDealServiceImpl implements OrderDealService {

	@Autowired
	private SysOrderDealMapper sysOrderDealMapper;
	
	public int deleteByPrimaryKey(String dealId){
		return sysOrderDealMapper.deleteByPrimaryKey(dealId);
	}

    public int insert(SysOrderDeal record){
    	return sysOrderDealMapper.insert(record);
    }

    public  SysOrderDeal selectByPrimaryKey(String dealId){
    	return sysOrderDealMapper.selectByPrimaryKey(dealId);
    }

    public int updateSysOrderDeal(SysOrderDeal record){
    	return sysOrderDealMapper.updateSysOrderDeal(record);
    }
    
    /**
     * 创建流水单编码
     * @param record
     * @return
     */
   public String createDealNumber(String deal_type){
	   //TODO
	   return "";
   }
   
   /**
    * 创建订单流水单--调用createOrderDealWithCashBack，穿过去参数cash_back_per="", cash_back=0
    * @param record
    * @return
    */
   public String createOrderDeal(String order_id, String deal_type, String remark, String run_success){
	   String cash_back_per ="";
	   BigDecimal cash_back = new BigDecimal("0");
	   return createOrderDealWithCashBack(order_id,deal_type,remark,cash_back_per,cash_back,run_success);
   }
   
   /**
    * 创建订单流水单
    * @param record
    * @return
    */
   public String createOrderDealWithCashBack(String order_id, String deal_type, String remark,String cash_back_per,BigDecimal cash_back, String run_success){
	   SysOrderDeal orderDeal = new SysOrderDeal();
	   String dealId = UUIDGenerator.getUUID();
	   orderDeal.setDealId(dealId);
	   orderDeal.setOrderId(order_id);
	   String dealNumber = this.createDealNumber(deal_type);
	   orderDeal.setDealNumber(dealNumber);
	   orderDeal.setDealDate(new Date());
	   orderDeal.setDealType(deal_type);
	   orderDeal.setRemark(remark);
	   orderDeal.setCashBackPer(cash_back_per);
	   orderDeal.setCashBack(cash_back);
	   orderDeal.setRunSuccess(run_success);
	   this.insert(orderDeal);
	   
	   return GlobalConstant.OrderProcessResult.SUCCESS;
   }
}