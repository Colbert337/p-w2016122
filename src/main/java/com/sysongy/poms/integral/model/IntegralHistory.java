package com.sysongy.poms.integral.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class IntegralHistory extends BaseModel {
	// 积分历史记录ID
	private String integral_history_id;
	//司机ID
	private String sys_driver_id;
	//积分规则ID
	private String integral_rule_id;
	//积分类别
	private String integral_type;	
	//积分数量
	private String integral_num;
	// 创建人ID
	private String create_person_id;
	// 创建时间
	private Date create_time;
	// 最后修改人ID
	private String lastmodify_person_id;
	// 最后修改时间
	private Date lastmodify_time;
	//司机名称
	private String full_name;
	//创建时间之前
	private String created_date_after;
	//创建时间之后
	private String created_date_before;
	//积分区间开始
	private String integral_num_less;
	//积分区间结束
	private String integral_num_more;
	//会员账号
	private String user_name;	
	//创建时间字符格式
	private String createdTimeStr;
	//创建时间字符格式
	private String integral_total;	
	
	public String getIntegral_total() {
		return integral_total;
	}

	public void setIntegral_total(String integral_total) {
		this.integral_total = integral_total;
	}

	public String getCreatedTimeStr() {
		return createdTimeStr;
	}

	public void setCreatedTimeStr(String createdTimeStr) {
		this.createdTimeStr = createdTimeStr;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}

	public String getRegis_source() {
		return regis_source;
	}

	public void setRegis_source(String regis_source) {
		this.regis_source = regis_source;
	}
	//注册工作站编号
	private String station_id;
	//注册工作站名称
	private String regis_source;	
	
	
	public String getIntegral_type() {
		return integral_type;
	}

	public void setIntegral_type(String integral_type) {
		this.integral_type = integral_type;
	}

	public String getIntegral_num_less() {
		return integral_num_less;
	}

	public void setIntegral_num_less(String integral_num_less) {
		this.integral_num_less = integral_num_less;
	}

	public String getIntegral_num_more() {
		return integral_num_more;
	}

	public void setIntegral_num_more(String integral_num_more) {
		this.integral_num_more = integral_num_more;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getCreated_date_after() {
		return created_date_after;
	}

	public void setCreated_date_after(String created_date_after) {
		this.created_date_after = created_date_after;
	}

	public String getCreated_date_before() {
		return created_date_before;
	}

	public void setCreated_date_before(String created_date_before) {
		this.created_date_before = created_date_before;
	}
	
	public String getIntegral_history_id() {
		return integral_history_id;
	}
	public void setIntegral_history_id(String integral_history_id) {
		this.integral_history_id = integral_history_id;
	}
	public String getSys_driver_id() {
		return sys_driver_id;
	}
	public void setSys_driver_id(String sys_driver_id) {
		this.sys_driver_id = sys_driver_id;
	}
	public String getIntegral_rule_id() {
		return integral_rule_id;
	}
	public void setIntegral_rule_id(String integral_rule_id) {
		this.integral_rule_id = integral_rule_id;
	}
	public String getIntegral_num() {
		return integral_num;
	}
	public void setIntegral_num(String integral_num) {
		this.integral_num = integral_num;
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
	
}