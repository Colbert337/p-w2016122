package com.sysongy.api.mobileConsumeClient;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.client.controller.model.PayCodeValidModel;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.integral.model.IntegralHistory;
import com.sysongy.poms.integral.model.IntegralRule;
import com.sysongy.poms.integral.service.IntegralHistoryService;
import com.sysongy.poms.integral.service.IntegralRuleService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;
import com.sysongy.poms.ordergoods.model.SysOrderGoodsForCRMReport;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.model.SysOperationLog;
import com.sysongy.poms.system.service.SysOperationLogService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.tcms.advance.service.TcVehicleService;
import com.sysongy.util.*;
import com.sysongy.util.pojo.AliShortMessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/crmInterface/cashServiceInterface")
public class CashServiceInterface {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    private OrderService orderService;

    @Autowired
    private SysUserAccountService sysUserAccountService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    DriverService driverService;

    @Autowired
    OrderDealService orderDealService;

    @Autowired
    private GasCardService gasCardService;

    @Autowired
    private GastationService gastationService;

    @Autowired
    private TcFleetService tcFleetService;

    @Autowired
    private TransportionService transportionService;

    @Autowired
    private TcVehicleService tcVehicleService;

    @Autowired
    RedisClientInterface redisClientImpl;
	
    @Autowired
	SysOperationLogService sysOperationLogService;
    @Autowired
    IntegralRuleService integralRuleService;
    @Autowired
    IntegralHistoryService integralHistoryService;
    
    @ResponseBody
    @RequestMapping("/web/customerGasCharge")
    public AjaxJson customerGasCharge(HttpServletRequest request, HttpServletResponse response, String strRecord) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        SysOrder record = JSON.parseObject(strRecord, SysOrder.class);
        if((record == null) || StringUtils.isEmpty(record.getOrderId()) ||
                StringUtils.isEmpty(record.getOperatorSourceId()) ){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单ID或者气站ID为空！！！");
            return ajaxJson;
        }

        try{
            SysOrder sysOrders = orderService.selectByPrimaryKey(record.getOrderId());
            if(sysOrders != null){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该订单已存在，请勿提交重复订单！！！");
                return ajaxJson;
            }

            record.setIs_discharge("0");
            record.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
            record.setOrderType(GlobalConstant.OrderType.CHARGE_TO_DRIVER);
            record.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
            record.setOrderNumber(orderService.createOrderNumber(GlobalConstant.OrderType.CHARGE_TO_DRIVER));
            String orderCharge = orderService.chargeToDriver(record);
			//系统关键日志记录
			SysOperationLog newSysOperationLog = new SysOperationLog();
			newSysOperationLog.setOperation_type("cz");
			newSysOperationLog.setLog_platform("2");
			newSysOperationLog.setOrder_number(record.getOrderNumber());
			newSysOperationLog.setLog_content("加气站为司机充值成功！订单号为："+record.getOrderNumber()); 
			//操作日志
			sysOperationLogService.saveOperationLog(newSysOperationLog,record.getOperator());
			
            if(!orderCharge.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("订单充值错误：" + orderCharge);
                return ajaxJson;
            }
            Date curDate = new Date();
            record.setOrderDate(curDate);
            Gastation gastation = gastationService.queryGastationByPK(record.getOperatorSourceId());
            if(gastation != null){
                record.setChannel(gastation.getGas_station_name());
                record.setChannelNumber(gastation.getSys_gas_station_id());
            }

            int nCreateOrder = orderService.insert(record, null);
            if(nCreateOrder < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("订单生成错误：" + record.getOrderId());
                return ajaxJson;
            }

            SysOrder recordNew = orderService.selectByPrimaryKey(record.getOrderId());
            recordNew.setGastation(gastation);
            String cashBack = orderDealService.selectCashBackByOrderID(record.getOrderId());
            recordNew.setCashBack(cashBack);
            SysDriver sysDriver = driverService.queryDriverByPK(record.getDebitAccount());

            if((sysDriver != null) && !StringUtils.isEmpty(sysDriver.getMobilePhone())){
                recordNew.setSysDriver(sysDriver);
                GasCard gasCard = gasCardService.selectByCardNoForCRM(sysDriver.getCardId());
                if(gasCard != null){
                    recordNew.setGasCard(gasCard);
                }
            } else {
                logger.error("发送充值短信出错， mobilePhone：" + sysDriver.getMobilePhone());
            }

            recordNew.setCash(formatCash(recordNew.getCash()));
            sendChargeMessage(recordNew);
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("sysOrder", recordNew);
            ajaxJson.setAttributes(attributes);
            return ajaxJson;
        } catch (Exception e){
            logger.warn("账户充值异常：" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(e.getMessage());
            return ajaxJson;
        }

    }

    private BigDecimal formatCash(BigDecimal cash){
        if(cash == null)
            return null;
        BigDecimal cashNew = cash.setScale(2, BigDecimal.ROUND_DOWN);
        return cashNew;
    }

    private void sendChargeMessage(SysOrder recordNew){
        AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
        aliShortMessageBean.setSendNumber(recordNew.getSysDriver().getMobilePhone());
        if(recordNew.getOrderDate() != null){
            String curStrDate = DateTimeHelper.formatDateTimetoString(recordNew.getOrderDate(),
                    DateTimeHelper.FMT_yyyyMMddHHmmss);
            aliShortMessageBean.setTime(curStrDate);
        }
        aliShortMessageBean.setString("充值");
        aliShortMessageBean.setMoney(recordNew.getCash().toString());
        aliShortMessageBean.setBackCash(recordNew.getCashBack());
        aliShortMessageBean.setMoney1(recordNew.getSysDriver().getAccount().getAccountBalance());
        AliShortMessage.sendShortMessage(aliShortMessageBean,
                AliShortMessage.SHORT_MESSAGE_TYPE.TRANSPORTION_TRANSFER_SELF_CHARGE);
    }

    @ResponseBody
    @RequestMapping("/web/customerGasPay")
    public AjaxJson customerGasPay(HttpServletRequest request, HttpServletResponse response, String strRecord) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        try {
            SysOrder record = JSON.parseObject(strRecord, SysOrder.class);
            record.setIs_discharge("0");
            if((record == null) || StringUtils.isEmpty(record.getOrderId()) ||
                    StringUtils.isEmpty(record.getOperatorSourceId()) ){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("订单ID或者气站ID为空！！！");
                return ajaxJson;
            }

            record.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
            SysOrder sysOrders = orderService.selectByPrimaryKey(record.getOrderId());
            if(sysOrders != null){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该订单已存在，请勿提交重复订单！！！");
                return ajaxJson;
            }

            String payCode = request.getParameter("payCode");
            if(StringUtils.isEmpty(payCode)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("支付密码为空！！！");
                return ajaxJson;
            }

            String checkCode = request.getParameter("checkCode");
            GasCard gasCard = null;
            if(StringUtils.isEmpty(checkCode)){
                gasCard = gasCardService.selectByCardNoForCRM(record.getConsume_card());
            }

            SysDriver sysDriver = null;
            if((gasCard != null) && (gasCard.getCard_property().equalsIgnoreCase(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_TRANSPORTION))){
                sysDriver = convertSysDriver(record.getConsume_card());
                List<TcVehicle> vehicles = tcVehicleService.queryVehicleByCardNo(record.getConsume_card());
                if (vehicles.size() > 1) {
                    logger.error("查询出现多个车辆: " + record.getConsume_card());
                    return null;
                }

                if((vehicles == null) || (vehicles.get(0) == null)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("所属车队为空！！！");
                    return ajaxJson;
                }

                if(isLock24Hours(vehicles.get(0).getTcVehicleId())){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("支付密码已输错5次，账户已被冻结24小时！！！");
                    return ajaxJson;
                }

                if(!(vehicles.get(0).getPayCode().equalsIgnoreCase(payCode))){
                    addWrongTimes(vehicles.get(0).getTcVehicleId());
                    if(isWrong4Times(vehicles.get(0).getTcVehicleId())){
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("支付密码错误，已输入错误4次，输入5次错误后冻结账户24小时！！！");
                        return ajaxJson;
                    }
                    if(isWrong5Times(vehicles.get(0).getTcVehicleId())){
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("您输入秘密错误次数过多，导致账户被冻结24小时！！！");
                        return ajaxJson;
                    }
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("支付密码错误！！！");
                    return ajaxJson;
                } else {
                    redisClientImpl.deleteFromCache(vehicles.get(0).getTcVehicleId());
                }
            } else {
                sysDriver = driverService.queryDriverByPK(record.getCreditAccount());
                SysUserAccount creditAccount = sysUserAccountService.selectByPrimaryKey(sysDriver.getSysUserAccountId());
                if((creditAccount == null) || (sysDriver == null)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("司机或账户为空！！！");
                    return ajaxJson;
                }

                if(isLock24Hours(sysDriver.getAccount().getSysUserAccountId())){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("支付密码已输错5次，账户已被冻结24小时！！！");
                    return ajaxJson;
                }

                if(!(sysDriver.getPayCode().equalsIgnoreCase(payCode))){
                    addWrongTimes(sysDriver.getAccount().getSysUserAccountId());
                    if(isWrong4Times(sysDriver.getAccount().getSysUserAccountId())){
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("支付密码错误，已输入错误4次，输入5次错误后冻结账户24小时！！！");
                        return ajaxJson;
                    }
                    if(isWrong5Times(sysDriver.getAccount().getSysUserAccountId())){
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("您输入秘密错误次数过多，导致账户被冻结24小时！！！");
                        return ajaxJson;
                    }
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("支付密码错误！！！");
                    return ajaxJson;
                }else {
                    redisClientImpl.deleteFromCache(sysDriver.getMobilePhone());
                }

            }

            if(!StringUtils.isEmpty(checkCode)){
                record.setConsume_card(sysDriver.getCardId());
                String checkCodeFromRedis = (String)redisClientImpl.getFromCache
                        (sysDriver.getSysDriverId());
                if(StringUtils.isEmpty(checkCodeFromRedis)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("验证码已失效，请重新生成验证码！！！");
                    return ajaxJson;
                }
                if(StringUtils.isEmpty(checkCode)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("短信验证码为空！！！");
                    return ajaxJson;
                }
                if(!checkCode.equalsIgnoreCase(checkCodeFromRedis)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("短信验证码错误！！！");
                    return ajaxJson;
                }
            }

            if(gasCard != null){
                record.setGasCard(gasCard);
            }

            BigDecimal totalPrice = new BigDecimal(0);
            for(SysOrderGoods goods : record.getSysOrderGoods()){
                totalPrice = totalPrice.add(goods.getSumPrice());
            }

            record.setCash(totalPrice);
            sysDriver.setDriverType(GlobalConstant.DriverType.GAS_STATION);
            if((gasCard != null) && (gasCard.getCard_property().equalsIgnoreCase(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_TRANSPORTION))){
                record.setOrderType(GlobalConstant.OrderType.CONSUME_BY_TRANSPORTION);      //车队消费
                record.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.TRANSPORTION);

                TcFleet tcFleet = findFleetInfo(record.getConsume_card());      //如果车队为空，则直接消费运输公司资金
                List<TcVehicle> vehicles = tcVehicleService.queryVehicleByCardNo(record.getConsume_card());
                Transportion transportion = null;
                if (vehicles.size() > 0) {
                    transportion = transportionService.queryTransportionByPK(vehicles.get(0).getStationId());
                }
                if(transportion == null){
                    logger.error("所属运输公司无法查询:" + tcFleet.getStationId());
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("所属运输公司无法查询！！！");
                    return ajaxJson;
                }

                record.setOrderNumber(orderService.createOrderNumber(GlobalConstant.OrderType.CONSUME_BY_TRANSPORTION));
                String orderConsume = orderService.consumeByTransportion(record, transportion, tcFleet);
                if(!orderConsume.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("订单消费错误：" + orderConsume);
                    return ajaxJson;
                }
            } else {
                record.setOrderType(GlobalConstant.OrderType.CONSUME_BY_DRIVER);            //预付款消费
                record.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
                record.setOrderNumber(orderService.createOrderNumber(GlobalConstant.OrderType.CONSUME_BY_DRIVER));

                String cardID = record.getConsume_card();
                if(record.getConsumeType().equalsIgnoreCase(GlobalConstant.ConsumeType.CONSUME_TYPE_CARD)){
                    record.setConsume_card(null);
                }
                
                String orderConsume = orderService.consumeByDriver(record);
                //消费增加积分
        		if(null!=record.getOperator()&&!"".equals(record.getOperator())){
        			//充值成功发放积分
        			HashMap<String, String> integralMap =  integralRuleService.selectRepeatIntegralType("xf");
        			String integral_rule_id = integralMap.get("integral_rule_id");
        			IntegralRule integralRule = integralRuleService.queryIntegralRuleByPK(integral_rule_id);
        			//存在积分规则
        			if(null!=integralRule){
        					HashMap<String,String> xfHashMap = new HashMap<String,String>();
        					xfHashMap.put("reward_cycle", integralRule.getReward_cycle());
        					xfHashMap.put("debit_Account", record.getDebitAccount());
        					xfHashMap.put("order_id",record.getOrderId());
        					List<HashMap<String,String>> orderList = orderService.queryOrderByOperator(xfHashMap);
        					//当前日/周/月 存在的 司机注册数
        					if(orderList.size()>0){
        							HashMap<String, String> driverMap = orderList.get(0);
        							//充值成功向积分记录表中插入积分历史数据
        							if("true".equals(integralMap.get("STATUS"))&&"1".equals(String.valueOf(integralMap.get("integral_rule_num")))){
        								String llimitnumber = integralRule.getLimit_number();
        								String reward_cycle = integralRule.getReward_cycle();
        								String count = String.valueOf(driverMap.get("count"));
        							boolean nolimit="不限".equals(llimitnumber);
        							boolean pass= !"one".equals(reward_cycle)&&!nolimit&&(Integer.parseInt(count)>=Integer.parseInt(llimitnumber));	
        							boolean one = "one".equals(reward_cycle)&&(Integer.parseInt(count)-1==Integer.parseInt(llimitnumber));	
        								//如果不限则不判断，一次则数量比限制值大1条，否则只要比限制值多则都加
        									if(nolimit||one||pass){
        										IntegralHistory xfHistory = new IntegralHistory();
        										xfHistory.setIntegral_type("xf");
        										xfHistory.setIntegral_rule_id(integralMap.get("integral_rule_id"));
        										xfHistory.setSys_driver_id(record.getDebitAccount());
        										String[] ladder_before = integralMap.get("ladder_before").split(",");
        										String[] ladder_after = integralMap.get("ladder_after").split(",");
        										String[] integral_reward = integralMap.get("integral_reward").split(",");
        										String[] reward_factor = integralMap.get("reward_factor").split(",");
        										String[] reward_max = integralMap.get("reward_max").split(",");
        										String integralreward = "";
        										for(int i=0;i<ladder_before.length;i++){
        											//判断是否在阶梯的消费区间内
        											if(record.getCash().compareTo(new BigDecimal(ladder_before[i]))>=0&&record.getCash().compareTo(new BigDecimal(ladder_after[i]))<0){
        												if(null!=integral_reward[i]&&!"".equals(integral_reward[i])){
        													xfHistory.setIntegral_num(integral_reward[i]);
        													integralreward = integral_reward[i];
        												}else{
        													xfHistory.setIntegral_num((record.getCash().multiply(new BigDecimal(reward_factor[i]))).setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 
        													integralreward = (record.getCash().multiply(new BigDecimal(reward_factor[i]))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        													if(record.getCash().multiply(new BigDecimal(reward_factor[i])).compareTo(new BigDecimal(reward_max[i]))>0){
        														xfHistory.setIntegral_num(reward_max[i]);
        														integralreward = reward_max[i];
        													}
        												}
        											}
        										}
        										integralHistoryService.addIntegralHistory(xfHistory, record.getDebitAccount());
        										SysDriver driver = new SysDriver();
        										driver.setIntegral_num(integralreward);
        										driver.setSysDriverId(record.getDebitAccount());
        										driverService.updateDriverByIntegral(driver);				
        								}	
        						}					
        					}	
        				}				
        		}	
				//系统关键日志记录
    			SysOperationLog sysOperationLog = new SysOperationLog();
    			sysOperationLog.setOperation_type("xf");
    			sysOperationLog.setLog_platform("1");
        		sysOperationLog.setOrder_number(record.getOrderNumber());
        		sysOperationLog.setLog_content("司机消费成功！订单号为："+record.getOrderNumber()); 
    			//操作日志
    			sysOperationLogService.saveOperationLog(sysOperationLog,record.getOperator());
                if(!orderConsume.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("订单消费错误：" + orderConsume);
                    return ajaxJson;
                }
                record.setConsume_card(cardID);
            }

            Date curDate = new Date();
            record.setOrderDate(curDate);
            Gastation gastation = gastationService.queryGastationByPK(record.getOperatorSourceId());
            if(gastation != null){
                record.setChannel(gastation.getGas_station_name());
                record.setChannelNumber(gastation.getSys_gas_station_id());
            }
            int nCreateOrder = orderService.insert(record, record.getSysOrderGoods());
            if(nCreateOrder < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("订单消费生成错误：" + record.getOrderId());
                return ajaxJson;
            }

            SysOrder recordNew = orderService.selectByPrimaryKey(record.getOrderId());
            recordNew.setGastation(gastation);

            String mobilePhone = record.getSysDriver().getMobilePhone();
            if((sysDriver != null) && !StringUtils.isEmpty(sysDriver.getMobilePhone())){
                SysDriver sysDriverNew = null;
                if((gasCard != null) && (gasCard.getCard_property().equalsIgnoreCase(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_TRANSPORTION))){
                    sysDriverNew = convertSysDriver(record.getConsume_card());
                    mobilePhone = sysDriverNew.getMobilePhone();
                    sysDriverNew.setMobilePhone(record.getConsume_card());
                    sysDriverNew.setFullName("");
                    recordNew.setGasCard(record.getGasCard());
                } else {
                    sysDriverNew = driverService.queryDriverByPK(record.getCreditAccount());
                    recordNew.setGasCard(sysDriverNew.getCardInfo());
                }
                recordNew.setSysDriver(sysDriverNew);

            } else {
                logger.error("发送充值短信出错， mobilePhone：" + sysDriver.getMobilePhone());
            }

            recordNew.setCash(formatCash(record.getCash()));
            sendConsumeMessage(recordNew, mobilePhone);
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("sysOrder", recordNew);
            ajaxJson.setAttributes(attributes);
            return ajaxJson;
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单消费错误：" + e.getMessage());
            return ajaxJson;
        }
    }

    private boolean isLock24Hours(String id){
        boolean bRet = false;
        PayCodeValidModel payCodeValidModel = (PayCodeValidModel)redisClientImpl.getFromCache(id);
        if((payCodeValidModel != null) && (payCodeValidModel.getErrTimes() == 4)){
            bRet = true;
        }
        return bRet;
    }

    private boolean isWrong4Times(String id){
        boolean bRet = false;
        PayCodeValidModel payCodeValidModel = (PayCodeValidModel)redisClientImpl.getFromCache(id);
        if((payCodeValidModel != null) && (payCodeValidModel.getErrTimes() == 3)){
            bRet = true;
        }
        return bRet;
    }

    private boolean isWrong5Times(String id){
        boolean bRet = false;
        PayCodeValidModel payCodeValidModel = (PayCodeValidModel)redisClientImpl.getFromCache(id);
        if((payCodeValidModel != null) && (payCodeValidModel.getErrTimes() == 4)){
            bRet = true;
        }
        return bRet;
    }

    private boolean addWrongTimes(String id){
        boolean bRet = false;
        PayCodeValidModel payCodeValidModel = (PayCodeValidModel)redisClientImpl.getFromCache(id);
        if(payCodeValidModel == null){
            PayCodeValidModel payCodeValidModelInfo = new PayCodeValidModel();
            payCodeValidModelInfo.setId(id);
            payCodeValidModelInfo.setErrTimes(0);
            redisClientImpl.addToCache(id, payCodeValidModelInfo, 86400);
        } else {
            payCodeValidModel.setErrTimes(payCodeValidModel.getErrTimes() + 1);
            redisClientImpl.addToCache(id, payCodeValidModel, 86400);
        }
        return bRet;
    }

    private void sendConsumeMessage(SysOrder recordNew, String mobilePhone){
        AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
        aliShortMessageBean.setSendNumber(mobilePhone);
        if(recordNew.getOrderDate() != null){
            String curStrDate = DateTimeHelper.formatDateTimetoString(recordNew.getOrderDate(),
                    DateTimeHelper.FMT_yyyyMMddHHmmss);
            aliShortMessageBean.setTime(curStrDate);
        }
        aliShortMessageBean.setString("消费");
        aliShortMessageBean.setMoney(recordNew.getCash().toString());
        aliShortMessageBean.setMoney1(recordNew.getSysDriver().getAccount().getAccountBalance());
        AliShortMessage.sendShortMessage(aliShortMessageBean,
                AliShortMessage.SHORT_MESSAGE_TYPE.SELF_CHARGE_CONSUME_PREINPUT);
    }

    private TcFleet findFleetInfo(String cardID){
        if(org.apache.commons.lang.StringUtils.isEmpty(cardID)){
            return null;
        }
        try {
            List<TcVehicle> vehicles = tcVehicleService.queryVehicleByCardNo(cardID);
            if (vehicles.size() != 1) {
                logger.warn("查询出现无车辆或多个车辆: " + cardID);
                return null;
            }
            for (TcVehicle tcVehicle : vehicles) {
                List<TcFleet> tcFleets = tcFleetService.queryFleetByVehicleId(tcVehicle.getStationId(), tcVehicle.getTcVehicleId());
                if (tcFleets.size() != 1) {
                    logger.warn("查询无车队或者多个车队: " + tcVehicle.getTcVehicleId());
                    return null;
                }
                return tcFleets.get(0);
            }
        } catch (Exception e){
            logger.error("获取车队出错： " + e);
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/web/hedgeFund")
    public AjaxJson hedgeFund(HttpServletRequest request, HttpServletResponse response, SysOrder record) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        if((record == null) || StringUtils.isEmpty(record.getOrderId())
                || StringUtils.isEmpty(record.getOperatorSourceId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单ID或者气站ID为空！！！");
            return ajaxJson;
        }

        String curUserName = request.getParameter("suserName");
        String curPassword = request.getParameter("spassword");
        SysUser sysUser = new SysUser();
        sysUser.setUserName(curUserName);
        sysUser.setMobilePhone(curUserName);
        sysUser.setPassword(curPassword);
        sysUser.setUserType(InterfaceConstants.USER_TYPE_CRM_USER);
        SysUser user = sysUserService.queryUserMapByAccount(sysUser);
        if(user == null){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("用户名或密码错误，请重新登录！");
            return ajaxJson;
        }

        String adminUserName = request.getParameter("adminUserName");
        String adminPassword = request.getParameter("adminPassword");
        if((StringUtils.isEmpty(adminUserName)) || (StringUtils.isEmpty(adminPassword))){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("管理员用户名或密码为空！！！");
            return ajaxJson;
        }

        String checkCode = request.getParameter("inputCode");
        String checkCodeFromRedis = (String)redisClientImpl.getFromCache(record.getOrderId());
        if(StringUtils.isEmpty(checkCode)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("短信验证码为空！！！");
            return ajaxJson;
        }

        if(!checkCode.equalsIgnoreCase(checkCodeFromRedis)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("短信验证码错误！！！");
            return ajaxJson;
        }

        SysUser sysUserAdmin = new SysUser();
        sysUserAdmin.setMobilePhone(adminUserName);
        sysUserAdmin.setUserName(adminUserName);
        sysUserAdmin.setPassword(adminPassword);
        sysUserAdmin.setUserType(InterfaceConstants.USER_TYPE_CRM_USER);
        SysUser sysUserOperator = sysUserService.queryUserMapByAccount(sysUserAdmin);
        if((sysUserOperator == null) || (sysUserOperator.getIsAdmin() != 0) ||
                !(sysUserOperator.getStationId().equalsIgnoreCase(record.getOperatorSourceId()))){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("该用户无冲红权限！！！");
            return ajaxJson;
        }

        SysOrder originalOrder = orderService.selectByPrimaryKey(record.getOrderId());
        boolean canDischarge = orderService.checkCanDischarge(originalOrder);
        if(!canDischarge){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("该订单不符合冲红条件！！！");
            return ajaxJson;
        }
        SysOrder hedgeRecord = orderService.createDischargeOrderByOriginalOrder(originalOrder,
                user.getSysUserId(), record.getDischarge_reason());

        String bSuccessful = orderService.dischargeOrder(originalOrder, hedgeRecord);
        
		//系统关键日志记录
		SysOperationLog sysOperationLog = new SysOperationLog();
		sysOperationLog.setOperation_type("ch");
		sysOperationLog.setLog_platform("3");
		sysOperationLog.setOrder_number(originalOrder.getOrderNumber());
		sysOperationLog.setLog_content("CRM用户冲红订单成功！订单号为："+originalOrder.getOrderNumber()+"，冲红订单号为："+hedgeRecord.getOrderNumber()); 
		//操作日志
		sysOperationLogService.saveOperationLog(sysOperationLog,user.getSysUserId());
		
        if(!bSuccessful.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)){
            logger.error("订单冲红保存错误：" + originalOrder.getOrderId());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单冲红错误：" + originalOrder.getOrderId());
            return ajaxJson;
        }

        hedgeRecord.setIs_discharge("1");
        hedgeRecord.setBeen_discharged("1");
        hedgeRecord.setDischargeOrderId(originalOrder.getOrderId());

        int nRet = orderService.insert(hedgeRecord, originalOrder.getSysOrderGoods());
        if(nRet < 1){
            logger.error("订单冲红保存错误：" + originalOrder.getOrderId());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单冲红保存错误：" + originalOrder.getOrderId());
            return ajaxJson;
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysOrder", hedgeRecord);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    @ResponseBody
    @RequestMapping("/web/queryNotHedgeOrder")
    public AjaxJson queryNotHedgeOrder(HttpServletRequest request, HttpServletResponse response, SysOrder record) throws Exception {
        AjaxJson ajaxJson = new AjaxJson();
        if((record == null) || StringUtils.isEmpty(record.getOrderNumber()) ||
                StringUtils.isEmpty(record.getOperatorSourceId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单ID或气站ID为空！！！" );
            return ajaxJson;
        }
        SysOrder originalOrder = orderService.selectByOrderGASID(record);
        if(originalOrder == null){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("该订单不存在或已被冲红！！！" );
            return ajaxJson;
        }
        if(originalOrder.getOperatorTargetType().equalsIgnoreCase(GlobalConstant.OrderOperatorTargetType.DRIVER)){
            if(StringUtils.isEmpty(originalOrder.getCreditAccount())){
                SysDriver sysDriver = driverService.queryDriverByPK(originalOrder.getDebitAccount());
                originalOrder.setSysDriver(sysDriver);
            } else {
                SysDriver sysDriver = driverService.queryDriverByPK(originalOrder.getCreditAccount());
                originalOrder.setSysDriver(sysDriver);
            }
        } else {
            Transportion transportion = transportionService.queryTransportionByPK(originalOrder.getCreditAccount());
            List<TcVehicle> vehicles = tcVehicleService.queryVehicleByCardNo(originalOrder.getConsume_card());
            if (vehicles.size() > 1) {
                logger.error("查询出现多个车辆: " + originalOrder.getConsume_card());
                return null;
            }
            originalOrder.setTransportion(transportion);
            SysDriver sysDriver = new SysDriver();
            sysDriver.setCardId(originalOrder.getConsume_card());
            sysDriver.setMobilePhone(vehicles.get(0).getNoticePhone());
            sysDriver.setSysDriverId(vehicles.get(0).getTcVehicleId());
            originalOrder.setSysDriver(sysDriver);
        }
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysOrder", originalOrder);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    @ResponseBody
    @RequestMapping("/web/queryOrderChangeDeal")
    public AjaxJson queryOrderChangeDeal(HttpServletRequest request, HttpServletResponse response,
                                         SysOrderDeal sysOrderDeal) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();

        if(StringUtils.isEmpty(sysOrderDeal.getStationID())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("气站ID为空！！！" );
            return ajaxJson;
        }

        if(StringUtils.isEmpty(sysOrderDeal.getIsCharge())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("报表类型为空！！！" );
            return ajaxJson;
        }

        if(StringUtils.isEmpty(sysOrderDeal.getStorage_time_after())
                || StringUtils.isEmpty(sysOrderDeal.getStorage_time_before())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("起始时间或终止时间为空！！！" );
            return ajaxJson;
        }

        if((sysOrderDeal.getOperator() != null) && (sysOrderDeal.getOperator().equalsIgnoreCase
                (GlobalConstant.Query_Condition.QUERY_CONDITION_ALL))){
            sysOrderDeal.setOperator(null);
        }

        if((sysOrderDeal.getDealType() != null) && (sysOrderDeal.getDealType().equalsIgnoreCase
                (GlobalConstant.Query_Condition.QUERY_CONDITION_ALL))){
            sysOrderDeal.setDealType(null);
        }

        if((sysOrderDeal.getGoodType() != null) && (sysOrderDeal.getGoodType().equalsIgnoreCase
                (GlobalConstant.Query_Condition.QUERY_CONDITION_ALL))){
            sysOrderDeal.setGoodType(null);
        }

        List<SysOrderDeal> sysOrderDeals = orderDealService.queryOrderDealCRMs(sysOrderDeal);
        if((sysOrderDeals == null) || (sysOrderDeals.size() == 0)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("您所查询的数据为空！！！" );
            return ajaxJson;
        }

        List<SysOrderDeal> sysOrderDealInfos = new ArrayList<SysOrderDeal>();
        if(sysOrderDeal.getIsCharge().equalsIgnoreCase("0")){
            for(SysOrderDeal sysOrderDealInfo : sysOrderDeals){
                if(sysOrderDealInfo.getSysOrderInfo().getOrderType().equalsIgnoreCase
                        (GlobalConstant.OrderType.CONSUME_BY_DRIVER)){
                    SysDriver sysDriverInfo = driverService.queryDriverByPK(sysOrderDealInfo.getSysOrderInfo().getCreditAccount());
                    sysOrderDealInfo.setSysDriver(sysDriverInfo);
                } else {
                    sysOrderDealInfo = findSysOrderDeal(sysOrderDealInfo, sysOrderDealInfo.getSysOrderInfo().getConsume_card());
                }
                sysOrderDealInfos.add(sysOrderDealInfo);
            }
        } else {
            for(SysOrderDeal sysOrderDealInfo : sysOrderDeals){
                SysDriver sysDriverInfo = driverService.queryDriverByPK(sysOrderDealInfo.getSysOrderInfo().getDebitAccount());
                sysOrderDealInfo.setSysDriver(sysDriverInfo);
                sysOrderDealInfos.add(sysOrderDealInfo);
            }
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysOrderDeals", sysOrderDealInfos);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    private SysOrderDeal findSysOrderDeal(SysOrderDeal sysOrderDealInfo, String cardID){
        if(StringUtils.isEmpty(cardID)){
            return null;
        }
        try {
            SysDriver sysDriver = new SysDriver();
            List<TcVehicle> vehicles = tcVehicleService.queryVehicleByCardNo(cardID);
            if (vehicles.size() > 1) {
                logger.error("查询出现多个车辆: " + cardID);
                return null;
            }

            for (TcVehicle tcVehicle : vehicles) {
                sysDriver.setPlateNumber(tcVehicle.getPlatesNumber());
                sysDriver.setMobilePhone(tcVehicle.getNoticePhone());
                sysDriver.setFullName(tcVehicle.getUserName());
                sysOrderDealInfo.setSysDriver(sysDriver);
                return sysOrderDealInfo;
            }
        } catch (Exception e){
            logger.error("获取车队出错： " + e);
            e.printStackTrace();
        }
        return null;
    }

    private SysDriver convertSysDriver(String cardID){
        if(StringUtils.isEmpty(cardID)){
            return null;
        }
        try {
                SysDriver sysDriver = new SysDriver();
                List<TcVehicle> vehicles = tcVehicleService.queryVehicleByCardNo(cardID);
                if (vehicles.size() > 1) {
                    logger.error("查询出现多个车辆: " + cardID);
                    return null;
                }

                for (TcVehicle tcVehicle : vehicles) {
                    sysDriver.setPlateNumber(tcVehicle.getPlatesNumber());
                    sysDriver.setMobilePhone(tcVehicle.getNoticePhone());
                    sysDriver.setFullName(tcVehicle.getUserName());
                    List<TcFleet> tcFleets = tcFleetService.queryFleetByVehicleId(tcVehicle.getStationId(), tcVehicle.getTcVehicleId());
                    if((tcFleets == null) || (tcFleets.size() == 0)){
                        sysDriver.setFullName("");
                        Transportion transportion = transportionService.queryTransportionByPK(tcVehicle.getStationId());
                        SysUserAccount sysUserAccount = new SysUserAccount();
                        sysUserAccount.setAccountBalance(transportion.getDeposit().toString());
                        sysDriver.setAccount(sysUserAccount);
                        return sysDriver;
                    }

                    if(tcFleets.size() > 1){
                        logger.error("查询出现多个车队: " + tcVehicle.getTcVehicleId());
                        return null;
                    }

                    for(TcFleet tcFleet : tcFleets){
                        SysUserAccount sysUserAccount = new SysUserAccount();
                        if((tcFleet != null) && (tcFleet.getIsAllot() == GlobalConstant.TCFLEET_IS_ALLOT_YES)){
                            sysUserAccount.setAccountBalance(tcFleet.getQuota().toString());
                            sysDriver.setAccount(sysUserAccount);
                        } else {
                            Transportion transportion = transportionService.queryTransportionByPK(tcVehicle.getStationId());
                            sysUserAccount.setAccountBalance(transportion.getDeposit().toString());
                        }
                        sysDriver.setAccount(sysUserAccount);
                        return sysDriver;
                    }
                }
            } catch(Exception e){
                logger.error("获取车队出错： " + e);
                e.printStackTrace();
            }
        return null;
    }

    @ResponseBody
    @RequestMapping("/web/queryGoodsOrderInfos")
    public AjaxJson queryGoodsOrderInfos(HttpServletRequest request, HttpServletResponse response,
                                         SysOrderGoodsForCRMReport sysOrderGoodsForCRMReport) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        if(StringUtils.isEmpty(sysOrderGoodsForCRMReport.getOperatorSourceId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("气站ID为空！！！" );
            return ajaxJson;
        }

        if(StringUtils.isEmpty(sysOrderGoodsForCRMReport.getStorage_time_after())
                || StringUtils.isEmpty(sysOrderGoodsForCRMReport.getStorage_time_before())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("起始时间或终止时间为空！！！" );
            return ajaxJson;
        }

        if((sysOrderGoodsForCRMReport.getGoodsType() != null) && (sysOrderGoodsForCRMReport.getGoodsType().equalsIgnoreCase
                (GlobalConstant.Query_Condition.QUERY_CONDITION_ALL))){
            sysOrderGoodsForCRMReport.setGoodsType(null);
        }


        if((sysOrderGoodsForCRMReport.getOperator() != null) && (sysOrderGoodsForCRMReport.getOperator().equalsIgnoreCase
                (GlobalConstant.Query_Condition.QUERY_CONDITION_ALL))){
            sysOrderGoodsForCRMReport.setOperator(null);
        }

        List<SysOrderGoodsForCRMReport> sysOrderGoodsForCRMReports =
                    orderService.queryGoodsOrderInfos(sysOrderGoodsForCRMReport);
        if((sysOrderGoodsForCRMReports == null) || (sysOrderGoodsForCRMReports.size() == 0)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("您所查询的数据为空！！！" );
            return ajaxJson;
        }

        List<SysOrderGoodsForCRMReport> sysOrderGoodsForCRMReportInfos = new ArrayList<SysOrderGoodsForCRMReport>();
        for(SysOrderGoodsForCRMReport goodsForCRMReport : sysOrderGoodsForCRMReports){
            if(goodsForCRMReport.getOrderType().equalsIgnoreCase
                    (GlobalConstant.OrderType.CONSUME_BY_DRIVER)){
                SysDriver sysDriverInfo = driverService.queryDriverByPK(goodsForCRMReport.getCreditAccount());
                goodsForCRMReport.setSysDriver(sysDriverInfo);
            } else {
                goodsForCRMReport = findSysOrderCRMReport(goodsForCRMReport, goodsForCRMReport.getConsume_card());
            }
            sysOrderGoodsForCRMReportInfos.add(goodsForCRMReport);
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysOrderGoodsForCRMReports", sysOrderGoodsForCRMReportInfos);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    private SysOrderGoodsForCRMReport findSysOrderCRMReport(SysOrderGoodsForCRMReport sysOrderGoodsForCRMReport, String cardID){
        if(StringUtils.isEmpty(cardID)){
            return null;
        }
        try {
            SysDriver sysDriver = new SysDriver();
            List<TcVehicle> vehicles = tcVehicleService.queryVehicleByCardNo(cardID);
            if (vehicles.size() > 1) {
                logger.error("查询出现多个车辆: " + cardID);
                return null;
            }

            for (TcVehicle tcVehicle : vehicles) {
                sysDriver.setPlateNumber(tcVehicle.getPlatesNumber());
                sysDriver.setMobilePhone(tcVehicle.getNoticePhone());
                sysDriver.setFullName(tcVehicle.getUserName());
                sysOrderGoodsForCRMReport.setSysDriver(sysDriver);
                return sysOrderGoodsForCRMReport;
            }
        } catch (Exception e){
            logger.error("获取车队出错： " + e);
            e.printStackTrace();
        }
        return null;
    }
}
