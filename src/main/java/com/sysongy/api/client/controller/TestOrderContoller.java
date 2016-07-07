package com.sysongy.api.client.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.sysongy.api.client.controller.model.CRMCardUpdateInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
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
        	order.setCash(new BigDecimal("188.6"));
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
    
    
    @RequestMapping(value = {"/dischargeToDriver"})
    @ResponseBody
    public AjaxJson testDischargeToDriver(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
       //测试充红，要通过原始订单，创建充红订单，然后调用discharge_order方法：
    	try{
        	SysOrder originalOrder = orderService.selectByPrimaryKey("57363949d9a0428f8eeb7e851fbc5e6a");
        	boolean bl = orderService.checkCanDischarge(originalOrder);
			System.out.println("能否充红："+bl);
			SysOrder dischargeOrder = orderService.createDischargeOrderByOriginalOrder(originalOrder, "006648b1e6c1469fa4fff0a0ad86ac06", "没啥原因，就想撤销。");
			orderService.dischargeOrder(originalOrder, dischargeOrder);
    		//orderService.chargeToDriver(order);
    	}catch(Exception e){
    		System.out.println("Found exception:"+e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return ajaxJson;
    }

}
