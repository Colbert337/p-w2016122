package com.sysongy.poms.permi.model;

import java.util.Date;

public class SysUserRole {
    private String sysUserRoleId;

    private String sysUserId;

    private String sysRoleId;

    private Date createdDate;

    public String getSysUserRoleId() {
        return sysUserRoleId;
    }

    public void setSysUserRoleId(String sysUserRoleId) {
        this.sysUserRoleId = sysUserRoleId == null ? null : sysUserRoleId.trim();
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId == null ? null : sysUserId.trim();
    }

    public String getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(String sysRoleId) {
        this.sysRoleId = sysRoleId == null ? null : sysRoleId.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}