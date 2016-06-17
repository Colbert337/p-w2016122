package com.sysongy.poms.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.order.dao.SysOrderMapper;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.util.GlobalConstant;

/**
 * 
 * @author zhangyt 2016-06-16
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private SysOrderMapper sysOrderMapper;
	
	@Override
	public int deleteByPrimaryKey(String orderId) {
		return sysOrderMapper.deleteByPrimaryKey(orderId);
	}

	@Override
	public int insert(SysOrder record) {
		return sysOrderMapper.insert(record);
	}

	@Override
	public SysOrder selectByPrimaryKey(String orderId) {
		return sysOrderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public int updateByPrimaryKey(SysOrder record) {
		return sysOrderMapper.updateByPrimaryKey(record);
	}

	
	/**
     * 给司机充值
     * @param order
     * @return
     */
	@Override
	public String chargeToDriver(SysOrder order){
	   if (order ==null){
		   return GlobalConstant.OrderProcessResult.ORDER_IS_NULL;
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.CHARGE))){
		   return GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_CHARGE;
	   }
	   String operatorType = order.getOperatorType();
	   if(operatorType==null || (!operatorType.equalsIgnoreCase(GlobalConstant.OrderOperatorType.DRIVER))){
		   return GlobalConstant.OrderProcessResult.OPERATOR_TYPE_IS_NOT_DRIVER;
	   }
	   //TODO 
	   
	   return GlobalConstant.OrderProcessResult.SUCCESS;	
	}
    
    /**
     * 给加注站充值
     * @param order
     * @return
     */
	@Override
	public String chargeToTransportion(SysOrder record){
		return GlobalConstant.OrderProcessResult.SUCCESS;
	}
}
