package com.sysongy.api.mobile.tools.loss;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.loss.ReportLoss;
import com.sysongy.api.mobile.tools.MobileUtils;

public class ReportLossUtil extends MobileUtils{
	
	public static final String RET_LOSS_SUCESS_MSG = "挂失成功";
	
	public static ReportLoss checkReportTheLossParam(MobileParams params, MobileReturn ret) throws Exception{
		
		ReportLoss loss = new ReportLoss();

		checkApiKey(params.getApiKey(), ret);
		
		try {
			loss = (ReportLoss) JSON.parseObject(params.getDetailParam(), ReportLoss.class);
		} catch (Exception e) {
			ret = MobileUtils.packagingMobileReturn(MobileUtils.RET_ERROR, MobileUtils.RET_PARAM_ERROR_MSG, null);
			throw e;
		}
		
		if (loss == null || StringUtils.isEmpty(loss.getToken()) || loss.getToken().length() != 32) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}
		
		if (StringUtils.isEmpty(loss.getLossType()) || loss.getLossType().length() != 1) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}
		
		return loss;
	}
}
