package com.sysongy.api.mobile.controller;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.avalon.framework.service.ServiceException;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.github.pagehelper.PageInfo;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.feedback.MbUserSuggest;
import com.sysongy.api.mobile.model.verification.MobileVerification;
import com.sysongy.api.mobile.service.MbDealOrderService;
import com.sysongy.api.mobile.service.MbUserSuggestServices;
import com.sysongy.api.mobile.tools.ali.OrderInfoUtil2_0;
import com.sysongy.api.mobile.tools.verification.MobileVerificationUtils;
import com.sysongy.api.mobile.tools.wechat.MD5;
import com.sysongy.api.mobile.tools.wechat.Util;
import com.sysongy.api.util.DESUtil;
import com.sysongy.api.util.DistCnvter;
import com.sysongy.api.util.ShareCodeUtil;
import com.sysongy.poms.base.model.DistCity;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.base.service.DistrictService;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.CouponGroup;
import com.sysongy.poms.coupon.model.UserCoupon;
import com.sysongy.poms.coupon.service.CouponGroupService;
import com.sysongy.poms.coupon.service.CouponService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.message.model.SysMessage;
import com.sysongy.poms.message.service.SysMessageService;
import com.sysongy.poms.mobile.controller.SysRoadController;
import com.sysongy.poms.mobile.model.MbAppVersion;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.model.MbStatistics;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.service.MbAppVersionService;
import com.sysongy.poms.mobile.service.MbBannerService;
import com.sysongy.poms.mobile.service.MbStatisticsService;
import com.sysongy.poms.mobile.service.SysRoadService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;
import com.sysongy.poms.order.service.OrderDealService;
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
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.AliShortMessage.SHORT_MESSAGE_TYPE;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.HttpUtil;
import com.sysongy.util.JsonTool;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.RealNameException;
import com.sysongy.util.RedisClientInterface;
import com.sysongy.util.TwoDimensionCode;
import com.sysongy.util.UUIDGenerator;
import com.sysongy.util.pojo.AliShortMessageBean;
import com.tencent.mm.sdk.modelpay.PayReq;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RequestMapping("/api/v1/mobile")
@Controller
public class MobileController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
	public final String keyStr = "sysongys";
	String localPath = (String) prop.get("http_poms_path");
	public final String appOperatorId = "8aa4ba67855a11e6a356000c291aa9e3";

	/**
	 * 新浪短网址API source:应用的appkey(3271760578) url_long:需要转换的长链接
	 * 示例：xml:http://api.t.sina.com.cn/short_url/shorten.xml?source=3271760578&
	 * url_long=http://www.douban.com/note/249723561/
	 */
	String XML_API = "http://api.t.sina.com.cn/short_url/shorten.xml";
	String JSON_API = "http://api.t.sina.com.cn/short_url/shorten.json";

	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2016011801102578"; // TODO 需要自定义常量类
	public static final String ss = "123123";

	/** 商户私钥，pkcs8格式 */
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALMGuZtJV5TvfNZHvIYUVqo6Gy2lqvYZ0DP2Dr7Si5aPqd+sEhDy7TjWAUlYV86d3z+/oOrhap9iHJn80oJoQk3UTf/NM/XNF0PCjW1UiGORQyFiBYFIVfXSylFWQ5IFBmxQfgu5cPzZNeTcRsooPTrON0/OAItxsuGbgDlCMusjAgMBAAECgYA1HhiyB2fSC+C5X12DVsOEDGuF9rKsBGqvECG94pCCIqwfblmJ59oU1AJbtbeP2W2k54GiTzGoip673bTD9pU9LPdyelWlFBePGEHiREbno2fXB01Tb9ML/TrZG5JFZdv7IS7ekitWiiK1lKwFg3mujMDXrFAwBQd/kBd0eOG7cQJBAOikToenP7JgCYuWM7N5pKZ5GsfZJsKVqDRZyqDDih0gXTdafW49IlwMHVpoWV01PwiG5feQDjASXv+MDNUgwbsCQQDFAFCFwWFjjRiKShgRjZurYOvSc04XqACf1lROefLrKJ+BsrFQJAhWahHEmJiV2pNuYnSybx2e2AwFbyt8pJG5AkEA3wduFdS8VxiE7iJATIaI1+PwTbmb1B4/lHikrnzn8sZtNzz0VPQc9ZvTpDG3wojidh1FaJHdWC60jk9ImiZ+MwJBAIw07Bo2Bo0ml2ec0kJz6W3wngX64IJ/pGodzYTI0DXDhLp3JjEmY/S0qw6jmD1XAgTW970izg8GLpATjfy416kCQQChQUYwe76hdGA/60lCycBCwiwYV+aHxezFnC2PcUxMif/cbKIwJvR6nhQK1QTKL60ExlRSKXA9xbB2eH/QXRNj";

	public static final String APP_ID = "wxbc6365b82bab3598";// Sysongy
	private static final String MCH_ID = "1280581101";// Sysongy
	private static final String API_KEY = "Gy325U2312T360o2312t2p23b212tR4a";// Sysongy
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
	@Autowired
	SysRoadService sysRoadService;
	@Autowired
	SysMessageService sysMessageService;
	@Autowired
	MbStatisticsService mbStatisticsService;
	@Autowired
	MbAppVersionService mbAppVersionService;
	@Autowired
	CouponService couponService;
	@Autowired
    CouponGroupService couponGroupService;
	@Autowired
	SysOperationLogService sysOperationLogService;
	@Autowired
	OrderDealService orderDealService;
	/**
	 * 用户登录
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = { "/user/login" })
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
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String username = "username";
			String type = "type";
			boolean b = JsonTool.checkJson(mainObj, username, type);
			/**
			 * 请求接口
			 */
			if (b) {
				type = mainObj.optString("type");
				SysDriver driver = new SysDriver();
				SysDriver queryDriver = null;
				// 賬號密碼登錄
				if ("1".equals(type)) {
					driver.setUserName(mainObj.optString("username"));
					driver.setPassword(mainObj.optString("password"));
					queryDriver = driverService.queryByUserNameAndPassword(driver);
					if (queryDriver != null) {
						Map<String, Object> tokenMap = new HashMap<>();
						tokenMap.put("token", queryDriver.getSysDriverId());
						result.setData(tokenMap);
					} else {
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("用户名或密码错误！");
					}
				} else {// 用戶名驗證碼登錄
					driver.setUserName(mainObj.optString("username"));
					driver.setMobilePhone(mainObj.optString("username"));
					String verificationCode = mainObj.optString("verificationCode");
					String veCode = (String) redisClientImpl.getFromCache(driver.getMobilePhone());
					if (verificationCode.equals(veCode)) {
						queryDriver = driverService.queryByUserName(driver);
						if (queryDriver == null) {
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("用户名或密码错误！");
						} else {
							Map<String, Object> tokenMap = new HashMap<>();
							tokenMap.put("token", queryDriver.getSysDriverId());
							result.setData(tokenMap);
						}

					} else {
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("验证码无效！");
					}
				}
				// 判断二维码是否为空
				if (queryDriver != null
						&& (queryDriver.getDriverQrcode() == null || "".equals(queryDriver.getDriverQrcode()))) {
					// 图片路径
					String rootPath = (String) prop.get("images_upload_path") + "/driver/";
					File file = new File(rootPath);
					// 如果根文件夹不存在则创建
					if (!file.exists() && !file.isDirectory()) {
						file.mkdir();
					}
					String path = rootPath + mainObj.optString("username") + "/";
					File file1 = new File(path);
					// 如果用户文件夹不存在则创建
					if (!file1.exists() && !file1.isDirectory()) {
						file1.mkdir();
					}
					// 二维码路径
					String imgPath = path + mainObj.optString("username") + ".jpg";
					String show_path = (String) prop.get("show_images_path") + "/driver/"
							+ mainObj.optString("username") + "/" + mainObj.optString("username") + ".jpg";
					// 生成二维码
					driver.setDriverQrcode(show_path);
					driver.setSysDriverId(queryDriver.getSysDriverId());
					Integer tmp = driverService.saveDriver(driver, "update", null, this.appOperatorId);
					String encoderContent = null;
					if (queryDriver.getFullName() == null || "".equals(queryDriver.getFullName())) {
						encoderContent = mainObj.optString("username");
					} else {
						encoderContent = mainObj.optString("username") + "_" + queryDriver.getFullName();
					}
					if (tmp > 0) {
						TwoDimensionCode handler = new TwoDimensionCode();
						handler.encoderQRCode(encoderContent, imgPath, TwoDimensionCode.imgType, null,
								TwoDimensionCode.size);
					}
				} else {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("用户名或密码错误！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("登录信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("登录失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("登录信息： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取短信验证码
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = { "/user/getVerificationCode" })
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
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String phoneNum = "phoneNum";
			String templateType = "templateType";
			boolean b = JsonTool.checkJson(mainObj, phoneNum, templateType);
			/**
			 * 请求接口
			 */
			if (b) {
				String msgType = mainObj.optString("templateType");
				// 发送短信
				Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);
				verification.setPhoneNum(mainObj.optString("phoneNum"));
				verification.setReqType(MobileVerificationUtils.APP_DRIVER_REG);

				AliShortMessage.SHORT_MESSAGE_TYPE msgTypeaTemp = AliShortMessage.SHORT_MESSAGE_TYPE.USER_REGISTER;
				switch (msgType) {
				case "1":
					msgTypeaTemp = AliShortMessage.SHORT_MESSAGE_TYPE.USER_REGISTER;
					verification.setContent("司集");
					break;
				case "2":
					msgTypeaTemp = AliShortMessage.SHORT_MESSAGE_TYPE.USER_CHANGE_PROFILE;
					verification.setContent("登录手机号码");
					break;
				case "3":
					msgTypeaTemp = AliShortMessage.SHORT_MESSAGE_TYPE.USER_CHANGE_PROFILE;
					verification.setContent("密保手机号码");
					break;
				case "4":
					msgTypeaTemp = AliShortMessage.SHORT_MESSAGE_TYPE.USER_LOGIN_CONFIRM;
					verification.setContent("APP");
					break;
				}
				Map<String, Object> tokenMap = new HashMap<>();
				if(msgTypeaTemp.equals(AliShortMessage.SHORT_MESSAGE_TYPE.USER_REGISTER)){
					SysDriver driver = new SysDriver();
					driver.setMobilePhone(mainObj.optString("phoneNum"));
					SysDriver queryDriver = driverService.queryDriverByMobilePhone(driver);
					if(queryDriver==null){
						MobileVerificationUtils.sendMSGType(verification, checkCode.toString(), msgTypeaTemp);
						// 设置短信有效期10分钟
						redisClientImpl.addToCache(verification.getPhoneNum(), checkCode.toString(), 600);
						tokenMap.put("verificationCode", checkCode.toString());
					}else{
						result.setMsg("该手机号已注册！");
					}
				}else{
					MobileVerificationUtils.sendMSGType(verification, checkCode.toString(), msgTypeaTemp);
					// 设置短信有效期10分钟
					redisClientImpl.addToCache(verification.getPhoneNum(), checkCode.toString(), 600);
					tokenMap.put("verificationCode", checkCode.toString());
				}
				result.setData(tokenMap);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("验证码信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("发送失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("发送失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = { "/user/register" })
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
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String phoneNum = "phoneNum";
			String verificationCode = "verificationCode";
			String password = "password";
			boolean b = JsonTool.checkJson(mainObj, phoneNum, verificationCode, password);
			/**
			 * 请求接口
			 */
			if (b) {
				SysDriver driver = new SysDriver();
				driver.setUserName(mainObj.optString("phoneNum"));
				driver.setMobilePhone(mainObj.optString("phoneNum"));
				String invitationCode = mainObj.optString("invitationCode");
				String veCode = (String) redisClientImpl.getFromCache(driver.getMobilePhone());
				if (veCode != null && !"".equals(veCode)) {
					List<SysDriver> driverlist = driverService.queryeSingleList(driver);
					if (driverlist != null && driverlist.size() > 0) {
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("该手机号已注册！");
						// throw new
						// Exception(MobileRegisterUtils.RET_DRIVER_MOBILE_REGISTED);
					} else {
						String sysDriverId = UUIDGenerator.getUUID();
						driver.setPassword(mainObj.optString("password"));
						driver.setSysDriverId(sysDriverId);
						driver.setRegisSource("APP");
						String encoderContent = mainObj.optString("phoneNum");
						// 图片路径
						String rootPath = (String) prop.get("images_upload_path") + "/driver/";
						File file = new File(rootPath);
						// 如果根文件夹不存在则创建
						if (!file.exists() && !file.isDirectory()) {
							file.mkdir();
						}
						String path = rootPath + mainObj.optString("phoneNum") + "/";
						File file1 = new File(path);
						// 如果用户文件夹不存在则创建
						if (!file1.exists() && !file1.isDirectory()) {
							file1.mkdir();
						}
						// 二维码路径
						String imgPath = path + mainObj.optString("phoneNum") + ".jpg";
						String show_path = (String) prop.get("show_images_path") + "/driver/"
								+ mainObj.optString("phoneNum") + "/" + mainObj.optString("phoneNum") + ".jpg";
						// 生成二维码
						driver.setDriverQrcode(show_path);
						//设置默认驾驶证和行驶证图片
                        driver.setDrivingLice("/image/default_productBig.jpg");
                        driver.setVehicleLice("/image/default_productBig.jpg");
						Integer tmp = driverService.saveDriver(driver, "insert", invitationCode, this.appOperatorId);
						//系统关键日志记录
						SysOperationLog sysOperationLog = new SysOperationLog();
						sysOperationLog.setOperation_type("kh");
						String name = driver.getFullName();
						if("".equals(name)||null==name){
							name = driver.getUserName();
						}
						if("".equals(name)||null==name){
							name = driver.getMobilePhone();
						}
						sysOperationLog.setLog_platform("1");
						sysOperationLog.setLog_content(name+"的账户卡通过APP开户成功！"); 
						//操作日志
						sysOperationLogService.saveOperationLog(sysOperationLog,driver.getSysDriverId());	
						if (tmp > 0) {
							TwoDimensionCode handler = new TwoDimensionCode();
							handler.encoderQRCode(encoderContent, imgPath, TwoDimensionCode.imgType, null,
									TwoDimensionCode.size);
						}
						Map<String, Object> tokenMap = new HashMap<>();
						tokenMap.put("token", sysDriverId);
						result.setData(tokenMap);
					}
				} else {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("验证码无效！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}

			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("注册信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("注册失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("注册失败： " + e);
			e.printStackTrace();
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} finally {
			return resultStr;
		}

	}

	/**
	 * 获取用户信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = { "/user/getUserInfo" })
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
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			String http_poms_path = (String) prop.get("http_poms_path");
			/**
			 * 必填参数
			 */
			String token = "token";
			boolean b = JsonTool.checkJson(mainObj, token);
			/**
			 * 请求接口
			 */
			if (b) {
				SysDriver driver = new SysDriver();
				String sysDriverId = mainObj.optString("token");
				if (sysDriverId != null && !sysDriverId.equals("")) {
					Map<String, Object> resultMap = new HashMap<>();
					driver.setSysDriverId(sysDriverId);
					List<SysDriver> driverlist = driverService.queryForPageList(driver);
					if (driverlist != null && driverlist.size() > 0) {
						List<Map<String, Object>> list = orderService.calcDriverCashBack(sysDriverId);
						String cashBack = "0.00";
						if (list != null && list.size() > 0 && list.get(0) != null
								&& list.get(0).get("cash_back") != null) {
							cashBack = list.get(0).get("cash_back").toString();
						}

						// 获取用户审核状态
						driver = driverlist.get(0);
						SysUserAccount sysUserAccount = sysUserAccountService
								.queryUserAccountByDriverId(driver.getSysDriverId());

						/*String driverCheckedStstus = driver.getCheckedStatus();
						if ("2".equals(driverCheckedStstus)) {
							resultMap.put("nick", driver.getNickname());
						} else {
							resultMap.put("nick", "");
						}*/
						String nick = driver.getNickname();
						if (nick!=null && !"".equals(nick)) {
							resultMap.put("nick", driver.getNickname());
						} else {
							resultMap.put("nick", "");
						}
						resultMap.put("account", driver.getUserName());
						resultMap.put("securityPhone", driver.getSecurityMobilePhone());
						resultMap.put("isRealNameAuth", driver.getCheckedStatus());
						resultMap.put("balance", driver.getAccount().getAccountBalance());
						resultMap.put("QRCodeUrl", http_poms_path + driverlist.get(0).getDriverQrcode());
						resultMap.put("cumulativeReturn", cashBack);
						resultMap.put("userStatus", sysUserAccount.getAccount_status());
						Gastation gastation = gastationService.queryGastationByPhone(driverService.queryDriverByPK(sysDriverId).getMobilePhone());
						//用户类型(1 司机 2 气站管理员)
						if(gastation==null){
							resultMap.put("userType","1");
						}else{
							resultMap.put("userType", "2");
						}
						if (driver.getAvatarB() == null || "".equals(driver.getAvatarB())) {
							resultMap.put("photoUrl", "");
						} else {
							resultMap.put("photoUrl", localPath + driver.getAvatarB());
						}

						String invitationCode = driver.getInvitationCode();// 获取邀请码
						if (invitationCode == null || "".equals(invitationCode)) {
							//被邀请用户第一次登录，进行邀请返现 ，送優惠券
							SysDriver queryDriver = driverService.queryDriverByPK(sysDriverId);
							driverService.cashBackForRegister(queryDriver,queryDriver.getRegisCompany(),this.appOperatorId);

							//系统关键日志记录
		        			SysOperationLog sysOperationLog = new SysOperationLog();
		        			sysOperationLog.setOperation_type("fx");
		        			sysOperationLog.setLog_platform("1");
		            		sysOperationLog.setLog_content("APP用户"+queryDriver.getMobilePhone()+"首次登陆返现成功！");
		        			//操作日志
		        			sysOperationLogService.saveOperationLog(sysOperationLog,sysDriverId);

							invitationCode = ShareCodeUtil.toSerialCode(driver.getDriver_number());
							// 更新当前司机邀请码
							SysDriver driverCode = new SysDriver();
							driverCode.setSysDriverId(driver.getSysDriverId());
							driverCode.setInvitationCode(invitationCode);
							driverService.saveDriver(driverCode, "update", null, null);
						}
						resultMap.put("invitationCode", invitationCode);
						if (driver.getTransportionName() != null
								&& !"".equals(driver.getTransportionName().toString())) {
							resultMap.put("company", driver.getTransportionName());
						} else if (driver.getGasStationName() != null
								&& !"".equals(driver.getGasStationName().toString())) {
							resultMap.put("company", driver.getGasStationName());
						} else {
							resultMap.put("company", "");
						}
						resultMap.put("cardId", (driver.getCardId() == null || "".equals(driver.getCardId())) ? ""
								: driver.getCardId());
						resultMap.put("isPayCode",
								(driver.getPayCode() == null || "".equals(driver.getPayCode())) ? "false" : "true");
						result.setData(resultMap);
					}
				} else {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("登录失败！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("查询用户信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 修改登录密码
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/user/updatePassword")
	@ResponseBody
	public String updatePassword(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("修改登录密码成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String password = "password";
			String verificationCode = "verificationCode";
			boolean b = JsonTool.checkJson(mainObj, token, password, verificationCode);
			/**
			 * 请求接口
			 */
			if (b) {
				SysDriver sysDriver = new SysDriver();
				sysDriver.setSysDriverId(mainObj.optString("token"));
				password = mainObj.optString("password");
				if (password != null && !"".equals(password)) {
					sysDriver.setPassword(password);
					sysDriver.setSysDriverId(mainObj.optString("token"));

					driverService.saveDriver(sysDriver, "update", null, null);
				} else {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("密码为空！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("修改登录密码信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("修改登录密码失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("修改登录密码失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 修改用户信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = { "/user/updateUser" })
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
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			boolean b = JsonTool.checkJson(mainObj, token);
			/**
			 * 请求接口
			 */
			if (b) {
				String name = mainObj.optString("name");
				String deviceToken = mainObj.optString("deviceToken");
				String imgUrl = mainObj.optString("imgUrl");
				SysDriver driver = new SysDriver();
				String sysDriverId = mainObj.optString("token");
				if (sysDriverId != null && !sysDriverId.equals("")) {
					
					if (name != null && !"".equals(name)) {
						driver.setNickname(name);
					}
					if (imgUrl != null && !"".equals(imgUrl)) {
						driver.setAvatarB(imgUrl);
					}
					driver.setSysDriverId(sysDriverId);
					driver.setDeviceToken(mainObj.optString("deviceToken"));
					if (deviceToken != null && !"".equals(deviceToken)) {
						SysDriver oldDriver = driverService.queryByDeviceToken(deviceToken);
						if (oldDriver != null) {
							oldDriver.setDeviceToken("");
							int resultoldVal = driverService.saveDriver(oldDriver, "update", null, null);
						}
					}
					int resultVal = driverService.saveDriver(driver, "update", null, null);
				} else {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("修改用户信息失败！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("修改用户信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("修改用户信息失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("修改用户信息失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 设置支付密码
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/user/setPaycode")
	@ResponseBody
	public String setPaycode(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("支付密码设置成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String paycode = "paycode";
			String verificationCode = "verificationCode";
			boolean b = JsonTool.checkJson(mainObj, token, paycode, verificationCode);
			/**
			 * 请求接口
			 */
			if (b) {
				SysDriver driver = new SysDriver();
				String sysDriverId = mainObj.optString("token");
				if (mainObj.optString("token") == null) {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("用户ID为空！");
				} else if (mainObj.optString("paycode") == null) {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("支付密码为空！");
				} else if (mainObj.optString("verificationCode") == null) {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("验证码为空！");
				} else {
					driver = driverService.queryDriverByPK(mainObj.optString("token"));
					String	veCode = (String) redisClientImpl.getFromCache(driver.getUserName());
					if (veCode != null && !"".equals(veCode)) {
						Map<String, Object> resultMap = new HashMap<>();
						driver.setSysDriverId(sysDriverId);
						paycode = mainObj.optString("paycode").toUpperCase();
						driver.setPayCode(paycode);
						driverService.saveDriver(driver, "update", null, null);// 设置支付密码
					}else{
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("验证码无效！");
					}
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("支付密码设置信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("支付密码设置失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("支付密码设置失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 修改支付密码
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/user/updatePayCode")
	@ResponseBody
	public String updatePayCode(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("修改支付密码成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String oldPayCode = "oldPayCode";
			String newPayCode = "newPayCode";
			boolean b = JsonTool.checkJson(mainObj, token, oldPayCode, newPayCode);
			/**
			 * 请求接口
			 */
			if (b) {
				SysDriver sysDriver = new SysDriver();
				sysDriver.setSysDriverId(mainObj.optString("token"));
				String driverId = mainObj.optString("token");
				oldPayCode = mainObj.optString("oldPayCode").toUpperCase();
				SysDriver driver = driverService.queryDriverByPK(driverId);
				String payCode = driver.getPayCode().toUpperCase();
				if (payCode.equals(oldPayCode)) {
					// 判断原支付密码是否正确
					newPayCode = mainObj.optString("newPayCode").toUpperCase();
					if (newPayCode != null && !"".equals(newPayCode)) {
						sysDriver.setPayCode(newPayCode);
						driverService.saveDriver(sysDriver, "update", null, null);
					}
				} else {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("原始密码错误！");
				}

			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("修改支付密码信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("修改支付密码失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("修改支付密码失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 图片上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/img/imageUpload" })
	@ResponseBody
	public String upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
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
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		}

		try {
			String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
			String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名

			/**
			 * 上传文件
			 */
			String realPath = "/mobile/";
			String path = (String) prop.get("images_upload_path");
			String show_path = (String) prop.get("show_images_path");
			String http_poms_path = (String) prop.get("http_poms_path");
			http_poms_path = http_poms_path + show_path;
			path += realPath + filename;// 上传路径
			show_path += realPath + filename;// 相对路径
			http_poms_path += realPath + filename;// 绝对路径（web访问路径）
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
			tokenMap.put("imageUrl", http_poms_path);
			tokenMap.put("relativeUrl", show_path);
			result.setData(tokenMap);

			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("图片上传信息： " + resultStr);
			/* resultStr = DESUtil.encode(keyStr,resultStr);//参数加密 */
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("图片上传失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("图片上传失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 申请实名认证
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/user/realNameAuth")
	@ResponseBody
	public String realNameAuth(String params, HttpServletRequest request) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("实名认证已提交审核，请耐心等待！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String name = "name";
			String driverLicenseImageUrl = "driverLicenseImageUrl";
			boolean b = JsonTool.checkJson(mainObj, token, name, driverLicenseImageUrl);
			/**
			 * 请求接口
			 */
			if (b) {
				SysDriver driver = new SysDriver();
				String fullName = mainObj.optString("name");
				driver.setSysDriverId(mainObj.optString("token"));
				// 获取用户电话
				List<SysDriver> driverList = driverService.queryeSingleList(driver);
				if (driverList != null && driverList.size() > 0) {
					driver.setFullName(fullName);
					driver.setPlateNumber(mainObj.optString("plateNumber"));
					driver.setFuelType(mainObj.optString("gasType"));
					if (mainObj.optString("endTime") != null && !"".equals(mainObj.optString("endTime"))) {
						SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
						Date date = sft.parse(mainObj.optString("endTime"));
						driver.setExpiryDate(date);
					}
					driver.setDrivingLice(mainObj.optString("drivingLicenseImageUrl"));
					driver.setVehicleLice(mainObj.optString("driverLicenseImageUrl"));

					driver.setCheckedStatus(GlobalConstant.DriverCheckedStatus.CERTIFICATING);
					driver.setIdentityCard(mainObj.optString("idCard"));
					String encoderContent = driverList.get(0).getMobilePhone() + "_" + fullName;
					// 图片路径
					String rootPath = (String) prop.get("images_upload_path") + "/driver/";
					File file = new File(rootPath);
					// 如果根文件夹不存在则创建
					if (!file.exists() && !file.isDirectory()) {
						file.mkdir();
					}
					String path = rootPath + driverList.get(0).getMobilePhone() + "/";
					File file1 = new File(path);
					// 如果用户文件夹不存在则创建
					if (!file1.exists() && !file1.isDirectory()) {
						file1.mkdir();
					}
					// 二维码路径
					String imgPath = path + driverList.get(0).getMobilePhone() + ".jpg";
					String show_path = (String) prop.get("show_images_path") + "/driver/"
							+ driverList.get(0).getMobilePhone() + "/" + driverList.get(0).getMobilePhone() + ".jpg";
					// 生成二维码
					driver.setDriverQrcode(show_path);
					driver.setVerifySource("1");
					int resultVal = driverService.saveDriver(driver, "update", null, null);
					if (resultVal <= 0) {
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("用户ID为空，申请失败！");
					} else {
						TwoDimensionCode handler = new TwoDimensionCode();
						handler.encoderQRCode(encoderContent, imgPath, TwoDimensionCode.imgType, null,
								TwoDimensionCode.size);
					}
				} else {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("当前用户不存在！");
					resutObj = JSONObject.fromObject(result);
					resutObj.remove("listMap");
					resultStr = resutObj.toString();
					resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
					return resultStr;
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
				resutObj = JSONObject.fromObject(result);
				resutObj.remove("listMap");
				resultStr = resutObj.toString();
				resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
				return resultStr;
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("实名认证已提交审核，请耐心等待！： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("申请失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("申请失败： " + e);
			e.printStackTrace();
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取参数列表
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/util/paramList")
	@ResponseBody
	public String getParamList(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String code = "code";
			boolean b = JsonTool.checkJson(mainObj, code);
			/**
			 * 请求接口
			 */
			if (b) {
				String gcode = mainObj.optString("code");
				if (gcode != null && !"".equals(gcode)) {
					List<Map<String, Object>> usysparamList = usysparamService.queryUsysparamMapByGcode(gcode);
					result.setListMap(usysparamList);
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("查询信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 意见反馈
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/user/feedback")
	@ResponseBody
	public String feedback(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("感谢您的宝贵建议！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "！";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String mobilePhone = "mobilePhone";
			String content = "content";
			boolean b = JsonTool.checkJson(mainObj, token, mobilePhone, content);
			/**
			 * 请求接口
			 */
			if (b) {
				MbUserSuggest suggest = new MbUserSuggest();
				suggest.setMbUserSuggestId(UUIDGenerator.getUUID());
				suggest.setSysDriverId(mainObj.optString("token"));
				suggest.setSuggest(mainObj.optString("content"));
				suggest.setMobilePhone(mainObj.optString("mobilePhone"));
				suggest.setCreatedDate(new Date());
				suggest.setUpdatedDate(new Date());
				suggest.setSuggestRes("来自APP");
				int resultVal = mbUserSuggestServices.saveSuggester(suggest);
				if (resultVal <= 0) {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("提交失败！");
				} else {
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("提交建议信息！： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("提交失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("提交失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 卡/账户挂失/解冻
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/loss/reportTheLoss")
	@ResponseBody
	public String reportTheLoss(String params) {
		MobileReturn result = new MobileReturn();
		String failStr = "操作成功！";
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String lossType = "lossType";
			boolean b = JsonTool.checkJson(mainObj, token, lossType);
			/**
			 * 请求接口
			 */
			if (b) {
				SysDriver driver = driverService.queryDriverByPK(mainObj.optString("token"));
				lossType = mainObj.optString("lossType");
				/* String cardId = mainObj.optString("cardId"); */
				int retvale = 0;// 操作影响行数
				if (lossType != null) {// 类型等于0 或者等于1
					String cardNo = "";
					if (driver.getCardInfo() != null) {
						cardNo = driver.getCardInfo().getCard_no();
					}
					retvale = sysUserAccountService.changeStatus(driver.getAccount().getSysUserAccountId(), lossType,
							cardNo);
				}
				//系统关键日志记录
				SysOperationLog sysOperationLog = new SysOperationLog();
				String operation="";
				if (retvale > 0) {
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					if ("2".equals(lossType)) {
						failStr = "解除挂失";
						 operation="解除挂失";
						sysOperationLog.setOperation_type("jcgs");
					} else {
						failStr = ("挂失");
						operation="挂失";
						sysOperationLog.setOperation_type("gs");
					}
					result.setMsg(failStr + "成功！");
				}
				sysOperationLog.setUser_name(driver.getFullName());
				sysOperationLog.setLog_platform("1");
				String cardNo="";
				if (driver.getCardInfo() != null) {
					cardNo = driver.getCardInfo().getCard_no();
				}
				sysOperationLog.setLog_content("APP用户"+driver.getMobilePhone()+"把"+cardNo+"账户卡"+operation+"成功！");
				//操作日志
				sysOperationLogService.saveOperationLog(sysOperationLog,driver.getSysDriverId());
				
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error(failStr + "信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("操作失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("操作失败！" + e);
			e.printStackTrace();
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取开通城市列表
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/util/getCityList")
	@ResponseBody
	public String getCityList(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");

			/**
			 * 请求接口
			 */
			if (mainObj != null) {
				List<DistCity> cityList = districtService.queryHotCityList();
				List<Map<String, Object>> listMap = new ArrayList<>();
				if (cityList != null && cityList.size() > 0) {
					for (DistCity city : cityList) {
						Map<String, Object> cityMap = new HashMap<>();
						cityMap.put("cityName", city.getCityName());
						listMap.add(cityMap);
					}
					result.setListMap(listMap);
				}

			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("查询信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询城市失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询城市失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取地图信息列表
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/map/getStationList")
	@ResponseBody
	public String queryStationList(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询加注站信息成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		Gastation gastation = new Gastation();

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			String http_poms_path = (String) prop.get("http_poms_path");
			/**
			 * 必填参数
			 */
			String longitudeIn = "longitude";
			String latitudeIn = "latitude";
			String infoType = "infoType";
			boolean b = JsonTool.checkJson(mainObj, longitudeIn, latitudeIn, infoType);

			/**
			 * 请求接口
			 */
			if (b) {
				String radius = mainObj.optString("radius");
				String longitudeStr = mainObj.optString("longitude");
				String latitudeStr = mainObj.optString("latitude");
				String name = mainObj.optString("name");
				String type = mainObj.optString("type");
				if(type!=null && !"".equals(type) && "0".equals(type)){
					gastation.setType(type);
				}
				//范围为空，列表显示加分页
				if(radius == null || "".equals(radius)){
					if (gastation.getPageNum() == null) {
						gastation.setPageNum(GlobalConstant.PAGE_NUM);
						gastation.setPageSize(GlobalConstant.PAGE_SIZE);
					} else {
						gastation.setPageNum(mainObj.optInt("pageNum"));
						gastation.setPageSize(mainObj.optInt("pageSize"));
					}
					gastation.setGas_station_name(name);
					Double longitude = new Double(0);
					Double latitude = new Double(0);
					// 获取气站列表
					List<Gastation> gastationAllList = gastationService.getAllStationList(gastation);
					if (gastationAllList != null && gastationAllList.size() > 0) {
						for (int i = 0; i < gastationAllList.size(); i++) {
							if (longitudeStr != null && !"".equals(longitudeStr) && latitudeStr != null && !"".equals(latitudeStr)) {
								longitude = new Double(longitudeStr);
								latitude = new Double(latitudeStr);
								String longStr = gastationAllList.get(i).getLongitude();
								String langStr = gastationAllList.get(i).getLatitude();
								Double longDb = new Double(0);
								Double langDb = new Double(0);
								if (longStr != null && !"".equals(longStr) && langStr != null && !"".equals(langStr)) {
									longDb = new Double(longStr);
									langDb = new Double(langStr);
								}
								// 计算当前加注站离指定坐标距离
								Double dist = DistCnvter.getDistance(longitude, latitude, longDb, langDb);
								gastationAllList.get(i).setDistance(dist);
							}
						}
						//按距离重新排序gastationAllList
						Collections.sort(gastationAllList);
						int pageNum = mainObj.optInt("pageNum");
						int pageSize = mainObj.optInt("pageSize");
						int allPage = gastationAllList.size()/pageSize==0?gastationAllList.size()/pageSize+1:(gastationAllList.size()/pageSize)+1;
						if(allPage >= pageNum ){
							if (pageNum == 0) {
								pageNum = 1;
							}
							int end = pageNum * pageSize;
							if (end > gastationAllList.size()) {
								end  = gastationAllList.size();
							}
							int begin = (pageNum - 1) * pageSize;
							PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(gastationAllList.subList(begin, end ));
							List<Gastation> gastationList1 = pageInfo.getList();
							List<Map<String, Object>> gastationArray = new ArrayList<>();
							if (gastationList1 != null && gastationList1.size() > 0) {
								for (Gastation gastationInfo : gastationList1) {
									Map<String, Object> gastationMap = new HashMap<>();
									gastationMap.put("stationId", gastationInfo.getSys_gas_station_id());
									gastationMap.put("name", gastationInfo.getGas_station_name());
									gastationMap.put("type", gastationInfo.getType());
									gastationMap.put("longitude", gastationInfo.getLongitude());
									gastationMap.put("latitude", gastationInfo.getLatitude());
									Usysparam usysparam = usysparamService.queryUsysparamByCode("STATION_DATA_TYPE",
											gastationInfo.getType());
									gastationMap.put("stationType", usysparam.getMname());
									gastationMap.put("service",
											gastationInfo.getGas_server() == null ? "暂无" : gastationInfo.getGas_server());// 提供服务
									gastationMap.put("preferential",
											gastationInfo.getPromotions() == null ? "暂无" : gastationInfo.getPromotions());// 优惠活动
									// 获取当前气站价格列表
									String price = gastationInfo.getLng_price();
									if (price != null && !"".equals(price)) {
										price = price.replaceAll("，", ",");
										price = price.replaceAll("：", ":");
										if (price.indexOf(":") != -1 && price.indexOf("/") != -1) {
											String strArray[] = price.split(",");
											Map[] map = new Map[strArray.length];
											for (int i = 0; i < strArray.length; i++) {
												String strInfo = strArray[i].trim();
												String strArray1[] = strInfo.split(":");
												String strArray2[] = strArray1[1].split("/");
												Map<String, Object> dataMap = new HashMap<>();
												dataMap.put("gasName", strArray1[0]);
												dataMap.put("gasPrice", strArray2[0]);
												dataMap.put("gasUnit", strArray2[1]);
												map[i] = dataMap;
											}
											gastationMap.put("priceList", map);
										} else {
											gastationMap.put("priceList", new ArrayList());
										}
									} else {
										gastationMap.put("priceList", new ArrayList());
									}
									gastationMap.put("phone", gastationInfo.getContact_phone());
									if (gastationInfo.getStatus().equals("0")) {
										gastationMap.put("state", "开启");
									} else {
										gastationMap.put("state", "关闭");
									}
									gastationMap.put("address", gastationInfo.getAddress());
									String infoUrl = http_poms_path + "/portal/crm/help/station?stationId="
											+ gastationInfo.getSys_gas_station_id();
									gastationMap.put("infoUrl", infoUrl);
									gastationMap.put("shareUrl", http_poms_path + "/portal/crm/help/share/station?stationId="
											+ gastationInfo.getSys_gas_station_id());
									gastationArray.add(gastationMap);
								}
								result.setListMap(gastationArray);

							} else {
								result.setStatus(MobileReturn.STATUS_SUCCESS);
								result.setMsg("暂无数据！");
								result.setListMap(new ArrayList<Map<String, Object>>());
							}
						}else {
							result.setStatus(MobileReturn.STATUS_SUCCESS);
							result.setMsg("没有更多数据！");
							result.setListMap(new ArrayList<Map<String, Object>>());
						}
						
					} else {
						result.setStatus(MobileReturn.STATUS_SUCCESS);
						result.setMsg("暂无数据！");
						result.setListMap(new ArrayList<Map<String, Object>>());
					}
				}else{//范围不为空，地图显示，不加分页
					//判断name，不等于null是分页搜索，不考虑范围
					name  = mainObj.optString("name");
					if(name!=null && !"".equals(name)){
						if (gastation.getPageNum() == null) {
							gastation.setPageNum(GlobalConstant.PAGE_NUM);
							gastation.setPageSize(GlobalConstant.PAGE_SIZE);
						} else {
							gastation.setPageNum(mainObj.optInt("pageNum"));
							gastation.setPageSize(mainObj.optInt("pageSize"));
						}
						gastation.setGas_station_name(name);
						Double longitude = new Double(0);
						Double latitude = new Double(0);
						// 获取气站列表
						List<Gastation> gastationAllList = gastationService.getAllStationList(gastation);
						if (gastationAllList != null && gastationAllList.size() > 0) {
							for (int i = 0; i < gastationAllList.size(); i++) {
								if (longitudeStr != null && !"".equals(longitudeStr) && latitudeStr != null && !"".equals(latitudeStr)) {
									longitude = new Double(longitudeStr);
									latitude = new Double(latitudeStr);
									String longStr = gastationAllList.get(i).getLongitude();
									String langStr = gastationAllList.get(i).getLatitude();
									Double longDb = new Double(0);
									Double langDb = new Double(0);
									if (longStr != null && !"".equals(longStr) && langStr != null && !"".equals(langStr)) {
										longDb = new Double(longStr);
										langDb = new Double(langStr);
									}
									// 计算当前加注站离指定坐标距离
									Double dist = DistCnvter.getDistance(longitude, latitude, longDb, langDb);
									gastationAllList.get(i).setDistance(dist);
								}
							}
							//按距离重新排序gastationAllList
							Collections.sort(gastationAllList);
							int pageNum = mainObj.optInt("pageNum");
							int pageSize = mainObj.optInt("pageSize");
							int allPage = gastationAllList.size()/pageSize==0?gastationAllList.size()/pageSize+1:(gastationAllList.size()/pageSize)+1;
							if(allPage >= pageNum ){
								if (pageNum == 0) {
									pageNum = 1;
								}
								int end = pageNum * pageSize;
								if (end > gastationAllList.size()) {
									end  = gastationAllList.size();
								}
								int begin = (pageNum - 1) * pageSize;
								if(begin > allPage ){
									begin = allPage;
								}
								PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(gastationAllList.subList(begin, end ));
								List<Gastation> gastationList1 = pageInfo.getList();
								List<Map<String, Object>> gastationArray = new ArrayList<>();
								if (gastationList1 != null && gastationList1.size() > 0) {
									for (Gastation gastationInfo : gastationList1) {
										Map<String, Object> gastationMap = new HashMap<>();
										gastationMap.put("stationId", gastationInfo.getSys_gas_station_id());
										gastationMap.put("name", gastationInfo.getGas_station_name());
										gastationMap.put("type", gastationInfo.getType());
										gastationMap.put("longitude", gastationInfo.getLongitude());
										gastationMap.put("latitude", gastationInfo.getLatitude());
										Usysparam usysparam = usysparamService.queryUsysparamByCode("STATION_DATA_TYPE",
												gastationInfo.getType());
										gastationMap.put("stationType", usysparam.getMname());
										gastationMap.put("service",
												gastationInfo.getGas_server() == null ? "暂无" : gastationInfo.getGas_server());// 提供服务
										gastationMap.put("preferential",
												gastationInfo.getPromotions() == null ? "暂无" : gastationInfo.getPromotions());// 优惠活动
										// 获取当前气站价格列表
										String price = gastationInfo.getLng_price();
										if (price != null && !"".equals(price)) {
											price = price.replaceAll("，", ",");
											price = price.replaceAll("：", ":");
											if (price.indexOf(":") != -1 && price.indexOf("/") != -1) {
												String strArray[] = price.split(",");
												Map[] map = new Map[strArray.length];
												for (int i = 0; i < strArray.length; i++) {
													String strInfo = strArray[i].trim();
													String strArray1[] = strInfo.split(":");
													String strArray2[] = strArray1[1].split("/");
													Map<String, Object> dataMap = new HashMap<>();
													dataMap.put("gasName", strArray1[0]);
													dataMap.put("gasPrice", strArray2[0]);
													dataMap.put("gasUnit", strArray2[1]);
													map[i] = dataMap;
												}
												gastationMap.put("priceList", map);
											} else {
												gastationMap.put("priceList", new ArrayList());
											}
										} else {
											gastationMap.put("priceList", new ArrayList());
										}
										gastationMap.put("phone", gastationInfo.getContact_phone());
										if (gastationInfo.getStatus().equals("0")) {
											gastationMap.put("state", "开启");
										} else {
											gastationMap.put("state", "关闭");
										}
										gastationMap.put("address", gastationInfo.getAddress());
										String infoUrl = http_poms_path + "/portal/crm/help/station?stationId="
												+ gastationInfo.getSys_gas_station_id();
										gastationMap.put("infoUrl", infoUrl);
										gastationMap.put("shareUrl", http_poms_path + "/portal/crm/help/share/station?stationId="
												+ gastationInfo.getSys_gas_station_id());
										gastationArray.add(gastationMap);
									}
									result.setListMap(gastationArray);
	
								} else {
									result.setStatus(MobileReturn.STATUS_SUCCESS);
									result.setMsg("暂无数据！");
									result.setListMap(new ArrayList<Map<String, Object>>());
								}
							}else {
								result.setStatus(MobileReturn.STATUS_SUCCESS);
								result.setMsg("无更多数据！");
								result.setListMap(new ArrayList<Map<String, Object>>());
							}
						} else {
							result.setStatus(MobileReturn.STATUS_SUCCESS);
							result.setMsg("暂无数据！");
							result.setListMap(new ArrayList<Map<String, Object>>());
						}
						//name为null时
					}else{
						String size = mainObj.optString("pageSize");
						//判断size，为空或者100时，地图显示，不分页
						if(size == null || "100".equals(size) ||"".equals(size)){
							int inRadius = Integer.parseInt(radius.trim());
							if(inRadius < 100000){
								inRadius = 500000;
							}
							radius = String.valueOf(inRadius);
							gastation.setGas_station_name(name);
							Double longitude = new Double(0);
							Double latitude = new Double(0);
							Double radiusDb = new Double(0);
							// 获取气站列表
							List<Gastation> gastationList = new ArrayList<Gastation>();
							List<Gastation> gastationAllList = gastationService.getAllStationList(gastation);
							if (gastationAllList != null && gastationAllList.size() > 0) {
								for (int i = 0; i < gastationAllList.size(); i++) {
									if (longitudeStr != null && !"".equals(longitudeStr) && latitudeStr != null
											&& !"".equals(latitudeStr) && radius != null && !"".equals(radius)) {
										longitude = new Double(longitudeStr);
										latitude = new Double(latitudeStr);
										radiusDb = new Double(radius);
										String longStr = gastationAllList.get(i).getLongitude();
										String langStr = gastationAllList.get(i).getLatitude();
										Double longDb = new Double(0);
										Double langDb = new Double(0);
										if (longStr != null && !"".equals(longStr) && langStr != null && !"".equals(langStr)) {
											longDb = new Double(longStr);
											langDb = new Double(langStr);
										}
										// 计算当前加注站离指定坐标距离
										Double dist = DistCnvter.getDistance(longitude, latitude, longDb, langDb);
										if (dist <= radiusDb) {// 在指定范围内，则返回当前加注站信息
											gastationList.add(gastationAllList.get(i));
										}
									}
								}
								PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(gastationList);
								List<Gastation> gastationList1 = pageInfo.getList();
								List<Map<String, Object>> gastationArray = new ArrayList<>();
								if (gastationList1 != null && gastationList1.size() > 0) {
									for (Gastation gastationInfo : gastationList1) {
										Map<String, Object> gastationMap = new HashMap<>();
										gastationMap.put("stationId", gastationInfo.getSys_gas_station_id());
										gastationMap.put("name", gastationInfo.getGas_station_name());
										gastationMap.put("type", gastationInfo.getType());
										gastationMap.put("longitude", gastationInfo.getLongitude());
										gastationMap.put("latitude", gastationInfo.getLatitude());
										Usysparam usysparam = usysparamService.queryUsysparamByCode("STATION_DATA_TYPE",
												gastationInfo.getType());
										gastationMap.put("stationType", usysparam.getMname());
										gastationMap.put("service",
												gastationInfo.getGas_server() == null ? "暂无" : gastationInfo.getGas_server());// 提供服务
										gastationMap.put("preferential",
												gastationInfo.getPromotions() == null ? "暂无" : gastationInfo.getPromotions());// 优惠活动
										// 获取当前气站价格列表
										String price = gastationInfo.getLng_price();
										if (price != null && !"".equals(price)) {
											price = price.replaceAll("，", ",");
											price = price.replaceAll("：", ":");
											if (price.indexOf(":") != -1 && price.indexOf("/") != -1) {
												String strArray[] = price.split(",");
												Map[] map = new Map[strArray.length];
												for (int i = 0; i < strArray.length; i++) {
													String strInfo = strArray[i].trim();
													String strArray1[] = strInfo.split(":");
													String strArray2[] = strArray1[1].split("/");
													Map<String, Object> dataMap = new HashMap<>();
													dataMap.put("gasName", strArray1[0]);
													dataMap.put("gasPrice", strArray2[0]);
													dataMap.put("gasUnit", strArray2[1]);
													map[i] = dataMap;
												}
												gastationMap.put("priceList", map);
											} else {
												gastationMap.put("priceList", new ArrayList());
											}
										} else {
											gastationMap.put("priceList", new ArrayList());
										}
										gastationMap.put("phone", gastationInfo.getContact_phone());
										if (gastationInfo.getStatus().equals("0")) {
											gastationMap.put("state", "开启");
										} else {
											gastationMap.put("state", "关闭");
										}
										gastationMap.put("address", gastationInfo.getAddress());
										String infoUrl = http_poms_path + "/portal/crm/help/station?stationId="
												+ gastationInfo.getSys_gas_station_id();
										gastationMap.put("infoUrl", infoUrl);
										gastationMap.put("shareUrl", http_poms_path + "/portal/crm/help/share/station?stationId="
												+ gastationInfo.getSys_gas_station_id());
										gastationArray.add(gastationMap);
									}
									result.setListMap(gastationArray);
								} else {
									result.setStatus(MobileReturn.STATUS_SUCCESS);
									result.setMsg("暂无数据！");
									result.setListMap(new ArrayList<Map<String, Object>>());
								}
							} else {
								result.setStatus(MobileReturn.STATUS_SUCCESS);
								result.setMsg("暂无数据！");
								result.setListMap(new ArrayList<Map<String, Object>>());
							}
						}else{//列表分页显示
							if (gastation.getPageNum() == null) {
								gastation.setPageNum(GlobalConstant.PAGE_NUM);
								gastation.setPageSize(GlobalConstant.PAGE_SIZE);
							} else {
								gastation.setPageNum(mainObj.optInt("pageNum"));
								gastation.setPageSize(mainObj.optInt("pageSize"));
							}
							gastation.setGas_station_name(name);
							Double longitude = new Double(0);
							Double latitude = new Double(0);
							// 获取气站列表
							List<Gastation> gastationAllList = gastationService.getAllStationList(gastation);
							if (gastationAllList != null && gastationAllList.size() > 0) {
								for (int i = 0; i < gastationAllList.size(); i++) {
									if (longitudeStr != null && !"".equals(longitudeStr) && latitudeStr != null && !"".equals(latitudeStr)) {
										longitude = new Double(longitudeStr);
										latitude = new Double(latitudeStr);
										String longStr = gastationAllList.get(i).getLongitude();
										String langStr = gastationAllList.get(i).getLatitude();
										Double longDb = new Double(0);
										Double langDb = new Double(0);
										if (longStr != null && !"".equals(longStr) && langStr != null && !"".equals(langStr)) {
											longDb = new Double(longStr);
											langDb = new Double(langStr);
										}
										// 计算当前加注站离指定坐标距离
										Double dist = DistCnvter.getDistance(longitude, latitude, longDb, langDb);
										gastationAllList.get(i).setDistance(dist);
									}
								}
								//按距离重新排序gastationAllList
								Collections.sort(gastationAllList);
								int pageNum = mainObj.optInt("pageNum");
								int pageSize = mainObj.optInt("pageSize");
								int allPage = gastationAllList.size()/pageSize==0?gastationAllList.size()/pageSize+1:(gastationAllList.size()/pageSize)+1;
								if(allPage >= pageNum ){
									if (pageNum == 0) {
										pageNum = 1;
									}
									int end = pageNum * pageSize;
									if (end > gastationAllList.size()) {
										end  = gastationAllList.size();
									}
									int begin = (pageNum - 1) * pageSize;
									if(begin > allPage ){
										begin = allPage;
									}
									PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(gastationAllList.subList(begin, end));
									List<Gastation> gastationList1 = pageInfo.getList();
									List<Map<String, Object>> gastationArray = new ArrayList<>();
									if (gastationList1 != null && gastationList1.size() > 0) {
										for (Gastation gastationInfo : gastationList1) {
											Map<String, Object> gastationMap = new HashMap<>();
											gastationMap.put("stationId", gastationInfo.getSys_gas_station_id());
											gastationMap.put("name", gastationInfo.getGas_station_name());
											gastationMap.put("type", gastationInfo.getType());
											gastationMap.put("longitude", gastationInfo.getLongitude());
											gastationMap.put("latitude", gastationInfo.getLatitude());
											Usysparam usysparam = usysparamService.queryUsysparamByCode("STATION_DATA_TYPE",
													gastationInfo.getType());
											gastationMap.put("stationType", usysparam.getMname());
											gastationMap.put("service",
													gastationInfo.getGas_server() == null ? "暂无" : gastationInfo.getGas_server());// 提供服务
											gastationMap.put("preferential",
													gastationInfo.getPromotions() == null ? "暂无" : gastationInfo.getPromotions());// 优惠活动
											// 获取当前气站价格列表
											String price = gastationInfo.getLng_price();
											if (price != null && !"".equals(price)) {
												price = price.replaceAll("，", ",");
												price = price.replaceAll("：", ":");
												if (price.indexOf(":") != -1 && price.indexOf("/") != -1) {
													String strArray[] = price.split(",");
													Map[] map = new Map[strArray.length];
													for (int i = 0; i < strArray.length; i++) {
														String strInfo = strArray[i].trim();
														String strArray1[] = strInfo.split(":");
														String strArray2[] = strArray1[1].split("/");
														Map<String, Object> dataMap = new HashMap<>();
														dataMap.put("gasName", strArray1[0]);
														dataMap.put("gasPrice", strArray2[0]);
														dataMap.put("gasUnit", strArray2[1]);
														map[i] = dataMap;
													}
													gastationMap.put("priceList", map);
												} else {
													gastationMap.put("priceList", new ArrayList());
												}
											} else {
												gastationMap.put("priceList", new ArrayList());
											}
											gastationMap.put("phone", gastationInfo.getContact_phone());
											if (gastationInfo.getStatus().equals("0")) {
												gastationMap.put("state", "开启");
											} else {
												gastationMap.put("state", "关闭");
											}
											gastationMap.put("address", gastationInfo.getAddress());
											String infoUrl = http_poms_path + "/portal/crm/help/station?stationId="
													+ gastationInfo.getSys_gas_station_id();
											gastationMap.put("infoUrl", infoUrl);
											gastationMap.put("shareUrl", http_poms_path + "/portal/crm/help/share/station?stationId="
													+ gastationInfo.getSys_gas_station_id());
											gastationArray.add(gastationMap);
										}
										result.setListMap(gastationArray);

									} else {
										result.setStatus(MobileReturn.STATUS_SUCCESS);
										result.setMsg("暂无数据！");
										result.setListMap(new ArrayList<Map<String, Object>>());
									}
								}else {
									result.setStatus(MobileReturn.STATUS_SUCCESS);
									result.setMsg("无更多数据！");
									result.setListMap(new ArrayList<Map<String, Object>>());
								}
							} else {
								result.setStatus(MobileReturn.STATUS_SUCCESS);
								result.setMsg("暂无数据！");
								result.setListMap(new ArrayList<Map<String, Object>>());
							}
						}
					}
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("查询气站信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询气站信息失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询气站信息失败： " + e);
			e.printStackTrace();
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 个人转账
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/deal/transferAccounts")
	@ResponseBody
	public String transferAccounts(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("转账成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String account = "account";
			String name = "name";
			String amount = "amount";
			String remark = "remark";
			String paycode = "paycode";
			boolean b = JsonTool.checkJson(mainObj, token, account, name, amount, remark, paycode);
			/**
			 * 请求接口
			 */
			if (b) {
				Map<String, Object> driverMap = new HashMap<>();
				driverMap.put("token", mainObj.optString("token"));
				driverMap.put("account", mainObj.optString("account"));
				driverMap.put("name", mainObj.optString("name"));
				driverMap.put("amount", mainObj.optString("amount"));
				driverMap.put("remark", mainObj.optString("remark"));
				driverMap.put("paycode", mainObj.optString("paycode"));
				int resultVal = mbDealOrderService.transferDriverToDriver(driverMap);
				if (resultVal < 0) {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("账户余额不足,无法转账！");
				} else if (resultVal == 1) {
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("转账成功！");
				} else if (resultVal == 2) {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("账户不存在,无法转账！");
				} else if (resultVal == 3) {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("司机不存在,无法转账！");
				} else if (resultVal == 4) {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("支付密码错误！");
				} else if (resultVal == 5) {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("账户和用户名不匹配！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("转账信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (RealNameException e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("未认证司机无法接受转账！");
			resutObj = JSONObject.fromObject(result);
			logger.error("转账失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("转账失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("转账失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 返现规则列表
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/deal/backRule")
	@ResponseBody
	public String getCashBackList(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询返现规则列表成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");

			/**
			 * 请求接口
			 */
			if (mainObj != null) {
				List<Map<String, Object>> cashBackList = sysCashBackService.queryCashBackList();
				result.setListMap(cashBackList);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("查询返现规则列表信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询返现规则列表失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询返现规则列表失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 头条推广
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/msg/extension")
	@ResponseBody
	public String recharge(String params) {
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
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String cityName = "cityName";
			String extendType = "extendType";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj, cityName, extendType, pageNum, pageSize);
			/**
			 * 请求接口
			 */
			if (b) {
				PageBean bean = new PageBean();
				String http_poms_path = (String) prop.get("http_poms_path");
				cityName = mainObj.optString("cityName");
				DistCity city = new DistCity();
				city.setCityName(cityName);
				city = districtService.queryCityInfo(city);
				String cityId = "";
				if (city != null) {
					cityId = city.getCityId();
				}
				mbBanner.setCityId(cityId);
				if (mbBanner.getPageNum() == null) {
					mbBanner.setPageNum(GlobalConstant.PAGE_NUM);
					mbBanner.setPageSize(GlobalConstant.PAGE_SIZE);
				} else {
					mbBanner.setPageNum(mainObj.optInt("pageNum"));
					mbBanner.setPageSize(mainObj.optInt("pageSize"));
				}

				if (mainObj.get("extendType") != null && !"".equals(mainObj.get("extendType").toString())) {
					mbBanner.setImgType(mainObj.get("extendType").toString());
				} else {
					mbBanner.setImgType(GlobalConstant.ImgType.TOP);
				}

				PageInfo<MbBanner> pageInfo = mbBannerService.queryMbBannerListPage(mbBanner);
				List<MbBanner> mbBannerList = pageInfo.getList();
				List<Map<String, Object>> bannerList = new ArrayList<>();
				if (mbBannerList != null && mbBannerList.size() > 0) {
					SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					for (MbBanner banner : mbBannerList) {
						Map<String, Object> bannerMap = new HashMap<>();
						bannerMap.put("bannerId", banner.getMbBannerId());
						bannerMap.put("title", banner.getTitle());
						bannerMap.put("content", banner.getContent());
						bannerMap.put("time", sft.format(banner.getCreatedDate()));
						bannerMap.put("contentUrl", http_poms_path + banner.getTargetUrl() + "&view="+banner.getMbBannerId());
						bannerMap.put("shareUrl", http_poms_path + banner.getTargetUrl() + "&show_download_button=1" + "&view="+banner.getMbBannerId());
						bannerMap.put("imgSmPath", http_poms_path + banner.getImgSmPath());
						if (banner.getImgPath() != null && !"".equals(banner.getImgPath().toString())) {
							bannerMap.put("imageUrl", http_poms_path + banner.getImgPath());
						} else {
							bannerMap.put("imageUrl", "");
						}

						bannerList.add(bannerMap);
					}
				}
				result.setListMap(bannerList);

			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("查询头条推广信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询头条推广失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询头条推广失败： " + e);
			e.printStackTrace();
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 充值记录
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/record/rechargeRecord")
	@ResponseBody
	public String rechargeRecord(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询充值记录成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			SysOrder order = new SysOrder();
			SysDriver driver = new SysDriver();

			SysDriver sysDriver = new SysDriver();
			/**
			 * 必填参数
			 */
			String token = "token";
			String time = "time";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj, token, time, pageNum, pageSize);
			/**
			 * 请求接口
			 */
			if (b) {
				sysDriver.setSysDriverId(mainObj.optString("token"));
				order.setSysDriver(sysDriver);
				if (order.getPageNum() == null) {
					order.setPageNum(GlobalConstant.PAGE_NUM);
					order.setPageSize(GlobalConstant.PAGE_SIZE);
				} else {
					order.setPageNum(mainObj.optInt("pageNum"));
					order.setPageSize(mainObj.optInt("pageSize"));
				}
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				SimpleDateFormat sft1 = new SimpleDateFormat("yyyy-MM");
				order.setOrderDate(sft1.parse(mainObj.optString("time")));
				BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
				BigDecimal totalBack = new BigDecimal(BigInteger.ZERO);
				PageInfo<Map<String, Object>> pageInfo = orderService.queryDriverReChargePage(order);
				List<Map<String, Object>> list = orderService.queryDriverReCharge(order);
				for (Map<String, Object> data : list) {
					// 汇总充值总额
					if (data.get("cash") != null && !"".equals(data.get("cash").toString())) {
						totalCash = totalCash.add(new BigDecimal(data.get("cash").toString()));
					}
					// 汇总返现总额
					if (data.get("cash_back_driver") != null && !"".equals(data.get("cash_back_driver").toString())) {
						totalBack = totalBack.add(new BigDecimal(data.get("cash_back_driver").toString()));
					}
				}
				List<Map<String, Object>> reChargeList = new ArrayList<>();
				Map<String, Object> reCharge = new HashMap<>();
				if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {

					for (Map<String, Object> map : pageInfo.getList()) {
						Map<String, Object> reChargeMap = new HashMap<>();
						reChargeMap.put("orderNum", map.get("orderNumber"));
						reChargeMap.put("amount", map.get("cash"));
						reChargeMap.put("cashBack", map.get("cash_back_driver"));
						reChargeMap.put("rechargePlatform", map.get("channel"));

						String chargeType = "";
						if (map.get("chargeType") != null && !"".equals(map.get("chargeType").toString())) {
							chargeType = GlobalConstant.getCashBackNumber(map.get("chargeType").toString());
						}
						reChargeMap.put("paymentType", chargeType);
						reChargeMap.put("remark", map.get("remark"));
						String dateTime = "";
						if (map.get("orderDate") != null && !"".equals(map.get("orderDate").toString())) {
							dateTime = map.get("orderDate").toString().substring(0, 19);
						} else {
							dateTime = sft.format(new Date());
						}
						reChargeMap.put("time", dateTime);
						reChargeList.add(reChargeMap);
					}
					driver = driverService.queryDriverByPK(mainObj.optString("token"));
					reCharge.put("listMap", reChargeList);
					if (driver != null && driver.getAccount() != null) {
						reCharge.put("totalAmount", driver.getAccount().getAccountBalance());
					} else {
						reCharge.put("totalAmount", 0.00);
					}

				}
				reCharge.put("totalCash", totalCash);
				reCharge.put("totalBack", totalBack);
				result.setData(reCharge);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("查询充值记录信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询充值记录失败！");
			resutObj = JSONObject.fromObject(result);
			e.printStackTrace();
			logger.error("查询充值记录失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 消费记录
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/record/consumeRecord")
	@ResponseBody
	public String consumeRecord(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询消费记录成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";

		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			SysOrder order = new SysOrder();
			/**
			 * 必填参数
			 */
			String token = "token";
			String time = "time";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj, token, time, pageNum, pageSize);
			/**
			 * 请求接口
			 */
			if (b) {
				if (order.getPageNum() == null) {
					order.setPageNum(GlobalConstant.PAGE_NUM);
					order.setPageSize(GlobalConstant.PAGE_SIZE);
				} else {
					order.setPageNum(mainObj.optInt("pageNum"));
					order.setPageSize(mainObj.optInt("pageSize"));
				}
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				SimpleDateFormat sft1 = new SimpleDateFormat("yyyy-MM");
				SysDriver sysDriver = new SysDriver();
				sysDriver.setSysDriverId(mainObj.optString("token"));
				order.setSysDriver(sysDriver);
				order.setOrderDate(sft1.parse(mainObj.optString("time")));
				PageInfo<Map<String, Object>> pageInfo = orderService.queryDriverConsumePage(order);
				List<Map<String, Object>> reChargeList = new ArrayList<>();
				Map<String, Object> reCharge = new HashMap<>();
				BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
				BigDecimal totalBack = new BigDecimal(BigInteger.ZERO);
				List<Map<String, Object>> list = orderService.queryDriverConsume(order);
				for (Map<String, Object> data : list) {
					// 汇总消费总额
					if (data.get("cash") != null && !"".equals(data.get("cash").toString())) {
						totalCash = totalCash.add(new BigDecimal(data.get("cash").toString()));
					}
				}
				if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {

					for (Map<String, Object> map : pageInfo.getList()) {
						Map<String, Object> reChargeMap = new HashMap<>();
						reChargeMap.put("orderNum", map.get("orderNumber"));
						reChargeMap.put("amount", map.get("cash"));
						reChargeMap.put("gasStationName", gastationService.queryGastationByPK(map.get("channelNumber").toString()).getGas_station_name());
						reChargeMap.put("gasStationId", map.get("channelNumber"));
						reChargeMap.put("gasTotal",map.get("goods_sum")==null?"0":map.get("goods_sum"));
						//0初始化，1成功，2失败，3待支付
						String status = map.get("orderStatus").toString();
						if(status.equals("0")){
							status = "订单初始化";
						}else if(status.equals("1")){
							status = "支付成功";
						}else if(status.equals("2")){
							status = "支付失败";
						}else if(status.equals("3")){
							status = "待支付订单";
						}
						reChargeMap.put("payStatus", status);
						/*String chargeType = "";
						if (map.get("chargeType") != null && !"".equals(map.get("chargeType").toString())) {
							chargeType = GlobalConstant.getCashBackNumber(map.get("chargeType").toString());
						}
						reChargeMap.put("paymentType", chargeType);*/
						reChargeMap.put("remark", map.get("remark"));
						String dateTime = "";
						if (map.get("orderDate") != null && !"".equals(map.get("orderDate").toString())) {
							dateTime = map.get("orderDate").toString().substring(0, 19);
						} else {
							dateTime = sft.format(new Date());
						}
						Object obj = map.get("preferential_cash");//平台优惠金额
						Object coupon_cash = map.get("coupon_cash");//优惠券优惠金额
						BigDecimal cashAll = new BigDecimal(0);
						if(obj!=null && !"".equals(obj)){
							cashAll = new BigDecimal(obj.toString());
						}
						if(coupon_cash!=null && !coupon_cash.equals("")){
							cashAll = cashAll.add(new BigDecimal(coupon_cash.toString()));
						}
						reChargeMap.put("time", dateTime);
						reChargeMap.put("shouldPayment",map.get("should_payment"));//应付金额
						reChargeMap.put("preferentialCash",cashAll);//优惠金额
						reChargeMap.put("couponTitle", map.get("couponTitle")==null?"":map.get("couponTitle"));//优惠券标题
						//C01 卡余额消费,C02 POS消费,C03	微信消费,C04 支付宝消费
						String chargeType = (String) map.get("spend_type");
						if(chargeType.equals("C01")){
							chargeType = "卡余额消费";
						}else if(chargeType.equals("C02")){
							chargeType = "POS消费";
						}else if(chargeType.equals("C03")){
							chargeType = "微信消费";
						}else if(chargeType.equals("C04 ")){
							chargeType = "支付宝消费";
						}
						reChargeMap.put("chargeType", chargeType);//
						reChargeList.add(reChargeMap);
					}
					reCharge.put("listMap", reChargeList);
				} else {
					reCharge.put("totalCash", "0");
					reCharge.put("listMap", new ArrayList<>());
				}
				reCharge.put("totalCash", totalCash);
				result.setData(reCharge);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("查询消费记录信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询消费记录失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询消费记录失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 转账记录
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/record/transferRecord")
	@ResponseBody
	public String transferRecord(String params) {
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
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String time = "time";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj, token, time, pageNum, pageSize);
			/**
			 * 请求接口
			 */
			if (b) {
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				SimpleDateFormat sft1 = new SimpleDateFormat("yyyy-MM");
				if (order.getPageNum() == null) {
					order.setPageNum(GlobalConstant.PAGE_NUM);
					order.setPageSize(GlobalConstant.PAGE_SIZE);
				} else {
					order.setPageNum(mainObj.optInt("pageNum"));
					order.setPageSize(mainObj.optInt("pageSize"));
				}
				SysDriver sysDriver = new SysDriver();
				sysDriver.setSysDriverId(mainObj.optString("token"));
				order.setSysDriver(sysDriver);
				order.setOrderDate(sft1.parse(mainObj.optString("time")));
				PageInfo<Map<String, Object>> pageInfo = orderService.queryDriverTransferPage(order);
				List<Map<String, Object>> reChargeList = new ArrayList<>();
				Map<String, Object> reCharge = new HashMap<>();
				BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
				BigDecimal totalBack = new BigDecimal(BigInteger.ZERO);
				List<Map<String, Object>> list = orderService.queryDriverTransfer(order);
				for (Map<String, Object> data : list) {
					if ("0".equals(data.get("type"))) {
						// 汇总转出总额
						if (data.get("cash") != null && !"".equals(data.get("cash").toString())) {
							BigDecimal tempVal = new BigDecimal(data.get("cash").toString());

							if (tempVal.compareTo(BigDecimal.ZERO) > 0) {
								totalCash = totalCash.add(tempVal);
							}
						}
					} else {
						// 汇总转入总额
						if (data.get("cash") != null && !"".equals(data.get("cash").toString())) {
							BigDecimal tempVal = new BigDecimal(data.get("cash").toString());
							if (tempVal.compareTo(BigDecimal.ZERO) > 0) {
								totalBack = totalBack.add(tempVal);
							}
						}
					}
				}
				if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {
					for (Map<String, Object> map : pageInfo.getList()) {
						Map<String, Object> reChargeMap = new HashMap<>();
						reChargeMap.put("orderNum", map.get("orderNumber"));
						reChargeMap.put("amount", map.get("cash"));
						reChargeMap.put("operator", map.get("operator"));
						reChargeMap.put("remark", map.get("remark"));
						reChargeMap.put("type", map.get("type"));
						String dateTime = "";
						if (map.get("orderDate") != null && !"".equals(map.get("orderDate").toString())) {
							dateTime = map.get("orderDate").toString().substring(0, 19);
						} else {
							dateTime = sft.format(new Date());
						}
						reChargeMap.put("time", dateTime);
						reChargeList.add(reChargeMap);
					}
					reCharge.put("listMap", reChargeList);
				} else {
					reCharge.put("listMap", new ArrayList<>());
				}
				reCharge.put("totalOut", totalCash);
				reCharge.put("totalIn", totalBack);
				result.setData(reCharge);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}

			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("查询转账记录信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询转账记录失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询转账记录失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 在线充值
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/deal/paramList")
	@ResponseBody
	public String getRecharge(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("充值成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		String http_poms_path = (String) prop.get("http_poms_path");
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);// 参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			boolean b = false;
			String orderType = mainObj.optString("orderType");
			String token = "token";
			String gastationId = "gastationId";
			String payableAmount = "payableAmount";
			String amount = "amount";
			String feeCount = "feeCount";
			String payType = "payType";
			String gasTotal = "gasTotal";
			//充值必填参数校验
			if(orderType==null || "".equals(orderType)){
				b= JsonTool.checkJson(mainObj, token, feeCount, payType);
			}
			//消费必填参数校验
			if(orderType!=null && !"".equals(orderType) && orderType.equals("220")){
				b= JsonTool.checkJson(mainObj, token, gastationId, payableAmount,amount);
			}
			/**
			 * 请求接口
			 */
			if (b) {
				payType = mainObj.optString("payType");
				feeCount = mainObj.optString("feeCount");
				String driverID = mainObj.optString("token");
				gasTotal = mainObj.optString("gasTotal");
				String orderID = UUIDGenerator.getUUID();
				Map<String, Object> data = new HashedMap();
				SysOrder sysOrder = null;
				String thirdPartyID = null;
				//发送短信
				MobileVerification verification = new MobileVerification();
				verification.setPhoneNum(driverService.queryDriverByPK(driverID).getMobilePhone());
				
				//不为空或空字符串，为司机消费订单
				if(orderType!=null && !"".equals(orderType) && orderType.equals("220")){
					//查询消费订单个数
					int number = orderService.queryConsumerOrderNumber(driverID);
					//优惠券ID
					String couponId = mainObj.optString("couponId");		
					//优惠券金额
					String couponCash = mainObj.optString("couponCash");	
					//气站ID
					gastationId = mainObj.optString("gastationId");	
					//应付金额
					payableAmount = mainObj.optString("payableAmount");	
					//实付金额
					amount =  mainObj.optString("amount");	
					//账户正常使用
					if(driverService.queryDriverByPK(driverID).getUserStatus().equals("0")){
						if (payType.equalsIgnoreCase("2")) { // 支付宝消费
							//设置平台优惠金额
							BigDecimal preferential_cash = new BigDecimal(0);
							preferential_cash = new BigDecimal(payableAmount).subtract(new BigDecimal(amount));
							sysOrder = createNewOrder(orderID, driverID, amount, GlobalConstant.OrderChargeType.APP_CONSUME_CHARGE,GlobalConstant.ORDER_SPEND_TYPE.ALIPAY,"2","C04"); // TODO充值成功后再去生成订单
							//设置优惠券ID
							if(couponId!=null && !"".equals(couponId)){
								UserCoupon uc = new UserCoupon();
								uc.setCoupon_id(couponId);
								uc.setSys_driver_id(token);
								sysOrder.setCoupon_number(couponService.queryUserCouponId(uc));
							}
							//设置优惠金额
							if(couponCash!=null && !"".equals(couponCash)){
								sysOrder.setCoupon_cash(new BigDecimal(couponCash));
								preferential_cash = preferential_cash.subtract(new BigDecimal(amount));
							}
							//设置平台优惠金额
							sysOrder.setPreferential_cash(preferential_cash);
							//设置气站ID
							sysOrder.setChannelNumber(gastationId);
							sysOrder.setChannel("APP-支付宝消费-"+gastationService.queryGastationByPK(gastationId).getGas_station_name());
							//设置应付金额
							sysOrder.setShould_payment(new BigDecimal(payableAmount));
							//设置实付金额
							sysOrder.setCash(new BigDecimal(amount));
							orderService.checkIfCanConsume(sysOrder);
							String notifyUrl = http_poms_path + "/api/v1/mobile/deal/alipayConsum";
							Map<String, String> paramsApp = OrderInfoUtil2_0.buildOrderParamMap(APPID, amount, "司集云平台-会员消费",
									"司集云平台-会员消费", orderID, notifyUrl);
							String orderParam = OrderInfoUtil2_0.buildOrderParam(paramsApp);
							String sign = OrderInfoUtil2_0.getSign(paramsApp, RSA_PRIVATE);
							thirdPartyID = orderID;
							if (sysOrder != null) {
								sysOrder.setThirdPartyOrderID(thirdPartyID);
								int nCreateOrder = orderService.insert(sysOrder, null);
								if (nCreateOrder < 1){
									throw new Exception("订单生成错误：" + sysOrder.getOrderId());
								}
								//添加OrderGoods信息
								List<Map<String, Object>> gsGasPriceList = gsGasPriceService.queryDiscount(gastationId);
								SysOrderGoods orderGoods = new SysOrderGoods ();
								//ID
								orderGoods.setOrderGoodsId(UUIDGenerator.getUUID());
								//orderID
								orderGoods.setOrderId(orderID);
								//原始单价
								orderGoods.setPrice(new BigDecimal(gsGasPriceList.get(0).get("product_price").toString()));
								//加气总量
								orderGoods.setNumber(Double.valueOf(gasTotal));
								//商品总价
								orderGoods.setSumPrice(new BigDecimal(payableAmount));
								//商品类型
								orderGoods.setGoodsType(gsGasPriceList.get(0).get("gas_name").toString());
								//优惠类型。
								orderGoods.setPreferential_type(gsGasPriceList.get(0).get("preferential_type").toString());
								//平台优惠金额
								orderGoods.setDiscountSumPrice(preferential_cash);
								int rs = sysOrderGoodsService.saveOrderGoods(orderGoods);
								if(rs < 1){
									throw new Exception("orderGoods信息添加失败");
								}
							}
							data.put("payReq", orderParam + "&" + sign);
							data.put("orderId", orderID);
							result.setData(data);
						} else if (payType.equalsIgnoreCase("1")) { // 微信消费
							sysOrder = createNewOrder(orderID, driverID, amount,GlobalConstant.OrderChargeType.APP_CONSUME_CHARGE,GlobalConstant.ORDER_SPEND_TYPE.WECHAT,"2","C03"); // TODO充值成功后再去生成订单
							//设置平台优惠金额
							BigDecimal preferential_cash = new BigDecimal(0);
							preferential_cash = new BigDecimal(payableAmount).subtract(new BigDecimal(amount));
							//设置优惠券ID
							if(couponId!=null && !"".equals(couponId)){
								UserCoupon uc = new UserCoupon();
								uc.setCoupon_id(couponId);
								uc.setSys_driver_id(token);
								sysOrder.setCoupon_number(couponService.queryUserCouponId(uc));
							}
							//设置优惠金额
							if(couponCash!=null && !"".equals(couponCash)){
								sysOrder.setCoupon_cash(new BigDecimal(couponCash));
								preferential_cash = preferential_cash.subtract(new BigDecimal(amount));
							}
							//设置平台优惠金额
							sysOrder.setPreferential_cash(preferential_cash);
							//设置气站ID
							sysOrder.setChannelNumber(gastationId);
							sysOrder.setChannel("APP-微信消费-"+gastationService.queryGastationByPK(gastationId).getGas_station_name());
							//设置应付金额
							sysOrder.setShould_payment(new BigDecimal(payableAmount));
							//设置实付金额
							sysOrder.setCash(new BigDecimal(amount));
							orderService.checkIfCanConsume(sysOrder);
							//消费金额，微信消费金额单位为分，不能有小数
							Integer money = (int) (new Double(amount)*100);
							String entity = genProductArgs(orderID,money.toString(),"2");
							byte[] buf = Util.httpPost(url, entity);
							String content = new String(buf, "utf-8");
							Map<String, String> orderHashs = decodeXml(content);
							String payReq = genPayReq(orderHashs);
							thirdPartyID = orderHashs.get("prepay_id");
							if (sysOrder != null) {
								sysOrder.setThirdPartyOrderID(thirdPartyID);
								int nCreateOrder = orderService.insert(sysOrder, null);
								if (nCreateOrder < 1){
									throw new Exception("订单生成错误：" + sysOrder.getOrderId());
								}
								//添加OrderGoods信息
								List<Map<String, Object>> gsGasPriceList = gsGasPriceService.queryDiscount(gastationId);
								SysOrderGoods orderGoods = new SysOrderGoods ();
								//ID
								orderGoods.setOrderGoodsId(UUIDGenerator.getUUID());
								//orderID
								orderGoods.setOrderId(orderID);
								//原始单价
								orderGoods.setPrice(new BigDecimal(gsGasPriceList.get(0).get("product_price").toString()));
								//加气总量
								orderGoods.setNumber(Double.valueOf(gasTotal));
								//商品总价
								orderGoods.setSumPrice(new BigDecimal(payableAmount));
								//商品类型
								orderGoods.setGoodsType(gsGasPriceList.get(0).get("gas_name").toString());
								//优惠类型。
								orderGoods.setPreferential_type(gsGasPriceList.get(0).get("preferential_type").toString());
								//平台优惠金额
								orderGoods.setDiscountSumPrice(preferential_cash);
								int rs = sysOrderGoodsService.saveOrderGoods(orderGoods);
								if(rs < 1){
									throw new Exception("orderGoods信息添加失败");
								}
							}
							JSONObject dataObj = JSONObject.fromObject(payReq);
							data.put("payReq", dataObj);
							data.put("orderId", orderID);
							result.setData(data);
						}
						//首次消费成功，发放优惠券
						if(number==0){
							CouponGroup couponGroup = new CouponGroup();
				            couponGroup.setIssued_type(GlobalConstant.COUPONGROUP_TYPE.FIRST_CONSUME);
				            List<CouponGroup> list = couponGroupService.queryCouponGroup(couponGroup).getList();
				            if(list.size()>0){
				            	couponGroupService.sendCouponGroup(driverID, list, this.appOperatorId);
				            }
						}
					}else if(driverService.queryDriverByPK(driverID).getUserStatus().equals("1")){//冻结
						result.setStatus(MobileReturn.STATUS_SUCCESS);
						result.setMsg("账户已冻结！");
					}else if(driverService.queryDriverByPK(driverID).getUserStatus().equals("2")){// 离职
						result.setStatus(MobileReturn.STATUS_SUCCESS);
						result.setMsg("司机已离职！");
					}
				}else{//充值订单
					if (payType.equalsIgnoreCase("2")) { // 支付宝支付
						sysOrder = createNewOrder(orderID, driverID, feeCount, GlobalConstant.OrderChargeType.CHARGETYPE_ALIPAY_CHARGE,null,"1",null); // TODO充值成功后再去生成订单
						orderService.checkIfCanChargeToDriver(sysOrder);
						String notifyUrl = http_poms_path + "/api/v1/mobile/deal/alipayCallBackPay";
						Map<String, String> paramsApp = OrderInfoUtil2_0.buildOrderParamMap(APPID, feeCount, "司集云平台-会员充值",
								"司集云平台-会员充值", orderID, notifyUrl);
						String orderParam = OrderInfoUtil2_0.buildOrderParam(paramsApp);
						String sign = OrderInfoUtil2_0.getSign(paramsApp, RSA_PRIVATE);
						thirdPartyID = orderID;
						if (sysOrder != null) {
							sysOrder.setThirdPartyOrderID(thirdPartyID);
							int nCreateOrder = orderService.insert(sysOrder, null);
							if (nCreateOrder < 1){
								throw new Exception("订单生成错误：" + sysOrder.getOrderId());
							}
						}
						data.put("payReq", orderParam + "&" + sign);
						result.setData(data);
					} else if (payType.equalsIgnoreCase("1")) { // 微信支付
						sysOrder = createNewOrder(orderID, driverID, feeCount,GlobalConstant.OrderChargeType.CHARGETYPE_WEICHAT_CHARGE,null,"1",null); // TODO充值成功后再去生成订单
						orderService.checkIfCanChargeToDriver(sysOrder);
						String entity = genProductArgs(orderID, feeCount,"1");
						byte[] buf = Util.httpPost(url, entity);
						String content = new String(buf, "utf-8");
						Map<String, String> orderHashs = decodeXml(content);
						String payReq = genPayReq(orderHashs);
						thirdPartyID = orderHashs.get("prepay_id");
						if (sysOrder != null) {
							sysOrder.setThirdPartyOrderID(thirdPartyID);
							int nCreateOrder = orderService.insert(sysOrder, null);
							if (nCreateOrder < 1){
								throw new Exception("订单生成错误：" + sysOrder.getOrderId());
							}
						}
						JSONObject dataObj = JSONObject.fromObject(payReq);
						data.put("payReq", dataObj);
						result.setData(data);
					}
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("充值信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
			return resultStr;
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("充值失败！" + e.getMessage());
			resutObj = JSONObject.fromObject(result);
			logger.error("充值失败： " + e.getMessage());
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		}
	}

	/**
	 * 微信在线支付回调方法(充值回调)
	 */
	@RequestMapping(value = "/deal/wechatCallBackPay")
	@ResponseBody
	public String wechatCallBackPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultStr = "";
		String orderId = "";
		String transaction_id = "";
		String feeCount = "";
		logger.debug("微信支付回调获取数据开始");
		String inputLine;
		String notityXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			logger.debug("xml获取失败：" + e);
			throw new ServiceException("xml获取失败！");
		}
		System.out.println("接收到的报文：" + notityXml);
		logger.debug("收到微信异步回调：");
		logger.debug(notityXml);
		if (StringUtils.isEmpty(notityXml)) {
			logger.debug("xml为空：");
			throw new ServiceException("xml为空！");
		} else {
			Document document = DocumentHelper.parseText(notityXml);
			Element node = document.getRootElement();
			listNodes(node);
			Element element = node.element("out_trade_no");
			orderId = element.getText();
			Element element1 = node.element("transaction_id");
			transaction_id = element1.getText();
			Element element2 = node.element("cash_fee");
			feeCount = element2.getText();
			feeCount = String.valueOf(Double.valueOf(feeCount)/100);
		}
		if (orderId != null && !"".equals(orderId)) {
			MobileReturn result = new MobileReturn();
			result.setStatus(MobileReturn.STATUS_SUCCESS);
			result.setMsg("支付成功！");
			JSONObject resutObj = new JSONObject();
			// 查询订单内容
			SysOrder order = orderService.selectByPrimaryKey(orderId);
			if (order != null && order.getOrderStatus() == 0) {// 0 初始化 1 成功 2
				SysUserAccount account=sysUserAccountService.queryUserAccountByDriverId(order.getDebitAccount());
				//判断是否是第一次充值
				if(!orderService.exisit(order.getDebitAccount())){
					List<SysCashBack> listBack=sysCashBackService.queryForBreak("202");
					if (listBack!=null && listBack.size() > 0) {
						SysCashBack back= listBack.get(0);//获取返现规则
						sysUserAccountService.addCashToAccount(account.getSysUserAccountId(), BigDecimal.valueOf(Double.valueOf(back.getCash_per())), GlobalConstant.OrderType.REGISTER_CASHBACK);
						//添加首次充值订单
						SysOrderDeal newDeal=new SysOrderDeal();
//						orderDealService
						newDeal.setOrderId(orderId);
						newDeal.setDealId(UUID.randomUUID().toString().replaceAll("-", ""));
						newDeal.setDealNumber(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
						newDeal.setDealDate(new Date());
						newDeal.setDealType("202");
						newDeal.setCashBack(new BigDecimal(back.getCash_per()));
						newDeal.setRunSuccess(GlobalConstant.OrderProcessResult.SUCCESS);
						newDeal.setRemark("");
						orderDealService.insert(newDeal);
					}else{
						logger.info("找不到匹配的返现规则，返现失败");
					}
				}
				// 修改订单状态
				SysOrder sysOrder = new SysOrder();
				sysOrder.setOrderId(orderId);
				sysOrder.setOrderStatus(1);
				sysOrder.setTrade_no(transaction_id);
				orderService.updateByPrimaryKey(sysOrder);
				//更新最新余额到账户
				//微信在线支付回调方法(充值回调)
				//sysUserAccountService.addCashToAccount(account.getSysUserAccountId(), new BigDecimal(feeCount), GlobalConstant.OrderType.CHARGE_TO_DRIVER);
				try {
					String orderCharge = orderService.chargeToDriver(order);
          			//系统关键日志记录
        			SysOperationLog sysOperationLog = new SysOperationLog();
        			sysOperationLog.setOperation_type("cz");
        			sysOperationLog.setLog_platform("1");
            		sysOperationLog.setOrder_number(order.getOrderNumber());
            		sysOperationLog.setLog_content("app微信在线充值回调成功！充值金额："+order.getCash()+"，订单号为："+order.getOrderNumber()); 
        			//操作日志
        			sysOperationLogService.saveOperationLog(sysOperationLog,order.getDebitAccount());
					if (!orderCharge.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)) {
						throw new Exception("订单充值错误：" + orderCharge);
					} else {
						resultStr = getWechatResult();// 返回通知微信支付成功
						//微信充值短信通知
						AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
						aliShortMessageBean.setSendNumber(driverService.queryDriverByPK(orderService.queryById(orderId).getDebitAccount()).getMobilePhone());
						aliShortMessageBean.setAccountNumber(driverService.queryDriverByPK(orderService.queryById(orderId).getDebitAccount()).getMobilePhone());
						aliShortMessageBean.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						aliShortMessageBean.setSpentMoney(feeCount);
						String backCash = String.valueOf(orderService.backCash(orderId));
						aliShortMessageBean.setBackCash(backCash.equals("null")||backCash==null?"0":backCash);
						aliShortMessageBean.setBalance(sysUserAccountService.queryUserAccountByDriverId(driverService.queryDriverByPK(orderService.queryById(orderId).getDebitAccount()).getSysDriverId()).getAccountBalance());
						AliShortMessage.sendShortMessage(aliShortMessageBean, SHORT_MESSAGE_TYPE.DRIVER_CHARGE_BACKCASH);
						//APP提示
						sysMessageService.saveMessageTransaction("微信充值", sysOrder,"1");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("订单充值错误：" + e);
				}
			}
		}
		return resultStr;
	}
	/**
	 * 微信在线支付回调方法(消费回调)
	 */
	@RequestMapping(value = "/deal/wechatConsum")
	@ResponseBody
	public String wechatConsum(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MobileVerification verification = new MobileVerification();
		String feeCount = "";
		String resultStr = "";
		String orderId = "";
		String transaction_id = "";
		logger.debug("微信支付回调获取数据开始");
		String inputLine;
		String notityXml = "";
		try {

			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			logger.debug("xml获取失败：" + e);
			throw new ServiceException("xml获取失败！");

		}
		System.out.println("接收到的报文：" + notityXml);
		logger.debug("收到微信异步回调：");
		logger.debug(notityXml);
		if (StringUtils.isEmpty(notityXml)) {
			logger.debug("xml为空：");
			throw new ServiceException("xml为空！");
		} else {
			Document document = DocumentHelper.parseText(notityXml);
			Element node = document.getRootElement();
			listNodes(node);
			Element element = node.element("out_trade_no");
			orderId = element.getText();
			Element element1 = node.element("transaction_id");
			transaction_id = element1.getText();
			Element element2 = node.element("cash_fee");
			feeCount = element2.getText();
			feeCount = String.valueOf(Double.valueOf(feeCount)/100);
		}

		if (orderId != null && !"".equals(orderId)) {
			MobileReturn result = new MobileReturn();
			result.setStatus(MobileReturn.STATUS_SUCCESS);
			result.setMsg("消费成功！");
			JSONObject resutObj = new JSONObject();

			// 查询订单内容
			SysOrder order = orderService.selectByPrimaryKey(orderId);
			if (order != null && order.getOrderStatus() == 0) {// 0 初始化 1 成功 2
																// 失败 3 待支付
				// 修改订单状态
				SysOrder sysOrder = new SysOrder();
				sysOrder.setOrderId(orderId);
				sysOrder.setOrderStatus(1);
				sysOrder.setTrade_no(transaction_id);
				orderService.updateByPrimaryKey(sysOrder);
				SysUserAccount account=sysUserAccountService.queryUserAccountByDriverId(order.getCreditAccount());
				//更新最新余额到账户
				sysUserAccountService.addCashToAccount(account.getSysUserAccountId(), new BigDecimal(feeCount), GlobalConstant.OrderType.CONSUME_BY_DRIVER);
				try {
					String orderCharge = orderService.consumeByDriver(order);
					//系统关键日志记录
	    			SysOperationLog sysOperationLog = new SysOperationLog();
	    			sysOperationLog.setOperation_type("cz");
	    			sysOperationLog.setLog_platform("2");
	        		sysOperationLog.setOrder_number(order.getOrderNumber());
	        		sysOperationLog.setLog_content("司机个人通过微信充值成功！充值金额："+order.getCash()+"，订单号："+order.getOrderNumber()); 
	    			//操作日志
	    			sysOperationLogService.saveOperationLog(sysOperationLog,order.getDebitAccount());
					if (!orderCharge.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)) {
						throw new Exception("消费订单错误：" + orderCharge);
					} else {
						resultStr = getWechatResult();// 返回通知微信支付成功
						String couponId = orderService.queryById(orderId).getCoupon_id();
						//更新优惠券使用状态
						if(couponId!=null && !couponId.equals("")){
							UserCoupon uc = new UserCoupon();
							uc.setUser_coupon_id(couponId);
							uc.setIsuse("1");
							int rs = couponService.updateUserCouponStatus(uc);
						}
						SysOrder sorder = orderService.queryById(orderId);
						//微信消费短信通知
						SysDriver driver = driverService.queryDriverByPK(sorder.getCreditAccount());
						String gasName = gastationService.queryGastationByPK(sorder.getChannelNumber()).getGas_station_name();
						AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
						aliShortMessageBean.setSendNumber(driver.getMobilePhone());
						aliShortMessageBean.setAccountNumber(driver.getMobilePhone());
						aliShortMessageBean.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						aliShortMessageBean.setName(gasName);
						aliShortMessageBean.setString("微信");
						aliShortMessageBean.setMoney(feeCount);
						AliShortMessage.sendShortMessage(aliShortMessageBean, SHORT_MESSAGE_TYPE.APP_CONSUME);
						//APP提示
						sysMessageService.saveMessageTransaction("微信消费", sorder,"2");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("消费订单错误：" + e);
				}
			}

		}
		return resultStr;
	}

	/**
	 * 支付宝在线支付回调方法(充值回调)
	 */
	@RequestMapping(value = "/deal/alipayCallBackPay")
	@ResponseBody
	public String alipayCallBackPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MobileVerification verification = new MobileVerification();
		String feeCount = "";
		String orderId = "";
		String trade_no= "";
		logger.debug("支付宝支付回调获取数据开始");
		try {
			orderId = request.getParameter("out_trade_no");
			trade_no = request.getParameter("trade_no");//支付宝交易号
			feeCount = request.getParameter("total_amount");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(orderId)) {
			logger.debug("订单号为空");
			throw new ServiceException("订单号为空！");
		}

		if (orderId != null && !"".equals(orderId)) {
			MobileReturn result = new MobileReturn();
			result.setStatus(MobileReturn.STATUS_SUCCESS);
			result.setMsg("支付成功！");
			JSONObject resutObj = new JSONObject();
			String resultStr = "";
			// 查询订单内容
			SysOrder order = orderService.selectByPrimaryKey(orderId);
		
			if (order != null && order.getOrderStatus() == 0) {// 当订单状态是初始化时，做状态更新
				// 修改订单状态
				SysOrder sysOrder = new SysOrder();
				sysOrder.setOrderId(orderId);
				sysOrder.setOrderStatus(1);
				sysOrder.setTrade_no(trade_no);
				SysUserAccount account=sysUserAccountService.queryUserAccountByDriverId(order.getDebitAccount());
				//判断是否是第一次充值
				if(!orderService.exisit(order.getDebitAccount())){
					List<SysCashBack> listBack=sysCashBackService.queryForBreak("202");
					if (listBack!=null && listBack.size() > 0) {
						SysCashBack back= listBack.get(0);//获取返现规则
						sysUserAccountService.addCashToAccount(account.getSysUserAccountId(), BigDecimal.valueOf(Double.valueOf(back.getCash_per())), GlobalConstant.OrderType.REGISTER_CASHBACK);
						//添加首次充值订单
						SysOrderDeal newDeal=new SysOrderDeal();
//						orderDealService
						newDeal.setOrderId(orderId);
						newDeal.setDealId(UUID.randomUUID().toString().replaceAll("-", ""));
						newDeal.setDealNumber(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
						newDeal.setDealDate(new Date());
						newDeal.setDealType("202");
						newDeal.setCashBack(new BigDecimal(back.getCash_per()));
						newDeal.setRunSuccess(GlobalConstant.OrderProcessResult.SUCCESS);
						newDeal.setRemark("");
						orderDealService.insert(newDeal);
					}else{
						logger.info("找不到匹配的返现规则，返现失败");
					}
				}
				orderService.updateByPrimaryKey(sysOrder);
				try {
					String orderCharge = orderService.chargeToDriver(order);
          			//系统关键日志记录
        			SysOperationLog sysOperationLog = new SysOperationLog();
        			sysOperationLog.setOperation_type("cz");
        			sysOperationLog.setLog_platform("1");
            		sysOperationLog.setOrder_number(order.getOrderNumber());
            		sysOperationLog.setLog_content("app支付宝在线充值回调成功！充值金额："+order.getCash()+"，订单号为："+order.getOrderNumber()); 
        			//操作日志
        			sysOperationLogService.saveOperationLog(sysOperationLog,order.getDebitAccount());

					if (!orderCharge.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)) {
						throw new Exception("订单充值错误：" + orderCharge);
					} else {
						response.getOutputStream().print("success");// 返回通知支付宝支付成功
						//支付宝充值短信通知
						AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
						aliShortMessageBean.setSendNumber(driverService.queryDriverByPK(orderService.queryById(orderId).getDebitAccount()).getMobilePhone());
						aliShortMessageBean.setAccountNumber(driverService.queryDriverByPK(orderService.queryById(orderId).getDebitAccount()).getMobilePhone());
						aliShortMessageBean.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						aliShortMessageBean.setSpentMoney(feeCount);
						String backCash = String.valueOf(orderService.backCash(orderId));
						aliShortMessageBean.setBackCash(backCash.equals("null")||backCash==null?"0":backCash);
						aliShortMessageBean.setBalance(sysUserAccountService.queryUserAccountByDriverId(driverService.queryDriverByPK(orderService.queryById(orderId).getDebitAccount()).getSysDriverId()).getAccountBalance());
						AliShortMessage.sendShortMessage(aliShortMessageBean, SHORT_MESSAGE_TYPE.DRIVER_CHARGE_BACKCASH);
						//APP提示
						sysMessageService.saveMessageTransaction("支付宝充值", sysOrder,"1");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("订单充值错误：" + e);
				}
			}
		}
		return "";
	}
	/**
	 * 支付宝在线支付回调方法(消费回调)
	 */
	@RequestMapping(value = "/deal/alipayConsum")
	@ResponseBody
	public String alipayConsum(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MobileVerification verification = new MobileVerification();
		String feeCount = "";
		String orderId = "";
		String trade_no= "";
		logger.debug("支付宝支付回调获取数据开始");
		try {
			orderId = request.getParameter("out_trade_no");
			trade_no = request.getParameter("trade_no");//支付宝交易号
			feeCount = request.getParameter("total_amount");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(orderId)) {
			logger.debug("订单号为空");
			throw new ServiceException("订单号为空！");
		}

		if (orderId != null && !"".equals(orderId)) {
			MobileReturn result = new MobileReturn();
			result.setStatus(MobileReturn.STATUS_SUCCESS);
			result.setMsg("消费成功！");
			JSONObject resutObj = new JSONObject();
			String resultStr = "";
			// 查询订单内容
			SysOrder order = orderService.selectByPrimaryKey(orderId);
			if (order != null && order.getOrderStatus() == 0) {// 当订单状态是初始化时，做状态更新
				// 修改订单状态
				SysOrder sysOrder = new SysOrder();
				sysOrder.setOrderId(orderId);
				sysOrder.setOrderStatus(1);
				sysOrder.setTrade_no(trade_no);
				orderService.updateByPrimaryKey(sysOrder);
				SysUserAccount account=sysUserAccountService.queryUserAccountByDriverId(order.getCreditAccount());
				//更新最新余额到账户
				sysUserAccountService.addCashToAccount(account.getSysUserAccountId(), new BigDecimal(feeCount), GlobalConstant.OrderType.CONSUME_BY_DRIVER);
				try {
					String orderCharge = orderService.consumeByDriver(order);
					//系统关键日志记录
	    			SysOperationLog sysOperationLog = new SysOperationLog();
	    			sysOperationLog.setOperation_type("cz");
	    			sysOperationLog.setLog_platform("1");
	        		sysOperationLog.setOrder_number(order.getOrderNumber());
	        		sysOperationLog.setLog_content("司机个人通过支付宝充值成功！充值金额："+order.getCash()+"，订单号为："+order.getOrderNumber()); 
	    			//操作日志
	    			sysOperationLogService.saveOperationLog(sysOperationLog,order.getDebitAccount());
					if (!orderCharge.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS)) {
						throw new Exception("消费订单错误：" + orderCharge);
					} else {
						response.getOutputStream().print("success");// 返回通知支付宝支付成功
						String couponId = orderService.queryById(orderId).getCoupon_id();
						//更新优惠券使用状态
						if(couponId!=null && !couponId.equals("")){
							UserCoupon uc = new UserCoupon();
							uc.setUser_coupon_id(couponId);
							uc.setIsuse("1");
							int rs = couponService.updateUserCouponStatus(uc);
						}
						//支付宝充值短信通知
						SysOrder sorder = orderService.queryById(orderId);
						SysDriver driver = driverService.queryDriverByPK(sorder.getCreditAccount());
						String gasName = gastationService.queryGastationByPK(sorder.getChannelNumber()).getGas_station_name();
						AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
						aliShortMessageBean.setSendNumber(driver.getMobilePhone());
						aliShortMessageBean.setAccountNumber(driver.getMobilePhone());
						aliShortMessageBean.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						aliShortMessageBean.setName(gasName);
						aliShortMessageBean.setString("支付宝");
						aliShortMessageBean.setMoney(feeCount);
						AliShortMessage.sendShortMessage(aliShortMessageBean, SHORT_MESSAGE_TYPE.APP_CONSUME);
						//APP提示
						sysMessageService.saveMessageTransaction("支付宝消费", sorder,"2");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("消费订单错误：" + e);
				}
			}
		}
		return "";
	}

	/**
	 * 退费并保存退费订单(支付宝退费回调)
	 * @param detail_data
	 * @return
	 */
	@RequestMapping("/breakReturn")
	@ResponseBody
	public String breakReturn(HttpServletRequest request, HttpServletResponse response,ModelMap map) {
		String batch_no = "";
		String result_details= "";
		logger.debug("支付宝支付回调获取数据开始");
		SysOrder order=null;
		try {
			batch_no = request.getParameter("batch_no");
			result_details = request.getParameter("result_details");//支付宝返回结果集
		if (StringUtils.isEmpty(result_details)&&StringUtils.isEmpty(batch_no)) {
			logger.debug("支付宝回调返回结果为空");
			throw new ServiceException("支付宝回调返回结果为空！");
		}
		order=orderService.queryByTrade(batch_no);
		String[] dates=result_details.split("#");//第一单
		String[] date=dates[0].split("\\|");//第一笔
		String no=date[0].split("\\^")[0];
		String money=date[0].split("\\^")[1];
		String b=date[0].split("\\^")[2];
			if (b.equalsIgnoreCase("SUCCESS")) {
				MobileReturn result = new MobileReturn();
				result.setStatus(MobileReturn.STATUS_SUCCESS);
				result.setMsg("退款成功！");
				JSONObject resutObj = new JSONObject();
				String resultStr = "";
				// 查询订单内容
				if (order != null && order.getOrderStatus() == 3) {// 当订单状态是初始化时，做状态更新
					// 修改订单状态
					order.setOrderStatus(1);
					order.setOrderRemark("退款成功！");
					order.setOrderDate(new Date());
					order.setBatch_no(batch_no);
					orderService.updateByBatchNo(order);
					SysUserAccount account=sysUserAccountService.queryUserAccountByDriverId(order.getDebitAccount());//初始化钱袋
					if (account==null) {
						logger.error("支付宝返现失败：");
					}
					account.setAccountBalance(account.getAccountBalanceBigDecimal().subtract(new BigDecimal(money)).toString());
					sysUserAccountService.addCashToAccount(account.getSysUserAccountId(),  (new BigDecimal("-"+money)), "230");
				}
			}else{
				order.setOrderStatus(2);
				order.setOrderRemark("退款失败！");
				order.setOrderDate(new Date());
				order.setBatch_no(batch_no);
				orderService.updateByBatchNo(order);
				logger.error("支付宝返现失败："+b);
			}
		} catch (Exception e) {
			logger.error("支付宝返现失败："+e.getLocalizedMessage());
			e.printStackTrace();
		}finally {
			return "";
		}

	}

	/**
	 * 修改账号手机号/密保手机
	 */
	@RequestMapping(value = "/user/updatePhone")
	@ResponseBody
	public String updatePhone(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("修改成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String phoneType = "phoneType";
			String phoneNum = "phoneNum";
			String veCode = "veCode";
			String newPhoneNum = "newPhoneNum";
			String newCode = "newCode";
			boolean b = JsonTool.checkJson(mainObj, phoneType, phoneNum, veCode, newPhoneNum, newCode);
			/**
			 * 请求接口
			 */
			if (b) {
				String payCode = mainObj.optString("payCode");
				SysDriver oldDriver = new SysDriver();
				oldDriver.setMobilePhone(mainObj.optString("phoneNum"));
				SysDriver oldD = driverService.queryDriverByMobilePhone(oldDriver);
				// 创建对象
				SysDriver sysDriver = new SysDriver();
				// 原电话号码赋值
				sysDriver.setMobilePhone(mainObj.optString("phoneNum"));
				// 获取验证码
				String inVeCode = mainObj.optString("veCode");
				newCode = mainObj.optString("newCode");
				veCode = (String) redisClientImpl.getFromCache(sysDriver.getMobilePhone());
				if (veCode != null && !"".equals(veCode) && inVeCode.equals(veCode)) {
					phoneType = mainObj.optString("phoneType");
					// 数据库查询
					List<SysDriver> driver = driverService.queryeSingleList(sysDriver);
					if (!driver.isEmpty()) {
						// 新电话号码
						newPhoneNum = mainObj.optString("newPhoneNum");
						SysDriver temp = new SysDriver();
						temp.setMobilePhone(newPhoneNum);
						SysDriver rs = driverService.queryDriverByMobilePhone(temp);
						Map<String, Object> dataMap = new HashMap<>();
						if(rs!=null && !"".equals(rs)){
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("该手机号已存在！！！");
							dataMap.put("resultVal", "false");
						}else{
							String RnewCode = (String) redisClientImpl.getFromCache(newPhoneNum);
							if(newCode.equals(RnewCode)){
								if(oldD.getPayCode().equals(payCode)){
									// 修改账户手机
									if ("1".equals(phoneType)) {
										sysDriver.setUserName(newPhoneNum);
										sysDriver.setMobilePhone(newPhoneNum);
									} else {
										sysDriver.setSecurityMobilePhone(newPhoneNum);
									}
									sysDriver.setDriverType(driver.get(0).getDriverType());
									sysDriver.setSysDriverId(driver.get(0).getSysDriverId());
									int resultVal = driverService.saveDriver(sysDriver, "update", null, null);
									// 返回大于0，成功
									if (resultVal <= 0) {
										result.setStatus(MobileReturn.STATUS_FAIL);
										result.setMsg("修改账号手机号/密保手机失败！");
									}else{
										dataMap.put("resultVal", "true");
									}
								}else{
									result.setStatus(MobileReturn.STATUS_FAIL);
									result.setMsg("支付密码错误！");
								}
							}else{
								result.setStatus(MobileReturn.STATUS_FAIL);
								result.setMsg("新手机验证码无效！");
							}
						}
						result.setData(dataMap);
					} else {
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("原始电话号码有误！");
					}
				} else {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("验证码无效！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("修改账号手机号/密保手机信息 ： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("修改账号手机号/密保手机失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("修改账号手机号/密保手机失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
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
	public String resetPayCode(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("重置支付密码成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String phoneNum = "phoneNum";
			String veCode = "veCode";
			String newPassword = "newPassword";
			boolean b = JsonTool.checkJson(mainObj, phoneNum, veCode, newPassword);
			/**
			 * 请求接口
			 */
			if (b) {
				newPassword = mainObj.optString("newPassword");
				// 创建对象
				SysDriver sysDriver = new SysDriver();
				// 电话号码赋值
				sysDriver.setUserName(mainObj.optString("phoneNum"));
				veCode = mainObj.optString("veCode");//原手机验证码
				String newCode = mainObj.optString("newCode");//密保手机验证码
				String newPhoneNum = mainObj.optString("newPhoneNum");//密保手机号
				phoneNum = mainObj.optString("phoneNum");//原手机号
				String redisVeCode = (String) redisClientImpl.getFromCache(sysDriver.getUserName());
				//验证密保手机和密保手机短信验证码
				if(newCode !=null && !"".equals(newCode) && newPhoneNum !=null && !"".equals(newPhoneNum)){
					String redisNewCode = (String) redisClientImpl.getFromCache(newPhoneNum);
					if(newCode.equals(redisNewCode)){//验证密保手机短信验证码
						if(phoneNum.equals(newPhoneNum)){//密保手机和原手机相同时，直接修改
							// 数据库查询
							List<SysDriver> driver = driverService.queryeSingleList(sysDriver);
							if (!driver.isEmpty()) {
								String initialPassword = mainObj.optString("newPassword");
								// 初始密码加密、赋值
								sysDriver.setPayCode(initialPassword);
								sysDriver.setSysDriverId(driver.get(0).getSysDriverId());
								// 更新初始密码
								int resultVal = driverService.saveDriver(sysDriver, "update", null, null);
								// 返回大于0，成功
								if (resultVal <= 0) {
									result.setStatus(MobileReturn.STATUS_FAIL);
									result.setMsg("重置支付密码失败！");
								}
								Map<String, Object> dataMap = new HashMap<>();
								dataMap.put("resultVal", "true");
								result.setData(dataMap);
							} else {
								result.setStatus(MobileReturn.STATUS_FAIL);
								result.setMsg("电话号码有误！");
							}
						}else{//密保手机和原手机不同时，验证原手机验证码
							if (veCode.equals(redisVeCode)) {
								// 数据库查询
								List<SysDriver> driver = driverService.queryeSingleList(sysDriver);
								if (!driver.isEmpty()) {
									String initialPassword = mainObj.optString("newPassword");
									// 初始密码加密、赋值
									sysDriver.setPayCode(initialPassword);
									sysDriver.setSysDriverId(driver.get(0).getSysDriverId());
									// 更新初始密码
									int resultVal = driverService.saveDriver(sysDriver, "update", null, null);
									// 返回大于0，成功
									if (resultVal <= 0) {
										result.setStatus(MobileReturn.STATUS_FAIL);
										result.setMsg("重置支付密码失败！");
									}
									Map<String, Object> dataMap = new HashMap<>();
									dataMap.put("resultVal", "true");
									result.setData(dataMap);
								} else {
									result.setStatus(MobileReturn.STATUS_FAIL);
									result.setMsg("电话号码有误！");
								}
							} else {
								result.setStatus(MobileReturn.STATUS_FAIL);
								result.setMsg("原手机验证码无效！");
							}
						}

					}else{
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("密保手机验证码无效！");
					}
				}else{//没有密保手机和密保手机短信验证码的情况，验证原手机和原手机验证码
					if (veCode.equals(redisVeCode)) {
						// 数据库查询
						List<SysDriver> driver = driverService.queryeSingleList(sysDriver);
						if (!driver.isEmpty()) {
							String initialPassword = mainObj.optString("newPassword");
							// 初始密码加密、赋值
							sysDriver.setPayCode(initialPassword);
							sysDriver.setSysDriverId(driver.get(0).getSysDriverId());
							// 更新初始密码
							int resultVal = driverService.saveDriver(sysDriver, "update", null, null);
							// 返回大于0，成功
							if (resultVal <= 0) {
								result.setStatus(MobileReturn.STATUS_FAIL);
								result.setMsg("重置支付密码失败！");
							}
							Map<String, Object> dataMap = new HashMap<>();
							dataMap.put("resultVal", "true");
							result.setData(dataMap);
						} else {
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("电话号码有误！");
						}
					} else {
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("验证码无效！");
					}
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");

			resultStr = resutObj.toString();
			logger.error("重置支付密码信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("重置支付密码失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("重置支付密码失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
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
	public String maxCashBack(String params) {
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
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getSys_cash_back_no().equals("101")) {
					dataMap.put("alipay", list.get(i).getCash_per());
				} else {
					dataMap.put("wechat", list.get(i).getCash_per());
				}
			}
			result.setData(dataMap);
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("查询信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 上报路况
	 */
	@RequestMapping(value = "/road/reportRoadInfo")
	@ResponseBody
	public String reportRoadInfo(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("上报成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String condition_img = "condition_img";
			String conditionType = "conditionType";
			String flashLongitude = "flashLongitude";
			String flashLatitude = "flashLatitude";
			String flashTime = "flashTime";
			String longitude = "longitude";
			String latitude = "latitude";
			String address = "address";
			String publisherName = "publisherName";
			String publisherPhone = "publisherPhone";
			String publisherTime = "publisherTime";
			String direction = "direction";
			boolean b = JsonTool.checkJson(mainObj, token, condition_img, conditionType, flashLongitude, flashLatitude,
					flashTime, longitude, latitude, address, publisherName, publisherPhone, publisherTime, direction);
			/**
			 * 请求接口
			 */
			if (b) {
				// 创建对象
				SysRoadCondition roadCondition = new SysRoadCondition();
				conditionType = mainObj.optString("conditionType");
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date start = sft.parse(mainObj.optString("flashTime"));
				roadCondition.setId(UUIDGenerator.getUUID());
				roadCondition.setConditionImg(mainObj.optString("condition_img"));
				roadCondition.setConditionType(conditionType);
				roadCondition.setCaptureLongitude(mainObj.optString("flashLongitude"));
				roadCondition.setCaptureLatitude(mainObj.optString("flashLatitude"));
				roadCondition.setCaptureTime(start);// 拍照时间
				roadCondition.setStartTime(sft.parse(mainObj.optString("publisherTime")));// 开始时间
				// 计算结束时间01-60min、02-120min、05-240min，其他为null
				Calendar cal = Calendar.getInstance();
				cal.setTime(start);
				if ("01".equals(conditionType)) {
					cal.add(Calendar.HOUR, 1);
					roadCondition.setEndTime(cal.getTime());
				} else if ("02".equals(conditionType)) {
					cal.add(Calendar.HOUR, 2);
					roadCondition.setEndTime(cal.getTime());
				} else if ("05".equals(conditionType)) {
					cal.add(Calendar.HOUR, 4);
					roadCondition.setEndTime(cal.getTime());
				} else {
					roadCondition.setEndTime(null);
				}
				roadCondition.setConditionMsg(mainObj.optString("conditionMsg"));
				roadCondition.setLongitude(mainObj.optString("longitude"));
				roadCondition.setLatitude(mainObj.optString("latitude"));
				roadCondition.setAddress(mainObj.optString("address"));
				roadCondition.setDirection(mainObj.optString("direction"));
				roadCondition.setPublisherName(mainObj.optString("publisherName"));
				roadCondition.setPublisherPhone(mainObj.optString("publisherPhone"));
				roadCondition.setPublisherTime(sft.parse(mainObj.optString("flashTime")));
				int tmp = sysRoadService.reportSysRoadCondition(roadCondition);
				if (tmp > 0) {
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("上报成功！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("上报路况信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("上报路況失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("上报路況失败： " + e);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取路况
	 */
	@RequestMapping(value = "/road/getRoadList")
	@ResponseBody
	public String getRoadList(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("获取路况成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			// 创建对象
			SysRoadCondition roadCondition = new SysRoadCondition();
			boolean b = false;
			if(mainObj != null && !"".equals(mainObj)){
				b=true;
			}
			/**
			 * 请求接口
			 */
			if (b) {
				String longitudeStr = mainObj.optString("longitude");
				String latitudeStr = mainObj.optString("latitude");
				String radius = mainObj.optString("radius");
				if(radius!=null &&!"".equals(radius)){
					if(Integer.parseInt(radius)<100000){
						radius="500000";
					}
				}
				Double longitude = new Double(0);
				Double latitude = new Double(0);
				Double radiusDb = new Double(0);
				roadCondition.setLongitude(longitudeStr);
				roadCondition.setLatitude(latitudeStr);
				roadCondition.setProvince(mainObj.optString("province"));
				roadCondition.setConditionType(mainObj.optString("conditionType"));
				// 获取所有ID(redis中的键为Road+id)
				List<SysRoadCondition> roadIdList = sysRoadService.queryRoadIDList();
				List<SysRoadCondition> redisList = new ArrayList<>();
				for (int i = 0; i < roadIdList.size(); i++) {
					SysRoadCondition sysRoadCondition = (SysRoadCondition) redisClientImpl.getFromCache("Road" + roadIdList.get(i).getId());
					if (sysRoadCondition != null) {
						redisList.add(sysRoadCondition);
					}else{
						SysRoadCondition src = new SysRoadCondition();
						src.setId(roadIdList.get(i).getId());
						src.setConditionStatus("0");
						int rs = sysRoadService.updateByPrimaryKey(src);
						if (rs ==1) {
							logger.info("更新 ID为Road" + roadIdList.get(i).getId()+"的路况状态为失效：成功!!!");
						}else{
							logger.error("更新 ID为Road" + roadIdList.get(i).getId()+"的路况状态为失效：失败!!!");
						}
					}
				}
				List<Map<String, Object>> reChargeList = new ArrayList<>();
				Map<String, Object> reCharge = new HashMap<>();
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String http_poms_path = (String) prop.get("http_poms_path");
				if (redisList != null && redisList.size() > 0) {
					for (SysRoadCondition roadConditionInfo : redisList) {
						Map<String, Object> reChargeMap = new HashMap<>();
						reChargeMap.put("roadId", roadConditionInfo.getId());
						reChargeMap.put("conditionType", roadConditionInfo.getConditionType());
						reChargeMap.put("longitude", roadConditionInfo.getLongitude());
						reChargeMap.put("latitude", roadConditionInfo.getLatitude());
						String url = roadConditionInfo.getConditionImg();
						//没有上传图片添加默认图片
						if(url==null || "".equals(url)){
							url = prop.get("default_img").toString();
						}
						reChargeMap.put("conditionImg", http_poms_path + url);
						reChargeMap.put("address", roadConditionInfo.getAddress());
						reChargeMap.put("publisherName", roadConditionInfo.getPublisherName());
						reChargeMap.put("publisherPhone", roadConditionInfo.getPublisherPhone());
						reChargeMap.put("direction", roadConditionInfo.getDirection());
						reChargeMap.put("conditionMsg", roadConditionInfo.getConditionMsg());
						reChargeMap.put("usefulCount", roadConditionInfo.getUsefulCount());
						reChargeMap.put("contentUrl", http_poms_path + "/portal/crm/help/trafficDetail?trafficId="
								+ roadConditionInfo.getId());
						reChargeMap.put("shareUrl", http_poms_path + "/portal/crm/help/trafficShare?trafficId="
								+ roadConditionInfo.getId());
						String publisherTime = "";
						if (roadConditionInfo.getPublisherTime() != null
								&& !"".equals(roadConditionInfo.getPublisherTime().toString())) {
							publisherTime = sft.format(roadConditionInfo.getPublisherTime());
						} else {
							publisherTime = sft.format(new Date());
						}
						reChargeMap.put("publisherTime", publisherTime);
						if (longitudeStr != null && !"".equals(longitudeStr) && latitudeStr != null
								&& !"".equals(latitudeStr) && radius != null && !"".equals(radius)) {
							longitude = new Double(longitudeStr);
							latitude = new Double(latitudeStr);
							radiusDb = new Double(radius);

							String longStr = roadConditionInfo.getLongitude();
							String langStr = roadConditionInfo.getLatitude();
							Double longDb = new Double(0);
							Double langDb = new Double(0);
							if (longStr != null && !"".equals(longStr) && langStr != null && !"".equals(langStr)) {
								longDb = new Double(longStr);
								langDb = new Double(langStr);
							}
							// 计算当前加注站离指定坐标距离
							Double dist = DistCnvter.getDistance(longitude, latitude, longDb, langDb);
							if (dist <= radiusDb) {// 在指定范围内
								reChargeList.add(reChargeMap);
							}
						} else {// 目标坐标及范围半径未传参
							reChargeList.add(reChargeMap);
						}
					}
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					reCharge.put("listMap", reChargeList);
				} else {
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					reCharge.put("listMap", new ArrayList<>());
				}
				result.setListMap(reChargeList);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("获取路况信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取路况失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取路况失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 路况有用统计
	 */
	@RequestMapping(value = "/road/updateUseful")
	@ResponseBody
	public String updateUseful(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("统计成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String roadId = "roadId";
			String token = "token";
			boolean b = JsonTool.checkJson(mainObj, roadId, token);
			/**
			 * 请求接口
			 */
			if (b) {
				MbStatistics mbStatistics = new MbStatistics();
				mbStatistics.setSysDriverId(mainObj.optString("token"));
				mbStatistics.setOperationType("3");
				mbStatistics.setContentType("1");
				mbStatistics.setContentId(mainObj.optString("roadId"));
				// 判断是否点过有用
				MbStatistics temp = mbStatisticsService.queryMbStatistics(mbStatistics);
				if (temp == null) {
					SysRoadCondition roadCondition = sysRoadService.selectByPrimaryKey(mainObj.optString("roadId"));
					if (roadCondition != null) {
						int count = Integer.parseInt(roadCondition.getUsefulCount());
						roadCondition.setUsefulCount(String.valueOf(count + 1));
						roadCondition.setId(mainObj.optString("roadId"));
						int rs = sysRoadService.updateByPrimaryKey(roadCondition);
						if (rs > 0) {
							result.setStatus(MobileReturn.STATUS_SUCCESS);
							result.setMsg("统计成功！");
							Usysparam param= usysparamService.queryUsysparamByCode("CONDITION_TYPE", roadCondition.getConditionType());
							int time = SysRoadController.sumTime(roadCondition,Integer.valueOf(param.getData()));
							redisClientImpl.addToCache("Road" + roadCondition.getId(), roadCondition, time);
							Map<String, Object> dataMap = new HashMap<>();
							dataMap.put("count", roadCondition.getUsefulCount());
							result.setData(dataMap);
							// 添加记录信息
							mbStatistics.setMbStatisticsId(UUIDGenerator.getUUID());
							int rs1 = mbStatisticsService.insertSelective(mbStatistics);
							if (rs1 > 0) {
								logger.error("记录点赞成功");
							} else {
								logger.error("记录点赞失败");
							}
						} else {
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("统计失败！");
						}
					} else {
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("无此路况！");
					}
				} else {
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("已统计过啦！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("统计信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("统计失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("统计失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 取消路况
	 */
	@RequestMapping(value = "/road/cancelRoadInfo")
	@ResponseBody
	public String cancelRoadInfo(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("取消路况提交成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String roadId = "roadId";
			String token = "token";
			String condition_img = "condition_img";
			String conditionType = "conditionType";
			String flashLongitude = "flashLongitude";
			String flashLatitude = "flashLatitude";
			String flashTime = "flashTime";
			String longitude = "longitude";
			String latitude = "latitude";
			String address = "address";
			String publisherName = "publisherName";
			String publisherPhone = "publisherPhone";
			String publisherTime = "publisherTime";
			boolean b = JsonTool.checkJson(mainObj, roadId, token, condition_img, conditionType, flashLongitude,
					flashLatitude, flashTime, longitude, latitude, address, publisherName, publisherPhone,
					publisherTime);
			/**
			 * 请求接口
			 */
			if (b) {
				// 创建对象
				SysRoadCondition roadCondition = new SysRoadCondition();
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				roadCondition.setId(UUIDGenerator.getUUID());
				roadCondition.setConditionImg(mainObj.optString("condition_img"));
				roadCondition.setConditionType(mainObj.optString("conditionType"));
				roadCondition.setCaptureLongitude(mainObj.optString("flashLongitude"));
				roadCondition.setCaptureLatitude(mainObj.optString("flashLatitude"));
				roadCondition.setCaptureTime(sft.parse(mainObj.optString("flashTime")));
				roadCondition.setConditionMsg(mainObj.optString("conditionMsg"));
				roadCondition.setLongitude(mainObj.optString("longitude"));
				roadCondition.setLatitude(mainObj.optString("latitude"));
				roadCondition.setAddress(mainObj.optString("address"));
				roadCondition.setPublisherName(mainObj.optString("publisherName"));
				roadCondition.setPublisherPhone(mainObj.optString("publisherPhone"));
				roadCondition.setPublisherTime(sft.parse(mainObj.optString("publisherTime")));
				roadCondition.setRoadId(mainObj.optString("roadId"));
				int tmp = sysRoadService.cancelSysRoadCondition(roadCondition);
				if (tmp > 0) {
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("取消路况提交成功！");
					// 添加记录
					MbStatistics mbStatistics = new MbStatistics();
					mbStatistics.setSysDriverId(mainObj.optString("token"));
					mbStatistics.setOperationType("4");
					mbStatistics.setContentType("1");
					mbStatistics.setContentId(mainObj.optString("roadId"));
					// 判断是否点过
					MbStatistics temp = mbStatisticsService.queryMbStatistics(mbStatistics);
					if (temp == null) {
						mbStatistics.setMbStatisticsId(UUIDGenerator.getUUID());
						int rs1 = mbStatisticsService.insertSelective(mbStatistics);
						if (rs1 > 0) {
							logger.error("记录取消成功");
						} else {
							logger.error("记录取消失败");
						}
					} else {
						result.setStatus(MobileReturn.STATUS_SUCCESS);
						result.setMsg("已提交过啦！");
					}
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("取消路况提交信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("取消路况提交失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("取消路况提交失败： " + e);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取实名认证信息
	 */
	@RequestMapping(value = "/user/getRealNameAuth")
	@ResponseBody
	public String getRealNameAuth(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("获取成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			boolean b = JsonTool.checkJson(mainObj, token);
			/**
			 * 请求接口
			 */
			if (b) {
				// 创建对象
				SysDriver driver = driverService.queryDriverByPK(mainObj.optString("token"));
				if (driver != null) {
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("获取实名认证信息成功！");
					String gasType = "";
					if (!"".equals(driver.getFuelType())) {
						List<Usysparam> list = usysparamService.query("FUEL_TYPE", driver.getFuelType());
						if (list != null && list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								if (driver.getFuelType().equals(list.get(i).getMcode())) {
									gasType = list.get(i).getMname();
								}
							}
						}
					}
					SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Map<String, Object> dataMap = new HashMap<>();
					String url = (String) prop.get("http_poms_path");
					String vehicleLice = "";// 驾驶证
					String drivingLice = "";// 行驶证
					if (driver.getVehicleLice() == null || "".equals(driver.getVehicleLice())) {
						vehicleLice = "";
					} else {
						if (driver.getVehicleLice().indexOf("http") != -1) {
							vehicleLice = driver.getVehicleLice();
						} else {
							vehicleLice = url + driver.getVehicleLice();
						}
					}
					if (driver.getDrivingLice() == null || "".equals(driver.getDrivingLice())) {
						drivingLice = "";
					} else {
						if (driver.getDrivingLice().indexOf("http") != -1) {
							drivingLice = driver.getDrivingLice();
						} else {
							drivingLice = url + driver.getDrivingLice();
						}
					}
					dataMap.put("name", driver.getFullName());
					dataMap.put("plateNumber", driver.getPlateNumber());
					dataMap.put("gasType", gasType);// 燃气类型字典表
					dataMap.put("endTime", sft.format(driver.getExpiryDate()));
					dataMap.put("drivingLicenseImageUrl", drivingLice);
					dataMap.put("driverLicenseImageUrl", vehicleLice);
					dataMap.put("idCard", driver.getIdentityCard());
					result.setData(dataMap);
				} else {
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("无此用户！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("获取实名认证信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取消息列表
	 */
	@RequestMapping(value = "/msg/getMsgList")
	@ResponseBody
	public String getMsgList(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("获取成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String msgType = "msgType";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj, msgType, pageNum, pageSize);
			/**
			 * 请求接口
			 */
			if (b) {
				pageNum = mainObj.optString("pageNum");
				pageSize = mainObj.optString("pageSize");
				msgType = mainObj.optString("msgType");
				SysMessage sysMessage = new SysMessage();
				String province = mainObj.optString("province");
				if ("2".equals(msgType)) {
					if ("全国".equals(province)) {
						province = "";
					}
					sysMessage.setProvince_name(province);
				}
				sysMessage.setMessageType(Integer.valueOf(msgType));
				sysMessage.setPageNum(Integer.valueOf(pageNum));
				sysMessage.setPageSize(Integer.valueOf(pageSize));
				sysMessage.setOrderby("message_send_time desc");
				PageInfo<Map<String, Object>> pageInfo = sysMessageService.queryMsgListForPage(sysMessage);
				List<Map<String, Object>> reChargeList = new ArrayList<>();
				Map<String, Object> reCharge = new HashMap<>();
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:mm");
				if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {
					for (Map<String, Object> map : pageInfo.getList()) {
						Map<String, Object> reChargeMap = new HashMap<>();
						reChargeMap.put("messageTitle", map.get("messageTitle"));
						reChargeMap.put("messageBody", map.get("messageBody"));
						reChargeMap.put("messageTicker", map.get("messageTicker"));
						reChargeMap.put("messageType", map.get("messageType").toString());
						reChargeMap.put("createdTime", sft.format(map.get("messageCreatedTime")));
						reChargeList.add(reChargeMap);
					}
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					reCharge.put("listMap", reChargeList);
				} else {
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					reCharge.put("listMap", new ArrayList<>());
				}
				result.setListMap(reChargeList);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("获取信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 记录统计数据
	 */
	@RequestMapping(value = "/statistics/record")
	@ResponseBody
	public String record(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("操作成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String id = "id";
			String operation = "operation";
			String type = "type";
			boolean b = JsonTool.checkJson(mainObj, id, operation, type);
			/**
			 * 请求接口
			 */
			if (b) {
				id = mainObj.optString("id");
				operation = mainObj.optString("operation");
				type = mainObj.optString("type");
				int rs;
				if ("1".equals(type)) {// 路况
					SysRoadCondition sysRoadCondition = sysRoadService.selectByPrimaryKey(id);
					sysRoadCondition.setId(sysRoadCondition.getId());
					if ("1".equals(operation)) {// 阅读
						SysRoadCondition sysRoad = new SysRoadCondition();
						String viewCount = sysRoadCondition.getViewCount();
						int viewCountInt = Integer.parseInt(viewCount) + 1;
						sysRoad.setViewCount(viewCountInt+"");
						sysRoad.setId(sysRoadCondition.getId());
						rs = sysRoadService.updateByPrimaryKey(sysRoad);
						if (rs > 0) {
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						} else {
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					} else {// 分享
						String shareCount = sysRoadCondition.getShareCount();
						SysRoadCondition sysRoad = new SysRoadCondition();
						int shareCountInt = Integer.parseInt(shareCount) + 1;
						sysRoad.setId(sysRoadCondition.getId());
						sysRoad.setShareCount(shareCountInt+"");
						rs = sysRoadService.updateByPrimaryKey(sysRoad);
						if (rs > 0) {
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						} else {
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					}
				} else if ("2".equals(type)) {// 商家
					Gastation gastation = gastationService.queryGastationByPK(id);
					gastation.setSys_user_account_id(gastation.getSys_gas_station_id());
					if ("1".equals(operation)) {// 阅读
						String viewCount = gastation.getViewCount();
						viewCount = String.valueOf(Integer.parseInt(viewCount) + 1);
						gastation.setViewCount(viewCount);
						rs = gastationService.updateByPrimaryKeySelective(gastation);
						if (rs > 0) {
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						} else {
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					} else {// 分享
						String shareCount = gastation.getShareCount();
						shareCount = String.valueOf(Integer.parseInt(shareCount) + 1);
						gastation.setShareCount(shareCount);
						rs = gastationService.updateByPrimaryKeySelective(gastation);
						if (rs > 0) {
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						} else {
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					}
				} else if ("3".equals(type)) {// 活动
					MbBanner mbBanner = mbBannerService.selectByPrimaryKey(id);
					mbBanner.setMbBannerId(mbBanner.getMbBannerId());
					if ("1".equals(operation)) {// 阅读
						String viewCount = mbBanner.getViewCount();
						viewCount = String.valueOf(Integer.parseInt(viewCount) + 1);
						mbBanner.setViewCount(viewCount);
						rs = mbBannerService.updateBanner(mbBanner);
						if (rs > 0) {
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						} else {
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					} else {// 分享
						String shareCount = mbBanner.getShareCount();
						shareCount = String.valueOf(Integer.parseInt(shareCount) + 1);
						mbBanner.setShareCount(shareCount);
						rs = mbBannerService.updateBanner(mbBanner);
						if (rs > 0) {
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						} else {
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					}
				} else {
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("类型有误！(1路况、2活动、3商家)");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("记录统计数据信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("操作失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("记录统计数据： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取邀请用户页面信息
	 */
	@RequestMapping(value = "/user/invitation")
	@ResponseBody
	public String invitation(String params, HttpServletRequest request) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("操作成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			boolean b = JsonTool.checkJson(mainObj, token);
			/**
			 * 请求接口
			 */

			if (b) {
				Map<String, Object> tokenMap = new HashMap<>();
				token = mainObj.optString("token");
				String http_poms_path = (String) prop.get("http_poms_path");
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ path;
				String inviteUrl = basePath + "/portal/crm/help/user/invitation?token=" + token;
				/* 生成短连接 */
				String shortStr = "";
				String shortUrl = "http://api.t.sina.com.cn/short_url/shorten.json?source=3271760578&url_long="
						+ inviteUrl;
				try {
					shortStr = HttpUtil.sendGet(shortUrl, "UTF-8");
					JSONArray array = new JSONArray();
					if (shortStr != null) {
						array = JSONArray.fromObject(shortStr);
						JSONObject object = array.getJSONObject(0);
						if (object != null && object.get("url_short") != null) {
							shortStr = object.getString("url_short");
						} else {
							shortStr = inviteUrl;
						}
					} else {
						shortStr = inviteUrl;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				tokenMap.put("inviteContent", "将此链接或邀请码分享给好友，好友通过您的邀请链接或邀请码完成注册并登录后，您的账户即可获得￥10现金充值。");
				tokenMap.put("msgContent",
						"司集专为3000多万卡车司机提供导航、实时路况、气站、油站、会员及周边服务，注册成功之后您的账户即可获得￥10现金充值，详情请访问：" + shortStr);
				tokenMap.put("title", "注册即享司集现金充值");
				tokenMap.put("content", "司集专为3000多万卡车司机提供导航、实时路况、气站、油站、会员及周边服务，完成注册并下载司集APP，您即可获得￥10账户充值，可在任意司集联盟站使用！");
				tokenMap.put("imgUrl", "默认图片路径");
				result.setData(tokenMap);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取获取APP版本信息
	 */
	@RequestMapping(value = "/util/getAPPInfo")
	@ResponseBody
	public String getAPPInfo() {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		JSONObject resutObj = new JSONObject();
		result.setMsg("获取成功！");
		String resultStr = "";
		try {
			MbAppVersion appVersion = mbAppVersionService.queryNewest();
			if(appVersion!=null){
				Map<String, Object> tokenMap = new HashMap<>();
				String localPath = (String) prop.get("http_poms_path");
				tokenMap.put("lastVersion", appVersion.getCode());//对内版本号
				tokenMap.put("downUrl", localPath + appVersion.getUrl());
				tokenMap.put("isUpdate", appVersion.getIsUpdate());
				tokenMap.put("appSize", appVersion.getAppSize());
				tokenMap.put("remark", appVersion.getRemark());
				result.setData(tokenMap);
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("暂无版本！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}
	
	/**
	 * 获取折扣信息
	 */
	@RequestMapping(value = "/deal/getDiscount")
	@ResponseBody
	public String getDiscount(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("操作成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String gastationId = "gastationId";
			String amount = "amount";
			boolean b = JsonTool.checkJson(mainObj, gastationId);
			/**
			 * 请求接口
			 */
			if (b) {
				List<Map<String, Object>> reChargeList = new ArrayList<>();
				gastationId = mainObj.optString("gastationId");
				amount = mainObj.optString("amount");
				List<Map<String, Object>> gsGasPriceList = gsGasPriceService.queryDiscount(gastationId);
				String gasName = null;
				if(gsGasPriceList!=null&&gsGasPriceList.size()>0){
//					List<BigDecimal> priceList = new ArrayList<BigDecimal>();
//					for (Map<String, Object> map : gsGasPriceList) {
//						gasName = usysparamService.query("CARDTYPE",map.get("gas_name").toString()).get(0).getMname();
//						if(gasName.indexOf("LNG")!=-1 || gasName.indexOf("lng")!=-1){
//							//获取最高单价
//							String gasPrice = map.get("product_price").toString();
//							priceList.add(new BigDecimal(gasPrice));
//						}
//					}
					//重排获取最高价格
//					Collections.sort(priceList);
//					String gasPrice = priceList.get(priceList.size()-1).toString();
					//通过价格获取气品名称及优惠规则
//					GsGasPrice ggp = gsGasPriceService.queryGsPrice(gastationId, gasPrice);
					Map<String, Object> reChargeMap = new HashMap<>();
					Object preferentialType = gsGasPriceList.get(0).get("preferential_type");//折扣类型
					String type;
					String discountAmount = "0";
					if(preferentialType!=null && !"".equals(preferentialType)){
						discountAmount = gsGasPriceList.get(0).get("minus_money")==null?gsGasPriceList.get(0).get("fixed_discount").toString():gsGasPriceList.get(0).get("minus_money").toString();
						type = preferentialType.toString();
					}else{
						type="";
					}
					gasName = usysparamService.query("CARDTYPE",gsGasPriceList.get(0).get("gas_name").toString()).get(0).getMname();
					double cashBack = 0;
					String priceUnit = usysparamService.query("GAS_UNIT", gsGasPriceList.get(0).get("unit").toString()).get(0).getMname();
					//加气总量
					BigDecimal gasTotal = new BigDecimal(amount).divide(new BigDecimal(gsGasPriceList.get(0).get("product_price").toString()),2, RoundingMode.HALF_UP) ;
					//没有折扣信息
					if("".equals(type)){
						//优惠总金额(消费总金额乘以折扣)
						reChargeMap.put("cashBack",cashBack);
						reChargeMap.put("preferential_type",preferentialType );
						reChargeMap.put("gasName",gasName);
						reChargeMap.put("remark", gsGasPriceList.get(0).get("remark"));
						reChargeMap.put("gasPrice",gsGasPriceList.get(0).get("product_price"));
						reChargeMap.put("priceUnit",priceUnit);
						reChargeMap.put("discountAmount",discountAmount);
						reChargeMap.put("gasTotal",gasTotal);
						reChargeList.add(reChargeMap);
					}else if(type.equals("1")){//固定折扣(整单折扣)
						//优惠总金额(消费总金额乘以折扣)
						cashBack = new BigDecimal(amount).subtract(new BigDecimal(amount).multiply(new BigDecimal(discountAmount))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						reChargeMap.put("cashBack",cashBack);
						reChargeMap.put("preferential_type",preferentialType );
						reChargeMap.put("gasName",gasName);
						reChargeMap.put("remark", gsGasPriceList.get(0).get("remark"));
						reChargeMap.put("gasPrice",gsGasPriceList.get(0).get("product_price"));
						reChargeMap.put("priceUnit",priceUnit);
						reChargeMap.put("discountAmount",discountAmount);
						reChargeMap.put("gasTotal",gasTotal);
						reChargeList.add(reChargeMap);
					}else{//0立减金额(单价立减)
						//优惠金额(单价减去立减金额乘以加气总量)
						cashBack = gasTotal.multiply(new BigDecimal(discountAmount)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();;
						reChargeMap.put("cashBack",cashBack);
						reChargeMap.put("preferential_type",preferentialType );
						reChargeMap.put("gasName",gasName);
						reChargeMap.put("remark", gsGasPriceList.get(0).get("remark"));
						reChargeMap.put("gasPrice",gsGasPriceList.get(0).get("product_price"));
						reChargeMap.put("priceUnit", priceUnit);
						reChargeMap.put("discountAmount",discountAmount);
						reChargeMap.put("gasTotal",gasTotal);
						reChargeList.add(reChargeMap);
					}
				}else{
					result.setMsg("暂无折扣信息");
				}
				result.setListMap(reChargeList);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取折扣信息失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取折扣信息失败： " + e);
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}
	/**
	 * 获取优惠券信息
	 */
	@RequestMapping(value = "/deal/getCoupon")
	@ResponseBody
	public String getCoupon(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("获取优惠券信息成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj, token,pageNum,pageSize);
			/**
			 * 请求接口
			 */
			if (b) {
				String gastationId = mainObj.optString("gastationId");
				String amount = mainObj.optString("amount");
				String driverId = mainObj.optString("token");
				pageNum = mainObj.optString("pageNum");
				pageSize = mainObj.optString("pageSize");
				Coupon coupon = new Coupon();
				coupon.setPageNum(Integer.valueOf(pageNum));
				coupon.setPageSize(Integer.valueOf(pageSize));
				List<Map<String, Object>> reChargeList = new ArrayList<>();
				String useCondition =null;
				String couponKind =null;
				//当加注站ID和消费金额不为空时，返回当前用户可用优惠券列表，按金额倒叙排列，分页。
				if(gastationId!=null&&!"".endsWith(gastationId)&&amount!=null&&!"".equals(amount)){
					coupon.setSys_gas_station_id(gastationId);
					coupon.setDriverId(driverId);
					coupon.setAmount(amount);
					PageInfo<Coupon> pageInfo = couponService.queryCouponOrderByAmount(coupon);
					if(pageInfo.getList()!=null&&pageInfo.getList().size()>0){
						for (Coupon data : pageInfo.getList()) {
							Map<String, Object> reChargeMap = new HashMap<>();
							reChargeMap.put("couponId",data.getCoupon_id());
							couponKind = data.getCoupon_kind();
							if(couponKind.equals("2")){
								reChargeMap.put("gasStationName",data.getGas_station_name());
							}
							reChargeMap.put("couponKind", couponKind);
							reChargeMap.put("couponType", data.getCoupon_type());
							useCondition = data.getUse_condition();
							reChargeMap.put("useCondition",useCondition);
							reChargeMap.put("preferentialDiscount",data.getPreferential_discount());
							reChargeMap.put("title",data.getCoupon_title());
							if(useCondition.equals("1")){
								reChargeMap.put("limit_money",data.getLimit_money());
							}
							reChargeMap.put("startTime",data.getStart_coupon_time().substring(0, 19));
							reChargeMap.put("endTime",data.getEnd_coupon_time().substring(0, 19));
							reChargeList.add(reChargeMap);
						}
					}else{
						result.setMsg("暂无优惠券！");
					}
				}else{//当加注站ID和消费金额为空时，返回当前用户所有优惠券列表，按金额倒叙排列，分页。
					PageInfo<Coupon> pageInfo = couponService.queryAllCouponForPage(coupon,driverId);
					if(pageInfo.getList()!=null&&pageInfo.getList().size()>0){
						for (Coupon data : pageInfo.getList()) {
							Map<String, Object> reChargeMap = new HashMap<>();
							reChargeMap.put("couponId",data.getCoupon_id());
							couponKind = data.getCoupon_kind();
							if(couponKind.equals("2")){
								reChargeMap.put("gasStationName",data.getGas_station_name());
							}
							reChargeMap.put("couponKind", couponKind);
							reChargeMap.put("couponType", data.getCoupon_type());
							reChargeMap.put("title",data.getCoupon_title());
							useCondition = data.getUse_condition();
							reChargeMap.put("useCondition", useCondition);
							if(useCondition.equals("1")){
								reChargeMap.put("limit_money",data.getLimit_money());
							}
							reChargeMap.put("preferentialDiscount",data.getPreferential_discount());
							reChargeMap.put("startTime",data.getStart_coupon_time().substring(0, 19));
							reChargeMap.put("endTime",data.getEnd_coupon_time().substring(0, 19));
							reChargeList.add(reChargeMap);
						}
					}else{
						result.setMsg("暂无优惠券！");
					}
				}
				result.setListMap(reChargeList);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取优惠券信息失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取优惠券信息失败： " + e);
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}
	/**
	 * 获取消费订单详情
	 */
	@RequestMapping(value = "/deal/getOrderInfo")
	@ResponseBody
	public String getOrderInfo(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("获取消费订单详情成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String orderId = "orderId";
			boolean b = JsonTool.checkJson(mainObj,orderId);
			if(b){
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				orderId = mainObj.optString("orderId");
				SysOrder order = orderService.queryById(orderId);
				Map<String, Object> tokenMap = new HashMap<>();
				tokenMap.put("cash",order.getCash());
				tokenMap.put("gastationName",order.getGas_station_name());
				tokenMap.put("orderStatus",order.getOrderStatus());
				tokenMap.put("dealTime",sft.format(order.getOrderDate()));
				String Spend_type = order.getSpend_type();
				if(Spend_type.equals("C01")){
					Spend_type ="卡余额消费";
				}else if(Spend_type.equals("C02")){
					Spend_type ="POS消费";
				}else if(Spend_type.equals("C03")){
					Spend_type ="微信消费";
				}else if(Spend_type.equals("C04")){
					Spend_type ="支付宝消费";
				}
				tokenMap.put("chargeType",Spend_type);
				tokenMap.put("orderId",orderId);
				tokenMap.put("orderNum",order.getOrderNumber());
				tokenMap.put("gastationId",order.getChannelNumber());
				tokenMap.put("payment",order.getShould_payment());
				BigDecimal cash = new BigDecimal(0);
				if(order.getPreferential_cash()!=null && !"".equals(order.getPreferential_cash())){
					cash = cash.add(new BigDecimal(order.getPreferential_cash().toString()));
				}
				if(order.getCoupon_cash() !=null && !"".equals(order.getCoupon_cash())){
					cash = cash.add(new BigDecimal(order.getCoupon_cash().toString()));
				}
				tokenMap.put("preferentialCash",cash);
				result.setData(tokenMap);
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("暂无消费订单！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取消费订单详情失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取消费订单详情失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}
	
	/**
	 * 账户余额消费
	 */
	@RequestMapping(value = "/deal/accountSpend")
	@ResponseBody
	public String accountSpend(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("订单支付成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			String payableAmount = "payableAmount";
			String amount = "amount";
			String gastationId = "gastationId";
			String payCode = "payCode";
			String gasTotal = "gasTotal";
			boolean b = JsonTool.checkJson(mainObj,token,payableAmount,amount,gastationId,payCode,gasTotal);
			if(b){
				payCode = mainObj.optString("payCode");
				token = mainObj.optString("token");
				gasTotal = mainObj.optString("gasTotal");
				gastationId = mainObj.optString("gastationId");
				SysDriver driver = driverService.queryDriverByPK(token);
				Gastation gas = gastationService.queryGastationByPK(gastationId);
				String driverPayCode = driver.getPayCode();
				Map<String, Object> data = new HashedMap();
				if(payCode.equals(driverPayCode)){
					String couponId = mainObj.optString("couponId");
					String couponCash = mainObj.optString("couponCash");
					String orderID = UUIDGenerator.getUUID();
					amount = mainObj.optString("amount");
					gastationId = mainObj.optString("gastationId");
					payableAmount = mainObj.optString("payableAmount");
					//设置平台优惠金额
					BigDecimal preferential_cash = new BigDecimal(0);
					preferential_cash = new BigDecimal(payableAmount).subtract(new BigDecimal(amount));//总优惠金额
						SysOrder sysOrder = createNewOrder(orderID, token, amount, GlobalConstant.OrderChargeType.APP_CONSUME_CHARGE,GlobalConstant.ORDER_SPEND_TYPE.CASH_BOX,"2","C01"); // TODO充值成功后再去生成订单
						//设置优惠券ID
						if(couponId!=null && !"".equals(couponId)){
							UserCoupon uc = new UserCoupon();
							uc.setCoupon_id(couponId);
							uc.setSys_driver_id(token);
							sysOrder.setCoupon_number(couponService.queryUserCouponId(uc));
						}
						//设置优惠金额
						if(couponCash!=null && !"".equals(couponCash)){
							sysOrder.setCoupon_cash(new BigDecimal(couponCash));
							preferential_cash = preferential_cash.subtract(new BigDecimal(couponCash));//总优惠金额减去优惠券优惠金额为平台优惠金额
						}
						//设置气站ID
						sysOrder.setChannelNumber(gastationId);
						sysOrder.setChannel("APP-余额消费-"+gas.getGas_station_name());
						//设置实付金额
						sysOrder.setCash(new BigDecimal(amount));
						//设置应付金额
						sysOrder.setShould_payment(new BigDecimal(payableAmount));
						sysOrder.setPreferential_cash(preferential_cash);
						//订单状态
						sysOrder.setOrderStatus(1);
						if (sysOrder != null) {
							int nCreateOrder = orderService.insert(sysOrder, null);
							if (nCreateOrder < 1){
								throw new Exception("订单生成错误：" + sysOrder.getOrderId());
							}else{
								orderService.consumeByDriver(sysOrder);
								//系统关键日志记录
				    			SysOperationLog sysOperationLog = new SysOperationLog();
				    			sysOperationLog.setOperation_type("xf");
				    			sysOperationLog.setLog_platform("1");
				        		sysOperationLog.setOrder_number(sysOrder.getOrderNumber());
				        		sysOperationLog.setLog_content("司机个人通过账户消费"+sysOrder.getCash()+"元！订单号为："+sysOrder.getOrderNumber()); 
				    			//操作日志
				    			sysOperationLogService.saveOperationLog(sysOperationLog,token);
								data.put("orderId", orderID);
								data.put("orderNum", sysOrder.getOrderNumber());
								//更新优惠券使用状态
								if(couponId!=null && !couponId.equals("")){
									UserCoupon uc = new UserCoupon();
									uc.setUser_coupon_id(couponId);
									uc.setIsuse("1");
									int rs = couponService.updateUserCouponStatus(uc);
								}
								//添加OrderGoods信息
								List<Map<String, Object>> gsGasPriceList = gsGasPriceService.queryDiscount(gastationId);
								SysOrderGoods orderGoods = new SysOrderGoods ();
								//ID
								orderGoods.setOrderGoodsId(UUIDGenerator.getUUID());
								//orderID
								orderGoods.setOrderId(orderID);
								//原始单价
								orderGoods.setPrice(new BigDecimal(gsGasPriceList.get(0).get("product_price").toString()));
								//加气总量
								orderGoods.setNumber(Double.valueOf(gasTotal));
								//商品总价
								orderGoods.setSumPrice(new BigDecimal(payableAmount));
								//商品类型
								orderGoods.setGoodsType(gsGasPriceList.get(0).get("gas_name").toString());
								//优惠类型。
								orderGoods.setPreferential_type(gsGasPriceList.get(0).get("preferential_type").toString());
								//平台优惠金额
								orderGoods.setDiscountSumPrice(preferential_cash);
								int rs = sysOrderGoodsService.saveOrderGoods(orderGoods);
								if(rs > 0 ){
									//余额消费短信通知
									AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
									/*SysOrder sorder = orderService.queryById(orderID);
									SysDriver sdriver = driverService.queryDriverByPK(sorder.getCreditAccount());
									String gasName = gastationService.queryGastationByPK(sorder.getChannelNumber()).getGas_station_name();*/
									String gasName = gas.getGas_station_name();
									aliShortMessageBean.setSendNumber(driver.getMobilePhone());
									aliShortMessageBean.setAccountNumber(driver.getMobilePhone());
									aliShortMessageBean.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
									aliShortMessageBean.setName(gasName);
									aliShortMessageBean.setString("账户余额");
									aliShortMessageBean.setMoney(amount);
									AliShortMessage.sendShortMessage(aliShortMessageBean, SHORT_MESSAGE_TYPE.APP_CONSUME);
									//APP提示
									sysMessageService.saveMessageTransaction("余额消费", sysOrder,"2");
								}else{
									throw new Exception("orderGoods信息添加失败");
								}
							}
						}
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("支付密码错误");
				}
				result.setData(data);
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("订单支付失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("订单支付情失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取加注站信息列表(默认时按距离置顶两个联盟站，按距离和价格时不置顶)
	 */
	@RequestMapping(value = "/station/getStationList")
	@ResponseBody
	public String getStationList(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("获取加注站信息成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String longitude = "longitude";
			String latitude = "latitude";
			String sortType = "sortType";
			String infoType = "infoType";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj,longitude,latitude,sortType,infoType,pageNum,pageSize);
			if(b){
				longitude = mainObj.getString("longitude");
				latitude = mainObj.getString("latitude");
				sortType =  mainObj.getString("sortType");//排序类型 1默认2 距离 3 价格(默认时按距离置顶两个联盟站，按距离和价格时不置顶)
				List<Map<String, Object>> gastationArray = new ArrayList<>();
				Double longIn = new Double(longitude);
				Double langIn = new Double(latitude);
				int pageNumIn = mainObj.optInt("pageNum");
				int pageSizeIn = mainObj.optInt("pageSize");
				Gastation gastation = new Gastation();
				//获取所有数据
				List<Gastation> gastationAllList = gastationService.getAllStationList(gastation);
				//默认排序(置顶两个联盟站)
				if(sortType.equals("1")){
					gastationArray = defaultList(gastationAllList,longIn,langIn,pageNumIn,pageSizeIn);
					if(gastationArray!=null && gastationArray.size() > 0 ){
						result.setListMap(gastationArray);
					}else{
						result.setStatus(MobileReturn.STATUS_SUCCESS);
						result.setMsg("暂无数据！");
						result.setListMap(new ArrayList<Map<String, Object>>());
					}
				//按距离排序
				}else if(sortType.equals("2")){
					gastationArray = orderByDistanceList(gastationAllList,longIn,langIn,pageNumIn,pageSizeIn);
					if(gastationArray!=null && gastationArray.size() > 0 ){
						result.setListMap(gastationArray);
					}else{
						result.setStatus(MobileReturn.STATUS_SUCCESS);
						result.setMsg("暂无数据！");
						result.setListMap(new ArrayList<Map<String, Object>>());
					}
				//按价格排序
				}else if(sortType.equals("3")){
					gastationArray = orderByPriceList(gastationAllList,pageNumIn,pageSizeIn);
					if(gastationArray!=null && gastationArray.size() > 0 ){
						result.setListMap(gastationArray);
					}else{
						result.setStatus(MobileReturn.STATUS_SUCCESS);
						result.setMsg("暂无数据！");
						result.setListMap(new ArrayList<Map<String, Object>>());
					}
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取加注站信息失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取加注站信息失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	/**
	 * 获取加注站信息列表(default)
	 * @return
	 */
	private List<Map<String, Object>> defaultList(List<Gastation> gastationAllList,Double longIn,Double langIn,int pageNumIn,int pageSizeIn){
		List<Map<String, Object>> gastationArray = new ArrayList<>();
		String http_poms_path = (String) prop.get("http_poms_path");
		//判断是否有数据
		if(gastationAllList!=null && gastationAllList.size() > 0){
			Double longDb = new Double(0);
			Double langDb = new Double(0);
			for (int i = 0; i < gastationAllList.size(); i++) {
				String longStr = gastationAllList.get(i).getLongitude();
				String langStr = gastationAllList.get(i).getLatitude();
				if (longStr != null && !"".equals(longStr) && langStr != null && !"".equals(langStr)) {
					longDb = new Double(longStr);
					langDb = new Double(langStr);
				}
				// 计算当前加注站离指定坐标距离
				Double dist = DistCnvter.getDistance(longIn, langIn, longDb, langDb);
				gastationAllList.get(i).setDistance(dist);
			}
			//按距离重新排序gastationResultList
			Collections.sort(gastationAllList);
			//获取两个联盟站并置顶
			int index = 0;
			for(int i=0;i<gastationAllList.size();i++ ){
				if(gastationAllList.get(i).getType().equals("0")){
					gastationAllList.add(index, gastationAllList.get(i));
					gastationAllList.remove(i+1);
					index++;
				}
				if(index > 1 ){
					break;
				}
			}
			//进行分页
			int allPage = gastationAllList.size()/pageSizeIn==0?gastationAllList.size()/pageSizeIn+1:(gastationAllList.size()/pageSizeIn)+1;
			if(allPage >= pageNumIn ){
				if (pageNumIn == 0) {
					pageNumIn = 1;
				}
				int end = pageNumIn * pageSizeIn;
				if (end > gastationAllList.size()) {
					end  = gastationAllList.size();
				}
				int begin = (pageNumIn - 1) * pageSizeIn;
				PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(gastationAllList.subList(begin, end ));
				List<Gastation> gastationList1 = pageInfo.getList();
				if (gastationList1 != null && gastationList1.size() > 0) {
					for (Gastation gastationInfo : gastationList1) {
						Map<String, Object> gastationMap = new HashMap<>();
						gastationMap.put("stationId", gastationInfo.getSys_gas_station_id());
						gastationMap.put("name", gastationInfo.getGas_station_name());
						gastationMap.put("type", gastationInfo.getType());
						gastationMap.put("longitude", gastationInfo.getLongitude());
						gastationMap.put("latitude", gastationInfo.getLatitude());
						Usysparam usysparam = usysparamService.queryUsysparamByCode("STATION_DATA_TYPE",gastationInfo.getType());
						gastationMap.put("stationType", usysparam.getMname());
						gastationMap.put("service",gastationInfo.getGas_server() == null ? "暂无" : gastationInfo.getGas_server());// 提供服务
						gastationMap.put("preferential",gastationInfo.getPromotions() == null ? "暂无" : gastationInfo.getPromotions());// 优惠活动
						// 获取当前气站价格列表
						String price = gastationInfo.getLng_price();
						if (price != null && !"".equals(price)) {
							price = price.replaceAll("，", ",");
							price = price.replaceAll("：", ":");
							if (price.indexOf(":") != -1 && price.indexOf("/") != -1) {
								String strArray[] = price.split(",");
								Map[] map = new Map[strArray.length];
								for (int i = 0; i < strArray.length; i++) {
									String strInfo = strArray[i].trim();
									String strArray1[] = strInfo.split(":");
									String strArray2[] = strArray1[1].split("/");
									Map<String, Object> dataMap = new HashMap<>();
									dataMap.put("gasName", strArray1[0]);
									dataMap.put("gasPrice", strArray2[0]);
									dataMap.put("gasUnit", strArray2[1]);
									map[i] = dataMap;
								}
								gastationMap.put("priceList", map);
							} else {
								gastationMap.put("priceList", new ArrayList());
							}
						} else {
							gastationMap.put("priceList", new ArrayList());
						}
						gastationMap.put("phone", gastationInfo.getContact_phone());
						if (gastationInfo.getStatus().equals("0")) {
							gastationMap.put("state", "开启");
						} else {
							gastationMap.put("state", "关闭");
						}
						gastationMap.put("address", gastationInfo.getAddress());
						String infoUrl = http_poms_path + "/portal/crm/help/station?stationId="+ gastationInfo.getSys_gas_station_id();
						gastationMap.put("infoUrl", infoUrl);
						gastationMap.put("shareUrl", http_poms_path + "/portal/crm/help/share/station?stationId="+ gastationInfo.getSys_gas_station_id());
						gastationArray.add(gastationMap);
					}
				}
			}
		}
		return gastationArray;
	}
	/**
	 * 获取加注站信息列表(orderByDistance)
	 */
	private List<Map<String, Object>> orderByDistanceList(List<Gastation> gastationAllList,Double longIn,Double langIn,int pageNumIn,int pageSizeIn){
		List<Map<String, Object>> gastationArray = new ArrayList<>();
		String http_poms_path = (String) prop.get("http_poms_path");
		//判断是否有数据
		if(gastationAllList!=null && gastationAllList.size() > 0){
			Double longDb = new Double(0);
			Double langDb = new Double(0);
			for (int i = 0; i < gastationAllList.size(); i++) {
				String longStr = gastationAllList.get(i).getLongitude();
				String langStr = gastationAllList.get(i).getLatitude();
				if (longStr != null && !"".equals(longStr) && langStr != null && !"".equals(langStr)) {
					longDb = new Double(longStr);
					langDb = new Double(langStr);
				}
				// 计算当前加注站离指定坐标距离
				Double dist = DistCnvter.getDistance(longIn, langIn, longDb, langDb);
				gastationAllList.get(i).setDistance(dist);
			}
			//按距离重新排序gastationResultList
			Collections.sort(gastationAllList);
			//进行分页
			int allPage = gastationAllList.size()/pageSizeIn==0?gastationAllList.size()/pageSizeIn+1:(gastationAllList.size()/pageSizeIn)+1;
			if(allPage >= pageNumIn ){
				if (pageNumIn == 0) {
					pageNumIn = 1;
				}
				int end = pageNumIn * pageSizeIn;
				if (end > gastationAllList.size()) {
					end  = gastationAllList.size();
				}
				int begin = (pageNumIn - 1) * pageSizeIn;
				PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(gastationAllList.subList(begin, end ));
				List<Gastation> gastationList1 = pageInfo.getList();
				if (gastationList1 != null && gastationList1.size() > 0) {
					for (Gastation gastationInfo : gastationList1) {
						Map<String, Object> gastationMap = new HashMap<>();
						gastationMap.put("stationId", gastationInfo.getSys_gas_station_id());
						gastationMap.put("name", gastationInfo.getGas_station_name());
						gastationMap.put("type", gastationInfo.getType());
						gastationMap.put("longitude", gastationInfo.getLongitude());
						gastationMap.put("latitude", gastationInfo.getLatitude());
						Usysparam usysparam = usysparamService.queryUsysparamByCode("STATION_DATA_TYPE",gastationInfo.getType());
						gastationMap.put("stationType", usysparam.getMname());
						gastationMap.put("service",gastationInfo.getGas_server() == null ? "暂无" : gastationInfo.getGas_server());// 提供服务
						gastationMap.put("preferential",gastationInfo.getPromotions() == null ? "暂无" : gastationInfo.getPromotions());// 优惠活动
						// 获取当前气站价格列表
						String price = gastationInfo.getLng_price();
						if (price != null && !"".equals(price)) {
							price = price.replaceAll("，", ",");
							price = price.replaceAll("：", ":");
							if (price.indexOf(":") != -1 && price.indexOf("/") != -1) {
								String strArray[] = price.split(",");
								Map[] map = new Map[strArray.length];
								for (int i = 0; i < strArray.length; i++) {
									String strInfo = strArray[i].trim();
									String strArray1[] = strInfo.split(":");
									String strArray2[] = strArray1[1].split("/");
									Map<String, Object> dataMap = new HashMap<>();
									dataMap.put("gasName", strArray1[0]);
									dataMap.put("gasPrice", strArray2[0]);
									dataMap.put("gasUnit", strArray2[1]);
									map[i] = dataMap;
								}
								gastationMap.put("priceList", map);
							} else {
								gastationMap.put("priceList", new ArrayList());
							}
						} else {
							gastationMap.put("priceList", new ArrayList());
						}
						gastationMap.put("phone", gastationInfo.getContact_phone());
						if (gastationInfo.getStatus().equals("0")) {
							gastationMap.put("state", "开启");
						} else {
							gastationMap.put("state", "关闭");
						}
						gastationMap.put("address", gastationInfo.getAddress());
						String infoUrl = http_poms_path + "/portal/crm/help/station?stationId="+ gastationInfo.getSys_gas_station_id();
						gastationMap.put("infoUrl", infoUrl);
						gastationMap.put("shareUrl", http_poms_path + "/portal/crm/help/share/station?stationId="+ gastationInfo.getSys_gas_station_id());
						gastationArray.add(gastationMap);
					}
				}
			}
		}
		return gastationArray;
	}
	/**
	 * 获取加注站信息列表(orderByPrice)
	 */
	private List<Map<String, Object>> orderByPriceList(List<Gastation> gastationAllList,int pageNumIn,int pageSizeIn){
		List<Map<String, Object>> gastationArray = new ArrayList<>();
		String http_poms_path = (String) prop.get("http_poms_path");
		if(gastationAllList!=null && gastationAllList.size() > 0){
			List<Gastation> rightList = new ArrayList<Gastation>();
			List<Gastation> wrongList = new ArrayList<Gastation>();
			for(int i=0;i<gastationAllList.size();i++ ){
				String price = gastationAllList.get(i).getLng_price();
				if(price!=null && !" ".equals(price) && !"".equals(price)){
					price = price.replaceAll("，", ",");
					price = price.replaceAll("：", ":");
					if(price.indexOf(":") != -1 && price.indexOf("/") != -1){
						rightList.add(gastationAllList.get(i));
					}else{
						wrongList.add(gastationAllList.get(i));
						gastationAllList.remove(i);
						i--;
					}
				}else{
					wrongList.add(gastationAllList.get(i));
					gastationAllList.remove(i);
					i--;
				}
			}
			for (int i=0;i <gastationAllList.size();i++ ){
				String price = gastationAllList.get(i).getLng_price();
				List<Double> priceList = new ArrayList<Double>();
				//获取价格
				if (price != null && !"".equals(price)) {
					price = price.replaceAll("，", ",");
					price = price.replaceAll("：", ":");
					if (price.indexOf(":") != -1 && price.indexOf("/") != -1) {
						String strArray[] = price.split(",");
						for (int x = 0; x < strArray.length; x++) {
							String strInfo = strArray[x].trim();
							String strArray1[] = strInfo.split(":");
							String strArray2[] = strArray1[1].split("/");
							Double m = Double.valueOf(strArray2[0].substring(0, strArray2[0].length()-1));
							priceList.add(m);
						}
					}else{
						priceList.add(0.0);
					}
				}else{
					priceList.add(0.0);
				}
				//获取最低价格,并赋值
				Collections.sort(priceList);
				gastationAllList.get(i).setMinPrice(priceList.get(0));
			}
	        Collections.sort(gastationAllList, new Comparator<Gastation>(){
	            public int compare(Gastation g1, Gastation g2) {
	            	int i = 0;
	        		i = g1.getMinPrice().compareTo(g2.getMinPrice());
	        		return i;
	            }
	        });
	        gastationAllList.addAll(wrongList);
	        //进行分页
			int allPage = gastationAllList.size()/pageSizeIn==0?gastationAllList.size()/pageSizeIn+1:(gastationAllList.size()/pageSizeIn)+1;
			if(allPage >= pageNumIn ){
				if (pageNumIn == 0) {
					pageNumIn = 1;
				}
				int end = pageNumIn * pageSizeIn;
				if (end > gastationAllList.size()) {
					end  = gastationAllList.size();
				}
				int begin = (pageNumIn - 1) * pageSizeIn;
				PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(gastationAllList.subList(begin, end ));
				List<Gastation> gastationList1 = pageInfo.getList();
				if (gastationList1 != null && gastationList1.size() > 0) {
					for (Gastation gastationInfo : gastationList1) {
						Map<String, Object> gastationMap = new HashMap<>();
						gastationMap.put("stationId", gastationInfo.getSys_gas_station_id());
						gastationMap.put("name", gastationInfo.getGas_station_name());
						gastationMap.put("type", gastationInfo.getType());
						gastationMap.put("longitude", gastationInfo.getLongitude());
						gastationMap.put("latitude", gastationInfo.getLatitude());
						Usysparam usysparam = usysparamService.queryUsysparamByCode("STATION_DATA_TYPE",gastationInfo.getType());
						gastationMap.put("stationType", usysparam.getMname());
						gastationMap.put("service",gastationInfo.getGas_server() == null ? "暂无" : gastationInfo.getGas_server());// 提供服务
						gastationMap.put("preferential",gastationInfo.getPromotions() == null ? "暂无" : gastationInfo.getPromotions());// 优惠活动
						// 获取当前气站价格列表
						String price = gastationInfo.getLng_price();
						if (price != null && !"".equals(price)) {
							price = price.replaceAll("，", ",");
							price = price.replaceAll("：", ":");
							if (price.indexOf(":") != -1 && price.indexOf("/") != -1) {
								String strArray[] = price.split(",");
								Map[] map = new Map[strArray.length];
								for (int i = 0; i < strArray.length; i++) {
									String strInfo = strArray[i].trim();
									String strArray1[] = strInfo.split(":");
									String strArray2[] = strArray1[1].split("/");
									Map<String, Object> dataMap = new HashMap<>();
									dataMap.put("gasName", strArray1[0]);
									dataMap.put("gasPrice", strArray2[0]);
									dataMap.put("gasUnit", strArray2[1]);
									map[i] = dataMap;
								}
								gastationMap.put("priceList", map);
							} else {
								gastationMap.put("priceList", new ArrayList());
							}
						} else {
							gastationMap.put("priceList", new ArrayList());
						}
						gastationMap.put("phone", gastationInfo.getContact_phone());
						if (gastationInfo.getStatus().equals("0")) {
							gastationMap.put("state", "开启");
						} else {
							gastationMap.put("state", "关闭");
						}
						gastationMap.put("address", gastationInfo.getAddress());
						String infoUrl = http_poms_path + "/portal/crm/help/station?stationId="+ gastationInfo.getSys_gas_station_id();
						gastationMap.put("infoUrl", infoUrl);
						gastationMap.put("shareUrl", http_poms_path + "/portal/crm/help/share/station?stationId="+ gastationInfo.getSys_gas_station_id());
						gastationArray.add(gastationMap);
					}
				}
			}
		}
		return gastationArray;
	}
	/**
	 * 获取单个商户信息
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/station/getStationInfo")
	@ResponseBody
	public String getStationInfo(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String token = "token";
			boolean b = JsonTool.checkJson(mainObj, token);
			/**
			 * 请求接口
			 */
			if (b) {
				Map<String, Object> dataMap = new HashMap<>();
				token = mainObj.optString("token");
				SysDriver driver = driverService.queryDriverByPK(token);
				if(driver!=null){
					Gastation gastation = gastationService.queryGastationByPhone(driver.getMobilePhone());
					if(gastation!=null){
						dataMap.put("stationId", gastation.getSys_gas_station_id());
						dataMap.put("address", gastation.getAddress());
						dataMap.put("stationName", gastation.getGas_station_name());
						dataMap.put("phone", gastation.getContact_phone());
						GsGasPrice gsGasPrice = gsGasPriceService.queryGsGasPriceInfo(gastation.getSys_gas_station_id());
						dataMap.put("price", gsGasPrice.getPrice());
						dataMap.put("unit", usysparamService.query("GAS_UNIT", gsGasPrice.getUnit()).get(0).getMname());
						dataMap.put("gasName", usysparamService.query("CARDTYPE", gsGasPrice.getGasName()).get(0).getMname());
						result.setData(dataMap);
					}else{
						result.setStatus(MobileReturn.STATUS_SUCCESS);
						result.setMsg("没有对应的气站信息！");
					}
				}else{
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("没有对应的司机账户！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取折扣信息失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取折扣信息失败： " + e);
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);
			return resultStr;
		} finally {
			return resultStr;
		}
	}
	/**
	 * 修改商户信息
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/station/updateStationInfo")
	@ResponseBody
	public String updateStationInfo(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("查询成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr, params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String stationId = "stationId";
			boolean b = JsonTool.checkJson(mainObj, stationId);
			/**
			 * 请求接口
			 */
			if (b) {
				stationId = mainObj.optString("stationId");
				Gastation gastation = new Gastation();
				gastation.setSys_gas_station_id(stationId);
				String stationName = mainObj.optString("stationName");
				String phone = mainObj.optString("phone");
				String price = mainObj.optString("price");
				String unit = mainObj.optString("unit");
				String promotions = mainObj.optString("promotions");
				if(stationName!=null && !"".equals(stationName)){
					gastation.setGas_station_name(stationName);
				}
				if(phone!=null && !"".equals(phone)){
					gastation.setContact_phone(phone);
				}
				if(promotions!=null && !"".equals(promotions)){
					gastation.setPromotions(promotions);
				}
				int rs = gastationService.updateByPrimaryKeySelective(gastation);
				if(rs > 0){
					result.setMsg("修改成功！");
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("修改失败！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取折扣信息失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取折扣信息失败： " + e);
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);
			return resultStr;
		} finally {
			return resultStr;
		}
	}

	private String genPayReq(Map<String, String> resultunifiedorder) {

		/*
		 * 1、调用服务端获取支付信息 { "appid": "wxb4ba3c02aa476ea1", "noncestr":
		 * "1bd2880eedbdd821a647bd56c45ad879", "package": "Sign=WXPay",
		 * "partnerid": "1305176001", "prepayid":
		 * "wx201608250930356a8a94d8370901437288", "sign":
		 * "AA994BB10D048C7F2EBE8D48514C0F4E", "timestamp": 1472088635 }
		 *
		 */
		PayReq payReq = new PayReq();
		payReq.appId = APP_ID;
		payReq.partnerId = MCH_ID;
		payReq.prepayId = resultunifiedorder.get("prepay_id");
		payReq.packageValue = "Sign=WXPay";
		payReq.nonceStr = genNonceStr();
		payReq.timeStamp = String.valueOf(genTimeStamp());
		// 生成签名
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", payReq.appId));
		signParams.add(new BasicNameValuePair("noncestr", payReq.nonceStr));
		signParams.add(new BasicNameValuePair("package", payReq.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", payReq.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", payReq.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", payReq.timeStamp));
		payReq.sign = genAppSign(signParams);
		StringBuffer bRet = new StringBuffer();
		bRet.append("{\"appId\":\"").append(APP_ID).append("\",").append("\"partnerId\":\"").append(MCH_ID)
				.append("\",").append("\"prepayId\":\"").append(payReq.prepayId).append("\",")
				.append("\"packageValue\":\"").append("Sign=WXPay").append("\",").append("\"nonceStr\":\"")
				.append(payReq.nonceStr).append("\",").append("\"timeStamp\":\"").append(payReq.timeStamp).append("\",")
				.append("\"sign\":\"").append(payReq.sign).append("\"}");
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

	private String genProductArgs(String orderID, String totalFee,String type) {
		String http_poms_path = (String) prop.get("http_poms_path");
		try {
			String nonceStr = genNonceStr();
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			String xmlstring;
			//充值
			if(type.equals("1")){
				packageParams.add(new BasicNameValuePair("appid", APP_ID));// 应用ID
				packageParams.add(new BasicNameValuePair("body", "司集云平台-会员充值"));// 商品描述
																				// 商品描述交易字段格式根据不同的应用场景按照以下格式
																				// APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
				packageParams.add(new BasicNameValuePair("detail", "司集云平台-会员充值"));// 商品详情
																					// 商品名称明细列表
				packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));// 微信支付分配的商户号
				packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));// 随机字符串
				packageParams.add(
						new BasicNameValuePair("notify_url", http_poms_path + "/api/v1/mobile/deal/wechatCallBackPay"));// 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
				packageParams.add(new BasicNameValuePair("out_trade_no", orderID));// 商户系统内部的订单号,32个字符内、可包含字母,
																					// 其他说明见商户订单号
				packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));// 用户端实际ip
				packageParams.add(new BasicNameValuePair("total_fee", totalFee));// 订单总金额，单位为分，详见支付金额
				packageParams.add(new BasicNameValuePair("trade_type", "APP"));
				String sign = genPackageSign(packageParams);
				packageParams.add(new BasicNameValuePair("sign", sign));// 签名，详见签名生成算法
				xmlstring = toXml(packageParams);
			}else{//消费
				packageParams.add(new BasicNameValuePair("appid", APP_ID));// 应用ID
				packageParams.add(new BasicNameValuePair("body", "司集云平台-会员消费"));// 商品描述
																				// 商品描述交易字段格式根据不同的应用场景按照以下格式
																				// APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
				packageParams.add(new BasicNameValuePair("detail", "司集云平台-会员消费"));// 商品详情
																					// 商品名称明细列表
				packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));// 微信支付分配的商户号
				packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));// 随机字符串
				packageParams.add(
						new BasicNameValuePair("notify_url", http_poms_path + "/api/v1/mobile/deal/wechatConsum"));// 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
				packageParams.add(new BasicNameValuePair("out_trade_no", orderID));// 商户系统内部的订单号,32个字符内、可包含字母,
																					// 其他说明见商户订单号
				packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));// 用户端实际ip
				packageParams.add(new BasicNameValuePair("total_fee", totalFee));// 订单总金额，单位为分，详见支付金额
				packageParams.add(new BasicNameValuePair("trade_type", "APP"));
				String sign = genPackageSign(packageParams);
				packageParams.add(new BasicNameValuePair("sign", sign));// 签名，详见签名生成算法
				xmlstring = toXml(packageParams);
			}
			return xmlstring;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 构造微信支付成功返回结果
	 * 
	 * @return
	 */
	private String getWechatResult() {
		String http_poms_path = (String) prop.get("http_poms_path");
		try {
			String nonceStr = genNonceStr();
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("return_code", "SUCCESS"));// 应用ID
			packageParams.add(new BasicNameValuePair("return_msg", "支付成功"));// 商品描述
																			// 商品描述交易字段格式根据不同的应用场景按照以下格式
																			// APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
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

	private SysOrder createNewOrder(String orderID, String driverID, String cash, String chargeType,String spendType,String type,String consumeType) throws Exception {
		SysOrder record = new SysOrder();
		//消费订单
		if(type.equals("2")){
			record.setChannel("APP-消费");
			record.setChannelNumber("APP-消费"); // 建立一个虚拟的APP气站，方便后期统计
			//微信消费
			if(consumeType.equals("C03")){
				record.setOrderType(GlobalConstant.ORDER_SPEND_TYPE.WECHAT);
			}else if(consumeType.equals("C04")){
				//支付宝消费
				record.setOrderType(GlobalConstant.ORDER_SPEND_TYPE.ALIPAY);
			}else if(consumeType.equals("C01")){
				//账户余额
				record.setOrderType(GlobalConstant.ORDER_SPEND_TYPE.CASH_BOX);
			}
			record.setSpend_type(spendType);
			record.setOrderId(orderID);
			record.setCreditAccount(driverID);
			record.setOperator(appOperatorId);
			record.setOperatorSourceId(appOperatorId);
			record.setCash(new BigDecimal(cash));
			record.setChargeType(chargeType);
			record.setIs_discharge("0");
			record.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.DRIVER);
			record.setOrderType(GlobalConstant.OrderType.CONSUME_BY_DRIVER);
			record.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
			record.setOrderNumber(orderService.createOrderNumber(GlobalConstant.OrderType.CONSUME_BY_DRIVER));
			record.setOrderStatus(0);
		}else{
			record.setChannel("APP");
			record.setChannelNumber("APP-支付宝充值"); // 建立一个虚拟的APP气站，方便后期统计
			if (chargeType.equals(GlobalConstant.OrderChargeType.CHARGETYPE_WEICHAT_CHARGE)) {// 微信支付，将金额转换为以元为单位
				BigDecimal cashBd = new BigDecimal(cash);
				BigDecimal num = new BigDecimal(100);
				cashBd = cashBd.divide(num, 2, RoundingMode.HALF_UP);
				cash = cashBd.toString();
				record.setChannel("APP");
				record.setChannelNumber("APP-微信充值"); // 建立一个虚拟的APP气站，方便后期统计
			}
			record.setOrderId(orderID);
			record.setDebitAccount(driverID);
			record.setOperator(appOperatorId);
			record.setOperatorSourceId(appOperatorId);
			record.setCash(new BigDecimal(cash));
			record.setChargeType(chargeType);
			record.setIs_discharge("0");
			record.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.GASTATION);
			record.setOrderType(GlobalConstant.OrderType.CHARGE_TO_DRIVER);
			record.setOperatorTargetType(GlobalConstant.OrderOperatorTargetType.DRIVER);
			record.setOrderNumber(orderService.createOrderNumber(GlobalConstant.OrderType.CHARGE_TO_DRIVER));
			record.setOrderStatus(0);
		}
		/**
		 * 该充值步骤要放到第三方回调接口里面 try{ String orderCharge =
		 * orderService.chargeToDriver(record);
		 * if(!orderCharge.equalsIgnoreCase(GlobalConstant.OrderProcessResult.
		 * SUCCESS)) throw new Exception("订单充值错误：" + orderCharge); } catch
		 * (Exception e) { e.printStackTrace(); throw new Exception("订单充值错误：" +
		 * e); }
		 **/
		Date curDate = new Date();
		record.setOrderDate(curDate);
		return record;
	}

	/**
	 * 遍历当前节点元素下面的所有(元素的)子节点
	 *
	 * @param node
	 */
	public void listNodes(Element node) {
		System.out.println("当前节点的名称：：" + node.getName());
		// 获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
		// 遍历属性节点
		for (Attribute attr : list) {
			System.out.println(attr.getText() + "-----" + attr.getName() + "---" + attr.getValue());
		}

		if (!(node.getTextTrim().equals(""))) {
			System.out.println("文本内容：：：：" + node.getText());
		}

		// 当前节点下面子节点迭代器
		Iterator<Element> it = node.elementIterator();
		// 遍历
		while (it.hasNext()) {
			// 获取某个子节点对象
			Element e = it.next();
			// 对子节点进行遍历
			listNodes(e);
		}
	}



	@RequestMapping(value = "/QR")
	@ResponseBody
	public void getQR() {
		try {
			List<SysDriver> list = driverService.queryAll();
			System.out.println(list.size());
			// 图片路径
			String rootPath = (String) prop.get("images_upload_path") + "/driver/";
			System.out.println(rootPath);
			File file = new File(rootPath);
			// 如果根文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			for (int i = 0; i < list.size(); i++) {
				String path = rootPath + list.get(i).getUserName() + "/";
				File file1 = new File(path);
				// 如果用户文件夹不存在则创建
				if (!file1.exists() && !file1.isDirectory()) {
					file1.mkdir();
				}
				// 二维码路径
				String imgPath = path + list.get(i).getUserName() + ".jpg";
				String show_path = (String) prop.get("show_images_path") + "/driver/" + list.get(i).getUserName() + "/"
						+ list.get(i).getUserName() + ".jpg";
				TwoDimensionCode handler = new TwoDimensionCode();
				String encoderContent = null;
				if (list.get(i).getFullName() != null && !"".equals(list.get(i).getFullName())) {
					String name = list.get(i).getFullName();
					// name = new
					// String(list.get(i).getFullName().getBytes("GB2312"),"UTF-8");
					// name = new
					// String(list.get(i).getFullName().getBytes("UTF-8"),"GB2312");
					// encoderContent=list.get(i).getUserName()+"_"+ new
					// String(new
					// String(list.get(i).getFullName().getBytes("UTF-8"),"GBK").getBytes("GBK"),"UTF-8");
					encoderContent = list.get(i).getUserName() + "_" + name;
				} else {
					encoderContent = list.get(i).getUserName();
				}
				SysDriver driver = new SysDriver();
				driver.setSysDriverId(list.get(i).getSysDriverId());
				driver.setDriverQrcode(show_path);
				int resultVal = driverService.saveDriver(driver, "update", null, null);
				handler.encoderQRCode(encoderContent, imgPath, TwoDimensionCode.imgType, null, TwoDimensionCode.size);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
