package com.sysongy.poms.mobile.model;

import com.sysongy.poms.base.model.BaseModel;

import java.util.Date;

public class MbAppVersion  extends BaseModel {
    private String appVersionId;

    private String url;

    private String version;

    private String code;

    private int status;

    private int downCount;

    private String isPublish;

    private String remark;

    private Date createdDate;

    private String createdDateStr;

    private String created_date_sel;//用于查询的虚拟字段
    private String updated_date_sel;//用于查询的虚拟字段

    private  String appSize;
    private  String isUpdate; //1强制 2不强制
    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }





    public String getCreated_date_sel() {
        return created_date_sel;
    }

    public void setCreated_date_sel(String created_date_sel) {
        this.created_date_sel = created_date_sel;
    }

    public String getUpdated_date_sel() {
        return updated_date_sel;
    }

    public void setUpdated_date_sel(String updated_date_sel) {
        this.updated_date_sel = updated_date_sel;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getAppVersionId() {
        return appVersionId;
    }

    public void setAppVersionId(String appVersionId) {
        this.appVersionId = appVersionId == null ? null : appVersionId.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(String isPublish) {
        this.isPublish = isPublish == null ? null : isPublish.trim();
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDownCount() {
        return downCount;
    }

    public void setDownCount(int downCount) {
        this.downCount = downCount;
    }
}