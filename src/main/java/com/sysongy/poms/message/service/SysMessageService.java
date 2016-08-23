package com.sysongy.poms.message.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.message.model.SysMessage;

public interface SysMessageService {
	
	public PageInfo<SysMessage> queryMessage(SysMessage obj) throws Exception;
	
	public SysMessage queryMessageByPK(String messageid) throws Exception;
	
	public String saveMessage(SysMessage obj,  String operation) throws Exception;
	
	public Integer delMessage(String messageid) throws Exception;

}
