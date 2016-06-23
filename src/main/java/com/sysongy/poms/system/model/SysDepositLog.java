package com.sysongy.poms.system.model;

import java.math.BigDecimal;
import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class SysDepositLog extends BaseModel{
	
    private String sysDepositLogId;

    private String stationId;

    private String accountId;

    private String company;

    private Date depositTime;
    
    private String depositTime_page;

    private String depositType;

    private String operator;

    private Date optime;
    
    private String optime_after;
    
    private String optime_before;

    private BigDecimal deposit;

    private String memo;

    public String getOptime_after() {
		return optime_after;
	}

	public void setOptime_after(String optime_after) {
		this.optime_after = optime_after;
	}

	public String getOptime_before() {
		return optime_before;
	}

	public void setOptime_before(String optime_before) {
		this.optime_before = optime_before;
	}

	public String getDepositTime_page() {
		return depositTime_page;
	}

	public void setDepositTime_page(String depositTime_page) {
		this.depositTime_page = depositTime_page;
	}

	public String getSysDepositLogId() {
        return sysDepositLogId;
    }

    public void setSysDepositLogId(String sysDepositLogId) {
        this.sysDepositLogId = sysDepositLogId == null ? null : sysDepositLogId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public Date getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(Date depositTime) {
        this.depositTime = depositTime;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType == null ? null : depositType.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}