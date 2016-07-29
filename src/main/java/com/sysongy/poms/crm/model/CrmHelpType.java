package com.sysongy.poms.crm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.sysongy.poms.base.model.BaseModel;

public class CrmHelpType extends BaseModel{
    private String crmHelpTypeId;

    private String title;

    private String remark;

    private Integer isDeleted;

    @DateTimeFormat(pattern="yyyy-MM-dd") 
    private Date createdDate;
    
    @DateTimeFormat(pattern="yyyy-MM-dd") 
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