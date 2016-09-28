package com.sysongy.poms.mobile.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class MbStatistics extends BaseModel{
    private String mbStatisticsId;

    private String sysDriverId;

    private String operationType;

    private String contentType;

    private String contentId;

    private String memo;

    private Date createdDate;

    public String getMbStatisticsId() {
        return mbStatisticsId;
    }

    public void setMbStatisticsId(String mbStatisticsId) {
        this.mbStatisticsId = mbStatisticsId == null ? null : mbStatisticsId.trim();
    }

    public String getSysDriverId() {
        return sysDriverId;
    }

    public void setSysDriverId(String sysDriverId) {
        this.sysDriverId = sysDriverId == null ? null : sysDriverId.trim();
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId == null ? null : contentId.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}