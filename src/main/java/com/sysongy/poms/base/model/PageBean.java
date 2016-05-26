package com.sysongy.poms.base.model;

public class PageBean extends BaseModel {
	
	private String retValue;
	private String retMsg;
	private Integer retCode;

	public String getRetValue() {
		return retValue;
	}

	public void setRetValue(String retValue) {
		this.retValue = retValue;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public Integer getRetCode() {
		return retCode;
	}

	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}

}
