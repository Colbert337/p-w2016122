package com.sysongy.poms.coupon.service;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.CouponGroup;


public interface CouponGroupService {

	public PageInfo<CouponGroup> queryCouponGroup(CouponGroup couponGroup) throws Exception;

	public String modifyCouponGroup( CouponGroup couponGroup,String userID) throws Exception;
	
	public String addCouponGroup(CouponGroup couponGroup,String userID) throws Exception;

	public Integer delCouponGroup(String coupongroupid) throws Exception;

	public CouponGroup queryCouponGroupByPK(String coupongroupid) throws Exception;
	
	public List<Coupon> queryCoupon(Coupon coupon,String coupongroup_id) throws Exception;
	
	public void sendCouponGroup(String driver_id, List<CouponGroup> list, String operator_id) throws Exception;
}
