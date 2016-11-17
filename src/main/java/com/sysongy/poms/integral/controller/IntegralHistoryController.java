package com.sysongy.poms.integral.controller;

import javax.servlet.http.HttpSession;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.integral.model.IntegralHistory;
import com.sysongy.poms.integral.service.IntegralHistoryService;

@RequestMapping("/web/integralHistory")
@Controller
public class IntegralHistoryController extends BaseContoller {

	@Autowired
	private IntegralHistoryService service;

	private IntegralHistory integralHistory;

	/**
	 * 查询积分历史信息
	 * 
	 * @param map
	 * @param integralHistory
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/integralHistoryList")
	public String queryAllIntegralHistoryList(ModelMap map, IntegralHistory integralHistory) throws Exception {
		this.integralHistory = integralHistory;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/integral/manageIntegralHistory";
		try {
			PageInfo<IntegralHistory> pageinfo = service.queryIntegralHistory(integralHistory);
			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("integralHistory", integralHistory);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			logger.error("查询积分历史信息错误！", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 更新积分历史信息
	 * 
	 * @param map
	 * @param integralHistory
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveIntegralHistory")
	public String addIntegralHistory(ModelMap map, IntegralHistory integralHistory, HttpServletRequest request)
			throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/integral/addIntegralHistory";
		String integral_history_id = null;
		HttpSession session = request.getSession(true);
		CurrUser currUser = (CurrUser) session.getAttribute("currUser");// 登录人角色
		if (null == currUser) {
			bean.setRetCode(5000);
			bean.setRetMsg("登录信息过期，请重新登录！");
			return ret;
		}
		try {
			if (null == integralHistory.getIntegral_history_id()) {
				integral_history_id = service.addIntegralHistory(integralHistory, currUser.getUserId());
				bean.setRetMsg("新增成功");
			} else {
				integral_history_id = service.modifyIntegralHistory(integralHistory, currUser.getUserId());
				bean.setRetMsg("保存成功");
			}
			ret = this.queryAllIntegralHistoryList(map,
					this.integralHistory == null ? new IntegralHistory() : this.integralHistory);
			bean.setRetCode(100);
			bean.setRetValue(integral_history_id);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			map.addAttribute("integral_history_id", integral_history_id);
			map.addAttribute("integralHistory", integralHistory);
			logger.error("更新积分历史信息出错！", e);
		} finally {
			return ret;
		}
	}

	/**
	 * 修改积分历史信息页面展现
	 * 
	 * @param map
	 * @param integral_history_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyIntegralHistory")
	public String modifyIntegralHistory(ModelMap map, String integral_history_id) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/integral/modifyIntegralHistory";
		IntegralHistory integralHistory = new IntegralHistory();
		try {
			integralHistory = service.queryIntegralHistoryByPK(integral_history_id);
			bean.setRetMsg("根据[" + integral_history_id + "]查询成功");
			bean.setRetCode(100);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("integralHistory", integralHistory);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			ret = this.queryAllIntegralHistoryList(map,
					this.integralHistory == null ? new IntegralHistory() : this.integralHistory);
			map.addAttribute("ret", bean);
			logger.error("根据" + integral_history_id + "查询积分历史信息出错！", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 删除积分历史记录
	 * 
	 * @param map
	 * @param integral_history_id
	 * @return
	 */
	@RequestMapping("/deleteIntegralHistory")
	public String deleteIntegralHistory(ModelMap map, @RequestParam String integral_history_id) {

		PageBean bean = new PageBean();

		String ret = "webpage/poms/integral/manageIntegralHistory";
		Integer rowcount = null;
		try {
			if (integral_history_id != null && !"".equals(integral_history_id)) {
				rowcount = service.delIntegralHistory(integral_history_id);
				ret = this.queryAllIntegralHistoryList(map,
						this.integralHistory == null ? new IntegralHistory() : this.integralHistory);
				bean.setRetCode(100);
				bean.setRetMsg("删除成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);
				map.addAttribute("ret", bean);
			}
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			ret = this.queryAllIntegralHistoryList(map,
					this.integralHistory == null ? new IntegralHistory() : this.integralHistory);
			map.addAttribute("ret", bean);
			logger.error("根据" + integral_history_id + "删除积分历史记录错误！", e);
			throw e;
		} finally {
			return ret;
		}
	}
}
