package com.sysongy.api.mobile.tools.verification;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.verification.MobileVerification;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;

public class MobileVerificationUtils extends MobileUtils{
	
	public static final String RET_VERIFICATION_CODE = "验证码已发送";
	
	public static MobileVerification checkVerificationCodeParam(MobileParams param, MobileReturn ret) throws Exception{
		
		MobileVerification verification = new MobileVerification();
		
		try {
			verification = (MobileVerification) JSON.parseObject(param.getDetailParam(), MobileVerification.class);
		} catch (Exception e) {
			ret = MobileLoginUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, MobileLoginUtils.RET_PARAM_ERROR_MSG, null);
			throw e;
		}

		checkApiKey(param.getApiKey(), ret);
		
		if (param == null || param.getDetailParam().length() != 11) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}
		
		return verification;
	}

}
