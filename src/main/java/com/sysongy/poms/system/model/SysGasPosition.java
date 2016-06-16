package com.sysongy.poms.system.model;

import java.util.Date;

public class SysGasPosition {
    private String sysGasPositionId;

    private String gasStationName;

    private String gasStationType;

    private String gasStationAddr;

    private String lng;

    private String lat;

    private String service;

    private String telephone;

    private String goodsInfo;

    private String isCooperation;

    private String isAuthenticate;

    private Date createdDate;

    private Date updatedDate;

    public String getSysGasPositionId() {
        return sysGasPositionId;
    }

    public void setSysGasPositionId(String sysGasPositionId) {
        this.sysGasPositionId = sysGasPositionId == null ? null : sysGasPositionId.trim();
    }

    public String getGasStationName() {
        return gasStationName;
    }

    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName == null ? null : gasStationName.trim();
    }

    public String getGasStationType() {
        return gasStationType;
    }

    public void setGasStationType(String gasStationType) {
        this.gasStationType = gasStationType == null ? null : gasStationType.trim();
    }

    public String getGasStationAddr() {
        return gasStationAddr;
    }

    public void setGasStationAddr(String gasStationAddr) {
        this.gasStationAddr = gasStationAddr == null ? null : gasStationAddr.trim();
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service == null ? null : service.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo == null ? null : goodsInfo.trim();
    }

    public String getIsCooperation() {
        return isCooperation;
    }

    public void setIsCooperation(String isCooperation) {
        this.isCooperation = isCooperation == null ? null : isCooperation.trim();
    }

    public String getIsAuthenticate() {
        return isAuthenticate;
    }

    public void setIsAuthenticate(String isAuthenticate) {
        this.isAuthenticate = isAuthenticate == null ? null : isAuthenticate.trim();
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