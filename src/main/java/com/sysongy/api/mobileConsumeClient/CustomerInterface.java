package com.sysongy.api.mobileConsumeClient;

import com.github.pagehelper.PageInfo;
import com.sysongy.api.client.controller.model.PayCodeValidModel;
import com.sysongy.api.client.controller.model.ShortMessageInfoModel;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.system.model.SysOperationLog;
import com.sysongy.poms.system.service.SysOperationLogService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.tcms.advance.service.TcVehicleService;
import com.sysongy.util.*;
import com.sysongy.util.pojo.AliShortMessageBean;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/crmInterface/customerInterface")
public class CustomerInterface {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    DriverService driverService;

    @Autowired
    RedisClientInterface redisClientImpl;

    @Autowired
    GastationService gastationService;

    @Autowired
    private TcFleetService tcFleetService;

    @Autowired
    private TcVehicleService tcVehicleService;

    @Autowired
    private TransportionService transportionService;

    @Autowired
    private GasCardService gasCardService;

	@Autowired
	SysOperationLogService sysOperationLogService;

    public DriverService getDriverService() {
        return driverService;
    }

    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    public RedisClientInterface getRedisClientImpl() {
        return redisClientImpl;
    }

    public void setRedisClientImpl(RedisClientInterface redisClientImpl) {
        this.redisClientImpl = redisClientImpl;
    }

    @RequestMapping(value = {"/web/queryCustomerInfo"})
    @ResponseBody
    public AjaxJson queryCustomerInfo(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver){
        AjaxJson ajaxJson = new AjaxJson();
        if(!StringUtils.isNotEmpty(sysDriver.getStationId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("气站ID为空！！！");
            return ajaxJson;
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            PageInfo<SysDriver> drivers = driverService.queryDrivers(sysDriver);
            if((drivers == null) || (drivers.getList().size() == 0)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("没有查询到所需内容！！！");
                return ajaxJson;
            }
            attributes.put("PageInfo", drivers);
            attributes.put("drivers", drivers.getList());
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_USER_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
        ajaxJson.setAttributes(attributes);
    	return ajaxJson;
    }

    private boolean isLock24Hours(String id){
        boolean bRet = false;
        PayCodeValidModel payCodeValidModel = (PayCodeValidModel)redisClientImpl.getFromCache(id);
        if((payCodeValidModel != null) && (payCodeValidModel.getErrTimes() == 4)){
            bRet = true;
        }
        return bRet;
    }

    private SysUserAccount convertSysUserAccount(SysUserAccount account){
        SysUserAccount sysUserAccount = account;
        Usysparam usysparam = sysUserAccount.getAccount_statusInfo();
        usysparam.setMname("已锁定");
        sysUserAccount.setAccount_statusInfo(usysparam);
        return sysUserAccount;
    }

    @RequestMapping(value = {"/web/querySingleCustomerInfo"})
    @ResponseBody
    public AjaxJson querySingleCustomerInfo(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver){
        AjaxJson ajaxJson = new AjaxJson();
        if(StringUtils.isEmpty(sysDriver.getMobilePhone()) && StringUtils.isEmpty(sysDriver.getCardId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("输入查询内容为空！！！");
            return ajaxJson;
        }
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            PageInfo<SysDriver> drivers = new PageInfo<SysDriver>();
            if(StringUtils.isNotEmpty(sysDriver.getMobilePhone())){
                drivers = driverService.querySingleDriver(sysDriver);
            } else {
                GasCard gasCard = gasCardService.selectByCardNoForCRM(sysDriver.getCardId());
                if((sysDriver.getIsCharge() == GlobalConstant.DriverType.TRANSPORT)  &&
                        (gasCard.getCard_property().equalsIgnoreCase(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_TRANSPORTION))){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("车队卡不允许在客户端充值！！！");
                    return ajaxJson;
                }

                if(StringUtils.isNotEmpty(sysDriver.getCardId())
                        && (gasCard.getCard_property().equalsIgnoreCase(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_DRIVER))){
                    drivers = driverService.querySingleDriver(sysDriver);
                    if((drivers.getList().size() > 0) &&
                            (isLock24Hours(drivers.getList().get(0).getSysUserAccountId()))){
                        List<SysDriver> sysDriverInfos = new ArrayList<SysDriver>();
                        SysDriver orgSysDriver = drivers.getList().get(0);
                        orgSysDriver.setIsLocked(1);
                        orgSysDriver.setAccount(convertSysUserAccount(orgSysDriver.getAccount()));
                        sysDriverInfos.add(orgSysDriver);
                        drivers.setList(sysDriverInfos);
                    }
                } else {
                    SysDriver sysDriverForFleet = findFleetInfo(sysDriver.getCardId());
                    if(sysDriverForFleet == null){
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("没有查询到所需内容！！！");
                        return ajaxJson;
                    }
                    List<SysDriver> driversList = new ArrayList<SysDriver>();
                    driversList.add(sysDriverForFleet);
                    drivers.setList(driversList);
                }
            }

            if((drivers == null) || (drivers.getList().size() == 0)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("没有查询到所需内容！！！");
                return ajaxJson;
            }

            if(drivers.getList().size() > 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该用户数据异常！！！");
                return ajaxJson;
            }

            attributes.put("PageInfo", drivers);
            attributes.put("drivers", drivers.getList());
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_USER_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    private SysDriver findFleetInfo(String cardID){
        if(StringUtils.isEmpty(cardID)){
            return null;
        }
        SysDriver sysDriverForFleet = null;
        try
        {
            List<TcVehicle> vehicles = tcVehicleService.queryVehicleByCardNo(cardID);
            if(vehicles.size() > 1){
                logger.error("查询出现多个车辆: " + cardID);
                return null;
            }
            for(TcVehicle tcVehicle : vehicles){
                List<TcFleet> tcFleets = tcFleetService.queryFleetByVehicleId(tcVehicle.getStationId(), tcVehicle.getTcVehicleId());
                if(tcFleets.size() > 1){
                    logger.error("查询出现多个车队: " + tcVehicle.getTcVehicleId());
                    return null;
                }
                TcFleet tcFleet = null;
                if(tcFleets.size() == 1){
                    tcFleet = tcFleets.get(0);
                }
                return getSysDriverFromVehicle(cardID, tcVehicle, tcFleet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysDriverForFleet;
    }

    private SysDriver getSysDriverFromVehicle(String cardID, TcVehicle tcVehicle, TcFleet tcFleet){
        try
        {
            Transportion transportion = transportionService.queryTransportionByPK(tcVehicle.getStationId());
            SysDriver sysDriverForFleet = new SysDriver();
            sysDriverForFleet.setSysDriverId(transportion.getSys_transportion_id());
            sysDriverForFleet.setFullName(tcVehicle.getPlatesNumber());
            sysDriverForFleet.setMobilePhone(tcVehicle.getCardNo());
            GasCard gasCard = gasCardService.selectByCardNoForCRM(cardID);
            sysDriverForFleet.setCardId(cardID);
            sysDriverForFleet.setCardInfo(gasCard);
            SysUserAccount sysUserAccount = new SysUserAccount();
            Usysparam usysparam = new Usysparam();
            if(isLock24Hours(tcVehicle.getTcVehicleId())){
                sysDriverForFleet.setIsLocked(1);
                usysparam.setMname("已锁定");
            } else {
                usysparam.setMname(gasCard.getCardStatusInfo().getMname());
            }
            sysUserAccount.setAccount_statusInfo(usysparam);
            if((tcFleet != null) && (tcFleet.getIsAllot() == GlobalConstant.TCFLEET_IS_ALLOT_YES)){
                sysUserAccount.setAccountBalance(tcFleet.getQuota().toString());
            } else {
                sysUserAccount.setAccountBalance(transportion.getDeposit().toString());
            }
            sysDriverForFleet.setAccount(sysUserAccount);
            return sysDriverForFleet;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/web/queryForPageSingleList"})
    @ResponseBody
    public AjaxJson queryForPageSingleList(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            PageInfo<SysDriver> drivers = driverService.queryDrivers(sysDriver);
            attributes.put("PageInfo", drivers);
            attributes.put("drivers", drivers.getList());
            if((drivers == null) || (drivers.getList().size() > 1)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该账户异常！！！");
                return ajaxJson;
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_SINGLE_USER_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }
    @RequestMapping(value = {"/web/sendMsg"})
    @ResponseBody
    public AjaxJson sendMsg(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver,@RequestParam(required = false) String mobilePhone){
        AjaxJson ajaxJson = new AjaxJson();
        if(sysDriver == null || sysDriver.getMobilePhone() == null || "".equals(sysDriver.getMobilePhone())){
            sysDriver.setMobilePhone(mobilePhone);
        }

        if(!StringUtils.isNotEmpty(sysDriver.getMobilePhone())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("手机号为空！！！");
            return ajaxJson;
        }

        if(checkIfFrequent(request, sysDriver)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("您发送短信的次数过于频繁，请稍后再试！！！");
            return ajaxJson;
        }

        try
        {
            Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);
            AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
            aliShortMessageBean.setSendNumber(sysDriver.getMobilePhone());
            aliShortMessageBean.setCode(checkCode.toString());
            aliShortMessageBean.setProduct("司集能源科技平台");

            String msgType = request.getParameter("msgType");
            String orderID = request.getParameter("orderID");
            if(msgType.equalsIgnoreCase("reSetCode")){
                redisClientImpl.addToCache(orderID, checkCode.toString(), InterfaceConstants.SHORT_MSEEAGE_CODE_EXPIRE_TIME);
            } else {
                redisClientImpl.addToCache(sysDriver.getSysDriverId(), checkCode.toString(), InterfaceConstants.SHORT_MSEEAGE_CODE_EXPIRE_TIME);
            }

            if(msgType.equalsIgnoreCase("changePassword")){
                AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_CHANGE_PASSWORD);
            } else if(msgType.equalsIgnoreCase("driverCharge")){
                AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.DRIVER_CHARGE);
            } else if(msgType.equalsIgnoreCase("driverConsume")){
                AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.DRIVER_CONSUME);
            }else if(msgType.equalsIgnoreCase("reSetCode")){
                aliShortMessageBean.setProduct("进行订单冲红");
                AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.DRIVER_HEDGE);
            } else {
                AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_REGISTER);
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_SEND_MSG_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
        }
        return ajaxJson;
    }


    private boolean checkIfFrequent(HttpServletRequest request, SysDriver sysDriver){
        boolean bRet = false;

        try {
            String ip = IPUtil.getIpAddress(request);
            Date catchIPTime = (Date)redisClientImpl.getFromCache(ip);
            if(catchIPTime == null){
                redisClientImpl.addToCache(ip, new Date(), 60);
            } else {
                if((new Date().getTime() - catchIPTime.getTime()) < 60000){     //IP地址发送过于频繁，同一分钟同一IP只允许发一次
                    logger.error("该IP发送短信过于频繁，手机号为：" + sysDriver.getMobilePhone());
                    return true;
                }
            }

            ShortMessageInfoModel shortMessageInfo = (ShortMessageInfoModel)redisClientImpl.getFromCache(sysDriver.getMobilePhone());
            if(shortMessageInfo == null){
                ShortMessageInfoModel shortMessageInfoModel = new ShortMessageInfoModel();
                shortMessageInfoModel.setCreateTime(new Date());
                shortMessageInfoModel.setSendTimes(shortMessageInfoModel.getSendTimes() + 1);
                redisClientImpl.addToCache(sysDriver.getMobilePhone(), shortMessageInfoModel, 120);
            } else {
                if( shortMessageInfo.getSendTimes() < 10 ){
                    shortMessageInfo.setSendTimes(shortMessageInfo.getSendTimes() + 1);
                } else {
                    logger.error("该手机在5分钟内发送短信超过10次，手机号为：" + sysDriver.getMobilePhone());
                    return true;
                }
            }
        } catch (IOException e) {
            logger.error("无法获取IP地址" + e);
        }
        return bRet;
    }

    @RequestMapping(value = {"/web/addCustomer"})
    @ResponseBody
    public AjaxJson addCustomer(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver){
        AjaxJson ajaxJson = new AjaxJson();
        try
        {
            if(!StringUtils.isNotEmpty(sysDriver.getSysDriverId())){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("用户ID未生成，请重新添加！！！");
                return ajaxJson;
            }

            int isExistDriver = driverService.isExists(sysDriver);
            if(isExistDriver > 0){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该用户手机号已被使用！！！");
                return ajaxJson;
            }

            SysDriver isAlreadyHaveDrivers = driverService.queryDriverByPK(sysDriver.getSysDriverId());
            if(isAlreadyHaveDrivers != null){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该用户已经创建成功，请勿重复创建！！！");
                return ajaxJson;
            }

            PageInfo<SysDriver> drivers = driverService.ifExistDriver(sysDriver);
            if((drivers != null) && (drivers.getList().size() != 0)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该用户已经创建成功，请勿重复创建！！！");
                return ajaxJson;
            }

            String checkCode = (String)redisClientImpl.getFromCache(sysDriver.getSysDriverId());
            if(!StringUtils.isNotEmpty(checkCode)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("验证码已失效，请重新生成验证码！！！");
                return ajaxJson;
            }

            String inputCode = request.getParameter("inputCode");
            if(!StringUtils.isNotEmpty(inputCode) || !inputCode.equalsIgnoreCase(checkCode)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("验证码输入错误！！！");
                return ajaxJson;
            }

            if(sysDriver.getIsIdent() == null){
                sysDriver.setIsIdent(0);
            }

            if(sysDriver.getFuelType() == null){
                sysDriver.setIsIdent(0);
            }
            Map<String, Object> attributes = new HashMap<String, Object>();
            sysDriver.setUserStatus("0");
            sysDriver.setIsFirstCharge(1);
            sysDriver.setCheckedStatus("0");
            sysDriver.setUpdatedDate(new Date());
            sysDriver.setCreatedDate(new Date() );
            sysDriver.setExpiryDate(new Date());
            sysDriver.setUserName(sysDriver.getMobilePhone());

            Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
            String show_path = (String) prop.get("default_img");
            String contextPath = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + contextPath;
            String basePathDefault = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort();
            String httpPath = basePathDefault + show_path;
            sysDriver.setDrivingLice(httpPath);
            sysDriver.setVehicleLice(httpPath);

            String sys_gas_station_id = request.getParameter("sys_gas_station_id");
            if(!StringUtils.isNotEmpty(sys_gas_station_id)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("气站ID输入错误！！！");
                return ajaxJson;
            }

            sysDriver.setStationId(sys_gas_station_id);
            Gastation gastation = gastationService.queryGastationByPK(sys_gas_station_id);
            sysDriver.setRegisSource(gastation.getGas_station_name());

            sysDriver.setDriverType(GlobalConstant.DriverType.GAS_STATION);
            int renum = driverService.saveDriver(sysDriver, "insert", null, null);
			//系统关键日志记录
			SysOperationLog sysOperationLog = new SysOperationLog();
			sysOperationLog.setOperation_type("zc");
			String name = sysDriver.getFullName();
			if("".equals(name)||null==name){
				name = sysDriver.getUserName();
			}
			if("".equals(name)||null==name){
				name = sysDriver.getMobilePhone();
			}
			sysOperationLog.setLog_platform("4");
			sysOperationLog.setLog_content(name+"的账户卡通过网站注册成功！"); 
			//操作日志
			sysOperationLogService.saveOperationLog(sysOperationLog,sysDriver.getSysDriverId());		
			
            attributes.put("driver", sysDriver);
            ajaxJson.setAttributes(attributes);
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无用户添加！！！");
                return ajaxJson;
            }
            sendRegisterSuccessMessage(sysDriver);
            SysDriver responseDriver = driverService.queryDriverByPK(sysDriver.getSysDriverId());
            attributes.put("driver", responseDriver);
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_ADD_USER_ERROR + e.getMessage());
            logger.error("add customer error： " + e);
        }
        return ajaxJson;
    }

    private void sendRegisterSuccessMessage(SysDriver sysDriver){
        AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
        aliShortMessageBean.setSendNumber(sysDriver.getMobilePhone());
        AliShortMessage.sendShortMessage(aliShortMessageBean,
                AliShortMessage.SHORT_MESSAGE_TYPE.DRIVER_REGISTER_SUCCESS);
    }

    @RequestMapping(value = {"/web/delCustomer"})
    @ResponseBody
    public AjaxJson delCustomer(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver){
        AjaxJson ajaxJson = new AjaxJson();
        try
        {
            int renum = driverService.delDriver(sysDriver.getSysDriverId());
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无用户删除 ！！！");
                return ajaxJson;
            }
        }catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.DELETE_CRM_USER_ERROR + e.getMessage());
            logger.error("delete Customer error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/updateCustomer"})
    @ResponseBody
    public AjaxJson updateCustomer(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            String msgType = request.getParameter("msgType");
            if(msgType.equalsIgnoreCase("changePassword")){
                String checkCode = (String)redisClientImpl.getFromCache(sysDriver.getSysDriverId());
                if(!StringUtils.isNotEmpty(checkCode)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("验证码已失效，请重新输入！！！");
                    return ajaxJson;
                }

                String inputCode = request.getParameter("inputCode");
                if(!StringUtils.isNotEmpty(inputCode) || !inputCode.equalsIgnoreCase(checkCode)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("验证码输入错误！！！");
                    return ajaxJson;
                }
            }
            SysDriver updateSysDriver = updateDriver(sysDriver);
            if(updateSysDriver == null){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg(InterfaceConstants.UPDATE_CRM_USER_ERROR);
                return ajaxJson;
            }

            Map<String, Object> attributes = new HashMap<String, Object>();
            List<SysDriver> listDrivers = new ArrayList<SysDriver>();
            SysDriver updateSysDriverNew = driverService.queryDriverByPK(updateSysDriver.getSysDriverId());
            listDrivers.add(updateSysDriverNew);
            attributes.put("drivers", listDrivers);
            ajaxJson.setAttributes(attributes);

        }catch (Exception e){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.UPDATE_CRM_USER_ERROR + e.getMessage());
            logger.error("update Customer error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }

    private SysDriver updateDriver(SysDriver sysDriver) throws Exception {
        try {
            SysDriver orgSysDriver = driverService.queryDriverByPK(sysDriver.getSysDriverId());
            if(orgSysDriver == null){
                logger.error("update customer error, can't find the driver:  " + sysDriver.getSysDriverId());
                return null;
            }

            if(StringUtils.isNotEmpty(sysDriver.getPassword())){
                orgSysDriver.setPassword(sysDriver.getPassword());
            }

            if(StringUtils.isNotEmpty(sysDriver.getCardId())){
                orgSysDriver.setCardId(sysDriver.getCardId());
            }

            if(StringUtils.isNotEmpty(sysDriver.getIdentityCard())){
                orgSysDriver.setIdentityCard(sysDriver.getIdentityCard());
            }

            if(StringUtils.isNotEmpty(sysDriver.getUserName())){
                orgSysDriver.setUserName(sysDriver.getUserName());
            }

            if(sysDriver.getFuelType() != null){
                orgSysDriver.setFuelType(sysDriver.getFuelType());
            }

            if(StringUtils.isNotEmpty(sysDriver.getPlateNumber())){
                orgSysDriver.setPlateNumber(sysDriver.getPlateNumber());
            }

            if(StringUtils.isNotEmpty(sysDriver.getExpireTimeForCRM())){
                orgSysDriver.setExpiryDate(DateUtil.strToDate(sysDriver.getExpireTimeForCRM(), "yyyy-MM-dd"));
            }

            if(StringUtils.isNotEmpty(sysDriver.getDrivingLice())){
                orgSysDriver.setDrivingLice(sysDriver.getDrivingLice());
            }

            if(StringUtils.isNotEmpty(sysDriver.getVehicleLice())){
                orgSysDriver.setVehicleLice(sysDriver.getVehicleLice());
            }

            if(StringUtils.isNotEmpty(sysDriver.getPayCode())){
                orgSysDriver.setPayCode(sysDriver.getPayCode());
            } else {
                orgSysDriver.setCheckedStatus("1");
            }

            if(StringUtils.isNotEmpty(sysDriver.getMobilePhone())){
                orgSysDriver.setMobilePhone(sysDriver.getMobilePhone());
            }

            if(StringUtils.isNotEmpty(sysDriver.getFullName())){
                orgSysDriver.setFullName(sysDriver.getFullName());
            }

            if(sysDriver.getCheckedDate() != null){
                orgSysDriver.setCheckedDate(sysDriver.getCheckedDate());
            }

            orgSysDriver.setUpdatedDate(new Date());
            int renum = driverService.saveDriver(orgSysDriver, "update", null, null);
            if(renum > 0){
                return orgSysDriver;
            }
        } catch (Exception e) {
            logger.error("update the customer get error： " + e);
            e.printStackTrace();
        }
        return null;
    }

    //多文件上传
    @RequestMapping(value = "/web/upload")
    @ResponseBody
    public AjaxJson uploadFileData(@RequestParam("filename")CommonsMultipartFile[] files, HttpServletRequest request,SysDriver sysDriver) {
        AjaxJson ajaxJson = new AjaxJson();
        String imgTag = request.getParameter("imgTag");
        if(files == null){
            ajaxJson.setMsg("上传文件为空！！！");
            ajaxJson.setSuccess(false);
            return ajaxJson;
        }

        String sysPathID = sysDriver.getSysDriverId();
        if(!StringUtils.isNotEmpty(sysDriver.getSysDriverId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("sysUserId为空！！！");
            return ajaxJson;
        }

        String realPath =  sysPathID + "/" ;
        String filePath = (String) prop.get("images_upload_path") + "/" + realPath;
        FileUtil.createIfNoExist(filePath);
        try {
            Map<String, Object> attributes = new HashMap<String, Object>();
            for (int i = 0; i < files.length; i++) {
                long timeStamp = System.currentTimeMillis();
                String path = filePath + timeStamp + files[i].getOriginalFilename();
                File destFile = new File(path);
                String contextPath = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + contextPath;
                String httpPath = basePath + (String) prop.get("show_images_path") + "/" + realPath + timeStamp + files[i].getOriginalFilename();
                String fileNum = String.valueOf(i);
                attributes.put(imgTag + fileNum, httpPath);
                FileUtils.copyInputStreamToFile(files[i].getInputStream(), destFile);// 复制临时文件到指定目录下
                if(imgTag.equalsIgnoreCase("driverURL")){
                    sysDriver.setDrivingLice(httpPath);
                }else{
                    sysDriver.setVehicleLice(httpPath);
                }
            }

            if(StringUtils.isNotEmpty(sysDriver.getExpireTimeForCRM())){
                sysDriver.setExpiryDate(DateUtil.strToDate(sysDriver.getExpireTimeForCRM(), "yyyy-MM-dd"));
            }
            sysDriver.setCheckedStatus("1");
            sysDriver.setUpdatedDate(new Date());
            driverService.saveDriver(sysDriver, "update", null, null);
            SysDriver sysDriverNew = driverService.queryDriverByPK(sysDriver.getSysDriverId());
            attributes.put("driver", sysDriverNew);
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("上传文件失败：" + e.getMessage());
            e.printStackTrace();
            logger.error("uploadFileData Customer error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/distributeCardToCustomer"})
    @ResponseBody
    public AjaxJson distributeCardToCustomer(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if(StringUtils.isEmpty(sysDriver.getMobilePhone()) || StringUtils.isEmpty(sysDriver.getCardId())
                    || StringUtils.isEmpty(sysDriver.getStationId())){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("手机号或者卡号或者气站号为空！！！");
                return ajaxJson;
            }
            sysDriver.setStationId("");
            SysDriver orgSysDriver = driverService.queryDriverByMobilePhone(sysDriver);
            if(orgSysDriver == null){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该司机不存在！！！");
                logger.error("update customer error, can't find the driver:  " + sysDriver.getMobilePhone());
                return ajaxJson;
            }

            orgSysDriver.setCardId(sysDriver.getCardId());
            orgSysDriver.setUpdatedDate(new Date());
            int renum = driverService.distributeCard(orgSysDriver);
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无卡分发！！！");
                return ajaxJson;
            }
            Map<String, Object> attributes = new HashMap<String, Object>();
            SysDriver orgSysDriverBack = driverService.queryDriverByMobilePhone(sysDriver);
            attributes.put("driver", orgSysDriverBack);
            ajaxJson.setAttributes(attributes);
        }catch (Exception e){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.DISTRIBUTE_CRM_USER_CARD_ERROR + e.getMessage());
            logger.error("update Customer error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 发送短信接口  add by wdq 20160622
     * @param request
     * @param response
     * @param sysDriver
     * @param mobilePhone
     * @return
     */
    @RequestMapping(value = {"/web/sendMsg/api"})
    @ResponseBody
    public AjaxJson sendMsgApi(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver,@RequestParam(required = false) String mobilePhone){
        AjaxJson ajaxJson = new AjaxJson();
        if(sysDriver == null || sysDriver.getMobilePhone() == null || "".equals(sysDriver.getMobilePhone())){
            sysDriver.setMobilePhone(mobilePhone);
        }

        if(!StringUtils.isNotEmpty(sysDriver.getMobilePhone())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("手机号为空！！！");
            return ajaxJson;
        }

        if(checkIfFrequent(request, sysDriver)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("您发送短信的次数过于频繁，请稍后再试！！！");
            return ajaxJson;
        }
        ajaxJson.setMsg("验证码发送成功！");
        try
        {
            Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);
            AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
            aliShortMessageBean.setSendNumber(sysDriver.getMobilePhone());
            aliShortMessageBean.setCode(checkCode.toString());
            aliShortMessageBean.setProduct("司集能源科技平台");
            String key = GlobalConstant.MSG_PREFIX + sysDriver.getMobilePhone();
            redisClientImpl.addToCache(key, checkCode.toString(), InterfaceConstants.SHORT_MSEEAGE_CODE_EXPIRE_TIME);

            String msgType = request.getParameter("msgType");
            if(msgType.equalsIgnoreCase("changePassword")){
                AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_CHANGE_PASSWORD);
            } else {
                AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_REGISTER);
            }

        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_SEND_MSG_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
        }
        return ajaxJson;
    }

    /**
     * 验证码验证
     * @return
     */
    @RequestMapping("/web/isMsg")
    @ResponseBody
    public JSONObject queryRoleList(HttpServletRequest request, @RequestParam String msgCode, @RequestParam String mobilePhone, ModelMap map){
        JSONObject json = new JSONObject();
        String key = GlobalConstant.MSG_PREFIX + mobilePhone;
        String code = "";
        if(redisClientImpl.getFromCache(key) == null){
            json.put("valid",false);
            return json;
        }else{
            code = redisClientImpl.getFromCache(key).toString();
        }

        if(msgCode == null){
            json.put("valid",false);
        }else if(mobilePhone == null){
            json.put("valid",false);
        }else if(msgCode.equals(code)){
            json.put("valid",true);
        }else{
            json.put("valid",false);
        }

        return json;
    }
}
