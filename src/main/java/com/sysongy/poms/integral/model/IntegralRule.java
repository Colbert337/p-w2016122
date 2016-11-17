package com.sysongy.poms.integral.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class IntegralRule extends BaseModel {
	//积分规则ID	
	private String integral_rule_id;
	//积分类别
	private String integral_type;
	//奖励周期
	private String reward_cycle;
	//奖励次数
	private String limit_number;
	//积分方式
	private String integral_reward_type;
	//积分奖励方式
	private String reward_type;
	//消费阶梯前
	private String ladder_before;
	//消费阶梯后
	private String ladder_after;
	//统一积分奖励
	private String integral_reward;
	//区间积分奖励
	private String reward_integral;
	//奖励系数
	private String reward_factor;
	//最大奖励积分
	private String reward_max;
	// 创建人ID
	private String create_person_id;
	// 创建时间
	private Date create_time;
	//选择限制次数
	private String limit;
	// 最后修改人ID
	private String lastmodify_person_id;
	// 最后修改时间
	private Date lastmodify_time;
	//页面显示的积分字符串
	private String integralStr;
	
	
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getIntegralStr() {
		return integralStr;
	}
	public void setIntegralStr(String integralStr) {
		this.integralStr = integralStr;
	}
	public String getReward_integral() {
		return reward_integral;
	}
	public void setReward_integral(String reward_integral) {
		this.reward_integral = reward_integral;
	}
	public String getIntegral_reward_type() {
		return integral_reward_type;
	}
	public void setIntegral_reward_type(String integral_reward_type) {
		this.integral_reward_type = integral_reward_type;
	}
	public String getReward_type() {
		return reward_type;
	}
	public void setReward_type(String reward_type) {
		this.reward_type = reward_type;
	}
	public String getIntegral_rule_id() {
		return integral_rule_id;
	}
	public void setIntegral_rule_id(String integral_rule_id) {
		this.integral_rule_id = integral_rule_id;
	}
	public String getIntegral_type() {
		return integral_type;
	}
	public void setIntegral_type(String integral_type) {
		this.integral_type = integral_type;
	}
	public String getReward_cycle() {
		return reward_cycle;
	}
	public void setReward_cycle(String reward_cycle) {
		this.reward_cycle = reward_cycle;
	}

	public String getLimit_number() {
		return limit_number;
	}
	public void setLimit_number(String limit_number) {
		this.limit_number = limit_number;
	}
	public String getIntegral_reward() {
		return integral_reward;
	}
	public void setIntegral_reward(String integral_reward) {
		this.integral_reward = integral_reward;
	}
	public String getReward_factor() {
		return reward_factor;
	}
	public void setReward_factor(String reward_factor) {
		this.reward_factor = reward_factor;
	}
	public String getReward_max() {
		return reward_max;
	}
	public void setReward_max(String reward_max) {
		this.reward_max = reward_max;
	}
	public String getCreate_person_id() {
		return create_person_id;
	}
	public void setCreate_person_id(String create_person_id) {
		this.create_person_id = create_person_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getLastmodify_person_id() {
		return lastmodify_person_id;
	}
	public void setLastmodify_person_id(String lastmodify_person_id) {
		this.lastmodify_person_id = lastmodify_person_id;
	}
	public Date getLastmodify_time() {
		return lastmodify_time;
	}
	public void setLastmodify_time(Date lastmodify_time) {
		this.lastmodify_time = lastmodify_time;
	}
	public String getLadder_before() {
		return ladder_before;
	}
	public void setLadder_before(String ladder_before) {
		this.ladder_before = ladder_before;
	}
	public String getLadder_after() {
		return ladder_after;
	}
	public void setLadder_after(String ladder_after) {
		this.ladder_after = ladder_after;
	}	
}
