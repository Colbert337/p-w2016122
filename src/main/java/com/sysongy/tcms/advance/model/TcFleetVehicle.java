package com.sysongy.tcms.advance.model;

import java.util.Date;

public class TcFleetVehicle {
    private String tcFleetVehicleId;

    private String fleetName;

    private String sysUserId;

    private Date createdDate;

    public String getTcFleetVehicleId() {
        return tcFleetVehicleId;
    }

    public void setTcFleetVehicleId(String tcFleetVehicleId) {
        this.tcFleetVehicleId = tcFleetVehicleId == null ? null : tcFleetVehicleId.trim();
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
}