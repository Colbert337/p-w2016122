package com.sysongy.poms.order.model;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.usysparam.model.Usysparam;

import java.math.BigDecimal;
import java.util.Date;

public class SysOrderDeal extends BaseModel {

    private String dealId;

    private String orderId;

    private String dealNumber;

    private Date dealDate;

    private String dealType;

    private String cashBackPer;

    private BigDecimal cashBack;

    private String runSuccess;
    
    private String remark;

    private SysOrder sysOrderInfo;

    private String storage_time_before;

    private String storage_time_after;

    private String operator;

    private String cardID;

    private String mobilePhone;

    private String stationID;

    private String isCharge;

    private SysDriver sysDriver;

    private Usysparam dealTypeInfo;

    private String goodType;

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId == null ? null : dealId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(String dealNumber) {
        this.dealNumber = dealNumber == null ? null : dealNumber.trim();
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType == null ? null : dealType.trim();
    }

    public String getCashBackPer() {
        return cashBackPer;
    }

    public void setCashBackPer(String cashBackPer) {
        this.cashBackPer = cashBackPer == null ? null : cashBackPer.trim();
    }

    public BigDecimal getCashBack() {
        return cashBack;
    }

    public void setCashBack(BigDecimal cashBack) {
        this.cashBack = cashBack;
    }

    public String getRunSuccess() {
        return runSuccess;
    }

    public void setRunSuccess(String runSuccess) {
        this.runSuccess = runSuccess == null ? null : runSuccess.trim();
    }

    public SysOrder getSysOrderInfo() {
        return sysOrderInfo;
    }

    public void setSysOrderInfo(SysOrder sysOrderInfo) {
        this.sysOrderInfo = sysOrderInfo;
    }

    public String getStorage_time_before() {
        return storage_time_before;
    }

    public void setStorage_time_before(String storage_time_before) {
        this.storage_time_before = storage_time_before;
    }

    public String getStorage_time_after() {
        return storage_time_after;
    }

    public void setStorage_time_after(String storage_time_after) {
        this.storage_time_after = storage_time_after;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(String isCharge) {
        this.isCharge = isCharge;
    }

    public SysDriver getSysDriver() {
        return sysDriver;
    }

    public void setSysDriver(SysDriver sysDriver) {
        this.sysDriver = sysDriver;
    }

    public Usysparam getDealTypeInfo() {
        return dealTypeInfo;
    }

    public void setDealTypeInfo(Usysparam dealTypeInfo) {
        this.dealTypeInfo = dealTypeInfo;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }
}