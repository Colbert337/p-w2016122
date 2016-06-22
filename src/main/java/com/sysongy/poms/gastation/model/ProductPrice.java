package com.sysongy.poms.gastation.model;

import java.util.Date;

public class ProductPrice {
    private String id;

    private String productPriceId;

    private Long productPrice;

    private String productUnit;

    private Integer version;

    private String productPriceStatus;

    private Date createTime;

    private Date stratTime;

    private Date finishTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductPriceId() {
        return productPriceId;
    }

    public void setProductPriceId(String productPriceId) {
        this.productPriceId = productPriceId == null ? null : productPriceId.trim();
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit == null ? null : productUnit.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getProductPriceStatus() {
        return productPriceStatus;
    }

    public void setProductPriceStatus(String productPriceStatus) {
        this.productPriceStatus = productPriceStatus == null ? null : productPriceStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStratTime() {
        return stratTime;
    }

    public void setStratTime(Date stratTime) {
        this.stratTime = stratTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}