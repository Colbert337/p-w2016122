package com.sysongy.poms.liquid.model;

import java.math.BigDecimal;
import java.util.Date;

public class SysGasSource {
    private String sysGasSourceId;

    private String gasFactoryName;

    private String technologyType;

    private String deliveryMethod;

    private BigDecimal marketPrice;

    private String gasFactoryAddr;

    private String status;

    private String remark;

    private Date createdDate;

    private Date updatedDate;

    public String getSysGasSourceId() {
        return sysGasSourceId;
    }

    public void setSysGasSourceId(String sysGasSourceId) {
        this.sysGasSourceId = sysGasSourceId == null ? null : sysGasSourceId.trim();
    }

    public String getGasFactoryName() {
        return gasFactoryName;
    }

    public void setGasFactoryName(String gasFactoryName) {
        this.gasFactoryName = gasFactoryName == null ? null : gasFactoryName.trim();
    }

    public String getTechnologyType() {
        return technologyType;
    }

    public void setTechnologyType(String technologyType) {
        this.technologyType = technologyType == null ? null : technologyType.trim();
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod == null ? null : deliveryMethod.trim();
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getGasFactoryAddr() {
        return gasFactoryAddr;
    }

    public void setGasFactoryAddr(String gasFactoryAddr) {
        this.gasFactoryAddr = gasFactoryAddr == null ? null : gasFactoryAddr.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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