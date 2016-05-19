package com.sysongy.poms.system.model;

import java.util.Date;

public class SystemMenuRole {
    private String systemMenuRoleId;

    private String systemMenuId;

    private String roleId;

    private Date createdDate;

    public String getSystemMenuRoleId() {
        return systemMenuRoleId;
    }

    public void setSystemMenuRoleId(String systemMenuRoleId) {
        this.systemMenuRoleId = systemMenuRoleId == null ? null : systemMenuRoleId.trim();
    }

    public String getSystemMenuId() {
        return systemMenuId;
    }

    public void setSystemMenuId(String systemMenuId) {
        this.systemMenuId = systemMenuId == null ? null : systemMenuId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}