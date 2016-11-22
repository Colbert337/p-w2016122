package com.sysongy.poms.integral.dao;


import java.util.List;

import com.sysongy.poms.integral.model.IntegralHistory;


public interface IntegralHistoryMapper {

	List<IntegralHistory> queryForPage(IntegralHistory integralHistory);
	
	 int deleteByPrimaryKey(String integral_history_id);
	 
	 int insert(IntegralHistory integralHistory);
	 
	 IntegralHistory selectByPrimaryKey(String integral_history_id);
	 
	 int updateByPrimaryKey(IntegralHistory integralHistory);
	 
}