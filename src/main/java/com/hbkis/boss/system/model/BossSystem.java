package com.hbkis.boss.system.model;

import java.beans.Transient;
import java.util.Date;

public class BossSystem {
    private String systemId;

    private String systemName;

    private Integer systemStatus;

    private Integer schoolCount;

    private String manager;

    private String phoneNumber;

    private Date createdDate;

    private Date modifiedDate;
    
    private String schoolName;//虚拟字段
    
    private String menuId;//虚拟字段
    
    private String pemsName;//平台账号
    
    private String pemsPwd;//平台密码
    
    private String userId;//用户ID

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }

    public Integer getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(Integer systemStatus) {
        this.systemStatus = systemStatus;
    }

    public Integer getSchoolCount() {
        return schoolCount;
    }

    public void setSchoolCount(Integer schoolCount) {
        this.schoolCount = schoolCount;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

	public String getSchoolName() {
		return schoolName;
	}
	@Transient
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getMenuId() {
		return menuId;
	}
	@Transient
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getPemsName() {
		return pemsName;
	}

	public void setPemsName(String pemsName) {
		this.pemsName = pemsName;
	}

	public String getPemsPwd() {
		return pemsPwd;
	}

	public void setPemsPwd(String pemsPwd) {
		this.pemsPwd = pemsPwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
	
    
}