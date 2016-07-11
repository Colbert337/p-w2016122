package com.sysongy.tcms.advance.model;

import java.util.Date;

public class TcFleetVehicle {
	
    private String tcFleetVehicleId;

    private String stationId;

    private String tcFleetId;

    private String tcVehicleId;

    private Date createdDate;
    
    private TcFleet tf;

    public TcFleet getTf() {
		return tf;
	}

	public void setTf(TcFleet tf) {
		this.tf = tf;
	}

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

    public String getTcFleetId() {
        return tcFleetId;
    }

    public void setTcFleetId(String tcFleetId) {
        this.tcFleetId = tcFleetId == null ? null : tcFleetId.trim();
    }

    public String getTcVehicleId() {
        return tcVehicleId;
    }

    public void setTcVehicleId(String tcVehicleId) {
        this.tcVehicleId = tcVehicleId == null ? null : tcVehicleId.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}