package com.sysongy.tcms.advance.model;

import java.util.Date;

public class TcFleet {
    private String tcFleetId;

    private String fleetName;

    private String sysUserId;

    private Date createdDate;

    private Date updatedDate;

    public String getTcFleetId() {
        return tcFleetId;
    }

    public void setTcFleetId(String tcFleetId) {
        this.tcFleetId = tcFleetId == null ? null : tcFleetId.trim();
    }

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName == null ? null : fleetName.trim();
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId == null ? null : sysUserId.trim();
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