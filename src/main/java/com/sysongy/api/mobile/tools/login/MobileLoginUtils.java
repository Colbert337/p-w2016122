package com.sysongy.api.mobile.tools.login;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.login.MobileLogin;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.poms.driver.model.SysDriver;

public class MobileLoginUtils extends MobileUtils{
	
	public static final String RET_LOGINPARAM_NULL_MSG = "用户名或密码为空";
	public static final String RET_LOGIN_ERROR_MSG = "用户名或密码错误";

	public static void checkLoginParam(MobileLogin login, String apiKey, MobileReturn ret) throws Exception{
		
		checkApiKey(apiKey, ret);
		
		if (login == null || StringUtils.isEmpty(login.getUsername()) || StringUtils.isEmpty(login.getPassword())) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_LOGINPARAM_NULL_MSG);
			throw new Exception(RET_LOGINPARAM_NULL_MSG);
		}
	}

	public static void checkLogin(List<SysDriver> driver, MobileReturn ret) throws Exception{

		if (driver == null || driver.size() != 1 || StringUtils.isEmpty(driver.get(0).getSysDriverId())) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_LOGIN_ERROR_MSG);
			throw new Exception(RET_LOGIN_ERROR_MSG);
		}
	}

	public static SysDriver packagingSysDriver(MobileLogin login) throws Exception{

		SysDriver driver = new SysDriver();

		driver.setUserName(login.getUsername());
		driver.setPassword(login.getPassword());

		return driver;
	}

}
