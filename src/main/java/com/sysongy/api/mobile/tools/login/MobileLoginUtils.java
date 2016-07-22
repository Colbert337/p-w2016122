package com.sysongy.api.mobile.tools.login;

import org.apache.commons.lang.StringUtils;

import com.sysongy.api.mobile.model.Login.MobileLogin;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.poms.permi.model.SysUser;

public class MobileLoginUtils {

	public static final String RET_SUCCESS = "0";
	public static final String RET_ERROR = "1";

	public static final String RET_SUCCESS_MSG = "登录成功";
	public static final String RET_ERROR_MSG = "系统繁忙";
	public static final String RET_LOGINPARAM_ERROR_MSG = "请求参数格式不正确";
	public static final String RET_LOGINPARAM_NULL_MSG = "用户名或密码为空";
	public static final String RET_LOGIN_ERROR_MSG = "用户名或密码错误";

	public static void checkParam(MobileLogin login, MobileReturn ret) {

		if (StringUtils.isEmpty(login.getUsername()) || StringUtils.isEmpty(login.getPassword())) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_LOGINPARAM_NULL_MSG);
		}
	}

	public static void checkLogin(SysUser sysUser, MobileReturn ret) {

		if (StringUtils.isEmpty(sysUser.getSysUserId())) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_LOGIN_ERROR_MSG);
		}
	}

	public static SysUser packagingSysUser(MobileLogin login) {

		SysUser sysUser = new SysUser();

		sysUser.setUserName(login.getUsername());
		sysUser.setPassword(login.getPassword());

		return sysUser;
	}

	public static MobileReturn packagingMobileReturn(String error, String msg, String data) {

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
