package com.sysongy.poms.coupon.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class CouponGroup extends BaseModel {

	// 优惠券组ID
	private String coupongroup_id;
	// 优惠券组编号
	private String coupongroup_no;
	// 优惠卷组名称
	private String coupongroup_title;
	// 优惠券组详情
	private String coupongroup_detail;
	// 优惠券组中多个优惠卷IDs
	private String coupon_ids;
	// 优惠券组中多个优惠卷的发放个数
	private String coupon_nums;
	//优惠卷组的发放类型
	private String issued_type;
	// 额定消费优惠开始时间
	private String start_moneyrated_time;
	// 额定消费优惠结束时间
	private String end_moneyrated_time;
	// 额定次数优惠开始时间
	private String start_timesrated_time;
	// 额定次数优惠结束时间
	private String end_timesrated_time;
	// 额定时间内额定消费金额
	private String consume_money;
	// 额定时间内额定消费次数
	private String consume_times;
	// 创建人ID
	private String create_person_id;
	// 创建时间
	private Date create_time;
	// 最后修改人ID
	private String lastmodify_person_id;
	// 最后修改时间
	private Date lastmodify_time;
	// 优惠券组中多个优惠卷IDs
	private String coupon_titles;

	public String getCoupon_nums() {
		return coupon_nums;
	}
	public void setCoupon_nums(String coupon_nums) {
		this.coupon_nums = coupon_nums;
	}
	public String getCoupongroup_id() {
		return coupongroup_id;
	}
	public void setCoupongroup_id(String coupongroup_id) {
		this.coupongroup_id = coupongroup_id;
	}
	public String getCoupongroup_no() {
		return coupongroup_no;
	}
	public void setCoupongroup_no(String coupongroup_no) {
		this.coupongroup_no = coupongroup_no;
	}
	public String getCoupongroup_title() {
		return coupongroup_title;
	}
	public void setCoupongroup_title(String coupongroup_title) {
		this.coupongroup_title = coupongroup_title;
	}
	public String getCoupongroup_detail() {
		return coupongroup_detail;
	}
	public void setCoupongroup_detail(String coupongroup_detail) {
		this.coupongroup_detail = coupongroup_detail;
	}
	public String getCoupon_ids() {
		return coupon_ids;
	}
	public void setCoupon_ids(String coupon_ids) {
		this.coupon_ids = coupon_ids;
	}
	public String getIssued_type() {
		return issued_type;
	}
	public void setIssued_type(String issued_type) {
		this.issued_type = issued_type;
	}
	public String getStart_moneyrated_time() {
		return start_moneyrated_time;
	}
	public void setStart_moneyrated_time(String start_moneyrated_time) {
		this.start_moneyrated_time = start_moneyrated_time;
	}
	public String getEnd_moneyrated_time() {
		return end_moneyrated_time;
	}
	public void setEnd_moneyrated_time(String end_moneyrated_time) {
		this.end_moneyrated_time = end_moneyrated_time;
	}
	public String getStart_timesrated_time() {
		return start_timesrated_time;
	}
	public void setStart_timesrated_time(String start_timesrated_time) {
		this.start_timesrated_time = start_timesrated_time;
	}
	public String getEnd_timesrated_time() {
		return end_timesrated_time;
	}
	public void setEnd_timesrated_time(String end_timesrated_time) {
		this.end_timesrated_time = end_timesrated_time;
	}
	public String getConsume_money() {
		return consume_money;
	}
	public void setConsume_money(String consume_money) {
		this.consume_money = consume_money;
	}
	public String getConsume_times() {
		return consume_times;
	}
	public void setConsume_times(String consume_times) {
		this.consume_times = consume_times;
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
	public String getCoupon_titles() {
		return coupon_titles;
	}
	public void setCoupon_titles(String coupon_titles) {
		this.coupon_titles = coupon_titles;
	}
}
