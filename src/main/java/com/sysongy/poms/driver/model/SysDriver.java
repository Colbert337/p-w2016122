package com.sysongy.poms.driver.model;

import com.sysongy.poms.base.model.BaseModel;

import java.util.Date;

public class SysDriver extends BaseModel {
    private String sysDriverId;

    private String userName;

    private String password;

    private String fullName;

    private String mobilePhone;

    private String payCode;

    private Integer isIdent;

    private String plateNumber;

    private Date expiryDate;

    private Integer fuelType;

    private String drivingLice;

    private String vehicleLice;

    private String identityCard;

    private String cardId;

    private String sysUserAccountId;

    private Date createdDate;

    private Date updatedDate;

    public String getSysDriverId() {
        return sysDriverId;
    }

    public void setSysDriverId(String sysDriverId) {
        this.sysDriverId = sysDriverId == null ? null : sysDriverId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode == null ? null : payCode.trim();
    }

    public Integer getIsIdent() {
        return isIdent;
    }

    public void setIsIdent(Integer isIdent) {
        this.isIdent = isIdent;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber == null ? null : plateNumber.trim();
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getFuelType() {
        return fuelType;
    }

    public void setFuelType(Integer fuelType) {
        this.fuelType = fuelType;
    }

    public String getDrivingLice() {
        return drivingLice;
    }

    public void setDrivingLice(String drivingLice) {
        this.drivingLice = drivingLice == null ? null : drivingLice.trim();
    }

    public String getVehicleLice() {
        return vehicleLice;
    }

    public void setVehicleLice(String vehicleLice) {
        this.vehicleLice = vehicleLice == null ? null : vehicleLice.trim();
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard == null ? null : identityCard.trim();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public String getsysUserAccountId() {
        return sysUserAccountId;
    }

    public void setsysUserAccountId(String sysUserAccountId) {
        this.sysUserAccountId = sysUserAccountId == null ? null : sysUserAccountId.trim();
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