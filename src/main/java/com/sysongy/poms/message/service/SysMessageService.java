package com.sysongy.poms.message.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.message.model.SysMessage;
import com.sysongy.poms.mobile.model.SysRoadCondition;

public interface SysMessageService {
	
	public PageInfo<SysMessage> queryMessage(SysMessage obj) throws Exception;
	
	
	
	public String saveMessage(SysMessage obj,  String operation) throws Exception;
	
	public Integer delMessage(String messageid) throws Exception;

	public PageInfo<SysDriver> queryDriver(SysDriver message);

	public PageInfo<SysDriver> queryDriver1(SysDriver driver);

	String saveMessage_New(SysMessage obj) throws Exception;

	PageInfo<SysDriver> queryDriver2(SysMessage message) throws Exception;
	
	public PageInfo<Map<String, Object>> queryMsgListForPage(SysMessage obj) throws Exception;

	SysMessage queryMessageByPK(SysMessage message) throws Exception;



	public String saveMessage_New_Road(String content, SysRoadCondition publisherPhone) throws Exception;





}
