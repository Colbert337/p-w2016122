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

    private String price;

    private String unit;

    private String remark;

    private Date createdDate;

    private Date updatedDate;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
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
}