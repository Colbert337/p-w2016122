package com.sysongy.poms.system.model;

import java.util.Date;

public class SysOperationLog {
    private String sysOperationLogId;

    private String logPlatform;

    private String systemModule;

    private String logContent;

    private String operator;

    private String roleName;

    private String userName;

    private String userIp;

    private Date createdDate;

    public String getSysOperationLogId() {
        return sysOperationLogId;
    }

    public void setSysOperationLogId(String sysOperationLogId) {
        this.sysOperationLogId = sysOperationLogId == null ? null : sysOperationLogId.trim();
    }

    public String getLogPlatform() {
        return logPlatform;
    }

    public void setLogPlatform(String logPlatform) {
        this.logPlatform = logPlatform == null ? null : logPlatform.trim();
    }

    public String getSystemModule() {
        return systemModule;
    }

    public void setSystemModule(String systemModule) {
        this.systemModule = systemModule == null ? null : systemModule.trim();
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent == null ? null : logContent.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp == null ? null : userIp.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}