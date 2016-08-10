package com.sysongy.api.mobile.tools.getcitys;

import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.tools.MobileUtils;

public class GetCitysUtils extends MobileUtils{
	
	public static final String RET_QUERY_SUCCESS_MSG = "查询成功";

	public static void checkParam(MobileParams params, MobileReturn ret) throws Exception{
		checkApiKey(params.getApiKey(), ret);
	}
}
