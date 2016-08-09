package com.sysongy.api.mobile.model.base;

import java.util.ArrayList;

public class MobileReturn {
	
	private String error;
	
	private String msg;
	
	private ArrayList<Data> data;
	
	private String totalAccountFor;
	
	private String totalReturn;
	
	public String getTotalAccountFor() {
		return totalAccountFor;
	}
	public void setTotalAccountFor(String totalAccountFor) {
		this.totalAccountFor = totalAccountFor;
	}
	public String getTotalReturn() {
		return totalReturn;
	}
	public void setTotalReturn(String totalReturn) {
		this.totalReturn = totalReturn;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ArrayList<Data> getData() {
		return data;
	}
	public void setData(ArrayList<Data> data) {
		this.data = data;
	}
}
