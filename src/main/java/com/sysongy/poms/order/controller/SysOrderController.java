package com.sysongy.poms.order.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.avalon.framework.service.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.github.pagehelper.PageInfo;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.order.model.OrderLog;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.util.GlobalConstant;
import com.tencent.WXPay;
import com.tencent.common.Configure;
import com.tencent.protocol.refund_protocol.RefundReqData;

import net.sf.json.JSONObject;

@RequestMapping("/web/order")
@Controller
public class SysOrderController extends BaseContoller {
	
	@Autowired 
	private OrderService service;

	@Autowired 
	private SysUserAccountService accountService;
	@RequestMapping("/queryOrderDeal")
   	public String queryProductPriceList(ModelMap map, OrderLog order) throws Exception{

   		PageBean bean = new PageBean();
   		String ret = "webpage/poms/gastation/gastation_order_log";

   		try {
   			if(order.getPageNum() == null){
   				order.setOrderby("deal_date desc");
   				order.setPageNum(1);
   				order.setPageSize(10);
   			}
   			
   			PageInfo<OrderLog> pageinfo = service.queryOrderLogs(order);

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
	
	@RequestMapping("/queryOrderDeal2")
   	public String queryProductPriceList2(ModelMap map, OrderLog order) throws Exception{

   		PageBean bean = new PageBean();
   		String ret = "webpage/poms/transportion/transpotion_order_log";

   		try {
   			if(order.getPageNum() == null){
   				order.setOrderby("deal_date desc");
   				order.setPageNum(1);
   				order.setPageSize(10);
   			}
   			
   			PageInfo<OrderLog> pageinfo = service.queryOrderLogs(order);

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
	 * 退款查询
	 * @param order
	 * @param map
	 * @param type
	 * @return
	 */
	@RequestMapping("/list/page")
	public String orderList(SysOrder order, ModelMap map, String type) {
		String ret = "webpage/poms/mobile/order_list";

		PageBean bean = new PageBean();
		order.setPageSize(20);
		try {
			if (order.getPageNum() == null || "".equals(order.getPageNum())) {
				order.setPageNum(GlobalConstant.PAGE_NUM);
				order.setPageSize(20);
			}
			if (StringUtils.isEmpty(order.getOrderby())) {
				order.setOrderby("order_date desc");
			}

			PageInfo<SysOrder> pageinfo = new PageInfo<SysOrder>();
			pageinfo = service.queryOrderListForBack(order);
			bean.setRetCode(100);
		 
			bean.setRetMsg("查询成功");
			
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("order", order);
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
	
	/**
	 * 查询一个交易号里面的累计退金额
	 * @param order
	 * @param map
	 * @param type
	 * @return
	 */
	@RequestMapping("/BreakMoney")
	@ResponseBody
	public String queryForBreakMoney(String orderNumber, ModelMap map) {
//		String ret = "webpage/poms/mobile/order_list";
		PageBean bean = new PageBean();
		String money = "";
		try {
			money = service.queryForBreakMoney(orderNumber);
			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			if ("null".equalsIgnoreCase(money)||money==null) {
				money="0";
			}
			// map.addAttribute("current_module",
			// "/web/mobile/suggest/suggestList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			logger.error("", e);
		} finally {
			return money+"";
		}

	}
	/*sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
	sParaTemp.put("partner", AlipayConfig.partner);
	sParaTemp.put("_input_charset", AlipayConfig.input_charset);
	sParaTemp.put("notify_url", notify_url);
	sParaTemp.put("batch_no", batch_no);
	sParaTemp.put("refund_date", refund_date);
	sParaTemp.put("batch_num", batch_num);
	sParaTemp.put("detail_data", detail_data);*/
	/**
	 * 退费并保存退费订单
	 * @param detail_data
	 * @return
	 */
	@RequestMapping("/saveBreak")
	@ResponseBody
	public String saveBreak(ModelMap map,String money, String msg, String tradeNo,String cash, String orderId, String type) {
		String http_poms_path = (String) prop.get("http_poms_path");
		SysOrder order = null;
		PageBean bean = new PageBean();
		SysUserAccount account;
		try {
			order =service.queryById(orderId);
			account=accountService.queryUserAccountByDriverId(order.getDebitAccount());
			if (account==null) {
				throw new Exception("查找司机失败,返现失败");
			}
			//生成退款定单
			String batch_no = new String(getBatchNo().getBytes("ISO-8859-1"), "UTF-8");
			if (type.equals("104")) {// 支付宝退费
				
				AlipayConfig.key=GlobalConstant.ALIKEY;
				AlipayConfig.log_path=(String)prop.get("log_path");
				AlipayConfig.partner=GlobalConstant.PARTNER;
				AlipayConfig.sign_type=GlobalConstant.SIGNTYPE;
				AlipayConfig.input_charset=GlobalConstant.INPUTCHARSET;
				
//				String notify_url = http_poms_path + "/refund_fastpay_by_platform_nopwd-JAVA-UTF-8/notify_url.jsp";
			
				// 退款请求时间
				String refund_date = new String(
						new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).getBytes("ISO-8859-1"), "UTF-8");
				// 必填，格式为：yyyy-MM-dd hh:mm:ss

				// 退款总笔数
				String batch_num = new String("1".getBytes("ISO-8859-1"), "UTF-8");
				// 必填，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的最大数量999个）
				if(msg.indexOf('^')>0){
					throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
				}
				if(msg.indexOf('|')>0){
					throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！"); 
				}
				if(msg.indexOf('$')>0){
					throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
				}
				if(msg.indexOf('#')>0){
					throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
				}
				// 单笔数据集
				String detail_data = new String((tradeNo + "^" + money + "^" + msg).getBytes("ISO-8859-1"), "UTF-8");
				

				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
				sParaTemp.put("partner", AlipayConfig.partner);
				sParaTemp.put("_input_charset", AlipayConfig.input_charset);
				sParaTemp.put("notify_url", http_poms_path+"/web/order/breakReturn");
				sParaTemp.put("batch_no", batch_no);
//				sParaTemp.put("", arg1);
				sParaTemp.put("refund_date", refund_date);
				sParaTemp.put("batch_num", batch_num);
				sParaTemp.put("detail_data", detail_data);
				String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);//支付宝接口 如果账户发生变化 请到AlipayConfig配置
				if (sHtmlText.toUpperCase().indexOf("<is_success>T</is_success>".toUpperCase())>0) {
					bean.setRetMsg("退款申请成功，等待支付退款");
//					order = service.queryById(orderId);
					order.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
					order.setCash(new BigDecimal(money).multiply(new BigDecimal(-1)));
					order.setIs_discharge("0");
					order.setOrderStatus(3);
					order.setOrderDate(new Date());
					order.setOrderType("230");
					order.setChargeType("110");
					order.setBatch_no(batch_no);
					order.setOrderRemark(msg);
					service.saveOrder(order);
				} else {
					throw new Exception("退款失败,错误代码：" + sHtmlText);
				}
				 
			}else{//微信退款
				Configure.setAppID(GlobalConstant.APPID);
				Configure.setKey(GlobalConstant.WXKEY);
				Configure.setMchID(GlobalConstant.MCHID);
				Configure.setCertLocalPath((String) (prop.get("certLocalPath")));
				Configure.setCertPassword(GlobalConstant.CERTPASSWORD);
				RefundReqData rrd = new RefundReqData(tradeNo, "", null, batch_no,
						Integer.valueOf((int) ((double) Double
								.parseDouble(new BigDecimal(cash).multiply(new BigDecimal(100)).toString()))),
						Integer.valueOf((int) ((double) Double
								.parseDouble(new BigDecimal(money).multiply(new BigDecimal(100)).toString()))),
						Configure.getMchid(), "CNY");
				// RefundReqData rrd = new
				// RefundReqData("4008642001201610126499353666", "", null,
				// "00059", 1, 1, Configure.getMchid(),"CNY");
				String xml = WXPay.requestRefundService(rrd);
				if (xml.indexOf("<return_msg><![CDATA[OK]]></return_msg>") > 0) {
					if (xml.indexOf("<err_code_des>") > 0) {
						throw new Exception("退款失败,错误信息：" + xml.substring(
								xml.indexOf("<err_code_des><![CDATA[") + "<err_code_des><![CDATA[".length(),
								xml.indexOf("]]></err_code_des>")));
					} else {
						bean.setRetMsg("退款成功");
//						order = service.queryById(orderId);
						order.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
						order.setCash(new BigDecimal(money).multiply(new BigDecimal(-1)));
						order.setIs_discharge("0");
						order.setOrderStatus(1);
						order.setOrderDate(new Date());
						order.setOrderType("230");
						order.setChargeType("111");
						order.setOrderRemark(msg);
						order.setBatch_no(batch_no);
						service.saveOrder(order);
					}
				}else{
					throw new Exception("退款失败,错误信息："
							+ xml.substring(xml.indexOf("<return_msg><![CDATA[") + "<return_msg><![CDATA[".length(),
									xml.indexOf("]]></return_msg>")));
				}
				account.setAccountBalanceBigDecimal(account.getAccountBalanceBigDecimal().add(new BigDecimal(money)));
				accountService.updateAccount(account);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			logger.error("", e);
		}  finally {
			return bean.getRetMsg();
		}
	}
	/**
	 * 退费并保存退费订单
	 * @param detail_data
	 * @return
	 */
	@RequestMapping("/breakReturn")
	@ResponseBody
	public String breakReturn(HttpServletRequest request, HttpServletResponse response,ModelMap map) {
		String batch_no = "";
		String result_details= "";
		logger.debug("支付宝支付回调获取数据开始");
		try {
			batch_no = request.getParameter("batch_no");
			result_details = request.getParameter("result_details");//支付宝返回结果集
		
		if (StringUtils.isEmpty(result_details)&&StringUtils.isEmpty(batch_no)) {
			logger.debug("支付宝回调返回结果为空");
			throw new ServiceException("支付宝回调返回结果为空！");
		}
		String[] dates=result_details.split("#");//第一单
		String[] date=dates[0].split("|");//第一笔
		String no=date[0].split("^")[0];
		String money=date[0].split("^")[1];
		String b=date[0].split("^")[2];
		SysOrder order=null;
		if (b.equalsIgnoreCase("SUCCESS")) {
			
			
			order=service.queryByTrade(batch_no);
			MobileReturn result = new MobileReturn();
			result.setStatus(MobileReturn.STATUS_SUCCESS);
			result.setMsg("支付成功！");
			JSONObject resutObj = new JSONObject();
			String resultStr = "";
			// 查询订单内容
//			SysOrder order = service.selectByPrimaryKey(batch_no);
			if (order != null && order.getOrderStatus() == 3) {// 当订单状态是初始化时，做状态更新
				// 修改订单状态
				order.setOrderStatus(1);
				order.setOrderRemark("退款成功！");
				order.setOrderDate(new Date());
				service.updateByPrimaryKey(order);
				SysUserAccount account=accountService.queryUserAccountByDriverId(order.getDebitAccount());//初始化钱袋
				if (account==null) {
					logger.error("支付宝返现失败：");
				}
				account.setAccountBalanceBigDecimal(account.getAccountBalanceBigDecimal().add(new BigDecimal(money)));
				accountService.updateAccount(account);
			}

		
		}else{
			order.setOrderStatus(2);
			order.setOrderRemark("退款失败！");
			order.setOrderDate(new Date());
			service.updateByPrimaryKey(order);
			logger.error("支付宝返现失败："+b);
		}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return "";
		}
		
	}
	//生成定点编号
	private synchronized String getBatchNo(){
		return (new SimpleDateFormat("yyyyMMdd").format(new Date())+System.currentTimeMillis()).toString();
	}
}
