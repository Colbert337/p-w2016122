package com.sysongy.poms.coupon.controller;

import jxl.Sheet;
import jxl.Workbook;
import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.UserCoupon;
import com.sysongy.poms.coupon.service.CouponService;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;



@RequestMapping("/web/coupon")
@Controller
public class CouponController extends BaseContoller {

	@Autowired
	private CouponService service;
	@Autowired
	private DriverService driverService;

	@Autowired
	private GastationService gastationService;

	private Coupon coupon;

	/**
	 * 查询优惠卷信息
	 * @param map
	 * @param coupon
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/couponList")
	public String queryAllCouponList(ModelMap map, Coupon coupon) throws Exception {
		this.coupon = coupon;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/manageCoupon";
		try {
			PageInfo<Coupon> pageinfo = service.queryCoupon(coupon);
			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("coupon", coupon);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			logger.error("查询优惠卷信息错误！", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 更新优惠卷信息
	 * @param map
	 * @param coupon
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveCoupon")
	public String addCoupon(ModelMap map, Coupon coupon, HttpServletRequest request) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/addCoupon";
		String coupon_id = null;
		HttpSession session = request.getSession(true);
		CurrUser currUser = (CurrUser) session.getAttribute("currUser");// 登录人角色
		if (null == currUser) {
			bean.setRetCode(5000);
			bean.setRetMsg("登录信息过期，请重新登录！");
			return ret;
		}
		try {
			if (null == coupon.getCoupon_id()) {
				coupon_id = service.addCoupon(coupon, currUser.getUserId());
				bean.setRetMsg("[" + coupon.getCoupon_title() + "]新增成功");
			} else {
				coupon_id = service.modifyCoupon(coupon, currUser.getUserId());
				bean.setRetMsg("[" + coupon.getCoupon_title() + "]保存成功");
			}
			ret = this.queryAllCouponList(map, this.coupon == null ? new Coupon() : this.coupon);
			bean.setRetCode(100);
			bean.setRetValue(coupon_id);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			map.addAttribute("coupon_id", coupon_id);
			map.addAttribute("coupon", coupon);
			logger.error("更新优惠卷信息错误！", e);
		} finally {
			return ret;
		}
	}

	/**
	 * 修改优惠卷信息页面展现
	 * @param map
	 * @param coupon
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyCoupon")
	public String modifyCoupon(ModelMap map, String coupon_id) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/modifyCoupon";
		Coupon coupon = new Coupon();
		try {
			coupon = service.queryCouponByPK(coupon_id);
			if ("1".equals(coupon.getCoupon_type())) {
				coupon.setPreferential_money(coupon.getPreferential_discount());
				coupon.setPreferential_discount("");
			}
			bean.setRetMsg("根据[" + coupon_id + "]查询成功");
			bean.setRetCode(100);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("coupon", coupon);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			ret = this.queryAllCouponList(map, this.coupon == null ? new Coupon() : this.coupon);
			map.addAttribute("ret", bean);
			logger.error("根据"+coupon_id+"查询优惠卷信息出错！", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 删除优惠卷
	 * @param map
	 * @param couponid
	 * @return
	 */
	@RequestMapping("/deleteCoupon")
	public String deleteCoupon(ModelMap map, @RequestParam String coupon_id) {

		PageBean bean = new PageBean();

		String ret = "webpage/poms/coupon/manageCoupon";
		Integer rowcount = null;
		try {
			if (coupon_id != null && !"".equals(coupon_id)) {
				rowcount = service.delCoupon(coupon_id);
			ret = this.queryAllCouponList(map, this.coupon == null ? new Coupon() : this.coupon);
			bean.setRetCode(100);
			bean.setRetMsg("删除成功");
			bean.setRetValue(rowcount.toString());
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			}
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			ret = this.queryAllCouponList(map, this.coupon == null ? new Coupon() : this.coupon);
			map.addAttribute("ret", bean);
			logger.error("根据"+coupon_id+"删除优惠卷错误！", e);
			throw e;
		} finally {
			return ret;
		}
	}
	
	/**
	 * 查询加注站信息
	 * @param map
	 * @return gastationList
	 * @throws Exception
	 */
	@RequestMapping("/gastationList")
	@ResponseBody
	public String selectGastationList(ModelMap map) throws Exception {
		Gastation gastation = new Gastation();
		List<Gastation> list;
		// 加注站状态是开启的
		gastation.setStatus("1");
		try {
			list = gastationService.getAllStationList(gastation);
		} catch (Exception e) {
			logger.error("查询加注站信息出错！", e);
			throw e;
		}
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(list);
		return jsonArray.toString();
	}

	/**
	 * 导入优惠卷页面
	 * 
	 * @param map
	 * @param coupon_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importUserCoupon")
	public String importUserCoupon(ModelMap map, String coupon_id) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/importUserCoupon";
		Coupon coupon = new Coupon();
		try {
			coupon = service.queryCouponByPK(coupon_id);
			if ("1".equals(coupon.getCoupon_type())) {
				coupon.setPreferential_money(coupon.getPreferential_discount());
				coupon.setPreferential_discount("");
			}
			bean.setRetMsg("根据[" + coupon_id + "]查询成功");
			bean.setRetCode(100);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("coupon", coupon);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			ret = this.queryAllCouponList(map, this.coupon == null ? new Coupon() : this.coupon);
			map.addAttribute("ret", bean);
			logger.error("根据[" + coupon_id + "]查询出错！", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 获得优惠卷的人员名单
	 * @param map
	 * @param userCoupon
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showUserCoupon")
	public String showUser(ModelMap map, UserCoupon userCoupon) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/userCouponList";
		try {
			PageInfo<UserCoupon> userCouponInfo = service.queryUserCoupon(userCoupon);
			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("userCouponInfo", userCouponInfo);
			map.addAttribute("userCoupon", userCoupon);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			logger.error("获得优惠卷的人员名单出错！", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * execl 显示导入的数据
	 * @param file
	 * @param coupon_id
	 * @param response
	 * @param request
	 * @return JSONObject
	 * @throws Exception
	 */
	@RequestMapping("/file")
	@ResponseBody
	public JSONObject file(@RequestParam("fileImport") MultipartFile file, String coupon_id, HttpServletRequest request)
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
						UserCoupon userCoupon = new UserCoupon();
						userCoupon.setCoupon_id(coupon_id);
						Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
						Matcher m = p.matcher(sheet.getRow(i)[1].getContents());
						SysDriver sysDriver = new SysDriver();
						sysDriver.setMobilePhone(sheet.getRow(i)[1].getContents());
						sysDriver.setNotin_checked_status("0");
						List<SysDriver> driverInfo = driverService.queryeSingleList(sysDriver);
						if (sheet.getRow(i)[1] == null
								|| "".equals(sheet.getCell(1, i).getContents().replace(" ", ""))) {
							message += "第" + (i + 1) + "行电话号码不能为空！\n";
							err++;
							continue;
						}
						if (!m.matches()) {
							message += "第" + (i + 1) + "行电话号码格式不正确！\n";
							err++;
							continue;
						}
						if (driverInfo.size() == 0) {
							message += "第" + (i + 1) + "行电话号码在系统中不存在！\n";
							err++;
							continue;
						}
						if (!driverInfo.get(0).getFullName().equals(sheet.getRow(i)[0].getContents())) {
							message += "第" + (i + 1) + "行姓名与电话号码在系统中不匹配！\n";
							err++;
							continue;
						}
						SysDriver driver = driverInfo.get(0);
						userCoupon.setUser_id(driver.getSysDriverId());
						driverList.add(driver);
						PageInfo<UserCoupon> userCouponInfo = service.queryUserCoupon(userCoupon);
						if (userCouponInfo.getSize() > 0) {
							Coupon coupon = service.queryCouponByPK(coupon_id);
							info += "第" + i  + "行" + sheet.getRow(i)[0].getContents() + "已有"
									+ coupon.getCoupon_title() + "优惠卷！";
						}
						count++;
					}
				}
				bean.setRetCode(100);
				if(!"".equals(info)){
					info += "\n如需重复获取，请确认保存！";
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
	@RequestMapping("/importCoupon")
	public String importCoupon(ModelMap map, @RequestParam("coupon_id") String coupon_id,
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
		 coupon_id =  new String(coupon_id.getBytes("iso8859-1"),"UTF-8");
		 String[] sysDriverId =  request.getParameterValues("sysDriverId");
		try {
			for(int i=0;i<sysDriverId.length;i++){
				UserCoupon userCoupon = new UserCoupon();
				userCoupon.setCoupon_id(coupon_id);
				userCoupon.setUser_id(sysDriverId[i]);
				 service.addUserCoupon(userCoupon, currUser.getUserId());
			}
			bean.setRetMsg("" + sysDriverId.length + "名司机导入成功");
			ret = this.queryAllCouponList(map, this.coupon == null ? new Coupon() : this.coupon);
			bean.setRetCode(100);
			bean.setRetValue(coupon_id);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			map.addAttribute("coupon_id", coupon_id);
			map.addAttribute("coupon", coupon);
			logger.error("" + sysDriverId.length + "名司机导入失败", e);
		} finally {
			return ret;
		}
	}
	
}
