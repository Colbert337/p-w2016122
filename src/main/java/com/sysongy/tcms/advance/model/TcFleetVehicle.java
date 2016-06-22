package com.sysongy.tcms.advance.model;

import com.sysongy.poms.base.model.BaseModel;

import java.util.Date;

public class TcFleetVehicle extends BaseModel{
    private String tcFleetVehicleId;

    private String stationId;

    private String fleetName;

    private String sysUserId;

    private Date createdDate;

    public String getTcFleetVehicleId() {
        return tcFleetVehicleId;
    }

    public void setTcFleetVehicleId(String tcFleetVehicleId) {
        this.tcFleetVehicleId = tcFleetVehicleId == null ? null : tcFleetVehicleId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
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