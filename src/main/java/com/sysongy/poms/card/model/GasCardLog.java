package com.sysongy.poms.card.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class GasCardLog extends BaseModel{
	
    private String cardLogId; 

    private String card_no;

    private String card_type;

    private String card_status;
    
    private String card_property;

    private String workstation;

	private String workstation_resp;

    private Date optime;
    
    private String optime_range;
    
    private String optime_before;
    
    private String optime_after;

	private String batch_no;

    private String operator;

    private Date storage_time;

    private Date release_time;
    
    private String action;

    private String memo;
    
    private String card_no_start;
	
	private String card_no_end;
    
	private String mname;
	
	private String Work;
    public String getWork() {
		return Work;
	}

	public void setWork(String work) {
		Work = work;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getCard_no_start() {
		return card_no_start;
	}

	public void setCard_no_start(String card_no_start) {
		this.card_no_start = card_no_start;
	}

	public String getCard_no_end() {
		return card_no_end;
	}

	public void setCard_no_end(String card_no_end) {
		this.card_no_end = card_no_end;
	}

	public String getCard_property() {
		return card_property;
	}

	public void setCard_property(String card_property) {
		this.card_property = card_property;
	}

	public String getOptime_range() {
		return optime_range;
	}

	public void setOptime_range(String optime_range) {
		this.optime_range = optime_range;
	}

    public String getCardLogId() {
        return cardLogId;
    }

    public void setCardLogId(String cardLogId) {
        this.cardLogId = cardLogId == null ? null : cardLogId.trim();
    }
    
    public String getWorkstation() {
  		return workstation;
  	}

  	public void setWorkstation(String workstation) {
  		this.workstation = workstation;
  	}

  	public String getWorkstation_resp() {
  		return workstation_resp;
  	}

  	public void setWorkstation_resp(String workstation_resp) {
  		this.workstation_resp = workstation_resp;
  	}

  	public String getAction() {
  		return action;
  	}

  	public void setAction(String action) {
  		this.action = action;
  	}

    public String getCard_status() {
		return card_status;
	}

	public void setCard_status(String card_status) {
		this.card_status = card_status;
	}

	public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getMemo() {
        return memo;
    }

    public String getCard_no() {
		return card_no;
	}

	public String getOptime_before() {
		return optime_before;
	}

	public void setOptime_before(String optime_before) {
		this.optime_before = optime_before;
	}

	public String getOptime_after() {
		return optime_after;
	}

	public void setOptime_after(String optime_after) {
		this.optime_after = optime_after;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public Date getStorage_time() {
		return storage_time;
	}

	public void setStorage_time(Date storage_time) {
		this.storage_time = storage_time;
	}

	public Date getRelease_time() {
		return release_time;
	}

	public void setRelease_time(Date release_time) {
		this.release_time = release_time;
	}

	public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}