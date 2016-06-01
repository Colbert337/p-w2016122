package com.sysongy.util.taglib.cache;

import java.util.List;

public class StaticDataVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -124055849640704217L;

	private String name = "";

	private String parentCode = "";

	private String code = "";

	private List exData = null;

	public String getName() {
		return name;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public void setExData(List exData) {
		this.exData = exData;
	}

	public String getCode() {
		return code;
	}

	public List getExData() {
		return exData;
	}

	public StaticDataVO() {
	}
}
