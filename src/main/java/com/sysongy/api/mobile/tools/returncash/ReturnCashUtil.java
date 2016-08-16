package com.sysongy.api.mobile.tools.returncash;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.returncash.MobileReturnCash;
import com.sysongy.api.mobile.tools.MobileUtils;

public class ReturnCashUtil extends MobileUtils {

	public static void checkParam(MobileParams param, MobileReturn ret) throws Exception {

		MobileReturnCash returnCash = new MobileReturnCash();

		try {
			returnCash = (MobileReturnCash) JSON.parseObject(param.getDetailParam(), MobileReturnCash.class);
		} catch (Exception e) {
			ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_ERROR, MobileUtils.RET_PARAM_ERROR_MSG, null);
			throw e;
		}

		checkApiKey(param.getApiKey(), ret);

		/*if (StringUtils.isEmpty(returnCash.getAmount()) || StringUtils.isEmpty(returnCash.getSys_driver_id())) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}*/
	}
	
	public static void calcCashBack(MobileReturnCash returnCash) throws Exception {
		
	}
}
