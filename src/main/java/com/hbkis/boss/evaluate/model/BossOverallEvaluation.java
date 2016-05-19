package com.hbkis.boss.evaluate.model;

import java.util.Date;

public class BossOverallEvaluation {
    private String bossOverallEvaluationId;

    private Integer heightLevel;

    private Integer weightLevel;

    private Integer bmiLevel;

    private String evaluate;

    private String suggest;

    private Integer sort;
    
    private Integer isDeleted;

    private Date createdDate;

    private Date modifiedDate;
    
    public String getBossOverallEvaluationId() {
        return bossOverallEvaluationId;
    }

    public void setBossOverallEvaluationId(String bossOverallEvaluationId) {
        this.bossOverallEvaluationId = bossOverallEvaluationId == null ? null : bossOverallEvaluationId.trim();
    }

    public Integer getHeightLevel() {
        return heightLevel;
    }

    public void setHeightLevel(Integer heightLevel) {
        this.heightLevel = heightLevel;
    }

    public Integer getWeightLevel() {
        return weightLevel;
    }

    public void setWeightLevel(Integer weightLevel) {
        this.weightLevel = weightLevel;
    }

    public Integer getBmiLevel() {
        return bmiLevel;
    }

    public void setBmiLevel(Integer bmiLevel) {
        this.bmiLevel = bmiLevel;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate == null ? null : evaluate.trim();
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest == null ? null : suggest.trim();
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

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
    
}