package com.sysongy.poms.system.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class SysOperationLog extends BaseModel {
	//日志ID
	private String sysOperationLogId;
	//用户类型：1、app用户 2、微信用户 3、CRM用户 4、后台用户
	private String logPlatform;
	//系统模块
	private String systemModule;
	//操作域
	private String operation_domain;
	//操作类型，kh开户，cz充值，xf消费，dj冻结，jd解冻，bcj补换卡，ch冲红
	private String operation_type;
	//订单号
	private String order_number;
	//日志内容
	private String logContent;
	//操作人员ID
	private String operator_id;
	//用户角色
	private String sys_role_id;
	//用户账户
	private String user_name;
	//用户IP
	private String user_ip;
	//创建时间
	private Date created_date;

	private String created_date_after;

	private String created_date_before;

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

	public String getSysOperationLogId() {
		return sysOperationLogId;
	}

	public void setSysOperationLogId(String sysOperationLogId) {
		this.sysOperationLogId = sysOperationLogId == null ? null : sysOperationLogId.trim();
	}

	public String getLogPlatform() {
		return logPlatform;
	}

	public void setLogPlatform(String logPlatform) {
		this.logPlatform = logPlatform == null ? null : logPlatform.trim();
	}

	public String getSystemModule() {
		return systemModule;
	}

	public void setSystemModule(String systemModule) {
		this.systemModule = systemModule == null ? null : systemModule.trim();
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent == null ? null : logContent.trim();
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id == null ? null : operator_id.trim();
	}

	public String getSys_role_id() {
		return sys_role_id;
	}

	public void setSys_role_id(String sys_role_id) {
		this.sys_role_id = sys_role_id == null ? null : sys_role_id.trim();
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name == null ? null : user_name.trim();
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip == null ? null : user_ip.trim();
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getOperation_domain() {
		return operation_domain;
	}

	public void setOperation_domain(String operation_domain) {
		this.operation_domain = operation_domain;
	}

	public String getOperation_type() {
		return operation_type;
	}

	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

}