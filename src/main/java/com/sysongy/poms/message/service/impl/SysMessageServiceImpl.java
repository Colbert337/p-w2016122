package com.sysongy.poms.message.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.message.dao.SysMessageMapper;
import com.sysongy.poms.message.model.SysMessage;
import com.sysongy.poms.message.service.SysMessageService;
import com.sysongy.util.UUIDGenerator;

@Service
public class SysMessageServiceImpl implements SysMessageService {

	@Autowired
	private SysMessageMapper messageMapper;
	
	@Override
	public PageInfo<SysMessage> queryMessage(SysMessage record) throws Exception {
		
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<SysMessage> list = messageMapper.queryForPage(record);
		PageInfo<SysMessage> pageInfo = new PageInfo<SysMessage>(list);
		
		return pageInfo;
	}

	@Override
	public SysMessage queryMessageByPK(String messageid) throws Exception {
		return messageMapper.selectByPrimaryKey(messageid);
	}

	@Override
	public String saveMessage(SysMessage obj, String operation) throws Exception {
		obj.setId(UUIDGenerator.getUUID());
		obj.setMessageCreatedTime(new Date());
		obj.setMessageSendTime(new Date());
		return String.valueOf(messageMapper.insert(obj));
	}

	@Override
	public Integer delMessage(String messageid) throws Exception {
		return messageMapper.deleteByPrimaryKey(messageid);
	}

}
