package com.sysongy.poms.message.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.api.umeng.push.model.CommonParams;
import com.sysongy.api.util.UmengUtil;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.message.dao.SysMessageMapper;
import com.sysongy.poms.message.model.SysMessage;
import com.sysongy.poms.message.service.SysMessageService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.UUIDGenerator;

@Service
public class SysMessageServiceImpl implements SysMessageService {

	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

	@Autowired
	private SysMessageMapper messageMapper;

	@Autowired
	private SysDriverMapper driverMapper;

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
	public String saveMessage(SysMessage obj, String token) throws Exception {

		obj.setId(UUIDGenerator.getUUID());
		obj.setMessageCreatedTime(new Date());
		obj.setMessageSendTime(new Date());

		UmengUtil umeng = new UmengUtil(prop.get("app_key").toString(), prop.get("app_master_secret").toString());

		CommonParams params = new CommonParams();

		params.setTicker(obj.getMessageTicker());
		params.setTitle(obj.getMessageTitle());
		params.setText(obj.getMessageBody());
		params.setContent(obj.getContent());
		params.setDevice_tokens(token);
		obj.setMessageGroup("1000");
		int status = umeng.sendAndroidBroadcast(params);

		if (status == 200) {
			return String.valueOf(messageMapper.insert(obj));
		} else {
			throw new Exception("Umeng信息发送异常，请检查");
		}

	}

	@Override
	public String saveMessage_New(SysMessage obj) throws Exception {

		obj.setId(UUIDGenerator.getUUID());
		obj.setMessageCreatedTime(new Date());
		obj.setMessageSendTime(new Date());
		String token[] = obj.getDevice_token().split(",");
		for (int i = 0; i < token.length; i++) {
			UmengUtil umeng = new UmengUtil(prop.get("app_key").toString(), prop.get("app_master_secret").toString());

			CommonParams params = new CommonParams();

			params.setTicker(obj.getMessageTicker());
			params.setTitle(obj.getMessageTitle());
			params.setText(obj.getMessageBody());
			params.setContent(obj.getContent());
			params.setDevice_tokens(token[i]);
			int status = umeng.sendAndroidUnicast(params);
			if (status != 200) {
				throw new Exception("Umeng信息发送异常，请检查");
			}
		}
		return String.valueOf(messageMapper.insert(obj));
	}

	@Override
	public Integer delMessage(String messageid) throws Exception {
		return messageMapper.deleteByPrimaryKey(messageid);
	}

	@Override
	public PageInfo<SysDriver> queryDriver(SysDriver message) {
		// TODO Auto-generated method stub
		PageHelper.startPage(message.getPageNum(), message.getPageSize(), message.getOrderby());
		List<SysDriver> list = driverMapper.queryForPage(message);
		PageInfo<SysDriver> page = new PageInfo<>(list);
		return page;
	}

	@Override
	public PageInfo<SysDriver> queryDriver1(SysDriver message) {
		// TODO Auto-generated method stub
		PageHelper.startPage(message.getPageNum(), message.getPageSize(), message.getOrderby());
		List<SysDriver> list = driverMapper.queryForPage1(message);
		PageInfo<SysDriver> page = new PageInfo<>(list);
		return page;
	}

}
