package com.sysongy.api.client.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.UUIDGenerator;

@Controller
@RequestMapping("/testOrder")
public class TestOrderContoller {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private TransportionService transportionService;
    
    @Autowired
    private TcFleetService tcFleetService;
    
    
    
    @RequestMapping(value = {"/chargeToDriver"})
    @ResponseBody
    public AjaxJson testChargeToDriver(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
       //测试充值：
    	try{
    		SysOrder order = new SysOrder();
        	order.setOrderId(UUIDGenerator.getUUID());
        	String order_type = GlobalConstant.OrderType.CHARGE_TO_DRIVER;
        	String order_number = orderService.createOrderNumber(order_type);
        	order.setOrderNumber(order_number);
        	order.setOrderType(order_type);
        	order.setOrderDate(new Date());
        	order.setCash(new BigDecimal("166.66"));
        	order.setDebitAccount("748f08a4e31545c2b6de454d3deb0979");
        	order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_CASH_CHARGE);
        	order.setChannel("亭口加注站");
        	order.setChannelNumber("GS12000003");
        	order.setOperator("14e9ef72ce5c424dbcc36859d6618a6b");
        	order.setOperatorSourceId("GS12000003");
        	order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
        	order.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
        	order.setIs_discharge(GlobalConstant.ORDER_ISCHARGE_NO);
    		orderService.insert(order);
    		orderService.chargeToDriver(order);
    	}catch(Exception e){
    		System.out.println("Found exception:"+e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return ajaxJson;
    }
    
    
    @RequestMapping(value = {"/discharge"})
    @ResponseBody
    public AjaxJson testDischarge(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
       //测试充红，要通过原始订单，创建充红订单，然后调用discharge_order方法：
    	try{
        	SysOrder originalOrder = orderService.selectByPrimaryKey("63db81c4aa314e0aa444594ef907fbf8");
        	boolean bl = orderService.checkCanDischarge(originalOrder);
        	System.out.println("能否充红："+bl);
        	if(bl){
	        	SysOrder dischargeOrder = orderService.createDischargeOrderByOriginalOrder(originalOrder, "006648b1e6c1469fa4fff0a0ad86ac06", "没啥原因，就想撤销。");
	        	//注意要保存充红订单
	        	orderService.insert(dischargeOrder);
	        	orderService.dischargeOrder(originalOrder, dischargeOrder);
        	}
    		//orderService.chargeToDriver(order);
    	}catch(Exception e){
    		System.out.println("Found exception:"+e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return ajaxJson;
    }
    
    @RequestMapping(value = {"/chargeToTransportion"})
    @ResponseBody
    public AjaxJson testChargeToTransportion(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
       //测试充值：
    	try{
    		SysOrder order = new SysOrder();
        	order.setOrderId(UUIDGenerator.getUUID());
        	String order_type = GlobalConstant.OrderType.CHARGE_TO_TRANSPORTION;
        	String order_number = orderService.createOrderNumber(order_type);
        	order.setOrderNumber(order_number);
        	order.setOrderType(order_type);
        	order.setOrderDate(new Date());
        	order.setCash(new BigDecimal("2000"));
        	order.setDebitAccount("TC37000001");
        	order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_CASH_CHARGE);
        	order.setChannel("本公司");
        	order.setChannelNumber("123456789");
        	order.setOperator("14e9ef72ce5c424dbcc36859d6618a6b");
        	//order.setOperatorSourceId();
        	//order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
        	order.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.TRANSPORTION);
        	order.setIs_discharge(GlobalConstant.ORDER_ISCHARGE_NO);
    		orderService.insert(order);
    		orderService.chargeToTransportion(order);
    	}catch(Exception e){
    		System.out.println("Found exception:"+e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return ajaxJson;
    }
    
    
    @RequestMapping(value = {"/chargeToGasStation"})
    @ResponseBody
    public AjaxJson testchargeToGasStation(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
       //测试充值：
    	try{
    		SysOrder order = new SysOrder();
        	order.setOrderId(UUIDGenerator.getUUID());
        	String order_type = GlobalConstant.OrderType.CHARGE_TO_GASTATION;
        	String order_number = orderService.createOrderNumber(order_type);
        	order.setOrderNumber(order_number);
        	order.setOrderType(order_type);
        	order.setOrderDate(new Date());
        	order.setCash(new BigDecimal("30000"));
        	order.setDebitAccount("GS12000003");
        	order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_POS_CHARGE);
        	order.setChannel("本公司");
        	order.setChannelNumber("123456789");
        	order.setOperator("14e9ef72ce5c424dbcc36859d6618a6b");
        	//order.setOperatorSourceId();
        	//order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
        	order.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.GASTATION);
        	order.setIs_discharge(GlobalConstant.ORDER_ISCHARGE_NO);
    		orderService.insert(order);
    		orderService.chargeToGasStation(order);
    	}catch(Exception e){
    		System.out.println("Found exception:"+e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return ajaxJson;
    }
    
    @RequestMapping(value = {"/consumeByDriver"})
    @ResponseBody
    public AjaxJson testConsumeByDriver(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
       //测试充值：
    	try{
    		SysOrder order = new SysOrder();
        	order.setOrderId(UUIDGenerator.getUUID());
        	String order_type = GlobalConstant.OrderType.CONSUME_BY_DRIVER;
        	String order_number = orderService.createOrderNumber(order_type);
        	order.setOrderNumber(order_number);
        	order.setOrderType(order_type);
        	order.setOrderDate(new Date());
        	order.setCash(new BigDecimal("86.88"));
        	order.setCreditAccount("748f08a4e31545c2b6de454d3deb0979");
        	//order.setDebitAccount("GS12000003");
        	//order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_POS_CHARGE);
        	order.setChannel("亭口加注站");
        	order.setChannelNumber("TK456123");
        	order.setOperator("3f974dee326248ec96464874bc04129e");
        	//order.setOperatorSourceId();
        	//order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
        	order.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
        	order.setIs_discharge(GlobalConstant.ORDER_ISCHARGE_NO);
    		orderService.insert(order);
    		orderService.consumeByDriver(order);
    	}catch(Exception e){
    		System.out.println("Found exception:"+e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return ajaxJson;
    }
    
    @RequestMapping(value = {"/consumeByTransportion"})
    @ResponseBody
    public AjaxJson testConsumeByTransportion(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
       //测试充值：
    	try{
    		String  tranId ="TC37000001";
    		//String  tcfleetId ="f29f828a74d14c50b8409b9f94f83da3"; //张三车队有分配额度
    		String  tcfleetId ="7731c97fad2e49a98e6c3a4939555f77";
    		SysOrder order = new SysOrder();
        	order.setOrderId(UUIDGenerator.getUUID());
        	String order_type = GlobalConstant.OrderType.CONSUME_BY_TRANSPORTION;
        	String order_number = orderService.createOrderNumber(order_type);
        	order.setOrderNumber(order_number);
        	order.setOrderType(order_type);
        	order.setOrderDate(new Date());
        	order.setCash(new BigDecimal("188.86"));
        	order.setCreditAccount(tranId);
        	//order.setDebitAccount("GS12000003");
        	//order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_POS_CHARGE);
        	order.setChannel("亭口加注站");
        	order.setChannelNumber("TK456123");
        	order.setOperator("3f974dee326248ec96464874bc04129e");
        	//order.setOperatorSourceId();
        	//order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
        	order.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.TRANSPORTION);
        	order.setIs_discharge(GlobalConstant.ORDER_ISCHARGE_NO);
    		orderService.insert(order);
    		Transportion tran = transportionService.queryTransportionByPK(tranId);
    		TcFleet tcFleetNew = new TcFleet();
    		tcFleetNew.setTcFleetId(tcfleetId);
    		TcFleet tcfleet = tcFleetService.queryFleet(tcFleetNew);
    		orderService.consumeByTransportion(order, tran, tcfleet);
    		
    	}catch(Exception e){
    		System.out.println("Found exception:"+e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return ajaxJson;
    }
    
    
    @RequestMapping(value = {"/transferTransportionToDriver"})
    @ResponseBody
    public AjaxJson testTransferTransportionToDriver(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
       //测试充值：
    	try{
    		String  tranId ="TC37000001";
    		//String  tcfleetId ="f29f828a74d14c50b8409b9f94f83da3"; //张三车队有分配额度
    		//String  tcfleetId ="7731c97fad2e49a98e6c3a4939555f77";
    		SysOrder order = new SysOrder();
        	order.setOrderId(UUIDGenerator.getUUID());
        	String order_type = GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER;
        	String order_number = orderService.createOrderNumber(order_type);
        	order.setOrderNumber(order_number);
        	order.setOrderType(order_type);
        	order.setOrderDate(new Date());
        	order.setCash(new BigDecimal("200"));
        	order.setCreditAccount(tranId);
        	order.setDebitAccount("748f08a4e31545c2b6de454d3deb0979");
        	//order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_POS_CHARGE);
        	//order.setChannel("亭口加注站");
        	//order.setChannelNumber("TK456123");
        	order.setOperator("3f974dee326248ec96464874bc04129e");
        	//order.setOperatorSourceId();
        	//order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
        	order.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
        	order.setIs_discharge(GlobalConstant.ORDER_ISCHARGE_NO);
    		orderService.insert(order);
    		orderService.transferTransportionToDriver(order);
    		
    	}catch(Exception e){
    		System.out.println("Found exception:"+e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return ajaxJson;
    }
    
    
    @RequestMapping(value = {"/transferDriverToDriver"})
    @ResponseBody
    public AjaxJson testTransferDriverToDriver(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
       //测试充值：
    	try{
    		SysOrder order = new SysOrder();
        	order.setOrderId(UUIDGenerator.getUUID());
        	String order_type = GlobalConstant.OrderType.TRANSFER_DRIVER_TO_DRIVER;
        	String order_number = orderService.createOrderNumber(order_type);
        	order.setOrderNumber(order_number);
        	order.setOrderType(order_type);
        	order.setOrderDate(new Date());
        	order.setCash(new BigDecimal("111"));
        	order.setCreditAccount("748f08a4e31545c2b6de454d3deb0979");
        	order.setDebitAccount("5afe8e6d943c4d8988bcb605123390ca");
        	//order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_POS_CHARGE);
        	//order.setChannel("亭口加注站");
        	//order.setChannelNumber("TK456123");
        	order.setOperator("3f974dee326248ec96464874bc04129e");
        	//order.setOperatorSourceId();
        	//order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
        	order.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
        	order.setIs_discharge(GlobalConstant.ORDER_ISCHARGE_NO);
    		orderService.insert(order);
    		orderService.transferDriverToDriver(order);
    		
    	}catch(Exception e){
    		System.out.println("Found exception:"+e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return ajaxJson;
    }
    

}
