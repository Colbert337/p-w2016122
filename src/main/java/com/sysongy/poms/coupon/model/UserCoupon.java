package com.sysongy.poms.coupon.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class UserCoupon extends BaseModel {
	// 用户优惠券关联ID
	private String user_coupon_id;
	// 优惠券ID
	private String coupon_id;
	// 优惠券编号
	private String coupon_no;
	// 用户角色编号
	private String sys_driver_id;
	// 优惠卷使用状态，0获得，1已使用，2冻结等异常状态
	private String isuse;
	//优惠开始时间
	private Date start_coupon_time;
	//优惠结束时间
	private Date end_coupon_time;
	// 创建人ID
	private String create_person_id;
	// 创建时间
	private Date create_time;
	// 最后修改人ID
	private String lastmodify_person_id;
	// 最后修改时间
	private Date lastmodify_time;
	//会员账号
	 private String user_name;
	 //认证姓名
	 private String full_name;
	 // 身份证号
	 private String identity_card;
	 //车牌号
	 private String plate_number;
	 //燃料类型
	 private String fuel_type;
	 //注册工作站编号
	 private String station_id;
	 //注册工作站名称
	private String regis_source;
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getIdentity_card() {
		return identity_card;
	}
	public void setIdentity_card(String identity_card) {
		this.identity_card = identity_card;
	}
	public String getPlate_number() {
		return plate_number;
	}
	public void setPlate_number(String plate_number) {
		this.plate_number = plate_number;
	}
	public String getFuel_type() {
		return fuel_type;
	}
	public void setFuel_type(String fuel_type) {
		this.fuel_type = fuel_type;
	}
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public String getRegis_source() {
		return regis_source;
	}
	public void setRegis_source(String regis_source) {
		this.regis_source = regis_source;
	}
	public String getUser_coupon_id() {
		return user_coupon_id;
	}
	public void setUser_coupon_id(String user_coupon_id) {
		this.user_coupon_id = user_coupon_id;
	}
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}

	public String getSys_driver_id() {
		return sys_driver_id;
	}
	public void setSys_driver_id(String sys_driver_id) {
		this.sys_driver_id = sys_driver_id;
	}
	public String getCoupon_no() {
		return coupon_no;
	}

	public void setCoupon_no(String coupon_no) {
		this.coupon_no = coupon_no;
	}
	public String getIsuse() {
		return isuse;
	}
	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}
	public Date getStart_coupon_time() {
		return start_coupon_time;
	}
	public void setStart_coupon_time(Date start_coupon_time) {
		this.start_coupon_time = start_coupon_time;
	}
	public Date getEnd_coupon_time() {
		return end_coupon_time;
	}
	public void setEnd_coupon_time(Date end_coupon_time) {
		this.end_coupon_time = end_coupon_time;
	}
	public String getCreate_person_id() {
		return create_person_id;
	}
	public void setCreate_person_id(String create_person_id) {
		this.create_person_id = create_person_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getLastmodify_person_id() {
		return lastmodify_person_id;
	}
	public void setLastmodify_person_id(String lastmodify_person_id) {
		this.lastmodify_person_id = lastmodify_person_id;
	}
	public Date getLastmodify_time() {
		return lastmodify_time;
	}
	public void setLastmodify_time(Date lastmodify_time) {
		this.lastmodify_time = lastmodify_time;
	}
}