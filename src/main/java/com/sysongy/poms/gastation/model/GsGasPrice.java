package com.sysongy.poms.gastation.model;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.usysparam.model.Usysparam;

import java.util.Date;

public class GsGasPrice  extends BaseModel {
    private String gsGasPriceId;

    private String sysGasStationId;

    private String gasNum;

    private Usysparam gasNumInfo;

    private String gasName;

    private Usysparam gasNameInfo;

    private String price_id;

    private String unit;

    private String remark;

    private Date createdDate;

    private Date updatedDate;

    private ProductPrice productPriceInfo;

    public String getGsGasPriceId() {
        return gsGasPriceId;
    }

    public void setGsGasPriceId(String gsGasPriceId) {
        this.gsGasPriceId = gsGasPriceId == null ? null : gsGasPriceId.trim();
    }

    public String getSysGasStationId() {
        return sysGasStationId;
    }

    public void setSysGasStationId(String sysGasStationId) {
        this.sysGasStationId = sysGasStationId == null ? null : sysGasStationId.trim();
    }

    public String getGasNum() {
        return gasNum;
    }

    public void setGasNum(String gasNum) {
        this.gasNum = gasNum == null ? null : gasNum.trim();
    }

    public String getGasName() {
        return gasName;
    }

    public void setGasName(String gasName) {
        this.gasName = gasName == null ? null : gasName.trim();
    }

    public Usysparam getGasNameInfo() {
        return gasNameInfo;
    }

    public void setGasNameInfo(Usysparam gasNameInfo) {
        this.gasNameInfo = gasNameInfo;
    }

    public String getPrice_id() {
        return price_id;
    }

    public void setPrice_id(String price_id) {
        this.price_id = price_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
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

    public Usysparam getGasNumInfo() {
        return gasNumInfo;
    }

    public void setGasNumInfo(Usysparam gasNumInfo) {
        this.gasNumInfo = gasNumInfo;
    }

    public ProductPrice getProductPriceInfo() {
        return productPriceInfo;
    }

    public void setProductPriceInfo(ProductPrice productPriceInfo) {
        this.productPriceInfo = productPriceInfo;
    }
}