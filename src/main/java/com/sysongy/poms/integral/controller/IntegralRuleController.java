package com.sysongy.poms.integral.controller;

import javax.servlet.http.HttpSession;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.integral.model.IntegralRule;
import com.sysongy.poms.integral.service.IntegralRuleService;

import net.sf.json.JSONArray;

@RequestMapping("/web/integralRule")
@Controller
public class IntegralRuleController extends BaseContoller {

	@Autowired
	private IntegralRuleService service;	
	

	private IntegralRule integralRule;

	/**
	 * 查询积分规则
	 * @param map
	 * @param integralRule
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/integralRuleList")
	public String queryAllCouponGroupList(ModelMap map,IntegralRule integralRule) throws Exception {
		this.integralRule = integralRule;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/integral/manageIntegralRule";
		try {
			PageInfo<IntegralRule> pageinfo = service.queryIntegralRule(integralRule);
			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("integralRule", integralRule);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			logger.error("查询积分规则信息错误！", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 更新积分规则信息
	 * @param map
	 * @param integralRule
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveIntegralRule")
	public String addCouponGroup(ModelMap map, IntegralRule integralRule,HttpServletRequest request) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/integral/addIntegralRule";
		String integral_rule_id = null;
		HttpSession session = request.getSession(true);
		CurrUser currUser = (CurrUser) session.getAttribute("currUser");// 登录人角色
		if (null == currUser) {
			bean.setRetCode(5000);
			bean.setRetMsg("登录信息过期，请重新登录！");
			return ret;
		} 
		try {
			if (null == integralRule.getIntegral_rule_id()) {
				integral_rule_id = service.addIntegralRule(integralRule, currUser.getUserId());
				bean.setRetMsg("积分规则新增成功！");
			} else {
				integral_rule_id = service.modifyIntegralRule(integralRule, currUser.getUserId());
				bean.setRetMsg("积分规则保存成功！");
			}
			ret = this.queryAllCouponGroupList(map, this.integralRule == null ? new IntegralRule() : this.integralRule);
			bean.setRetCode(100);
			bean.setRetValue(integral_rule_id);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			map.addAttribute("integral_rule_id", integral_rule_id);
			map.addAttribute("integralRule", integralRule);
			logger.error("更新积分规则信息错误！", e);
		} finally {
			return ret;
		}
	}

	/**
	 * 修改积分规则信息
	 * @param map
	 * @param integral_rule_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyIntegralRule")
	public String modifyIntegralRule(ModelMap map, String integral_rule_id) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/integral/modifyIntegralRule";
		IntegralRule integralRule = new IntegralRule();
		try {
			integralRule = service.queryIntegralRuleByPK(integral_rule_id);
			bean.setRetMsg("根据[" + integral_rule_id + "]查询成功");
			bean.setRetCode(100);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("integralRule", integralRule);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			ret = this.queryAllCouponGroupList(map, this.integralRule == null ? new IntegralRule() : this.integralRule);
			map.addAttribute("ret", bean);
			logger.error("根据" + integral_rule_id + "查询积分规则信息出错！", e);
			throw e;
		} finally {
			return ret;
		}
	}


	/**
	 * 删除积分规则
	 * @param map
	 * @param integral_rule_id
	 * @return
	 */
	@RequestMapping("/deleteIntegralRule")
	public String deleteCoupon(ModelMap map, @RequestParam String integral_rule_id) {

		PageBean bean = new PageBean();

		String ret = "webpage/poms/integral/manageIntegralRule";
		Integer rowcount = null;
		try {
			if (integral_rule_id != null && !"".equals(integral_rule_id)) {
				rowcount = service.delIntegralRule(integral_rule_id);
				ret = this.queryAllCouponGroupList(map,
						this.integralRule == null ? new IntegralRule() : this.integralRule);
				bean.setRetCode(100);
				bean.setRetMsg("删除成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);
				map.addAttribute("ret", bean);
			}
		} catch (Exception e) { 
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			ret = this.queryAllCouponGroupList(map, this.integralRule == null ? new IntegralRule() : this.integralRule);
			map.addAttribute("ret", bean);
			logger.error("根据" + integral_rule_id + "删除积分规则错误！", e);
			throw e;
		} finally {
			return ret;
		}
	}
	
	/**
	 * 查询积分规则信息
	 * @param integral_type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectRepeatIntegralType")
	@ResponseBody
	public JSONObject selectRepeatIntegralType(@RequestParam String integral_type) throws Exception {
		HashMap<String,String> statusMap = new HashMap<String,String>();
		try {
			statusMap = service.selectRepeatIntegralType(integral_type);
		} catch (Exception e) {
			logger.error("查询加注站信息出错！", e);
			throw e;
		}
		JSONObject jSONObject = new JSONObject();
		jSONObject.putAll(statusMap);
		return jSONObject;
	}
}
