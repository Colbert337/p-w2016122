package com.sysongy.poms.card.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.usysparam.model.Usysparam;

@JsonIgnoreProperties
public class GasCard extends BaseModel{
	
	private String card_no;  //用户卡号
	
	private String card_no_arr;
	
	private List<String> card_no_list;
	
	private String card_type; //用户卡类型 0:LNG  1:柴油  2:CNG  3:汽油 4: 煤油

	private Usysparam card_type_info;

	//用户卡状态  0:已冻结 1：已入库；2：已出库；3:已/未发放 4:使用中 5:已失效
	private String card_status;

	private Usysparam cardStatusInfo;
	
	private String card_property;//用户卡属性 0:车辆卡  1:个人卡

	private String card_property_info;

	private String workstation; //用户卡所在地
	
	private String workstation_resp; //出入地责任人
	
	private String operator; //操作员
	
	private String batch_no; //批次号
	
	private Date storage_time;  //入库时间

	private Date release_time;  //出库时间
	
	private String storage_time_range;
	
	private String storage_time_before;
	
	private String storage_time_after;

	private String memo;

	private int card_flag_id;

	private String dName;

	private String mPhone;

	private String driverID;

	private Date station_receive_time;

	private String sysUserId;

	private SysUser sysUserIdInfo;
	
	private String card_no_start;
	
	private String card_no_end;

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

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCard_no_arr() {
		return card_no_arr;
	}

	public void setCard_no_arr(String card_no_arr) {
		this.card_no_arr = card_no_arr;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStorage_time_range() {
		return storage_time_range;
	}

	public void setStorage_time_range(String storage_time_range) {
		this.storage_time_range = storage_time_range;
	}

	public String getStorage_time_before() {
		return storage_time_before;
	}

	public void setStorage_time_before(String storage_time_before) {
		this.storage_time_before = storage_time_before;
	}

	public String getStorage_time_after() {
		return storage_time_after;
	}

	public void setStorage_time_after(String storage_time_after) {
		this.storage_time_after = storage_time_after;
	}

	public List<String> getCard_no_list() {
		return card_no_list;
	}

	public void setCard_no_list(List<String> card_no_list) {
		this.card_no_list = card_no_list;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_status() {
		return card_status;
	}

	public void setCard_status(String card_status) {
		this.card_status = card_status;
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

	public int getCard_flag_id() {
		return card_flag_id;
	}

	public void setCard_flag_id(int card_flag_id) {
		this.card_flag_id = card_flag_id;
	}

	public Usysparam getCard_type_info() {
		return card_type_info;
	}

	public void setCard_type_info(Usysparam card_type_info) {
		this.card_type_info = card_type_info;
	}

	public Usysparam getCardStatusInfo() {
		return cardStatusInfo;
	}

	public void setCardStatusInfo(Usysparam cardStatusInfo) {
		this.cardStatusInfo = cardStatusInfo;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public Date getStation_receive_time() {
		return station_receive_time;
	}

	public void setStation_receive_time(Date station_receive_time) {
		this.station_receive_time = station_receive_time;
	}

	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public SysUser getSysUserIdInfo() {
		return sysUserIdInfo;
	}

	public void setSysUserIdInfo(SysUser sysUserIdInfo) {
		this.sysUserIdInfo = sysUserIdInfo;
	}

	public String getDriverID() {
		return driverID;
	}

	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}

	public String getCard_property_info() {
		return card_property_info;
	}

	public void setCard_property_info(String card_property_info) {
		this.card_property_info = card_property_info;
	}
}