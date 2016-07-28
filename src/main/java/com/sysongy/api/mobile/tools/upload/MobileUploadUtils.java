package com.sysongy.api.mobile.tools.upload;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.upload.MobileUpload;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;

public class MobileUploadUtils extends MobileUtils{
	
	public static final String RET_USERINFO_SUCCESS = "获取司机用户信息成功";
	
	public static MobileUpload checkUploadParam(MobileParams params, MobileReturn ret) throws Exception{
		
		MobileUpload upload = new MobileUpload();
		
		try {
			upload = (MobileUpload) JSON.parseObject(params.getDetailParam(), MobileUpload.class);
		} catch (Exception e) {
			ret = MobileUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, MobileLoginUtils.RET_PARAM_ERROR_MSG, null);
			throw e;
		}

		checkApiKey(params.getApiKey(), ret);
		
		if (StringUtils.isEmpty(upload.getReqType()) || StringUtils.isEmpty(upload.getToken()) || params.getFile() == null) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}
		
		return upload;
	}

}
