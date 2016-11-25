package com.sysongy.poms.gastation.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.system.model.SysOperationLog;
import com.sysongy.poms.system.service.SysDepositLogService;
import com.sysongy.poms.system.service.SysOperationLogService;
import com.sysongy.util.DateTimeHelper;
import com.sysongy.util.ExportUtil;
import com.sysongy.util.GlobalConstant;


@RequestMapping("/web/gastation")
@Controller
public class GastationController extends BaseContoller{

	@Autowired
	private GastationService service;
	@Autowired
	private SysDepositLogService depositLogService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OrderService orderService;
	
	private Gastation gastation;
	
	@Autowired
	SysOperationLogService sysOperationLogService;
	
	/**
	 * 加气站查询
	 * @param map
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/gastationList")
	public String queryAllGastationList(ModelMap map, Gastation gastation) throws Exception{
		
		this.gastation = gastation;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list";

		try {
			if(gastation.getPageNum() == null){
				gastation.setPageNum(1);
				gastation.setPageSize(10);
			}
			if(gastation.getConvertPageNum() != null){
				if(gastation.getConvertPageNum() > gastation.getPageNumMax()){
					gastation.setPageNum(gastation.getPageNumMax());
				}else{
					gastation.setPageNum(gastation.getConvertPageNum());
				}
			}
			if(gastation.getPageNumMax() != null && gastation.getPageNum() > gastation.getPageNumMax()){
				gastation.setPageNum(gastation.getPageNumMax());
			}
			if(StringUtils.isEmpty(gastation.getOrderby())){
				gastation.setOrderby("created_time desc");
			}
			
			gastation.setType("0");//联盟气站

			PageInfo<Gastation> pageinfo = service.queryGastation(gastation);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gastation",gastation);
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
	
	@RequestMapping("/gastationList2")
	public String queryAllGastationList2(ModelMap map, Gastation gastation) throws Exception{

		this.gastation = gastation;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list2";

		try {
			if(gastation.getPageNum() == null){
				gastation.setPageNum(1);
				gastation.setPageSize(10);
			}
			if(gastation.getConvertPageNum() != null){
				if(gastation.getConvertPageNum() > gastation.getPageNumMax()){
					gastation.setPageNum(gastation.getPageNumMax());
				}else{
					gastation.setPageNum(gastation.getConvertPageNum());
				}
			}
			if(StringUtils.isEmpty(gastation.getOrderby())){
				gastation.setOrderby("created_time desc");
			}
			
			gastation.setType("0");//联盟气站

			PageInfo<Gastation> pageinfo = service.queryGastation(gastation);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gastation",gastation);
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
	
	@RequestMapping("/depositReport")
    public String queryTransferListReport(ModelMap map, SysDepositLog deposit, HttpServletResponse response, @ModelAttribute CurrUser currUser) throws IOException {
        try {
			deposit.setPageNum(1);
			deposit.setPageSize(1048576);
			
			if(StringUtils.isEmpty(deposit.getOrderby())){
				deposit.setOrderby("optime desc");
			}
			
			if(GlobalConstant.USER_TYPE_STATION == currUser.getUser().getUserType()){
				deposit.setStationId(currUser.getStationId());
			}
			
			deposit.setStation_type(GlobalConstant.OrderOperatorTargetType.GASTATION);
			PageInfo<SysDepositLog> pageinfo = depositLogService.queryDepositLog(deposit);
			List<SysDepositLog> list = pageinfo.getList();

            int cells = 0 ; // 记录条数
            
            if(list != null && list.size() > 0){
                cells += list.size();
            }
            OutputStream os = response.getOutputStream();
            ExportUtil reportExcel = new ExportUtil();
            
            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
            downLoadFileName = "预存款充值_" + downLoadFileName;
           
            try {
                response.addHeader("Content-Disposition","attachment;filename="+ new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));  
            } catch (UnsupportedEncodingException e1) {
                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
            }
            
            String[][] content = new String[cells+1][9];//[行数][列数]
            //第一列
            if(GlobalConstant.USER_TYPE_MANAGE == currUser.getUser().getUserType()){
            	 content[0] = new String[]{"订单号","工作站编号","工作站名称","转账时间","转账方式","操作员","操作时间","预存款金额"};
            }else{
            	 content[0] = new String[]{"订单号","工作站编号","工作站名称","转账时间","充值方式 ","操作员","操作时间","预存款金额"};
            }
           

            int i = 1;
            if(list != null && list.size() > 0){
                for (SysDepositLog station : list) {
                	
                    String orderNumber = station.getOrder_number();
                    String stationid = station.getStationId();
                    String stationname = station.getStationName();
                    String company = station.getCompany();
                    String depositTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(station.getDepositTime());
                    String depositType = station.getDepositType();
                    if(GlobalConstant.USER_TYPE_MANAGE == currUser.getUser().getUserType()){
                    	switch (station.getDepositType()) {
    					case "0":{
    						depositType = "公对公";
    						break;	
    					}
    					case "1":{
    						depositType = "支票";
    						break;
    					}
    					case "2":{
    						depositType = "承兑汇票";
    						break;
    					}
    					case "3":{
    						depositType = "现金";
    						break;
    					}
    					case "4":{
    						depositType = "POS机";
    						break;
    					}
    					default:
    						break;
    					}
                    }else{
                    	depositType = "后台充值";
                    }
                    
                    String operator = station.getOperator();
                    String optime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(station.getOptime());
                    String deposit_ = station.getDeposit().toString();
                   
                    content[i] = new String[]{orderNumber,stationid,stationname,depositTime,depositType,operator,optime,deposit_};
                    i++;
                }
            }

            String [] mergeinfo = new String []{"0,0,0,0"};
            //单元格默认宽度
            String sheetName = "预存款充值";
            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, null, 22, null, 0, null, null, false);

        } catch (Exception e) {
            logger.error("", e);
        }

        return null;
    }
	
	@RequestMapping("/queryGastationInfo")
	public String queryGastationInfo(ModelMap map, @ModelAttribute CurrUser currUser) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_infomation";

		try {
			Gastation gastation = service.queryGastationByPK(currUser.getStationId());

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("gastation",gastation);
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
	
	@RequestMapping("/depositList")
	public String querydepositList(ModelMap map, SysDepositLog deposit, @ModelAttribute CurrUser currUser) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_deposit_log";

		try {
			if(deposit.getPageNum() == null){
				deposit.setPageNum(1);
				deposit.setPageSize(10);
			}
			if(deposit.getConvertPageNum() != null){
				if(deposit.getConvertPageNum() > deposit.getPageNumMax()){
					deposit.setPageNum(deposit.getPageNumMax());
				}else{
					deposit.setPageNum(deposit.getConvertPageNum());
				}
			}
			if(StringUtils.isEmpty(deposit.getOrderby())){
				deposit.setOrderby("optime desc");
			}
			
			if(GlobalConstant.USER_TYPE_STATION == currUser.getUser().getUserType()){
				deposit.setStationId(currUser.getStationId());
			}
			
			deposit.setStation_type(GlobalConstant.OrderOperatorTargetType.GASTATION);
			PageInfo<SysDepositLog> pageinfo = depositLogService.queryDepositLog(deposit);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("deposit",deposit);
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
	/**
	 * 用户卡入库
	 * @param map
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveGastation")
	public String saveGastation(ModelMap map, Gastation gastation, HttpServletRequest request) throws Exception{
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_new";
		String gastationid = null;
		HttpSession session = request.getSession(true);
		CurrUser currUser = (CurrUser) session.getAttribute("currUser");// 登录人角色
		if (null == currUser) {
			bean.setRetCode(5000);
			bean.setRetMsg("登录信息过期，请重新登录！");
			return ret;
		}
		try {
			if(StringUtils.isEmpty(gastation.getSys_gas_station_id())){
				gastationid = service.saveGastation(gastation,"insert");
				//系统关键日志记录
				SysOperationLog sysOperationLog = new SysOperationLog();
				sysOperationLog.setOperation_type("zc");
				sysOperationLog.setLog_platform("4");
	    		sysOperationLog.setLog_content("加注站注册成功！加注站id为："+gastationid); 
				//操作日志
				sysOperationLogService.saveOperationLog(sysOperationLog,currUser.getUserId());
				
				bean.setRetMsg("["+gastationid+"]新增成功");
				ret = "webpage/poms/gastation/gastation_upload";
			}else{
				ret = "webpage/poms/gastation/gastation_update";
				gastationid = service.saveGastation(gastation,"update");
				bean.setRetMsg("["+gastationid+"]保存成功");
				ret = this.queryAllGastationList(map, this.gastation==null?new Gastation():this.gastation);
			}

			bean.setRetCode(100);
			
			bean.setRetValue(gastationid);
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			map.addAttribute("station", gastation);
			
			logger.error("", e);
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/preUpdate")
	public String preUpdate(ModelMap map, @RequestParam String gastationid){
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_update";
		Gastation station = new Gastation();
		
			try {
				if(gastationid != null && !"".equals(gastationid)){
					station = service.queryGastationByPK(gastationid);
				}
		
				bean.setRetCode(100);
				bean.setRetMsg("根据["+gastationid+"]查询Gastation成功");
				bean.setPageInfo(ret);
	
				map.addAttribute("ret", bean);
				map.addAttribute("station", station);
	
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
	
			ret = this.queryAllGastationList(map, this.gastation==null?new Gastation():this.gastation);
	
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

	@RequestMapping("/deleteCard")
	public String deleteCard(ModelMap map, @RequestParam String gastationid){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list";
		Integer rowcount = null;

		try {
				if(gastationid != null && !"".equals(gastationid)){
					rowcount = service.delGastation(gastationid);
				}

				ret = this.queryAllGastationList(map, this.gastation==null?new Gastation():this.gastation);

				bean.setRetCode(100);
				bean.setRetMsg("["+gastationid+"]删除成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllGastationList(map, this.gastation==null?new Gastation():this.gastation);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/resetPassword")
	public String resetPassword(ModelMap map, @RequestParam String gastationid, @RequestParam String username){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list";
		Integer rowcount = null;

		try {
				if(gastationid != null && !"".equals(gastationid)){
					SysUser user = new SysUser();
					user.setStationId(gastationid);
					user.setUserName(username);
					rowcount = sysUserService.updateUserByUserName(user);
				}

				ret = this.queryAllGastationList(map, this.gastation==null?new Gastation():this.gastation);

				bean.setRetCode(100);
				bean.setRetMsg("["+gastationid+"]管理员["+username+"]密码已成功重置为 111111");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllGastationList(map, this.gastation==null?new Gastation():this.gastation);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

	@RequestMapping("/depositGastation")
	public String depositGastation(ModelMap map, SysDepositLog deposit, @ModelAttribute CurrUser currUser){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list";
		Integer rowcount = null;

		try {
				if(deposit.getAccountId() != null && !"".equals(deposit.getAccountId())){
					rowcount = service.updatedepositGastation(deposit, currUser.getUser().getSysUserId());
					//系统关键日志记录
        			SysOperationLog sysOperationLog = new SysOperationLog();
        			sysOperationLog.setOperation_type("cz");
        			sysOperationLog.setLog_platform("4");
            		sysOperationLog.setLog_content("加注站充值成功！充值金额为："+deposit.getDeposit()); 
        			//操作日志
        			sysOperationLogService.saveOperationLog(sysOperationLog,currUser.getUserId());
				}

				ret = this.queryAllGastationList2(map, this.gastation==null?new Gastation():this.gastation);

				bean.setRetCode(100);
				bean.setRetMsg("["+deposit.getStationName()+"]充值成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllGastationList2(map, this.gastation==null?new Gastation():this.gastation);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/queryRechargeReport")
	public String queryRechargeReport(ModelMap map, SysOrder sysOrder, @RequestParam String page, @ModelAttribute CurrUser currUser) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_rechargereport";
		if(StringUtils.isBlank(sysOrder.getStartDate()) || StringUtils.isBlank(sysOrder.getEndDate())){//首次载入页面不加载数据
			return ret;
		}
		try {
			if(sysOrder.getPageNum() == null  || sysOrder.getPageSize()==null){
				sysOrder.setPageNum(1);
				sysOrder.setPageSize(20);
			}
			if(sysOrder.getConvertPageNum() != null){
				if(sysOrder.getConvertPageNum() > sysOrder.getPageNumMax()){
					sysOrder.setPageNum(sysOrder.getPageNumMax());
				}else{
					sysOrder.setPageNum(sysOrder.getConvertPageNum());
				}
			}
 			if(StringUtils.isEmpty(sysOrder.getOrderby())){
				sysOrder.setOrderby("order_date desc");
			}
			if(GlobalConstant.USER_TYPE_STATION == currUser.getUser().getUserType()){
				sysOrder.setChannelNumber(currUser.getStationId());
			}

			PageInfo<Map<String, Object>> pageinfo = orderService.queryGastationRechargeReport(sysOrder);
			PageInfo<Map<String, Object>> total = orderService.queryGastationRechargeReportTotal(sysOrder);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("sysOrder", sysOrder);
			map.addAttribute("page", page);
			map.addAttribute("totalCash",total.getList().get(0)==null?"0":total.getList().get(0).get("total"));
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
	
	@RequestMapping("/reChargeReport")
    public String reChargeReport(ModelMap map, SysOrder sysOrder, HttpServletResponse response, @ModelAttribute CurrUser currUser) throws IOException {
		 try {
	        	sysOrder.setPageNum(1);
	        	sysOrder.setPageSize(1048576);

				if(StringUtils.isEmpty(sysOrder.getOrderby())){
					sysOrder.setOrderby("order_date desc");
				}

				PageInfo<Map<String, Object>> pageinfo = orderService.queryGastationRechargeReport(sysOrder);
				List<Map<String, Object>> list = pageinfo.getList();

	            int cells = 0 ; // 记录条数

	            if(list != null && list.size() > 0){
	                cells += list.size();
	            }
	            OutputStream os = response.getOutputStream();
	            ExportUtil reportExcel = new ExportUtil();

	            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
	            downLoadFileName = "充值明细_" + downLoadFileName;

	            try {
	            	response.addHeader("Content-Disposition","attachment;filename="+ new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
	            } catch (UnsupportedEncodingException e1) {
	                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
	            }

	            String[][] content = new String[cells+1][9];//[行数][列数]
	            //第一列
	            if(GlobalConstant.USER_TYPE_MANAGE == currUser.getUser().getUserType()){
	            	content[0] = new String[]{"订单编号","交易流水号","交易时间","交易类型","订单类型","加注站编号","加注站名称","客户姓名","会员账号","手机号","支付方式","充值金额","返现金额","操作人"};
	            }else{
	            	content[0] = new String[]{"订单编号","交易流水号","交易时间","交易类型","客户姓名","会员账号","支付方式","充值金额","操作人"};
	            }
	            
	            int i = 1;
	            if(list != null && list.size() > 0){
	            	 for (Map<String, Object> tmpMap:pageinfo.getList()) {
	            		 
	            		String order_number = tmpMap.get("order_number")==null?"":tmpMap.get("order_number").toString();
	            		String order_type;
	            		String deal_number = tmpMap.get("deal_number")==null?"":tmpMap.get("deal_number").toString();
	            		String order_date = tmpMap.get("order_date")==null?"":tmpMap.get("order_date").toString();
	            		String is_discharge;
	            		String deal_type;
	            		String channel_number = tmpMap.get("channel_number")==null?"":tmpMap.get("channel_number").toString();
	            		String channel = tmpMap.get("channel")==null?"":tmpMap.get("channel").toString();
	            		String full_name = tmpMap.get("full_name")==null?"":tmpMap.get("full_name").toString();
	            		String user_name = tmpMap.get("user_name")==null?"":tmpMap.get("user_name").toString();
						 String mobile_phone = tmpMap.get("mobile_phone")==null?"":tmpMap.get("mobile_phone").toString();
	            		String charge_type;
	            		String cash = tmpMap.get("cash")==null?"":tmpMap.get("cash").toString();
	            		String cash_back = tmpMap.get("cash_back")==null?"0.00":tmpMap.get("cash_back").toString();
	            		String operator = tmpMap.get("operator")==null?"":tmpMap.get("operator").toString();

	            		switch (tmpMap.get("is_discharge")==null?"":tmpMap.get("is_discharge").toString()) {
						case "0":{
							is_discharge = "消费";
							break;
						}
						case "1":{
							is_discharge = "冲红";
							break;
						}
						default:
							is_discharge = "";
							break;
						}
	            		
	                    switch (tmpMap.get("order_type")==null?"":tmpMap.get("order_type").toString()) {
						case "130":{
							order_type = "个人充值";
							break;
						}
						default:
							order_type = "";
							break;
						}
	                    
	                    switch (tmpMap.get("deal_type")==null?"":tmpMap.get("deal_type").toString()) {
						case "131":{
							deal_type = "个人充值";
							break;
						}
						case "132":{
							deal_type = "首次充值返现";
							break;
						}
						case "133":{
							deal_type = "充值返现";
							break;
						}
						case "134":{
							deal_type = "个人充值冲红";
							break;
						}
						case "135":{
							deal_type = "首次充值返现冲红";
							break;
						}
						case "136":{
							deal_type = "充值返现冲红";
							break;
						}
						case "137":{
							deal_type = "加注站预付款余额扣除";
							break;
						}
						case "138":{
							deal_type = "加注站预付款余额扣除冲红";
							break;
						}
						default:
							deal_type = "";
							break;
						}
	                    
	                    switch (tmpMap.get("charge_type")==null?"":tmpMap.get("charge_type").toString()) {
						case "101":{
							charge_type = "转账返现";
							break;
						}
						case "102":{
							charge_type = "现金充值";
							break;
						}
						case "103":{
							charge_type = "微信充值";
							break;
						}
						case "104":{
							charge_type = "支付宝充值";
							break;
						}
						case "105":{
							charge_type = "银联充值";
							break;
						}
						case "106":{
							charge_type = "充值卡充值";
							break;
						}
						case "107":{
							charge_type = "POS充值";
							break;
						}
						case "108":{
							charge_type = "后台充值";
							break;
						}
						case "201":{
							charge_type = "注册";
							break;
						}
						case "202":{
							charge_type = "首次充值";
							break;
						}
						case "203":{
							charge_type = "邀请";
							break;
						}
						default:
							charge_type = "";
							break;
						}

	    	            if(GlobalConstant.USER_TYPE_MANAGE == currUser.getUser().getUserType()){
	    	            	content[i] = new String[]{order_number,deal_number,order_date,is_discharge,deal_type,channel_number,channel,full_name,user_name,mobile_phone,charge_type,cash,cash_back,operator};
	    	            }else{
	    	            	content[i] = new String[]{order_number,deal_number,order_date,is_discharge,full_name,user_name,mobile_phone,charge_type,cash,operator};
	    	            }
	                    i++;
	                }
	            }

	            String [] mergeinfo = new String []{"0,0,0,0"};
	            //单元格默认宽度
	            String sheetName = "转账明细";
	            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, null, 22, null, 0, null, null, false);

	        } catch (Exception e) {
	            logger.error("", e);
	        }

	        return null;
    }
	
	@RequestMapping("/queryConsumeReport")
	public String queryConsumeReport(ModelMap map, SysOrder sysOrder, @ModelAttribute CurrUser currUser) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_consumereport";
		if(StringUtils.isBlank(sysOrder.getStartDate()) || StringUtils.isBlank(sysOrder.getEndDate())){//首次载入页面不加载数据
			return ret;
		}
		try {
			if(sysOrder.getPageNum() == null || sysOrder.getPageSize()==null){
				sysOrder.setPageNum(1);
				sysOrder.setPageSize(20);
			}
			if(sysOrder.getConvertPageNum() != null){
				if(sysOrder.getConvertPageNum() > sysOrder.getPageNumMax()){
					sysOrder.setPageNum(sysOrder.getPageNumMax());
				}else{
					sysOrder.setPageNum(sysOrder.getConvertPageNum());
				}
			}
			if(StringUtils.isEmpty(sysOrder.getOrderby())){
				sysOrder.setOrderby("order_date desc");
			}
			if(GlobalConstant.USER_TYPE_STATION == currUser.getUser().getUserType()){
				sysOrder.setChannelNumber(currUser.getStationId());
			}

			PageInfo<Map<String, Object>> pageinfo = orderService.queryGastationConsumeReport(sysOrder);
			PageInfo<Map<String, Object>> total = orderService.queryGastationConsumeReportTotal(sysOrder);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("sysOrder", sysOrder);
			map.addAttribute("totalCash",total.getList().get(0)==null?"0":total.getList().get(0).get("total"));
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
    public String consumeReport(ModelMap map, SysOrder sysOrder, HttpServletResponse response, @ModelAttribute CurrUser currUser) throws IOException {
		 try {
	        	sysOrder.setPageNum(1);
	        	sysOrder.setPageSize(1048576);

				if(StringUtils.isEmpty(sysOrder.getOrderby())){
					sysOrder.setOrderby("order_date desc");
				}

				PageInfo<Map<String, Object>> pageinfo = orderService.queryGastationConsumeReport(sysOrder);
				List<Map<String, Object>> list = pageinfo.getList();

	            int cells = 0 ; // 记录条数

	            if(list != null && list.size() > 0){
	                cells += list.size();
	            }
	            OutputStream os = response.getOutputStream();
	            ExportUtil reportExcel = new ExportUtil();

	            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
	            downLoadFileName = "加注站消费_" + downLoadFileName;

	            try {
	            	response.addHeader("Content-Disposition","attachment;filename="+ new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
	            } catch (UnsupportedEncodingException e1) {
	                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
	            }

	            String[][] content = new String[cells+1][9];//[行数][列数]
	            //第一列
	            if(GlobalConstant.USER_TYPE_MANAGE == currUser.getUser().getUserType()){
	            	 content[0] = new String[]{"订单编号","订单类型","交易流水号","交易类型","实收金额","订单金额","支付方式","交易时间","交易对象","加注站名称","加注站编号","会员账号","操作人"};
	            }else{
	            	content[0] = new String[]{"订单编号","交易流水号","交易类型","实收金额","订单金额","支付方式","交易时间","交易对象","会员账号","操作人"};
	            }
	           

	            int i = 1;
	            if(list != null && list.size() > 0){
	            	 for (Map<String, Object> tmpMap:pageinfo.getList()) {
	            		 
	            		String order_number = tmpMap.get("order_number")==null?"":tmpMap.get("order_number").toString();
	            		String order_type;
	            		String deal_number = tmpMap.get("deal_number")==null?"":tmpMap.get("deal_number").toString();
	            		String deal_type;
	            		String cash = tmpMap.get("cash")==null?"0.0":tmpMap.get("cash").toString();
	            		String should_payment = tmpMap.get("should_payment") == null?"0.0":tmpMap.get("should_payment").toString();
	            		String spend_type = tmpMap.get("spend_type") == null?"":tmpMap.get("spend_type").toString();
	            		String order_date = tmpMap.get("order_date")==null?"":tmpMap.get("order_date").toString();
	            		String credit_account = tmpMap.get("creditAccount")==null?"":tmpMap.get("creditAccount").toString();
	            		String channel = tmpMap.get("channel")==null?"":tmpMap.get("channel").toString();
	            		String channel_number = tmpMap.get("channel_number")==null?"":tmpMap.get("channel_number").toString();
	            		String user_name = tmpMap.get("user_name")==null?"":tmpMap.get("user_name").toString();
	            		String operator = tmpMap.get("operator")==null?"":tmpMap.get("operator").toString();
	            		
	            		switch (tmpMap.get("deal_type")==null?"":tmpMap.get("deal_type").toString()) {
						case "211":{
							deal_type = "车队消费";
							break;
						}
						case "212":{
							deal_type = "车队消费冲红";
							break;
						}
						case "221":{
							deal_type = "个人消费";
							break;
						}
						case "222":{
							deal_type = "个人消费冲红";
							break;
						}
						default:
							deal_type = "";
							break;
						}
	            		
	                    switch (tmpMap.get("order_type")==null?"":tmpMap.get("order_type").toString()) {
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
	                    
	                    if(!StringUtils.isEmpty(spend_type)){
	                    	switch (tmpMap.get("spend_type").toString()) {
	    					case "C01":{
	    						spend_type = "卡余额消费";
	    						break;
	    					}
	    					case "C02":{
	    						spend_type = "POS消费";
	    						break;
	    					}
	    					case "C03":{
	    						spend_type = "微信消费";
	    						break;
	    					}
	    					case "C04":{
	    						spend_type = "支付宝消费";
	    						break;
	    					}
	    					default:
	    						spend_type = "";
	    						break;
	    					}
	                    }
	                    
	                    if(credit_account.length() == 32){
	                    	credit_account = "个人";
	                    }else{
	                    	credit_account = "车队";
	                    }
	                    if(GlobalConstant.USER_TYPE_MANAGE == currUser.getUser().getUserType()){
	                    	content[i] = new String[]{order_number,order_type,deal_number,deal_type,cash,should_payment,spend_type,order_date,credit_account,channel,channel_number,user_name,operator};
	                    }else{
	                    	content[i] = new String[]{order_number,deal_number,deal_type,cash,should_payment,spend_type,order_date,credit_account,user_name,operator};
	                    }
	                    
	                    i++;
	                }
	            }

	            String [] mergeinfo = new String []{"0,0,0,0"};
	            //单元格默认宽度
	            String sheetName = "加注站消费";
	            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, null, 22, null, 0, null, null, false);

	        } catch (Exception e) {
	            logger.error("", e);
	        }

	        return null;
    }
	
	@RequestMapping("/queryConsumeReportDetail")
	public String queryConsumeReportDetail(ModelMap map,@RequestParam String order_id,@RequestParam String order_type,@RequestParam String cash) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_consumereportdetail";

		try {
			SysOrder sysOrder = new SysOrder();
			sysOrder.setOrderId(order_id);
			sysOrder.setOrderType(order_type);
			
			if(sysOrder.getPageNum() == null){
				sysOrder.setPageNum(1);
				sysOrder.setPageSize(10);
			}
			if(sysOrder.getConvertPageNum() != null){
				if(sysOrder.getConvertPageNum() > sysOrder.getPageNumMax()){
					sysOrder.setPageNum(sysOrder.getPageNumMax());
				}else{
					sysOrder.setPageNum(sysOrder.getConvertPageNum());
				}
			}
			if(StringUtils.isEmpty(sysOrder.getOrderby())){
				sysOrder.setOrderby("order_date desc");
			}

			PageInfo<Map<String, Object>> pageinfo = orderService.queryGastationConsumeReportDetail(sysOrder);

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
	
	@RequestMapping("/gastationConsumeReport")
	public String gastationConsumeReport(ModelMap map, SysOrder order, @ModelAttribute CurrUser currUser) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_consumecollectreport";
		if(StringUtils.isBlank(order.getStartDate()) || StringUtils.isBlank(order.getEndDate())){//首次载入页面不加载数据
			return ret;
		}
		try {
			if(order.getPageNum() == null || order.getPageSize()==null){
				order.setPageNum(1);
				order.setPageSize(20);
			}
			if(order.getConvertPageNum() != null){
				if(order.getConvertPageNum() > order.getPageNumMax()){
					order.setPageNum(order.getPageNumMax());
				}else{
					order.setPageNum(order.getConvertPageNum());
				}
			}
			if(StringUtils.isEmpty(order.getOrderby())){
				order.setOrderby("sys_gas_station_id desc");
			}
			if(GlobalConstant.USER_TYPE_STATION == currUser.getUser().getUserType()){
				order.setChannelNumber(currUser.getStationId());
			}

			PageInfo<Map<String, Object>> pageinfo = service.gastionConsumeReport(order);
			PageInfo<Map<String, Object>> total = service.gastionConsumeReportTotal(order);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("order", order);	
			map.addAttribute("totalCash",total.getList().get(0)==null?"0":total.getList().get(0).get("total"));
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

	@RequestMapping("/gastationConsumeReport/import")
	public String transportionConsumeReportImport(ModelMap map, SysOrder order, HttpServletResponse response, @ModelAttribute CurrUser currUser) throws IOException {
		 try {
			 	order.setPageNum(1);
			 	order.setPageSize(1048576);

				if(StringUtils.isEmpty(order.getOrderby())){
					order.setOrderby("sys_gas_station_id desc");
				}
				if(GlobalConstant.USER_TYPE_STATION == currUser.getUser().getUserType()){
					order.setChannelNumber(currUser.getStationId());
				}

				PageInfo<Map<String, Object>> pageinfo = service.gastionConsumeReport(order);
				List<Map<String, Object>> list = pageinfo.getList();

	            int cells = 0 ; // 记录条数

	            if(list != null && list.size() > 0){
	                cells += list.size();
	            }
	            OutputStream os = response.getOutputStream();
	            ExportUtil reportExcel = new ExportUtil();

	            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
	            downLoadFileName = "加注站消费汇总_" + downLoadFileName;

	            try {
	            	response.addHeader("Content-Disposition","attachment;filename="+ new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
	            } catch (UnsupportedEncodingException e1) {
	                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
	            }

	            String[][] content = new String[cells+1][9];//[行数][列数]
	            //第一列
	            content[0] = new String[]{"加注站编号","加注站名称","运管人员","销售人员","消费金额(包含冲红)","冲红金额","消费量","消费次数"};

	            int i = 1;
	            if(list != null && list.size() > 0){
	            	 for (Map<String, Object> tmpMap:pageinfo.getList()) {
	            		 
	            		String sys_gas_station_id = tmpMap.get("sys_gas_station_id")==null?"":tmpMap.get("sys_gas_station_id").toString();
	            		String gas_station_name = tmpMap.get("gas_station_name")==null?"":tmpMap.get("gas_station_name").toString();
	            		String cash = tmpMap.get("cash")==null?"":tmpMap.get("cash").toString();
	            		String hedgefund = tmpMap.get("hedgefund")==null?"":tmpMap.get("hedgefund").toString();
	            		String salesmen_name = tmpMap.get("salesmen_name")==null?"":tmpMap.get("salesmen_name").toString();
	            		String operations_name = tmpMap.get("operations_name")==null?"":tmpMap.get("operations_name").toString();
	            		String summit = tmpMap.get("summit")==null?"":tmpMap.get("summit").toString();
	            		String consumecount = tmpMap.get("consumecount")==null?"":tmpMap.get("consumecount").toString();
	            		
	                    content[i] = new String[]{sys_gas_station_id,gas_station_name,operations_name,salesmen_name,cash,hedgefund,summit,consumecount};
	                    i++;
	                }
	            }

	            String [] mergeinfo = new String []{"0,0,0,0"};
	            //单元格默认宽度
	            String sheetName = "加注站消费汇总";
	            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, null, 22, null, 0, null, null, false);

	        } catch (Exception e) {
	            logger.error("", e);
	        }

	        return null;
   }
	
	@RequestMapping("/gastationRechargeReport")
	public String gastationRechargeReport(ModelMap map, SysOrder order, @ModelAttribute CurrUser currUser) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_rechargecollectreport";
		if(StringUtils.isBlank(order.getStartDate()) || StringUtils.isBlank(order.getEndDate())){//首次载入页面不加载数据
			return ret;
		}
		try {
			if(order.getPageNum() == null || order.getPageSize() == null){
				order.setPageNum(1);
				order.setPageSize(20);
			}
			if(order.getConvertPageNum() != null){
				if(order.getConvertPageNum() > order.getPageNumMax()){
					order.setPageNum(order.getPageNumMax());
				}else{
					order.setPageNum(order.getConvertPageNum());
				}
			}
			if(StringUtils.isEmpty(order.getOrderby())){
				order.setOrderby("sys_gas_station_id desc");
			}
			if(GlobalConstant.USER_TYPE_STATION == currUser.getUser().getUserType()){
				order.setChannelNumber(currUser.getStationId());
			}

			PageInfo<Map<String, Object>> pageinfo = service.gastionRechargeReport(order);
			PageInfo<Map<String, Object>> total = service.gastionRechargeReportTotal(order);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("order", order);
			map.addAttribute("totalCash",total.getList().get(0)==null?"0":total.getList().get(0).get("total"));
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

	@RequestMapping("/gastationRechargeReport/import")
	public String gastationRechargeReportImport(ModelMap map, SysOrder order, HttpServletResponse response, @ModelAttribute CurrUser currUser) throws IOException {
		 try {
			 	order.setPageNum(1);
			 	order.setPageSize(1048576);

				if(StringUtils.isEmpty(order.getOrderby())){
					order.setOrderby("sys_gas_station_id desc");
				}
				if(GlobalConstant.USER_TYPE_STATION == currUser.getUser().getUserType()){
					order.setChannelNumber(currUser.getStationId());
				}

				PageInfo<Map<String, Object>> pageinfo = service.gastionRechargeReport(order);
				List<Map<String, Object>> list = pageinfo.getList();

	            int cells = 0 ; // 记录条数

	            if(list != null && list.size() > 0){
	                cells += list.size();
	            }
	            OutputStream os = response.getOutputStream();
	            ExportUtil reportExcel = new ExportUtil();

	            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
	            downLoadFileName = "加注站充值汇总_" + downLoadFileName;

	            try {
	            	response.addHeader("Content-Disposition","attachment;filename="+ new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
	            } catch (UnsupportedEncodingException e1) {
	                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
	            }

	            String[][] content = new String[cells+1][9];//[行数][列数]
	            //第一列
	            content[0] = new String[]{"加注站编号","加注站名称","充值金额(包含冲红)","冲红金额","运管人员","销售人员"};

	            int i = 1;
	            if(list != null && list.size() > 0){
	            	 for (Map<String, Object> tmpMap:pageinfo.getList()) {
	            		 
	            		String sys_gas_station_id = tmpMap.get("sys_gas_station_id")==null?"":tmpMap.get("sys_gas_station_id").toString();
	            		String gas_station_name = tmpMap.get("gas_station_name")==null?"":tmpMap.get("gas_station_name").toString();
	            		String cash = tmpMap.get("cash")==null?"":tmpMap.get("cash").toString();
	            		String hedgefund = tmpMap.get("hedgefund")==null?"":tmpMap.get("hedgefund").toString();
	            		String salesmen_name = tmpMap.get("salesmen_name")==null?"":tmpMap.get("salesmen_name").toString();
	            		String operations_name = tmpMap.get("operations_name")==null?"":tmpMap.get("operations_name").toString();

	                    content[i] = new String[]{sys_gas_station_id,gas_station_name,cash,hedgefund,operations_name,salesmen_name};
	                    i++;
	                }
	            }

	            String [] mergeinfo = new String []{"0,0,0,0"};
	            //单元格默认宽度
	            String sheetName = "加注站充值汇总";
	            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, null, 22, null, 0, null, null, false);

	        } catch (Exception e) {
	            logger.error("", e);
	        }

	        return null;
   }
	
}
