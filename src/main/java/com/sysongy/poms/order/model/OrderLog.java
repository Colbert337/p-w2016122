package com.sysongy.poms.order.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class OrderLog extends BaseModel {
	
	private String order_id;
	private String order_number;
	private String deal_number;
	private String remark;
	private String order_type;
	private String operator;
	private String run_success;
	private Date deal_date;
	private String deal_date_after;
	private String deal_date_before;
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeal_date_after() {
		return deal_date_after;
	}
	public void setDeal_date_after(String deal_date_after) {
		this.deal_date_after = deal_date_after;
	}
	public String getDeal_date_before() {
		return deal_date_before;
	}
	public void setDeal_date_before(String deal_date_before) {
		this.deal_date_before = deal_date_before;
	}

	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getDeal_number() {
		return deal_number;
	}
	public void setDeal_number(String deal_number) {
		this.deal_number = deal_number;
	}

	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRun_success() {
		return run_success;
	}
	public void setRun_success(String run_success) {
		this.run_success = run_success;
	}
	public Date getDeal_date() {
		return deal_date;
	}
	public void setDeal_date(Date deal_date) {
		this.deal_date = deal_date;
	}
	
	
}
