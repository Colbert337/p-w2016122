package com.sysongy.api.mobile.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

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
import com.github.pagehelper.PageInfo;
import com.sysongy.api.mobile.model.base.Data;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.feedback.MobileFeedBack;
import com.sysongy.api.mobile.model.login.MobileLogin;
import com.sysongy.api.mobile.model.loss.MobileLoss;
import com.sysongy.api.mobile.model.record.MobileRecord;
import com.sysongy.api.mobile.model.register.MobileRegister;
import com.sysongy.api.mobile.model.upload.MobileUpload;
import com.sysongy.api.mobile.model.userinfo.MobileUserInfo;
import com.sysongy.api.mobile.model.verification.MobileVerification;
import com.sysongy.api.mobile.service.MbUserSuggestServices;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.feedback.MobileFeedBackUtils;
import com.sysongy.api.mobile.tools.getcitys.GetCitysUtils;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;
import com.sysongy.api.mobile.tools.loss.ReportLossUtil;
import com.sysongy.api.mobile.tools.record.MobileRecordUtils;
import com.sysongy.api.mobile.tools.register.MobileRegisterUtils;
import com.sysongy.api.mobile.tools.returncash.ReturnCashUtil;
import com.sysongy.api.mobile.tools.upload.MobileUploadUtils;
import com.sysongy.api.mobile.tools.userinfo.MobileGetUserInfoUtils;
import com.sysongy.api.mobile.tools.verification.MobileVerificationUtils;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;
import com.sysongy.poms.ordergoods.service.SysOrderGoodsService;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.RedisClientInterface;

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
	@Autowired
	MbUserSuggestServices mbUserSuggestServices;
	@Autowired
	SysUserAccountService sysUserAccountService;
	@Autowired
	SysOrderGoodsService sysOrderGoodsService;

	@RequestMapping(value = {"Login"})
	@ResponseBody
	public String login(MobileParams params) {

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
			
			ArrayList<Data> list = new ArrayList<Data>();
	        list.add(data);

			ret = MobileLoginUtils.packagingMobileReturn(MobileLoginUtils.RET_SUCCESS,	 null, list);

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
	public String getVerificationCode(MobileParams params) {
		
		MobileReturn ret = new MobileReturn();
		MobileVerification verification = new MobileVerification();
		
		try {	
			verification = MobileVerificationUtils.checkVerificationCodeParam(params, ret);

	        Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);

	        MobileVerificationUtils.sendMSG(verification, checkCode.toString());

	        redisClientImpl.addToCache(verification.getPhoneNum(), checkCode.toString(), 60);
	        
	        Data data = new Data();
			data.setVerificationCode(checkCode.toString());
			
			ArrayList<Data> list = new ArrayList<Data>();
	        list.add(data);
			
	        ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, MobileVerificationUtils.RET_VERIFICATION_CODE, list);
		
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
	public String register(MobileParams params) {
		
		MobileReturn ret = new MobileReturn();
		
		try {	
			MobileRegister register = MobileRegisterUtils.checkRegisterParam(params, ret, redisClientImpl);

			SysDriver driver = new SysDriver();
			driver.setUserName(register.getPhoneNum());
			
			List<SysDriver> driverlist = driverService.queryeSingleList(driver);
			if(driverlist.size() != 0){
				ret.setError(MobileUtils.RET_ERROR);
				ret.setMsg(MobileRegisterUtils.RET_DRIVER_MOBILE_REGISTED);
				
				ArrayList<Data> list = new ArrayList<Data>();
		        list.add(new Data());
		        
				ret.setData(list);
				
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
			
			ArrayList<Data> list = new ArrayList<Data>();
	        list.add(data);
	        
	        ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, MobileVerificationUtils.RET_VERIFICATION_CODE, list);
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
	public String getUserInfo(MobileParams params) {
		
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
				
				ArrayList<Data> listc = new ArrayList<Data>();
				listc.add(new Data());
				
				ret.setData(listc);
		        
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
	        
	        ArrayList<Data> listd = new ArrayList<Data>();
	        listd.add(data);
        	
	        ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, MobileGetUserInfoUtils.RET_USERINFO_SUCCESS, listd);
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
	
    @RequestMapping(value = "Upload")
    @ResponseBody
    public String upload(MobileParams params, @RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request){
    	
    	MobileReturn ret = new MobileReturn();
    	MobileUpload upload = new MobileUpload();
    	
    	try {
			upload = MobileUploadUtils.checkUploadParam(params, ret);
			
			String realPath =  upload.getToken() + "/" ;
	        String filePath = (String) prop.get("images_upload_path") + "/" + realPath;
	
	        String []path = MobileUploadUtils.upload(request, prop, files, filePath, realPath, upload);
            
            SysDriver sysDriver = new SysDriver();
            
            sysDriver.setSysDriverId(upload.getToken());
            sysDriver.setDrivingLice(path[0]);
            sysDriver.setVehicleLice(path[1]);
            sysDriver.setUpdatedDate(new Date());

            driverService.saveDriver(sysDriver, "update");
            
            Data data = new Data();
            data.setImageUrl(path.toString());
            
            ArrayList<Data> list = new ArrayList<Data>();
        	list.add(data);
            
            ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, MobileGetUserInfoUtils.RET_USERINFO_SUCCESS, list);

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
    
    @RequestMapping(value = "Feedback")
    @ResponseBody
    public String feedback(MobileParams params){
    	
    	MobileReturn ret = new MobileReturn();
    	MobileFeedBack feedback = new MobileFeedBack();
    	
    	try {
    		feedback = MobileFeedBackUtils.checkFeedBackParam(params, ret);

    		mbUserSuggestServices.saveSuggester(feedback);
            
    		Data data = new Data();
        	
        	ArrayList<Data> list = new ArrayList<Data>();
        	list.add(data);
            
            ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, MobileFeedBackUtils.RET_FEEDBACK_SAVE_MSG, list);

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
    
    @RequestMapping(value = "ReportTheLoss")
    @ResponseBody
    public String reportTheLoss(MobileParams params){
    	
    	MobileReturn ret = new MobileReturn();
    	MobileLoss loss = new MobileLoss();
    	
    	try {
    		loss = ReportLossUtil.checkReportTheLossParam(params, ret);

    		SysDriver driver = driverService.queryDriverByPK(loss.getToken());
    		int retvale = sysUserAccountService.changeStatus(driver.getAccount().getSysUserAccountId(), loss.getLossType(), driver.getCardInfo().getCard_no());
            
            
            if(retvale >0 ){
            	Data data = new Data();
            	
            	ArrayList<Data> list = new ArrayList<Data>();
            	list.add(data);
            	
            	ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, ReportLossUtil.RET_LOSS_SUCESS_MSG, list);
            }
           
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
    
    @RequestMapping(value = "TransactionRecord")
    @ResponseBody
    public String transactionRecord(MobileParams params){
    	
    	MobileReturn ret = new MobileReturn();
    	MobileRecord record = new MobileRecord();
    	
    	try {
    		record = MobileRecordUtils.checkParam(params, ret);
    		
    		SysDriver driver = driverService.queryDriverByPK(record.getToken());
			
			SysOrder order = new SysOrder();
			order.setSysDriver(driver);
			order.setPageNum(Integer.valueOf(record.getCurrentPage()));
			order.setPageSize(Integer.valueOf(record.getPageSize()));
			
			ArrayList<Data> listc = new ArrayList<Data>();
    		
    		if(MobileRecordUtils.METHOD_RECHARGE.equals(record.getRecordType())){

    			PageInfo<Map<String, Object>> info = orderService.queryRechargeReport(order);
    			List<Map<String, Object>> list = info.getList();			
            	
    			for(int i=0;i<list.size();i++){
    				Data data = new Data();
    				data.setTime(list.get(i).get("order_date").toString());
    				data.setCashBack(list.get(i).get("cash_back").toString());
    				data.setTitle(list.get(i).get("channel").toString());
    				data.setAmount(list.get(i).get("cash").toString());
    				data.setOrderCode(list.get(i).get("order_number").toString());
    				data.setRechargePlatform(list.get(i).get("cash").toString());
    				data.setAmount(list.get(i).get("cash").toString());
    				
    				listc.add(data);
    			}
    		}else if(MobileRecordUtils.METHOD_CONSUME.equals(record.getRecordType())){
    			
    			PageInfo<Map<String, Object>> info = orderService.queryRechargeDriverReport(order);
    			
    			List<Map<String, Object>> list = info.getList();			
            	
    			for(int i=0;i<list.size();i++){
    				
    				Data data = new Data();
    				
    				data.setTime(list.get(i).get("order_date").toString());
    				data.setTitle(list.get(i).get("channel").toString());
    				data.setAmount(list.get(i).get("cash").toString());
    				data.setOrderCode(list.get(i).get("order_number").toString());
    				data.setRechargePlatform(list.get(i).get("cash").toString());
    				data.setAmount(list.get(i).get("cash").toString());
    				
    				List<SysOrderGoods> goods = sysOrderGoodsService.selectByOrderID(list.get(i).get("order_id").toString());
    				data.setGoods(goods);
    				
    				listc.add(data);
    			}
    		}else if(MobileRecordUtils.METHOD_TRANSFER.equals(record.getRecordType())){

    		}
    		
    		ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, MobileRecordUtils.RET_SUCCESS_MSG, listc);
    		
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
    
    @RequestMapping(value = "GetCitys")
    @ResponseBody
    public String getCitys(MobileParams params){
    	
    	MobileReturn ret = new MobileReturn();
    	
    	try {
    		GetCitysUtils.checkParam(params, ret);
    		
    		String opencity = (String) prop.get("opencity");
            
            if(opencity.split("~").length >0 ){
            	Data data = new Data();
            	data.setCity(opencity.split("~"));
            	
            	ArrayList<Data> list = new ArrayList<Data>();
            	list.add(data);
            	
            	ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, GetCitysUtils.RET_QUERY_SUCCESS_MSG, list);
            }
           
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
    
    @RequestMapping(value = "ReturnCash")
    @ResponseBody
    public String getReturnCash(MobileParams params){
    	
    	MobileReturn ret = new MobileReturn();
    	
    	try {
    		ReturnCashUtil.checkParam(params, ret);
    		
            
            Data data = new Data();
            	
            ArrayList<Data> list = new ArrayList<Data>();
            list.add(data);
            	
            ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_SUCCESS, GetCitysUtils.RET_QUERY_SUCCESS_MSG, list);

           
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
