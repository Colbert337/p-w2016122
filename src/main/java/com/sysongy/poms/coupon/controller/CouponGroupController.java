package com.sysongy.poms.coupon.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.CouponGroup;
import com.sysongy.poms.coupon.model.UserCoupon;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.coupon.service.CouponGroupService;
import com.sysongy.poms.coupon.service.CouponService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;

import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpSession;
import org.springframework.ui.ModelMap;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/web/couponGroup")
@Controller
public class CouponGroupController extends BaseContoller {

	@Autowired
	private CouponGroupService service;	
	@Autowired
	private CouponService couponService;
	@Autowired
	private DriverService driverService;

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
			if(couponGroup.getConvertPageNum() != null){
				if(couponGroup.getConvertPageNum() > couponGroup.getPageNumMax()){
					couponGroup.setPageNum(couponGroup.getPageNumMax());
				}else{
					couponGroup.setPageNum(couponGroup.getConvertPageNum());
				}
			}
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
			 issuedtype.substring(0,issuedtype.length()-1);
			 couponGroup.setIssued_type(issuedtype);
		 } 
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
		coupon.setNowDate("nowDate");
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
	
	/**
	 * 导入优惠卷组人员名单页面显示
	 * @param map
	 * @param coupongroup_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importUserCouponGroup")
	public String importUserCouponGroup(ModelMap map, String coupongroup_id) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/importUserCouponGroup";
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
			logger.error("根据[" + coupongroup_id + "]查询出错！", e);
			throw e;
		} finally {
			return ret;
		}
	}
	
	/**
	 * 导入优惠卷组给用户
	 * @param file
	 * @param coupon_id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/file")
	@ResponseBody
	public JSONObject file(@RequestParam("fileImport") MultipartFile file, String coupon_ids, HttpServletRequest request)
			throws Exception {
		String message = "";
		String info = "";
		PageBean bean = new PageBean();
		int count = 0;
		List<SysDriver> driverList = new ArrayList<SysDriver>();
		HttpSession session = request.getSession(true);
		CurrUser currUser = (CurrUser) session.getAttribute("currUser");// 登录人角色
		if (null == currUser) {
			bean.setRetCode(5000);
			bean.setRetMsg("登录信息过期，请重新登录！");
		}
		if (file.isEmpty()) {
			throw new Exception("文件不存在！");
		}
		String[] coupon_id = coupon_ids.split(",");
		try {
			if (file != null && !"".equals(file)) {
				// 转换成输入流
				InputStream is = file.getInputStream();
				Workbook book = Workbook.getWorkbook(is);
				Sheet sheet = book.getSheet(0);
				int rows = sheet.getRows();
				int err = 0;
				for (int i = 1; i < rows; i++) {
					if (sheet.getRow(i)[1] != null && !"".equals(sheet.getRow(i)[1])) {
						if(sheet.getRow(i)[1].getContents()!=null&&!"".equals(sheet.getRow(i)[1].getContents())){
							SysDriver sysDriver = new SysDriver();
							sysDriver.setMobilePhone(sheet.getRow(i)[1].getContents());
							List<SysDriver> driverInfo = driverService.queryeSingleList(sysDriver);
							if (sheet.getRow(i)[1] == null
									|| "".equals(sheet.getCell(1, i).getContents().replace(" ", ""))) {
								message += "第" + (i + 1) + "行电话号码不能为空！<br/>\r\n";
								err++;
								continue;
							}
							if (driverInfo.size() == 0) {
								message += "第" + (i + 1) + "行电话号码在系统中不存在！<br/>\r\n";
								err++;
								continue;
							}
							SysDriver driver = driverInfo.get(0);
							driverList.add(driver);
						for(int j=0;j<coupon_id.length;j++){
							UserCoupon userCoupon = new UserCoupon();
							userCoupon.setCoupon_id(coupon_id[j]);
							userCoupon.setSys_driver_id(driver.getSysDriverId());
							PageInfo<UserCoupon> userCouponInfo = couponService.queryUserCoupon(userCoupon);
							if (userCouponInfo.getSize() > 0) {
								Coupon coupon = couponService.queryCouponByPK(coupon_id[j]);
								info += "第" + i  + "行" + sheet.getRow(i)[0].getContents() + "已有"
										+ coupon.getCoupon_title() + "优惠卷！";
							}
						}
						count++;	
					  }
					}	
				}
				bean.setRetCode(100);
				if(!"".equals(info)){
					info += "如需重复获取，请确认保存！";
				}
				if(count>0){
					message += "导入名单中" + count + "名司机可以获得优惠卷!";
				}
				if(err>0){
					message += "导入名单中" + err + "名司机不能获得优惠卷！";
				}	
				bean.setRetMsg(message);
			}
		} catch (Exception e) {
			bean.setRetCode(500);
			bean.setRetMsg(e.getMessage());
			logger.error("", e);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("driverList", driverList);
		jsonObject.put("message", message);
		jsonObject.put("info", info);
		return jsonObject;
	}
	
	/**
	 * 导入享优惠卷司机名单
	 * @param map
	 * @param coupon_id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importCouponGroup")
	public String importCoupon(ModelMap map, @RequestParam("coupon_ids") String coupon_ids,
			 @RequestParam("coupon_nums") String coupon_nums,
			@RequestParam("coupongroup_id") String coupongroup_id,
			 HttpServletRequest request) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/importUserCoupon";
		HttpSession session = request.getSession(true);
		CurrUser currUser = (CurrUser) session.getAttribute("currUser");// 登录人角色
		if (null == currUser) {
			bean.setRetCode(5000);
			bean.setRetMsg("登录信息过期，请重新登录！");
			return ret;
		}
		coupon_ids =  new String(coupon_ids.getBytes("iso8859-1"),"UTF-8");
		//司机ID
		String[] sysDriverId =  request.getParameter("sysDriverIds").split(",");
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 //优惠卷ID
		 String[] couponid = coupon_ids.split(",");
		 //优惠卷数量
		 String[] coupon_num = coupon_nums.split(",");
		try {
			for(int j=0;j<couponid.length;j++){
				Coupon coupon = couponService.queryCouponByPK(couponid[j]);
				for(int i=0;i<sysDriverId.length;i++){
					UserCoupon userCoupon = new UserCoupon();
					userCoupon.setCoupon_id(couponid[j]);
					userCoupon.setCoupon_no(coupon.getCoupon_no());
					userCoupon.setCoupon_kind(coupon.getCoupon_kind());
					userCoupon.setSys_gas_station_id(coupon.getSys_gas_station_id());
					userCoupon.setStart_coupon_time(sdf.parse(coupon.getStart_coupon_time()));
					userCoupon.setEnd_coupon_time(sdf.parse(coupon.getEnd_coupon_time()));
					userCoupon.setSys_driver_id(sysDriverId[i]); 
					//获得优惠卷
					userCoupon.setIsuse("0");
					for(int num=0;num<Integer.parseInt(coupon_num[j]);num++){
						couponService.addUserCoupon(userCoupon, currUser.getUserId());		
					}
				}
			}
			bean.setRetMsg("" + sysDriverId.length + "名司机导入成功");
			ret = this.queryAllCouponGroupList(map, this.couponGroup == null ? new CouponGroup() : this.couponGroup);
			bean.setRetCode(100);
			bean.setRetValue(coupongroup_id);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			map.addAttribute("coupongroup_id", coupongroup_id);
			map.addAttribute("couponGroup", couponGroup);
			logger.error("" + sysDriverId.length + "名司机导入失败", e);
		} finally {
			return ret;
		}
	}
}
