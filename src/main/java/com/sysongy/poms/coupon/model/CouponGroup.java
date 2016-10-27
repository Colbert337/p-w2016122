package com.sysongy.poms.coupon.model;

import java.util.Date;
import java.util.List;

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
	// 创建人ID
	private String create_person_id;
	// 创建时间
	private Date create_time;
	// 最后修改人ID
	private String lastmodify_person_id;
	// 最后修改时间
	private Date lastmodify_time;
	// 优惠券组中多个优惠卷标题
	private String coupon_titles;
	//优惠卷发送类型
	private List<Integer> issued_types; 
	
	public List<Integer> getIssued_types() {
		return issued_types;
	}
	public void setIssued_types(List<Integer> issued_types) {
		this.issued_types = issued_types;
	}
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
