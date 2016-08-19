package com.sysongy.api.mobile.tools.record;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.record.MobileRecord;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;

public class MobileRecordUtils extends MobileUtils {
	
	public static final String METHOD_RECHARGE = "1";
	public static final String METHOD_CONSUME = "2";
	public static final String METHOD_TRANSFER = "3";
	public static final String RET_SUCCESS_MSG = "查询成功";
	
	public static MobileRecord checkParam(MobileParams param, MobileReturn ret) throws Exception {

		MobileRecord record = new MobileRecord();

		checkApiKey(param.getApiKey(), ret);

		try {
			record = (MobileRecord) JSON.parseObject(param.getDetailParam(), MobileRecord.class);
		} catch (Exception e) {
			ret = MobileUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, MobileUtils.RET_PARAM_ERROR_MSG, null);
			throw e;
		}

		if (record == null || StringUtils.isEmpty(record.getPageSize()) || StringUtils.isEmpty(record.getCurrentPage())) {
			/*ret.setError(RET_ERROR);*/
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}

		if (StringUtils.isEmpty(record.getRecordType()) || StringUtils.isEmpty(record.getTime())) {
			/*ret.setError(RET_ERROR);*/
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}

		return record;
	}
}
