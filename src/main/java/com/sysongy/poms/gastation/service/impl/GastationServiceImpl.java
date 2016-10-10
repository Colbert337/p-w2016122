package com.sysongy.poms.gastation.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.dao.GastationMapper;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.order.dao.SysPrepayMapper;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysPrepay;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.dao.SysDepositLogMapper;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.UUIDGenerator;
import com.sysongy.util.pojo.AliShortMessageBean;

@Service
public class GastationServiceImpl implements GastationService {
	
	@Autowired
	private GastationMapper gasStationMapper;
	@Autowired
	private SysUserAccountMapper sysUserAccountMapper;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private UsysparamService usysparamService;
	@Autowired
	private SysDepositLogMapper sysDepositLogMapper;
	
	
	@Autowired
	private SysPrepayMapper sysPrepayMapper;

	@Autowired
	private OrderDealService orderDealService;
	
	@Autowired
	private OrderService orderService;


	@Override
	public PageInfo<Gastation> queryGastation(Gastation record) throws Exception {
		
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Gastation> list = gasStationMapper.queryForPage3(record);
		PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(list);
		
		return pageInfo;
	}

	@Override
	public List<Gastation> getAllStationList(Gastation record) throws Exception {
		List<Gastation> list = gasStationMapper.queryForPage3(record);
		return list;
	}

	@Override
	public String saveGastation(Gastation record, String operation) throws Exception {
		
		boolean statusChanged = false;
		
		if("insert".equals(operation)){
			Gastation station = gasStationMapper.findGastationid("GS"+record.getStation_level()+record.getProvince_id());
			String newid;
			
			if(station == null || StringUtils.isEmpty(station.getSys_gas_station_id())){
				newid = "GS"+record.getStation_level()+record.getProvince_id() + "0001";
			}else{
				Integer tmp = Integer.valueOf(station.getSys_gas_station_id().substring(6, 10)) + 1;
				newid = "GS"+record.getStation_level()+record.getProvince_id() +StringUtils.leftPad(tmp.toString() , 4, "0");
			}
			//初始化钱袋信息
			SysUserAccount sysUserAccount = new SysUserAccount();
			sysUserAccount.setSysUserAccountId(UUIDGenerator.getUUID());
			sysUserAccount.setAccountCode("GS"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
			sysUserAccount.setAccountType(GlobalConstant.AccounType.GASTATION);
			sysUserAccount.setAccountBalance("0.0");
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
			record.setSys_gas_station_id(newid);
			record.setIndu_com_certif(show_path);
			record.setTax_certif(show_path);
			record.setLng_certif(show_path);
			record.setDcp_certif(show_path);
			record.setPrepay_balance(new BigDecimal(0));
			record.setPrepay_version(1);
			record.setType(GlobalConstant.StationType.UNION);
			
			gasStationMapper.insert(record);
			//创建管理员
			SysUser user = new SysUser();
			user.setUserName(record.getAdmin_username());
			user.setPassword(record.getAdmin_userpassword());
			user.setUserType(GlobalConstant.USER_TYPE_STATION);

			// 给用户关联站点编号
			user.setStationId(newid);
			user.setIsAdmin(0);//管理员
			sysUserService.addAdminUser(user);
			//同步系统参数字典表
			Usysparam usysparam = new Usysparam();
			usysparam.setGcode("WORKSTATION");
			usysparam.setMcode(record.getSys_gas_station_id());
			usysparam.setMname(record.getGas_station_name());
			usysparam.setScode("");
			usysparamService.saveUsysparam(usysparam);
			
			return newid;
		}else{
			if(!StringUtils.isEmpty(record.getExpiry_date_frompage())){
				record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			}
			record.setUpdated_time(new Date());
			Gastation mergeobj = gasStationMapper.selectByPrimaryKey(record.getSys_gas_station_id());
			if(!mergeobj.getStatus().equals(record.getStatus())){
				statusChanged = true;
			}
			gasStationMapper.updateByPrimaryKeySelective(record);
			//更新系统参数字典表
			Usysparam usysparam = new Usysparam();
			usysparam.setGcode("WORKSTATION");
			usysparam.setMcode(record.getSys_gas_station_id());
			if(StringUtils.isEmpty(record.getGas_station_name())){
				Gastation tmp = gasStationMapper.selectByPrimaryKey(record.getSys_gas_station_id());
				usysparam.setMname(tmp.getGas_station_name());
			}else{
				usysparam.setMname(record.getGas_station_name());
			}
		
			usysparam.setScode("");
			usysparamService.updateUsysparam(usysparam);
			
			//维护系统参数表
			if(GlobalConstant.StationStatus.PAUSE.equals(record.getStatus())){
				usysparamService.deleteUsysparam(usysparam);
			}else{
				Usysparam tmp = usysparamService.queryUsysparamByCode("WORKSTATION", record.getSys_gas_station_id());
				if(tmp == null){
					usysparamService.saveUsysparam(usysparam);
				}
			}
			
			if(statusChanged && GlobalConstant.StationStatus.PAUSE.equals(record.getStatus())){
				AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
				aliShortMessageBean.setSendNumber(record.getContact_phone());
				aliShortMessageBean.setName(record.getGas_station_name());
				AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.GAS_STATION_FROZEN);
			}
			
			return record.getSys_gas_station_id();
		}
	}

	@Override
	public Integer delGastation(String gastationid) throws Exception {
		
		Gastation gastation = gasStationMapper.selectByPrimaryKey(gastationid);
		//删除对应的管理员用户
		SysUser user = new SysUser();
		user.setUserName(gastation.getAdmin_username());
		user.setUserType(GlobalConstant.USER_TYPE_STATION);
		user.setStatus(GlobalConstant.STATUS_DELETE);
		sysUserService.updateUserByName(user);
		//删除对应的系统参数字典表
		Usysparam usysparam = new Usysparam();
		usysparam.setGcode("WORKSTATION");
		usysparam.setMcode(gastation.getSys_gas_station_id());
		usysparamService.deleteUsysparam(usysparam);
		
		return gasStationMapper.deleteByPrimaryKey(gastationid);
	}

	@Override
	public List<Gastation> getAllStationByArea(String areacode) throws Exception {
		return gasStationMapper.getAllStationByArea(areacode);
	}

	@Override
	public Gastation queryGastationByPK(String gastationid) throws Exception {
		 
		Gastation station =  gasStationMapper.selectByPrimaryKey(gastationid);
		 
		if(station != null){
			station.setExpiry_date_frompage(new SimpleDateFormat("yyyy-MM-dd").format(station.getExpiry_date()));
			SysUserAccount account = sysUserAccountMapper.selectByPrimaryKey(station.getSys_user_account_id());
			station.setAccount(account);
		}

		return station;
	}

	@Override
	public int updatedepositGastation(SysDepositLog log, String operation) throws Exception {
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
		order.setOrderType(GlobalConstant.OrderType.CHARGE_TO_GASTATION);
		order.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.GASTATION);
		String orderNumber = orderService.createOrderNumber(GlobalConstant.OrderType.CHARGE_TO_GASTATION);
		order.setOrderNumber(orderNumber);
		order.setChannel("综合管理平台");
		order.setChannelNumber("ZHGLPT");
		order.setIs_discharge("0");
		order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.PLATFORM);
		orderService.insert(order, null);
		orderService.chargeToGasStation(order);
		
		//写日志
		log.setOptime(new Date());
		Gastation gastation = gasStationMapper.selectByPrimaryKey(log.getStationId());
		log.setStationName(gastation.getGas_station_name());
		log.setSysDepositLogId(UUIDGenerator.getUUID());
		log.setStation_type(GlobalConstant.OrderOperatorTargetType.GASTATION);
		log.setOrder_number(orderNumber);
		if(StringUtils.isEmpty(log.getTransfer_photo())){
			Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
			String show_path = (String) prop.get("default_img");
			log.setTransfer_photo(show_path);
		}
		return sysDepositLogMapper.insert(log);
	}

	/**
	 * 更新气站的预付款余额。
	 * addCash如果是负值，则是消费的时候减少预付款， 如果是正值则是增加预付款
	 */
	public synchronized String updatePrepayBalance(Gastation obj, BigDecimal addCash) throws Exception{
		BigDecimal prepay_balance = obj.getPrepay_balance();
		BigDecimal new_prepay_balance = prepay_balance.add(addCash);
		if (new_prepay_balance.compareTo(new BigDecimal(0)) <0 ){
			throw new Exception(GlobalConstant.OrderProcessResult.ORDER_ERROR_PREPAY_IS_NOT_ENOUGH);
		}
		Integer prepay_version =  obj.getPrepay_version();
		obj.setPrepay_version(prepay_version.intValue()+1);
		obj.setPrepay_balance(new_prepay_balance);
	    int ret_int = gasStationMapper.updatePrepayBalance(obj);
	    if(ret_int<1){
	    	throw new Exception(GlobalConstant.OrderProcessResult.ORDER_ERROR_UPDATE_GASTATION_PREYPAY_ERROR);
	    }
		return GlobalConstant.OrderProcessResult.SUCCESS;
	}

	/**
	 * 向司机充值的时候，更新加注站预付款余额：分为充值和充红
	 * @return
	 */
	public String chargeToDriverUpdateGastationPrepay(SysOrder order, String is_discharge) throws Exception{
		   String operator_source_type = order.getOperatorSourceType();
		   if(GlobalConstant.OrderOperatorSourceType.GASTATION.equalsIgnoreCase(operator_source_type)){
			   String gasId = order.getOperatorSourceId();
			   if(gasId==null || gasId.equalsIgnoreCase("")){
				   throw new Exception( GlobalConstant.OrderProcessResult.CHARGE_TO_DRIVER_BY_CASH_OPERATOR_SOURCE_TYPE_IS_NULL);
			   }
			   Gastation gastation = queryGastationByPK(order.getOperatorSourceId());
			   BigDecimal prepay = gastation.getPrepay_balance();
			   BigDecimal cash = order.getCash();
			   if(cash.compareTo(prepay) > 0){
				   throw new Exception( GlobalConstant.OrderProcessResult.ORDER_ERROR_PREPAY_IS_NOT_ENOUGH);
			   }
			   //减少预付款，并增加预付款操作记录。将订单中的金额cash修改为负数，因为是要减少预付款---如果是充红，则刚好进行了相反操作，变成正值。正确
		 	   BigDecimal addCash = cash.multiply(new BigDecimal(-1));
		 	   String updateBalance_success =  updatePrepayBalance(gastation, addCash);
		 	   //增加订单操作流程：
			 	String orderDealType = GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_DEDUCT_GASTATION_PREPAY;
				String remark = "因司机充值，修改"+ gastation.getGas_station_name()+"的预付款，减少金额"+cash.toString()+"。";
			    if(GlobalConstant.ORDER_ISCHARGE_YES.equalsIgnoreCase(is_discharge)){
			 		orderDealType = GlobalConstant.OrderDealType.DISCHARGE_TO_DRIVER_DEDUCT_GASTATION_PREPAY;
			 		remark = "因司机充红，修改"+ gastation.getGas_station_name()+"的预付款，增加金额"+addCash.toString()+"。";
			 	}
			   orderDealService.createOrderDeal(order.getOrderId(), orderDealType, remark,updateBalance_success);
		 	   if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(updateBalance_success)){
		 		   //如果出错直接返回错误代码退出
		 		  throw new Exception( updateBalance_success);
		 	   }
		 	   //增加预付款操作历史流程：
		 	   SysPrepay sysPrepay = new SysPrepay();
			   sysPrepay.setPrepayId(UUIDGenerator.getUUID());
			   sysPrepay.setPayerId(order.getDebitAccount());
			   sysPrepay.setCash(addCash);
			   sysPrepay.setPayDate(new Date());
			   if(GlobalConstant.ORDER_ISCHARGE_YES.equalsIgnoreCase(is_discharge)){
				   sysPrepay.setOperateType(GlobalConstant.SysPrepayOperate.DISCHARGE_TO_DRIVER_DEDUCT);
			   }else{
				   sysPrepay.setOperateType(GlobalConstant.SysPrepayOperate.CHARGE_TO_DRIVER_DEDUCT);
			   }
			   if(GlobalConstant.ORDER_ISCHARGE_YES.equalsIgnoreCase(is_discharge)){
				  remark = "因司机现金充红，增加"+ gastation.getGas_station_name()+"的预付款"+addCash.toString()+"。";
			   }else{
			      remark = "因司机现金充值，抵扣"+ gastation.getGas_station_name()+"的预付款"+cash.toString()+"。";
			   }
			   sysPrepay.setRemark(remark);
			   sysPrepayMapper.insert(sysPrepay);
		   }else{
			   throw new Exception("此订单的操作发起者不是加注站。");
		   }
		   return GlobalConstant.OrderProcessResult.SUCCESS;
	}
	
	/**
	 * 余额提醒短信
	 * @return
	 * @throws Exception
	 */
	public Integer alertPrepayBalance() throws Exception{
		List<Gastation> list = gasStationMapper.queryForPage(new Gastation());
		
		for(int i=0;i<list.size();i++){
			AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
			aliShortMessageBean.setSendNumber(list.get(i).getContact_phone());
			aliShortMessageBean.setName(list.get(i).getPrepay_balance().toString());

			AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.REMIND_BALANCE);
		}
		
		return list.size();
	}

	@Override
	public PageInfo<Map<String, Object>> gastionConsumeReport(SysOrder record) throws Exception{
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Map<String, Object>> list = gasStationMapper.gastionConsumeReport(record);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<Map<String, Object>> gastionConsumeReportTotal(SysOrder record) throws Exception{
		PageHelper.startPage(1, 20, record.getOrderby());
		List<Map<String, Object>> list = gasStationMapper.gastionConsumeReportTotal(record);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<Map<String, Object>> gastionRechargeReport(SysOrder record) throws Exception{
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Map<String, Object>> list = gasStationMapper.gastionRechargeReport(record);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<Map<String, Object>> gastionRechargeReportTotal(SysOrder record) throws Exception {
		PageHelper.startPage(1, 20, record.getOrderby());
		List<Map<String, Object>> list = gasStationMapper.gastionRechargeReportTotal(record);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<Gastation> queryGastation2(Gastation record) throws Exception {

		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Gastation> list = gasStationMapper.queryForPage2(record);
		PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(list);

		return pageInfo;
	}

	@Override
	public void insert(Gastation gas) throws Exception {
		// TODO Auto-generated method stub
		
		Usysparam param=new Usysparam();
		List<Usysparam> list=new ArrayList<>();
		param.setGcode("PROVINCE_CODE");
		param.setMname(gas.getProvince_id());
		list=usysparamService.queryAll(param);
		if (list.size()>0) {
			param=list.get(0);
		}
		gas.setProvince_id(param.getMcode());
		param=new Usysparam();
		list=new ArrayList<>();
//		param.setGcode("STATION_MAP_TYPE");
//		param.setMname(gas.getMap_type());
//		list=usysparamService.queryAll(param);
//		if (list.size()>0) {
//			param=list.get(0);
//		}
//		gas.setMap_type(param.getMcode());
		
		Gastation station = gasStationMapper
				.findGastationid("GS" + 4+ gas.getProvince_id());
		String newid;

		if (station == null || StringUtils.isEmpty(station.getSys_gas_station_id())) {
			newid = "GS" + 4 + gas.getProvince_id() + "0001";
		} else {
			Integer tmp = Integer.valueOf(station.getSys_gas_station_id().substring(6, 10)) + 1;
			newid = "GS" + 4 + gas.getProvince_id()
					+ StringUtils.leftPad(tmp.toString(), 4, "0");
		}
		gas.setSys_gas_station_id(newid);
		gas.setType("1");
		gasStationMapper.insert(gas);
	}

	@Override
	public void update(Gastation gas) {
		// TODO Auto-generated method stub
		
		Usysparam param=new Usysparam();
		List<Usysparam> list=new ArrayList<>();
		param.setGcode("PROVINCE_CODE");
		param.setMname(gas.getProvince_id());
		list=usysparamService.queryAll(param);
		if (list.size()>0) {
			param=list.get(0);
		}
		gas.setProvince_id(param.getMcode());
		param=new Usysparam();
		list=new ArrayList<>();
//		param.setGcode("STATION_MAP_TYPE");
//		param.setMname(gas.getMap_type());
//		list=usysparamService.queryAll(param);
//		if (list.size()>0) {
//			param=list.get(0);
//		}
//		gas.setMap_type(param.getMcode());
		gas.setType("1");
		gasStationMapper.updateByPrimaryKeySelective2(gas);
	}

	/**
	 * 判断对象是否存在 适用于execl导入数据 数据表type为1
	 */
	@Override
	public boolean exists(String gas) throws Exception {
		// TODO Auto-generated method stub
		List<Gastation> list = gasStationMapper.exists(gas);
		if (list.size() != 0) {
			return false;
		}
		return true;
	}

	@Override
	public int delete(String gas) {
		// TODO Auto-generated method stub
		
		return gasStationMapper.delete(gas);
	}

	@Override
	public int updateByPrimaryKeySelective(Gastation record) throws Exception {
		// TODO Auto-generated method stub
		return gasStationMapper.updateByPrimaryKeySelective(record);
	}
}
