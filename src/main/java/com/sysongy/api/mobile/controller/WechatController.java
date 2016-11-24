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
import com.sysongy.poms.integral.model.IntegralHistory;
import com.sysongy.poms.integral.model.IntegralRule;
import com.sysongy.poms.integral.service.IntegralHistoryService;
import com.sysongy.poms.integral.service.IntegralRuleService;
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
import com.sysongy.poms.system.model.SysOperationLog;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.poms.system.service.SysOperationLogService;
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

@RequestMapping("/api/v1/wechat")
@Controller
public class WechatController {

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
	public final String wechatOperatorId = "553c248d906611e6b41c3497f629c5bd";

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
	@Autowired
	SysOperationLogService sysOperationLogService;
	@Autowired
	IntegralRuleService integralRuleService;
	@Autowired
	IntegralHistoryService integralHistoryService;

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
			 * 必填参数
			 */
			String username = "username";
			String password = "password";
			boolean b = JsonTool.checkJson(mainObj,username,password);
			/**
			 * 请求接口
			 */
			if(b){
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
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
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
			 * 必填参数
			 */
			String phoneNum = "phoneNum";
			String openId = "openId";
			String payCode = "payCode";
			String plateNumber = "plateNumber";
			boolean b = JsonTool.checkJson(mainObj,phoneNum,openId,payCode,plateNumber);
			/**
			 * 请求接口
			 */
			if(b){
				SysDriver driver = new SysDriver();
				driver.setUserName(mainObj.optString("phoneNum"));
				driver.setMobilePhone(mainObj.optString("phoneNum"));
				List<SysDriver> driverlist = driverService.queryeSingleList(driver);
				String newInvitationCode =ShareCodeUtil.toSerialCode(driver.getDriver_number());
				if(driverlist != null && driverlist.size() > 0){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("该手机号已注册！");
					//throw new Exception(MobileRegisterUtils.RET_DRIVER_MOBILE_REGISTED);
				}else{
					String sysDriverId = UUIDGenerator.getUUID();
					payCode = mainObj.optString("payCode");
					payCode = Encoder.MD5Encode(payCode.getBytes());
					driver.setPayCode(payCode);
					driver.setSysDriverId(sysDriverId);
					driver.setPlateNumber(mainObj.optString("plateNumber"));
					driver.setRegisSource("WeChat");//注册来源
					driver.setInvitationCode(newInvitationCode);//生成邀请码

					//获取邀请码并查询用户
					String invitationCode = mainObj.optString("invitationCode");
					driver.setRegisCompany(invitationCode);
					Integer tmp = driverService.saveDriver(driver, "insert", null, this.wechatOperatorId);
					//系统关键日志记录
					SysOperationLog sysOperationLog = new SysOperationLog();
					sysOperationLog.setOperation_type("zc");
					//手机端
					String name = driver.getFullName();
					if("".equals(name)||null==name){
						name = driver.getUserName();
					}
					if("".equals(name)||null==name){
						name = driver.getMobilePhone();
					}
					sysOperationLog.setLog_platform("2");
					sysOperationLog.setLog_content(name+"的账户卡通过微信注册成功！");
					//操作日志
					sysOperationLogService.saveOperationLog(sysOperationLog,driver.getSysDriverId());

					//大于0注册成功
					if(tmp > 0 ){
						if(invitationCode !=null && !"".equals(invitationCode)){
							SysDriver oldDriver = driverService.queryByInvitationCode(invitationCode);
							//如果用户不为空，则为原用户和新用户账户充值，创建订单进行充值
							if(oldDriver!=null){
								//获取注册返现规则信息
								Usysparam usysparam = usysparamService.queryUsysparamByCode("REGISTRATION_RETURN", "0");
								//原用户订单ID
								String oldDriverOrderID = UUIDGenerator.getUUID();
								String driverOrderID = UUIDGenerator.getUUID();//新用户订单ID
								SysOrder oldDriverOrder = new SysOrder();//原用户订单对象
								oldDriverOrder.setOrderId(oldDriverOrderID);//订单ID
								oldDriverOrder.setDebitAccount(oldDriver.getSysDriverId());//增加的账户ID
								oldDriverOrder.setOperator(wechatOperatorId);//操作人ID
								oldDriverOrder.setOperatorSourceId(wechatOperatorId);//被操作人ID
								oldDriverOrder.setCash(new BigDecimal(usysparam.getData()));//交易金额
								oldDriverOrder.setChargeType("0");//充值方式
								oldDriverOrder.setIs_discharge("0");//是否红冲
								oldDriverOrder.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);//操作者发起人类型
								oldDriverOrder.setOrderType(GlobalConstant.OrderType.CHARGE_TO_DRIVER);//订单类型
								oldDriverOrder.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);//操作对象类型
								oldDriverOrder.setOrderNumber(orderService.createOrderNumber(GlobalConstant.OrderType.CHARGE_TO_DRIVER));//订单号
								oldDriverOrder.setOrderStatus(0);//订单初始化
								orderService.chargeToDriver(oldDriverOrder);
								//充值增加积分
								addIntegralHistory(oldDriverOrder);
								//系统关键日志记录
			        			SysOperationLog oldSysOperationLog = new SysOperationLog();
			        			oldSysOperationLog.setOperation_type("cz");
			        			oldSysOperationLog.setLog_platform("2");
			        			oldSysOperationLog.setOrder_number(oldDriverOrder.getOrderNumber());
			        			oldSysOperationLog.setLog_content("司机注册返现原用户订单充值成功！充值金额为："+new BigDecimal(usysparam.getData())+"，订单号为："+oldDriverOrder.getOrderNumber());
			        			//操作日志
			        			sysOperationLogService.saveOperationLog(oldSysOperationLog,wechatOperatorId);

								//新用户订单对象
								SysOrder driverOrder = new SysOrder();
								driverOrder.setOrderId(driverOrderID);//订单ID
								driverOrder.setDebitAccount(sysDriverId);//增加的账户ID
								driverOrder.setOperator(wechatOperatorId);//操作人ID
								driverOrder.setOperatorSourceId(wechatOperatorId);//被操作人ID
								driverOrder.setCash(new BigDecimal(usysparam.getData()));//交易金额
								driverOrder.setChargeType("0");//充值方式
								driverOrder.setIs_discharge("0");//是否红冲
								driverOrder.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);//操作者发起人类型
								driverOrder.setOrderType(GlobalConstant.OrderType.CHARGE_TO_DRIVER);//订单类型
								driverOrder.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);//操作对象类型
								driverOrder.setOrderNumber(orderService.createOrderNumber(GlobalConstant.OrderType.CHARGE_TO_DRIVER));//订单号
								driverOrder.setOrderStatus(0);//订单初始化
								orderService.chargeToDriver(driverOrder);
								//充值增加积分
								addIntegralHistory(driverOrder);
								//系统关键日志记录
			        			SysOperationLog newSysOperationLog = new SysOperationLog();
			        			newSysOperationLog.setOperation_type("cz");
			        			newSysOperationLog.setLog_platform("2");
			        			newSysOperationLog.setOrder_number(driverOrder.getOrderNumber());
			        			newSysOperationLog.setLog_content("司机注册返现新用户订单充值成功！充值金额为："+new BigDecimal(usysparam.getData())+"，订单号为："+driverOrder.getOrderNumber());
			        			//操作日志
			        			sysOperationLogService.saveOperationLog(newSysOperationLog,wechatOperatorId);
							}
						}
					}
					Map<String, Object> tokenMap = new HashMap<>();
					tokenMap.put("token",sysDriverId);
					tokenMap.put("invitationCode",newInvitationCode);
					result.setData(tokenMap);
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("注册信息： " + resultStr);
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
			 * 必填参数
			 */
			String phoneNum = "phoneNum";
			boolean b = JsonTool.checkJson(mainObj,phoneNum);
			/**
			 * 请求接口
			 */
			if(b){
				SysDriver driver = new SysDriver();
				String sysDriverId = mainObj.optString("phoneNum");
				if(sysDriverId != null && !sysDriverId.equals("")){
					Map<String, Object> resultMap = new HashMap<>();
					driver.setUserName(sysDriverId);
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
						/**司机头像
						if(driver.getAvatarB() == null){
							resultMap.put("photoUrl","");
						}else{
							resultMap.put("photoUrl",localPath+driver.getAvatarB());
						}*/

						String invitationCode = driver.getInvitationCode();//获取邀请码
						if(invitationCode == null || "".equals(invitationCode)){
							invitationCode = ShareCodeUtil.toSerialCode(driver.getDriver_number());
							//更新当前司机邀请码
							SysDriver driverCode = new SysDriver();
							driverCode.setSysDriverId(driver.getSysDriverId());
							driverCode.setInvitationCode(invitationCode);
							driverService.saveDriver(driverCode,"update", null, null);
						}
						resultMap.put("invitationCode",invitationCode);
						/**关联公司
						if(driver.getTransportionName() != null && !"".equals(driver.getTransportionName().toString()) ){
							resultMap.put("company",driver.getTransportionName());
						}else if(driver.getGasStationName() != null && !"".equals(driver.getGasStationName().toString())){
							resultMap.put("company",driver.getGasStationName());
						}else{
							resultMap.put("company","");
						}*/
						resultMap.put("cardId",(driver.getCardId() == null || "".equals(driver.getCardId())) ? "":driver.getCardId());
						resultMap.put("isPayCode",(driver.getPayCode() == null || "".equals(driver.getPayCode())) ? "false":"true");
						
						result.setData(resultMap);
					}else{
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("该用户尚未注册");
						result.setData(null);
					}
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("查询失败！");
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
	 * 用户充值
	 * @param params
	 * @return
     */
	@RequestMapping(value = {"/deal/returnCash"})
	@ResponseBody
	public String returnCash(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("用户充值成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		Map<String, Object> dataMap = new HashMap<>();
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);//参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String amount = "amount";
			String phoneNum = "phoneNum";
			boolean b = JsonTool.checkJson(mainObj,amount,phoneNum);
			/**
			 * 请求接口
			 */
			if(b){
				String mobile = mainObj.optString("phoneNum");
				if(mobile != null && !"".equals(mobile)){
					//查询当前token是否注册
					SysDriver driver = new SysDriver();
					driver.setUserName(mobile);
					List<SysDriver>  sysDriver = driverService.queryeSingleList(driver);
					if(sysDriver !=null && sysDriver.size() > 0){
						//新用户订单对象
						SysOrder driverOrder = new SysOrder();
						String driverOrderID = UUIDGenerator.getUUID();//新用户订单ID
						driverOrder.setOrderId(driverOrderID);//订单ID
						driverOrder.setDebitAccount(sysDriver.get(0).getSysDriverId());//增加的账户ID
						driverOrder.setOperator(wechatOperatorId);//操作人ID
						driverOrder.setOperatorSourceId(wechatOperatorId);//被操作人ID
						driverOrder.setCash(new BigDecimal(mainObj.optString("amount")));//交易金额
						driverOrder.setChargeType("103");//充值方式 103代表微信支付
						driverOrder.setIs_discharge("0");//是否红冲
						driverOrder.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.WECHAT);//操作者发起人类型
						driverOrder.setOrderType(GlobalConstant.OrderType.CHARGE_TO_DRIVER);//订单类型
						driverOrder.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);//操作对象类型
						driverOrder.setOrderNumber(orderService.createOrderNumber(GlobalConstant.OrderType.CHARGE_TO_DRIVER));//订单号
						driverOrder.setOrderStatus(0);//订单初始化
						driverOrder.setChannel("微信VIP充值");
						driverOrder.setChannelNumber("微信VIP充值");
						driverOrder.setOrderDate(new Date());
						int nCreateOrder = orderService.insert(driverOrder, null);
						if(nCreateOrder>0){
							orderService.chargeToDriver(driverOrder);
							//充值增加积分
							addIntegralHistory(driverOrder);
							//系统关键日志记录
		        			SysOperationLog newSysOperationLog = new SysOperationLog();
		        			newSysOperationLog.setOperation_type("cz");
		        			newSysOperationLog.setLog_platform("2");
		        			newSysOperationLog.setOrder_number(driverOrder.getOrderNumber());
		        			newSysOperationLog.setLog_content("司机订单微信充值成功！充值金额为："+new BigDecimal(mainObj.optString("amount"))+"，订单号为："+driverOrder.getOrderNumber());
		        			//操作日志
		        			sysOperationLogService.saveOperationLog(newSysOperationLog,wechatOperatorId);
							dataMap.put("resultVal","true");
							result.setData(dataMap);
						}else{
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("订单生成错误！");
							result.setData(null);
						}
						
					}else{
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("该用户尚未注册");
						result.setData(null);
					}
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("充值失败！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("返回信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("充值失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("充值失败： " + e);
			resutObj.remove("listMap");
			dataMap.put("resultVal","false");
			result.setData(dataMap);
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}
	
	private SysOrder createNewOrder(String orderID, String driverID, String cash, String chargeType) throws Exception{
		SysOrder record = new SysOrder();

		record.setOrderId(orderID);
		record.setDebitAccount(driverID);
		record.setOperator(wechatOperatorId);
		record.setOperatorSourceId(wechatOperatorId);

		record.setCash(new BigDecimal(cash));
		record.setChargeType(chargeType);

		record.setIs_discharge("0");
		record.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
		record.setOrderType(GlobalConstant.OrderType.CHARGE_TO_DRIVER);
		record.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
		record.setOrderNumber(orderService.createOrderNumber(GlobalConstant.OrderType.CHARGE_TO_DRIVER));
		record.setOrderStatus(0);
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
		record.setChannel("微信VIP充值");
		record.setChannelNumber("微信VIP充值");   //建立一个虚拟的APP气站，方便后期统计
		return record;
	}
	
	/**
	 * 充值增加积分
	 * @param order
	 * @throws Exception
	 */
	public void addIntegralHistory(SysOrder order) throws Exception{
		if(null!=order.getOperator()&&!"".equals(order.getOperator())){
			//充值成功发放积分
			HashMap<String, String> integralMap =  integralRuleService.selectRepeatIntegralType("cz");
			String integral_rule_id = integralMap.get("integral_rule_id");
			IntegralRule integralRule = integralRuleService.queryIntegralRuleByPK(integral_rule_id);
			//存在积分规则
			if(null!=integralRule){
					HashMap<String,String> czHashMap = new HashMap<String,String>();
					czHashMap.put("reward_cycle", integralRule.getReward_cycle());
					czHashMap.put("debit_Account", order.getDebitAccount());
					czHashMap.put("order_id",order.getOrderId());
					List<HashMap<String,String>> orderList = orderService.queryOrderByOperator(czHashMap);
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
										IntegralHistory czHistory = new IntegralHistory();
										czHistory.setIntegral_type("cz");
										czHistory.setIntegral_rule_id(integralMap.get("integral_rule_id"));
										czHistory.setSys_driver_id(order.getDebitAccount());
										String[] ladder_before = integralMap.get("ladder_before").split(",");
										String[] ladder_after = integralMap.get("ladder_after").split(",");
										String[] integral_reward = integralMap.get("integral_reward").split(",");
										String[] reward_factor = integralMap.get("reward_factor").split(",");
										String[] reward_max = integralMap.get("reward_max").split(",");
										String integralreward = "";
										for(int i=0;i<ladder_before.length;i++){
											//判断是否在阶梯的消费区间内
											if(order.getCash().compareTo(new BigDecimal(ladder_before[i]))>=0&&order.getCash().compareTo(new BigDecimal(ladder_after[i]))<0){
												if(null!=integral_reward[i]&&!"".equals(integral_reward[i])){
													czHistory.setIntegral_num(integral_reward[i]);
													integralreward = integral_reward[i];
												}else{
													czHistory.setIntegral_num((order.getCash().multiply(new BigDecimal(reward_factor[i]))).setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 
													integralreward = (order.getCash().multiply(new BigDecimal(reward_factor[i]))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
													if(order.getCash().multiply(new BigDecimal(reward_factor[i])).compareTo(new BigDecimal(reward_max[i]))>0){
														czHistory.setIntegral_num(reward_max[i]);
														integralreward = reward_max[i];
													}
												}
											}
										}
										integralHistoryService.addIntegralHistory(czHistory, order.getDebitAccount());
										SysDriver driver = new SysDriver();
										driver.setIntegral_num(integralreward);
										driver.setSysDriverId(order.getDebitAccount());
										driverService.updateDriverByIntegral(driver);				
								}	
						}					
					}	
				}				
		}		
	}
}
