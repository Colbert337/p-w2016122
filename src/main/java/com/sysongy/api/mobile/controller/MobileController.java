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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
						resultMap.put("company",driver.getStationId());
						resultMap.put("cardId",driver.getCardId());
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
				String cardId = mainObj.optString("cardId");
				int retvale = 0;//操作影响行数
				if(lossType != null){//类型等于0 或者等于1
					retvale = sysUserAccountService.changeStatus(driver.getAccount().getSysUserAccountId(), lossType, driver.getCardInfo().getCard_no());
				}

				if(retvale >0 ){
					result.setStatus(MobileReturn.STATUS_FAIL);
					if("2".equals(lossType)){
						failStr = "解除挂失";
					}else{
						failStr = ("挂失失败");
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
	@RequestMapping(value = "/deal/cashBack")
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
						bannerMap.put("imageUrl",http_poms_path+banner.getImgPath());

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
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("查询头条推广成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询头条推广失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询头条推广失败： " + e);
			resutObj.remove("data");
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
	@RequestMapping(value = "/deal/recharge")
	@ResponseBody
	public String getRecharge(String params){
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


			}else{
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数解密

			logger.error("查询翻新金额成功： " + resultStr);

		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("查询翻新金额失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("查询翻新金额失败： " + e);
			resutObj.remove("listMap");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr,resultStr);//参数加密
			return resultStr;
		} finally {
			return resultStr;
		}
	}

}
