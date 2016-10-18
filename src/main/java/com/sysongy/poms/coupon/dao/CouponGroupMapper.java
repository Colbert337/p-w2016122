package com.sysongy.poms.coupon.dao;

import java.util.List;

import com.sysongy.poms.coupon.model.CouponGroup;



public interface CouponGroupMapper {

	List<CouponGroup> queryForPage(CouponGroup couponGroup);
	
	 int deleteByPrimaryKey(String id);
	 
	 int insert(CouponGroup couponGroup);
	 
	 CouponGroup selectByPrimaryKey(String coupongroup_id);
	 
	 int updateByPrimaryKey(CouponGroup coupongroup_id);
	 
}