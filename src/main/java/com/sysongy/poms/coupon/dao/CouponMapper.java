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
	
	UserCoupon queryUserCouponByNo(String coupon_no, String driver_id);
}