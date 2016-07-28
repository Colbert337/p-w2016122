package com.sysongy.api.mobile.model.base;

public class Data {
	
	private String token;
	
	private String verificationCode;
	
	private String nick;
	
	private String username;
	
	private String securityPhone;
	
	private String isRealNameAuth;
	
	private String balance;
	
	private String cumulativeReturn;//累计返现
	
	private String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSecurityPhone() {
		return securityPhone;
	}

	public void setSecurityPhone(String securityPhone) {
		this.securityPhone = securityPhone;
	}

	public String getIsRealNameAuth() {
		return isRealNameAuth;
	}

	public void setIsRealNameAuth(String isRealNameAuth) {
		this.isRealNameAuth = isRealNameAuth;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCumulativeReturn() {
		return cumulativeReturn;
	}

	public void setCumulativeReturn(String cumulativeReturn) {
		this.cumulativeReturn = cumulativeReturn;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}	
}
