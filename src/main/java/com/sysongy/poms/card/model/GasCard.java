package com.sysongy.poms.card.model;

import java.util.Date;
import java.util.List;

import com.sysongy.poms.base.model.BaseModel;

public class GasCard extends BaseModel{
	
	private String card_no;  //用户卡号
	
	private String card_no_arr;
	
	private List<String> card_no_list;
	
	private String card_type; //用户卡类型 0:LNG  1:柴油  2:CNG
	
	private String card_name;

	private String card_status; //用户卡状态  0:已冻结  1:未使用  2:使用中

	private String workstation; //用户卡所在地
	
	private String workstation_resp; //出入地责任人
	
	private String operator; //操作员
	
	private Date storage_time;  //入库时间

	private Date release_time;  //出库时间
	
	private String release_time_range;
	
	private String release_time_before;
	
	private String release_time_after;

	private String memo;

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCard_no_arr() {
		return card_no_arr;
	}

	public void setCard_no_arr(String card_no_arr) {
		this.card_no_arr = card_no_arr;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
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
	
	public List<String> getCard_no_list() {
		return card_no_list;
	}

	public void setCard_no_list(List<String> card_no_list) {
		this.card_no_list = card_no_list;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_status() {
		return card_status;
	}

	public void setCard_status(String card_status) {
		this.card_status = card_status;
	}

	public String getWorkstation() {
		return workstation;
	}

	public void setWorkstation(String workstation) {
		this.workstation = workstation;
	}

	public String getWorkstation_resp() {
		return workstation_resp;
	}

	public void setWorkstation_resp(String workstation_resp) {
		this.workstation_resp = workstation_resp;
	}

}