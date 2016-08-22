package com.sysongy.poms.message.model;

import java.util.Date;

public class SysMessage {
    private String id;

    private String messageTitle;

    private String messageBody;

    private String messageTicker;

    private String messageGroup;

    private Date messageSendTime;

    private Date messageCreatedTime;

    private String operator;

    private String memo;

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
}