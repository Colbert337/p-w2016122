package com.sysongy.poms.mobile.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.util.httpClient.HttpResponse;
import com.github.pagehelper.PageInfo;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.util.DESUtil;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.message.service.SysMessageService;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.model.SysRoadConditionStr;
import com.sysongy.poms.mobile.service.SysRoadService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.DateTimeHelper;
import com.sysongy.util.DateUtil;
import com.sysongy.util.ExportUtil;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.RedisClientInterface;
import com.sysongy.util.UUIDGenerator;

import net.sf.json.JSONObject;

/**
 * @FileName: SysRoadController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.mobile.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年09月08日, 17:18
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */

@RequestMapping("/web/mobile/road")
@Controller
public class SysRoadController extends BaseContoller {
	@Autowired
	SysRoadService sysRoadService;

	@Autowired
	private UsysparamService usysparamservice;

	@Autowired
	RedisClientInterface redisClientImpl;

	@Autowired
	SysMessageService messageService;

	@RequestMapping("/roadList")
	public String roadList(SysRoadCondition road, ModelMap map, String type) {
		String ret = "webpage/poms/mobile/roadList";

		PageBean bean = new PageBean();
		road.setPageSize(20);
		try {
			if (road.getPageNum() == null || "".equals(road.getPageNum())) {
				road.setPageNum(GlobalConstant.PAGE_NUM);
				road.setPageSize(20);
			}
			if(road.getConvertPageNum() != null){
				if(road.getConvertPageNum() > road.getPageNumMax()){
					road.setPageNum(road.getPageNumMax());
				}else{
					road.setPageNum(road.getConvertPageNum());
				}
			}
			if (StringUtils.isEmpty(road.getOrderby())) {
				road.setOrderby("start_time desc");
			}

			PageInfo<SysRoadCondition> pageinfo = new PageInfo<SysRoadCondition>();
			pageinfo = sysRoadService.queryRoadList(road);
			bean.setRetCode(100);
			if (type != null && !"".equals(type)) {
				bean.setRetMsg(msg);
			} else {
				bean.setRetMsg("查询成功");
			}
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("road", road);
			// map.addAttribute("current_module",
			// "/web/mobile/suggest/suggestList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}

	}

	@RequestMapping("/roadListStr")
	public String roadListStr(SysRoadCondition road, ModelMap map, String type) {
		String ret = "webpage/poms/mobile/roadListStr";

		PageBean bean = new PageBean();
		try {
			int pageNum = 1;
			if (road.getPageNum() == null) {
				road.setPageNum(GlobalConstant.PAGE_NUM);
				road.setPageSize(5);
			}
			if(road.getConvertPageNum() != null){
				if(road.getConvertPageNum() > road.getPageNumMax()){
					road.setPageNum(road.getPageNumMax());
				}else{
					road.setPageNum(road.getConvertPageNum());
				}
			}
			if (StringUtils.isEmpty(road.getOrderby())) {
				road.setOrderby(" publisher_time desc");
			}
			pageNum = road.getPageNum();
			PageInfo<SysRoadConditionStr> pageinfo = new PageInfo<SysRoadConditionStr>();
			road = sysRoadService.selectByPrimaryKey(road.getId());
			if (road == null) {
				throw new Exception("路况不存在");
			}
			road.setPageSize(5);
			road.setPageNum(pageNum);
			pageinfo = sysRoadService.queryRoadListStr(road);
			bean.setRetCode(100);
			if (type != null && !"".equals(type)) {
				bean.setRetMsg(msg);
			} else {
				bean.setRetMsg("查询成功");
			}
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("road", road);
			// map.addAttribute("current_module",
			// "/web/mobile/suggest/suggestList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}

	}

	private String msg = "";

	@RequestMapping("/saveRoad")
	public String saveRoad(SysRoadCondition road, ModelMap map, HttpSession session) {
		String ret = "redirect:/web/mobile/road/roadList?type=add";
		PageBean bean = new PageBean();
		msg = "";
		try {
			if (road.getPageNum() == null) {
				road.setPageNum(GlobalConstant.PAGE_NUM);
				road.setPageSize(GlobalConstant.PAGE_SIZE);
			}
			String xy[] = road.getLongitude().split(",");
			if (xy.length == 2) {
				road.setLongitude(xy[0]);
				road.setLatitude(xy[1]);
			} else {
				throw new Exception("坐标格式有误，请输入经纬度并用“,”隔开");

			}
			if (!"".equals(road.getCaptureLongitude()) && null != road.getCaptureLongitude()) {
				xy = road.getCaptureLongitude().split(",");
				if (xy.length == 2) {
					road.setCaptureLongitude(xy[0]);
					road.setCaptureLatitude(xy[1]);
				} else {
					throw new Exception("拍照坐标格式有误，请输入经纬度并用“,”隔开");

				}
			}
			road.setStartTime(stringToDate(road.getStartTime_str()));
			road.setEndTime(stringToDate(road.getEndTime_str()));
			road.setCaptureTime(stringToDate(road.getCaptureTime_str()));
			CurrUser user = (CurrUser) session.getAttribute("currUser");
			road.setAuditor(user.getUser().getUserName());
			road.setAuditorPhone(user.getUser().getMobilePhone());
			road.setAuditorTime(new Date());
			road.setId(UUIDGenerator.getUUID());
			road.setPublisherTime(new Date());
			road.setPublisherName(user.getUser().getUserName());
			road.setConditionStatus("2");
			road.setPublisherPhone(user.getUser().getMobilePhone());
			Usysparam param= usysparamservice.queryUsysparamByCode("CONDITION_TYPE", road.getConditionType());
			int time = sumTime(road,Integer.valueOf(param.getData()));
			int a = sysRoadService.saveRoad(road);
			bean.setRetCode(100);
			bean.setRetMsg("保存成功");
			msg = "保存成功";
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("road", road);
			map.addAttribute("suggest", road);
			// 保存readis

			if (time == -1 || time > 0) {
				road.setUsefulCount("0");
				redisClientImpl.addToCache("Road" + road.getId(), road, time);
			}

			// map.addAttribute("current_module",
			// "/web/mobile/suggest/suggestList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			msg = "保存错误："+e.getMessage();
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}

	}

	private Date stringToDate(String time) throws ParseException {
		if (!"".equals(time)) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			return sdf.parse(time);
		}
		return null;
	}
/**
 * 考虑通用性，不修改原有算法，传入参数需要路况对象和失效时间
 * @param road 路况对象
 * @param m 失效时间(分钟)
 * @return
 */
	public static int sumTime(SysRoadCondition road,Integer m) {
		// TODO Auto-generated method stub
		
		if (m==0||null==m) {
			return -1;
		}
		
//		switch (road.getConditionType()) {
//		case "01":
//			h = 1;
//			break;
//		case "02":
//			h = 2;
//			break;
//		case "05":
//			h = 4;
//			break;
//		default:
//			break;
//		}

		if (m != 0) {

			long a = road.getStartTime().getTime() - new Date().getTime();
			int time = m * 60 + (int) a / 1000;
			road.setEndTime(new Date(new Date().getTime() + m* 60 * 1000 + a));
			return time;
		} else if (road.getEndTime() != null) {
			long a = road.getAuditorTime().getTime() - road.getEndTime().getTime();
			int time = (int) a / 1000;
			return time;
		} else {
			return -1;
		}
	}

	@RequestMapping("/queryListforRedis")
	@ResponseBody
	public String queryListforRedis(String params) {
		MobileReturn result = new MobileReturn();
		result.setStatus(MobileReturn.STATUS_SUCCESS);
		result.setMsg("获取路况成功！");
		JSONObject resutObj = new JSONObject();
		String keyStr = "sysongys";
		String resultStr = "";
		List<SysRoadCondition> list = sysRoadService.queryRoadIDList();
		List<SysRoadCondition> redis = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			SysRoadCondition one = (SysRoadCondition) redisClientImpl.getFromCache("Road" + list.get(i).getId());
			if (one != null) {
				redis.add(one);
			}

		}
		try {
			/**
			 * 解析参数
			 */
			params = DESUtil.decode("sysongys", params);
			JSONObject paramsObj = JSONObject.fromObject(params);
			JSONObject mainObj = paramsObj.optJSONObject("main");
			/**
			 * 请求接口
			 */
			if (mainObj != null) {
				int pageNum = mainObj.optInt("pageNum");
				int pageSize = mainObj.optInt("pageSize");
				SysRoadCondition roadCondition = new SysRoadCondition();
				roadCondition.setPageNum(pageNum);
				roadCondition.setPageSize(pageSize);
				roadCondition.setLongitude(mainObj.optString("longitude"));
				roadCondition.setLatitude(mainObj.optString("latitude"));
				roadCondition.setProvince(mainObj.optString("province"));
				roadCondition.setConditionType(mainObj.optString("conditionType"));
				List<Map<String, Object>> reChargeList = new ArrayList<>();
				Map<String, Object> reCharge = new HashMap<>();
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if (redis != null && redis.size() > 0) {
					for (SysRoadCondition one : redis) {
						Map<String, Object> reChargeMap = new HashMap<>();
						reChargeMap.put("conditionType", one.getConditionType());
						reChargeMap.put("longitude", one.getLongitude());
						reChargeMap.put("latitude", one.getLatitude());
						reChargeMap.put("conditionImg", one.getConditionImg());
						reChargeMap.put("address", one.getAddress());
						reChargeMap.put("publisherName", one.getPublisherName());
						reChargeMap.put("publisherPhone", one.getPublisherPhone());
						reChargeMap.put("contentUrl", "");
						String publisherTime = "";
						if (one.getPublisherTime() != null && !"".equals(one.getPublisherTime().toString())) {
							publisherTime = sft.format(new Date());
						}
						reChargeMap.put("publisherTime", publisherTime);
						reChargeList.add(reChargeMap);
					}
				} else {
					reCharge.put("listMap", new ArrayList<>());
				}
				result.setListMap(reChargeList);
			} else {
				result.setStatus(MobileReturn.STATUS_FAIL);
				result.setMsg("参数有误！");
			}
			resutObj = JSONObject.fromObject(result);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
			logger.error("获取路况成功： " + resultStr);
		} catch (Exception e) {
			result.setStatus(MobileReturn.STATUS_FAIL);
			result.setMsg("获取路况失败！");
			resutObj = JSONObject.fromObject(result);
			logger.error("获取路况失败： " + e);
			resutObj.remove("data");
			resultStr = resutObj.toString();
			resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
			// return resultStr;
		} finally {
			return resultStr;
		}

	}

	@RequestMapping("/updateRoad")
	public String updateRoad(SysRoadCondition road, ModelMap map, HttpSession session, String content) {
		String ret = "redirect:/web/mobile/road/roadList?type=update";
		PageBean bean = new PageBean();
		msg = "";
		// road.setConditionStatus(status);
		try {
			if (road.getPageNum() == null) {
				road.setPageNum(GlobalConstant.PAGE_NUM);
				road.setPageSize(GlobalConstant.PAGE_SIZE);
			}
			CurrUser user = (CurrUser) session.getAttribute("currUser");
			if ("0".equals(road.getConditionStatus())) {
				msg = "失效成功";
				redisClientImpl.deleteFromCache("Road" + road.getId());
			} else {
				road.setAuditor(user.getUser().getUserName());
				road.setAuditorPhone(user.getUser().getMobilePhone());
				road.setAuditorTime(new Date());
			}
			int a = sysRoadService.updateRoad(road,user.getUser().getSysUserId());

			bean.setRetCode(100);
			bean.setRetMsg("审核成功");
			// msg = "审核成功";
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);

			map.addAttribute("road", road);
			map.addAttribute("suggest", road);
			road = sysRoadService.selectByPrimaryKey(road.getId());
			if ("2".equals(road.getConditionStatus())) {
				// 放到redis
				msg = "审核成功";
				
				Usysparam param= usysparamservice.queryUsysparamByCode("CONDITION_TYPE", road.getConditionType());
				int time = sumTime(road,Integer.valueOf(param.getData()));
				if (time == -1 || time > 0) {
					road.setUsefulCount("0");
					redisClientImpl.addToCache("Road" + road.getId(), road, time);
				}
				messageService.saveMessage_New_Road("您上报的路况信息已审核通过，感谢您的贡献！", road);
			}
			if ("3".equals(road.getConditionStatus())) {
				msg = "审核成功";
				messageService.saveMessage_New_Road(content, road);
			}
			bean.setRetCode(100);
			bean.setRetMsg("审核成功");
			// msg = "审核成功";
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);

			// map.addAttribute("current_module",
			// "/web/mobile/suggest/suggestList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			msg = "审核错误";
			map.addAttribute("ret", bean);
			logger.error("", e);
		} finally {
			return ret;
		}

	}

	@RequestMapping("/delete")
	public String delete(SysRoadCondition road, ModelMap map) {
		String ret = "redirect:/web/mobile/road/roadList?type=delete";
		PageBean bean = new PageBean();
		try {

			sysRoadService.deleteRoad(road);
			redisClientImpl.deleteFromCache(road.getId());
			bean.setRetCode(100);
			bean.setRetMsg("删除成功");
			msg = "删除成功";
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("road", road);
			map.addAttribute("suggest", road);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			msg = "删除错误";
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;// TODO: handle finally clause
		}
	}

	@RequestMapping("/seachInvalid")
	public String seachInvalid() {
		String ret = "redirect:/web/mobile/road/roadList?type=delete";
		PageBean bean = new PageBean();
		return ret;
	}
	@RequestMapping("/restoreRedis")	
	@ResponseBody
	public String restoreRedis( ModelMap map){
		PageBean bean = new PageBean();
		if (sysRoadService.restoreRedis(redisClientImpl)) {
			bean.setRetMsg("恢复成功");
		}else{
			bean.setRetMsg("恢复失败");
		}
		return bean.getRetMsg();
	}
	@RequestMapping("/outExcel")	
	public String outExcel(ModelMap map, SysRoadCondition road, HttpServletResponse response) {
		PageBean bean = new PageBean();
		try {
			/*
			 * if(StringUtils.isNotBlank(gascard.getStorage_time_after()) ||
			 * StringUtils.isNotBlank(gascard.getStorage_time_before())
			 * ||StringUtils.isNotBlank(gascard.getCard_property())||StringUtils
			 * .isNotBlank(gascard.getCard_status())||
			 * StringUtils.isNotBlank(gascard.getOperator())) {
			 */
			road.setPageNum(1);
			road.setPageSize(1048576);
			// }

			if (StringUtils.isEmpty(road.getOrderby())) {
				road.setOrderby("order_date desc");
			}

			PageInfo<SysRoadCondition> pageinfo = sysRoadService.queryForExcel(road);
			List<SysRoadCondition> list = pageinfo.getList();

			int cells = 0; // 记录条数

			if (list != null && list.size() > 0) {
				cells += list.size();
			}
			OutputStream os = response.getOutputStream();
			ExportUtil reportExcel = new ExportUtil();

			String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),
					DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
			downLoadFileName = "路况信息_" + downLoadFileName;

			try {
				response.addHeader("Content-Disposition",
						"attachment;filename=" + new String(downLoadFileName.getBytes("GB2312"), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				response.setHeader("Content-Disposition", "attachment;filename=" + downLoadFileName);
			}

			String[][] content = new String[cells + 1][9];// [行数][列数]
			// 第一列
			content[0] = new String[] { "审核人电话", "审核人", "路况类型", "审核状态", "拍照时间", "路况说明", "备注", "创建人", "创建人电话",
					"发布时间","审核时间" };
			int i = 1;
			if (list != null && list.size() > 0) {
				for (SysRoadCondition one : pageinfo.getList()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
					String publisherPhone = one.getPublisherPhone() == null ? "" : one.getPublisherPhone().toString();
					String publisherName = one.getPublisherName() == null ? "" : one.getPublisherName().toString();
					String condition_type = one.getConditionType() == null ? "" : one.getConditionType();
					String captureTime = one.getCaptureTime() == null ? "" : sdf.format(one.getCaptureTime());
					String workstation = "";
					String condStatus = "";
					switch (one.getConditionStatus()) {
					case "0":
						condStatus = "已失效";
						break;
					case "1":
						condStatus = "待审核";
						break;
					case "2":
						condStatus = "审核通过";
						break;
					case "3":
						condStatus = "未通过";
						break;
					default:
						condStatus = "";
						break;
					}
					String mname=one.getMname();
					String msg = one.getConditionMsg(); 
					String memo = one.getMemo() == null ? ""
							: one.getMemo().toString();
					String auditor = one.getAuditor() == null ? "" : one.getAuditor();
					String auditorPhone = one.getAuditorPhone() == null ? "" : one.getAuditorPhone();
					String auditorTime = one.getAuditorTime() == null ? "" : DateUtil.format(one.getAuditorTime());
					String release_time = one.getPublisherTime() == null ? "" : DateUtil.format(one.getPublisherTime());
//				content[0] = new String[] { "审核人电话", "审核人", "路况类型", "审核状态", "拍照时间", "路况说明", "备注", "创建人", "创界人电话",
//					"发布时间","审核时间" };
					content[i] = new String[] { auditorPhone, auditor, mname, condStatus, captureTime,
							msg, memo, publisherName, publisherPhone, release_time,auditorTime };
					i++;
				}
			}

			String[] mergeinfo = new String[] { "0,0,0,0" };
			// 单元格默认宽度
			String sheetName = "用户卡信息报表";
			reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, null, 22, null, 0, null, null, false);

		} catch (Exception e) {
			map.addAttribute("ret", bean);
			logger.error("", e);
		}
		return null;

	}
}
