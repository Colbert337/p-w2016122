package com.sysongy.api.tools.service.model;

import net.sf.json.JSONObject;

/**
 * @FileName: ShortMessage
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.tools.service.model
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月01日, 11:16
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public class ShortMessage {

    /**
     * 接收短息的手机号码
     */
    private String phoneNumber;
    /**
     * 验证码
     */
    private String code;
    /**
     *支付密码
     */
    private String payCode;
    /**
     *地点/实体（运输公司/气站）
     */
    private String station;
    /**
     *操作时间
     */
    private String operateDate;
    /**
     *入账金额
     */
    private String accountedForAmount ;
    /**
     *返现金额
     */
    private String cashBackAmount;
    /**
     *入账流水号
     */
    private String accountedForNo;
    /**
     *账户余额
     */
    private String accountedBalance;
    /**
     *出账金额
     */
    private String accountedOutAmount;
    /**
     *出账流水号
     */
    private String accountedOutNo;
    /**
     *出账对象
     */
    private String accountedOutObj;
    /**
     *入账对象
     */
    private String accountedForObj;
    /**
     *车牌号
     */
    private String plateNumber;
    /**
     *账户名称
     */
    private String accountName;
    /**
     *客服电话
     */
    private String servicePhone;
    /**
     *会员卡号
     */
    private String memberNumber;
    /**
     *会员账号
     */
    private String memberAccount;
    /**
     *操作（转账、消费、预付款充值）
     */
    private String operate;

    /**
     * 文本内容
     */
    private String content;


    /**************************************接口参数**************************************/
    /**
     *回传参数
     */
    private String extend;
    /**
     *短信类型，传入值请填写normal
     */
    private String smsType = "normal";
    /**
     *短信签名
     */
    private String smsFreeSignName;
    /**
     *短信模板参数
     */
    private String smsParam;
    /**
     *短信模板参数json格式
     */
    private JSONObject smsParamJson;
    /**
     *短信接收号码
     */
    private String recNum;
    /**
     *短信模板编号
     */
    private String smsTemplateCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(String operateDate) {
        this.operateDate = operateDate;
    }

    public String getAccountedForAmount() {
        return accountedForAmount;
    }

    public void setAccountedForAmount(String accountedForAmount) {
        this.accountedForAmount = accountedForAmount;
    }

    public String getCashBackAmount() {
        return cashBackAmount;
    }

    public void setCashBackAmount(String cashBackAmount) {
        this.cashBackAmount = cashBackAmount;
    }

    public String getAccountedForNo() {
        return accountedForNo;
    }

    public void setAccountedForNo(String accountedForNo) {
        this.accountedForNo = accountedForNo;
    }

    public String getAccountedBalance() {
        return accountedBalance;
    }

    public void setAccountedBalance(String accountedBalance) {
        this.accountedBalance = accountedBalance;
    }

    public String getAccountedOutAmount() {
        return accountedOutAmount;
    }

    public void setAccountedOutAmount(String accountedOutAmount) {
        this.accountedOutAmount = accountedOutAmount;
    }

    public String getAccountedOutNo() {
        return accountedOutNo;
    }

    public void setAccountedOutNo(String accountedOutNo) {
        this.accountedOutNo = accountedOutNo;
    }

    public String getAccountedOutObj() {
        return accountedOutObj;
    }

    public void setAccountedOutObj(String accountedOutObj) {
        this.accountedOutObj = accountedOutObj;
    }

    public String getAccountedForObj() {
        return accountedForObj;
    }

    public void setAccountedForObj(String accountedForObj) {
        this.accountedForObj = accountedForObj;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSmsFreeSignName() {
        return smsFreeSignName;
    }

    public void setSmsFreeSignName(String smsFreeSignName) {
        this.smsFreeSignName = smsFreeSignName;
    }

    public String getSmsParam() {
        return smsParam;
    }

    public void setSmsParam(String smsParam) {
        this.smsParam = smsParam;
    }

    public JSONObject getSmsParamJson() {
        return smsParamJson;
    }

    public void setSmsParamJson(JSONObject smsParamJson) {
        this.smsParamJson = smsParamJson;
    }

    public String getRecNum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
