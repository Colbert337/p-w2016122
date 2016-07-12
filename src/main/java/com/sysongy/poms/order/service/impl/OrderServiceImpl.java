package com.sysongy.poms.order.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.ordergoods.dao.SysOrderGoodsMapper;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.order.dao.SysOrderMapper;
import com.sysongy.poms.order.dao.SysPrepayMapper;
import com.sysongy.poms.order.model.OrderLog;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;
import com.sysongy.poms.order.model.SysPrepay;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;

/**
 * 
 * @author zhangyt 2016-06-16
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysOrderMapper sysOrderMapper;

	@Autowired
	private DriverService driverService;

	@Autowired
	SysUserAccountMapper sysUserAccountMapper;
	
	@Autowired
	private SysCashBackService sysCashBackService;
	
	@Autowired
	private OrderDealService orderDealService;
	
	@Autowired
	private GastationService gastationService;
	
	@Autowired
	private SysUserAccountService sysUserAccountService;
	
	@Autowired
	private TransportionService transportionService;
	
	@Autowired
	private SysPrepayMapper sysPrepayMapper;
	
	@Autowired
	private TcFleetService tcFleetService;

	@Autowired
	private SysOrderGoodsMapper sysOrderGoodsMapper;

	@Override
	public PageInfo<SysOrder> queryOrders(SysOrder record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<SysOrder> list = sysOrderMapper.queryForPage(record);
		PageInfo<SysOrder> pageInfo = new PageInfo<SysOrder>(list);
		return pageInfo;
	}
	
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
		SysOrder sysOrder = sysOrderMapper.selectByPrimaryKey(orderId);
		List<SysOrderGoods> sysOrderGoods = sysOrderGoodsMapper.selectByOrderID(orderId);
		if((sysOrderGoods != null) && (sysOrderGoods.size() != 0)){
			sysOrder.setSysOrderGoods(sysOrderGoods);
		}
		return sysOrder;
	}

	@Override
	public SysOrder selectByOrderGASID(SysOrder record) {
		SysOrder sysOrder = sysOrderMapper.selectByOrderGASID(record);
		List<SysOrderGoods> sysOrderGoods = sysOrderGoodsMapper.selectByOrderID(record.getOrderId());
		if((sysOrderGoods != null) && (sysOrderGoods.size() != 0)){
			sysOrder.setSysOrderGoods(sysOrderGoods);
		}
		return sysOrder;
	}

	@Override
	public int updateByPrimaryKey(SysOrder record) {
		return sysOrderMapper.updateByPrimaryKey(record);
	}

    /**
     * 创建流水单编码
     * @paramrecord
     * @return
     */
    public String createOrderNumber(String order_type){
       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
  	   long times = System.currentTimeMillis();  
  	   Date date = new Date(times);  
  	   return order_type+sdf.format(date); 
    }
    
	/**
     * 给司机充值
     * 1.如果现金充值，取操作源operator_source_type，如果是加注站，则判断加注站预付款不能超过充值金额,
     *   不符合返回错误，符合了加注站减少预付款。
     * 2.给司机充值
     * 3.调用返现
     * @param order
     * @return
     */
	@Override
	public String chargeToDriver(SysOrder order) throws Exception{
	   if (order ==null){
		   throw new Exception(GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType == null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.CHARGE_TO_DRIVER))){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH);
	   }
	   String operatorTargetType = order.getOperatorTargetType();
	   if(operatorTargetType==null || (!operatorTargetType.equalsIgnoreCase(GlobalConstant.OrderOperatorTargetType.DRIVER))){
		   throw new Exception( GlobalConstant.OrderProcessResult.OPERATOR_TYPE_IS_NOT_DRIVER);
	   }
	   String is_discharge = order.getIs_discharge();
	   //1.现金充值抵扣预付款。如果现金充值，取操作源operator_source_type，如果是加注站，则判断充值金额不能超过加注站预付款
	   String charge_type = order.getChargeType();
	   BigDecimal cash = order.getCash();
	   if(GlobalConstant.OrderChargeType.CHARGETYPE_CASH_CHARGE.equalsIgnoreCase(charge_type)){
		   //
		   String chargeToDriverUpdateGastationPrepay_success = gastationService.chargeToDriverUpdateGastationPrepay(order, is_discharge);
		   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(chargeToDriverUpdateGastationPrepay_success)){
			   //如果出错直接返回错误代码退出
			   throw new Exception( chargeToDriverUpdateGastationPrepay_success);
		   }
	   }
	   
	   //2.首先给司机充值
	   String success_charge = driverService.chargeCashToDriver(order,is_discharge);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_charge)){
		   //如果出错直接返回错误代码退出
		   throw new Exception( success_charge);
	   }
	   //3.调用返现--在返现里面判断是否首次返现，是则增加调用首次返现规则，然后再继续调用返现
	   String success_cashback = driverService.cashBackToDriver(order);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_cashback)){
		   //如果出错直接返回错误代码退出
		   throw new Exception( success_cashback);
	   }
	  
	   return GlobalConstant.OrderProcessResult.SUCCESS;	
	}
    
	/**
     * 根据原订单，创建充红订单
     * @paramorder
     * @return
     * @throws Exception
     */
	@Override
    public SysOrder createDischargeOrderByOriginalOrder(SysOrder originalOrder, String operatorId,String discharge_reason) throws Exception{
    	SysOrder dischargeOrder = new SysOrder();
    	dischargeOrder.setOrderId(UUIDGenerator.getUUID());
    	//和原订单number一致
    	dischargeOrder.setOrderNumber(originalOrder.getOrderNumber());
    	dischargeOrder.setOrderType(originalOrder.getOrderType());
    	dischargeOrder.setOrderDate(new Date());
    	//将订单金额转为负
    	BigDecimal dis_cash = originalOrder.getCash().multiply(new BigDecimal(-1));
    	dischargeOrder.setCash(dis_cash);
    	dischargeOrder.setCreditAccount(originalOrder.getCreditAccount());
    	dischargeOrder.setDebitAccount(originalOrder.getDebitAccount());
    	dischargeOrder.setChargeType(originalOrder.getChargeType());
    	dischargeOrder.setChannel(originalOrder.getChannel());
    	dischargeOrder.setChannelNumber(originalOrder.getChannelNumber());
    	//设置新的operatorId
    	dischargeOrder.setOperator(operatorId);  
    	dischargeOrder.setOperatorSourceId(originalOrder.getOperatorSourceId());
    	dischargeOrder.setOperatorSourceType(originalOrder.getOperatorSourceType());
    	dischargeOrder.setOperatorTargetType(originalOrder.getOperatorTargetType());
    	//设置为充红
    	dischargeOrder.setIs_discharge(GlobalConstant.ORDER_ISCHARGE_YES);
    	//设置dischargeorderid，为原订单Id
    	dischargeOrder.setDischargeOrderId(originalOrder.getOrderId()); 
    	dischargeOrder.setConsume_cardInfo(originalOrder.getConsume_cardInfo());
    	//设置订单充红原因
    	dischargeOrder.setDischarge_reason(discharge_reason);
    	return dischargeOrder;
    }
    
	/**
     * 判断订单能否充红
     * 1.如果是充值：从订单对应的账户debit_account中找到对应的司机或者车队，如果此司机或者车队没有在订单创建时间后消费，则可以充红。
     *   不用have_consume字段，只能从order表中查询是否在这个订单后，有消费记录
     *    
     * 2.如果是消费，如果此消费订单没有超过24小时，则可以充红
     * @paramorder
     * @return
     * @throws Exception
     */
    public boolean checkCanDischarge(SysOrder originalOrder) throws Exception{
    	
    	String order_type = originalOrder.getOrderType();
    	//如果是对司机充值的订单充红，或者是对车队充值的订单充红
    	if(GlobalConstant.OrderType.CHARGE_TO_DRIVER.equalsIgnoreCase(order_type)
    	  ||GlobalConstant.OrderType.CHARGE_TO_TRANSPORTION.equalsIgnoreCase(order_type)
    	  ||GlobalConstant.OrderType.CHARGE_TO_GASTATION.equalsIgnoreCase(order_type)){
    		String debit_account = originalOrder.getDebitAccount();
    		Date order_date = originalOrder.getOrderDate();
    		HashMap map = new HashMap();
    		map.put("userId", debit_account);
    		map.put("order_date", order_date);
    		map.put("CONSUME_BY_TRANSPORTION",GlobalConstant.OrderType.CONSUME_BY_TRANSPORTION);
    		map.put("CONSUME_BY_DRIVER",GlobalConstant.OrderType.CONSUME_BY_DRIVER);
    		List<SysOrder> orderList = sysOrderMapper.queryConsumeOrderByUserId(map);
    		if(orderList==null || orderList.isEmpty()){
    			return true;
    		}else{
    			return false;
    		}
    	}
    	
    	if(GlobalConstant.OrderType.CONSUME_BY_DRIVER.equalsIgnoreCase(order_type) || GlobalConstant.OrderType.CONSUME_BY_TRANSPORTION.equalsIgnoreCase(order_type)){
        	Date order_date = originalOrder.getOrderDate();
        	long order_date_long = order_date.getTime(); 
        	long now = System.currentTimeMillis();
        	BigDecimal bg_old = new BigDecimal(order_date_long);
        	BigDecimal bg_new = new BigDecimal(now);
        	BigDecimal diff = bg_new.subtract(bg_old);
        	BigDecimal onehour = new BigDecimal(1000*60*60);
        	BigDecimal hours = diff.divide(onehour,2,RoundingMode.HALF_UP);
        	if(hours.compareTo(new BigDecimal(24)) > 0){
        		return false;
        	}else{
        		return true;
        	}
    	}
    	
    	return true;
    }
    
	/**
	 * 充红订单--包括充值充红、消费充红
	 * 1.从查询原始订单的订单处理流程
	 * 2.针对每个处理过程，进行反向操作
	 * @paramorder 充红的订单对象，其中属性discharge_order_id存的是对冲的订单ID,其他信息是初始化原始订单的信息，尤其是cash字段
	 */
    @Override
    public String dischargeOrder(SysOrder originalOrder, SysOrder dischargeOrder) throws Exception{
	   if (dischargeOrder ==null){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
	   }
	   
	   if(!checkCanDischarge(dischargeOrder)){
		   throw new Exception( GlobalConstant.OrderProcessResult.DISCHARGE_ORDER_CAN_NOT_BE_DISCHARGE);
	   }
	   
	   String is_discharge = dischargeOrder.getIs_discharge();
	   if(is_discharge==null || (!is_discharge.equalsIgnoreCase(GlobalConstant.ORDER_ISCHARGE_YES))){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_DISCHARGE);
	   }
	   
	   String discharge_order_id = dischargeOrder.getDischargeOrderId();
	   if(discharge_order_id==null){
		   throw new Exception( GlobalConstant.OrderProcessResult.DISCHARGE_ORDER_ID_IS_NULL);
	   }
	   
	   //充红的金额必须是负值
	   BigDecimal cash = dischargeOrder.getCash();
	   if(cash.compareTo(new BigDecimal("0")) > 0 ){
		   throw new Exception( GlobalConstant.OrderProcessResult.DISCHARGE_ORDER_CASH_IS_NOT_NEGATIVE);
	   }
	   //1.查询原始订单的订单处理流程：
	   List<SysOrderDeal> sysOrdelDeal_list = orderDealService.queryOrderDealByOrderId(discharge_order_id);
	   if(sysOrdelDeal_list==null|| sysOrdelDeal_list.size()==0){
		   throw new Exception( GlobalConstant.OrderProcessResult.DISCHARGE_ORDER_ORDERDEAL_IS_EMPTY);
	   }
	   //2.取得每个订单流水，根据类型做相应处理
	   for(SysOrderDeal sysOrderDeal: sysOrdelDeal_list){
		   String orderDealType = sysOrderDeal.getDealType();
		   if(orderDealType.equalsIgnoreCase(GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CHARGE)){
			  String dischargeCashToDriver_success =  driverService.chargeCashToDriver(dischargeOrder, is_discharge);
			  if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(dischargeCashToDriver_success)){
		    		//如果出错，直接退出
				  throw new Exception( dischargeCashToDriver_success);
		      }
		   }
		   //如果类型是加注站预付款扣除，则将加注站预付款的扣除充红
		   if(orderDealType.equalsIgnoreCase(GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_DEDUCT_GASTATION_PREPAY)){
			   String charge_type = dischargeOrder.getChargeType(); 
			   if(GlobalConstant.OrderChargeType.CHARGETYPE_CASH_CHARGE.equalsIgnoreCase(charge_type)){
				   String chargeToDriverUpdateGastationPrepay_success = gastationService.chargeToDriverUpdateGastationPrepay(dischargeOrder, is_discharge);
				   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(chargeToDriverUpdateGastationPrepay_success)){
					   //如果出错直接返回错误代码退出
					   throw new Exception( chargeToDriverUpdateGastationPrepay_success);
				   }
			   }
		   }
		   
		   if(orderDealType.equalsIgnoreCase(GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK)){
			  String newOrderDealType = GlobalConstant.OrderDealType.DISCHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK;
			  String disChargecashback_success = disCashBack(dischargeOrder,sysOrderDeal,newOrderDealType);
			  if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(disChargecashback_success)){
		    		//如果出错，直接退出
				  throw new Exception( disChargecashback_success);
		      }
		   }
		   if(orderDealType.equalsIgnoreCase(GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CASHBACK)){
			  String newOrderDealType = GlobalConstant.OrderDealType.DISCHARGE_TO_DRIVER_CASHBACK;
			  String disChargecashback_success = disCashBack(dischargeOrder,sysOrderDeal,newOrderDealType);
			  if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(disChargecashback_success)){
		    		//如果出错，直接退出
				  throw new Exception( disChargecashback_success);
		      }
		   }
		   
		   //针对司机消费充红
		   if(orderDealType.equalsIgnoreCase(GlobalConstant.OrderDealType.CONSUME_DRIVER_DEDUCT)){
			  String disChargeConsume_success = driverService.deductCashToDriver(dischargeOrder, GlobalConstant.ORDER_ISCHARGE_YES);
			  if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(disChargeConsume_success)){
		    		//如果出错，直接退出
				  throw new Exception( disChargeConsume_success);
		      }
		   }
		   
		  //针对运输公司消费充红
		   if(orderDealType.equalsIgnoreCase(GlobalConstant.OrderDealType.CONSUME_TRANSPORTION_DEDUCT)){
			  String disChargeConsume_success = transportionService.consumeTransportion(dischargeOrder);
			  if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(disChargeConsume_success)){
		    		//如果出错，直接退出
				  throw new Exception( disChargeConsume_success);
		      }
			  //和产品经理沟通：暂时不恢复运输公司或者车队的额度。
		   }
		   
		   
		 //TODO 针对其他类型进行操作
	   }
	   //充红完成后，将对应的原订单里面的discharge_order_id修改为此订单ID。同时修改been_discharged字段为1
	   HashMap<String,String> map = new HashMap<String,String>();
	   map.put("orderId", originalOrder.getOrderId());
	   map.put("dischargeOrderId", dischargeOrder.getOrderId());
	   map.put("discharge_reason", dischargeOrder.getDischarge_reason());
	   map.put("been_discharged", GlobalConstant.ORDER_BEEN_DISCHARGED_YES);
	   sysOrderMapper.updateOriginalOrderAfterDischarged(map);
//	   originalOrder.setDischargeOrderId(dischargeOrder.getOrderId());
//	   originalOrder.setDischarge_reason(dischargeOrder.getDischarge_reason());
//	   originalOrder.setBeen_discharged(GlobalConstant.ORDER_BEEN_DISCHARGED_YES);
	   
	   return GlobalConstant.OrderProcessResult.SUCCESS;
	}
    
    private String disCashBack(SysOrder order, SysOrderDeal sysOrderDeal, String newOrderDealType) throws Exception{
    	  //判断是给哪个类型操作
		  String operatorTargetType = order.getOperatorTargetType();
		  String accountId = "",accountUserName="";
		  if(GlobalConstant.OrderOperatorTargetType.DRIVER.equalsIgnoreCase(operatorTargetType)){
			  SysDriver driver = driverService.queryDriverByPK(order.getDebitAccount());
			  accountId = driver.getSysUserAccountId();
			  accountUserName = driver.getFullName(); 
		  }
		  //TODO if()其他类型判断
		  String disChargecashback_success =  sysCashBackService.disCashBackToAccount(order, sysOrderDeal, accountId, accountUserName, newOrderDealType);
		  if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(disChargecashback_success)){
	    		//如果出错，直接退出
			  throw new Exception( disChargecashback_success);
	      }
		  return disChargecashback_success;
    } 
    
    /**
     * 给运输公司充值
     * 1.充值
     * 2.不返现
     * @paramorder
     * @return
     */
	@Override
	public String chargeToTransportion(SysOrder order) throws Exception{
	   if (order ==null){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.CHARGE_TO_TRANSPORTION))){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH);
	   }
	   String operatorTargetType = order.getOperatorTargetType();
	   if(operatorTargetType==null || (!operatorTargetType.equalsIgnoreCase(GlobalConstant.OrderOperatorTargetType.TRANSPORTION))){
		   throw new Exception( GlobalConstant.OrderProcessResult.OPERATOR_TYPE_IS_NOT_TRANSPORTION);
	   }
	   //给运输公司充值
	   String success_charge = transportionService.chargeCashToTransportion(order);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_charge)){
		   //如果出错直接返回错误代码退出
		   throw new Exception( success_charge);
	   }
	   return GlobalConstant.OrderProcessResult.SUCCESS;	
	}
	
	/**
     * 加注站预付款充值
     * 1.充值预付款
     * 2.并增加预付款操作记录。
     * 3.不返现
     * @return
     */
	@Override
	public String chargeToGasStation(SysOrder order) throws Exception{
	   if (order ==null){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.CHARGE_TO_GASTATION))){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH);
	   }
	   
	   String operatorTargetType = order.getOperatorTargetType();
	   if(operatorTargetType==null || (!operatorTargetType.equalsIgnoreCase(GlobalConstant.OrderOperatorTargetType.GASTATION))){
		   throw new Exception( GlobalConstant.OrderProcessResult.OPERATOR_TYPE_IS_NOT_GASTATION);
	   }
	   
	   BigDecimal cash = order.getCash();
	   String debit_account = order.getDebitAccount();
	   if(debit_account==null ||debit_account.equalsIgnoreCase("")){
		   throw new Exception( GlobalConstant.OrderProcessResult.DEBIT_ACCOUNT_IS_NULL);
	   }
	   Gastation gastation = gastationService.queryGastationByPK(debit_account);
	   //1.充值预付款：直接充值，就是直接增加
	   BigDecimal addCash = cash;
	   String updateBalance_success =  gastationService.updatePrepayBalance(gastation, addCash);
	   //记录交易流水：
	   String orderDealType = GlobalConstant.OrderDealType.CHARGE_TO_GASTATION_CHARGE;
	   String remark = "给"+ gastation.getGas_station_name()+"的账户，充值预付款"+cash.toString()+"。";
	   orderDealService.createOrderDeal(order.getOrderId(), orderDealType, remark,updateBalance_success);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(updateBalance_success)){
  		   //如果出错直接返回错误代码退出
		   throw new Exception( updateBalance_success);
  	   }
	   //2.记录预付款操作记录：
	   SysPrepay sysPrepay = new SysPrepay();
	   sysPrepay.setPrepayId(UUIDGenerator.getUUID());
	   sysPrepay.setPayerId(order.getDebitAccount());
	   sysPrepay.setCash(addCash);
	   sysPrepay.setPayDate(new Date());
	   sysPrepay.setOperateType(GlobalConstant.SysPrepayOperate.CHARGE);
	   remark ="给"+ gastation.getGas_station_name()+"的账户，充值预付款"+cash.toString()+"。";
	   sysPrepay.setRemark(remark);
	   sysPrepayMapper.insert(sysPrepay);
	   
	   return GlobalConstant.OrderProcessResult.SUCCESS;	
	}
    
	/**
	 * 个人消费：
	 * 注意消费的时候，cash一定传正值
	 * 1.判断账户余额
	 * 2.扣除账户金额--乐观锁操作
	 */
	@Override
	public String consumeByDriver(SysOrder order) throws Exception{
	   if (order ==null){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.CONSUME_BY_DRIVER))){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH);
	   }
	   
	   String credit_account = order.getCreditAccount();
	   if(credit_account==null || credit_account.equalsIgnoreCase("")){
		   throw new Exception( GlobalConstant.OrderProcessResult.CREDIT_ACCOUNT_IS_NULL);
	   }

	   String operatorTargetType = order.getOperatorTargetType();
	   if(operatorTargetType==null || (!operatorTargetType.equalsIgnoreCase(GlobalConstant.OrderOperatorTargetType.DRIVER))){
		   throw new Exception( GlobalConstant.OrderProcessResult.OPERATOR_TYPE_IS_NOT_DRIVER);
	   }

	   //消费的时候传过去的cash是正值
	   String consume_success =driverService.deductCashToDriver(order, GlobalConstant.ORDER_ISCHARGE_NO);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(consume_success)){
  		   //如果出错直接返回错误代码退出
		   throw new Exception( consume_success);
  	   }

		for (SysOrderGoods sysOrderGoods : order.getSysOrderGoods()) {
			sysOrderGoods.setOrderId(order.getOrderId());
			int nRet = sysOrderGoodsMapper.insert(sysOrderGoods);
			if(nRet < 1){
				logger.error("产品插入出错" + sysOrderGoods.getOrderId());
			}
		}

	   return GlobalConstant.OrderProcessResult.SUCCESS;
	}

	/**
	 * 运输公司消费： 车队消费，扣除的是运输公司的账户里面的钱，并扣除车队的分配额度
	 * 注意消费的时候，cash一定传正值
	 * 注意：order里面传的credit_account存的是运输公司的UUID
	 * 1.判断此车队是否分配额度，如果分配，则扣除此车队的额度。
	 *   如果没有分配，则扣除此车队对应的运输公司的额度。
	 * 2.扣除运输公司账户金额--和消费一样
	 */
	@Override
	public String consumeByTransportion(SysOrder order,Transportion tran, TcFleet tcfleet) throws Exception{
	   if (order ==null){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
	   }
	   
	   if (tran ==null){
		   throw new Exception( GlobalConstant.OrderProcessResult.TRANSPORTION_IS_NULL);
	   }
	   if (tcfleet ==null){
		   throw new Exception( GlobalConstant.OrderProcessResult.TCFLEET_IS_NULL);
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.CONSUME_BY_TRANSPORTION))){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH);
	   }
	   
	   String credit_account = order.getCreditAccount();
	   if(credit_account==null || credit_account.equalsIgnoreCase("")){
		   throw new Exception( GlobalConstant.OrderProcessResult.CREDIT_ACCOUNT_IS_NULL);
	   }

	   String operatorTargetType = order.getOperatorTargetType();
	   if(operatorTargetType==null || (!operatorTargetType.equalsIgnoreCase(GlobalConstant.OrderOperatorTargetType.TRANSPORTION))){
		   throw new Exception( GlobalConstant.OrderProcessResult.OPERATOR_TYPE_IS_NOT_TRANSPORTION);
	   }
	   //1.判断此车队是否分配额度
	   Integer is_allot = tcfleet.getIsAllot();
	 //扣除车队额度//传过去负值
	   BigDecimal cash = order.getCash();
	   BigDecimal addcash = cash.multiply(new BigDecimal(-1)); 
	   if(is_allot.intValue()==GlobalConstant.TCFLEET_IS_ALLOT_YES){
		   
		   tcFleetService.updateFleetQuota(tran.getSys_transportion_id(), tcfleet.getTcFleetId(), addcash);
	   }else if(is_allot.intValue()==GlobalConstant.TCFLEET_IS_ALLOT_NO){
		   transportionService.modifyDeposit(tran, addcash);
	   }
	   
	   //2.扣除运输公司账户金额
	   //消费的时候传过去的cash是正值,充红的时候传过去的是负值
	   String consume_success =transportionService.consumeTransportion(order);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(consume_success)){
  		   //如果出错直接返回错误代码退出
		   throw new Exception( consume_success);
  	   }

		for (SysOrderGoods sysOrderGoods : order.getSysOrderGoods()) {
			sysOrderGoods.setOrderId(order.getOrderId());
			int nRet = sysOrderGoodsMapper.insert(sysOrderGoods);
			if(nRet < 1){
				logger.error("产品插入出错" + sysOrderGoods.getOrderId());
			}
		}

	   return GlobalConstant.OrderProcessResult.SUCCESS;
	}
	
	@Override
	public String validAccount(SysOrder record){
		String strRet = GlobalConstant.OrderProcessResult.SUCCESS;
		SysUserAccount creditAccount = sysUserAccountMapper.selectByPrimaryKey(record.getCreditAccount());
		SysUserAccount debitAccount = sysUserAccountMapper.selectByPrimaryKey(record.getDebitAccount());
		String strFrozen = validateAccountIfFroen(creditAccount, debitAccount, record);
		if(strFrozen.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS))
			return strFrozen;
		String strLackMoney = validateAccountBalance(creditAccount, record);
		if(strLackMoney.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS))
			return strLackMoney;
		return strRet;
	}

	/**
	 * 查看消费账户冻结
	 * @param creditAccount
	 * @param debitAccount
	 * @param record
     * @return
     */
	private String validateAccountIfFroen(SysUserAccount creditAccount, SysUserAccount debitAccount, SysOrder record){
		String strRet = GlobalConstant.OrderProcessResult.SUCCESS;
		boolean isCreditFrozen = creditAccount.getAccount_status().equalsIgnoreCase("0");
		if(isCreditFrozen)
			return GlobalConstant.OrderProcessResult.ORDER_ERROR_CREDIT_ACCOUNT_IS_FROEN;

		boolean isCreditAccountCardFrozen = (StringUtils.isNotEmpty(record.getConsume_card())
				&& (creditAccount.getAccount_status().equalsIgnoreCase("1")));
		if(isCreditAccountCardFrozen){
			return GlobalConstant.OrderProcessResult.ORDER_ERROR_CREDIT_ACCOUNT_CARD_IS_FROEN;
		}
		return strRet;
	}

	/**
	 * 查看消费账户余额
	 * @param creditAccount
	 * @param record
     * @return
     */
	private String validateAccountBalance(SysUserAccount creditAccount, SysOrder record){
		String strRet = GlobalConstant.OrderProcessResult.SUCCESS;
		BigDecimal balance = new BigDecimal(creditAccount.getAccountBalance());
		if(record.getCash().compareTo(balance) == 1)
			return GlobalConstant.OrderProcessResult.ORDER_ERROR_BALANCE_IS_NOT_ENOUGH;
		return strRet;
	}

	/**
	 * 运输公司给个人转账:注意cash是正值
	 * 1.扣除运输公司账户
	 * 2.个人账户增加金额
	 * 3.给运输公司返现。
	 * @return
	 */
	public String transferTransportionToDriver(SysOrder order) throws Exception{
	
	   if (order ==null){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER))){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH);
	   }
	   String credit_account = order.getCreditAccount();
	   if(credit_account==null || credit_account.equalsIgnoreCase("")){
		   throw new Exception( GlobalConstant.OrderProcessResult.TRANSFER_CREDIT_ACCOUNT_IS_NULL);
	   }
	   String debit_account = order.getDebitAccount();
	   if(debit_account==null || debit_account.equalsIgnoreCase("")){
		   throw new Exception( GlobalConstant.OrderProcessResult.TRANSFER_DEBIT_ACCOUNT_IS_NULL);
	   }
	   
	   Transportion tran = transportionService.queryTransportionByPK(credit_account);
	   String tran_account = tran.getSys_user_account_id();
		
	   //1.扣除运输公司账户
	   String success_deduct = transportionService.transferTransportionToDriverDeductCash(order,tran);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_deduct)){
  		   //如果出错直接返回错误代码退出
		   throw new Exception( success_deduct);
  	   }
	   //增加逻辑：要扣除运输公司的额度,因为转账的时候cash是正值，这里需要转为负值
	   BigDecimal cash = order.getCash();
	   BigDecimal increment = cash.multiply(new BigDecimal(-1));
	   int up_row = transportionService.modifyDeposit(tran, increment);
	   if(up_row!=1){
			throw new Exception("更新运输公司"+tran.getTransportion_name()+"的剩余额度时出错。");
	   }
	   
	   //2.个人账户增加金额
	   String is_discharge = GlobalConstant.ORDER_ISCHARGE_NO;
	   String success_chong = driverService.chargeCashToDriver(order, is_discharge);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_chong)){
  		   //如果出错直接返回错误代码退出
		   throw new Exception( success_chong);
  	   }
	   //3.给运输公司返现：
	   String cashbackNumber = GlobalConstant.CashBackNumber.CASHBACK_TRANSFER_CHARGE;
	   List<SysCashBack> cashBackList = sysCashBackService.queryCashBackByNumber(cashbackNumber);
	   String accountId = new String(tran_account);
	   String accountUserName = tran.getTransportion_name();
	   String orderDealType = GlobalConstant.OrderDealType.TRANSFER_TRANSPORTION_TO_DRIVER_CASHBACK_TO_TRANSPORTION;
	   //修改了cashToAccount方法，里面增加了运输公司转账的时候，修改运输公司剩余额度。
	   String success_cashBack = sysCashBackService.cashToAccount(order, cashBackList, accountId, accountUserName, orderDealType);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_cashBack)){
  		   //如果出错直接返回错误代码退出
		   throw new Exception( success_cashBack);
  	   }
		return GlobalConstant.OrderProcessResult.SUCCESS;
	}

	/**
	 * 个人给个人转账
	 * 1.扣除个人账户credit_account
	 * 2.增加个人账户debit_account
	 * 3.不返现
	 * @return
	 */
	public String transferDriverToDriver(SysOrder order) throws Exception{
	   if (order ==null){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.TRANSFER_DRIVER_TO_DRIVER))){
		   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH);
	   }
	   String credit_account = order.getCreditAccount();
	   if(credit_account==null || credit_account.equalsIgnoreCase("")){
		   throw new Exception( GlobalConstant.OrderProcessResult.TRANSFER_CREDIT_ACCOUNT_IS_NULL);
	   }
	   String debit_account = order.getDebitAccount();
	   if(debit_account==null || debit_account.equalsIgnoreCase("")){
		   throw new Exception( GlobalConstant.OrderProcessResult.TRANSFER_DEBIT_ACCOUNT_IS_NULL);
	   }
	   //1.扣除credit_account账户钱
	   String is_discharge = GlobalConstant.ORDER_ISCHARGE_NO;
	   String success_deduct = driverService.deductCashToDriver(order, is_discharge);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_deduct)){
  		   //如果出错直接返回错误代码退出
		   throw new Exception( success_deduct);
  	   }
	   //2.个人账户增加金额
	   is_discharge = GlobalConstant.ORDER_ISCHARGE_NO;
	   String success_chong = driverService.chargeCashToDriver(order, is_discharge);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_chong)){
  		   //如果出错直接返回错误代码退出
		   throw new Exception( success_chong);
  	   }
	   return GlobalConstant.OrderProcessResult.SUCCESS;
	}

	public String selectCashBackByOrderID(String orderId){
		if(StringUtils.isEmpty(orderId)){
			logger.error("The order id is null!!!");
			return null;
		}
		return orderDealService.selectCashBackByOrderID(orderId);
	}

	@Override
	public PageInfo<OrderLog> queryOrderLogs(OrderLog record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<OrderLog> list = sysOrderMapper.queryOrderLogs(record);
		PageInfo<OrderLog> pageInfo = new PageInfo<OrderLog>(list);
		return pageInfo;
	}
}
