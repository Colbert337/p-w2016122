package com.sysongy.poms.mobile.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class Suggest  extends BaseModel{
	private String mb_user_suggest_id;
	private String sys_driver_id;
	private String mobile_phone;
	private String suggest;
	private String suggest_res;
	private String memo;
	private String follow_up;
	private String user_name;
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	private Date created_date;
	private Date updated_date;

	public String getMb_user_suggest_id() {
		return mb_user_suggest_id;
	}

	public void setMb_user_suggest_id(String mb_user_suggest_id) {
		this.mb_user_suggest_id = mb_user_suggest_id;
	}

	public String getSys_driver_id() {
		return sys_driver_id;
	}

	public void setSys_driver_id(String sys_driver_id) {
		this.sys_driver_id = sys_driver_id;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getSuggest_res() {
		return suggest_res;
	}

	public void setSuggest_res(String suggest_res) {
		this.suggest_res = suggest_res;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFollow_up() {
		return follow_up;
	}

	public void setFollow_up(String follow_up) {
		this.follow_up = follow_up;
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
