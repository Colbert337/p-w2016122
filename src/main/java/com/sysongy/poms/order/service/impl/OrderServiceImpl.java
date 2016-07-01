package com.sysongy.poms.order.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.order.dao.SysOrderMapper;
import com.sysongy.poms.order.dao.SysPrepayMapper;
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
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;

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
		return sysOrderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public int updateByPrimaryKey(SysOrder record) {
		return sysOrderMapper.updateByPrimaryKey(record);
	}

    /**
     * 创建流水单编码
     * @param record
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
		   return GlobalConstant.OrderProcessResult.ORDER_IS_NULL;
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.CHARGE_TO_DRIVER))){
		   return GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH;
	   }
	   String operatorTargetType = order.getOperatorTargetType();
	   if(operatorTargetType==null || (!operatorTargetType.equalsIgnoreCase(GlobalConstant.OrderOperatorTargetType.DRIVER))){
		   return GlobalConstant.OrderProcessResult.OPERATOR_TYPE_IS_NOT_DRIVER;
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
			   return chargeToDriverUpdateGastationPrepay_success;
		   }
	   }
	   
	   //2.首先给司机充值
	   String success_charge = driverService.chargeCashToDriver(order,is_discharge);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_charge)){
		   //如果出错直接返回错误代码退出
		   return success_charge;
	   }
	   //3.调用返现--在返现里面判断是否首次返现，是则增加调用首次返现规则，然后再继续调用返现
	   String success_cashback = driverService.cashBackToDriver(order);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_cashback)){
		   //如果出错直接返回错误代码退出
		   return success_cashback;
	   }
	  
	   return GlobalConstant.OrderProcessResult.SUCCESS;	
	}
    
	/**
     * 判断订单能否充红
     * 取得订单的创建时间，然后从订单对应的账户debit_account中找到对应的司机或者车队，如果此司机或者车队没有在订单创建时间后消费，则可以充值。
     * @param order
     * @return
     * @throws Exception
     */
    public boolean checkCanDischarge(SysOrder order) throws Exception{
    	//TODO
    	return true;
    }
    
	/**
	 * 充红订单
	 * 1.从查询原始订单的订单处理流程
	 * 2.针对每个处理过程，进行反向操作
	 * @param order 充红的订单对象，其中属性discharge_order_id存的是对冲的订单ID,其他信息是初始化原始订单的信息，尤其是cash字段
	 */
    public String dischargeOrder(SysOrder order) throws Exception{
	   if (order ==null){
		   return GlobalConstant.OrderProcessResult.ORDER_IS_NULL;
	   }
	   
	   if(!checkCanDischarge(order)){
		   return GlobalConstant.OrderProcessResult.DISCHARGE_ORDER_CAN_NOT_BE_DISCHARGE;
	   }
	   
	   String is_discharge = order.getIs_discharge();
	   if(is_discharge==null || (!is_discharge.equalsIgnoreCase(GlobalConstant.ORDER_ISCHARGE_YES))){
		   return GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_DISCHARGE;
	   }
	   
	   String discharge_order_id = order.getDischargeOrderId();
	   if(discharge_order_id==null){
		   return GlobalConstant.OrderProcessResult.DISCHARGE_ORDER_ID_IS_NULL;
	   }
	   
	   BigDecimal cash = order.getCash();
	   if(cash.compareTo(new BigDecimal("0")) > 0 ){
		   return GlobalConstant.OrderProcessResult.DISCHARGE_ORDER_CASH_IS_NOT_NEGATIVE;
	   }
	   //1.查询原始订单的订单处理流程：
	   List<SysOrderDeal> sysOrdelDeal_list = orderDealService.queryOrderDealByOrderId(discharge_order_id);
	   if(sysOrdelDeal_list==null|| sysOrdelDeal_list.size()==0){
		   return GlobalConstant.OrderProcessResult.DISCHARGE_ORDER_ORDERDEAL_IS_EMPTY;
	   }
	   //2.取得每个订单流水，根据类型做相应处理
	   for(SysOrderDeal sysOrderDeal: sysOrdelDeal_list){
		   String orderDealType = sysOrderDeal.getDealType();
		   if(orderDealType.equalsIgnoreCase(GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CHARGE)){
			  String dischargeCashToDriver_success =  driverService.chargeCashToDriver(order, is_discharge);
			  if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(dischargeCashToDriver_success)){
		    		//如果出错，直接退出
		    		return dischargeCashToDriver_success;
		      }
		   }
		   //如果类型是加注站预付款扣除，则将加注站预付款的扣除充红
		   if(orderDealType.equalsIgnoreCase(GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_DEDUCT_GASTATION_PREPAY)){
			   String charge_type = order.getChargeType(); 
			   if(GlobalConstant.OrderChargeType.CHARGETYPE_CASH_CHARGE.equalsIgnoreCase(charge_type)){
				   String chargeToDriverUpdateGastationPrepay_success = gastationService.chargeToDriverUpdateGastationPrepay(order, is_discharge);
				   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(chargeToDriverUpdateGastationPrepay_success)){
					   //如果出错直接返回错误代码退出
					   return chargeToDriverUpdateGastationPrepay_success;
				   }
			   }
		   }
		   
		   if(orderDealType.equalsIgnoreCase(GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK)){
			  String newOrderDealType = GlobalConstant.OrderDealType.DISCHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK;
			  String disChargecashback_success = disCashBack(order,sysOrderDeal,newOrderDealType);
			  if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(disChargecashback_success)){
		    		//如果出错，直接退出
		    		return disChargecashback_success;
		      }
		   }
		   if(orderDealType.equalsIgnoreCase(GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CASHBACK)){
			  String newOrderDealType = GlobalConstant.OrderDealType.DISCHARGE_TO_DRIVER_CASHBACK;
			  String disChargecashback_success = disCashBack(order,sysOrderDeal,newOrderDealType);
			  if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(disChargecashback_success)){
		    		//如果出错，直接退出
		    		return disChargecashback_success;
		      }
		   }
		   
		 //TODO 针对其他类型进行操作
	   }
	   //TODO
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
	    		return disChargecashback_success;
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
		   return GlobalConstant.OrderProcessResult.ORDER_IS_NULL;
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.CHARGE_TO_TRANSPORTION))){
		   return GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH;
	   }
	   String operatorTargetType = order.getOperatorTargetType();
	   if(operatorTargetType==null || (!operatorTargetType.equalsIgnoreCase(GlobalConstant.OrderOperatorTargetType.TRANSPORTION))){
		   return GlobalConstant.OrderProcessResult.OPERATOR_TYPE_IS_NOT_TRANSPORTION;
	   }
	   //给运输公司充值
	   String success_charge = transportionService.chargeCashToTransportion(order);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_charge)){
		   //如果出错直接返回错误代码退出
		   return success_charge;
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
		   return GlobalConstant.OrderProcessResult.ORDER_IS_NULL;
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.CHARGE_TO_GASTATION))){
		   return GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH;
	   }
	   
	   String operatorTargetType = order.getOperatorTargetType();
	   if(operatorTargetType==null || (!operatorTargetType.equalsIgnoreCase(GlobalConstant.OrderOperatorTargetType.GASTATION))){
		   return GlobalConstant.OrderProcessResult.OPERATOR_TYPE_IS_NOT_GASTATION;
	   }
	   
	   BigDecimal cash = order.getCash();
	   String debit_account = order.getDebitAccount();
	   if(debit_account==null ||debit_account.equalsIgnoreCase("")){
			return GlobalConstant.OrderProcessResult.DEBIT_ACCOUNT_IS_NULL;
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
  		   return updateBalance_success;
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
	 * 1.判断账户余额
	 * 2.扣除账户金额--乐观锁操作
	 */
	@Override
	public String consumeByDriver(SysOrder order) throws Exception{
	   if (order ==null){
		   return GlobalConstant.OrderProcessResult.ORDER_IS_NULL;
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.CONSUME_BY_DRIVER))){
		   return GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH;
	   }
	   
	   String credit_account = order.getCreditAccount();
	   if(credit_account==null || credit_account.equalsIgnoreCase("")){
		   return GlobalConstant.OrderProcessResult.CREDIT_ACCOUNT_IS_NULL;
	   }

	   String operatorTargetType = order.getOperatorTargetType();
	   if(operatorTargetType==null || (!operatorTargetType.equalsIgnoreCase(GlobalConstant.OrderOperatorTargetType.DRIVER))){
		   return GlobalConstant.OrderProcessResult.OPERATOR_TYPE_IS_NOT_DRIVER;
	   }
	   
	   String consume_success = driverService.deductCashToDriver(order, GlobalConstant.ORDER_ISCHARGE_NO);
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
	 * 运输公司给个人转账
	 * 1.扣除运输公司账户
	 * 2.个人账户增加金额
	 * 3.给运输公司返现。
	 * @return
	 */
	public String transferTransportionToDriver(SysOrder order) throws Exception{
	
	   if (order ==null){
		   return GlobalConstant.OrderProcessResult.ORDER_IS_NULL;
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER))){
		   return GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH;
	   }
	   String credit_account = order.getCreditAccount();
	   if(credit_account==null || credit_account.equalsIgnoreCase("")){
		   return GlobalConstant.OrderProcessResult.TRANSFER_CREDIT_ACCOUNT_IS_NULL;
	   }
	   String debit_account = order.getDebitAccount();
	   if(debit_account==null || debit_account.equalsIgnoreCase("")){
		   return GlobalConstant.OrderProcessResult.TRANSFER_DEBIT_ACCOUNT_IS_NULL;
	   }
	   
	   Transportion tran = transportionService.queryTransportionByPK(credit_account);
	   String tran_account = tran.getSys_user_account_id();
		
	   //1.扣除运输公司账户
	   String success_deduct = transportionService.transferTransportionToDriverDeductCash(order,tran);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_deduct)){
  		   //如果出错直接返回错误代码退出
  		   return success_deduct;
  	   }
	   //2.个人账户增加金额
	   String is_discharge = GlobalConstant.ORDER_ISCHARGE_NO;
	   String success_chong = driverService.chargeCashToDriver(order, is_discharge);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_chong)){
  		   //如果出错直接返回错误代码退出
  		   return success_chong;
  	   }
	   //3.给运输公司返现：
	   String cashbackNumber = GlobalConstant.CashBackNumber.CASHBACK_TRANSFER_CHARGE;
	   List<SysCashBack> cashBackList = sysCashBackService.queryCashBackByNumber(cashbackNumber);
	   String accountId = new String(tran_account);
	   String accountUserName = tran.getTransportion_name();
	   String orderDealType = GlobalConstant.OrderDealType.TRANSFER_TRANSPORTION_TO_DRIVER_CASHBACK_TO_TRANSPORTION;
	   String success_cashBack = sysCashBackService.cashToAccount(order, cashBackList, accountId, accountUserName, orderDealType);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_cashBack)){
  		   //如果出错直接返回错误代码退出
  		   return success_cashBack;
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
		   return GlobalConstant.OrderProcessResult.ORDER_IS_NULL;
	   }
	   
	   String orderType = order.getOrderType();
	   if(orderType==null || (!orderType.equalsIgnoreCase(GlobalConstant.OrderType.TRANSFER_DRIVER_TO_DRIVER))){
		   return GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_MATCH;
	   }
	   String credit_account = order.getCreditAccount();
	   if(credit_account==null || credit_account.equalsIgnoreCase("")){
		   return GlobalConstant.OrderProcessResult.TRANSFER_CREDIT_ACCOUNT_IS_NULL;
	   }
	   String debit_account = order.getDebitAccount();
	   if(debit_account==null || debit_account.equalsIgnoreCase("")){
		   return GlobalConstant.OrderProcessResult.TRANSFER_DEBIT_ACCOUNT_IS_NULL;
	   }
	   //1.扣除credit_account账户钱
	   String is_discharge = GlobalConstant.ORDER_ISCHARGE_NO;
	   String success_deduct = driverService.deductCashToDriver(order, is_discharge);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_deduct)){
  		   //如果出错直接返回错误代码退出
  		   return success_deduct;
  	   }
	   //2.个人账户增加金额
	   is_discharge = GlobalConstant.ORDER_ISCHARGE_NO;
	   String success_chong = driverService.chargeCashToDriver(order, is_discharge);
	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(success_chong)){
  		   //如果出错直接返回错误代码退出
  		   return success_chong;
  	   }
	   return GlobalConstant.OrderProcessResult.SUCCESS;
	}
	
    public static void main(String[] args){
    	OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
    	//测试充值：
    	SysOrder order = new SysOrder();
    	order.setOrderId(UUIDGenerator.getUUID());
    	String order_type = GlobalConstant.OrderType.CHARGE_TO_DRIVER;
    	String order_number = orderServiceImpl.createOrderNumber(order_type);
    	order.setOrderNumber(order_number);
    	order.setOrderType(order_type);
    	order.setOrderDate(new Date());
    	order.setCash(new BigDecimal("16.88"));
    	order.setDebitAccount("748f08a4e31545c2b6de454d3deb0979");
    	order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_CASH_CHARGE);
    	order.setChannel("亭口加注站");
    	order.setChannelNumber("GS12000003");
    	order.setOperator("14e9ef72ce5c424dbcc36859d6618a6b");
    	order.setOperatorSourceId("GS12000003");
    	order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
    	order.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
    	order.setIs_discharge(GlobalConstant.ORDER_ISCHARGE_NO);
    	try{
    		orderServiceImpl.insert(order);
    		orderServiceImpl.chargeToDriver(order);
    	}catch(Exception e){
    		System.out.println("Found exception:"+e.getMessage());
    		e.printStackTrace();
    	}
    }
}
