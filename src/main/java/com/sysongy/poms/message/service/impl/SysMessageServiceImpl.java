package com.sysongy.poms.message.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
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
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.usysparam.dao.UsysparamMapper;
import com.sysongy.poms.usysparam.service.UsysparamService;
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

	@Autowired
	private UsysparamService paramservice;

	@Override
	public PageInfo<SysMessage> queryMessage(SysMessage record) throws Exception {

		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<SysMessage> list = messageMapper.queryForPage(record);
		PageInfo<SysMessage> pageInfo = new PageInfo<SysMessage>(list);

		return pageInfo;
	}

	@Override
	public SysMessage queryMessageByPK(SysMessage message) throws Exception {
		return messageMapper.selectByPrimaryKey(message);
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
		params.setProvince(obj.getProvince_name());
		obj.setMessageGroup("1000");
		int status =0;
		if (obj.getMessageType().equals(1)) {
			status = umeng.sendAndroidBroadcast(params);//设置广播
		}else{
			status = umeng.sendAndroidGroupcast(params);//设置组播
		}

		if (status == 200) {
			return String.valueOf(messageMapper.insert(obj));
		} else {
			throw new Exception("Umeng信息发送异常，请检查,code:" + status);
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
			params.setText(obj.getMessageBody());
			params.setTicker(obj.getMessageTicker());
			params.setTitle(obj.getMessageTitle());
			params.setText(obj.getMessageBody());
			params.setContent(obj.getContent());
			params.setDevice_tokens(token[i]);
			int status = umeng.sendAndroidUnicast(params);
			if (status != 200) {
				throw new Exception("Umeng信息发送异常，请检查,code:" + status);
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

	@Override
	public PageInfo<SysDriver> queryDriver2(SysMessage message) throws Exception {
		// TODO Auto-generated method stub

		SysMessage mes = queryMessageByPK(message);
		if (mes.getDriverName() != null) {
			List<String> str = new ArrayList<>();
			String mesId[] = mes.getDriverName().split(",");

			for (int i = 0; i < mesId.length; i++) {
				str.add(mesId[i]);
			}
			mes.setPageNum(message.getPageNum());
			mes.setPageSize(message.getPageSize());
			if (StringUtils.isEmpty(mes.getOrderby())) {
				mes.setOrderby("updated_date desc");
			}

			PageHelper.startPage(mes.getPageNum(), mes.getPageSize(), mes.getOrderby());
			List<SysDriver> list = driverMapper.queryForPage2(str);
			PageInfo<SysDriver> page = new PageInfo<>(list);
			return page;
		}
		return null;
	}

	@Override
	public PageInfo<Map<String, Object>> queryMsgListForPage(SysMessage record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Map<String, Object>> list = messageMapper.queryMsgListForPage(record);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		return pageInfo;
	}

	@Override
	public String saveMessage_New_Road(String content, SysRoadCondition road) throws Exception {

		SysDriver driver = new SysDriver();
		driver.setMobilePhone(road.getPublisherPhone());
		driver = driverMapper.queryDriverByMobilePhone(driver);
		SysMessage message = new SysMessage();

		if (null == driver || "".equals(driver.getDeviceToken())) {
			throw new Exception("用户Token为空，发送消息失败！");
		}
		message.setOperator("admin");
		message.setMessageType(1);

		message.setMessageBody(
				"路况类型：" + paramservice.queryUsysparamByCode("CONDITION_TYPE", road.getConditionType()).getMname()
						+ "\n路况位置：" + road.getAddress() + "\n审核结果：" + content + "");
		message.setContent(
				"路况类型：" + paramservice.queryUsysparamByCode("CONDITION_TYPE", road.getConditionType()).getMname()
						+ "\n路况位置：" + road.getAddress() + "\n审核结果：" + content);
		message.setDevice_token(driver.getDeviceToken());
		message.setDriver_name(driver.getSysDriverId());
		message.setDriverName(driver.getDeviceToken());
		message.setMessageGroup("999");
		message.setMemo("路况位置：" + road.getAddress() + "。路况详情：" + road.getConditionMsg());
		message.setMessageTicker("上报路况审核提醒");
		message.setMessageTitle("上报路况审核提醒");
		message.setMessageType(1);
		return saveMessage_New(message);
		// TODO Auto-generated method stub
	} 

	@Override
	public String saveMessageTransaction(String content, SysOrder order,String type) throws Exception {
		SysMessage message = new SysMessage();
		SysDriver driver = null;
		message.setOperator("admin");
		message.setMessageType(1);
		//充值
		if(type.equals("1")){
			driver = driverMapper.queryByPK(order.getDebitAccount());
		}else{//消费
			driver = driverMapper.queryByPK(order.getCreditAccount());
		}
		String deviceToken = driver.getDeviceToken();
		if(deviceToken!=null &&!"".equals(deviceToken)){
			message.setDevice_token(driver.getDeviceToken());
			message.setDriver_name(driver.getSysDriverId());
			message.setDriverName(driver.getDeviceToken());
			message.setMessageGroup("999");
			message.setMessageTicker("交易提醒");
			message.setMessageTitle("交易提醒");
			message.setMessageBody("您的账户于"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getOrderDate())+content+order.getCash()+"元");
			message.setContent("您的账户于"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getOrderDate())+content+order.getCash()+"元");
			return saveMessage_New(message);
		}else{
			return null;
		}
	}

	@Override
	public String sendMessageUploadRoad(SysDriver driver) throws Exception {
		String deviceToken = driver.getDeviceToken();
		if(deviceToken!=null &&!"".equals(deviceToken)){
			SysMessage message = new SysMessage();
			message.setOperator("admin");
			message.setMessageType(1);
			message.setDevice_token(driver.getDeviceToken());
			message.setDriver_name(driver.getSysDriverId());
			message.setDriverName(driver.getDeviceToken());
			message.setMessageGroup("999");
			message.setMessageTicker("上报路况提醒");
			message.setMessageTitle("上报路况提醒");
			message.setMessageBody("您于"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"上报路况信息成功，感谢您的参与！");
			message.setContent("您于"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"上报路况信息成功，感谢您的参与！");
			return saveMessage_New(message);
		}else{
			return null;
		}
	}
}
