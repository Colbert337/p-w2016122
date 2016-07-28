package com.sysongy.api.mobile.tools.userinfo;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.userinfo.MobileUserInfo;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;

public class MobileGetUserInfoUtils extends MobileUtils {
	
	public static final String RET_USERINFO_SUCCESS = "获取司机用户信息成功";
	public static final String RET_USERINFO_ERROR = "获取司机用户信息失败";
	
	public static MobileUserInfo checkGetUserInfoParam(MobileParams param, MobileReturn ret) throws Exception{
		
		MobileUserInfo userinfo = new MobileUserInfo();
		
		try {
			userinfo = (MobileUserInfo) JSON.parseObject(param.getDetailParam(), MobileUserInfo.class);
		} catch (Exception e) {
			ret = MobileLoginUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, MobileLoginUtils.RET_PARAM_ERROR_MSG, null);
			throw e;
		}
		
		checkApiKey(param.getApiKey(), ret);

		if (param == null || StringUtils.isEmpty(param.getDetailParam()) || param.getDetailParam().length() != 32) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}
		
		return userinfo;
	}
}
