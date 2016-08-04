package com.sysongy.poms.transportion.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.dao.SysDepositLogMapper;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.transportion.dao.TransportionMapper;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.UUIDGenerator;
import com.sysongy.util.pojo.AliShortMessageBean;

@Service
public class TransportionServiceImpl implements TransportionService {
	
	@Autowired
	private TransportionMapper transportionMapper;
	@Autowired
	private SysUserAccountMapper sysUserAccountMapper;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private UsysparamService usysparamService;
	@Autowired
	private SysDepositLogMapper sysDepositLogMapper;
	@Autowired
	private SysUserAccountService sysUserAccountService;
	@Autowired
	private OrderDealService orderDealService;
	@Autowired
	private OrderService orderService;
	
	
	@Override
	public PageInfo<Transportion> queryTransportion(Transportion record) throws Exception {
		
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Transportion> list = transportionMapper.queryForPage(record);
		PageInfo<Transportion> pageInfo = new PageInfo<Transportion>(list);
		
		return pageInfo;
	}

	@Override
	public String saveTransportion(Transportion record, String operation) throws Exception {
		if("insert".equals(operation)){
			Transportion station = transportionMapper.findTransportationid("TC"+record.getProvince_id());
			String newid;
			
			if(station == null || StringUtils.isEmpty(station.getSys_transportion_id())){
				newid = "TC"+record.getProvince_id() + "00001";
			}else{
				Integer tmp = Integer.valueOf(station.getSys_transportion_id().substring(5, 10)) + 1;
				newid = "TC"+record.getProvince_id() +StringUtils.leftPad(tmp.toString() , 5, "0");
			}
			//初始化钱袋信息
			SysUserAccount sysUserAccount = new SysUserAccount();
			sysUserAccount.setSysUserAccountId(UUIDGenerator.getUUID());
			sysUserAccount.setAccountCode("GS"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
			sysUserAccount.setAccountType(GlobalConstant.AccounType.GASTATION);
			sysUserAccount.setAccountBalance("0.00");
			sysUserAccount.setCreatedDate(new Date());
			sysUserAccount.setUpdatedDate(new Date());
			sysUserAccount.setAccount_status(GlobalConstant.AccountStatus.NORMAL);
			int ret = sysUserAccountMapper.insert(sysUserAccount);
			if(ret != 1){
				throw new Exception("钱袋初始化失败");
			}
			
			Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
			String show_path = (String) prop.get("default_img");
			record.setSys_user_account_id(sysUserAccount.getSysUserAccountId());
			record.setStatus(GlobalConstant.StationStatus.USED);
			record.setCreated_time(new Date());
			record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			record.setSys_transportion_id(newid);
			record.setIndu_com_certif(show_path);
			record.setTax_certif(show_path);
			record.setLng_certif(show_path);
			record.setDcp_certif(show_path);
			record.setDeposit(BigDecimal.valueOf(0.00));
			
			transportionMapper.insert(record);
			//创建管理员
			SysUser user = new SysUser();
			user.setUserName(record.getAdmin_username());
			user.setPassword(record.getAdmin_userpassword());
			user.setUserType(GlobalConstant.USER_TYPE_TRANSPORT);

			// 给用户关联运输公司编号
			user.setStationId(newid);
			user.setIsAdmin(0);//管理员
			sysUserService.addAdminUser(user);
			//同步系统参数字典表
			Usysparam usysparam = new Usysparam();
			usysparam.setGcode("TRANSTION");
			usysparam.setMcode(record.getSys_transportion_id());
			usysparam.setMname(record.getTransportion_name());
			usysparam.setScode("");
			usysparamService.saveUsysparam(usysparam);
			
			return newid;
		}else{
			if(!StringUtils.isEmpty(record.getExpiry_date_frompage())){
				record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			}
			record.setUpdated_time(new Date());
			transportionMapper.updateByPrimaryKeySelective(record);
			//更新系统参数字典表
			Usysparam usysparam = new Usysparam();
			usysparam.setGcode("TRANSTION");
			usysparam.setMcode(record.getSys_transportion_id());
			usysparam.setMname(record.getTransportion_name());
			if(StringUtils.isEmpty(record.getTransportion_name())){
				Transportion tmp = transportionMapper.selectByPrimaryKey(record.getSys_transportion_id());
				usysparam.setMname(tmp.getTransportion_name());
			}
			usysparam.setScode("");
			usysparamService.updateUsysparam(usysparam);
			
			//维护系统参数表
			if(GlobalConstant.StationStatus.PAUSE.equals(record.getStatus())){
				usysparamService.deleteUsysparam(usysparam);
			}else{
				Usysparam tmp = usysparamService.queryUsysparamByCode("TRANSTION", record.getSys_transportion_id());
				if(tmp == null){
					usysparamService.saveUsysparam(usysparam);
				}
			}
			return record.getSys_transportion_id();
		}
	}

	@Override
	public Integer delTransportion(String transportionid) throws Exception {
		Transportion transportion = transportionMapper.selectByPrimaryKey(transportionid);
		//删除对应的管理员用户
		SysUser user = new SysUser();
		user.setUserName(transportion.getAdmin_username());
		user.setUserType(GlobalConstant.USER_TYPE_TRANSPORT);
		user.setStatus(GlobalConstant.STATUS_DELETE);
		sysUserService.updateUserByName(user);
		//删除对应的系统参数字典表
		Usysparam usysparam = new Usysparam();
		usysparam.setGcode("WORKSTATION");
		usysparam.setMcode(transportion.getTransportion_name());
		usysparamService.deleteUsysparam(usysparam);
		
		return transportionMapper.deleteByPrimaryKey(transportionid);
	}

	@Override
	public List<Transportion> getAllTransportionByArea(String areacode) throws Exception {
//		return transportionMapper.getAllStationByArea(areacode);
		return null;
	}

	@Override
	public Transportion queryTransportionByPK(String transportionid) throws Exception {
		Transportion station =  transportionMapper.selectByPrimaryKey(transportionid);
		if(station != null && station.getExpiry_date() != null){
			station.setExpiry_date_frompage(new SimpleDateFormat("yyyy-MM-dd").format(station.getExpiry_date()));
		}
		return station;
	}
	
	
	 /**
	 * 给运输公司充值(无充红,不返现)
	 * 同时增加运输公司的剩余额度。
	 * @param order
	 * @return
     * @throws Exception 
	 */
	public String chargeCashToTransportion(SysOrder order) throws Exception{
		if (order ==null){
			throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
		}
		
		String debit_account = order.getDebitAccount();
		if(debit_account==null ||debit_account.equalsIgnoreCase("")){
			throw new Exception( GlobalConstant.OrderProcessResult.DEBIT_ACCOUNT_IS_NULL);
		}
		
		//给账户充钱
		Transportion tran = this.queryTransportionByPK(debit_account);
		String tran_account = tran.getSys_user_account_id();
		BigDecimal cash = order.getCash();
		String cash_success = sysUserAccountService.addCashToAccount(tran_account,cash,order.getOrderType());
		
		//变更额度：
		int up_row = modifyDeposit(tran,cash);
		if(up_row!=1){
			throw new Exception("更新运输公司"+tran.getTransportion_name()+"的剩余额度时出错。");
		}
		//记录订单流水
		String chong = "充值";
		String orderDealType = GlobalConstant.OrderDealType.CHARGE_TO_TRANSPORTION_CHARGE;

		String remark = "给"+ tran.getTransportion_name()+"的账户，"+chong+cash.toString()+"。";
		orderDealService.createOrderDeal(order.getOrderId(), orderDealType, remark,cash_success);
		
		return cash_success;
	}
	
	/**
	 * 运输公司消费，以及消费充红 ---扣除账户的操作
	 * @param order
	 * @return
     * @throws Exception 
	 */
	@Override
	public String consumeTransportion(SysOrder order) throws Exception{
		if (order ==null){
			throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
		}
		
		String credit_account = order.getCreditAccount();
		if(credit_account==null ||credit_account.equalsIgnoreCase("")){
			throw new Exception( GlobalConstant.OrderProcessResult.CREDIT_ACCOUNT_IS_NULL);
		}
		
		//从账户扣钱
		Transportion tran = this.queryTransportionByPK(credit_account);
		String tran_account = tran.getSys_user_account_id();
				
		BigDecimal cash = order.getCash();
		//订单流水
		String chong = "消费";
		String orderDealType = GlobalConstant.OrderDealType.CONSUME_TRANSPORTION_DEDUCT;
				
		String is_discharge = order.getIs_discharge();
		if(GlobalConstant.ORDER_ISCHARGE_YES.equalsIgnoreCase(is_discharge)){
			chong = "消费冲红";
			orderDealType = GlobalConstant.OrderDealType.DISCONSUME_TRANSPORTION_DEDUCT;
			//充红传过来必须是负值
			if(cash.compareTo(new BigDecimal(0)) > 0){
				throw new Exception(GlobalConstant.OrderProcessResult.DISCHARGE_ORDER_CASH_IS_NOT_NEGATIVE);
			}
		}
		
		//消费传过来的cash是正值，需要乘以-1,如果是充红，订单传过来的是负值，乘以-1，就成为正值。
		BigDecimal addCash = cash.multiply(new BigDecimal(-1));
		String cash_success = sysUserAccountService.addCashToAccount(tran_account,addCash,order.getOrderType());

		String remark =  tran.getTransportion_name()+"的账户，"+chong+cash.toString()+"。" + order.getDischarge_reason();
		orderDealService.createOrderDeal(order.getOrderId(), orderDealType, remark,cash_success);
		
		return cash_success;
	}
	/**
	 * 转账的时候，扣除运输公司账户金额
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public String transferTransportionToDriverDeductCash(SysOrder order,Transportion tran) throws Exception{
		if (order ==null){
			throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
		}
		if (tran ==null){
			throw new Exception( GlobalConstant.OrderProcessResult.TRANSPORTION_IS_NULL);
		}
		//从账户减钱
		String tran_account = tran.getSys_user_account_id();
		BigDecimal cash = order.getCash();
		//乘以-1，讲订单里面的cash变为负值，则就是减钱
		BigDecimal add_cash = cash.multiply(new BigDecimal(-1));
		String cash_success = sysUserAccountService.addCashToAccount(tran_account,add_cash,order.getOrderType());
		String orderDealType = GlobalConstant.OrderDealType.TRANSFER_TRANSPORTION_TO_DRIVER_DEDUCT_TRANSPORTION;

		String remark = "从"+ tran.getTransportion_name()+"的账户，扣款"+cash.toString()+"。";
		orderDealService.createOrderDeal(order.getOrderId(), orderDealType, remark,cash_success);
		
		return cash_success;
	}

	@Override
	public int updatedeposiTransport(Transportion transportion) throws Exception {
		return transportionMapper.updateByPrimaryKeySelective(transportion);
	}

	/**
	 * 修改运输公司的额度
	 * increment ---正值是增加，负值是减少
	 * @param transportion
	 * @return
	 * @throws Exception
	 */
	@Override
	public synchronized int modifyDeposit(Transportion transportion, BigDecimal increment) throws Exception {
		BigDecimal deposit = transportion.getDeposit();
		BigDecimal deposit_result = deposit.add(increment);
		if(deposit_result.compareTo(new BigDecimal(0)) < 0){
			throw new Exception(GlobalConstant.OrderProcessResult.ORDER_ERROR_PREPAY_IS_NOT_ENOUGH);
		}
		transportion.setDeposit(deposit_result);
		return transportionMapper.updateDeposit(transportion);
	}

	/**
	 * 修改运输公司额度
	 * @param transportion
	 * @return
	 * @throws Exception
     */
	@Override
	public int updateDeposit(Transportion transportion) throws Exception {
		return transportionMapper.updateDeposit(transportion);
	}

	@Override
	public int updatedeposiTransportion(SysDepositLog log, String operation) throws Exception {
		SysUserAccount account = new SysUserAccount();
		account.setSysUserAccountId(log.getAccountId());
		account.setDeposit(log.getDeposit());
//		int retbnum = sysUserAccountMapper.updateByPrimaryKeySelective(account);
		
		SysOrder order = new SysOrder();
		order.setOrderId(UUIDGenerator.getUUID());
		order.setDebitAccount(log.getStationId());
		order.setCash(log.getDeposit());
		order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_PLATFORM_CHARGE);
		order.setOperator(operation);
		order.setOrderDate(new Date());
		order.setOrderType(GlobalConstant.OrderType.CHARGE_TO_TRANSPORTION);
		order.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.TRANSPORTION);
		String orderNumber = orderService.createOrderNumber(GlobalConstant.OrderType.CHARGE_TO_TRANSPORTION);
		order.setOrderNumber(orderNumber);
		order.setChannel("综合管理平台");
		order.setChannelNumber("ZHGLPT");
		order.setIs_discharge("0");
		order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.PLATFORM);
		orderService.insert(order, null);
		orderService.chargeToTransportion(order);
		
		//写日志
		Transportion transportion = transportionMapper.selectByPrimaryKey(log.getStationId());
		log.setStationName(transportion.getTransportion_name());
		log.setOptime(new Date());
		log.setSysDepositLogId(UUIDGenerator.getUUID());
		log.setStation_type(GlobalConstant.OrderOperatorTargetType.TRANSPORTION);
		log.setOrder_number(orderNumber);
		if(StringUtils.isEmpty(log.getTransfer_photo())){
			Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
			String show_path = (String) prop.get("default_img");
			log.setTransfer_photo(show_path);
		}
		return sysDepositLogMapper.insert(log);
	}
	
	/**
	 * 余额提醒短信
	 * @return
	 * @throws Exception
	 */
	public Integer alertPrepayBalance() throws Exception{
		
		List<Transportion> list = transportionMapper.queryForPage(new Transportion());
		
		for(int i=0;i<list.size();i++){
			
			if(list.get(i).getDeposit().compareTo(BigDecimal.valueOf(2000)) == -1){
				AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
				aliShortMessageBean.setSendNumber(list.get(i).getContact_phone());
				aliShortMessageBean.setName(list.get(i).getDeposit().toString());
				AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.REMIND_BALANCE);
			}
		}
		
		return list.size();
	}

}
