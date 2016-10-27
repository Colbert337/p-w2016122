package com.sysongy.poms.order.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.github.pagehelper.PageInfo;
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
			pageinfo = service.queryRoadListForBack(order);
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
	public String saveBreak(ModelMap map,String money, String msg, String tradeNo,String cash, String orderid, String type) {
		String http_poms_path = (String) prop.get("http_poms_path");
		SysOrder order = null;
		PageBean bean = new PageBean();
		
		try {
			//生成退款定单
			String batch_no = new String(getBatchNo().getBytes("ISO-8859-1"), "UTF-8");
			if (type.equals("104")) {// 支付宝退费
				AlipayConfig.key=(String)prop.get("alikey");
				AlipayConfig.log_path=(String)prop.get("log_path");
				AlipayConfig.partner=(String)prop.get("partner");
				AlipayConfig.sign_type=(String)prop.get("sign_type");
				AlipayConfig.input_charset=(String)prop.get("input_charset");
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
//				sParaTemp.put("notify_url", notify_url);
				sParaTemp.put("batch_no", batch_no);
				sParaTemp.put("refund_date", refund_date);
				sParaTemp.put("batch_num", batch_num);
				sParaTemp.put("detail_data", detail_data);
				String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);//支付宝接口 如果账户发生变化 请到AlipayConfig配置
				if (sHtmlText.equalsIgnoreCase("T")) {
					bean.setRetMsg("退款成功");
					order = service.queryById(orderid);
					order.setCash(new BigDecimal(money).subtract(new BigDecimal(-1)));
					order.setIs_discharge("1");
					order.setOrderDate(new Date());
					order.setOrderType("230");
					order.setChargeType("110");
					order.setOrderRemark(msg);
					service.saveOrder(order);
				} else {
					throw new Exception("退款失败,错误代码：" + sHtmlText);
				}
				 
			}else{//微信退款
				Configure.setAppID((String)prop.get("appid"));
				Configure.setKey((String)prop.get("wxkey"));
				Configure.setMchID((String)prop.get("mchID"));
				Configure.setCertLocalPath((String)(prop.get("certLocalPath")));
				Configure.setCertPassword((String)prop.get("certPassword"));
				RefundReqData rrd = new RefundReqData(tradeNo, "", null, batch_no,Integer.valueOf(new BigDecimal(cash).subtract(new BigDecimal(100))+""),Integer.valueOf( new BigDecimal(money).subtract(new BigDecimal(100))+""), Configure.getMchid(),"CNY");
				String xml=WXPay.requestRefundService(rrd);
				if (xml.indexOf("<err_code_des>")>0) {
					throw new Exception("退款失败,错误信息：" + xml.substring(xml.indexOf("<err_code_des>")+"<err_code_des>".length(), xml.indexOf("</err_code_des>")));
				}else{
					bean.setRetMsg("退款成功");
					order = service.queryById(orderid);
					order.setCash(new BigDecimal(money).subtract(new BigDecimal(-1)));
					order.setIs_discharge("1");
					order.setOrderDate(new Date());
					order.setOrderType("230");
					order.setChargeType("110");
					order.setOrderRemark(msg);
					service.saveOrder(order);
				}
			}
			SysUserAccount account=accountService.queryUserAccountByDriverId(order.getSysDriver().getSysDriverId());//初始化钱袋
			account.setAccountBalanceBigDecimal(account.getAccountBalanceBigDecimal().add(new BigDecimal(money)));
			accountService.updateAccount(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			logger.error("", e);
		} finally {
			return bean.getRetMsg();
		}
	}
	//生成定点编号
	private synchronized String getBatchNo(){
		return (new SimpleDateFormat("yyyyMMdd").format(new Date())+System.currentTimeMillis()).toString();
	}
}
