package com.sysongy.poms.permi.model;

import java.util.Date;

public class SysRoleFunction {
    private String sysRoleFunctionId;

    private String sysRoleId;

    private String sysFunctionId;

    private Date createdDate;

    public String getSysRoleFunctionId() {
        return sysRoleFunctionId;
    }

    public void setSysRoleFunctionId(String sysRoleFunctionId) {
        this.sysRoleFunctionId = sysRoleFunctionId == null ? null : sysRoleFunctionId.trim();
    }

    public String getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(String sysRoleId) {
        this.sysRoleId = sysRoleId == null ? null : sysRoleId.trim();
    }

    public String getSysFunctionId() {
        return sysFunctionId;
    }

    public void setSysFunctionId(String sysFunctionId) {
        this.sysFunctionId = sysFunctionId == null ? null : sysFunctionId.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}