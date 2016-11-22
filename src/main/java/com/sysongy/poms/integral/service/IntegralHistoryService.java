package com.sysongy.poms.integral.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.integral.model.IntegralHistory;

public interface IntegralHistoryService {

	public PageInfo<IntegralHistory> queryIntegralHistory(IntegralHistory integralHistory) throws Exception;

	public String modifyIntegralHistory(IntegralHistory integralHistory, String userID) throws Exception;

	public String addIntegralHistory(IntegralHistory integralHistory, String userID) throws Exception;

	public Integer delIntegralHistory(String integral_history_id) throws Exception;

	public IntegralHistory queryIntegralHistoryByPK(String integral_history_id) throws Exception;
}
