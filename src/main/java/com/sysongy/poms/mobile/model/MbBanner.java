package com.sysongy.poms.mobile.model;

import com.sysongy.poms.base.model.BaseModel;

import java.util.Date;

public class MbBanner extends BaseModel{
    private String mbBannerId;

    private String title;

    private String imgPath;

    private String imgSmPath;

    private String targetUrl;

    private String version;

    private String imgType;

    private String sort;

    private String remark;

    private int isDeleted;

    private Date createdDate;
    private String createdDate_str;

    public String getCreatedDate_str() {
		return createdDate_str;
	}

	public void setCreatedDate_str(String createdDate_str) {
		this.createdDate_str = createdDate_str;
	}

	public String getUpdatedDate_str() {
		return updatedDate_str;
	}

	public void setUpdatedDate_str(String updatedDate_str) {
		this.updatedDate_str = updatedDate_str;
	}

	private Date updatedDate;
    private String updatedDate_str;
    private String cityId;
    
    private String content;
    
    private String city_id;
    
    private String city_name;
    public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	 

	public String getCity_id() {
		return city_id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	private String operator;

    public String getMbBannerId() {
        return mbBannerId;
    }

    public void setMbBannerId(String mbBannerId) {
        this.mbBannerId = mbBannerId == null ? null : mbBannerId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    public String getImgSmPath() {
        return imgSmPath;
    }

    public void setImgSmPath(String imgSmPath) {
        this.imgSmPath = imgSmPath == null ? null : imgSmPath.trim();
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl == null ? null : targetUrl.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}