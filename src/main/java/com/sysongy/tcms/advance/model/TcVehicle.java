package com.sysongy.tcms.advance.model;

import com.sysongy.poms.base.model.BaseModel;

import java.util.Date;

public class TcVehicle extends BaseModel{
    private String tcVehicleId;

    private String platesNumber;

    private String tcFleetId;

    private Integer isAllot;

    private String stationId;

    private String noticePhone;

    private String copyPhone;

    private String cardNo;

    private String userName;

    private Date createdDate;

    private Date updatedDate;

    private String isDeleted;

    private String payCode;

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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public Integer getIsAllot() {
        return isAllot;
    }

    public void setIsAllot(Integer isAllot) {
        this.isAllot = isAllot;
    }
}