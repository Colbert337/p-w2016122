package com.sysongy.poms.message.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.message.model.SysMessage;

public interface SysMessageService {
	
	public PageInfo<SysMessage> queryMessage(SysMessage obj) throws Exception;
	
	public SysMessage queryMessageByPK(String messageid) throws Exception;
	
	public String saveMessage(SysMessage obj,  String operation) throws Exception;
	
	public Integer delMessage(String messageid) throws Exception;

	public PageInfo<SysDriver> queryDriver(SysDriver message);

	public PageInfo<SysDriver> queryDriver1(SysDriver driver);

	String saveMessage_New(SysMessage obj) throws Exception;

	PageInfo<SysDriver> queryDriver2(SysMessage message) throws Exception;

}
