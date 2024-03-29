package com.sysongy.poms.coupon.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.coupon.model.UserCoupon;
import com.sysongy.poms.coupon.service.CouponService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;

import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;



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
			if(coupon.getConvertPageNum() != null){
				if(coupon.getConvertPageNum() > coupon.getPageNumMax()){
					coupon.setPageNum(coupon.getPageNumMax());
				}else if(coupon.getConvertPageNum() < 1){
					coupon.setPageNum(1);
				}else{
					coupon.setPageNum(coupon.getConvertPageNum());
				}
			}
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
				bean.setRetMsg("新增成功");
			} else {
				coupon_id = service.modifyCoupon(coupon, currUser.getUserId());
				bean.setRetMsg("保存成功");
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
		List<Gastation> gastationList = new ArrayList<Gastation>();
		// 加注站状态是开启的
		gastation.setStatus("1");
		 gastation.setType("0");//联盟气站
		try {
			gastationList = gastationService.queryGastationForCoupon(gastation);
		} catch (Exception e) {
			logger.error("查询加注站信息出错！", e);
			throw e;
		}
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(gastationList);
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
						if(sheet.getRow(i)[1].getContents()!=null&&!"".equals(sheet.getRow(i)[1].getContents())){
						UserCoupon userCoupon = new UserCoupon();
						userCoupon.setCoupon_id(coupon_id);
//						Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
//						Matcher m = p.matcher(sheet.getRow(i)[1].getContents());
						SysDriver sysDriver = new SysDriver();
						sysDriver.setMobilePhone(sheet.getRow(i)[1].getContents());
//						sysDriver.setNotin_checked_status("0");
						List<SysDriver> driverInfo = driverService.queryeSingleList(sysDriver);
						if (sheet.getRow(i)[1] == null
								|| "".equals(sheet.getCell(1, i).getContents().replace(" ", ""))) {
							message += "第" + (i + 1) + "行电话号码不能为空！<br/>\r\n";
							err++;
							continue;
						}
//						if (!m.matches()) {
//							message += "第" + (i + 1) + "行电话号码格式不正确！\n";
//							err++;
//							continue;
//						}
						if (driverInfo.size() == 0) {
							message += "第" + (i + 1) + "行电话号码在系统中不存在！<br/>\r\n";
							err++;
							continue;
						}
						/*if (!driverInfo.get(0).getFullName().equals(sheet.getRow(i)[0].getContents())) {
							message += "第" + (i + 1) + "行姓名与电话号码在系统中不匹配！\n";
							err++;
							continue;
						}*/
						SysDriver driver = driverInfo.get(0);
						userCoupon.setSys_driver_id(driver.getSysDriverId());
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
				}
				bean.setRetCode(100);
				if(!"".equals(info)){
					info += "\r\n如需重复获取，请确认保存！";
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
			@RequestParam("coupon_no") String coupon_no,@RequestParam("start_coupon_time") String start_coupon_time,
			@RequestParam("end_coupon_time") String end_coupon_time,@RequestParam("coupon_kind") String coupon_kind,
			 @RequestParam("sys_gas_station_id") String sys_gas_station_id,
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
		 coupon_no =  new String(coupon_no.getBytes("iso8859-1"),"UTF-8");
		 start_coupon_time =  new String(start_coupon_time.getBytes("iso8859-1"),"UTF-8");
		 end_coupon_time =  new String(end_coupon_time.getBytes("iso8859-1"),"UTF-8");
		 coupon_kind =  new String(coupon_kind.getBytes("iso8859-1"),"UTF-8");
		 sys_gas_station_id =  new String(sys_gas_station_id.getBytes("iso8859-1"),"UTF-8");
		 String[] sysDriverId =  request.getParameter("sysDriverIds").split(",");
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			for(int i=0;i<sysDriverId.length;i++){
				UserCoupon userCoupon = new UserCoupon();
				userCoupon.setCoupon_id(coupon_id);
				userCoupon.setCoupon_no(coupon_no);
				userCoupon.setCoupon_kind(coupon_kind);
				userCoupon.setSys_gas_station_id(sys_gas_station_id);
				userCoupon.setStart_coupon_time(sdf.parse(start_coupon_time));
				userCoupon.setEnd_coupon_time(sdf.parse(end_coupon_time));
				userCoupon.setSys_driver_id(sysDriverId[i]);
				//获得优惠卷
				userCoupon.setIsuse("0");
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
	
	
	/**
	 * 查看优惠人员信息
	 * @param map
	 * @param userCoupon
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/couponUserList")
	public String queryCouponUserList(ModelMap map, UserCoupon userCoupon) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/coupon/manageCouponUser";
		try {
			if(userCoupon.getConvertPageNum() != null){
				if(userCoupon.getConvertPageNum() > userCoupon.getPageNumMax()){
					userCoupon.setPageNum(userCoupon.getPageNumMax());
				}else if(userCoupon.getConvertPageNum() < 1){
					userCoupon.setPageNum(1);
				}else{
					userCoupon.setPageNum(userCoupon.getConvertPageNum());
				}
			}
			PageInfo<UserCoupon> userCouponInfo = service.queryUserCoupon(userCoupon);
			bean.setRetCode(100);
			bean.setRetMsg("查询成功"); 
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", userCouponInfo);
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
	
}
