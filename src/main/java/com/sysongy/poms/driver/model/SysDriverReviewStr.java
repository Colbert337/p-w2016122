package com.sysongy.poms.driver.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class SysDriverReviewStr extends BaseModel{
	
    private String sysDriverId;

    private String userName;

    private String password;

    private String fullName;

    private String mobilePhone;

    private String payCode;

    private String userStatus;

    private Integer isIdent;

    private String plateNumber;

    private Date expiryDate;

    private String fuelType;

    private String drivingLice;

    private String vehicleLice;

    private String identityCard;

    private String cardId;

    private String sysUserAccountId;

    private String sysTransportId;

    private String regisCompany;

    private String regisSource;

    private Date createdDate;

    private Date updatedDate;

    private Integer isFirstCharge;

    private String checkedStatus;

    private Date checkedDate;

    private String stationId;

    private String memo;
    
    private String createdDate_before;

    private String createdDate_after;

    public String getCreatedDate_before() {
		return createdDate_before;
	}

	public void setCreatedDate_before(String createdDate_before) {
		this.createdDate_before = createdDate_before;
	}

	public String getCreatedDate_after() {
		return createdDate_after;
	}

	public void setCreatedDate_after(String createdDate_after) {
		this.createdDate_after = createdDate_after;
	}

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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
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

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType == null ? null : fuelType.trim();
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

    public String getSysUserAccountId() {
        return sysUserAccountId;
    }

    public void setSysUserAccountId(String sysUserAccountId) {
        this.sysUserAccountId = sysUserAccountId == null ? null : sysUserAccountId.trim();
    }

    public String getSysTransportId() {
        return sysTransportId;
    }

    public void setSysTransportId(String sysTransportId) {
        this.sysTransportId = sysTransportId == null ? null : sysTransportId.trim();
    }

    public String getRegisCompany() {
        return regisCompany;
    }

    public void setRegisCompany(String regisCompany) {
        this.regisCompany = regisCompany == null ? null : regisCompany.trim();
    }

    public String getRegisSource() {
        return regisSource;
    }

    public void setRegisSource(String regisSource) {
        this.regisSource = regisSource == null ? null : regisSource.trim();
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

    public Integer getIsFirstCharge() {
        return isFirstCharge;
    }

    public void setIsFirstCharge(Integer isFirstCharge) {
        this.isFirstCharge = isFirstCharge;
    }

    public String getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(String checkedStatus) {
        this.checkedStatus = checkedStatus == null ? null : checkedStatus.trim();
    }

    public Date getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(Date checkedDate) {
        this.checkedDate = checkedDate;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}