package com.sysongy.poms.gastation.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class Deposit extends BaseModel {
	
	private String station_id;
	private String account_id;
	private String deposit;
	private Date deposit_time;
	private Date optime;
	private String deposit_type;
	private String operator;
	private String memo;

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public Date getDeposit_time() {
		return deposit_time;
	}

	public void setDeposit_time(Date deposit_time) {
		this.deposit_time = deposit_time;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public String getDeposit_type() {
		return deposit_type;
	}

	public void setDeposit_type(String deposit_type) {
		this.deposit_type = deposit_type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
