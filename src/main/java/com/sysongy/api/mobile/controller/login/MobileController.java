package com.sysongy.api.mobile.controller.login;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.Data;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.login.MobileLogin;
import com.sysongy.api.mobile.model.register.MobileRegister;
import com.sysongy.api.mobile.model.upload.MobileUpload;
import com.sysongy.api.mobile.model.userinfo.MobileUserInfo;
import com.sysongy.api.mobile.model.verification.MobileVerification;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;
import com.sysongy.api.mobile.tools.register.MobileRegisterUtils;
import com.sysongy.api.mobile.tools.upload.MobileUploadUtils;
import com.sysongy.api.mobile.tools.userinfo.MobileGetUserInfoUtils;
import com.sysongy.api.mobile.tools.verification.MobileVerificationUtils;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.DateUtil;
import com.sysongy.util.FileUtil;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.RedisClientInterface;
import com.sysongy.util.pojo.AliShortMessageBean;

@RequestMapping("/api/mobile/user/")
@Controller
public class MobileController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

	@Autowired
	SysUserService sysUserService;
	@Autowired
	DriverService driverService;
	@Autowired
    RedisClientInterface redisClientImpl;
	@Autowired
	OrderService orderService;

	@RequestMapping(value = {"Login"})
	@ResponseBody
	public String Login(MobileParams params) {

		MobileLogin mobileLogin = new MobileLogin();
		MobileReturn ret = new MobileReturn();
		try {
			
			try {
				mobileLogin = (MobileLogin) JSON.parseObject(params.getDetailParam(), MobileLogin.class);
			} catch (Exception e) {
				ret = MobileLoginUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, MobileLoginUtils.RET_PARAM_ERROR_MSG, null);

				logger.error("MobileController.Login ERROR： " + e);
			}

			MobileLoginUtils.checkLoginParam(mobileLogin, params.getApiKey(), ret);

			SysDriver driver = MobileLoginUtils.packagingSysDriver(mobileLogin);
			List<SysDriver> driverlist = driverService.queryeSingleList(driver);

			MobileLoginUtils.checkLogin(driverlist, ret);
			
			Data data = new Data();
			data.setToken(driverlist.get(0).getSysDriverId());

			ret = MobileLoginUtils.packagingMobileReturn(MobileLoginUtils.RET_SUCCESS, null, data);

		} catch (Exception e) {
			
			if(StringUtils.isEmpty(ret.getMsg())){
				ret = MobileLoginUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, null, null);
			}
			
			logger.error("MobileController.Login ERROR： " + e);
			
		} finally {
			return JSON.toJSONString(ret);
		}
	}
	
	@RequestMapping(value = {"GetVerificationCode"})
	@ResponseBody
	public String GetVerificationCode(MobileParams params) {
		
		MobileReturn ret = new MobileReturn();
		MobileVerification verification = new MobileVerification();
		
		try {	
			verification = MobileVerificationUtils.checkVerificationCodeParam(params, ret);

	        Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);

	        MobileVerificationUtils.sendMSG(verification, checkCode.toString());

	        redisClientImpl.addToCache(verification.getPhoneNum(), checkCode.toString(), 60);
	        
	        Data data = new Data();
			data.setVerificationCode(checkCode.toString());
			
	        ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, MobileVerificationUtils.RET_VERIFICATION_CODE, data);
		
		} catch (Exception e) {
			if(StringUtils.isEmpty(ret.getMsg())){
				ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_ERROR, null, null);
			}
			
			logger.error("MobileController.Login ERROR： " + e);
		}
		finally {
			return JSON.toJSONString(ret);
		}
	}
	
	@RequestMapping(value = {"Register"})
	@ResponseBody
	public String Register(MobileParams params) {
		
		MobileReturn ret = new MobileReturn();
		
		try {	
			MobileRegister register = MobileRegisterUtils.checkRegisterParam(params, ret, redisClientImpl);

			SysDriver driver = new SysDriver();
			driver.setUserName(register.getPhoneNum());
			
			List<SysDriver> driverlist = driverService.queryeSingleList(driver);
			if(driverlist.size() != 0){
				ret.setError(MobileUtils.RET_ERROR);
				ret.setMsg(MobileRegisterUtils.RET_DRIVER_MOBILE_REGISTED);
				ret.setData(new Data());
				throw new Exception(MobileRegisterUtils.RET_DRIVER_MOBILE_REGISTED);
			}
			
			
			driver.setPassword(register.getPassword());
			Integer tmp = driverService.saveDriver(driver, "insert");
			if(tmp == 1){
				List<SysDriver> tmplist = driverService.queryeSingleList(driver);
				driver = tmplist.get(0);
			}
			
			Data data = new Data();
			data.setToken(driver.getSysDriverId());
			
	        ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, MobileVerificationUtils.RET_VERIFICATION_CODE, data);
		} catch (Exception e) {
			
			if(StringUtils.isEmpty(ret.getMsg())){
				ret = MobileUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, null, null);
			}
			
			logger.error("MobileController.Login ERROR： " + e);
		}
		finally {
			return JSON.toJSONString(ret);
		}
	}
	
	@RequestMapping(value = {"GetUserInfo"})
	@ResponseBody
	public String GetUserInfo(MobileParams params) {
		
		MobileReturn ret = new MobileReturn();
		MobileUserInfo userinfo = new MobileUserInfo();
		
		try {	
			userinfo = MobileGetUserInfoUtils.checkGetUserInfoParam(params, ret);

			SysDriver driver = new SysDriver();
			driver.setSysDriverId(userinfo.getToken());
			List<SysDriver> driverlist = driverService.queryeSingleList(driver);
			
			if(driverlist.size() != 1){
				ret.setError(MobileUtils.RET_ERROR);
				ret.setMsg(MobileGetUserInfoUtils.RET_USERINFO_ERROR);
				ret.setData(new Data());
				throw new Exception(MobileGetUserInfoUtils.RET_USERINFO_ERROR);
			}
			
			driver = driverlist.get(0);
			List<Map<String, Object>> list = orderService.calcDriverCashBack(driver.getSysDriverId());
			
	        Data data = new Data();
	        data.setNick(driver.getFullName());
	        data.setUsername(driver.getUserName());
	        data.setSecurityPhone(driver.getMobilePhone());
	        data.setBalance(driver.getAccount().getAccountBalance());
	        data.setCumulativeReturn(list.size()!=1?"0":list.get(0).get("cash_back").toString());
	        data.setIsRealNameAuth(GlobalConstant.DriverCheckedStatus.ALREADY_CERTIFICATED.equalsIgnoreCase(driver.getCheckedStatus())?"true":"false");			    
	        
	        ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, MobileGetUserInfoUtils.RET_USERINFO_SUCCESS, data);
		} catch (Exception e) {
			if(StringUtils.isEmpty(ret.getMsg())){
				ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_ERROR, null, null);
			}
			
			logger.error("MobileController.Login ERROR： " + e);
		}
		finally {
			return JSON.toJSONString(ret);
		}
	}
	
	//单文件上传
    @RequestMapping(value = "/web/upload")
    @ResponseBody
    public String upload(MobileParams params, @RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request){
    	
    	MobileReturn ret = new MobileReturn();
    	MobileUpload upload = new MobileUpload();
    	
    	try {
		upload = MobileUploadUtils.checkUploadParam(params, ret);
		
		String sysPathID = upload.getToken();
		String realPath =  sysPathID + "/" ;
        String filePath = (String) prop.get("images_upload_path") + "/" + realPath;
        String httpPath = "";
        FileUtil.createIfNoExist(filePath);
        
            Map<String, Object> attributes = new HashMap<String, Object>();
            for (int i = 0; i < files.length; i++) {
                long timeStamp = System.currentTimeMillis();
                String path = filePath + timeStamp + files[i].getOriginalFilename();
                File destFile = new File(path);
                String contextPath = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + contextPath;
                httpPath = basePath + (String) prop.get("show_images_path") + "/" + realPath + timeStamp + files[i].getOriginalFilename();

                FileUtils.copyInputStreamToFile(files[i].getInputStream(), destFile);// 复制临时文件到指定目录下
            }

//            if(StringUtils.isNotEmpty(sysDriver.getExpireTimeForCRM())){
//                sysDriver.setExpiryDate(DateUtil.strToDate(sysDriver.getExpireTimeForCRM(), "yyyy-MM-dd"));
//            }
//
//            driverService.saveDriver(sysDriver, "update");
//            SysDriver sysDriverNew = driverService.queryDriverByPK(sysDriver.getSysDriverId());
            
            Data data = new Data();
            data.setImageUrl(httpPath);
            
            ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, MobileGetUserInfoUtils.RET_USERINFO_SUCCESS, data);

        } catch (Exception e) {
        	if(StringUtils.isEmpty(ret.getMsg())){
				ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_ERROR, null, null);
			}
			
			logger.error("MobileController.Login ERROR： " + e);
        }
        finally {
			return JSON.toJSONString(ret);
		}
    }
}
