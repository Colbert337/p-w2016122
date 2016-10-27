package com.sysongy.poms.coupon.service;


import com.github.pagehelper.PageInfo;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.UserCoupon;

import java.util.List;
import java.util.Map;

public interface CouponService {

	public PageInfo<Coupon> queryCoupon(Coupon record) throws Exception;

	public String modifyCoupon(Coupon record,String userID) throws Exception;
	
	public String addCoupon(Coupon record,String userID) throws Exception;

	public Integer delCoupon(String couponid) throws Exception;

	public Coupon queryCouponByPK(String couponid) throws Exception;

	public String modifyUserCoupon(UserCoupon userCoupon, String userID) throws Exception;

	public int modifyUserCouponStatus(UserCoupon record) throws Exception ;

	public String addUserCoupon(UserCoupon userCoupon, String userID) throws Exception;

	public PageInfo<UserCoupon> queryUserCoupon(UserCoupon userCoupon) throws Exception;
	
	public UserCoupon queryUserCouponByPK(String user_coupon_id) throws Exception;
	
	public UserCoupon queryUserCouponByNo(String coupon_no, String driver_id) throws Exception;

	public PageInfo<Coupon> queryCouponOrderByAmount(Coupon record) throws Exception;

	/**
	 * 查询当前用户所有优惠券
	 * @param driverId
	 * @return
	 */
	public PageInfo<Coupon> queryAllCouponForPage(Coupon record,String driverId) throws Exception;

	/**
	 * 当前用户可用优惠券列表（返回MAP）
	 * @param record
	 * @return
	 */
	PageInfo <Map<String, Object>> queryCouponMapByAmount(Coupon record);
}
