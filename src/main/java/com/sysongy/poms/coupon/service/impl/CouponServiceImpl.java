package com.sysongy.poms.coupon.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sysongy.poms.coupon.dao.CouponMapper;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.UserCoupon;
import com.sysongy.poms.coupon.service.CouponService;
import com.sysongy.util.UUIDGenerator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CouponServiceImpl implements CouponService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CouponMapper couponMapper;

	@Override
	public PageInfo<Coupon> queryCoupon(Coupon record) throws Exception {
		if (record.getPageNum() == null) {
			record.setPageNum(1);
			record.setPageSize(10);
		}
		if (StringUtils.isEmpty(record.getOrderby())) {
			record.setOrderby("lastmodify_time desc");
		}
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Coupon> list = couponMapper.queryForPage(record);
		PageInfo<Coupon> pageInfo = new PageInfo<Coupon>(list);
		return pageInfo;
	}

	@Override
	public String addCoupon(Coupon coupon, String userID) throws Exception {
		coupon.setCoupon_id(UUIDGenerator.getUUID());
		coupon.setCreate_person_id(userID);
		coupon.setCreate_time(new Date());
		coupon.setLastmodify_person_id(userID);
		coupon.setLastmodify_time(new Date());
		// 设置优惠卷编号
		String coupon_no = "";
		Coupon aCoupon = new Coupon();
		aCoupon.setOrderby("coupon_no desc");
		PageHelper.startPage(aCoupon.getPageNum(), aCoupon.getPageSize(), aCoupon.getOrderby());
			//平台优惠卷
			if (coupon.getCoupon_kind().equals("1")) {
				aCoupon.setCoupon_kind("1");
				List<Coupon> couponList = couponMapper.queryForPage(aCoupon);
				if(couponList.size()==0){
					coupon_no = "J10010000100001";
				}else{
					Integer tmp = Integer.valueOf(couponList.get(0).getCoupon_no().substring(10,couponList.get(0).getCoupon_no().length()))+1;
					coupon_no = "J100100001" + StringUtils.leftPad(tmp.toString(), 5, "0");
				}
			} else {
				aCoupon.setCoupon_kind("2");
				aCoupon.setSys_gas_station_id(coupon.getSys_gas_station_id());
				List<Coupon> couponList = couponMapper.queryForPage(aCoupon);
				if(couponList.size()==0){
					coupon_no = "J0" + coupon.getSys_gas_station_id().substring(2, coupon.getSys_gas_station_id().length())
							+ "00001";
				}else{
					Integer tmp = Integer.valueOf(couponList.get(0).getCoupon_no().substring(couponList.get(0).getCoupon_no().length()-5,couponList.get(0).getCoupon_no().length()))+1;
					coupon_no = "J0" + couponList.get(0).getCoupon_no().substring(2,couponList.get(0).getCoupon_no().length()-5)+StringUtils.leftPad(tmp.toString(), 5, "0");
				}
			}
		coupon.setCoupon_no(coupon_no);
		if ("1".equals(coupon.getCoupon_type())) {
			coupon.setPreferential_discount(coupon.getPreferential_money());
		} else {
			coupon.setPreferential_discount(coupon.getPreferential_discount());
		}
		//优惠卷标题
		String coupontitle="";
		if("1".equals(coupon.getCoupon_type())){
			if("1".equals(coupon.getUse_condition())){
				coupontitle="满"+coupon.getLimit_money()+"元减"+coupon.getPreferential_discount()+"元";
			}else{
				coupontitle="立减"+coupon.getPreferential_discount()+"元";
			}
		}else{
			if("1".equals(coupon.getUse_condition())){
				coupontitle="满"+coupon.getLimit_money()+"元打"+coupon.getPreferential_discount()+"折";
			}else{
				coupontitle="立打"+coupon.getPreferential_discount()+"折";
			}
		}
		coupon.setCoupon_title(coupontitle);
		//设置优惠结束时间
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sf.parse(coupon.getEnd_coupon_time()));
		calendar.add(Calendar.HOUR_OF_DAY ,23);
		calendar.add(Calendar.MINUTE, 59);
		calendar.add(Calendar.SECOND, 59);
		coupon.setEnd_coupon_time(sdf.format(calendar.getTime()));
		couponMapper.insert(coupon);
		return coupon.getCoupon_id();
	}

	@Override
	public String modifyCoupon(Coupon coupon, String userID) throws Exception {
		coupon.setLastmodify_person_id(userID);
		coupon.setLastmodify_time(new Date());
		if ("1".equals(coupon.getCoupon_type())) {
			coupon.setPreferential_discount(coupon.getPreferential_money());
		} else {
			coupon.setPreferential_discount(coupon.getPreferential_discount());
		}
		//优惠卷标题
		String coupontitle="";
		if("1".equals(coupon.getCoupon_type())){
			if("1".equals(coupon.getUse_condition())){
				coupontitle="满"+coupon.getLimit_money()+"元减"+coupon.getPreferential_discount()+"元";
			}else{
				coupontitle="立减"+coupon.getPreferential_discount()+"元";
			}
		}else{
			if("1".equals(coupon.getUse_condition())){
				coupontitle="满"+coupon.getLimit_money()+"元打"+coupon.getPreferential_discount()+"折";
			}else{
				coupontitle="立打"+coupon.getPreferential_discount()+"折";
			}
		}
		coupon.setCoupon_title(coupontitle);
		//设置优惠结束时间
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sf.parse(coupon.getEnd_coupon_time()));
		calendar.add(Calendar.HOUR_OF_DAY ,23);
		calendar.add(Calendar.MINUTE, 59);
		calendar.add(Calendar.SECOND, 59);
		coupon.setEnd_coupon_time(sdf.format(calendar.getTime()));
		couponMapper.updateByPrimaryKey(coupon);
		return coupon.getCoupon_id();
	}

	@Override
	public Integer delCoupon(String couponid) throws Exception {
		return couponMapper.deleteByPrimaryKey(couponid);
	}

	@Override
	public Coupon queryCouponByPK(String couponid) throws Exception {
		return couponMapper.selectByPrimaryKey(couponid);
	}

	@Override
	public PageInfo<UserCoupon> queryUserCoupon(UserCoupon userCoupon) throws Exception {
		if (userCoupon.getPageNum() == null) {
			userCoupon.setPageNum(1);
			userCoupon.setPageSize(10);
		}
		if (StringUtils.isEmpty(userCoupon.getOrderby())) {
			userCoupon.setOrderby("lastmodify_time desc");
		}
		PageHelper.startPage(userCoupon.getPageNum(), userCoupon.getPageSize(), userCoupon.getOrderby());
		List<UserCoupon> list = couponMapper.selectByUser_Coupon(userCoupon);
		PageInfo<UserCoupon> pageInfo = new PageInfo<UserCoupon>(list);
		return pageInfo;
	}

	@Override
	public String addUserCoupon(UserCoupon userCoupon, String userID) throws Exception {
		userCoupon.setUser_coupon_id(UUIDGenerator.getUUID());
		userCoupon.setCreate_person_id(userID);
		userCoupon.setCreate_time(new Date());
		userCoupon.setLastmodify_person_id(userID);
		userCoupon.setLastmodify_time(new Date());
		couponMapper.insertUserCoupon(userCoupon);
		return userCoupon.getUser_coupon_id();
	}

	@Override
	public String modifyUserCoupon(UserCoupon userCoupon, String userID) throws Exception {
		userCoupon.setLastmodify_person_id(userID);
		userCoupon.setLastmodify_time(new Date());
		couponMapper.updateUserCoupon(userCoupon);
		return userCoupon.getUser_coupon_id();
	}

	@Override
	public int modifyUserCouponStatus(UserCoupon userCoupon) throws Exception {
		return couponMapper.updateUserCouponStatus(userCoupon);
	}
	@Override
	public UserCoupon queryUserCouponByPK(String user_coupon_id) throws Exception {
		return couponMapper.selectByUserCouponByPK(user_coupon_id);
	}

	@Override
	public PageInfo<Coupon> queryCouponOrderByAmount(Coupon record) throws Exception {
		if (record.getPageNum() == null) {
			record.setPageNum(1);
			record.setPageSize(10);
		}

		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Coupon> list = couponMapper.queryCouponOrderByAmount(record);
		PageInfo<Coupon> pageInfo = new PageInfo<Coupon>(list);
		return pageInfo;
	}

	@Override
	public List<Map<String, Object>> queryCouponMapByAmount(Coupon record) {
		List<Map<String, Object>> list = couponMapper.queryCouponMapByAmount(record);
		return list;
	}

	/**
	 * 查询当前用户所有优惠券
	 * @param driverId
	 * @return
	 */
	@Override
	public PageInfo<Coupon> queryAllCouponForPage(Coupon record, String driverId) throws Exception {
		if (record.getPageNum() == null) {
			record.setPageNum(1);
			record.setPageSize(10);
		}
		if (StringUtils.isEmpty(record.getOrderby())) {
			record.setOrderby("preferential_discount+0 DESC");
		}
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Coupon> list = couponMapper.queryAllCouponForPage(driverId);
		PageInfo<Coupon> pageInfo = new PageInfo<Coupon>(list);
		return pageInfo;
	}

	@Override
	public UserCoupon queryUserCouponByNo(String coupon_id, String driver_id) throws Exception {
		return couponMapper.queryUserCouponByID(coupon_id, driver_id);
	}

	@Override
	public int updateUserCouponStatus(UserCoupon record) {
		return couponMapper.updateUserCouponStatus(record);
	}

	@Override
	public String queryUserCouponId(UserCoupon record) {
		return couponMapper.queryUserCouponId(record);
	}

	@Override
	public int updateStatus(String couponId, String sysDriverId) {
		return couponMapper.updateStatus(couponId, sysDriverId);
	}

}
