package com.sysongy.poms.page.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class SysStaticPage extends BaseModel{
	
    private String id;

    private String pageTitle;

    private String pageBody;

    private String pageTicker;

    private String pageStatus;

    private Date pageCreatedTime;

    private String pageCreator;

    private String memo;

    private String pageHtml;
    
    private String page_created_time;
    
    private String page_created_time_after;
    
    private String page_created_time_before;

    public String getPage_created_time() {
		return page_created_time;
	}

	public void setPage_created_time(String page_created_time) {
		this.page_created_time = page_created_time;
	}

	public String getPage_created_time_before() {
		return page_created_time_before;
	}

	public void setPage_created_time_before(String page_created_time_before) {
		this.page_created_time_before = page_created_time_before;
	}

	public String getPage_created_time_after() {
		return page_created_time_after;
	}

	public void setPage_created_time_after(String page_created_time_after) {
		this.page_created_time_after = page_created_time_after;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle == null ? null : pageTitle.trim();
    }

    public String getPageBody() {
        return pageBody;
    }

    public void setPageBody(String pageBody) {
        this.pageBody = pageBody == null ? null : pageBody.trim();
    }

    public String getPageTicker() {
        return pageTicker;
    }

    public void setPageTicker(String pageTicker) {
        this.pageTicker = pageTicker == null ? null : pageTicker.trim();
    }

    public String getPageStatus() {
        return pageStatus;
    }

    public void setPageStatus(String pageStatus) {
        this.pageStatus = pageStatus == null ? null : pageStatus.trim();
    }

    public Date getPageCreatedTime() {
        return pageCreatedTime;
    }

    public void setPageCreatedTime(Date pageCreatedTime) {
        this.pageCreatedTime = pageCreatedTime;
    }

    public String getPageCreator() {
        return pageCreator;
    }

    public void setPageCreator(String pageCreator) {
        this.pageCreator = pageCreator == null ? null : pageCreator.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getPageHtml() {
        return pageHtml;
    }

    public void setPageHtml(String pageHtml) {
        this.pageHtml = pageHtml == null ? null : pageHtml.trim();
    }
}