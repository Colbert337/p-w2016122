package com.sysongy.poms.permi.model;

import java.math.BigDecimal;
import java.util.Date;

public class SysUserAccountStr {
    private String sysUserAccountId;

    private String accountCode;

    private String accountType;

    private String accountBalance;

    private Date createdDate;

    private Date updatedDate;

    private Integer version;

    private String accountStatus;

    private BigDecimal deposit;

    private String haveConsume;

    private String resouceCode;

    private String resouce;

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
        this.accountType = accountType == null ? null : accountType.trim();
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus == null ? null : accountStatus.trim();
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getHaveConsume() {
        return haveConsume;
    }

    public void setHaveConsume(String haveConsume) {
        this.haveConsume = haveConsume == null ? null : haveConsume.trim();
    }

    public String getResouceCode() {
        return resouceCode;
    }

    public void setResouceCode(String resouceCode) {
        this.resouceCode = resouceCode == null ? null : resouceCode.trim();
    }

    public String getResouce() {
        return resouce;
    }

    public void setResouce(String resouce) {
        this.resouce = resouce == null ? null : resouce.trim();
    }
}