package com.sysongy.tcms.advance.model;

import java.util.Date;

public class TcVehicle {
    private String tcVehicleId;

    private String platesNumber;

    private String tcFleetId;

    private String sysTransportId;

    private String noticePhone;

    private String copyPhone;

    private String cardNo;

    private String userName;

    private Date createdDate;

    private Date updatedDate;

    public String getTcVehicleId() {
        return tcVehicleId;
    }

    public void setTcVehicleId(String tcVehicleId) {
        this.tcVehicleId = tcVehicleId == null ? null : tcVehicleId.trim();
    }

    public String getPlatesNumber() {
        return platesNumber;
    }

    public void setPlatesNumber(String platesNumber) {
        this.platesNumber = platesNumber == null ? null : platesNumber.trim();
    }

    public String getTcFleetId() {
        return tcFleetId;
    }

    public void setTcFleetId(String tcFleetId) {
        this.tcFleetId = tcFleetId == null ? null : tcFleetId.trim();
    }

    public String getSysTransportId() {
        return sysTransportId;
    }

    public void setSysTransportId(String sysTransportId) {
        this.sysTransportId = sysTransportId == null ? null : sysTransportId.trim();
    }

    public String getNoticePhone() {
        return noticePhone;
    }

    public void setNoticePhone(String noticePhone) {
        this.noticePhone = noticePhone == null ? null : noticePhone.trim();
    }

    public String getCopyPhone() {
        return copyPhone;
    }

    public void setCopyPhone(String copyPhone) {
        this.copyPhone = copyPhone == null ? null : copyPhone.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
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