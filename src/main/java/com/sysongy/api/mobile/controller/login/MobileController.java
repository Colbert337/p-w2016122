package com.sysongy.api.mobile.controller.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sysongy.api.mobile.model.Login.MobileLogin;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysUserService;

@RequestMapping("/api/mobile/user/")
@Controller
public class MobileController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SysUserService sysUserService;

	@RequestMapping(value = { "Login" })
	@ResponseBody
	public MobileReturn Login(MobileParams params) {

		MobileLogin mobileLogin = new MobileLogin();
		MobileReturn ret = new MobileReturn();
		try {
			
			try {
				mobileLogin = (MobileLogin) JSON.parse(params.getDetailParam());
			} catch (Exception e) {
				ret = MobileLoginUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, MobileLoginUtils.RET_LOGINPARAM_ERROR_MSG, null);

				logger.error("MobileController.Login ERROR： " + e);
			}

			MobileLoginUtils.checkParam(mobileLogin, ret);

			SysUser sysUser = MobileLoginUtils.packagingSysUser(mobileLogin);

			sysUser = sysUserService.queryUserMapByAccount(sysUser);

			MobileLoginUtils.checkLogin(sysUser, ret);
			
			JSONObject json = new JSONObject();
			json.put("token", sysUser.getSysUserId());

			MobileLoginUtils.packagingMobileReturn(MobileLoginUtils.RET_SUCCESS, null, json.toJSONString());

		} catch (Exception e) {
			ret = MobileLoginUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, null, null);

			logger.error("MobileController.Login ERROR： " + e);
		} finally {
			return ret;
		}
	}

}
