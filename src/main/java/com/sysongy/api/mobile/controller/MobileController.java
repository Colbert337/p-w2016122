package com.sysongy.api.mobile.controller;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
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

import com.github.pagehelper.PageHelper;
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
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.message.model.SysMessage;
import com.sysongy.poms.message.service.SysMessageService;
import com.sysongy.poms.mobile.controller.SysRoadController;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.service.MbBannerService;
import com.sysongy.poms.mobile.service.SysRoadService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.ordergoods.service.SysOrderGoodsService;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.JsonTool;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.RedisClientInterface;
import com.sysongy.util.TwoDimensionCode;
import com.sysongy.util.UUIDGenerator;
import com.tencent.mm.sdk.modelpay.PayReq;

import net.sf.json.JSONObject;

@RequestMapping("/api/v1/mobile")
@Controller
public class MobileController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
	public final String keyStr = "sysongys";
	String localPath = (String) prop.get("http_poms_path");

	/**
	 * 新浪短网址API
	 * source:应用的appkey(3271760578)
	 * url_long:需要转换的长链接
	 * 示例：xml:http://api.t.sina.com.cn/short_url/shorten.xml?source=3271760578&url_long=http://www.douban.com/note/249723561/
	 */
	String XML_API = "http://api.t.sina.com.cn/short_url/shorten.xml";
	String JSON_API = "http://api.t.sina.com.cn/short_url/shorten.json";

	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2016011801102578"; //TODO 需要自定义常量类
	public static final String ss ="123123";

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
	@Autowired
	SysRoadService sysRoadService;
	@Autowired
	SysMessageService sysMessageService;

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
			String type = "type";
			boolean b = JsonTool.checkJson(mainObj,username,type);
			/**
			 * 请求接口
			 */
			if(b){
				type = mainObj.optString("type");
				SysDriver driver = new SysDriver();
				SysDriver queryDriver = null;
				//賬號密碼登錄
				if("1".equals(type)){
					driver.setUserName(mainObj.optString("username"));
					driver.setPassword(mainObj.optString("password"));
					queryDriver = driverService.queryByUserNameAndPassword(driver);
					if(queryDriver != null ){
						Map<String, Object> tokenMap = new HashMap<>();
						tokenMap.put("token",queryDriver.getSysDriverId());
						result.setData(tokenMap);
					}else{
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("用户名或密码错误！");
					}
				}else{//用戶名驗證碼登錄
					driver.setUserName(mainObj.optString("username"));
					driver.setMobilePhone(mainObj.optString("username"));
					String verificationCode = mainObj.optString("verificationCode");
					String veCode = (String) redisClientImpl.getFromCache(driver.getMobilePhone());
					if(verificationCode.equals(veCode)){
						queryDriver = driverService.queryByUserName(driver);
						Map<String, Object> tokenMap = new HashMap<>();
						tokenMap.put("token",queryDriver.getSysDriverId());
						result.setData(tokenMap);
					}else{
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("验证码无效！");
					}
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("登录信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("登录失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("登录信息： " + e);
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
			 * 必填参数
			 */
			String phoneNum = "phoneNum";
			String templateType = "templateType";
			boolean b = JsonTool.checkJson(mainObj,phoneNum,templateType);
			/**
			 * 请求接口
			 */
			if(b){
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
			logger.error("验证码信息： " + resultStr);
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
			 * 必填参数
			 */
			String phoneNum = "phoneNum";
			String verificationCode = "verificationCode";
			String password = "password";
			boolean b = JsonTool.checkJson(mainObj,phoneNum,verificationCode,password);
			/**
			 * 请求接口
			 */
			if(b){
				SysDriver driver = new SysDriver();
				driver.setUserName(mainObj.optString("phoneNum"));
				driver.setMobilePhone(mainObj.optString("phoneNum"));
				String invitationCode = mainObj.optString("invitationCode");
				String veCode = (String) redisClientImpl.getFromCache(driver.getMobilePhone());
				if(veCode != null && !"".equals(veCode)) {
					List<SysDriver> driverlist = driverService.queryeSingleList(driver);
					if (driverlist != null && driverlist.size() > 0) {
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("该手机号已注册！");
						//throw new Exception(MobileRegisterUtils.RET_DRIVER_MOBILE_REGISTED);
					} else {
						String sysDriverId = UUIDGenerator.getUUID();
						driver.setPassword(mainObj.optString("password"));
						driver.setSysDriverId(sysDriverId);
						driver.setRegisSource("APP");
						String encoderContent=mainObj.optString("phoneNum");
						//图片路径
						String rootPath = (String) prop.get("images_upload_path")+ "/driver/";
				        File file =new File(rootPath);    
						//如果根文件夹不存在则创建    
						if  (!file.exists()  && !file.isDirectory()){       
						    file.mkdir();    
						}
						String path = rootPath+mainObj.optString("phoneNum")+"/";
						File file1 =new File(path);    
						//如果用户文件夹不存在则创建    
						if  (!file1.exists()  && !file1.isDirectory()){       
						    file1.mkdir();    
						}
						//二维码路径
						String imgPath = path+mainObj.optString("phoneNum")+".jpg";
						String show_path = (String) prop.get("show_images_path")+ "/driver/"+mainObj.optString("phoneNum")+"/"+mainObj.optString("phoneNum")+".jpg";
						//生成二维码
						driver.setDriverQrcode(show_path);

						Integer tmp = driverService.saveDriver(driver, "insert", invitationCode);
						if(tmp > 0){
							TwoDimensionCode handler = new TwoDimensionCode();
							handler.encoderQRCode(encoderContent,imgPath, TwoDimensionCode.imgType,null, TwoDimensionCode.size);
						}
						Map<String, Object> tokenMap = new HashMap<>();
						tokenMap.put("token", sysDriverId);
						result.setData(tokenMap);
					}
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("验证码无效！");
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
			e.printStackTrace();
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
			String http_poms_path =  (String) prop.get("http_poms_path");
			/**
			 * 必填参数
			 */
			String token = "token";
			boolean b = JsonTool.checkJson(mainObj,token);
			/**
			 * 请求接口
			 */
			if(b){
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

						//获取用户审核状态
						driver = driverlist.get(0);
						SysUserAccount sysUserAccount = sysUserAccountService.queryUserAccountByDriverId(driver.getSysDriverId());
						
						String driverCheckedStstus = driver.getCheckedStatus();
						if("2".equals(driverCheckedStstus)){
							resultMap.put("nick",driver.getFullName());
						}else{
							resultMap.put("nick","");
						}
						resultMap.put("account",driver.getUserName());
						resultMap.put("securityPhone",driver.getMobilePhone());
						resultMap.put("isRealNameAuth",driver.getCheckedStatus());
						resultMap.put("balance",driver.getAccount().getAccountBalance());
						resultMap.put("QRCodeUrl",http_poms_path+driverlist.get(0).getDriverQrcode());
						resultMap.put("cumulativeReturn",cashBack);
						resultMap.put("userStatus",sysUserAccount.getAccount_status());
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
							driverService.saveDriver(driverCode,"update",null);
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
			logger.error("查询用户信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
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
			 * 必填参数
			 */
			String token = "token";
			String password = "password";
			String verificationCode = "verificationCode";
			boolean b = JsonTool.checkJson(mainObj,token,password,verificationCode);
			/**
			 * 请求接口
			 */
			if(b){
				SysDriver sysDriver = new SysDriver();
				sysDriver.setSysDriverId(mainObj.optString("token"));
				password = mainObj.optString("password");
				if(password != null && !"".equals(password)){
					password = Encoder.MD5Encode(password.getBytes());
					sysDriver.setPassword(password);
					sysDriver.setSysDriverId(mainObj.optString("token"));

					driverService.saveDriver(sysDriver,"update",null);
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
			logger.error("修改登录密码信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
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
			 * 必填参数
			 */
			String token = "token";
			String name = "name";
			String imgUrl = "imgUrl";
			String deviceToken = "deviceToken";
			boolean b = JsonTool.checkJson(mainObj,token,name,imgUrl,deviceToken);
			/**
			 * 请求接口
			 */
			if(b){
				SysDriver driver = new SysDriver();
				String sysDriverId = mainObj.optString("token");
				if(sysDriverId != null && !sysDriverId.equals("")){
					Map<String, Object> resultMap = new HashMap<>();
					driver.setSysDriverId(sysDriverId);
					driver.setFullName(mainObj.optString("name"));
					driver.setDeviceToken(mainObj.optString("deviceToken"));
					driver.setAvatarB(mainObj.optString("imgUrl"));
					int resultVal = driverService.saveDriver(driver,"update",null);
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
			logger.error("修改用户信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
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
			 * 必填参数
			 */
			String token = "token";
			String paycode = "paycode";
			String verificationCode = "verificationCode";
			boolean b = JsonTool.checkJson(mainObj,token,paycode,verificationCode);
			/**
			 * 请求接口
			 */
			if(b){
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

					driverService.saveDriver(driver,"update",null);//设置支付密码
				}

			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("支付密码设置信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
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
			 * 必填参数
			 */
			String token = "token";
			String oldPayCode = "oldPayCode";
			String newPayCode = "newPayCode";
			boolean b = JsonTool.checkJson(mainObj,token,oldPayCode,newPayCode);
			/**
			 * 请求接口
			 */
			if(b){
				SysDriver sysDriver = new SysDriver();
				sysDriver.setSysDriverId(mainObj.optString("token"));
				String driverId = mainObj.optString("token");
				oldPayCode = mainObj.optString("oldPayCode");
				SysDriver driver = driverService.queryDriverByPK(driverId);
				String payCode = driver.getPayCode();
				if(payCode.equals(oldPayCode)){
					//判断原支付密码是否正确
					newPayCode = mainObj.optString("newPayCode");
					if(newPayCode != null && !"".equals(newPayCode)){
						sysDriver.setPayCode(newPayCode);
						driverService.saveDriver(sysDriver,"update",null);
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
			logger.error("修改支付密码信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
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
			logger.error("图片上传信息： " + resultStr);
			/*resultStr = DESUtil.encode(keyStr,resultStr);//参数加密*/
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
	public String realNameAuth(String params,HttpServletRequest request){
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
			 * 必填参数
			 */
			String token = "token";
			String name = "name";
			String plateNumber = "plateNumber";
			String gasType = "gasType";
			String endTime = "endTime";
			String drivingLicenseImageUrl = "drivingLicenseImageUrl";
			String driverLicenseImageUrl = "driverLicenseImageUrl";
			String idCard = "idCard";
			boolean b = JsonTool.checkJson(mainObj,token,name,plateNumber,gasType,endTime,drivingLicenseImageUrl,driverLicenseImageUrl,idCard);
			/**
			 * 请求接口
			 */
			if(b){
				SysDriver driver = new SysDriver();
				String fullName = mainObj.optString("name");
				driver.setSysDriverId(mainObj.optString("token"));
				//获取用户电话
				List<SysDriver> driverList = driverService.queryeSingleList(driver);
				if(driverList != null && driverList.size() > 0){
					driver.setFullName(fullName);
					driver.setPlateNumber(mainObj.optString("plateNumber"));
					driver.setFuelType(mainObj.optString("gasType"));
					if(mainObj.optString("endTime") != null && !"".equals(mainObj.optString("endTime"))){
						SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
						Date date = sft.parse(mainObj.optString("endTime"));
						driver.setExpiryDate(date);
					}
					driver.setDrivingLice(mainObj.optString("drivingLicenseImageUrl"));
					driver.setVehicleLice(mainObj.optString("driverLicenseImageUrl"));

					driver.setCheckedStatus(GlobalConstant.DriverCheckedStatus.CERTIFICATING);
					driver.setIdentityCard(mainObj.optString("idCard"));
					String encoderContent=driverList.get(0).getMobilePhone()+"_"+fullName;
					//图片路径
					String rootPath = (String) prop.get("images_upload_path")+ "/driver/";
					File file =new File(rootPath);
					//如果根文件夹不存在则创建
					if  (!file.exists()  && !file.isDirectory()){
						file.mkdir();
					}
					String path = rootPath+driverList.get(0).getMobilePhone()+"/";
					File file1 =new File(path);
					//如果用户文件夹不存在则创建
					if  (!file1.exists()  && !file1.isDirectory()){
						file1.mkdir();
					}
					//二维码路径
					String imgPath = path+driverList.get(0).getMobilePhone()+".jpg";
					String show_path = (String) prop.get("show_images_path")+ "/driver/"+driverList.get(0).getMobilePhone()+"/"+driverList.get(0).getMobilePhone()+".jpg";
					//生成二维码
					driver.setDriverQrcode(show_path);
					int resultVal = driverService.saveDriver(driver,"update",null);
					if(resultVal <= 0){
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("用户ID为空，申请失败！");
					}else{
						TwoDimensionCode handler = new TwoDimensionCode();
						handler.encoderQRCode(encoderContent,imgPath, TwoDimensionCode.imgType,null, TwoDimensionCode.size);
					}
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("当前用户不存在！");
					resutObj = JSONObject.fromObject(result);
					resutObj.remove("listMap");
					resultStr = resutObj.toString();
					resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
					return resultStr;
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
				resutObj = JSONObject.fromObject(result);
				resutObj.remove("listMap");
				resultStr = resutObj.toString();
				resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
				return resultStr;
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("实名认证已提交审核，请耐心等待！： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("申请失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("申请失败： " + e);
			e.printStackTrace();
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
			 * 必填参数
			 */
			String code = "code";
			boolean b = JsonTool.checkJson(mainObj,code);
			/**
			 * 请求接口
			 */
			if(b){
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
			logger.error("查询信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询失败： " + e);
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
			 * 必填参数
			 */
			String token = "token";
			String mobilePhone = "mobilePhone";
			String content = "content";
			boolean b = JsonTool.checkJson(mainObj,token,mobilePhone,content);
			/**
			 * 请求接口
			 */
			if(b){
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
			logger.error("提交建议信息！： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
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
		String failStr = "操作成功！";
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
			 * 必填参数
			 */
			String token = "token";
			String idCard = "idCard";
			String verificationCode = "verificationCode";
			String payCode = "payCode";
			String lossType = "lossType";
			boolean b = JsonTool.checkJson(mainObj,token,idCard,verificationCode,payCode,lossType);
			/**
			 * 请求接口
			 */
			if(b){
				SysDriver driver = driverService.queryDriverByPK(mainObj.optString("token"));
				lossType = mainObj.optString("lossType");
				/*String cardId = mainObj.optString("cardId");*/
				int retvale = 0;//操作影响行数
				if(lossType != null){//类型等于0 或者等于1
					String cardNo = "";
					if(driver.getCardInfo() != null){
						cardNo = driver.getCardInfo().getCard_no();
					}
					retvale = sysUserAccountService.changeStatus(driver.getAccount().getSysUserAccountId(), lossType, cardNo);
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
			logger.error(failStr+"信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
        } catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("操作失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("操作失败！" + e);
			e.printStackTrace();
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
			logger.error("查询信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
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
		Gastation gastation = new Gastation();
    	
    	try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);//参数解密
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			String http_poms_path =  (String) prop.get("http_poms_path");
			/**
			 * 必填参数
			 */
			String code = "code";
			boolean b = JsonTool.checkJson(mainObj,code);
			
			/**
			 * 请求接口
			 */
			if(mainObj != null){
				if(gastation.getPageNum() == null){
					gastation.setPageNum(GlobalConstant.PAGE_NUM);
					gastation.setPageSize(GlobalConstant.PAGE_SIZE);
				}else{
					gastation.setPageNum(mainObj.optInt("pageNum"));
					gastation.setPageSize(mainObj.optInt("pageSize"));
				}

				String longitudeStr = mainObj.optString("longitude");
				String latitudeStr = mainObj.optString("latitude");
				String radius = mainObj.optString("radius");
				String name = mainObj.optString("name");
				gastation.setGas_station_name(name);
				Double longitude = new Double(0);
				Double latitude = new Double(0);
				Double radiusDb = new Double(0);
				//获取气站列表
				List<Map<String, Object>> gastationArray = new ArrayList<>();
				PageInfo<Gastation> pageInfo = gastationService.queryGastation(gastation);
				List<Gastation> gastationList = pageInfo.getList();
				if(gastationList != null && gastationList.size() > 0){
					for (Gastation gastationInfo:gastationList){
						Map<String, Object> gastationMap = new HashMap<>();
						gastationMap.put("stationId",gastationInfo.getSys_gas_station_id());
						gastationMap.put("name",gastationInfo.getGas_station_name());
						gastationMap.put("type",gastationInfo.getType());
						gastationMap.put("longitude",gastationInfo.getLongitude());
						gastationMap.put("latitude",gastationInfo.getLatitude());
						Usysparam usysparam = usysparamService.queryUsysparamByCode("STATION_MAP_TYPE", gastationInfo.getMap_type());
						gastationMap.put("stationType",usysparam.getMname());
						gastationMap.put("service",gastationInfo.getGas_server());//提供服务
						gastationMap.put("preferential",gastationInfo.getPromotions());//优惠活动
						//获取当前气站价格列表
						//List<Map<String, Object>> priceList = gsGasPriceService.queryPriceList(gastationInfo.getSys_gas_station_id());
						String price = gastationInfo.getLng_price();
						price = price.replaceAll("，",",");
						price = price.replaceAll("：",":");
						String strArray[] = price.split(",");
						Map[] map = new Map[strArray.length];
						for(int i = 0;i<strArray.length;i++){
							String strInfo = strArray[i].trim();
							String strArray1[] = strInfo.split(":");
							String strArray2[] = strArray1[1].split("/");
							Map<String, Object> dataMap = new HashMap<>();
							dataMap.put("gasName",strArray1[0]);
							dataMap.put("gasPrice",strArray2[0]);
							dataMap.put("gasUnit",strArray2[1]);
							map[i] = dataMap;
						}
						gastationMap.put("priceList",map);
						gastationMap.put("phone",gastationInfo.getContact_phone());
						if(gastationInfo.getStatus().equals("0")){
							gastationMap.put("state","开启");
						}else{
							gastationMap.put("state","关闭");
						}
						gastationMap.put("address",gastationInfo.getAddress());
						String infoUrl = http_poms_path+"/portal/crm/help/station?stationId="+gastationInfo.getSys_gas_station_id();
						gastationMap.put("infoUrl",infoUrl);
						gastationMap.put("shareUrl",http_poms_path+"/portal/crm/help/share/station?stationId="+ gastationInfo.getSys_gas_station_id());
						if(longitudeStr != null && !"".equals(longitudeStr) && latitudeStr != null && !"".equals(latitudeStr) && radius != null && !"".equals(radius)){
							longitude = new Double(longitudeStr);
							latitude = new Double(latitudeStr);
							radiusDb = new Double(radius);

							String longStr = gastationInfo.getLongitude();
							String langStr = gastationInfo.getLatitude();
							Double longDb = new Double(0);
							Double langDb = new Double(0);
							if(longStr != null && !"".equals(longStr) && langStr != null && !"".equals(langStr)){
								longDb = new Double(longStr);
								langDb = new Double(langStr);
							}
							//计算当前加注站离指定坐标距离
							Double dist = DistCnvter.getDistance(longitude,latitude,longDb,langDb);
							if(dist <= radiusDb){//在指定范围内，则返回当前加注站信息
								gastationArray.add(gastationMap);
							}
						}else{//目标坐标及范围半径未传参，则返回所有加注站信息
							gastationArray.add(gastationMap);
						}
					}
					result.setListMap(gastationArray);
				}else{
					result.setStatus(MobileReturn.STATUS_MSG_SUCCESS);
					result.setMsg("暂无数据！");
					result.setListMap(new ArrayList<Map<String, Object>>());
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("查询气站信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
        } catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询气站信息失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询气站信息失败： " + e);
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
			 * 必填参数
			 */
			String token = "token";
			String account = "account";
			String name = "name";
			String amount = "amount";
			String remark = "remark";
			String paycode = "paycode";
			boolean b = JsonTool.checkJson(mainObj,token,account,name,amount,remark,paycode);
			/**
			 * 请求接口
			 */
			if(b){
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
				}else if(resultVal == 4){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("支付密码错误！");
				}else if(resultVal == 5){
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("账户和用户名不匹配！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("转账信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
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
			logger.error("查询返现规则列表信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
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
			 * 必填参数
			 */
			String cityName = "cityName";
			String extendType = "extendType";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj,cityName,extendType,pageNum,pageSize);
			/**
			 * 请求接口
			 */
			if(b){
				PageBean bean = new PageBean();
				String http_poms_path =  (String) prop.get("http_poms_path");
				cityName = mainObj.optString("cityName");
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
						bannerMap.put("bannerId",banner.getMbBannerId());
						bannerMap.put("title",banner.getTitle());
						bannerMap.put("content",banner.getContent());
						bannerMap.put("time",sft.format(banner.getCreatedDate()) );
						bannerMap.put("contentUrl",banner.getTargetUrl());
						bannerMap.put("shareUrl",banner.getTargetUrl()+"&show_download_button=1");
						bannerMap.put("imgSmPath",http_poms_path+banner.getImgSmPath());
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
			logger.error("查询头条推广信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
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
			SysDriver driver = new SysDriver();

			SysDriver sysDriver = new SysDriver();
			sysDriver.setSysDriverId(mainObj.optString("token"));
			order.setSysDriver(sysDriver);
			/**
			 * 必填参数
			 */
			String token = "token";
			String time = "time";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj,token,time,pageNum,pageSize);
			/**
			 * 请求接口
			 */
			if(b){
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
					driver = driverService.queryDriverByPK(mainObj.optString("token"));
					reCharge.put("totalCash",totalCash);
					reCharge.put("totalBack",totalBack);
					reCharge.put("listMap",reChargeList);
					if(driver != null && driver.getAccount() != null){
						reCharge.put("totalAmount", driver.getAccount().getAccountBalance());
					}else{
						reCharge.put("totalAmount", 0.00);
					}

				}
				result.setData(reCharge);
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("查询充值记录信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询充值记录失败！");
			resutObj = JSONObject.fromObject(result);
			e.printStackTrace();
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
			 * 必填参数
			 */
			String token = "token";
			String time = "time";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj,token,time,pageNum,pageSize);
			/**
			 * 请求接口
			 */
			if(b){
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
					
				}else{
					reCharge.put("totalCash","");
					reCharge.put("listMap",new ArrayList<>());
				}
				result.setData(reCharge);
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("查询消费记录信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
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
			 * 必填参数
			 */
			String token = "token";
			String time = "time";
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj,token,time,pageNum,pageSize);
			/**
			 * 请求接口
			 */
			if(b){
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
				if(pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() == 0) {

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
				}else{
					reCharge.put("totalOut","");
					reCharge.put("totalIn","");
					reCharge.put("listMap",new ArrayList<>());
				}
				result.setData(reCharge);
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}

			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("查询转账记录信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
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
			 * 必填参数
			 */
			String token = "token";
			String feeCount = "feeCount";
			String payType = "payType";
			boolean b = JsonTool.checkJson(mainObj,token,feeCount,payType);
			/**
			 * 请求接口
			 */
			if(b){
				payType = mainObj.optString("payType");
				feeCount = mainObj.optString("feeCount");
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
					String entity = genProductArgs(orderID, feeCount);
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
					JSONObject dataObj = JSONObject.fromObject(payReq);
					data.put("payReq", dataObj);
					result.setData(data);
				}

			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("充值信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密
			return resultStr;
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("充值失败！"+e.getMessage());
			resutObj = JSONObject.fromObject(result);
			logger.error("充值失败： " + e.getMessage());
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		}
	}


	/**
	 * 在线支付回调方法
	 */
	@RequestMapping(value = "/deal/callBackPay")
	@ResponseBody
	public String callBackPay(@RequestParam String orderId) throws Exception{
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("支付成功！");
		JSONObject resutObj = new JSONObject();
		String resultStr = "";
		//查询订单内容
		SysOrder order = orderService.selectByPrimaryKey(orderId);
		//修改订单状态
		SysOrder sysOrder = new SysOrder();
		sysOrder.setOrderId(orderId);
		sysOrder.setOrderStatus(1);
		orderService.updateByPrimaryKey(sysOrder);
		try{
			String orderCharge = orderService.chargeToDriver(order);
			if(!orderCharge.equalsIgnoreCase(GlobalConstant.OrderProcessResult.SUCCESS))
				throw new Exception("订单充值错误：" + orderCharge);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("订单充值错误：" + e);
		}
		return null;
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
			 * 必填参数
			 */
			String phoneType = "phoneType";
			String phoneNum = "phoneNum";
			String veCode = "veCode";
			String newPhoneNum = "newPhoneNum";
			String newCode = "newCode";
			boolean b = JsonTool.checkJson(mainObj,phoneType,phoneNum,veCode,newPhoneNum,newCode);
			/**
			 * 请求接口
			 */
			if(b){
				//创建对象
				SysDriver sysDriver = new SysDriver();
				//原电话号码赋值
				sysDriver.setMobilePhone(mainObj.optString("phoneNum"));
				//获取验证码
				String codePay = mainObj.optString("veCode");
				veCode = (String) redisClientImpl.getFromCache(sysDriver.getMobilePhone());
				if(veCode != null && !"".equals(veCode)){
					phoneType = mainObj.optString("phoneType");
					//数据库查询
					List<SysDriver> driver = driverService.queryeSingleList(sysDriver);
					String codeStr = mainObj.optString("phoneNum");
					if(!driver.isEmpty()){
						//新电话号码
						newPhoneNum = mainObj.optString("newPhoneNum");
						//修改账户手机
						if("1".equals(phoneType)){
							sysDriver.setUserName(newPhoneNum);
							sysDriver.setMobilePhone(newPhoneNum);
						}else{
							sysDriver.setSecurityMobilePhone(newPhoneNum);
						}
						sysDriver.setDriverType(driver.get(0).getDriverType());
						sysDriver.setSysDriverId(driver.get(0).getSysDriverId());
						int resultVal = driverService.saveDriver(sysDriver,"update",null);
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
					result.setMsg("验证码无效！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			logger.error("修改账号手机号/密保手机信息 ： " + resultStr);
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
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode(keyStr,params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 必填参数
			 */
			String phoneNum = "phoneNum";
			String veCode = "veCode";
			String newPassword = "newPassword";
			boolean b = JsonTool.checkJson(mainObj,phoneNum,veCode,newPassword);
			/**
			 * 请求接口
			 */
			if(b){
				newPassword = mainObj.optString("newPassword");
				//校验密码
				boolean format = newPassword.matches("^[0-9]*$");
				boolean length = newPassword.matches("^.{6}$");
				if(format & length){
					//创建对象
					SysDriver sysDriver = new SysDriver();
					//电话号码赋值
					sysDriver.setMobilePhone(mainObj.optString("phoneNum"));
					veCode = (String) redisClientImpl.getFromCache(sysDriver.getMobilePhone());
					if(veCode != null && !"".equals(veCode)) {
						//数据库查询
						List<SysDriver> driver = driverService.queryeSingleList(sysDriver);
						if (!driver.isEmpty()) {
							String initialPassword = mainObj.optString("newPassword");
							//初始密码加密、赋值
							initialPassword = Encoder.MD5Encode(initialPassword.getBytes());
							sysDriver.setPayCode(initialPassword);
							sysDriver.setSysDriverId(driver.get(0).getSysDriverId());
							//更新初始密码
							int resultVal = driverService.saveDriver(sysDriver, "update", null);
							//返回大于0，成功
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
					}else {
						result.setStatus(MobileReturn.STATUS_FAIL);
						result.setMsg("验证码无效！");
					}
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("密码格式有误！");
				}
			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			
			resultStr = resutObj.toString();
			logger.error("重置支付密码信息： " + resultStr);
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
			logger.error("查询信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
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
			String Token = "Token";
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
			boolean b = JsonTool.checkJson(mainObj,Token,condition_img,conditionType,flashLongitude,flashLatitude,flashTime,longitude,latitude,address,publisherName,publisherPhone,publisherTime,direction);
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
				roadCondition.setCaptureTime(start);//拍照时间
				roadCondition.setStartTime(start);//开始时间
				//计算结束时间01-60min、02-120min、05-240min，其他为null
				Calendar cal = Calendar.getInstance();
				cal.setTime(start);
				if("01".equals(conditionType)){
					cal.add(Calendar.HOUR,1);
					roadCondition.setEndTime(cal.getTime());
				}else if("02".equals(conditionType)){
					cal.add(Calendar.HOUR,2);
					roadCondition.setEndTime(cal.getTime());
				}else if("05".equals(conditionType)){
					cal.add(Calendar.HOUR,4);
					roadCondition.setEndTime(cal.getTime());
				}else{
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
			/**
			 * 必填参数
			 */
			String pageNum = "pageNum";
			String pageSize = "pageSize";
			boolean b = JsonTool.checkJson(mainObj,pageNum,pageSize);
			/**
			 * 请求接口
			 */
			if (b) {
				if(roadCondition.getPageNum() == null){
					roadCondition.setPageNum(GlobalConstant.PAGE_NUM);
					roadCondition.setPageSize(GlobalConstant.PAGE_SIZE);
				}else{
					roadCondition.setPageNum(mainObj.optInt("pageNum"));
					roadCondition.setPageSize(mainObj.optInt("pageSize"));
				}
				String longitudeStr = mainObj.optString("longitude");
				String latitudeStr = mainObj.optString("latitude");
				String radius = mainObj.optString("radius");
				Double longitude = new Double(0);
				Double latitude = new Double(0);
				Double radiusDb = new Double(0);
				roadCondition.setLongitude(longitudeStr);
				roadCondition.setLatitude(latitudeStr);
				roadCondition.setProvince(mainObj.optString("province"));
				roadCondition.setConditionType(mainObj.optString("conditionType"));
				//获取所有ID(redis中的键为Road+id)
				List<SysRoadCondition> roadIdList = sysRoadService.queryRoadIDList();
				List<SysRoadCondition> redisList = new ArrayList<>();
				for (int i = 0; i < roadIdList.size(); i++) {
					SysRoadCondition sysRoadCondition = (SysRoadCondition) redisClientImpl.getFromCache("Road" + roadIdList.get(i).getId());
					if(sysRoadCondition != null){
						redisList.add(sysRoadCondition);
					}
				}
				//设置页码
				PageHelper.startPage(roadCondition.getPageNum(), roadCondition.getPageSize(), roadCondition.getOrderby());
				PageInfo<SysRoadCondition> pageInfo = new PageInfo<SysRoadCondition>(redisList);
				List<Map<String, Object>> reChargeList = new ArrayList<>();
				Map<String, Object> reCharge = new HashMap<>();
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String http_poms_path =  (String) prop.get("http_poms_path");
				if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {
					for (SysRoadCondition roadConditionInfo : pageInfo.getList()) {
						Map<String, Object> reChargeMap = new HashMap<>();
						reChargeMap.put("roadId", roadConditionInfo.getId());
						reChargeMap.put("conditionType", roadConditionInfo.getConditionType());
						reChargeMap.put("longitude", roadConditionInfo.getLongitude());
						reChargeMap.put("latitude", roadConditionInfo.getLatitude());
						reChargeMap.put("conditionImg", http_poms_path+roadConditionInfo.getConditionImg());
						reChargeMap.put("address", roadConditionInfo.getAddress());
						reChargeMap.put("publisherName", roadConditionInfo.getPublisherName());
						reChargeMap.put("publisherPhone", roadConditionInfo.getPublisherPhone());
						reChargeMap.put("direction", roadConditionInfo.getDirection());
						reChargeMap.put("conditionMsg", roadConditionInfo.getConditionMsg());
						reChargeMap.put("usefulCount", roadConditionInfo.getUsefulCount());
						reChargeMap.put("contentUrl",http_poms_path+"/portal/crm/help/trafficDetail?trafficId="+ roadConditionInfo.getId());
						reChargeMap.put("shareUrl",http_poms_path+"/portal/crm/help/trafficShare?trafficId="+ roadConditionInfo.getId());
						String publisherTime = "";
						if (roadConditionInfo.getPublisherTime() != null && !"".equals(roadConditionInfo.getPublisherTime().toString())) {
							publisherTime = sft.format(roadConditionInfo.getPublisherTime());
						}else{
							publisherTime = sft.format(new Date());
						}
						reChargeMap.put("publisherTime", publisherTime);
						if(longitudeStr != null && !"".equals(longitudeStr) && latitudeStr != null && !"".equals(latitudeStr) && radius != null && !"".equals(radius)){
							longitude = new Double(longitudeStr);
							latitude = new Double(latitudeStr);
							radiusDb = new Double(radius);

							String longStr = roadConditionInfo.getLongitude();
							String langStr = roadConditionInfo.getLatitude();
							Double longDb = new Double(0);
							Double langDb = new Double(0);
							if(longStr != null && !"".equals(longStr) && langStr != null && !"".equals(langStr)){
								longDb = new Double(longStr);
								langDb = new Double(langStr);
							}
							//计算当前加注站离指定坐标距离
							Double dist = DistCnvter.getDistance(longitude,latitude,longDb,langDb);
							if(dist <= radiusDb){//在指定范围内
								reChargeList.add(reChargeMap);
							}
						}else{//目标坐标及范围半径未传参
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
			boolean b = JsonTool.checkJson(mainObj,roadId);
			/**
			 * 请求接口
			 */
			if (b) {
				// 创建对象
				SysRoadCondition roadCondition = sysRoadService.selectByPrimaryKey(mainObj.optString("roadId"));
				int count = Integer.parseInt(roadCondition.getUsefulCount());
				roadCondition.setUsefulCount(String.valueOf(count+1));
				roadCondition.setId(mainObj.optString("roadId"));
				int rs = sysRoadService.updateByPrimaryKey(roadCondition);
				if(rs > 0){
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("统计成功！");
					int time = SysRoadController.sumTime(roadCondition);
					redisClientImpl.addToCache("Road" + roadCondition.getId(), roadCondition, time);
					Map<String, Object> dataMap = new HashMap<>();
					dataMap.put("count", roadCondition.getUsefulCount());
					result.setData(dataMap);
				}else{
					result.setStatus(MobileReturn.STATUS_FAIL);
					result.setMsg("无此路况！");
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
		result.setMsg("取消路况成功！");
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
			boolean b = JsonTool.checkJson(mainObj,roadId,condition_img,conditionType,flashLongitude,flashLatitude,flashTime,longitude,latitude,address,publisherName,publisherPhone,publisherTime);
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
				roadCondition.setPublisherTime(sft.parse(mainObj.optString("flashTime")));
				roadCondition.setRoadId(mainObj.optString("token"));
				int tmp = sysRoadService.cancelSysRoadCondition(roadCondition);
				if (tmp > 0) {
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("取消路况成功！");
				}
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resutObj.remove("data");
			resultStr = resutObj.toString();
			logger.error("取消路况信息： " + resultStr);
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("取消路况失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("取消路况失败： " + e);
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
			boolean b = JsonTool.checkJson(mainObj,token);
			/**
			 * 请求接口
			 */
			if (b) {
				// 创建对象
				SysDriver driver = driverService.queryDriverByPK(mainObj.optString("token"));
				if(driver != null){
					result.setStatus(MobileReturn.STATUS_SUCCESS);
					result.setMsg("获取实名认证信息成功！");
					String gasType = "";
					if(!"".equals(driver.getFuelType())){
						List<Usysparam> list =  usysparamService.query("FUEL_TYPE", driver.getFuelType());
						if(list!=null && list.size() > 0 ){
							for(int i=0;i< list.size();i++){
								if(driver.getFuelType().equals(list.get(i).getMcode())){
									gasType=list.get(i).getMname();
								}
							}
						}
					}
					SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Map<String, Object> dataMap = new HashMap<>();
					String url= (String) prop.get("http_poms_path");
					String vehicleLice="";//驾驶证
					String drivingLice="";//行驶证
					if(driver.getVehicleLice()==null || "".equals(driver.getVehicleLice())){
						vehicleLice="";
					}else{
						if(driver.getVehicleLice().indexOf("http") != -1){
							vehicleLice = driver.getVehicleLice();
						}else{
							vehicleLice = url+driver.getVehicleLice();
						}
					}
					if(driver.getDrivingLice()==null || "".equals(driver.getDrivingLice())){
						drivingLice="";
					}else{
						if(driver.getDrivingLice().indexOf("http") != -1){
							drivingLice = driver.getDrivingLice();
						}else{
							drivingLice = url+driver.getDrivingLice();
						}
					}
					dataMap.put("name", driver.getFullName());
					dataMap.put("plateNumber", driver.getPlateNumber());
					dataMap.put("gasType", gasType);//燃气类型字典表
					dataMap.put("endTime", sft.format(driver.getExpiryDate()));
					dataMap.put("drivingLicenseImageUrl", drivingLice);
					dataMap.put("driverLicenseImageUrl", vehicleLice);
					dataMap.put("idCard", driver.getIdentityCard());
					result.setData(dataMap);
				}else{
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
			boolean b = JsonTool.checkJson(mainObj,msgType,pageNum,pageSize);
			/**
			 * 请求接口
			 */
			if (b) {
				pageNum = mainObj.optString("pageNum");
				pageSize = mainObj.optString("pageSize");
				msgType = mainObj.optString("msgType");
				SysMessage sysMessage = new SysMessage();
				String province = mainObj.optString("province");
				if("2".equals(msgType)){
					if("全国".equals(province)){
						province="";
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
			boolean b = JsonTool.checkJson(mainObj,id,operation,type);
			/**
			 * 请求接口
			 */
			if (b) {
				id = mainObj.optString("id");
				operation = mainObj.optString("operation");
				type = mainObj.optString("type");
				int rs;
				if("1".equals(type)){//路况
					SysRoadCondition sysRoadCondition = sysRoadService.selectByPrimaryKey(id);
					sysRoadCondition.setId(sysRoadCondition.getId());
					if("1".equals(operation)){//阅读
						String viewCount = sysRoadCondition.getViewCount();
						viewCount = String.valueOf(Integer.parseInt(viewCount)+1);
						sysRoadCondition.setViewCount(viewCount);
						rs = sysRoadService.updateRoad(sysRoadCondition);
						if(rs > 0){
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						}else{
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					}else{//分享
						String shareCount = sysRoadCondition.getShareCount();
						shareCount = String.valueOf(Integer.parseInt(shareCount)+1);
						sysRoadCondition.setShareCount(shareCount);
						rs = sysRoadService.updateRoad(sysRoadCondition);
						if(rs > 0){
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						}else{
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					}
				}else if("2".equals(type)){//商家
					Gastation gastation = gastationService.queryGastationByPK(id);
					gastation.setSys_user_account_id(gastation.getSys_gas_station_id());
					if("1".equals(operation)){//阅读
						String viewCount = gastation.getViewCount();
						viewCount = String.valueOf(Integer.parseInt(viewCount)+1);
						gastation.setViewCount(viewCount);
						rs = gastationService.updateByPrimaryKeySelective(gastation);
						if(rs > 0){
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						}else{
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					}else{//分享
						String shareCount = gastation.getShareCount();
						shareCount = String.valueOf(Integer.parseInt(shareCount)+1);
						gastation.setShareCount(shareCount);
						rs = gastationService.updateByPrimaryKeySelective(gastation);
						if(rs > 0){
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						}else{
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					}
				}else if("3".equals(type)){//活动
					MbBanner mbBanner = mbBannerService.selectByPrimaryKey(id);
					mbBanner.setMbBannerId(mbBanner.getMbBannerId());
					if("1".equals(operation)){//阅读
						String viewCount = mbBanner.getViewCount();
						viewCount = String.valueOf(Integer.parseInt(viewCount)+1);
						mbBanner.setViewCount(viewCount);
						rs = mbBannerService.updateBanner(mbBanner);
						if(rs > 0){
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						}else{
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					}else{//分享
						String shareCount = mbBanner.getShareCount();
						shareCount = String.valueOf(Integer.parseInt(shareCount)+1);
						mbBanner.setShareCount(shareCount);
						rs = mbBannerService.updateBanner(mbBanner);
						if(rs > 0){
							result.setStatus(MobileReturn.STATUS_SUCCESS);
						}else{
							result.setStatus(MobileReturn.STATUS_FAIL);
							result.setMsg("操作失败！");
						}
					}
				}else{
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
	public String invitation(String params) {
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
			boolean b = JsonTool.checkJson(mainObj,token);
			/**
			 * 请求接口
			 */

			if (b) {
				Map<String, Object> tokenMap = new HashMap<>();
				token = mainObj.optString("token");
				String http_poms_path =  (String) prop.get("http_poms_path");
				String inviteUrl = http_poms_path+"/portal/crm/help/user/invitation?token="+token;
				/*生成短连接*/


				tokenMap.put("inviteContent","将此链接或邀请码分享给好友，好友通过您的邀请链接或邀请码完成注册并登录后，您的账户即可获得￥10现金充值。");
				tokenMap.put("msgContent","司集专为3000多万卡车司机提供导航、实时路况、气站、油站、会员及周边服务，注册成功之后您的账户即可获得￥10现金充值，详情请访问："+inviteUrl);
				tokenMap.put("title","注册即享司集现金充值");
				tokenMap.put("content","司集专为3000多万卡车司机提供导航、实时路况、气站、油站、会员及周边服务，完成注册并下载司集APP，您即可获得￥10账户充值，可在任意司集联盟站使用！");
				tokenMap.put("imgUrl","默认图片路径");
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

	private String genProductArgs(String orderID, String totalFee) {
		String http_poms_path =  (String) prop.get("http_poms_path");
		try {
			String nonceStr = genNonceStr();
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", APP_ID));//应用ID
			packageParams.add(new BasicNameValuePair("body", "司集云平台-会员充值"));//商品描述 商品描述交易字段格式根据不同的应用场景按照以下格式 APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
			packageParams.add(new BasicNameValuePair("detail", "司集云平台-会员充值"));//商品详情 	商品名称明细列表
			packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));//	微信支付分配的商户号
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));//随机字符串
			packageParams.add(new BasicNameValuePair("notify_url",http_poms_path+"/api/v1/mobile/deal/callBackPay"));//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
			packageParams.add(new BasicNameValuePair("out_trade_no", orderID));//商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
			packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));//用户端实际ip
			packageParams.add(new BasicNameValuePair("total_fee", totalFee));//订单总金额，单位为分，详见支付金额
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
