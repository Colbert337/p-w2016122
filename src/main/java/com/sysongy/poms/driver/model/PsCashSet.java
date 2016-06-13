package com.sysongy.poms.driver.model;

import java.util.Date;

public class PsCashSet {
    private String psCashSetId;

    private String minValue;

    private String maxValue;

    private String cashPer;

    private String status;

    private String level;

    private String sysUserId;

    private String userName;

    private String remark;

    private Date startDate;

    private Date endDate;

    private Date createdDate;

    private Date updatedDate;

    public String getPsCashSetId() {
        return psCashSetId;
    }

    public void setPsCashSetId(String psCashSetId) {
        this.psCashSetId = psCashSetId == null ? null : psCashSetId.trim();
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue == null ? null : minValue.trim();
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue == null ? null : maxValue.trim();
    }

    public String getCashPer() {
        return cashPer;
    }

    public void setCashPer(String cashPer) {
        this.cashPer = cashPer == null ? null : cashPer.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId == null ? null : sysUserId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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