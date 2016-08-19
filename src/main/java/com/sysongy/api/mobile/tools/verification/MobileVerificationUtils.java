package com.sysongy.api.mobile.tools.verification;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.verification.MobileVerification;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.AliShortMessage.SHORT_MESSAGE_TYPE;
import com.sysongy.util.pojo.AliShortMessageBean;

public class MobileVerificationUtils extends MobileUtils{
	
	public static final String RET_VERIFICATION_CODE = "验证码已发送";
	
	public static final String APP_DRIVER_REG = "1";
	
	
	public static MobileVerification checkVerificationCodeParam(MobileParams param, MobileReturn ret) throws Exception{
		
		MobileVerification verification = new MobileVerification();
		
		try {
			verification = (MobileVerification) JSON.parseObject(param.getDetailParam(), MobileVerification.class);
		} catch (Exception e) {
			ret = MobileUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, MobileLoginUtils.RET_PARAM_ERROR_MSG, null);
			throw e;
		}

		checkApiKey(param.getApiKey(), ret);
		
		if (param == null || param.getDetailParam().length() != 11) {
			/*ret.setError(RET_ERROR);*/
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}
		
		return verification;
	}
	
	public static void sendMSG(MobileVerification verification, String checkCode) throws Exception{
		
        AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
        
		aliShortMessageBean.setSendNumber(verification.getPhoneNum());
		aliShortMessageBean.setProduct(createMSGProductStr(verification.getReqType()));
		aliShortMessageBean.setCode(checkCode.toString());
		
		SHORT_MESSAGE_TYPE msgType = APP_DRIVER_REG.equals(checkCode)?AliShortMessage.SHORT_MESSAGE_TYPE.USER_REGISTER:AliShortMessage.SHORT_MESSAGE_TYPE.USER_CHANGE_PROFILE;
		
		AliShortMessage.sendShortMessage(aliShortMessageBean, msgType);
	}
	
	private static String createMSGProductStr(String reqType) throws Exception{
		
		String productStr = "";
		
		switch (reqType) {
		case "1":
			productStr = "司集能源科技平台";
			break;
		case "2":
			productStr = "登录手机号码";
			break;
		case "3":
			productStr = "密保手机号码";
			break;

		default:
			break;
		}
		
		return productStr;
	}

}
