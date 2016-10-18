package com.sysongy.poms.coupon.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class Coupon extends BaseModel {
	// 优惠券ID
	private String coupon_id;
	// 优惠券编号
	private String coupon_no;
	// 优惠卷名称
	private String coupon_title;
	// 优惠券详情
	private String coupon_detail;
	// 优惠券类型，1代金卷 2折扣卷
	private String coupon_type;
	// 优惠卷种类，1平台优惠卷 2气站优惠卷
	private String coupon_kind;
	//加注站Id
	private String sys_gas_station_id;
	//加注站名称
	private String gas_station_name;
	// 优惠金额
	private String preferential_money;
	// 优惠折扣
	private String preferential_discount;
	// 使用条件，1满XX元使用 2无条件使用
	private String use_condition;
	// 限制金额
	private String limit_money;
	// 发放方式，1发放到账、2用户抢领
	private String issuance_type;
	// 消费方式，1加气、2加油、3积分兑换
	private String consume_type;
	// 是否启用，1启用 2停用
	private String status;
	// 优惠开始时间
	private String start_coupon_time;
	// 优惠结束时间
	private String end_coupon_time;
	// 创建人ID
	private String create_person_id;
	// 创建时间
	private Date create_time;
	// 最后修改人ID
	private String lastmodify_person_id;
	// 最后修改时间
	private Date lastmodify_time;

	public String getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}

	public String getCoupon_title() {
		return coupon_title;
	}

	public void setCoupon_title(String coupon_title) {
		this.coupon_title = coupon_title;
	}

	public String getCoupon_detail() {
		return coupon_detail;
	}

	public void setCoupon_detail(String coupon_detail) {
		this.coupon_detail = coupon_detail;
	}

	public String getCoupon_type() {
		return coupon_type;
	}

	public void setCoupon_type(String coupon_type) {
		this.coupon_type = coupon_type;
	}

	public String getCoupon_kind() {
		return coupon_kind;
	}

	public void setCoupon_kind(String coupon_kind) {
		this.coupon_kind = coupon_kind;
	}

	public String getPreferential_money() {
		return preferential_money;
	}

	public void setPreferential_money(String preferential_money) {
		this.preferential_money = preferential_money;
	}

	public String getPreferential_discount() {
		return preferential_discount;
	}

	public void setPreferential_discount(String preferential_discount) {
		this.preferential_discount = preferential_discount;
	}

	public String getUse_condition() {
		return use_condition;
	}

	public void setUse_condition(String use_condition) {
		this.use_condition = use_condition;
	}

	public String getLimit_money() {
		return limit_money;
	}

	public void setLimit_money(String limit_money) {
		this.limit_money = limit_money;
	}

	public String getIssuance_type() {
		return issuance_type;
	}

	public void setIssuance_type(String issuance_type) {
		this.issuance_type = issuance_type;
	}

	public String getConsume_type() {
		return consume_type;
	}

	public void setConsume_type(String consume_type) {
		this.consume_type = consume_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStart_coupon_time() {
		return start_coupon_time;
	}

	public void setStart_coupon_time(String start_coupon_time) {
		this.start_coupon_time = start_coupon_time;
	}

	public String getEnd_coupon_time() {
		return end_coupon_time;
	}

	public void setEnd_coupon_time(String end_coupon_time) {
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

	public String getGas_station_name() {
		return gas_station_name;
	}

	public void setGas_station_name(String gas_station_name) {
		this.gas_station_name = gas_station_name;
	}
	public String getSys_gas_station_id() {
		return sys_gas_station_id;
	}

	public void setSys_gas_station_id(String sys_gas_station_id) {
		this.sys_gas_station_id = sys_gas_station_id;
	}

	public String getCoupon_no() {
		return coupon_no;
	}

	public void setCoupon_no(String coupon_no) {
		this.coupon_no = coupon_no;
	}
	
}
