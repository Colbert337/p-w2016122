package com.sysongy.poms.integral.service;


import com.github.pagehelper.PageInfo;
import com.sysongy.poms.integral.model.IntegralRule;


public interface IntegralRuleService {

	public PageInfo<IntegralRule> queryIntegralRule(IntegralRule integralRule) throws Exception;

	public String modifyIntegralRule( IntegralRule integralRule,String userID) throws Exception;
	
	public String addIntegralRule(IntegralRule integralRule,String userID) throws Exception;

	public Integer delIntegralRule(String integral_rule_id) throws Exception;
	
	public IntegralRule queryIntegralRuleByPK(String integral_rule_id) throws Exception;
	
	public String selectRepeatIntegralType(String integral_type) throws Exception;
	
}
