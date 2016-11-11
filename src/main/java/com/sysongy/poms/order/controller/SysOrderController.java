package com.sysongy.poms.order.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.github.pagehelper.PageInfo;
import com.sysongy.api.mobile.model.verification.MobileVerification;
import com.sysongy.api.mobile.tools.verification.MobileVerificationUtils;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.order.model.OrderLog;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.RedisClientInterface;
import com.tencent.WXPay;
import com.tencent.common.Configure;
import com.tencent.protocol.refund_protocol.RefundReqData;

@RequestMapping("/web/order")
@Controller
public class SysOrderController extends BaseContoller {

	@Autowired
	private OrderService service;

	@Autowired
	RedisClientInterface redisClientImpl;
	
	@Autowired
	private SysUserAccountService accountService;

	@RequestMapping("/queryOrderDeal")
	public String queryProductPriceList(ModelMap map, OrderLog order) throws Exception {

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_order_log";

		try {
			if (order.getPageNum() == null) {
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
			map.addAttribute("order", order);
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

	@RequestMapping("/queryOrderDeal2")
	public String queryProductPriceList2(ModelMap map, OrderLog order) throws Exception {

		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transpotion_order_log";

		try {
			if (order.getPageNum() == null) {
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
			map.addAttribute("order", order);
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
	 * 退款主查询
	 * 
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
	 * 退款子查询
	 * 
	 * @param order
	 * @param map
	 * @param type
	 * @return
	 */
	@RequestMapping("/showOrderForBack")
	public String showOrderForBreak(SysOrder order, ModelMap map, String type) {
		String ret = "webpage/poms/mobile/order_listForBack";

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
			pageinfo = service.queryOrderListForBack2(order);
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
	 * 
	 * @param order
	 * @param map
	 * @param type
	 * @return
	 */
	@RequestMapping("/BreakMoney")
	@ResponseBody
	public String queryForBreakMoney(String orderNumber, ModelMap map) {
		// String ret = "webpage/poms/mobile/order_list";
		PageBean bean = new PageBean();
		String money = "";
		try {
			money = service.queryForBreakMoney(orderNumber);
			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			if ("null".equalsIgnoreCase(money) || money == null) {
				money = "0";
			}
			// map.addAttribute("current_module",
			// "/web/mobile/suggest/suggestList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			logger.error("", e);
		} finally {
			return money + "";
		}

	}

	/*
	 * sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
	 * sParaTemp.put("partner", AlipayConfig.partner);
	 * sParaTemp.put("_input_charset", AlipayConfig.input_charset);
	 * sParaTemp.put("notify_url", notify_url); sParaTemp.put("batch_no",
	 * batch_no); sParaTemp.put("refund_date", refund_date);
	 * sParaTemp.put("batch_num", batch_num); sParaTemp.put("detail_data",
	 * detail_data);
	 */
	/**
	 * 退费并保存退费订单
	 * 
	 * @param detail_data
	 * @return
	 */
	@RequestMapping("/saveBreak")
	@ResponseBody
	public String saveBreak(ModelMap map, String money, String msg, String tradeNo, String cash, String orderId,
			String type, String phone, String code) {

		String http_poms_path = (String) prop.get("http_poms_path");
		SysOrder order = null;
		SysOrder newOrder = null;
		PageBean bean = new PageBean();
		SysUserAccount account;
		try {
			if (code != null) {
				if (((String) redisClientImpl.getFromCache(phone)).equalsIgnoreCase(code)) {
					logger.debug("验证码正确");
				}
			} else {
				throw new Exception("验证码不能为空");
			}
			order = service.queryById(orderId);
			newOrder = service.queryById(orderId);
			// 判断司机消费
			if (order.getOrderType().equals("220")) {
				account = accountService.queryUserAccountByDriverId(order.getCreditAccount());
			} else {
				// 个人充值
				account = accountService.queryUserAccountByDriverId(order.getDebitAccount());
			}

			if (account == null) {
				throw new Exception("查找司机失败,返现失败");
			}
			// 生成退款定单
			String batch_no = new String(getBatchNo().getBytes("ISO-8859-1"), "UTF-8");
			// 判断司机消费
			if (order.getOrderType().equals("220")) {
				if (order.getSpend_type().equalsIgnoreCase("C04")) {// 支付宝退费

					AlipayConfig.key = GlobalConstant.ALIKEY;
					AlipayConfig.log_path = (String) prop.get("log_path");
					AlipayConfig.partner = GlobalConstant.PARTNER;
					AlipayConfig.sign_type = GlobalConstant.SIGNTYPE;
					AlipayConfig.input_charset = GlobalConstant.INPUTCHARSET;

					// String notify_url = http_poms_path +
					// "/refund_fastpay_by_platform_nopwd-JAVA-UTF-8/notify_url.jsp";

					// 退款请求时间
					String refund_date = new String(
							new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).getBytes("ISO-8859-1"),
							"UTF-8");
					// 必填，格式为：yyyy-MM-dd hh:mm:ss

					// 退款总笔数
					String batch_num = new String("1".getBytes("ISO-8859-1"), "UTF-8");
					// 必填，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的最大数量999个）
					if (msg.indexOf('^') > 0) {
						throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
					}
					if (msg.indexOf('|') > 0) {
						throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
					}
					if (msg.indexOf('$') > 0) {
						throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
					}
					if (msg.indexOf('#') > 0) {
						throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
					}
					// 单笔数据集
					String detail_data = new String((tradeNo + "^" + money + "^" + msg).getBytes("ISO-8859-1"),
							"UTF-8");

					Map<String, String> sParaTemp = new HashMap<String, String>();
					sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
					sParaTemp.put("partner", AlipayConfig.partner);
					sParaTemp.put("_input_charset", AlipayConfig.input_charset);
					sParaTemp.put("notify_url", http_poms_path + "/api/v1/mobile/breakReturn");
					sParaTemp.put("batch_no", batch_no);
					// sParaTemp.put("", arg1);
					sParaTemp.put("refund_date", refund_date);
					sParaTemp.put("batch_num", batch_num);
					sParaTemp.put("detail_data", detail_data);
					String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);// 支付宝接口
																					// 如果账户发生变化
																					// 请到AlipayConfig配置
					if (sHtmlText.toUpperCase().indexOf("<is_success>T</is_success>".toUpperCase()) > 0) {
						bean.setRetMsg("退款申请成功，等待支付退款");
						// order = service.queryById(orderId);
						newOrder.setOrderRemark(msg);
						newOrder.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
						newOrder.setCash(new BigDecimal(money).multiply(new BigDecimal(-1)));
						newOrder.setIs_discharge("0");
						newOrder.setOrderStatus(3);
						newOrder.setOrderDate(new Date());
						newOrder.setOrderType("230");
						newOrder.setChargeType("110");
						newOrder.setShould_payment(order.getCash());
						newOrder.setBatch_no(batch_no);
						newOrder.setOrderRemark(msg);
						order.setCash(order.getCash().subtract(new BigDecimal(money)));
						service.updateByPrimaryKey(order);
						service.saveOrder(newOrder);
					} else {
						throw new Exception("退款失败,错误代码：" + sHtmlText);
					}

				}else
				{
					// 微信退款
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
							// order = service.queryById(orderId);
							newOrder.setOrderRemark(msg);
							newOrder.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
							newOrder.setCash(new BigDecimal(money).multiply(new BigDecimal(-1)));
							newOrder.setIs_discharge("0");
							newOrder.setOrderStatus(1);
							newOrder.setOrderDate(new Date());
							newOrder.setOrderType("230");
							newOrder.setChargeType("111");
							newOrder.setOrderRemark(msg);
							newOrder.setShould_payment(order.getCash());
							newOrder.setBatch_no(batch_no);
							order.setCash(order.getCash().subtract(new BigDecimal(money)));
							service.updateByPrimaryKey(order);
							service.saveOrder(newOrder);
						}
					} else {
						throw new Exception("退款失败,错误信息："
								+ xml.substring(xml.indexOf("<return_msg><![CDATA[") + "<return_msg><![CDATA[".length(),
										xml.indexOf("]]></return_msg>")));
					}
					account.setAccountBalance(
							account.getAccountBalanceBigDecimal().subtract(new BigDecimal(money)).toString());
//					accountService.updateAccount(account);
					accountService.addCashToAccount(account.getSysUserAccountId(), (new BigDecimal(money)), "230");
				
					
				}
			} else {
				// 个人充值
			
			if (type.equals("104")) {// 支付宝退费

				AlipayConfig.key = GlobalConstant.ALIKEY;
				AlipayConfig.log_path = (String) prop.get("log_path");
				AlipayConfig.partner = GlobalConstant.PARTNER;
				AlipayConfig.sign_type = GlobalConstant.SIGNTYPE;
				AlipayConfig.input_charset = GlobalConstant.INPUTCHARSET;

				// String notify_url = http_poms_path +
				// "/refund_fastpay_by_platform_nopwd-JAVA-UTF-8/notify_url.jsp";

				// 退款请求时间
				String refund_date = new String(
						new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).getBytes("ISO-8859-1"), "UTF-8");
				// 必填，格式为：yyyy-MM-dd hh:mm:ss

				// 退款总笔数
				String batch_num = new String("1".getBytes("ISO-8859-1"), "UTF-8");
				// 必填，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的最大数量999个）
				if (msg.indexOf('^') > 0) {
					throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
				}
				if (msg.indexOf('|') > 0) {
					throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
				}
				if (msg.indexOf('$') > 0) {
					throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
				}
				if (msg.indexOf('#') > 0) {
					throw new Exception("退款理由格式不正确，不可以包含“^”、“|”、“$”、“#”等特殊字符！");
				}
				// 单笔数据集
				String detail_data = new String((tradeNo + "^" + money + "^" + msg).getBytes("ISO-8859-1"), "UTF-8");

				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
				sParaTemp.put("partner", AlipayConfig.partner);
				sParaTemp.put("_input_charset", AlipayConfig.input_charset);
				sParaTemp.put("notify_url", http_poms_path + "/api/v1/mobile/breakReturn");
				sParaTemp.put("batch_no", batch_no);
				// sParaTemp.put("", arg1);
				sParaTemp.put("refund_date", refund_date);
				sParaTemp.put("batch_num", batch_num);
				sParaTemp.put("detail_data", detail_data);
				String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);// 支付宝接口
																				// 如果账户发生变化
																				// 请到AlipayConfig配置
				if (sHtmlText.toUpperCase().indexOf("<is_success>T</is_success>".toUpperCase()) > 0) {
					bean.setRetMsg("退款申请成功，等待支付退款");
					// order = service.queryById(orderId);
					newOrder.setOrderRemark(msg);
					newOrder.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
					newOrder.setCash(new BigDecimal(money).multiply(new BigDecimal(-1)));
					newOrder.setIs_discharge("0");
					newOrder.setOrderStatus(3);
					newOrder.setOrderDate(new Date());
					newOrder.setOrderType("230");
					newOrder.setChargeType("110");
					newOrder.setShould_payment(order.getCash());
					newOrder.setBatch_no(batch_no);
					newOrder.setOrderRemark(msg);
					order.setCash(order.getCash().subtract(new BigDecimal(money)));
					service.updateByPrimaryKey(order);
					service.saveOrder(newOrder);
				} else {
					throw new Exception("退款失败,错误代码：" + sHtmlText);
				}

			} else {// 微信退款
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
						// order = service.queryById(orderId);
						newOrder.setOrderRemark(msg);
						newOrder.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
						newOrder.setCash(new BigDecimal(money).multiply(new BigDecimal(-1)));
						newOrder.setIs_discharge("0");
						newOrder.setOrderStatus(1);
						newOrder.setOrderDate(new Date());
						newOrder.setOrderType("230");
						newOrder.setChargeType("111");
						newOrder.setOrderRemark(msg);
						newOrder.setShould_payment(order.getCash());
						newOrder.setBatch_no(batch_no);
						order.setCash(order.getCash().subtract(new BigDecimal(money)));
						service.updateByPrimaryKey(order);
						service.saveOrder(newOrder);
					}
				} else {
					throw new Exception("退款失败,错误信息："
							+ xml.substring(xml.indexOf("<return_msg><![CDATA[") + "<return_msg><![CDATA[".length(),
									xml.indexOf("]]></return_msg>")));
				}
				account.setAccountBalance(
						account.getAccountBalanceBigDecimal().subtract(new BigDecimal(money)).toString());
				
				accountService.addCashToAccount(account.getSysUserAccountId(),  (new BigDecimal("-"+money)), "230");
			}
			}
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

	@RequestMapping("/saveBreakForRe")
	@ResponseBody
	public String saveBreakForRe(String msg, String money, String orderId,String phone,String code) {
		PageBean bean = new PageBean();
		try {
			if (code!=null) {
				if (((String)redisClientImpl.getFromCache(phone)).equalsIgnoreCase(code)) {
					
				}
			}else{
				throw new Exception("验证码不能为空");
			}
			 
			service.saveBareakForRe(msg,money,orderId);
			bean.setRetMsg("退款成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setRetMsg("退款失败:"+e.getMessage());
		}finally {
			return bean.getRetMsg();
		}
		
		// order.setBatch_no(batch_no);

	}
	@RequestMapping("/checkPhone")
	@ResponseBody
	public String check(String phone){
		Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);
		String md5phone=(String) prop.get("checkPhone");
		String md5=Encoder.MD5Encode(phone.getBytes());
		PageBean bean=new PageBean();
		try {
			if (md5phone.equalsIgnoreCase(md5)) {
				AliShortMessage.SHORT_MESSAGE_TYPE msgTypeaTemp = AliShortMessage.SHORT_MESSAGE_TYPE.USER_VALIDATION;
				MobileVerification verification = new MobileVerification();
				verification.setPhoneNum(phone);
				verification.setContent("退款");
				MobileVerificationUtils.sendMSGType(verification,checkCode.toString(),msgTypeaTemp);
				// 设置短信有效期10分钟
				redisClientImpl.addToCache(verification.getPhoneNum(), checkCode.toString(), 600);
				bean.setRetMsg("获取验证码成功！");
			}else{
				bean.setRetMsg("发送失败，手机号码不正确！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setRetMsg("发送失败："+e.getMessage());
		}finally {
			return bean.getRetMsg();
		}
		
	}
	// 生成定点编号
	public static synchronized String getBatchNo() {
		return (new SimpleDateFormat("yyyyMMdd").format(new Date()) + System.currentTimeMillis()).toString();
	}
}
