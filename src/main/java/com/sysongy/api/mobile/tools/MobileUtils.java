package com.sysongy.api.mobile.tools;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sysongy.api.mobile.model.base.Data;
import com.sysongy.api.mobile.model.base.MobileReturn;

public class MobileUtils {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static final String API_KEY = "SYSONGYMOBILE2016726";

	public static final String RET_SUCCESS = "0";
	public static final String RET_ERROR = "1";

	public static final String RET_SUCCESS_MSG = "登录成功";
	public static final String RET_ERROR_MSG = "系统繁忙";
	public static final String RET_PARAM_ERROR_MSG = "请求参数格式不正确";

	public static void checkApiKey(String apiKey, MobileReturn ret) throws Exception{
		if(!API_KEY.equals(apiKey)){
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}
	}
	
	public static MobileReturn packagingMobileReturn(String error, String msg, ArrayList<Data> data) throws Exception{

		MobileReturn ret = new MobileReturn();

		ret.setError(error);
		ret.setData(data);
		if(StringUtils.isEmpty(msg)){
			switch (error) {
			case RET_SUCCESS:
				ret.setMsg(RET_SUCCESS_MSG);
				break;

			case RET_ERROR:
				ret.setMsg(RET_ERROR_MSG);
				break;

			default:
				ret.setMsg(msg == null?"":msg);
			}
		}

		return ret;
	}
}
