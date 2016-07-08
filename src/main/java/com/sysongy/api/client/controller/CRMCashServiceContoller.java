package com.sysongy.api.client.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
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
@RequestMapping("/crmCashServiceContoller")
public class CRMCashServiceContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    private OrderService orderService;

    @Autowired
    private SysUserAccountService sysUserAccountService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    RedisClientInterface redisClientImpl;

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

        SysOrder sysOrders = orderService.selectByPrimaryKey(record.getOrderId());
        if(sysOrders != null){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("该订单已存在，请勿提交重复订单！！！");
            return ajaxJson;
        }

        record.setOrderType(GlobalConstant.OrderType.CHARGE_TO_DRIVER);
        record.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
        String orderCharge = orderService.chargeToDriver(record);
        if(!orderCharge.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单充值错误：" + orderCharge);
            return ajaxJson;
        }
        Date curDate = new Date();
        String curStrDate = DateTimeHelper.formatDateTimetoString(curDate, DateTimeHelper.FMT_YYMMddhhmmsssss_noseparator);
        record.setOrderNumber("130"+ curStrDate);
        record.setOrderDate(curDate);
        int nCreateOrder = orderService.insert(record);
        if(nCreateOrder < 1){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单生成错误：" + record.getOrderId());
            return ajaxJson;
        }

        SysOrder recordNew = orderService.selectByPrimaryKey(record.getOrderId());
        String cashBack = orderDealService.selectCashBackByOrderID(record.getOrderId());
        recordNew.setCashBack(cashBack);
        SysDriver sysDriver = driverService.queryDriverByPK(record.getDebitAccount());
        Gastation gastation = gastationService.queryGastationByPK(record.getOperatorSourceId());
        if(gastation != null){
            recordNew.setGastation(gastation);
        }
        if((sysDriver != null) && !StringUtils.isEmpty(sysDriver.getMobilePhone())){
            recordNew.setSysDriver(sysDriver);
            GasCard gasCard = gasCardService.selectByCardNoForCRM(sysDriver.getCardId());
            if(gasCard != null){
                recordNew.setGasCard(gasCard);
            }
            AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
            aliShortMessageBean.setSendNumber(sysDriver.getMobilePhone());
            aliShortMessageBean.setProduct("司集能源科技平台");
            AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_CHANGE_PASSWORD);
        } else {
            logger.error("发送充值短信出错， mobilePhone：" + sysDriver.getMobilePhone());
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysOrder", recordNew);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    @ResponseBody
    @RequestMapping("/web/customerGasPay")
    public AjaxJson customerGasPay(HttpServletRequest request, HttpServletResponse response, String strRecord) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        SysOrder record = JSON.parseObject(strRecord, SysOrder.class);
        if((record == null) || StringUtils.isEmpty(record.getOrderId()) ||
                StringUtils.isEmpty(record.getOperatorSourceId()) ){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单ID或者气站ID为空！！！");
            return ajaxJson;
        }

        PageInfo<SysOrder> sysOrders = orderService.queryOrders(record);
        if((sysOrders == null) || (sysOrders.getList().size() > 0)){
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

        SysDriver sysDriver = driverService.queryDriverByPK(record.getCreditAccount());
        SysUserAccount creditAccount = sysUserAccountService.selectByPrimaryKey(sysDriver.getSysUserAccountId());
        if((creditAccount == null) || (sysDriver == null)
                || !(sysDriver.getPayCode().equalsIgnoreCase(payCode))){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("支付密码错误！！！");
            return ajaxJson;
        }

        String checkCode = request.getParameter("checkCode");
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

        GasCard gasCard = null;
        if(StringUtils.isEmpty(checkCode)){
            gasCard = gasCardService.selectByCardNoForCRM(sysDriver.getCardId());
        } else {
            gasCard = gasCardService.selectByCardNoForCRM(record.getConsume_card());
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
        if(gasCard.getCard_property().equalsIgnoreCase(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_TRANSPORTION)){
            record.setOrderType(GlobalConstant.OrderType.CONSUME_BY_TRANSPORTION);      //车队消费
            record.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.TRANSPORTION);
            TcFleet tcFleet = findFleetInfo(record.getConsume_card());
            if(tcFleet == null){
                logger.error("所属车队无法查询:" + record.getConsume_card());
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("所属车队无法查询！！！");
                return ajaxJson;
            }

            Transportion transportion = transportionService.queryTransportionByPK(tcFleet.getStationId());
            if(transportion == null){
                logger.error("所属运输公司无法查询:" + tcFleet.getStationId());
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("所属运输公司无法查询！！！");
                return ajaxJson;
            }

            String orderConsume = orderService.consumeByTransportion(record, transportion, tcFleet);
            if(!orderConsume.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("订单消费错误：" + orderConsume);
                return ajaxJson;
            }
        } else {
            record.setOrderType(GlobalConstant.OrderType.CONSUME_BY_DRIVER);            //预付款消费
            record.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
            String orderConsume = orderService.consumeByDriver(record);
            if(!orderConsume.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("订单消费错误：" + orderConsume);
                return ajaxJson;
            }
        }

        Date curDate = new Date();
        String curStrDate = DateTimeHelper.formatDateTimetoString(curDate, DateTimeHelper.FMT_YYMMddhhmmsssss_noseparator);
        record.setOrderNumber("220"+ curStrDate);
        record.setOrderDate(curDate);
        int nCreateOrder = orderService.insert(record);
        if(nCreateOrder < 1){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单消费生成错误：" + record.getOrderId());
            return ajaxJson;
        }

        SysOrder recordNew = orderService.selectByPrimaryKey(record.getOrderId());
        recordNew.setCash(record.getCash());
        recordNew.setGasCard(record.getGasCard());
        Gastation gastation = gastationService.queryGastationByPK(record.getOperatorSourceId());
        if(gastation != null){
            recordNew.setGastation(gastation);
        }
        if((sysDriver != null) && !StringUtils.isEmpty(sysDriver.getMobilePhone())){
            SysDriver sysDriverNew = driverService.queryDriverByPK(record.getCreditAccount());
            recordNew.setSysDriver(sysDriverNew);
            AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
            aliShortMessageBean.setSendNumber(sysDriver.getMobilePhone());
            aliShortMessageBean.setProduct("司集能源科技平台");
            AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_CHANGE_PASSWORD);
        } else {
            logger.error("发送充值短信出错， mobilePhone：" + sysDriver.getMobilePhone());
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysOrder", recordNew);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    private TcFleet findFleetInfo(String cardID){
        if(org.apache.commons.lang.StringUtils.isEmpty(cardID)){
            return null;
        }
        try {
            List<TcVehicle> vehicles = tcVehicleService.queryVehicleByCardNo(cardID);
            if (vehicles.size() > 0) {
                logger.error("查询出现多个车辆: " + cardID);
                return null;
            }
            for (TcVehicle tcVehicle : vehicles) {
                List<TcFleet> tcFleets = tcFleetService.queryFleetByVehicleId(tcVehicle.getStationId(), tcVehicle.getTcVehicleId());
                if (tcFleets.size() > 0) {
                    logger.error("查询出现多个车队: " + tcVehicle.getTcVehicleId());
                    return null;
                }
                return tcFleets.get(1);
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
        if((record == null) || StringUtils.isEmpty(record.getOrderId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单ID为空！！！");
            return ajaxJson;
        }

        String curUserName = request.getParameter("suserName");
        String curPassword = request.getParameter("spassword");
        SysUser sysUser = new SysUser();
        sysUser.setUserName(curUserName);
        sysUser.setMobilePhone(curUserName);
        sysUser.setPassword(curPassword);
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

        SysUser sysUserAdmin = new SysUser();
        sysUserAdmin.setMobilePhone(adminUserName);
        sysUserAdmin.setUserName(adminUserName);
        sysUserAdmin.setPassword(adminPassword);
        SysUser sysUserOperator = sysUserService.queryUserMapByAccount(sysUserAdmin);
        if((sysUserOperator == null) || (sysUserOperator.getIsAdmin() != 0)){
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
                sysUserOperator.getSysUserId(), record.getDischarge_reason());

        String bSuccessful = orderService.dischargeOrder(originalOrder, hedgeRecord);
        if(!bSuccessful.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)){
            logger.error("订单冲红保存错误：" + originalOrder.getOrderId());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单冲红错误：" + originalOrder.getOrderId());
            return ajaxJson;
        }

        hedgeRecord.setIs_discharge("1");
        hedgeRecord.setBeen_discharged("1");
        hedgeRecord.setDischargeOrderId(originalOrder.getOrderId());
        int nRet = orderService.insert(hedgeRecord);
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
            if(StringUtils.isEmpty(record.getCreditAccount())){
                SysDriver sysDriver = driverService.queryDriverByPK(originalOrder.getDebitAccount());
                originalOrder.setSysDriver(sysDriver);
            } else {
                SysDriver sysDriver = driverService.queryDriverByPK(originalOrder.getCreditAccount());
                originalOrder.setSysDriver(sysDriver);
            }
        } else {
            Transportion transportion = transportionService.queryTransportionByPK(originalOrder.getCreditAccount());
            originalOrder.setTransportion(transportion);

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

        if(StringUtils.isEmpty(sysOrderDeal.getStorage_time_after())
                || StringUtils.isEmpty(sysOrderDeal.getStorage_time_before())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("起始时间或终止时间为空！！！" );
            return ajaxJson;
        }

        List<SysOrderDeal> sysOrderDeals = orderDealService.queryOrderDeals(sysOrderDeal);
        if((sysOrderDeals == null) || (sysOrderDeals.size() == 0)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("您所查询的数据为空！！！" );
            return ajaxJson;
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysOrderDeals", sysOrderDeals);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }
}
