package com.hbkis.boss.wechat.model;

import java.util.Date;

public class Story {
    private String wxStoryId;

    private String storyName;

    private String coverImg;

    private String storyIntro;

    private Double storyTime;

    private Double commentCount;

    private String authorIntro;

    private Date createdDate;

    private Date modifiedDate;

    public String getWxStoryId() {
        return wxStoryId;
    }

    public void setWxStoryId(String wxStoryId) {
        this.wxStoryId = wxStoryId == null ? null : wxStoryId.trim();
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName == null ? null : storyName.trim();
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg == null ? null : coverImg.trim();
    }

    public String getStoryIntro() {
        return storyIntro;
    }

    public void setStoryIntro(String storyIntro) {
        this.storyIntro = storyIntro == null ? null : storyIntro.trim();
    }

    public Double getStoryTime() {
        return storyTime;
    }

    public void setStoryTime(Double storyTime) {
        this.storyTime = storyTime;
    }

    public Double getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Double commentCount) {
        this.commentCount = commentCount;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public void setAuthorIntro(String authorIntro) {
        this.authorIntro = authorIntro == null ? null : authorIntro.trim();
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
}