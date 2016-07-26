package com.sysongy.poms.transportion.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.api.client.controller.model.PayCodeValidModel;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.system.service.SysDepositLogService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.service.TcVehicleService;
import com.sysongy.util.*;
import com.sysongy.util.mail.MailEngine;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletResponse;


@RequestMapping("/web/transportion")
@Controller
public class TransportionController extends BaseContoller{

	@Autowired
	private TransportionService service;
	@Autowired
	private SysDepositLogService depositLogService;
	@Autowired
	private MailEngine mailEngine;
	@Autowired
	private SimpleMailMessage mailMessage;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
    TcVehicleService tcVehicleService;
	@Autowired
	private OrderDealService orderDealService;
	@Autowired
	TransportionService transportionService;
	@Autowired
	OrderService orderService;
	@Autowired
	RedisClientInterface redisClientImpl;

	private Transportion transportion;

	/**
	 * 运输公司查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transportionList")
	public String queryAllTransportionList(ModelMap map, Transportion transportion) throws Exception{
		
		this.transportion = transportion;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_list";

		try {
			if(transportion.getPageNum() == null){
				transportion.setPageNum(1);
				transportion.setPageSize(10);
			}
			if(StringUtils.isEmpty(transportion.getOrderby())){
				transportion.setOrderby("created_time desc");
			}

			if(!StringUtils.isEmpty(transportion.getExpiry_date_frompage())){
				String []tmpRange = transportion.getExpiry_date_frompage().split("-");
				if(tmpRange.length==2){
					transportion.setExpiry_date_after(tmpRange[0].trim()+" 00:00:00");
					transportion.setExpiry_date_before(tmpRange[1]+" 23:59:59");
				}
			}

			PageInfo<Transportion> pageinfo = service.queryTransportion(transportion);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("transportion",transportion);
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

	@RequestMapping("/transportionList2")
	public String queryAllTransportionList2(ModelMap map, Transportion transportion) throws Exception{
		
		this.transportion = transportion;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_list2";

		try {
			if(transportion.getPageNum() == null){
				transportion.setPageNum(1);
				transportion.setPageSize(10);
			}
			if(StringUtils.isEmpty(transportion.getOrderby())){
				transportion.setOrderby("created_time desc");
			}

			if(!StringUtils.isEmpty(transportion.getExpiry_date_frompage())){
				String []tmpRange = transportion.getExpiry_date_frompage().split("-");
				if(tmpRange.length==2){
					transportion.setExpiry_date_after(tmpRange[0].trim()+" 00:00:00");
					transportion.setExpiry_date_before(tmpRange[1]+" 23:59:59");
				}
			}

			PageInfo<Transportion> pageinfo = service.queryTransportion(transportion);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("transportion",transportion);
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
	
	@RequestMapping("/queryTransportionReport")
	public String queryTransportionReport(ModelMap map, SysOrder sysOrder) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_report";

		try {
			if(sysOrder.getPageNum() == null){
				sysOrder.setPageNum(1);
				sysOrder.setPageSize(10);
			}
			if(StringUtils.isEmpty(sysOrder.getOrderby())){
				//transportion.setOrderby("created_time desc");
			}

			PageInfo<Map<String, Object>> pageinfo = orderService.queryTransportionReport(sysOrder);
			PageInfo<Map<String, Object>> total = orderService.queryTransportionReportTotal(sysOrder);

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
	
	@RequestMapping("/depositTransportionReport")
    public String depositTransportionReport(ModelMap map, SysOrder sysOrder, HttpServletResponse response, @ModelAttribute CurrUser currUser) throws IOException {
		 try {
	        	sysOrder.setPageNum(1);
	        	sysOrder.setPageSize(1048576);

				if(StringUtils.isEmpty(sysOrder.getOrderby())){
					sysOrder.setOrderby("order_date desc");
				}

				PageInfo<Map<String, Object>> pageinfo = orderService.queryTransportionReport(sysOrder);
				List<Map<String, Object>> list = pageinfo.getList();

	            int cells = 0 ; // 记录条数

	            if(list != null && list.size() > 0){
	                cells += list.size();
	            }
	            OutputStream os = response.getOutputStream();
	            ExportUtil reportExcel = new ExportUtil();

	            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
	            downLoadFileName = "转账明细_" + downLoadFileName;

	            try {
	                response.setHeader("Content-Disposition","attachment;filename=" + java.net.URLEncoder.encode(downLoadFileName, "UTF-8"));
	            } catch (UnsupportedEncodingException e1) {
	                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
	            }

	            String[][] content = new String[cells+1][9];//[行数][列数]
	            //第一列
	            content[0] = new String[]{"订单编号","订单类型","交易流水号","交易时间","交易类型","运输公司名称","运输公司编号","个人账号","交易金额","操作人"};

	            int i = 1;
	            if(list != null && list.size() > 0){
	            	 for (Map<String, Object> tmpMap:pageinfo.getList()) {
	            		 
	            		String order_number = tmpMap.get("order_number")==null?"":tmpMap.get("order_number").toString();
	            		String order_type;
	            		String deal_number = tmpMap.get("deal_number")==null?"":tmpMap.get("deal_number").toString();
	            		String order_date = tmpMap.get("order_date").toString();
	            		String deal_type;
	            		String transportion_name = tmpMap.get("transportion_name")==null?"":tmpMap.get("transportion_name").toString();
	            		String creditAccount = tmpMap.get("creditAccount").toString();
	            		String debit_account = tmpMap.get("debitAccount")==null?"":tmpMap.get("debitAccount").toString();
	            		String cash = tmpMap.get("cash").toString();
	            		String operator = tmpMap.get("operator").toString();


	                    switch (tmpMap.get("order_type").toString()) {
						case "310":{
							order_type = "运输公司对个人转账";
							break;
						}
						case "320":{
							order_type = "个人对个人转账";
							break;
						}
						default:
							order_type = "";
							break;
						}
	                    
	                    switch (tmpMap.get("deal_type")==null?"":tmpMap.get("deal_type").toString()) {
						case "311":{
							deal_type = "运输公司转出";
							break;
						}
						case "312":{
							deal_type = "运输公司转出";
							break;
						}
						case "313":{
							deal_type = "运输公司转出返现";
							break;
						}
						default:
							deal_type = "";
							break;
						}


	                    content[i] = new String[]{order_number,order_type,deal_number,order_date,deal_type,transportion_name,creditAccount,debit_account,cash,operator};
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
	
	@RequestMapping("/queryRechargeReport")
	public String queryRechargeReport(ModelMap map, SysOrder sysOrder) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_rechargereport";

		try {
			if(sysOrder.getPageNum() == null){
				sysOrder.setPageNum(1);
				sysOrder.setPageSize(10);
			}
			if(StringUtils.isEmpty(sysOrder.getOrderby())){
				//transportion.setOrderby("created_time desc");
			}

			PageInfo<Map<String, Object>> pageinfo = orderService.queryRechargeReport(sysOrder);
			PageInfo<Map<String, Object>> total = orderService.queryRechargeReportTotal(sysOrder);

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
		String ret = "webpage/poms/transportion/transportion_rechargereportdetail";

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

			PageInfo<Map<String, Object>> pageinfo = orderService.queryRechargeReportDetail(sysOrder);
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

	/**
	 * 用户卡入库
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveTransportion")
	public String saveTransportion(ModelMap map, Transportion transportion) throws Exception{
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_new";
		String transportionid = null;

		try {
			if(StringUtils.isEmpty(transportion.getSys_transportion_id())){
				transportionid = service.saveTransportion(transportion,"insert");
				bean.setRetMsg("["+transportionid+"]新增成功");
				ret = "webpage/poms/transportion/transportion_upload";
			}else{
				ret = "webpage/poms/transportion/transportion_update";
				transportionid = service.saveTransportion(transportion,"update");
				bean.setRetMsg("["+transportionid+"]保存成功");
				ret = this.queryAllTransportionList(map, this.transportion==null?new Transportion():this.transportion);
			}

			bean.setRetCode(100);
			bean.setRetValue(transportionid);
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
			return  ret;
		}
	}

	@RequestMapping("/preUpdate")
	public String preUpdate(ModelMap map, @RequestParam String transportionid){
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_update";
		Transportion station = new Transportion();

			try {
				if(transportionid != null && !"".equals(transportionid)){
					station = service.queryTransportionByPK(transportionid);
				}

				bean.setRetCode(100);
				bean.setRetMsg("根据["+transportionid+"]查询Transportion成功");
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);
				map.addAttribute("station", station);

		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllTransportionList(map, this.transportion==null?new Transportion():this.transportion);

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

			if(GlobalConstant.USER_TYPE_STATION == currUser.getUserType()){
				deposit.setStationId(currUser.getStationId());
			}

			deposit.setStation_type(GlobalConstant.OrderOperatorTargetType.TRANSPORTION);
			PageInfo<SysDepositLog> pageinfo = depositLogService.queryDepositLog(deposit);
			List<SysDepositLog> list = pageinfo.getList();

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
            content[0] = new String[]{"订单号","工作站编号","工作站名称","所属公司","转账时间","转账方式","操作员","操作时间","充值金额"};

            int i = 1;
            if(list != null && list.size() > 0){
                for (SysDepositLog station : list) {

                    String orderNumber = station.getOrder_number();
                    String stationid = station.getStationId();
                    String stationname = station.getStationName();
                    String company = station.getCompany();
                    String depositTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(station.getDepositTime());
                    String depositType = station.getDepositType();
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
                    String operator = station.getOperator();
                    String optime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(station.getOptime());
                    String deposit_ = station.getDeposit().toString();

                    content[i] = new String[]{orderNumber,stationid,stationname,company,depositTime,depositType,operator,optime,deposit_};
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
	
	@RequestMapping("/consumeReport")
    public String queryConsumeReport(ModelMap map, SysOrder sysOrder, HttpServletResponse response, @ModelAttribute CurrUser currUser) throws IOException {
        try {
        	sysOrder.setPageNum(1);
        	sysOrder.setPageSize(1048576);

			if(StringUtils.isEmpty(sysOrder.getOrderby())){
				sysOrder.setOrderby("order_date desc");
			}

			PageInfo<Map<String, Object>> pageinfo = orderService.queryRechargeReport(sysOrder);
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
            content[0] = new String[]{"订单号","订单类型","交易流水号","交易时间","交易类型","交易金额","运输公司编号","加注站名称","车牌号","备注","操作人"};

            int i = 1;
            if(list != null && list.size() > 0){
            	 for (Map<String, Object> tmpMap:pageinfo.getList()) {
            		 
            		String order_number = tmpMap.get("order_number").toString();
            		String order_type;
            		String deal_number = tmpMap.get("deal_number").toString();
            		String order_date = tmpMap.get("order_date").toString();
            		String is_discharge = tmpMap.get("is_discharge") == null?"":tmpMap.get("is_discharge").toString()=="0"?"冲红":"消费";
            		String cash = tmpMap.get("cash").toString();
            		String credit_account = tmpMap.get("creditAccount") == null?"":tmpMap.get("creditAccount").toString();
            		String channel = tmpMap.get("channel") == null?"":tmpMap.get("channel").toString();
            		String plates_number = tmpMap.get("plates_number").toString();
            		String remark = tmpMap.get("remark").toString();
            		String user_name = tmpMap.get("user_name").toString();


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


                    content[i] = new String[]{order_number,order_type,deal_number,order_date,is_discharge,cash,credit_account,channel,plates_number,remark,user_name};
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

	/**
	 * 根据主键查询运输公司信息
	 * @param map
	 * @param currUser
     * @return
     */
	@RequestMapping("/info")
	public String queryTransportInfo(ModelMap map, @ModelAttribute CurrUser currUser){

		String transportionId = currUser.getStationId();
		Transportion transportion = new Transportion();
		try {
			transportion = service.queryTransportionByPK(transportionId);
		}catch (Exception e){
			e.printStackTrace();
		}

		map.addAttribute("transportion",transportion);
		return "webpage/poms/transportion/transport_info";
	}

	@RequestMapping("/info/tc")
	@ResponseBody
	public Transportion queryTransport(ModelMap map, @ModelAttribute CurrUser currUser){

		String transportionId = currUser.getStationId();
		Transportion transportion = new Transportion();
		try {
			transportion = service.queryTransportionByPK(transportionId);
		}catch (Exception e){
			e.printStackTrace();
		}

		return transportion;
	}

	/**
	 * 判断支付密码是否正确
	 * @param map
	 * @param currUser
     * @return
     */
	@RequestMapping("/info/password")
	@ResponseBody
	public JSONObject queryPasswordIsRight(Transportion transport, @ModelAttribute CurrUser currUser, ModelMap map){
		JSONObject json = new JSONObject();

		String transportionId = currUser.getStationId();
		Transportion transportion = new Transportion();
		try {
			transportion = service.queryTransportionByPK(transportionId);

			if(transportion != null && transport != null){
				String password = transportion.getPay_code();
				if(password.equals(Encoder.MD5Encode(transport.getPay_code().getBytes()))){
					json.put("valid",true);
				}else{
					json.put("valid",false);
				}
			}

		}catch (Exception e){
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping("/depositList")
	public String querydepositList(ModelMap map, SysDepositLog deposit) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_deposit_log";

		try {
			if(deposit.getPageNum() == null){
				deposit.setPageNum(1);
				deposit.setPageSize(10);
			}
			if(StringUtils.isEmpty(deposit.getOrderby())){
				deposit.setOrderby("optime desc");
			}

			deposit.setStation_type(GlobalConstant.OrderOperatorTargetType.TRANSPORTION);
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

	@RequestMapping("/deposiTransportion")
	public String deposiTransportion(ModelMap map, SysDepositLog deposit, @ModelAttribute CurrUser currUser){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_list2";
		Integer rowcount = null;

		try {
				if(deposit.getAccountId() != null && !"".equals(deposit.getAccountId())){
					rowcount = service.updatedeposiTransportion(deposit, currUser.getUser().getSysUserId());
				}

				ret = this.queryAllTransportionList2(map, this.transportion==null?new Transportion():this.transportion);

				bean.setRetCode(100);
				bean.setRetMsg("["+deposit.getStationName()+"]充值成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllTransportionList2(map, this.transportion==null?new Transportion():this.transportion);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

	/**
	 * 添加运输公司支付密码
	 * @param currUser 当前用户
	 * @param map
	 * @return
	 */
	@RequestMapping("/save/password")
	public String saveTcPassword(@ModelAttribute("currUser") CurrUser currUser,Transportion transportion, ModelMap map){
		String stationId = currUser.getStationId();
		transportion.setSys_transportion_id(stationId);
		try {
			service.updatedeposiTransport(transportion);
		}catch (Exception e){
			e.printStackTrace();
		}

		return "redirect:/web/tcms/fleetQuota/list/page";
	}

	/**
	 * 发送设置密码邮件
	 * @param currUser
	 * @param transportion
	 * @param map
     * @return
     */
	@RequestMapping("/update/setPasswordMail")
	public String sendSetPassword(@ModelAttribute("currUser") CurrUser currUser,Transportion transportion, ModelMap map){
		String userName = currUser.getUser().getUserName();
		String stationId = currUser.getStationId();
		Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
		String http_poms_path = (String) prop.get("http_poms_path");
		int resultInt = 7;
		try {
			transportion = service.queryTransportionByPK(stationId);
			String email = transportion.getEmail();
			String url = http_poms_path;
			mailMessage.setTo(email);
			mailMessage.setSubject("用户设置密码邮件通知");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName",userName);
			model.put("url",url+"/msg/info/setPassword");
			mailEngine.send(mailMessage, "password.ftl", model);
			resultInt = 8;
		}catch (Exception e){
			resultInt = 7;
			e.printStackTrace();
		}
		return "redirect:/web/tcms/fleetQuota/list/page?resultInt="+resultInt;
	}

	/**
	 * 进入设置密码页面
	 * @param currUser
	 * @param transportion
	 * @param map
     * @return
     */
	@RequestMapping("/info/setPassword")
	public String querySetPassword(@ModelAttribute("currUser") CurrUser currUser,Transportion transportion, ModelMap map){
		if(currUser == null){
			return "redirect:/";
		}else{
			String userName = currUser.getUser().getUserName();
			String stationId = currUser.getStationId();

			map.addAttribute("userName",userName);
			map.addAttribute("stationId",stationId);

			return "webpage/poms/transportion/ps_set";
		}
	}

	/**
	 * 保存邮件设置的密码
	 * @param currUser
	 * @param transportion
	 * @param map
	 * @return
	 */
	@RequestMapping("/save/payCode")
	public String saveMailPayCode(@ModelAttribute("currUser") CurrUser currUser, Transportion transportion,
								   SessionStatus sessionStatus, ModelMap map) throws Exception{
		String userName = currUser.getUser().getUserName();
		String stationId = currUser.getStationId();

		Transportion transport = new Transportion();
		transport.setSys_transportion_id(stationId);
		String password = transportion.getPay_code();
		password = Encoder.MD5Encode(password.getBytes());
		transport.setPay_code(password);

		service.updatedeposiTransport(transport);
		sessionStatus.setComplete();
		return "redirect:/";
	}
	/**
	 * 发送修改密码邮件
	 * @param currUser
	 * @param transportion
	 * @param map
	 * @return
	 */
	@RequestMapping("/update/passwordMail")
	public String sendUpdatePassword(@ModelAttribute("currUser") CurrUser currUser,Transportion transportion, ModelMap map){

		String userName = currUser.getUser().getUserName();
		String stationId = currUser.getStationId();
		Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
		String http_poms_path = (String) prop.get("http_poms_path");
		int resultInt = 7;
		try {
			transportion = service.queryTransportionByPK(stationId);
			String email = transportion.getEmail();
			String url = http_poms_path;

			mailMessage.setTo(email);
			mailMessage.setSubject("用户修改密码邮件通知");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName",userName);
			model.put("url",url+"/web/transportion/info/setPassword");
			mailEngine.send(mailMessage, "password.ftl", model);
			resultInt = 8;
		}catch (Exception e){
			resultInt = 7;
			e.printStackTrace();
		}
		return "redirect:/web/tcms/fleetQuota/list/page?resultInt="+resultInt;
	}

	/**
	 * 进入修改密码页面
	 * @param currUser
	 * @param transportion
	 * @param map
	 * @return
	 */
	@RequestMapping("/info/updatePassword")
	public String queryUpdatePassword(@ModelAttribute("currUser") CurrUser currUser,Transportion transportion, ModelMap map){
		String userName = currUser.getUser().getUserName();
		String stationId = currUser.getStationId();

		try {
			transportion = service.queryTransportionByPK(stationId);

		}catch (Exception e){
			e.printStackTrace();
		}
		return "redirect:/web/tcms/fleetQuota/list/page";
	}

	/**
	 * 更新运输公司密码
	 * @param currUser
	 * @param transportion
	 * @param map
     * @return
     */
	@RequestMapping("/update/password")
	public String updatePassword(@ModelAttribute("currUser") CurrUser currUser,Transportion transportion, ModelMap map){
		String userName = currUser.getUser().getUserName();
		String stationId = currUser.getStationId();
		transportion.setSys_transportion_id(stationId);
		int resultInt = 5;
		try {
			transportion.setPay_code(Encoder.MD5Encode(transportion.getPay_code().getBytes()));
			service.updatedeposiTransport(transportion);
			resultInt = 6;

		}catch (Exception e){
			e.printStackTrace();
			resultInt = 5;
		}
		return "redirect:/web/tcms/fleetQuota/list/page?resultInt="+resultInt;
	}

	/**
	 * 判断密码是否正确
	 * @param currUser
	 * @param password
	 * @param map
     * @return
     */
	@RequestMapping("/info/isExist")
	@ResponseBody
	public JSONObject queryRoleList(@ModelAttribute("currUser") CurrUser currUser, @RequestParam String password, ModelMap map){
		JSONObject json = new JSONObject();
		String userName = currUser.getUser().getUserName();
		String stationId = currUser.getStationId();
		Transportion transportion = new Transportion();
		String passwordTemp = "";
		try {
			transportion = service.queryTransportionByPK(stationId);
			password = Encoder.MD5Encode(password.getBytes());

			if (transportion != null && transportion.getPay_code().equals(password)){
				json.put("valid",true);
			}else{
				json.put("valid",false);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/resetPassword")
	public String resetPassword(ModelMap map, @RequestParam String gastationid, @RequestParam String username){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_list";
		Integer rowcount = null;

		try {
				if(gastationid != null && !"".equals(gastationid)){
					SysUser user = new SysUser();
					user.setStationId(gastationid);
					user.setUserName(username);
					rowcount = sysUserService.updateUserByUserName(user);
				}

				ret = this.queryAllTransportionList(map, this.transportion==null?new Transportion():this.transportion);

				bean.setRetCode(100);
				bean.setRetMsg("["+gastationid+"]管理员["+username+"]密码已成功重置为 111111");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllTransportionList(map, this.transportion==null?new Transportion():this.transportion);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}



	@RequestMapping("/Vehiclelist")
    public String queryVehiclelist(@ModelAttribute CurrUser currUser, TcVehicle vehicle, ModelMap map){
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/vehicle_list";

		try {
				String stationId = currUser.getStationId();
		        if(vehicle.getPageNum() == null){
		            vehicle.setPageNum(GlobalConstant.PAGE_NUM);
		            vehicle.setPageSize(GlobalConstant.PAGE_SIZE);
		        }
		        if(StringUtils.isEmpty(vehicle.getOrderby())){
		        	vehicle.setOrderby("created_date desc");
				}
		        vehicle.setStationId(stationId);

				PageInfo<TcVehicle> pageinfo = tcVehicleService.queryVehicleList(vehicle);


				List<TcVehicle> tcVehicles = pageinfo.getList();
				List<TcVehicle> tcVehicleNews = new ArrayList<TcVehicle>();
				for(TcVehicle tcVehicleInfo : tcVehicles){
					PayCodeValidModel payCodeValidModel = (PayCodeValidModel)
							redisClientImpl.getFromCache(tcVehicleInfo.getTcVehicleId());
					if((payCodeValidModel != null) && (payCodeValidModel.getErrTimes() == 4)){
						tcVehicleInfo.setIsLocked(1);
					}
					tcVehicleNews.add(tcVehicleInfo);
				}
				pageinfo.setList(tcVehicleNews);

				bean.setRetCode(100);
				bean.setRetMsg("查询成功");
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);
				map.addAttribute("pageInfo", pageinfo);
				map.addAttribute("vehicle",vehicle);

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

	@RequestMapping("/unLockDriver")
	public String unLockDriver(@RequestParam String tcVehicleId, ModelMap map)throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/vehicle_list";
		try {
			PayCodeValidModel payCodeValidModel = (PayCodeValidModel) redisClientImpl.getFromCache(tcVehicleId);
			if (payCodeValidModel != null) {
				redisClientImpl.deleteFromCache(tcVehicleId);
			}
			TcVehicle tcVehicle = new TcVehicle();
			tcVehicle.setTcVehicleId(tcVehicleId);
			//ret = this.queryDriverInfoList(this.driver ==null?new SysDriver():this.driver, map);
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
		} finally {
			return ret;
		}
	}
	/**
	 * 运输公司充值报表（单个运输公司）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/recharge")
	public String queryRechargeList(@ModelAttribute CurrUser currUser, ModelMap map, SysOrder order) throws Exception{
		String stationId = currUser.getStationId();
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_recharge_log";

		try {
			if(order.getPageNum() == null){
				order.setOrderby("deal_date desc");
				order.setPageNum(1);
				order.setPageSize(10);
			}
			order.setDebitAccount(stationId);
			order.setCash(new BigDecimal(BigInteger.ZERO));
			PageInfo<Map<String, Object>> pageinfo = orderDealService.queryRechargeList(order);

			List<Map<String, Object>> list = orderDealService.queryRechargeListCount(order);
			BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
			if(list != null && list.size() > 0){

				for (Map<String, Object> quotaMap:list) {
					if(quotaMap.get("cash") != null && !"".equals(quotaMap.get("cash").toString())){
						totalCash = totalCash.add(new BigDecimal(quotaMap.get("cash").toString()));
					}
				}
			}
			//累计总划款金额
			map.addAttribute("totalCash",totalCash);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("order",order);
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
	 * 运输公司充值报表导出（单个运输公司）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/recharge/import")
	public String queryRechargeListImport(@ModelAttribute CurrUser currUser, ModelMap map,
										  SysOrder order,HttpServletResponse response) throws Exception{
		String stationId = currUser.getStationId();
		Transportion transportion = new Transportion();
		String transName = "";
		try {
			transportion = transportionService.queryTransportionByPK(stationId);
			if(transportion != null){
				transName = transportion.getTransportion_name();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		PageBean bean = new PageBean();

		try {
			if(order.getPageNum() == null){
				order.setOrderby("deal_date desc");
				order.setPageNum(1);
				order.setPageSize(1048576);
			}
			order.setDebitAccount(stationId);
			order.setCash(new BigDecimal(BigInteger.ZERO));
			PageInfo<Map<String, Object>> pageInfo = orderDealService.queryRechargeList(order);

            /*生成报表*/
			int cells = 0 ; // 记录条数
			if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
				cells += pageInfo.getList().size();
			}
			OutputStream os = response.getOutputStream();
			ExportUtil reportExcel = new ExportUtil();
			String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
			downLoadFileName = "充值报表_" + downLoadFileName;
			try {
				response.setHeader("Content-Disposition","attachment;filename=" + java.net.URLEncoder.encode(downLoadFileName, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
			}
			String[][] content = new String[cells+3][13];//[行数][列数]
			//设置表头
			content[0] = new String[]{transName+"充值报表"};
			content[2] = new String[]{"订单编号","充值方式","充值金额","操作人","交易时间"};
			//设置列宽
			String [] wcell = new String []{"0,26","1,13","2,13","3,13","4,13"};
			//合并第一行单元格
			String [] mergeinfo = new String []{"0,0,4,0","1,1,4,1"};
			//设置表名
			String sheetName = "充值报表";
			//设置字体
			String [] font = new String []{"0,15","2,13"};
            /*组装报表*/
			BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
			int i = 3;

			if(pageInfo.getList() != null && pageInfo.getList().size() > 0){

				for (Map<String, Object> quotaMap:pageInfo.getList()) {
					if(quotaMap.get("cash") != null && !"".equals(quotaMap.get("cash").toString())){
						totalCash = totalCash.add(new BigDecimal(quotaMap.get("cash").toString()));
					}

					//组装表格
					String orderNumber = "";//订单编号
					if(quotaMap.get("orderNumber") != null){
						orderNumber = quotaMap.get("orderNumber").toString();
					}
					String chargeType = "";
					if(quotaMap.get("charge_type") != null){
						chargeType = quotaMap.get("charge_type").toString();
					}
					String cash = "";
					if(quotaMap.get("cash") != null){
						cash = quotaMap.get("cash").toString();
					}
					String operator = "";
					if(quotaMap.get("operator") != null){
						operator = quotaMap.get("operator").toString();
					}
					String dealDate = "";
					if(quotaMap.get("dealDate") != null){
						dealDate = quotaMap.get("dealDate").toString();
					}

					content[i] = new String[]{orderNumber,chargeType,cash,operator,dealDate};
					i++;
				}
			}
			//合计交易金额和返现金额
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			content[1] = new String[]{"合计："+totalCash.toString(),"导出时间："+sdf.format(new Date())};
			reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, wcell, 0, null, 0, font, null, false);

			//累计总划款金额
			map.addAttribute("totalCash",totalCash);

			map.addAttribute("pageInfo", pageInfo);
			map.addAttribute("order",order);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
			return null;
	}

}
