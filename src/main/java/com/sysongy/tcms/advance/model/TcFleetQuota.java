package com.sysongy.tcms.advance.model;

import com.sysongy.poms.base.model.BaseModel;

import java.util.Date;

public class TcFleetQuota extends BaseModel{
    private String tcFleetQuotaId;

    private String tcFleetId;

    private String stationId;

    private String quota;

    private Integer isAllot;

    private Date createdDate;

    private Date updatedDate;

    public String getTcFleetQuotaId() {
        return tcFleetQuotaId;
    }

    public void setTcFleetQuotaId(String tcFleetQuotaId) {
        this.tcFleetQuotaId = tcFleetQuotaId == null ? null : tcFleetQuotaId.trim();
    }

    public String getTcFleetId() {
        return tcFleetId;
    }

    public void setTcFleetId(String tcFleetId) {
        this.tcFleetId = tcFleetId == null ? null : tcFleetId.trim();
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota == null ? null : quota.trim();
    }

    public Integer getIsAllot() {
        return isAllot;
    }

    public void setIsAllot(Integer isAllot) {
        this.isAllot = isAllot;
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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}