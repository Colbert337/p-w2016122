package com.sysongy.poms.driver.model;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.util.NumberUtils;

import java.util.Date;

public class SysDriver extends BaseModel{

    private String sysDriverId;

    private String userName;

    private String password;

    private String fullName;

    private String mobilePhone;

    private String payCode;

    private String userStatus;

    private Integer isIdent;

    private Usysparam isIdentInfo;

    private String plateNumber;

    private Date expiryDate;

    private String fuelType;

    private Usysparam fuelTypeInfo;

    private String drivingLice;

    private String vehicleLice;

    private String identityCard;

    private String avatarS;

    private String avatarB;

    private String cardId;

    private GasCard cardInfo;

    private String sysUserAccountId;

    private String regisCompany;

    private String regisSource;

    private Date createdDate;

    private String createdDate_before;

    private String createdDate_after;

    private Date updatedDate;

    private Integer isFirstCharge;

    private String checkedStatus;
    
    private String notin_checked_status;

    private Date checkedDate;
    
    private String checkedDate_before;

    private String checkedDate_after;

    private SysUserAccount account;

    private String stationId;

    private String walletId;

    private String expireTimeForCRM;
    
    private String memo;

    private int driverType;

    private int isCharge;

    private int driver_number;

    private String strDriverNumber;

    private int isLocked = 0;

    private String invitationCode;

    private String isOpen;

    private String deviceToken;

    private String transportionName;

    private String gasStationName;

    public String getMemo() {
		return memo;
	}

	public String getCheckedDate_before() {
		return checkedDate_before;
	}

	public void setCheckedDate_before(String checkedDate_before) {
		this.checkedDate_before = checkedDate_before;
	}

	public String getCheckedDate_after() {
		return checkedDate_after;
	}

	public void setCheckedDate_after(String checkedDate_after) {
		this.checkedDate_after = checkedDate_after;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getNotin_checked_status() {
		return notin_checked_status;
	}

	public void setNotin_checked_status(String notin_checked_status) {
		this.notin_checked_status = notin_checked_status;
	}

	public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

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

    public String getSysUserAccountId() {
        return sysUserAccountId;
    }

    public void setSysUserAccountId(String sysUserAccountId) {
        this.sysUserAccountId = sysUserAccountId == null ? null : sysUserAccountId.trim();
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
        this.checkedStatus = checkedStatus;
    }

    public Date getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(Date checkedDate) {
        this.checkedDate = checkedDate;
    }

    public SysUserAccount getAccount() {
        return account;
    }

    public void setAccount(SysUserAccount account) {
        this.account = account;
    }

    public GasCard getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(GasCard cardInfo) {
        this.cardInfo = cardInfo;
    }

    public Usysparam getIsIdentInfo() {
        return isIdentInfo;
    }

    public void setIsIdentInfo(Usysparam isIdentInfo) {
        this.isIdentInfo = isIdentInfo;
    }

    public String getExpireTimeForCRM() {
        return expireTimeForCRM;
    }

    public void setExpireTimeForCRM(String expireTimeForCRM) {
        this.expireTimeForCRM = expireTimeForCRM;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Usysparam getFuelTypeInfo() {
        return fuelTypeInfo;
    }

    public void setFuelTypeInfo(Usysparam fuelTypeInfo) {
        this.fuelTypeInfo = fuelTypeInfo;
    }

    public int getDriverType() {
        return driverType;
    }

    public void setDriverType(int driverType) {
        this.driverType = driverType;
    }

    public int getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(int isCharge) {
        this.isCharge = isCharge;
    }

    public int getDriver_number() {
        return driver_number;
    }

    public void setDriver_number(int driver_number) {
        this.driver_number = driver_number;
    }

    public String getStrDriverNumber() {
        return NumberUtils.convertFormatNumber(this.driver_number);
    }

    public void setStrDriverNumber(String strDriverNumber) {
        this.strDriverNumber = NumberUtils.convertFormatNumber(this.driver_number);
    }

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    public String getAvatarS() {
        return avatarS;
    }

    public void setAvatarS(String avatarS) {
        this.avatarS = avatarS;
    }

    public String getAvatarB() {
        return avatarB;
    }

    public void setAvatarB(String avatarB) {
        this.avatarB = avatarB;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getTransportionName() {
        return transportionName;
    }

    public void setTransportionName(String transportionName) {
        this.transportionName = transportionName;
    }

    public String getGasStationName() {
        return gasStationName;
    }

    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }
}