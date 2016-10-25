package com.sysongy.poms.coupon.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.sysongy.poms.coupon.dao.CouponGroupMapper;
import com.sysongy.poms.coupon.dao.CouponMapper;
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
	
	@Autowired
	private CouponMapper couponMapper;
	@Autowired
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
			aCouponGroup.setOrderby("coupongroup_no desc");
		}
		PageHelper.startPage(aCouponGroup.getPageNum(), aCouponGroup.getPageSize(), aCouponGroup.getOrderby());
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
			
			String []nums = group.getCoupon_nums().split(",");
			
			if(coupon.length != nums.length){
				throw new Exception("优惠劵组配置错误，请检查");
			}
			
			if(coupon.length < 1){
				throw new Exception("找不到对应的优惠劵信息");
			}
			
			for(int i=0;i<coupon.length;i++){
				for(int k=0;k<Integer.valueOf(nums[i]);k++){
					Coupon tmp_coupon = couponService.queryCouponByPK(coupon[i]);
					
					UserCoupon userCoupon = new UserCoupon();
					BeanUtils.copyProperties(tmp_coupon, userCoupon);
					userCoupon.setUser_coupon_id(UUIDGenerator.getUUID());
					userCoupon.setSys_driver_id(driver_id);
					userCoupon.setIsuse(GlobalConstant.COUPON_STATUS.UNUSE);

					couponService.addUserCoupon(userCoupon, operator_id);
				}
			}
		}
	}

	@Override
	public List<Coupon> queryCoupon(Coupon coupon,String coupongroup_id) throws Exception {
		CouponGroup couponGroup = couponGroupMapper.selectByPrimaryKey(coupongroup_id);
		List<Coupon> couponlist = couponMapper.queryForPage(coupon);
		if(null!=couponGroup){
			if(!"".equals(couponGroup.getCoupon_ids())&&null!=couponGroup.getCoupon_ids()){
				String[] coupon_id = couponGroup.getCoupon_ids().split(",");
				String[] coupon_num = couponGroup.getCoupon_nums().split(",");
				Iterator it = couponlist.iterator();
				while(it.hasNext()){
					Coupon aCoupon = (Coupon) it.next();
					for(int i=0;i<coupon_id.length;i++){
						if(coupon_id[i].equals(aCoupon.getCoupon_id())){
							aCoupon.setCoupon_check_status("true");
							aCoupon.setCoupon_check_nums(coupon_num[i]);
						}
					}
				}	
			}	
		}
			return couponlist;
	}
}
