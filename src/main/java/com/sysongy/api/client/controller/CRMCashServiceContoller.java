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
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
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

        PageInfo<SysOrder> sysOrders = orderService.queryOrders(record);
        if((sysOrders == null) || (sysOrders.getList().size() > 0)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("该订单已存在，请勿提交重复订单！！！");
            return ajaxJson;
        }

        record.setOrderType(GlobalConstant.OrderType.CHARGE_TO_DRIVER);
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
    public AjaxJson customerGasPay(HttpServletRequest request, HttpServletResponse response, SysOrder record) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
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
        SysUserAccount creditAccount = sysUserAccountService.selectByPrimaryKey(record.getCreditAccount());
        if((creditAccount != null) || (sysDriver != null)
                || !(sysDriver.getPayCode().equalsIgnoreCase(payCode))){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("支付密码错误！！！");
            return ajaxJson;
        }

        String checkCode = request.getParameter("checkCode");
        if(!StringUtils.isEmpty(checkCode)){
            record.setConsume_card(sysDriver.getCardId());
            String checkCodeFromRedis = (String)redisClientImpl.getFromCache
                    (sysDriver.getMobilePhone());
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

        GasCard gasCard = gasCardService.selectByCardNoForCRM(sysDriver.getCardId());
        if(gasCard != null){
            record.setGasCard(gasCard);
        }
        sysDriver.setDriverType(GlobalConstant.DriverType.GAS_STATION);
        if(sysDriver.getDriverType() == GlobalConstant.DriverType.TRANSPORT){
            record.setOrderType(GlobalConstant.OrderType.CONSUME_BY_TRANSPORTION);      //车队消费
            String orderConsume = orderService.consumeByTransportion(record);
            if(!orderConsume.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("订单消费错误：" + orderConsume);
                return ajaxJson;
            }
        } else {
            record.setOrderType(GlobalConstant.OrderType.CONSUME_BY_DRIVER);            //预付款消费
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

        record = orderService.selectByPrimaryKey(record.getOrderId());
        Gastation gastation = gastationService.queryGastationByPK(record.getOperatorSourceId());
        if(gastation != null){
            record.setGastation(gastation);
        }
        if((sysDriver != null) && !StringUtils.isEmpty(sysDriver.getMobilePhone())){
            record.setSysDriver(sysDriver);
            AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
            aliShortMessageBean.setSendNumber(sysDriver.getMobilePhone());
            aliShortMessageBean.setProduct("司集能源科技平台");
            AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_CHANGE_PASSWORD);
        } else {
            logger.error("发送充值短信出错， mobilePhone：" + sysDriver.getMobilePhone());
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysOrder", record);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
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

        String curUserName = request.getParameter("userName");
        String curPassword = request.getParameter("password");
        SysUser sysUser = new SysUser();
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
        SysUser sysUserOperator = sysUserService.queryUser(sysUserAdmin);

        String strReason = request.getParameter("hedgeReason");
        SysOrder hedgeRecord = orderService.createDischargeOrderByOriginalOrder(record,
                sysUserOperator.getSysUserId(), strReason);
        hedgeRecord.getConsume_card();
        //orderService.dischargeOrder();

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysOrder", record);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }
}
