package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/crmCashServiceContoller")
public class CRMCashServiceContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping("/web/customerGasPay")
    public AjaxJson customerGasPay(HttpServletRequest request, HttpServletResponse response, SysOrder record) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();

        if((record == null) || StringUtils.isEmpty(record.getOrderId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单ID为空！！！");
            return ajaxJson;
        }

        PageInfo<SysOrder> sysOrders = orderService.queryOrders(record);
        if((sysOrders == null) || (sysOrders.getList().size() > 0)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("该订单已存在，请勿提交重复订单！！！");
            return ajaxJson;
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sysOrder", sysOrders);
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

}
