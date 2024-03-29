package com.sysongy.poms.mobile.model;

import java.util.Date;

public class SysRoadConditionStr {
    private String id;

    private String conditionType;

    private String conditionStatus;

    private String longitude;

    private String latitude;

    private String captureLongitude;

    private String captureLatitude;

    private Date captureTime;

    private String conditionMsg;

    private String conditionImg;

    private Date startTime;

    private Date endTime;

    private String province;

    private String address;

    private String direction;

    private String usefulCount;

    private String publisherName;

    private String publisherPhone;

    private Date publisherTime;

    private String auditor;

    private String auditorPhone;

    private Date auditorTime;

    private String memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType == null ? null : conditionType.trim();
    }

    public String getConditionStatus() {
        return conditionStatus;
    }

    public void setConditionStatus(String conditionStatus) {
        this.conditionStatus = conditionStatus == null ? null : conditionStatus.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getCaptureLongitude() {
        return captureLongitude;
    }

    public void setCaptureLongitude(String captureLongitude) {
        this.captureLongitude = captureLongitude == null ? null : captureLongitude.trim();
    }

    public String getCaptureLatitude() {
        return captureLatitude;
    }

    public void setCaptureLatitude(String captureLatitude) {
        this.captureLatitude = captureLatitude == null ? null : captureLatitude.trim();
    }

    public Date getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
    }

    public String getConditionMsg() {
        return conditionMsg;
    }

    public void setConditionMsg(String conditionMsg) {
        this.conditionMsg = conditionMsg == null ? null : conditionMsg.trim();
    }

    public String getConditionImg() {
        return conditionImg;
    }

    public void setConditionImg(String conditionImg) {
        this.conditionImg = conditionImg == null ? null : conditionImg.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public String getUsefulCount() {
        return usefulCount;
    }

    public void setUsefulCount(String usefulCount) {
        this.usefulCount = usefulCount == null ? null : usefulCount.trim();
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName == null ? null : publisherName.trim();
    }

    public String getPublisherPhone() {
        return publisherPhone;
    }

    public void setPublisherPhone(String publisherPhone) {
        this.publisherPhone = publisherPhone == null ? null : publisherPhone.trim();
    }

    public Date getPublisherTime() {
        return publisherTime;
    }

    public void setPublisherTime(Date publisherTime) {
        this.publisherTime = publisherTime;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getAuditorPhone() {
        return auditorPhone;
    }

    public void setAuditorPhone(String auditorPhone) {
        this.auditorPhone = auditorPhone == null ? null : auditorPhone.trim();
    }

    public Date getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(Date auditorTime) {
        this.auditorTime = auditorTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}