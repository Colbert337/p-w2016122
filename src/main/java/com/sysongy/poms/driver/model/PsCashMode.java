package com.sysongy.poms.driver.model;

import java.util.Date;

public class PsCashMode {
    private String psCashModeId;

    private String modeName;

    private Date createdDate;

    private Date updatedDate;

    public String getPsCashModeId() {
        return psCashModeId;
    }

    public void setPsCashModeId(String psCashModeId) {
        this.psCashModeId = psCashModeId == null ? null : psCashModeId.trim();
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName == null ? null : modeName.trim();
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