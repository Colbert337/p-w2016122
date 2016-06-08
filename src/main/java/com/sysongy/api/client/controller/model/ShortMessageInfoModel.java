package com.sysongy.api.client.controller.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/6/8.
 */
public class ShortMessageInfoModel {

    private Integer sendTimes = 0;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(Integer sendTimes) {
        this.sendTimes = sendTimes;
    }
}
