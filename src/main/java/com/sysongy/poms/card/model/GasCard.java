package com.sysongy.poms.card.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class GasCard extends BaseModel{
	private String card_id;

	private String card_no;

	private Integer card_type;
	
	private String card_name;

	private Integer card_status;

	private String operator;

	private Date storage_time;

	private Date release_time;
	
	private String release_time_range;
	
	private String release_time_before;
	
	private String release_time_after;

	private String memo;

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public Integer getCard_type() {
		return card_type;
	}

	public void setCard_type(Integer card_type) {
		this.card_type = card_type;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public Integer getCard_status() {
		return card_status;
	}

	public void setCard_status(Integer card_status) {
		this.card_status = card_status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getStorage_time() {
		return storage_time;
	}

	public void setStorage_time(Date storage_time) {
		this.storage_time = storage_time;
	}

	public Date getRelease_time() {
		return release_time;
	}

	public void setRelease_time(Date release_time) {
		this.release_time = release_time;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getRelease_time_range() {
		return release_time_range;
	}

	public void setRelease_time_range(String release_time_range) {
		this.release_time_range = release_time_range;
	}

	public String getRelease_time_before() {
		return release_time_before;
	}

	public void setRelease_time_before(String release_time_before) {
		this.release_time_before = release_time_before;
	}

	public String getRelease_time_after() {
		return release_time_after;
	}

	public void setRelease_time_after(String release_time_after) {
		this.release_time_after = release_time_after;
	}

}