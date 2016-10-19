package com.sysongy.poms.coupon.dao;

import java.util.List;

import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.UserCoupon;

public interface CouponMapper {

	List<Coupon> queryForPage(Coupon record);
	
	 int deleteByPrimaryKey(String id);
	 
	 int insert(Coupon record);
	 
	 Coupon selectByPrimaryKey(String coupon_id);
	 
	 int updateByPrimaryKey(Coupon record);
	 
	 List<UserCoupon> selectByUser_Coupon(UserCoupon record);
	 
	int insertUserCoupon(UserCoupon record);
	
	int updateUserCoupon(UserCoupon record);
	
	UserCoupon selectByUserCouponByPK(String user_coupon_id);
	/**
	 * 前用户可用优惠券列表
	 * @param record
	 * @return
	 */
	List<Coupon> queryCouponOrderByAmount(Coupon record);
	/**
	 * 查询当前用户所有优惠券
	 * @param driverId
	 * @return
	 */
	List<Coupon> queryAllCouponForPage(String driverId);

	UserCoupon queryUserCouponByNo(String user_coupon_no);
}