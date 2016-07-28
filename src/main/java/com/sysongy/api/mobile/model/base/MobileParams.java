package com.sysongy.api.mobile.model.base;

import java.io.File;

public class MobileParams {
	
	private String versionCode;
	
	private String apiKey;
	
	private String detailParam;
	
	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getDetailParam() {
		return detailParam;
	}

	public void setDetailParam(String detailParam) {
		this.detailParam = detailParam;
	}
	
}
