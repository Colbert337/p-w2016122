package com.sysongy.poms.card.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SysongyChargeCard {
    private String id;

    private String password;

    private String ownid;
    
    @DateTimeFormat(pattern="MM/dd/yyyy") 
    private Date useTime;

    private BigDecimal cash;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getOwnid() {
        return ownid;
    }

    public void setOwnid(String ownid) {
        this.ownid = ownid == null ? null : ownid.trim();
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

}