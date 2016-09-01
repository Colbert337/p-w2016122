package com.sysongy.api.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.sysongy.api.mobile.model.base.Data;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.feedback.MbUserSuggest;
import com.sysongy.api.mobile.model.feedback.MobileFeedBack;
import com.sysongy.api.mobile.model.loss.MobileLoss;
import com.sysongy.api.mobile.model.record.MobileRecord;
import com.sysongy.api.mobile.model.upload.MobileUpload;
import com.sysongy.api.mobile.model.userinfo.MobileUserInfo;
import com.sysongy.api.mobile.model.verification.MobileVerification;
import com.sysongy.api.mobile.service.MbDealOrderService;
import com.sysongy.api.mobile.service.MbUserSuggestServices;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.ali.OrderInfoUtil2_0;
import com.sysongy.api.mobile.tools.feedback.MobileFeedBackUtils;
import com.sysongy.api.mobile.tools.getcitys.GetCitysUtils;
import com.sysongy.api.mobile.tools.loss.ReportLossUtil;
import com.sysongy.api.mobile.tools.record.MobileRecordUtils;
import com.sysongy.api.mobile.tools.register.MobileRegisterUtils;
import com.sysongy.api.mobile.tools.returncash.ReturnCashUtil;
import com.sysongy.api.mobile.tools.upload.MobileUploadUtils;
import com.sysongy.api.mobile.tools.userinfo.MobileGetUserInfoUtils;
import com.sysongy.api.mobile.tools.verification.MobileVerificationUtils;
import com.sysongy.api.mobile.tools.wechat.MD5;
import com.sysongy.api.mobile.tools.wechat.Util;
import com.sysongy.api.util.DESUtil;
import com.sysongy.api.util.ParameterUtil;
import com.sysongy.api.util.ShareCodeUtil;
import com.sysongy.poms.base.model.DistCity;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.base.service.DistrictService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.model.Suggest;
import com.sysongy.poms.mobile.service.MbBannerService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;
import com.sysongy.poms.ordergoods.service.SysOrderGoodsService;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.*;
import com.tencent.mm.sdk.modelpay.PayReq;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/api/v1/mobile")
@Controller
public class MobileController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
	public final String keyStr = "sysongys";
	String localPath = (String) prop.get("http_poms_path");

	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2016011801102578"; //TODO 需要自定义常量类

	/** 商户私钥，pkcs8格式 */
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALMGuZtJV5TvfNZHvIYUVqo6Gy2lqvYZ0DP2Dr7Si5aPqd+sEhDy7TjWAUlYV86d3z+/oOrhap9iHJn80oJoQk3UTf/NM/XNF0PCjW1UiGORQyFiBYFIVfXSylFWQ5IFBmxQfgu5cPzZNeTcRsooPTrON0/OAItxsuGbgDlCMusjAgMBAAECgYA1HhiyB2fSC+C5X12DVsOEDGuF9rKsBGqvECG94pCCIqwfblmJ59oU1AJbtbeP2W2k54GiTzGoip673bTD9pU9LPdyelWlFBePGEHiREbno2fXB01Tb9ML/TrZG5JFZdv7IS7ekitWiiK1lKwFg3mujMDXrFAwBQd/kBd0eOG7cQJBAOikToenP7JgCYuWM7N5pKZ5GsfZJsKVqDRZyqDDih0gXTdafW49IlwMHVpoWV01PwiG5feQDjASXv+MDNUgwbsCQQDFAFCFwWFjjRiKShgRjZurYOvSc04XqACf1lROefLrKJ+BsrFQJAhWahHEmJiV2pNuYnSybx2e2AwFbyt8pJG5AkEA3wduFdS8VxiE7iJATIaI1+PwTbmb1B4/lHikrnzn8sZtNzz0VPQc9ZvTpDG3wojidh1FaJHdWC60jk9ImiZ+MwJBAIw07Bo2Bo0ml2ec0kJz6W3wngX64IJ/pGodzYTI0DXDhLp3JjEmY/S0qw6jmD1XAgTW970izg8GLpATjfy416kCQQChQUYwe76hdGA/60lCycBCwiwYV+aHxezFnC2PcUxMif/cbKIwJvR6nhQK1QTKL60ExlRSKXA9xbB2eH/QXRNj";

	public static final String APP_ID = "wxbc6365b82bab3598";//Sysongy
	private static final String MCH_ID = "1280581101";//Sysongy
	private static final String API_KEY = "Gy325U2312T360o2312t2p23b212tR4a";//Sysongy
	private static final String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

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
	@Autowired
	DistrictService districtService;
	@Autowired
	GastationService gastationService;
	@Autowired
	GsGasPriceService gsGasPriceService;
	@Autowired
	MbDealOrderService mbDealOrderService;
	@Autowired
	SysCashBackService sysCashBackService;
	@Autowired
	MbBannerService mbBannerService;
	@Autowired
	UsysparamService usysparamService;

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
		result.setMsg("验证码已发送,有效期10分钟！");
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

				//设置短信有效期10分钟
				redisClientImpl.addToCache(verification.getPhoneNum(), checkCode.toString(), 600);
				Map<String, Object> tokenMap = new HashMap<>();
				tokenMap.put("verificationCode",checkCode.toString());
				result.setData(tokenMap);
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密

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
				driver.setMobilePhone(mainObj.optString("phoneNum"));
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
					List<SysDriver> driverlist = driverService.queryForPageList(driver);
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
						if(driver.getTransportionName() != null && !"".equals(driver.getTransportionName().toString()) ){
							resultMap.put("company",driver.getTransportionName());
						}else if(driver.getGasStationName() != null && !"".equals(driver.getGasStationName().toString())){
							resultMap.put("company",driver.getGasStationName());
						}else{
							resultMap.put("company","");
						}
						resultMap.put("cardId",(driver.getCardId() == null || "".equals(driver.getCardId())) ? "":driver.getCardId());
						resultMap.put("isPayCode",(driver.getPayCode() == null || "".equals(driver.getPayCode())) ? "false":"true");
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
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
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
	 * 修改登录密码
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/user/updatePassword")
	@ResponseBody
	public String updatePassword(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("修改登录密码成功！");
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
				SysDriver sysDriver = new SysDriver();
				sysDriver.setSysDriverId(mainObj.optString("token"));
				String password = mainObj.optString("password");
				if(password != null && !"".equals(password)){
					password = Encoder.MD5Encode(password.getBytes());
					sysDriver.setPassword(password);
					sysDriver.setSysDriverId(mainObj.optString("token"));

					driverService.saveDriver(sysDriver,"update");
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("密码为空！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("修改登录密码成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("修改登录密码失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("修改登录密码失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 修改用户信息
	 * @param params
	 * @return
	 */
	@RequestMapping(value = {"/user/updateUser"})
	@ResponseBody
	public String updateUser(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("修改用户信息成功！");
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
					driver.setFullName(mainObj.optString("name"));
					driver.setDeviceToken(mainObj.optString("deviceToken"));
					driver.setAvatarB(mainObj.optString("imgUrl"));
					int resultVal = driverService.saveDriver(driver,"update");
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("修改用户信息失败！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			logger.error("修改用户信息成功： " + resultStr);
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("修改用户信息失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("修改用户信息失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}


	/**
	 * 设置支付密码
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/user/setPaycode")
	@ResponseBody
	public String setPaycode(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("支付密码设置成功！");
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
				if(mainObj.optString("token") == null){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("用户ID为空！");
				}else if(mainObj.optString("paycode") == null){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("支付密码为空！");
				}else if(mainObj.optString("verificationCode") == null){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("验证码为空！");
				}else{
					Map<String, Object> resultMap = new HashMap<>();
					driver.setSysDriverId(sysDriverId);
					driver.setPayCode(mainObj.optString("paycode"));

					driverService.saveDriver(driver,"update");//设置支付密码
				}

			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("支付密码设置成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("支付密码设置失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("支付密码设置失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 修改支付密码
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/user/updatePayCode")
	@ResponseBody
	public String updatePayCode(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("修改支付密码成功！");
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
				SysDriver sysDriver = new SysDriver();
				sysDriver.setSysDriverId(mainObj.optString("token"));
				String driverId = mainObj.optString("token");
				String oldPayCode = mainObj.optString("oldPayCode");
				oldPayCode = Encoder.MD5Encode(oldPayCode.getBytes());
				SysDriver driver = driverService.queryDriverByPK(driverId);
				String payCode = driver.getPayCode();
				if(payCode.equals(oldPayCode)){
					//判断原支付密码是否正确
					String newPayCode = mainObj.optString("newPayCode");
					if(newPayCode != null && !"".equals(newPayCode)){
						newPayCode = Encoder.MD5Encode(newPayCode.getBytes());
						sysDriver.setPassword(newPayCode);
						driverService.saveDriver(sysDriver,"update");
					}
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("原始密码错误！");
				}

			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("修改支付密码成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("修改支付密码失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("修改支付密码失败： " + e);
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
			/*resultStr = DESUtil.encode(keyStr,resultStr);//参数加密*/

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
	 * 申请实名认证
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/user/realNameAuth")
	@ResponseBody
	public String realNameAuth(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("实名认证已提交审核，请耐心等待！");
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
				driver.setFullName(mainObj.optString("name"));
				driver.setPlateNumber(mainObj.optString("plateNumber"));
				driver.setFuelType(mainObj.optString("gasType"));
				if(mainObj.optString("endTime") != null && !"".equals(mainObj.optString("endTime"))){
					SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
					Date date = sft.parse(mainObj.optString("endTime"));
					driver.setExpiryDate(date);
				}
				driver.setDrivingLice(mainObj.optString("drivingLicenseImageUrl"));
				driver.setVehicleLice(mainObj.optString("driverLicenseImageUrl"));
				driver.setSysDriverId(mainObj.optString("token"));
				driver.setCheckedStatus(GlobalConstant.DriverCheckedStatus.CERTIFICATING);
				driver.setIdentityCard(mainObj.optString("idCard"));

				int resultVal = driverService.saveDriver(driver,"update");
				if(resultVal <= 0){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("用户ID为空，申请失败！");
				}

			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密

			logger.error("实名认证已提交审核，请耐心等待！： " + resultStr);
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("申请失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("申请失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取参数列表
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/util/paramList")
	@ResponseBody
	public String getParamList(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询返现规则成功！");
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
				String gcode = mainObj.optString("code");
				if(gcode != null && !"".equals(gcode)){
					List<Map<String, Object>> usysparamList = usysparamService.queryUsysparamMapByGcode(gcode);
					result.setListMap(usysparamList);
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("查询成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询列表失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询列表失败： " + e);
			resutObj.remove("data");
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
	@RequestMapping(value = "/user/feedback")
	@ResponseBody
	public String feedback(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("感谢您的宝贵建议！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "！";
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
				MbUserSuggest suggest = new MbUserSuggest();
				suggest.setMbUserSuggestId(UUIDGenerator.getUUID());
				suggest.setSysDriverId(mainObj.optString("token"));
				suggest.setSuggest(mainObj.optString("content"));
				suggest.setMobilePhone(mainObj.optString("mobilePhone"));
				suggest.setCreatedDate(new Date());
				suggest.setUpdatedDate(new Date());

				int resultVal = mbUserSuggestServices.saveSuggester(suggest);
				if(resultVal <= 0){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("提交失败！");
				}else{
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密

			logger.error("提交建议成功！！： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("提交失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("提交失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

    /**
	 * 卡/账户挂失/解冻
	 * @param params
	 * @return
     */
    @RequestMapping(value = "/loss/reportTheLoss")
    @ResponseBody
    public String reportTheLoss(String params){
		MobileReturn result = new MobileReturn();
		String failStr = "参数有误";
		result.setStatus(MobileReturn.STATUS_SUCCESS);
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
				SysDriver driver = driverService.queryDriverByPK(mainObj.optString("token"));
				String lossType = mainObj.optString("lossType");
				/*String cardId = mainObj.optString("cardId");*/
				int retvale = 0;//操作影响行数
				if(lossType != null){//类型等于0 或者等于1
					retvale = sysUserAccountService.changeStatus(driver.getAccount().getSysUserAccountId(), lossType, driver.getCardInfo().getCard_no());
				}

				if(retvale >0 ){
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					if("2".equals(lossType)){
						failStr = "解除挂失";
					}else{
						failStr = ("挂失");
					}
					result.setMsg(failStr+"成功！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密

			logger.error(failStr+"成功： " + resultStr);
        } catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg(failStr+"失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error(failStr+"失败！" + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
    }


	/**
	 * 获取开通城市列表
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/util/getCityList")
	@ResponseBody
	public String getCityList(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询成功！");
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
				List<DistCity> cityList = districtService.queryHotCityList();
				List<Map<String, Object>> listMap = new ArrayList<>();
				if(cityList != null && cityList.size() > 0){
					for(DistCity city:cityList){
						Map<String, Object> cityMap = new HashMap<>();
						cityMap.put("cityName",city.getCityName());
						listMap.add(cityMap);
					}
					result.setListMap(listMap);
				}

			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密

			logger.error("登录成功： " + resultStr);
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询城市失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询城市失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

    /**
	 * 获取地图信息列表
	 * @param params
	 * @return
     */
    @RequestMapping(value = "/map/getStationList")
    @ResponseBody
    public String queryStationList(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询加注站信息成功！");
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
				//获取气站列表
				Gastation gastation = new Gastation();
				List<Map<String, Object>> gastationArray = new ArrayList<>();
				List<Gastation> gastationList = gastationService.getAllStationList(gastation);
				if(gastationList != null && gastationList.size() > 0){
					for (Gastation gastationInfo:gastationList){
						Map<String, Object> gastationMap = new HashMap<>();
						gastationMap.put("stationId",gastationInfo.getSys_gas_station_id());
						gastationMap.put("name",gastationInfo.getGas_station_name());
						gastationMap.put("longitude",gastationInfo.getLongitude());
						gastationMap.put("latitude",gastationInfo.getLatitude());
						gastationMap.put("service","");
						//获取当前气站价格列表
						List<Map<String, Object>> priceList = gsGasPriceService.queryPriceList(gastationInfo.getSys_gas_station_id());
						gastationMap.put("priceList",priceList);
						gastationMap.put("phone",gastationInfo.getContact_phone());
						if(gastationInfo.getStatus().equals("0")){
							gastationMap.put("state","开启");
						}else{
							gastationMap.put("state","关闭");
						}
						gastationMap.put("preferential","");
						gastationMap.put("address",gastationInfo.getAddress());

						gastationArray.add(gastationMap);
					}

					result.setListMap(gastationArray);
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密

			logger.error("查询气站信息成功： " + resultStr);
        } catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询气站信息失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询气站信息失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
    }

	/**
	 * 个人转账
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/deal/transferAccounts")
	@ResponseBody
	public String transferAccounts(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("转账成功！");
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
				Map<String, Object> driverMap = new HashMap<>();
				driverMap.put("token",mainObj.optString("token"));
				driverMap.put("account",mainObj.optString("account"));
				driverMap.put("name",mainObj.optString("name"));
				driverMap.put("amount",mainObj.optString("amount"));
				driverMap.put("remark",mainObj.optString("remark"));
				driverMap.put("paycode",mainObj.optString("paycode"));

				int resultVal = mbDealOrderService.transferDriverToDriver(driverMap);
				if(resultVal < 0){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("账户余额不足,无法转账！");
				}else if(resultVal == 1){
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("转账成功！");
				}else if(resultVal == 2){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("账户不存在,无法转账！");
				}else if(resultVal == 3){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("司机不存在,无法转账！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("转账成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("转账失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("转账失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 返现规则列表
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/deal/backRule")
	@ResponseBody
	public String getCashBackList(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询返现规则列表成功！");
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
				List<Map<String, Object>> cashBackList = sysCashBackService.queryCashBackList();
				result.setListMap(cashBackList);
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
//			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("查询返现规则列表成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询返现规则列表失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询返现规则列表失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 头条推广
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/msg/extension")
	@ResponseBody
	public String recharge(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询头条推广成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		MbBanner mbBanner = new MbBanner();
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
				PageBean bean = new PageBean();
				String http_poms_path =  (String) prop.get("http_poms_path");
				String cityName = mainObj.optString("cityName");
				DistCity city = new DistCity();
				city.setCityName(cityName);
				city = districtService.queryCityInfo(city);
				String cityId = "";
				if(city != null){
					cityId = city.getCityId();
				}
				mbBanner.setCityId(cityId);
				if(mbBanner.getPageNum() == null){
					mbBanner.setPageNum(GlobalConstant.PAGE_NUM);
					mbBanner.setPageSize(GlobalConstant.PAGE_SIZE);
				}else{
					mbBanner.setPageNum(mainObj.optInt("pageNum"));
					mbBanner.setPageSize(mainObj.optInt("pageSize"));
				}

				if(mainObj.get("extendType") != null && !"".equals(mainObj.get("extendType").toString())){
					mbBanner.setImgType(mainObj.get("extendType").toString());
				}else{
					mbBanner.setImgType(GlobalConstant.ImgType.TOP);
				}

				PageInfo<MbBanner> pageInfo = mbBannerService.queryMbBannerListPage(mbBanner);
				List<MbBanner> mbBannerList = pageInfo.getList();
				List<Map<String, Object>> bannerList = new ArrayList<>();
				if(mbBannerList != null && mbBannerList.size() > 0){
					SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					for (MbBanner banner:mbBannerList){
						Map<String, Object> bannerMap = new HashMap<>();
						bannerMap.put("title",banner.getTitle());
						bannerMap.put("content","");
						bannerMap.put("time",sft.format(banner.getCreatedDate()) );
						bannerMap.put("contentUrl","");
						if(banner.getImgPath() != null && !"".equals(banner.getImgPath().toString())){
							bannerMap.put("imageUrl",http_poms_path+banner.getImgPath());
						}else{
							bannerMap.put("imageUrl","");
						}


						bannerList.add(bannerMap);
					}
				}
				result.setListMap(bannerList);

			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
//			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("查询头条推广成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询头条推广失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询头条推广失败： " + e);
			e.printStackTrace();
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 充值记录
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/record/rechargeRecord")
	@ResponseBody
	public String rechargeRecord(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询充值记录成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);//参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			SysOrder order = new SysOrder();

			SysDriver sysDriver = new SysDriver();
			sysDriver.setSysDriverId(mainObj.optString("token"));
			order.setSysDriver(sysDriver);

			/**
			 * 请求接口
			 */
			if(mainObj != null){
				if(order.getPageNum() == null){
					order.setPageNum(GlobalConstant.PAGE_NUM);
					order.setPageSize(GlobalConstant.PAGE_SIZE);
				}else{
					order.setPageNum(mainObj.optInt("pageNum"));
					order.setPageSize(mainObj.optInt("pageSize"));
				}

			 	PageInfo<Map<String, Object>> pageInfo = orderService.queryDriverReChargePage(order);
				List<Map<String,Object>> reChargeList = new ArrayList<>();
				Map<String,Object> reCharge = new HashMap<>();
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
				BigDecimal totalBack = new BigDecimal(BigInteger.ZERO);
			 	if(pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {

					for(Map<String, Object> map:pageInfo.getList()){
						Map<String, Object> reChargeMap = new HashMap<>();
						reChargeMap.put("orderNum",map.get("orderNumber"));
						reChargeMap.put("amount",map.get("cash"));
						reChargeMap.put("cashBack",map.get("cashBackDriver"));
						reChargeMap.put("rechargePlatform",map.get("channel"));

						String chargeType = "";
						if(map.get("chargeType") != null && !"".equals(map.get("chargeType").toString())){
							chargeType = GlobalConstant.getCashBackNumber(map.get("chargeType").toString());
						}
						reChargeMap.put("paymentType",chargeType);
						reChargeMap.put("remark",map.get("remark"));
						String dateTime = "";
						if(map.get("orderDate") != null && !"".equals(map.get("orderDate").toString())){
							dateTime = sft.format(new Date());
						}
						reChargeMap.put("time",dateTime);

						reChargeList.add(reChargeMap);

						//汇总充值总额
						if(reChargeMap.get("cash") != null && !"".equals(reChargeMap.get("cash").toString())){
							totalCash = totalCash.add(new BigDecimal(reChargeMap.get("cash").toString()));
						}
						//汇总返现总额
						if(reChargeMap.get("cashBackDriver") != null && !"".equals(reChargeMap.get("cashBackDriver").toString())){
							totalBack = totalBack.add(new BigDecimal(reChargeMap.get("cashBackDriver").toString()));
						}
					}

					reCharge.put("totalCash",totalCash);
					reCharge.put("totalBack",totalBack);
					reCharge.put("listMap",reChargeList);
				}

			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("查询充值记录成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询充值记录失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询充值记录失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 消费记录
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/record/consumeRecord")
	@ResponseBody
	public String consumeRecord(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询消费记录成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);//参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			SysOrder order = new SysOrder();

			SysDriver sysDriver = new SysDriver();
			sysDriver.setSysDriverId(mainObj.optString("token"));
			order.setSysDriver(sysDriver);

			/**
			 * 请求接口
			 */
			if(mainObj != null){
				if(order.getPageNum() == null){
					order.setPageNum(GlobalConstant.PAGE_NUM);
					order.setPageSize(GlobalConstant.PAGE_SIZE);
				}else{
					order.setPageNum(mainObj.optInt("pageNum"));
					order.setPageSize(mainObj.optInt("pageSize"));
				}

				PageInfo<Map<String, Object>> pageInfo = orderService.queryDriverConsumePage(order);
				List<Map<String,Object>> reChargeList = new ArrayList<>();
				Map<String,Object> reCharge = new HashMap<>();
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
				BigDecimal totalBack = new BigDecimal(BigInteger.ZERO);
				if(pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {

					for(Map<String, Object> map:pageInfo.getList()){
						Map<String, Object> reChargeMap = new HashMap<>();
						reChargeMap.put("orderNum",map.get("orderNumber"));
						reChargeMap.put("amount",map.get("cash"));
						reChargeMap.put("gasStationName",map.get("channel"));
						reChargeMap.put("gasStationId",map.get("channelNumber"));
						reChargeMap.put("gasTotal",map.get("goodsSum"));
						reChargeMap.put("payStatus","1");

						String chargeType = "";
						if(map.get("chargeType") != null && !"".equals(map.get("chargeType").toString())){
							chargeType = GlobalConstant.getCashBackNumber(map.get("chargeType").toString());
						}
						reChargeMap.put("paymentType",chargeType);
						reChargeMap.put("remark",map.get("remark"));
						String dateTime = "";
						if(map.get("orderDate") != null && !"".equals(map.get("orderDate").toString())){
							dateTime = sft.format(new Date());
						}
						reChargeMap.put("time",dateTime);

						reChargeList.add(reChargeMap);

						//汇总消费总额
						if(reChargeMap.get("cash") != null && !"".equals(reChargeMap.get("cash").toString())){
							totalCash = totalCash.add(new BigDecimal(reChargeMap.get("cash").toString()));
						}
					}

					reCharge.put("totalCash",totalCash);
					reCharge.put("listMap",reChargeList);
				}

			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}

			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("查询消费记录成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询消费记录失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询消费记录失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 转账记录
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/record/transferRecord")
	@ResponseBody
	public String transferRecord(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询转账记录成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		SysOrder order = new SysOrder();
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);//参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");

			SysDriver sysDriver = new SysDriver();
			sysDriver.setSysDriverId(mainObj.optString("token"));
			order.setSysDriver(sysDriver);

			/**
			 * 请求接口
			 */
			if(mainObj != null){
				if(order.getPageNum() == null){
					order.setPageNum(GlobalConstant.PAGE_NUM);
					order.setPageSize(GlobalConstant.PAGE_SIZE);
				}else{
					order.setPageNum(mainObj.optInt("pageNum"));
					order.setPageSize(mainObj.optInt("pageSize"));
				}

				PageInfo<Map<String, Object>> pageInfo = orderService.queryDriverTransferPage(order);
				List<Map<String,Object>> reChargeList = new ArrayList<>();
				Map<String,Object> reCharge = new HashMap<>();
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
				BigDecimal totalBack = new BigDecimal(BigInteger.ZERO);
				if(pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {

					for(Map<String, Object> map:pageInfo.getList()){
						Map<String, Object> reChargeMap = new HashMap<>();
						reChargeMap.put("orderNum",map.get("orderNumber"));
						reChargeMap.put("amount",map.get("cash"));
						reChargeMap.put("operator",map.get("operator"));

						reChargeMap.put("remark",map.get("remark"));
						String dateTime = "";
						if(map.get("orderDate") != null && !"".equals(map.get("orderDate").toString())){
							dateTime = sft.format(new Date());
						}
						reChargeMap.put("time",dateTime);

						reChargeList.add(reChargeMap);

						//汇总转出/转入总额
						if(reChargeMap.get("cash") != null && !"".equals(reChargeMap.get("cash").toString())){
							BigDecimal tempVal = new BigDecimal(reChargeMap.get("cash").toString());

							if(tempVal.compareTo(BigDecimal.ZERO) > 0){
								totalCash = totalCash.add(tempVal);
							}else{
								totalBack = totalBack.add(tempVal);
							}
						}

					}

					reCharge.put("totalOut",totalCash);
					reCharge.put("totalIn",totalBack);
					reCharge.put("listMap",reChargeList);
				}

			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}

			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("查询转账记录成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询转账记录失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询转账记录失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 在线充值
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/deal/paramList")
	@ResponseBody
	public String getRecharge(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("充值成功！");
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
				String payType = mainObj.optString("payType");
				String feeCount = mainObj.optString("feeCount");
				String driverID = mainObj.optString("token");
				String orderID = UUIDGenerator.getUUID();
				Map<String, Object> data = new HashedMap();
				SysOrder sysOrder = null;
				String thirdPartyID = null;
				if(payType.equalsIgnoreCase("2")){           //支付宝支付
					sysOrder = createNewOrder(orderID, driverID, feeCount, GlobalConstant.OrderChargeType.CHARGETYPE_ALIPAY_CHARGE);  //TODO充值成功后再去生成订单
					orderService.checkIfCanChargeToDriver(sysOrder);
					Map<String, String> paramsApp = OrderInfoUtil2_0.buildOrderParamMap(APPID, feeCount, "司集云平台-会员充值",
							"司集云平台-会员充值", orderID);
					String orderParam = OrderInfoUtil2_0.buildOrderParam(paramsApp);
					String sign = OrderInfoUtil2_0.getSign(paramsApp, RSA_PRIVATE);
					thirdPartyID = orderID;
					if(sysOrder != null){
						sysOrder.setThirdPartyOrderID(thirdPartyID);
						int nCreateOrder = orderService.insert(sysOrder, null);
						if(nCreateOrder < 1)
							throw new Exception("订单生成错误：" + sysOrder.getOrderId());
					}
					data.put("payReq", orderParam + "&" + sign);
					result.setData(data);
				} else if(payType.equalsIgnoreCase("1")){   //微信支付
					sysOrder = createNewOrder(orderID, driverID, feeCount, GlobalConstant.OrderChargeType.CHARGETYPE_WEICHAT_CHARGE); //TODO充值成功后再去生成订单
					orderService.checkIfCanChargeToDriver(sysOrder);
					String entity = genProductArgs(orderID);
					byte[] buf = Util.httpPost(url, entity);
					String content = new String(buf, "utf-8");
					Map<String, String> orderHashs = decodeXml(content);
					String payReq = genPayReq(orderHashs);
					thirdPartyID = orderHashs.get("prepay_id");
					if(sysOrder != null){
						sysOrder.setThirdPartyOrderID(thirdPartyID);
						int nCreateOrder = orderService.insert(sysOrder, null);
						if(nCreateOrder < 1)
							throw new Exception("订单生成错误：" + sysOrder.getOrderId());
					}
					data.put("payReq", payReq);
					result.setData(data);
				}


			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("充值成功： " + resultStr);
			return resultStr;
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("充值失败！" + e.getMessage());
			resutObj = JSONObject.fromObject(result);
			logger.error("充值失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		}
	}
	
	/**
	 * 修改账号手机号/密保手机
	 */
	@RequestMapping(value = "/user/updatePhone")
	@ResponseBody
	public String updatePhone(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("修改成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 请求接口
			 */
			if(mainObj != null){
				//创建对象
				SysDriver sysDriver = new SysDriver();
				String phoneType = mainObj.optString("phoneType");
				//原电话号码赋值
				sysDriver.setMobilePhone(mainObj.optString("phoneNum"));
				//数据库查询
				List<SysDriver> driver = driverService.queryeSingleList(sysDriver);
				if(!driver.isEmpty()){
					//新电话号码
					String newPhoneNum = mainObj.optString("newPhoneNum");
					//修改账户手机
					if("1".equals(phoneType)){
						sysDriver.setUserName(newPhoneNum);
						sysDriver.setMobilePhone(newPhoneNum);
					}else{
						sysDriver.setSecurityMobilePhone(newPhoneNum);
					}
					sysDriver.setDriverType(driver.get(0).getDriverType());
					sysDriver.setSysDriverId(driver.get(0).getSysDriverId());
					int resultVal = driverService.saveDriver(sysDriver,"update");
					//返回大于0，成功
					if(resultVal <= 0){
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("修改账号手机号/密保手机失败！");
					}
					Map<String, Object> dataMap = new HashMap<>();
					dataMap.put("resultVal","true");
					result.setData(dataMap);
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("原始电话号码有误！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("修改账号手机号/密保手机成功： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("修改账号手机号/密保手机失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("修改账号手机号/密保手机失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}
	
	/**
	 * 重置支付密码
	 */
	@RequestMapping(value = "/user/resetPayCode")
	@ResponseBody
	public String resetPayCode(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("重置支付密码成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		String initialPassword = "12345678";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 请求接口
			 */
			if(mainObj != null){
				//创建对象
				SysDriver sysDriver = new SysDriver();
				//电话号码赋值
				sysDriver.setMobilePhone(mainObj.optString("phoneNum"));
				//数据库查询
				List<SysDriver> driver = driverService.queryeSingleList(sysDriver);
				if(!driver.isEmpty()){
					//初始密码加密、赋值
					initialPassword = Encoder.MD5Encode(initialPassword.getBytes());
					sysDriver.setPayCode(initialPassword);
					sysDriver.setSysDriverId(driver.get(0).getSysDriverId());
					//更新初始密码
					int resultVal = driverService.saveDriver(sysDriver,"update");
					//返回大于0，成功
					if(resultVal <= 0){
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("重置支付密码失败！");
					}
					Map<String, Object> dataMap = new HashMap<>();
					dataMap.put("resultVal","true");
					result.setData(dataMap);
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("电话号码有误！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			
			resultStr = resutObj.toString();
			logger.error("重置支付密码成功： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("重置支付密码失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("重置支付密码失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}
	
	/**
	 * 最高返现规则列表
	 */
	@RequestMapping(value = "/deal/maxCashBack")
	@ResponseBody
	public String maxCashBack(String params){
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 请求接口
			 */
			List<SysCashBack> list = sysCashBackService.queryMaxCashBack();
			Map<String, Object> dataMap = new HashMap<>();
			for(int i=0;i<list.size();i++){
				if(list.get(i).getSys_cash_back_no().equals("101")){
					dataMap.put("alipay",list.get(i).getCash_per());
				}else{
					dataMap.put("wechat",list.get(i).getCash_per());
				}
			}
			result.setData(dataMap);
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("查询成功： " + resultStr);
//			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
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

	private String genPayReq(Map<String, String> resultunifiedorder ) {

		/*
		* 1、调用服务端获取支付信息
		* {
            "appid": "wxb4ba3c02aa476ea1",
            "noncestr": "1bd2880eedbdd821a647bd56c45ad879",
            "package": "Sign=WXPay",
            "partnerid": "1305176001",
            "prepayid": "wx201608250930356a8a94d8370901437288",
            "sign": "AA994BB10D048C7F2EBE8D48514C0F4E",
            "timestamp": 1472088635
		  }
		*
		* */
		PayReq payReq = new PayReq();
		payReq.appId = APP_ID;
		payReq.partnerId = MCH_ID;
		payReq.prepayId = resultunifiedorder.get("prepay_id");
		payReq.packageValue = "Sign=WXPay";
		payReq.nonceStr = genNonceStr();
		payReq.timeStamp = String.valueOf(genTimeStamp());
		//生成签名
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", payReq.appId));
		signParams.add(new BasicNameValuePair("noncestr", payReq.nonceStr));
		signParams.add(new BasicNameValuePair("package", payReq.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", payReq.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", payReq.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", payReq.timeStamp));
		payReq.sign = genAppSign(signParams);
		StringBuffer bRet = new StringBuffer();
		bRet.append("{\"appId\":\"")
			.append(APP_ID)
			.append("\",")
			.append("\"partnerId\":\"")
			.append(MCH_ID)
			.append("\",")
			.append("\"prepayId\":\"")
			.append(payReq.prepayId)
			.append("\",")
			.append("\"packageValue\":\"")
			.append("Sign=WXPay")
			.append("\",")
			.append("\"nonceStr\":\"")
			.append(payReq.nonceStr)
			.append("\",")
			.append("\"timeStamp\":\"")
			.append(payReq.timeStamp)
			.append("\",")
			.append("\"sign\":\"")
			.append(payReq.sign)
			.append("\"}");
		return bRet.toString();
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(API_KEY);
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		return appSign;
	}

	private String genProductArgs(String orderID) {
		try {
			String nonceStr = genNonceStr();
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", APP_ID));//应用ID
			packageParams.add(new BasicNameValuePair("body", "司集云平台-会员充值"));//商品描述 商品描述交易字段格式根据不同的应用场景按照以下格式 APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
			packageParams.add(new BasicNameValuePair("detail", "司集云平台-会员充值"));//商品详情 	商品名称明细列表
			packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));//	微信支付分配的商户号
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));//随机字符串
			packageParams.add(new BasicNameValuePair("notify_url",
					"http://36.47.179.46:8090/jfinal_demo/rechargeOrder/wxRechargeOrder"));//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
			packageParams.add(new BasicNameValuePair("out_trade_no", orderID));//商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
			packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));//用户端实际ip
			packageParams.add(new BasicNameValuePair("total_fee", "1"));//订单总金额，单位为分，详见支付金额
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));
			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));//签名，详见签名生成算法
			String xmlstring = toXml(packageParams);
			// return xmlstring;
			return xmlstring;
		} catch (Exception e) {
			return null;
		}
    }

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(API_KEY);

		String packageSign = null;
		try {
			packageSign = MD5.getMessageDigest(sb.toString().getBytes("UTF-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return packageSign;
	}

	public Map<String, String> decodeXml(String content) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				String nodeName = parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if ("xml".equals(nodeName) == false) {
							// 实例化student对象
							xml.put(nodeName, parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private SysOrder createNewOrder(String orderID, String driverID, String cash, String chargeType) throws Exception{
		SysOrder record = new SysOrder();

		record.setOrderId(orderID);
		record.setDebitAccount(driverID);
		record.setOperator(driverID);
		record.setOperatorSourceId(driverID);

		record.setCash(new BigDecimal(cash));
		record.setChargeType(chargeType);

		record.setIs_discharge("0");
		record.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
		record.setOrderType(GlobalConstant.OrderType.CHARGE_TO_DRIVER);
		record.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
		record.setOrderNumber(orderService.createOrderNumber(GlobalConstant.OrderType.CHARGE_TO_DRIVER));
		record.setOrderStatus(0);
		orderService.chargeToDriver(record);
		/**该充值步骤要放到第三方回调接口里面
		try{
			String orderCharge = orderService.chargeToDriver(record);
			if(!orderCharge.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS))
				throw new Exception("订单充值错误：" + orderCharge);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("订单充值错误：" + e);
		}
		 **/
		Date curDate = new Date();
		record.setOrderDate(curDate);
		record.setChannel("司集能源APP");
		record.setChannelNumber("");   //建立一个虚拟的APP气站，方便后期统计
		return record;
	}
}
