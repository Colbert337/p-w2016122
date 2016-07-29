package com.sysongy.poms.crm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.sysongy.poms.base.model.BaseModel;

public class CrmHelp extends BaseModel{
    private String crmHelpId;
    
    private String crmHelpTypeId;

    private String title;

    private String question;

    private String issuer;

    private String answer;

    private String imgs;

    private Integer isNotice;

    private Integer isDeleted;
    
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    private Date createdDate;
    
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    private Date updatedDate;

    public String getCrmHelpId() {
        return crmHelpId;
    }

    public void setCrmHelpId(String crmHelpId) {
        this.crmHelpId = crmHelpId == null ? null : crmHelpId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer == null ? null : issuer.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs == null ? null : imgs.trim();
    }

    public Integer getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(Integer isNotice) {
        this.isNotice = isNotice;
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

	public String getCrmHelpTypeId() {
		return crmHelpTypeId;
	}

	public void setCrmHelpTypeId(String crmHelpTypeId) {
		this.crmHelpTypeId = crmHelpTypeId;
	}
    
}