package com.sysongy.poms.order.model;

import java.math.BigDecimal;
import java.util.Date;

public class SysOrderDeal {
    private String dealId;

    private String orderId;

    private String dealNumber;

    private Date dealDate;

    private String dealType;

    private String cashBackPer;

    private BigDecimal cashBack;

    private String runSuccess;

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
}