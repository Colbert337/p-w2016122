package com.sysongy.api.mobile.tools.register;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.register.MobileRegister;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;
import com.sysongy.util.RedisClientInterface;

public class MobileRegisterUtils extends MobileUtils {
	
	public static final String RET_VERIFICATION_ERROR = "验证码验证失败";
	public static final String RET_DRIVER_MOBILE_REGISTED = "该手机号已注册";
	
	public static MobileRegister checkRegisterParam(MobileParams param, MobileReturn ret, RedisClientInterface redisClientImpl) throws Exception{
		
		MobileRegister register = new MobileRegister();

		checkApiKey(param.getApiKey(), ret);
		
		try {
			register = (MobileRegister) JSON.parseObject(param.getDetailParam(), MobileRegister.class);
		} catch (Exception e) {
			ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_ERROR, MobileUtils.RET_PARAM_ERROR_MSG, null);
			throw e;
		}
		
		/*if (register == null || StringUtils.isEmpty(register.getPhoneNum()) || register.getPhoneNum().length() != 11) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}
		
		if (StringUtils.isEmpty(register.getVerificationCode()) || StringUtils.isEmpty(register.getPassword()) || register.getPassword().length()>50) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}
		
		String verificationCode = (String) redisClientImpl.getFromCache(register.getPhoneNum());
		if(!register.getVerificationCode().equals(verificationCode)){
			ret.setError(RET_ERROR);
			ret.setMsg(RET_VERIFICATION_ERROR);
			throw new Exception(RET_VERIFICATION_ERROR);
		}*/
		
		return register;
	}
}
