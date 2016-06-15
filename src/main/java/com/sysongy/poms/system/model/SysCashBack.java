package com.sysongy.poms.system.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class SysCashBack extends BaseModel{

	private String sys_cash_back_id;
	
	private String sys_cash_back_no;

	private String threshold_min_value;

	private String threshold_max_value;

	private String cash_per;

	private String status;

	private String level;

	private Date start_date;

	private Date end_date;

	private Date created_date;

	private Date updated_date;
	
	private String start_date_after;
	
	private String start_date_before;

	public String getSys_cash_back_no() {
		return sys_cash_back_no;
	}

	public void setSys_cash_back_no(String sys_cash_back_no) {
		this.sys_cash_back_no = sys_cash_back_no;
	}

	public String getStart_date_after() {
		return start_date_after;
	}

	public void setStart_date_after(String start_date_after) {
		this.start_date_after = start_date_after;
	}

	public String getStart_date_before() {
		return start_date_before;
	}

	public void setStart_date_before(String start_date_before) {
		this.start_date_before = start_date_before;
	}

	public String getSys_cash_back_id() {
		return sys_cash_back_id;
	}

	public void setSys_cash_back_id(String sys_cash_back_id) {
		this.sys_cash_back_id = sys_cash_back_id;
	}

	public String getThreshold_min_value() {
		return threshold_min_value;
	}

	public void setThreshold_min_value(String threshold_min_value) {
		this.threshold_min_value = threshold_min_value;
	}

	public String getThreshold_max_value() {
		return threshold_max_value;
	}

	public void setThreshold_max_value(String threshold_max_value) {
		this.threshold_max_value = threshold_max_value;
	}

	public String getCash_per() {
		return cash_per;
	}

	public void setCash_per(String cash_per) {
		this.cash_per = cash_per;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

}