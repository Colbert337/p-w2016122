package com.sysongy.tcms.advance.model;

import java.math.BigDecimal;
import java.util.Date;

public class TcTransferAccount {
    private String tcTransferAccountId;

    private String stationId;

    private String sysDriverId;

    private String fullName;

    private String mobilePhone;

    private BigDecimal amount;

    private String used;

    private Date createdDate;

    private Date updatedDate;

    public String getTcTransferAccountId() {
        return tcTransferAccountId;
    }

    public void setTcTransferAccountId(String tcTransferAccountId) {
        this.tcTransferAccountId = tcTransferAccountId == null ? null : tcTransferAccountId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getSysDriverId() {
        return sysDriverId;
    }

    public void setSysDriverId(String sysDriverId) {
        this.sysDriverId = sysDriverId == null ? null : sysDriverId.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used == null ? null : used.trim();
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
}