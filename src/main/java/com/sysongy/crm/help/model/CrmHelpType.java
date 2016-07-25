package com.sysongy.crm.help.model;

import java.util.Date;

public class CrmHelpType {
    private String crmHelpTypeId;

    private String title;

    private String remark;

    private Integer isDeleted;

    private Date createdDate;

    private Date updatedDate;

    public String getCrmHelpTypeId() {
        return crmHelpTypeId;
    }

    public void setCrmHelpTypeId(String crmHelpTypeId) {
        this.crmHelpTypeId = crmHelpTypeId == null ? null : crmHelpTypeId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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