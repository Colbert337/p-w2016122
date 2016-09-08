package com.sysongy.poms.message.model;

import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;

public class SysMessage extends BaseModel {

	private String id;

	private String device_token;

	public String getDevice_token() {
		return device_token;
	}

	public void setDevice_token(String device_token) {
		this.device_token = device_token;
	}

	private String driver_name;
	private String messageTitle;

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	private String messageBody;

	private String messageTicker;

	private String messageGroup;

	private String messageType;

	private Date messageSendTime;

	private Date messageCreatedTime;

	private String message_created_time_before;

	private String message_created_time_after;

	private String operator;

	private String memo;

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle == null ? null : messageTitle.trim();
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody == null ? null : messageBody.trim();
	}

	public String getMessageTicker() {
		return messageTicker;
	}

	public void setMessageTicker(String messageTicker) {
		this.messageTicker = messageTicker == null ? null : messageTicker.trim();
	}

	public String getMessageGroup() {
		return messageGroup;
	}

	public void setMessageGroup(String messageGroup) {
		this.messageGroup = messageGroup == null ? null : messageGroup.trim();
	}

	public Date getMessageSendTime() {
		return messageSendTime;
	}

	public void setMessageSendTime(Date messageSendTime) {
		this.messageSendTime = messageSendTime;
	}

	public Date getMessageCreatedTime() {
		return messageCreatedTime;
	}

	public void setMessageCreatedTime(Date messageCreatedTime) {
		this.messageCreatedTime = messageCreatedTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator == null ? null : operator.trim();
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public String getMessage_created_time_before() {
		return message_created_time_before;
	}

	public void setMessage_created_time_before(String message_created_time_before) {
		this.message_created_time_before = message_created_time_before;
	}

	public String getMessage_created_time_after() {
		return message_created_time_after;
	}

	public void setMessage_created_time_after(String message_created_time_after) {
		this.message_created_time_after = message_created_time_after;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

}