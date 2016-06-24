package com.sysongy.poms.gastation.model;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.usysparam.model.Usysparam;

import java.util.Date;

public class ProductPrice extends BaseModel {
    private String id;

    private String productPriceId;

    private Usysparam productPriceIdInfo;

    private double productPrice;

    private Usysparam productUnitInfo;

    private String productUnit;

    private Integer version;

    private Usysparam productPriceStatusInfo;

    private String productPriceStatus;

    private Date createTime;

    private Date stratTime;

    private Date finishTime;

    private String product_price_type;

    private Usysparam product_price_type_info;

    private String product_id;

    private GsGasPrice product_info;

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

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
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

    public String getProduct_price_type() {
        return product_price_type;
    }

    public void setProduct_price_type(String product_price_type) {
        this.product_price_type = product_price_type;
    }

    public Usysparam getProduct_price_type_info() {
        return product_price_type_info;
    }

    public void setProduct_price_type_info(Usysparam product_price_type_info) {
        this.product_price_type_info = product_price_type_info;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Usysparam getProductPriceIdInfo() {
        return productPriceIdInfo;
    }

    public void setProductPriceIdInfo(Usysparam productPriceIdInfo) {
        this.productPriceIdInfo = productPriceIdInfo;
    }

    public Usysparam getProductUnitInfo() {
        return productUnitInfo;
    }

    public void setProductUnitInfo(Usysparam productUnitInfo) {
        this.productUnitInfo = productUnitInfo;
    }

    public GsGasPrice getProduct_info() {
        return product_info;
    }

    public void setProduct_info(GsGasPrice product_info) {
        this.product_info = product_info;
    }

    public Usysparam getProductPriceStatusInfo() {
        return productPriceStatusInfo;
    }

    public void setProductPriceStatusInfo(Usysparam productPriceStatusInfo) {
        this.productPriceStatusInfo = productPriceStatusInfo;
    }
}