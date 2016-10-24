package com.sysongy.poms.coupon.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.CouponGroup;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.coupon.service.CouponGroupService;
import com.sysongy.poms.gastation.model.Gastation;

import net.sf.json.JSONArray;

import javax.servlet.http.HttpSession;
import org.springframework.ui.ModelMap;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/web/couponGroup")
@Controller
public class CouponGroupController extends BaseContoller {

	@Autowired
	private CouponGroupService service;	
	

	private CouponGroup couponGroup;

	/**
	 * 查询优惠卷组信息
	 * 
	 * @param map
	 * @param coupon
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/couponGroupList")
	public String queryAllCouponGroupList(ModelMap map, CouponGroup couponGroup) throws Exception {
		this.couponGroup = couponGroup;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/manageCouponGroup";
		try {
			PageInfo<CouponGroup> pageinfo = service.queryCouponGroup(couponGroup);
			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("couponGroup", couponGroup);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			logger.error("查询优惠卷组信息错误！", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 更新优惠卷组信息
	 * 
	 * @param map
	 * @param coupon
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveCouponGroup")
	public String addCouponGroup(ModelMap map, CouponGroup couponGroup,
			@RequestParam("couponNums") String couponNums,	HttpServletRequest request) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/addCouponGroup";
		String coupongroup_id = null;
		HttpSession session = request.getSession(true);
		CurrUser currUser = (CurrUser) session.getAttribute("currUser");// 登录人角色
		if (null == currUser) {
			bean.setRetCode(5000);
			bean.setRetMsg("登录信息过期，请重新登录！");
			return ret;
		}
		//优惠卷个数
		couponNums =  new String(couponNums.getBytes("iso8859-1"),"UTF-8");
		 //发放类型
		 String[] issued_type =  request.getParameterValues("issued_type");
		 couponGroup.setCoupon_nums(couponNums);
		 //设置发放类型
		 String issuedtype = "";
		 if(issued_type!=null){
			 for(int i=0;i<issued_type.length;i++){
				 issuedtype+=issued_type[i]+",";
			 } 	 
		 }
		 issuedtype.substring(0,issuedtype.length()-1);
		 couponGroup.setIssued_type(issuedtype);
		try {
			if (null == couponGroup.getCoupongroup_id()) {
				coupongroup_id = service.addCouponGroup(couponGroup, currUser.getUserId());
				bean.setRetMsg("[" + couponGroup.getCoupongroup_title() + "]新增成功");
			} else {
				coupongroup_id = service.modifyCouponGroup(couponGroup, currUser.getUserId());
				bean.setRetMsg("[" + couponGroup.getCoupongroup_title() + "]保存成功");
			}
			ret = this.queryAllCouponGroupList(map, this.couponGroup == null ? new CouponGroup() : this.couponGroup);
			bean.setRetCode(100);
			bean.setRetValue(coupongroup_id);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			map.addAttribute("coupon_id", coupongroup_id);
			map.addAttribute("couponGroup", couponGroup);
			logger.error("更新优惠卷组信息错误！", e);
		} finally {
			return ret;
		}
	}

	/**
	 * 修改优惠卷信息页面展现
	 * 
	 * @param map
	 * @param coupon
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyCouponGroup")
	public String modifyCouponGroup(ModelMap map, String coupongroup_id) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/modifyCouponGroup";
		CouponGroup couponGroup = new CouponGroup();
		try {
			couponGroup = service.queryCouponGroupByPK(coupongroup_id);
			bean.setRetMsg("根据[" + coupongroup_id + "]查询成功");
			bean.setRetCode(100);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("couponGroup", couponGroup);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			ret = this.queryAllCouponGroupList(map, this.couponGroup == null ? new CouponGroup() : this.couponGroup);
			map.addAttribute("ret", bean);
			logger.error("根据" + coupongroup_id + "查询优惠卷组信息出错！", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 删除优惠卷
	 * 
	 * @param map
	 * @param couponid
	 * @return
	 */
	@RequestMapping("/deleteCouponGroup")
	public String deleteCoupon(ModelMap map, @RequestParam String coupongroup_id) {

		PageBean bean = new PageBean();

		String ret = "webpage/poms/coupon/manageCouponGroup";
		Integer rowcount = null;
		try {
			if (coupongroup_id != null && !"".equals(coupongroup_id)) {
				rowcount = service.delCouponGroup(coupongroup_id);
				ret = this.queryAllCouponGroupList(map,
						this.couponGroup == null ? new CouponGroup() : this.couponGroup);
				bean.setRetCode(100);
				bean.setRetMsg("删除成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);
				map.addAttribute("ret", bean);
			}
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			ret = this.queryAllCouponGroupList(map, this.couponGroup == null ? new CouponGroup() : this.couponGroup);
			map.addAttribute("ret", bean);
			logger.error("根据" + coupongroup_id + "删除优惠卷组错误！", e);
			throw e;
		} finally {
			return ret;
		}
	}
	
	
	/**
	 * 查询所有的优惠卷信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/couponList")
	@ResponseBody
	public String selectCouponList(ModelMap map,HttpServletRequest request) throws Exception {
		Coupon coupon = new Coupon();
		List<Coupon> list;
		// 优惠卷状态是开启的
		coupon.setStatus("1");
		String coupongroupid =   request.getParameter("coupongroup_id");
		try {
			list =  service.queryCoupon(coupon,coupongroupid);
		} catch (Exception e) {
			logger.error("查询优惠卷信息出错！", e);
			throw e;
		}
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(list);
		return jsonArray.toString();
	}
	
	
}
