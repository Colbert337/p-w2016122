package com.sysongy.api.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sysongy.api.mobile.model.base.Data;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.feedback.MobileFeedBack;
import com.sysongy.api.mobile.model.loss.MobileLoss;
import com.sysongy.api.mobile.model.record.MobileRecord;
import com.sysongy.api.mobile.model.upload.MobileUpload;
import com.sysongy.api.mobile.model.userinfo.MobileUserInfo;
import com.sysongy.api.mobile.model.verification.MobileVerification;
import com.sysongy.api.mobile.service.MbUserSuggestServices;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.feedback.MobileFeedBackUtils;
import com.sysongy.api.mobile.tools.getcitys.GetCitysUtils;
import com.sysongy.api.mobile.tools.loss.ReportLossUtil;
import com.sysongy.api.mobile.tools.record.MobileRecordUtils;
import com.sysongy.api.mobile.tools.register.MobileRegisterUtils;
import com.sysongy.api.mobile.tools.returncash.ReturnCashUtil;
import com.sysongy.api.mobile.tools.upload.MobileUploadUtils;
import com.sysongy.api.mobile.tools.userinfo.MobileGetUserInfoUtils;
import com.sysongy.api.mobile.tools.verification.MobileVerificationUtils;
import com.sysongy.api.util.DESUtil;
import com.sysongy.api.util.ParameterUtil;
import com.sysongy.api.util.ShareCodeUtil;
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
import com.sysongy.util.UUIDGenerator;
import net.sf.json.JSONObject;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RequestMapping("/api/v1/mobile")
@Controller
public class MobileController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
	public final String keyStr = "sysongys";
	String localPath = (String) prop.get("http_poms_path");

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

	/**
	 * 用户登录
	 * @param params
	 * @return
     */
	@RequestMapping(value = {"/user/login"})
	@ResponseBody
	public String login(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg(MobileReturn.STATUS_MSG_SUCCESS);
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);//参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");

			/**
			 * 请求接口
			 */
			if(mainObj != null){
				SysDriver driver = new SysDriver();
				driver.setUserName(mainObj.optString("username"));
				driver.setPassword(mainObj.optString("password"));
				List<SysDriver> driverlist = driverService.queryeSingleList(driver);
				if(driverlist != null && driverlist.size() > 0){
					Map<String, Object> tokenMap = new HashMap<>();
					tokenMap.put("token",driverlist.get(0).getSysDriverId());
					result.setData(tokenMap);
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("登录失败！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
//			resultStr = DESUtil.decode(keyStr,resultStr);//参数解密
			logger.error("登录成功： " + resultStr);
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("登录失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("登录失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取短信验证码
	 * @param params
	 * @return
     */
	@RequestMapping(value = {"/user/getVerificationCode"})
	@ResponseBody
	public String getVerificationCode(String params) {

		MobileVerification verification = new MobileVerification();
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("获取验证码成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);//参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");

			/**
			 * 请求接口
			 */
			if(mainObj != null){
				//发送短信
				Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);
				verification.setPhoneNum(mainObj.optString("phoneNum"));
				verification.setReqType(MobileVerificationUtils.APP_DRIVER_REG);
				MobileVerificationUtils.sendMSG(verification, checkCode.toString());

				//设置短信有效期60秒
				redisClientImpl.addToCache(verification.getPhoneNum(), checkCode.toString(), 90);
				Map<String, Object> tokenMap = new HashMap<>();
				tokenMap.put("tokenMap",checkCode.toString());
				result.setData(tokenMap);
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
//			resultStr = DESUtil.encode(resultStr);
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("发送失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("发送失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 用户注册
	 * @param params
	 * @return
     */
	@RequestMapping(value = {"/user/register"})
	@ResponseBody
	public String register(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("注册成功");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);//参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");

			/**
			 * 请求接口
			 */
			if(mainObj != null){
				SysDriver driver = new SysDriver();
				driver.setUserName(mainObj.optString("phoneNum"));

				List<SysDriver> driverlist = driverService.queryeSingleList(driver);
				if(driverlist != null && driverlist.size() > 0){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("该手机号已注册！");
					throw new Exception(MobileRegisterUtils.RET_DRIVER_MOBILE_REGISTED);
				}else{
					String sysDriverId = UUIDGenerator.getUUID();
					driver.setPassword(mainObj.optString("password"));
					driver.setSysDriverId(sysDriverId);
					Integer tmp = driverService.saveDriver(driver, "insert");

					Map<String, Object> tokenMap = new HashMap<>();
					tokenMap.put("token",sysDriverId);
					result.setData(tokenMap);
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
//			resultStr = DESUtil.encode(resultStr);
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("注册失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("注册失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
		} finally {
			return resultStr;
		}

	}

	/**
	 * 获取用户信息
	 * @param params
	 * @return
     */
	@RequestMapping(value = {"/user/getUserInfo"})
	@ResponseBody
	public String getUserInfo(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("获取用户信息成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);//参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");

			/**
			 * 请求接口
			 */
			if(mainObj != null){
				SysDriver driver = new SysDriver();
				String sysDriverId = mainObj.optString("token");
				if(sysDriverId != null && !sysDriverId.equals("")){
					Map<String, Object> resultMap = new HashMap<>();
					driver.setSysDriverId(sysDriverId);
					List<SysDriver> driverlist = driverService.queryeSingleList(driver);
					if(driverlist != null && driverlist.size() > 0){
						List<Map<String, Object>> list = orderService.calcDriverCashBack(sysDriverId);
						String cashBack = "0.00";
						if(list != null && list.size() > 0 && list.get(0) != null && list.get(0).get("cash_back") != null){
							cashBack = list.get(0).get("cash_back").toString();
						}

						driver = driverlist.get(0);
						resultMap.put("nick",driver.getFullName());
						resultMap.put("account",driver.getUserName());
						resultMap.put("securityPhone",driver.getMobilePhone());
						resultMap.put("isRealNameAuth",GlobalConstant.DriverCheckedStatus.ALREADY_CERTIFICATED.equalsIgnoreCase(driver.getCheckedStatus())?"true":"false");
						resultMap.put("balance",driver.getAccount().getAccountBalance());

						resultMap.put("cumulativeReturn",cashBack);
						if(driver.getAvatarB() == null){
							resultMap.put("photoUrl","");
						}else{
							resultMap.put("photoUrl",localPath+driver.getAvatarB());
						}


						String invitationCode = driver.getInvitationCode();//获取邀请码
						if(invitationCode == null || "".equals(invitationCode)){
							invitationCode = ShareCodeUtil.toSerialCode(driver.getDriver_number());
							//更新当前司机邀请码
							SysDriver driverCode = new SysDriver();
							driverCode.setSysDriverId(driver.getSysDriverId());
							driverCode.setInvitationCode(invitationCode);
							driverService.saveDriver(driverCode,"update");
						}
						resultMap.put("invitationCode",invitationCode);
						result.setData(resultMap);
					}
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("登录失败！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
//			resultStr = DESUtil.decode(keyStr,resultStr);//参数解密
			logger.error("查询成功： " + resultStr);
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 图片上传
	 * @param request
     * @return
     */
	@RequestMapping(value = {"/img/imageUpload"})
    @ResponseBody
    public String upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("图片上传成功!");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		if (file.isEmpty()) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("上传图片为空！");
			resutObj = JSONObject.fromObject(result);
			logger.error("上传图片为空： ");
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		}

    	try {
			String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
			String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名

			/**
			 * 上传文件
			 */
			String realPath =  "/mobile/" ;
			String path = (String) prop.get("images_upload_path");
			String show_path = (String) prop.get("show_images_path");
			String http_poms_path =  (String) prop.get("http_poms_path");
			http_poms_path = http_poms_path + show_path;
			path+= realPath + filename;//上传路径
			show_path+= realPath + filename;//相对路径
			http_poms_path += realPath + filename;//绝对路径（web访问路径）
			File destFile = new File(path);
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

			/**
			 * 封装返回数据
			 */
			Map<String, Object> tokenMap = new HashMap<>();
			tokenMap.put("imageUrl",http_poms_path);
			tokenMap.put("relativeUrl",show_path);
			result.setData(tokenMap);

			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
//			resultStr = DESUtil.decode(keyStr,resultStr);//参数解密

			logger.error("图片上传成功： " + resultStr);
        } catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("图片上传失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("图片上传失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
    }

    /**
	 * 意见反馈
	 * @param params
	 * @return
     */
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

    /**
	 * 挂失
	 * @param params
	 * @return
     */
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

	/**
	 * 交易记录
	 * @param params
	 * @return
     */
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

    /**
	 * 获取开通城市列表
	 * @param params
	 * @return
     */
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

    /**
	 * 获取返现金额
	 * @param params
	 * @return
     */
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
