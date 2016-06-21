package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.api.client.controller.model.ShortMessageInfoModel;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.util.*;
import com.sysongy.util.pojo.AliShortMessageBean;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/crmCustomerService")
public class CRMCustomerContoller {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    DriverService driverService;

    @Autowired
    RedisClientInterface redisClientImpl;


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
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            PageInfo<SysDriver> drivers = driverService.queryDrivers(sysDriver);
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
            redisClientImpl.addToCache(sysDriver.getSysDriverId(), checkCode.toString(), 60);

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

    private boolean checkIfFrequent(HttpServletRequest request, SysDriver sysDriver){
        boolean bRet = false;

        try {
            String ip = IPUtil.getIpAddress(request);
            Date catchIPTime = (Date)redisClientImpl.getFromCache(ip);
            if(catchIPTime == null){
                redisClientImpl.addToCache(ip, new Date(), 60);
            } else {
                if((new Date().getTime() - catchIPTime.getTime()) < 60000){     //IP地址发送过于频繁，同一分钟同一IP只允许发一次
                    return true;
                }
            }

            ShortMessageInfoModel shortMessageInfo = (ShortMessageInfoModel)redisClientImpl.getFromCache(sysDriver.getMobilePhone());
            if(shortMessageInfo == null){
                ShortMessageInfoModel shortMessageInfoModel = new ShortMessageInfoModel();
                shortMessageInfoModel.setCreateTime(new Date());
                shortMessageInfoModel.setSendTimes(shortMessageInfoModel.getSendTimes() + 1);
                redisClientImpl.addToCache(sysDriver.getMobilePhone(), shortMessageInfoModel, 300);
            } else {
                if( shortMessageInfo.getSendTimes() < 10 ){
                    shortMessageInfo.setSendTimes(shortMessageInfo.getSendTimes() + 1);
                } else {
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
            int renum = driverService.saveDriver(sysDriver, "insert");
            attributes.put("driver", sysDriver);
            ajaxJson.setAttributes(attributes);
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无用户添加！！！");
                return ajaxJson;
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_ADD_USER_ERROR + e.getMessage());
            logger.error("add customer error： " + e);
        }
        return ajaxJson;
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
            listDrivers.add(updateSysDriver);
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
            }

            if(StringUtils.isNotEmpty(sysDriver.getMobilePhone())){
                orgSysDriver.setMobilePhone(sysDriver.getMobilePhone());
            }

            if(StringUtils.isNotEmpty(sysDriver.getFullName())){
                orgSysDriver.setFullName(sysDriver.getFullName());
            }

            int renum = driverService.saveDriver(orgSysDriver, "update");
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
                String path = filePath + files[i].getOriginalFilename();
                File destFile = new File(path);
                String contextPath = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + contextPath;
                String httpPath = basePath + (String) prop.get("show_images_path") + "/" + realPath + files[i].getOriginalFilename();
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
            driverService.saveDriver(sysDriver, "update");
            attributes.put("driver", sysDriver);
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
}
