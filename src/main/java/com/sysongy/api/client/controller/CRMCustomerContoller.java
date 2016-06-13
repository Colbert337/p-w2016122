package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.api.client.controller.model.ShortMessageInfoModel;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.FileUtil;
import com.sysongy.util.IPUtil;
import com.sysongy.util.RedisClientInterface;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/crmCustomerService")
public class CRMCustomerContoller {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public AjaxJson sendMsg(HttpServletRequest request, HttpServletResponse response, SysDriver sysDriver){
        AjaxJson ajaxJson = new AjaxJson();

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

            int renum = driverService.saveDriver(sysDriver, "insert");
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无用户添加！！！");
                return ajaxJson;
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_ADD_USER_ERROR + e.getMessage());
            logger.error("add customer error： " + e);
            e.printStackTrace();
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
                if(StringUtils.isNotEmpty(checkCode)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("验证码已失效，请重新输入！！！");
                    return ajaxJson;
                }

                String inputCode = request.getParameter("inputCode");
                if(StringUtils.isNotEmpty(inputCode) || !inputCode.equalsIgnoreCase(checkCode)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("验证码输入错误！！！");
                    return ajaxJson;
                }
            }

            updateDriver(sysDriver);
        }catch (Exception e){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.UPDATE_CRM_USER_ERROR + e.getMessage());
            logger.error("update Customer error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }

    private boolean updateDriver(SysDriver sysDriver) throws Exception {
        boolean bRet = false;
        try {
            SysDriver orgSysDriver = driverService.queryDriverByPK(sysDriver.getSysDriverId());
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

            if(sysDriver.getExpiryDate() != null){
                orgSysDriver.setExpiryDate(sysDriver.getExpiryDate());
            }

            if(StringUtils.isNotEmpty(sysDriver.getDrivingLice())){
                orgSysDriver.setDrivingLice(sysDriver.getDrivingLice());
            }

            if(StringUtils.isNotEmpty(sysDriver.getVehicleLice())){
                orgSysDriver.setVehicleLice(sysDriver.getVehicleLice());
            }
            int renum = driverService.saveDriver(orgSysDriver, "update");
        } catch (Exception e) {
            throw e;
        }
        return bRet;
    }

    //多文件上传
    @RequestMapping(value = "/web/upload")
    @ResponseBody
    public AjaxJson uploadFileData(@RequestParam("filename")CommonsMultipartFile[] files, HttpServletRequest request) {
        AjaxJson ajaxJson = new AjaxJson();
        String imgTag = request.getParameter("imgTag");

        if(files == null){
            ajaxJson.setMsg("上传文件为空！！！");
            ajaxJson.setSuccess(false);
            return ajaxJson;
        }

        String sysDriverId = request.getParameter("sysDriverId");
        if(!StringUtils.isNotEmpty(sysDriverId)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("sysDriverId 为空！！！");
            return ajaxJson;
        }

        String realPath =  sysDriverId + "/" ;
        //String realPath = request.getSession().getServletContext().getRealPath("/")+ "/upload/" + sysDriverId + "/" ;
        String filePath = "D:/upload/" + realPath;
        FileUtil.createIfNoExist(filePath);
        try {
            for (int i = 0; i < files.length; i++) {
                Map<String, Object> attributes = new HashMap<String, Object>();
                String path = filePath + files[i].getOriginalFilename();
                File destFile = new File(path);
                attributes.put(imgTag, request.getContextPath() + "/upload/" + realPath + files[i].getOriginalFilename());
                ajaxJson.setAttributes(attributes);
                FileUtils.copyInputStreamToFile(files[i].getInputStream(), destFile);// 复制临时文件到指定目录下
            }
        } catch (IOException e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("上传文件失败：" + e.getMessage());
            logger.error("uploadFileData Customer error： " + e);
        }
        return ajaxJson;
    }
}
