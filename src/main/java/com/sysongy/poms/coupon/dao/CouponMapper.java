package com.sysongy.poms.coupon.dao;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.UserCoupon;

public interface CouponMapper {

	List<Coupon> queryForPage(Coupon record);
	
	 int deleteByPrimaryKey(String id);
	 
	 int insert(Coupon record);
	 
	 Coupon selectByPrimaryKey(String coupon_id);

	Coupon queryCouponById(String coupon_id);

	 int updateByPrimaryKey(Coupon record);
	 
	 List<UserCoupon> selectByUser_Coupon(UserCoupon record);
	 
	int insertUserCoupon(UserCoupon record);
	
	int updateUserCoupon(UserCoupon record);

	int updateUserCouponStatus(UserCoupon record);
	
	UserCoupon selectByUserCouponByPK(String user_coupon_id);
	
	UserCoupon queryUserCouponByID(String coupon_id, String sys_driver_id);

	/**
	 * 前用户可用优惠券列表
	 * @param record
	 * @return
	 */
	List<Coupon> queryCouponOrderByAmount(Coupon record);

	/**
	 * 当前用户可用优惠券列表（返回MAP）
	 * @param record
	 * @return
     */
	List<Map<String, Object>> queryCouponMapByAmount(Coupon record);

	/**
	 * 查询当前用户所有优惠券
	 * @param driverId
	 * @return
	 */
	List<Coupon> queryAllCouponForPage(String driverId);
	
	String queryUserCouponId(UserCoupon record);
	
	int updateStatus(String couponId, String sysDriverId);
	
	
}