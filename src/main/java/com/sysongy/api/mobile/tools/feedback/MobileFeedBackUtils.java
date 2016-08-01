package com.sysongy.api.mobile.tools.feedback;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.feedback.MobileFeedBack;
import com.sysongy.api.mobile.tools.MobileUtils;

public class MobileFeedBackUtils extends MobileUtils{
	
	public static final String RET_FEEDBACK_PARAM_NULL_MSG = "用户名或密码为空";
	public static final String RET_LOGIN_ERROR_MSG = "用户名或密码错误";

	public static MobileFeedBack checkFeedBackParam(MobileParams params, MobileReturn ret) throws Exception{
		
		MobileFeedBack feedback = new MobileFeedBack();
		
		try {
			feedback = (MobileFeedBack) JSON.parseObject(params.getDetailParam(), MobileFeedBack.class);
		} catch (Exception e) {
			ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_ERROR, MobileUtils.RET_PARAM_ERROR_MSG, null);
			throw e;
		}
		
		checkApiKey(params.getApiKey(), ret);
		
		if (feedback == null || StringUtils.isEmpty(feedback.getToken()) || StringUtils.isEmpty(feedback.getMsg())) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}
		
		return feedback;
	}
}
