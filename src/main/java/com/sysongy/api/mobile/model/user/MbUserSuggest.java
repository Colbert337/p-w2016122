package com.sysongy.api.mobile.model.user;

import java.util.Date;

public class MbUserSuggest {
    private String mbUserSuggestId;

    private String sysDriverId;

    private String mobilePhone;

    private String suggest;

    private String suggestRes;

    private String followUp;

    private String memo;

    private Date createdDate;

    private Date updatedDate;

    public String getMbUserSuggestId() {
        return mbUserSuggestId;
    }

    public void setMbUserSuggestId(String mbUserSuggestId) {
        this.mbUserSuggestId = mbUserSuggestId == null ? null : mbUserSuggestId.trim();
    }

    public String getSysDriverId() {
        return sysDriverId;
    }

    public void setSysDriverId(String sysDriverId) {
        this.sysDriverId = sysDriverId == null ? null : sysDriverId.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest == null ? null : suggest.trim();
    }

    public String getSuggestRes() {
        return suggestRes;
    }

    public void setSuggestRes(String suggestRes) {
        this.suggestRes = suggestRes == null ? null : suggestRes.trim();
    }

    public String getFollowUp() {
        return followUp;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp == null ? null : followUp.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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