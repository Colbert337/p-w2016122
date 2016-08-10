package com.sysongy.api.mobile.tools.returncash;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.verification.MobileVerification;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;

public class ReturnCashUtil extends MobileUtils {
	
public static void checkParam(MobileParams param, MobileReturn ret) throws Exception{
		
//		try {
//			verification = (MobileVerification) JSON.parseObject(param.getDetailParam(), MobileVerification.class);
//		} catch (Exception e) {
//			ret = MobileUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, MobileLoginUtils.RET_PARAM_ERROR_MSG, null);
//			throw e;
//		}
//
//		checkApiKey(param.getApiKey(), ret);
//		
//		if (param == null || param.getDetailParam().length() != 11) {
//			ret.setError(RET_ERROR);
//			ret.setMsg(RET_PARAM_ERROR_MSG);
//			throw new Exception(RET_PARAM_ERROR_MSG);
//		}
	}
}
