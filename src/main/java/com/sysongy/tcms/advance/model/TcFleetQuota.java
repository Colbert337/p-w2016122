package com.sysongy.tcms.advance.model;

import com.sysongy.poms.base.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class TcFleetQuota extends BaseModel{
    private String tcFleetQuotaId;

    private String tcFleetId;

    private String stationId;

    private BigDecimal quota;

    private Integer isAllot;

    private Date createdDate;

    private Date updatedDate;

    private String fleetName;

    private String realName;

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

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
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

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}