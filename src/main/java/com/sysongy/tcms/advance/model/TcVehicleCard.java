package com.sysongy.tcms.advance.model;

import java.util.Date;

public class TcVehicleCard {
    private String tcVehicleCardId;

    private String stationId;

    private String tcVehicleId;

    private String cardNo;

    private Date createdDate;

    public String getTcVehicleCardId() {
        return tcVehicleCardId;
    }

    public void setTcVehicleCardId(String tcVehicleCardId) {
        this.tcVehicleCardId = tcVehicleCardId;
    }

    public String getTcVehicleId() {
        return tcVehicleId;
    }

    public void setTcVehicleId(String tcVehicleId) {
        this.tcVehicleId = tcVehicleId == null ? null : tcVehicleId.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}