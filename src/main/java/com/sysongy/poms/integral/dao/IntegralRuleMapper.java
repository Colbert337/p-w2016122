package com.sysongy.poms.integral.dao;

import java.util.HashMap;
import java.util.List;

import com.sysongy.poms.integral.model.IntegralRule;



public interface IntegralRuleMapper {

	List<IntegralRule> queryForPage(IntegralRule integralRule);
	
	 int deleteByPrimaryKey(String id);
	 
	 int insert(IntegralRule integralRule);
	 
	 IntegralRule selectByPrimaryKey(String coupongroup_id);
	 
	 int updateByPrimaryKey(IntegralRule integralRule);

	 HashMap<String,String> selectRepeatIntegralType(String integral_type);
	 
}