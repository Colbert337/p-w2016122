package com.sysongy.poms.order.controller;

import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.order.model.SysOrderDeal;
import com.sysongy.poms.order.service.OrderDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.order.model.OrderLog;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;

import java.util.Map;

@RequestMapping("/web/order")
@Controller
public class SysOrderController extends BaseContoller {
	
	@Autowired 
	private OrderService service;

	
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

}
