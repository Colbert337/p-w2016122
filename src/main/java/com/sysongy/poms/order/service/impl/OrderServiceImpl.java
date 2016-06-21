package com.sysongy.poms.order.service.impl;

import com.sysongy.poms.permi.dao.SysRoleFunctionMapper;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.dao.SysOrderMapper;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.GlobalConstant.OrderOperatorType;

/**
 * 
 * @author zhangyt 2016-06-16
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private SysOrderMapper sysOrderMapper;

	@Autowired
	private DriverService driverService;

	@Autowired
	SysUserAccountMapper sysUserAccountMapper;
	
	@Autowired
	private SysCashBackService sysCashBackService;
	
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
	   //TODO 充值流程：1.充值，2.返现
	   try{
		   //1.首先给司机充值
		   String success_charge = driverService.chargeCashToDriver(order);
		   //2.调用返现--在返现里面判断是否首次返现，则增加调用首次返现规则，然后再继续调用返现
		   String success_cashback = sysCashBackService.cashBackToDriver(order);
		   
	   }catch(Exception e){
		   
	   }
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

	@Override
	public String consumeMoney(SysOrder record){
		String strRet = GlobalConstant.OrderProcessResult.SUCCESS;


		return strRet;
	}


	private String validAccount(){
		String strRet = GlobalConstant.OrderProcessResult.SUCCESS;



		return strRet;
	}
}
