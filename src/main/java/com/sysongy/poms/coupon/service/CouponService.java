package com.sysongy.poms.coupon.service;


import com.github.pagehelper.PageInfo;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.UserCoupon;

public interface CouponService {

	public PageInfo<Coupon> queryCoupon(Coupon record) throws Exception;

	public String modifyCoupon(Coupon record,String userID) throws Exception;
	
	public String addCoupon(Coupon record,String userID) throws Exception;

	public Integer delCoupon(String couponid) throws Exception;

	public Coupon queryCouponByPK(String couponid) throws Exception;

	public String modifyUserCoupon(UserCoupon userCoupon, String userID) throws Exception;

	public String addUserCoupon(UserCoupon userCoupon, String userID) throws Exception;

	public PageInfo<UserCoupon> queryUserCoupon(UserCoupon userCoupon) throws Exception;
	
	public UserCoupon queryUserCouponByPK(String user_coupon_id) throws Exception;

}
