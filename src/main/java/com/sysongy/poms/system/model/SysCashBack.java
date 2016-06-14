package com.sysongy.poms.system.model;

import java.util.Date;

public class SysCashBack {
    private String sysCashBackId;

    private String thresholdMinValue;

    private String thresholdMaxValue;

    private String cashPer;

    private String status;

    private String level;

    private Date startDate;

    private Date endDate;

    private Date createdDate;

    private Date updatedDate;

    public String getSysCashBackId() {
        return sysCashBackId;
    }

    public void setSysCashBackId(String sysCashBackId) {
        this.sysCashBackId = sysCashBackId == null ? null : sysCashBackId.trim();
    }

    public String getThresholdMinValue() {
        return thresholdMinValue;
    }

    public void setThresholdMinValue(String thresholdMinValue) {
        this.thresholdMinValue = thresholdMinValue == null ? null : thresholdMinValue.trim();
    }

    public String getThresholdMaxValue() {
        return thresholdMaxValue;
    }

    public void setThresholdMaxValue(String thresholdMaxValue) {
        this.thresholdMaxValue = thresholdMaxValue == null ? null : thresholdMaxValue.trim();
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