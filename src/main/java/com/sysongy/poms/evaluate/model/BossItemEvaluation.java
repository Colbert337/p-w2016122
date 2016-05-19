package com.sysongy.poms.evaluate.model;

import java.util.Date;

public class BossItemEvaluation {
    private String bossItemEvaluationId;

    private Integer itemType;

    private String standard;

    private Integer level;

    private String evaluate;
    
    private Integer sort;

    private Integer isDeleted;

    private Date createdDate;

    private Date modifiedDate;

    public String getBossItemEvaluationId() {
        return bossItemEvaluationId;
    }

    public void setBossItemEvaluationId(String bossItemEvaluationId) {
        this.bossItemEvaluationId = bossItemEvaluationId == null ? null : bossItemEvaluationId.trim();
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard == null ? null : standard.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate == null ? null : evaluate.trim();
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