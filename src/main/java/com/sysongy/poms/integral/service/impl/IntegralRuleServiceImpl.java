package com.sysongy.poms.integral.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.sysongy.util.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.sysongy.poms.integral.model.IntegralRule;
import com.sysongy.poms.integral.dao.IntegralRuleMapper;
import com.sysongy.poms.integral.service.IntegralRuleService;

@Service
public class IntegralRuleServiceImpl implements IntegralRuleService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IntegralRuleMapper integralRuleMapper;


	@Override
	public PageInfo<IntegralRule> queryIntegralRule(IntegralRule integralRule) throws Exception {
		if (integralRule.getPageNum() == null) {
			integralRule.setPageNum(1);
			integralRule.setPageSize(10);
		}
		if (StringUtils.isEmpty(integralRule.getOrderby())) {
			integralRule.setOrderby("lastmodify_time desc");
		}
		PageHelper.startPage(integralRule.getPageNum(), integralRule.getPageSize(), integralRule.getOrderby());
		List<IntegralRule> list = integralRuleMapper.queryForPage(integralRule);
		for(int i=0;i<list.size();i++){
			IntegralRule aIntegralRule = list.get(i);
			if("2".equals(aIntegralRule.getIntegral_reward_type())){
				String[] ladder_befores = aIntegralRule.getLadder_before().split(",");
				String[] ladder_afters = aIntegralRule.getLadder_after().split(",");
				String[] reward_factors = aIntegralRule.getReward_factor().split(",");
				String[] reward_maxs = aIntegralRule.getReward_max().split(",");
				String[] integral_rewards = aIntegralRule.getIntegral_reward().split(",");
				String[] reward_types = aIntegralRule.getReward_type().split(",");
				String integralStrs ="";
				for(int j=0;j<ladder_befores.length;j++){
					String ladderStr = "";
					String integralStr = "";
					ladderStr = "积分阶梯为："+ladder_befores[j]+"元~"+ladder_afters[j]+"元，";
					if("1".equals(reward_types[j])){
						integralStr = integral_rewards[j]+"分";
					}else{
						integralStr = "金额*"+reward_factors[j]+"<="+reward_maxs[j]+"分";
					}
					integralStrs+=ladderStr+integralStr+",";
				}
				integralStrs = integralStrs.substring(0, integralStrs.length()-1);
				aIntegralRule.setIntegralStr(integralStrs);
			}
		}
		
		PageInfo<IntegralRule> pageInfo = new PageInfo<IntegralRule>(list);
		return pageInfo;
	}

	@Override
	public IntegralRule queryIntegralRuleByPK(String integral_rule_id) throws Exception {
		return integralRuleMapper.selectByPrimaryKey(integral_rule_id);
	}

	@Override
	public String modifyIntegralRule(IntegralRule integralRule, String userID) throws Exception {
		integralRule.setLastmodify_person_id(userID);
		integralRule.setLastmodify_time(new Date());
		//积分类型
		if("2".equals(integralRule.getIntegral_reward_type())){
			integralRule.setIntegral_reward(integralRule.getReward_integral());
		}
		//限制次数
		if("2".equals(integralRule.getLimit())){
			integralRule.setLimit_number("不限");
		}
		integralRuleMapper.updateByPrimaryKey(integralRule);
		return integralRule.getIntegral_rule_id();
	}

	@Override
	public String addIntegralRule(IntegralRule integralRule, String userID) throws Exception {
		integralRule.setIntegral_rule_id(UUIDGenerator.getUUID());
		integralRule.setCreate_person_id(userID);
		integralRule.setCreate_time(new Date());
		integralRule.setLastmodify_person_id(userID);
		integralRule.setLastmodify_time(new Date());
		//积分类型
		if("2".equals(integralRule.getIntegral_reward_type())){
			integralRule.setIntegral_reward(integralRule.getReward_integral());
		}
		//限制次数
		if("2".equals(integralRule.getLimit())){
			integralRule.setLimit_number("不限");
		}
		integralRuleMapper.insert(integralRule);
		return integralRule.getIntegral_rule_id();
	}

	@Override
	public Integer delIntegralRule(String integral_rule_id) throws Exception {
		return integralRuleMapper.deleteByPrimaryKey(integral_rule_id);
	}
	@Override
	public HashMap<String,String> selectRepeatIntegralType(String integral_type) throws Exception{
		return integralRuleMapper.selectRepeatIntegralType(integral_type);
	}

}
