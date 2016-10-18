package com.sysongy.poms.coupon.service;


import com.github.pagehelper.PageInfo;
import com.sysongy.poms.coupon.model.CouponGroup;


public interface CouponGroupService {

	public PageInfo<CouponGroup> queryCouponGroup(CouponGroup couponGroup) throws Exception;

	public String modifyCouponGroup( CouponGroup couponGroup,String userID) throws Exception;
	
	public String addCouponGroup(CouponGroup couponGroup,String userID) throws Exception;

	public Integer delCouponGroup(String coupongroupid) throws Exception;

	public CouponGroup queryCouponGroupByPK(String coupongroupid) throws Exception;


}
