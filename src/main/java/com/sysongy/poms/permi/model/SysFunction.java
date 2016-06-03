package com.sysongy.poms.permi.model;

import com.sysongy.poms.base.model.BaseModel;

import java.util.Date;

public class SysFunction extends BaseModel{
    private String sysFunctionId;

    private String parentId;

    private String functionName;

    private Integer functionType;

    private String functionDesc;

    private String functionCode;

    private Integer functionSort;

    private String functionPath;

    private String functionIcon;

    private Integer functionStatus;

    private Integer isMenu;

    private Integer isDeleted;

    private Date createdDate;

    private Date updatedDate;

    public String getSysFunctionId() {
        return sysFunctionId;
    }

    public void setSysFunctionId(String sysFunctionId) {
        this.sysFunctionId = sysFunctionId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public Integer getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Integer functionType) {
        this.functionType = functionType;
    }

    public String getFunctionDesc() {
        return functionDesc;
    }

    public void setFunctionDesc(String functionDesc) {
        this.functionDesc = functionDesc == null ? null : functionDesc.trim();
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode == null ? null : functionCode.trim();
    }

    public Integer getFunctionSort() {
        return functionSort;
    }

    public void setFunctionSort(Integer functionSort) {
        this.functionSort = functionSort;
    }

    public String getFunctionPath() {
        return functionPath;
    }

    public void setFunctionPath(String functionPath) {
        this.functionPath = functionPath == null ? null : functionPath.trim();
    }

    public String getFunctionIcon() {
        return functionIcon;
    }

    public void setFunctionIcon(String functionIcon) {
        this.functionIcon = functionIcon == null ? null : functionIcon.trim();
    }

    public Integer getFunctionStatus() {
        return functionStatus;
    }

    public void setFunctionStatus(Integer functionStatus) {
        this.functionStatus = functionStatus;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
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
}