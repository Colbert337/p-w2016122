package com.sysongy.poms.card.model;

import java.util.Date;

public class GasCardProp {
    private String cardPropId;

    private String cardNo;

    private String ownerId;

    private String ownerCardNo;

    private Date updateTime;

    private String operationSys;

    private String batchNo;

    private String memo;

    public String getCardPropId() {
        return cardPropId;
    }

    public void setCardPropId(String cardPropId) {
        this.cardPropId = cardPropId == null ? null : cardPropId.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public String getOwnerCardNo() {
        return ownerCardNo;
    }

    public void setOwnerCardNo(String ownerCardNo) {
        this.ownerCardNo = ownerCardNo == null ? null : ownerCardNo.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperationSys() {
        return operationSys;
    }

    public void setOperationSys(String operationSys) {
        this.operationSys = operationSys == null ? null : operationSys.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}