package com.sysongy.poms.driver.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.model.SysDriverReviewStr;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.util.DateTimeHelper;
import com.sysongy.util.Encoder;
import com.sysongy.util.ExportUtil;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.RedisClientInterface;
import com.sysongy.util.UUIDGenerator;

import net.sf.json.JSONObject;

/**
 * @FileName: DriverController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.driver
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月20日, 9:14
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */

@RequestMapping("/web/driver")
@Controller
public class DriverController extends BaseContoller{

    @Autowired
    DriverService driverService;
	@Autowired
	RedisClientInterface redisClientImpl;
	@Autowired
	SysUserAccountService sysUserAccountService;
	@Autowired
	TransportionService transportionService;
	@Autowired
	OrderService orderService;

	SysDriver driver;
	/**
     * 查询司机列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryDriverListPage(@ModelAttribute CurrUser currUser, SysDriver driver, @RequestParam(required = false) Integer resultInt, ModelMap map){
		String stationId = currUser.getStationId();
		if(driver.getPageNum() == null){
            driver.setPageNum(GlobalConstant.PAGE_NUM);
            driver.setPageSize(GlobalConstant.PAGE_SIZE);
        }
		driver.setStationId(stationId);

        //封装分页参数，用于查询分页内容
        PageInfo<SysDriver> driverPageInfo = new PageInfo<SysDriver>();
        try {
            driverPageInfo = driverService.querySearchDriverList(driver);
        }catch (Exception e){
            e.printStackTrace();
        }

        map.addAttribute("driverList",driverPageInfo.getList());
        map.addAttribute("pageInfo",driverPageInfo);
		map.addAttribute("driver",driver);

		if(resultInt != null && resultInt > 0){
			Map<String, Object> resultMap = new HashMap<>();

			if(resultInt == 1){
				resultMap.put("retMsg","新建司机成功！");
			}else if(resultInt == 2){
				resultMap.put("retMsg","修改司机成功！");
			}
			map.addAttribute("ret",resultMap);
		}

        return "webpage/tcms/driver/driver_list";
    }

    @RequestMapping("/driverListStr")
    public String queryDriverReviewStr(@ModelAttribute CurrUser currUser, SysDriverReviewStr driver, ModelMap map){
		String stationId = currUser.getStationId();
		if(driver.getPageNum() == null){
            driver.setPageNum(GlobalConstant.PAGE_NUM);
            driver.setPageSize(GlobalConstant.PAGE_SIZE);
        }
		driver.setStationId(stationId);

        //封装分页参数，用于查询分页内容
        PageInfo<SysDriverReviewStr> driverPageInfo = new PageInfo<SysDriverReviewStr>();
        
        if(StringUtils.isEmpty(driver.getOrderby())){
        	driver.setOrderby("checked_date desc");
        }
        
        try {
            driverPageInfo = driverService.queryDriversLog(driver);
        }catch (Exception e){
            e.printStackTrace();
        }

        map.addAttribute("driverList",driverPageInfo.getList());
        map.addAttribute("pageInfo",driverPageInfo);
		map.addAttribute("driver",driver);

        return "webpage/poms/system/driver_review_log";
    }

	/**
	 * 添加司机
	 * @param currUser 当前用户
	 * @param driver 司机
	 * @param map
     * @return
     */
	@RequestMapping("/save")
	public String saveDriver(@ModelAttribute("currUser") CurrUser currUser, SysDriver driver, ModelMap map) throws Exception{
		int userType = currUser.getUser().getUserType();
		int result = 0;
		int resultInt = 0;

		String stationId = currUser.getStationId();
		String operation = "insert";
		String payCode = driver.getPayCode();

		driver.setUserName(driver.getMobilePhone());
		driver.setPassword(Encoder.MD5Encode("111111".getBytes()));
		driver.setUserStatus("0");//0 使用中 1 已冻结
		driver.setCheckedStatus("0");//审核状态 0 新注册 1 待审核 2 已通过 3 未通过
		driver.setStationId(stationId);//站点编号
		Transportion transportion = transportionService.queryTransportionByPK(stationId);
		driver.setRegisSource(transportion.getTransportion_name());//司机注册来源（运输公司名称）

		/*String newid;
		SysDriver driverTemp = driverService.queryMaxIndex();
		if(driverTemp == null || StringUtils.isEmpty(driverTemp.getSysDriverId())){
			newid = "P" + "000000001";
		}else{
			Integer tmp = Integer.valueOf(driverTemp.getSysDriverId().substring(1, 10)) + 1;
			newid = "P" + StringUtils.leftPad(tmp.toString() , 9, "0");
		}*/

		driver.setSysDriverId(UUIDGenerator.getUUID());
		driver.setPayCode(Encoder.MD5Encode(payCode.getBytes()));

		try {
			result = driverService.saveDriver(driver,operation);
			resultInt = 1;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "redirect:/web/driver/list/page?resultInt="+resultInt;
	}

    @RequestMapping("/driverList")
    public String queryDriverList(SysDriver driver, ModelMap map)throws Exception{
    	
    	PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_review";
		this.driver = driver;
		
		try {
        PageInfo<SysDriver> pageinfo = new PageInfo<SysDriver>();
        
        if(StringUtils.isEmpty(driver.getOrderby())){
        	driver.setOrderby("created_date desc");
        }
        
        driver.setNotin_checked_status("0");

        pageinfo = driverService.queryDrivers(driver);
        
        bean.setRetCode(100);
		bean.setRetMsg("查询成功");
		bean.setPageInfo(ret);
		
		map.addAttribute("ret", bean);
		map.addAttribute("pageInfo", pageinfo);
		map.addAttribute("driver",driver);

		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
    }

    @RequestMapping("/driverInfoList")
    public String queryDriverInfoList(SysDriver driver, ModelMap map)throws Exception{
    	this.driver = driver;
    	PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_info";

		try {
	        PageInfo<SysDriver> pageinfo = new PageInfo<SysDriver>();

	        if(StringUtils.isEmpty(driver.getOrderby())){
	        	driver.setOrderby("created_date desc");
	        }

	        pageinfo = driverService.queryDrivers(driver);

	        bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("driver",driver);
			map.addAttribute("current_module", "webpage/poms/system/driver_info");

		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
    }

    @RequestMapping("/changeDriverStatus")
    public String changeDriverStatus(@RequestParam String accountid, @RequestParam String status, @RequestParam String cardno, ModelMap map)throws Exception{
    	PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_info";

		try {
			sysUserAccountService.changeStatus(accountid, status, cardno);

			SysDriver driver = new SysDriver();
			driver.setSysUserAccountId(accountid);
			
			ret = this.queryDriverInfoList(this.driver ==null?new SysDriver():this.driver, map);

			bean.setRetCode(100);
			bean.setRetMsg("状态修改成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
    }

    @RequestMapping("/review")
	public String review(ModelMap map, @RequestParam String driverid,@RequestParam String type,@RequestParam String memo, @ModelAttribute("currUser") CurrUser currUser){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_review";
		Integer rowcount = null;

		try {
				if(driverid != null && !"".equals(driverid)){
					rowcount = driverService.updateAndReview(driverid, type, memo, currUser.getUser().getUserName());
				}

				ret = this.queryDriverList(this.driver ==null?new SysDriver():this.driver, map);

				bean.setRetCode(100);
				bean.setRetMsg("已审核");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryDriverList(this.driver ==null?new SysDriver():this.driver, map);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
	/**
	 * 司机离职
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteDriverByIds(HttpServletRequest request,ModelMap map){
		SysDriver driver = new SysDriver();
		String[] ids = request.getParameterValues("pks");
		List<String> idList = new ArrayList<>();
		if(ids != null && ids.length > 0){
			for (int i = 0;i < ids.length;i++) {
				idList.add(ids[i]);
			}
		}

		driverService.deleteDriverByIds(idList);
		return "redirect:/web/driver/list/page";
	}

	@RequestMapping("/info/isExist")
	@ResponseBody
	public JSONObject queryRoleList(HttpServletRequest request, @RequestParam String mobilePhone, ModelMap map){
		JSONObject json = new JSONObject();
		SysDriver sysDriver = new SysDriver();
		sysDriver.setMobilePhone(mobilePhone);
		try {
			SysDriver driver = driverService.queryDriverByMobilePhone(sysDriver);
			if (driver != null){
				json.put("valid",false);
			}else{
				json.put("valid",true);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/queryRechargeDriverReport")
	public String queryRechargeDriverReport(ModelMap map, SysOrder sysOrder) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_rechargereport";

		try {
			if(sysOrder.getPageNum() == null){
				sysOrder.setPageNum(1);
				sysOrder.setPageSize(10);
			}
			if(StringUtils.isEmpty(sysOrder.getOrderby())){
				//transportion.setOrderby("created_time desc");
			}

			PageInfo<Map<String, Object>> pageinfo = orderService.queryRechargeDriverReport(sysOrder);
			PageInfo<Map<String, Object>> total = orderService.queryRechargeDriverReportTotal(sysOrder);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("sysOrder", sysOrder);
			map.addAttribute("totalCash",total.getList().get(0).get("total"));
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/queryRechargeReportDetail")
	public String queryRechargeReportDetail(ModelMap map,@RequestParam String order_id,@RequestParam String order_type,@RequestParam String cash) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_rechargereportdetail";

		try {
			SysOrder sysOrder = new SysOrder();
			sysOrder.setOrderId(order_id);
			sysOrder.setOrderType(order_type);
			
			if(sysOrder.getPageNum() == null){
				sysOrder.setPageNum(1);
				sysOrder.setPageSize(10);
			}
			if(StringUtils.isEmpty(sysOrder.getOrderby())){
				//transportion.setOrderby("created_time desc");
			}

			PageInfo<Map<String, Object>> pageinfo = orderService.queryRechargeDriverReportDetail(sysOrder);
			//PageInfo<Map<String, Object>> total = orderService.queryRechargeReportTotal(sysOrder);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("sysOrder", sysOrder);
			map.addAttribute("totalCash",cash);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/consumeReport")
    public String queryConsumeReport(ModelMap map, SysOrder sysOrder, HttpServletResponse response, @ModelAttribute CurrUser currUser) throws IOException {
        try {
        	sysOrder.setPageNum(1);
        	sysOrder.setPageSize(1048576);

			if(StringUtils.isEmpty(sysOrder.getOrderby())){
				sysOrder.setOrderby("order_date desc");
			}

			PageInfo<Map<String, Object>> pageinfo = orderService.queryRechargeDriverReport(sysOrder);
			List<Map<String, Object>> list = pageinfo.getList();

            int cells = 0 ; // 记录条数

            if(list != null && list.size() > 0){
                cells += list.size();
            }
            OutputStream os = response.getOutputStream();
            ExportUtil reportExcel = new ExportUtil();

            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
            downLoadFileName = "充值_" + downLoadFileName;

            try {
                response.setHeader("Content-Disposition","attachment;filename=" + java.net.URLEncoder.encode(downLoadFileName, "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
            }

            String[][] content = new String[cells+1][9];//[行数][列数]
            //第一列
            content[0] = new String[]{"订单号","订单类型","交易流水号","交易时间","交易类型","交易金额","会员账号","加注站编号","加注站名称","管联运输公司","备注","操作人"};

            int i = 1;
            if(list != null && list.size() > 0){
            	 for (Map<String, Object> tmpMap:pageinfo.getList()) {
            		 
            		String order_number = tmpMap.get("order_number").toString();
            		String order_type;
            		String deal_number = tmpMap.get("deal_number").toString();
            		String order_date = tmpMap.get("order_date").toString();
            		String is_discharge = tmpMap.get("is_discharge")==null?"":tmpMap.get("is_discharge").toString()=="0"?"冲红":"消费";
            		String cash = tmpMap.get("cash").toString();
            		String user_name = tmpMap.get("user_name")==null?"":tmpMap.get("user_name").toString();
            		String channel = tmpMap.get("channel")==null?"":tmpMap.get("channel").toString();
            		String channel_number = tmpMap.get("channel_number")==null?"":tmpMap.get("channel_number").toString();
            		String transportion_name = tmpMap.get("transportion_name")==null?"":tmpMap.get("transportion_name").toString();
            		String remark = tmpMap.get("remark").toString();
            		String operator = tmpMap.get("operator").toString();


                    switch (tmpMap.get("order_type").toString()) {
					case "210":{
						order_type = "运输公司消费";
						break;
					}
					case "220":{
						order_type = "司机消费";
						break;
					}
					default:
						order_type = "";
						break;
					}


                    content[i] = new String[]{order_number,order_type,deal_number,order_date,is_discharge,cash,user_name,channel,channel_number,transportion_name,remark,operator};
                    i++;
                }
            }

            String [] mergeinfo = new String []{"0,0,0,0"};
            //单元格默认宽度
            String sheetName = "消费报表";
            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, null, 22, null, 0, null, null, false);

        } catch (Exception e) {
            logger.error("", e);
        }

        return null;
    }
}
