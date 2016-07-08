package com.sysongy.poms.permi.model;

import java.math.BigDecimal;
import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.usysparam.model.Usysparam;

public class SysUserAccount extends BaseModel{
	
    private String sysUserAccountId;

    private String accountCode;

    private String accountType;

    private String accountBalance;

    private BigDecimal accountBalanceBigDecimal;

    private Date createdDate;

    private Date updatedDate;

    private int version;
    
    private String account_status;

    private String have_consume;
    
    public String getHave_consume() {
		return have_consume;
	}

	public void setHave_consume(String have_consume) {
		this.have_consume = have_consume;
	}

	private Usysparam account_statusInfo;

    private BigDecimal deposit;

    public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public String getAccount_status() {
		return account_status;
	}

	public void setAccount_status(String account_status) {
		this.account_status = account_status;
	}

	public String getSysUserAccountId() {
        return sysUserAccountId;
    }

    public void setSysUserAccountId(String sysUserAccountId) {
        this.sysUserAccountId = sysUserAccountId == null ? null : sysUserAccountId.trim();
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode == null ? null : accountCode.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance == null ? null : accountBalance.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Usysparam getAccount_statusInfo() {
        return account_statusInfo;
    }

    public void setAccount_statusInfo(Usysparam account_statusInfo) {
        this.account_statusInfo = account_statusInfo;
    }

    public BigDecimal getAccountBalanceBigDecimal() {
        if(this.accountBalanceBigDecimal == null){
            BigDecimal bd = new BigDecimal(accountBalance);
            bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
            this.accountBalanceBigDecimal = bd;
        }
        return accountBalanceBigDecimal;
    }

    public void setAccountBalanceBigDecimal(BigDecimal accountBalanceBigDecimal) {
        if(this.accountBalanceBigDecimal == null){
            BigDecimal bd = new BigDecimal(accountBalance);
            bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
            this.accountBalanceBigDecimal = bd;
        }
    }
}