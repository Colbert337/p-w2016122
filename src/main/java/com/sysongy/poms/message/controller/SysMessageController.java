package com.sysongy.poms.message.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.message.model.SysMessage;
import com.sysongy.poms.message.service.SysMessageService;

@RequestMapping("/web/message")
@Controller
public class SysMessageController extends BaseContoller {

	@Autowired
	private SysMessageService service;

	private SysMessage message;

	/**
	 * 加载司机列表
	 * 
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/driverList")
	public String driver(ModelMap map, SysDriver driver) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/message/dirverList";
		driver.setPageSize(10);
		try {
			if (driver.getPageNum() == null) {
				driver.setPageNum(1);

			}
			if (StringUtils.isEmpty(message.getOrderby())) {
				driver.setOrderby("created_date desc");
			}

			PageInfo<SysDriver> pageinfo = service.queryDriver1(driver);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("driver", driver);
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
	 * 查询已发生信息的司机
	 * @param map
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showUser")
	public String showUser(ModelMap map, SysMessage message) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/message/dirverList2";
		message.setPageSize(10);
		try {
			if (message.getPageNum() == null) {
				message.setPageNum(1);

			}
			if (StringUtils.isEmpty(message.getOrderby())) {
				message.setOrderby("message_created_time desc");
			}

			PageInfo<SysDriver> pageinfo = service.queryDriver2(message);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo1", pageinfo);
			map.addAttribute("message", message);
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
	 * 加气站查询
	 * 
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/messageList")
	public String queryAllMessageList(ModelMap map, SysMessage message) throws Exception {

		this.message = message;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/message/message_list";

		try {
			if (message.getPageNum() == null) {
				message.setPageNum(1);
				message.setPageSize(10);
			}
			if (StringUtils.isEmpty(message.getOrderby())) {
				message.setOrderby("message_created_time desc");
			}

			PageInfo<SysMessage> pageinfo = service.queryMessage(message);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("message", message);
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

	@RequestMapping("/saveMessage")
	public String saveMessage(ModelMap map, SysMessage message) throws Exception {

		PageBean bean = new PageBean();

		String ret = "webpage/poms/message/message_new";
		String messageid = null;
		message.setContent(message.getMessageBody());
		try {
			if (StringUtils.isEmpty(message.getId())) {

				messageid = service.saveMessage(message, "insert");
				
				bean.setRetMsg("发送成功");
				ret = "webpage/poms/message/message_new";
			} else {
				ret = "webpage/poms/gastation/gastation_update";
				messageid = service.saveMessage(message, "update");
				bean.setRetMsg("保存成功");
				ret = this.queryAllMessageList(map, this.message == null ? new SysMessage() : this.message);
			}

			bean.setRetCode(100);

			bean.setRetValue(messageid);
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			map.addAttribute("message", messageid);

			logger.error("", e);
		} finally {
			return ret;
		}
	}

	@RequestMapping("/saveMessageNew")
	public String saveMessageNew(ModelMap map, SysMessage message) throws Exception {

		PageBean bean = new PageBean();
		message.setContent(message.getMessageBody());
		String ret = "webpage/poms/message/messageNEW";
		String messageid = null;
		message.setMessageGroup("999");// 指定用户组
		message.setMessageType(1);
		try {

			messageid = service.saveMessage_New(message);
			bean.setRetMsg("发送成功");
//			ret = "webpage/poms/message/message_new";
			bean.setRetCode(100);
			bean.setRetValue(messageid);
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			map.addAttribute("message", messageid);

			logger.error("", e);
		} finally {
			return ret;
		}
	}

	@RequestMapping("/deleteMessage")
	public String deleteMessage(ModelMap map, @RequestParam String messageid) {

		PageBean bean = new PageBean();

		String ret = "webpage/poms/message/message_list";
		Integer rowcount = null;

		try {
			if (messageid != null && !"".equals(messageid)) {
				rowcount = service.delMessage(messageid);
			}

			ret = this.queryAllMessageList(map, this.message == null ? new SysMessage() : this.message);

			bean.setRetCode(100);
			bean.setRetMsg("删除成功");
			bean.setRetValue(rowcount.toString());
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);

		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllMessageList(map, this.message == null ? new SysMessage() : this.message);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}
	}

}
