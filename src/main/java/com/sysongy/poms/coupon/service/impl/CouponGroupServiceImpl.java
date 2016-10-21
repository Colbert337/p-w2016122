package com.sysongy.poms.coupon.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.sysongy.util.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.sysongy.poms.coupon.dao.CouponGroupMapper;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.CouponGroup;
import com.sysongy.poms.coupon.model.UserCoupon;
import com.sysongy.poms.coupon.service.CouponGroupService;
import com.sysongy.poms.coupon.service.CouponService;

@Service
public class CouponGroupServiceImpl implements CouponGroupService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CouponGroupMapper couponGroupMapper;
	
	private CouponService couponService;

	@Override
	public PageInfo<CouponGroup> queryCouponGroup(CouponGroup couponGroup) throws Exception {
		if (couponGroup.getPageNum() == null) {
			couponGroup.setPageNum(1);
			couponGroup.setPageSize(10);
		}
		if (StringUtils.isEmpty(couponGroup.getOrderby())) {
			couponGroup.setOrderby("create_time desc");
		}
		PageHelper.startPage(couponGroup.getPageNum(), couponGroup.getPageSize(), couponGroup.getOrderby());
		List<CouponGroup> list = couponGroupMapper.queryForPage(couponGroup);
		PageInfo<CouponGroup> pageInfo = new PageInfo<CouponGroup>(list);
		return pageInfo;
	}

	@Override
	public CouponGroup queryCouponGroupByPK(String coupongroupid) throws Exception {
		return couponGroupMapper.selectByPrimaryKey(coupongroupid);
	}

	@Override
	public String modifyCouponGroup(CouponGroup couponGroup, String userID) throws Exception {
		couponGroup.setLastmodify_person_id(userID);
		couponGroup.setLastmodify_time(new Date());
		couponGroupMapper.updateByPrimaryKey(couponGroup);
		return couponGroup.getCoupongroup_id();
	}

	@Override
	public String addCouponGroup(CouponGroup couponGroup, String userID) throws Exception {
		couponGroup.setCoupongroup_id(UUIDGenerator.getUUID());
		couponGroup.setCreate_person_id(userID);
		couponGroup.setCreate_time(new Date());
		couponGroup.setLastmodify_person_id(userID);
		couponGroup.setLastmodify_time(new Date());
		// 设置优惠卷组编号
		String coupongroup_no = "";
		CouponGroup aCouponGroup = new CouponGroup();
		if (StringUtils.isEmpty(aCouponGroup.getOrderby())) {
			aCouponGroup.setOrderby("create_time desc");
		}
		List<CouponGroup> couponGroupList = couponGroupMapper.queryForPage(aCouponGroup);
		if (couponGroupList.size() == 0) {
			coupongroup_no = "YHZ00001";
		} else {
			Integer tmp = Integer.valueOf(couponGroupList.get(0).getCoupongroup_no().substring(3,
					couponGroupList.get(0).getCoupongroup_no().length())) + 1;
			coupongroup_no = "YHZ" + StringUtils.leftPad(tmp.toString(), 5, "0");
		}
		couponGroup.setCoupongroup_no(coupongroup_no);
		couponGroupMapper.insert(couponGroup);
		return couponGroup.getCoupongroup_id();
	}

	@Override
	public Integer delCouponGroup(String coupongroupid) throws Exception {
		return couponGroupMapper.deleteByPrimaryKey(coupongroupid);
	}
	
	/**
	 * 根据业务场景给用户发送优惠劵组
	 */
	@Override
	public void sendCouponGroup(String driver_id, List<CouponGroup> list, String operator_id) throws Exception{
		
		if(list.size()<1){
			throw new Exception("优惠劵组为空，请检查");
		}
		
		for(int j=0;j<list.size();j++){

			CouponGroup group = list.get(j);
			
			String []coupon = group.getCoupon_ids().split(",");
			
			if(coupon.length < 1){
				throw new Exception("找不到对应的优惠劵信息");
			}
			
			for(int i=0;i<coupon.length;i++){
				Coupon tmp_coupon = couponService.queryCouponByPK(coupon[i]);
				
				UserCoupon userCoupon = new UserCoupon();
				BeanUtils.copyProperties(tmp_coupon, userCoupon);
				
				couponService.addUserCoupon(userCoupon, operator_id);
			}
		}
	}

}
