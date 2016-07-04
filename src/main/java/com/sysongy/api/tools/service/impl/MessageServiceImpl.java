package com.sysongy.api.tools.service.impl;

import com.sysongy.api.client.controller.model.ShortMessageInfoModel;
import com.sysongy.api.tools.service.MessageService;
import com.sysongy.api.tools.service.model.MessageGlobal;
import com.sysongy.api.tools.service.model.ShortMessage;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.util.*;
import com.sysongy.util.pojo.AliShortMessageBean;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * @FileName: MessageServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.tools.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月01日, 9:20
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public class MessageServiceImpl implements MessageService{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    RedisClientInterface redisClientImpl;
    public RedisClientInterface getRedisClientImpl() {
        return redisClientImpl;
    }
    public void setRedisClientImpl(RedisClientInterface redisClientImpl) {
        this.redisClientImpl = redisClientImpl;
    }

    @Override
    public AjaxJson sendMessage(ShortMessage shortMessage,HttpServletRequest request) {

        AjaxJson ajaxJson = new AjaxJson();
        if(shortMessage == null){
            if(shortMessage.getPhoneNumber() == null || "".equals(shortMessage.getPhoneNumber())){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("手机号为空！");
                return ajaxJson;
            }else if(shortMessage.getSmsTemplateCode() == null || "".equals(shortMessage.getSmsTemplateCode() )
            || shortMessage.getSmsFreeSignName() == null || "".equals(shortMessage.getSmsFreeSignName())
            || shortMessage.getSmsParamJson() == null || "".equals(shortMessage.getSmsParamJson())){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("系统参数传递有误！");
                return ajaxJson;
            }else{
                String phoneNumber = shortMessage.getPhoneNumber();
                if(checkIfFrequent(request, shortMessage.getPhoneNumber())){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("您发送短信的次数过于频繁，请稍后再试！！！");
                    return ajaxJson;
                }

                try
                {
                    //生成设置验证码
                    Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);
                    shortMessage.setCode(checkCode.toString());
                    //将手机号码与验证码存储到redis
                    redisClientImpl.addToCache(phoneNumber, checkCode.toString(), 60);
                    //发送短信
                    sendShortMessage(shortMessage);

                } catch (Exception e) {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_SEND_MSG_ERROR + e.getMessage());
                    logger.error("queryCardInfo error： " + e);
                }
            }
        }
        return ajaxJson;
    }

    /**
     * 司集短信接口
     *
     * @param shortMessage
     * @return
     */
    public String sendShortMessage(ShortMessage shortMessage) {
        logger.info("********************send the short message begin: telephone =" + shortMessage.getPhoneNumber() +"msgType=" + MessageGlobal.TemplateNumber.ACTIVITY + "*******************");
        String resp = null;
        TaobaoClient client = new DefaultTaobaoClient(MessageGlobal.SHORT_MESSAGE_URL, MessageGlobal.APP_KEY, MessageGlobal.SECRET_STRING);

        //设置短信发送信息
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        req.setSmsType(MessageGlobal.SMS_TYPE);//设置短信类型（默认为短信类型normal）
        if(!StringUtils.isEmpty(shortMessage.getExtend())){//设置传递参数
            req.setExtend(shortMessage.getExtend());
        }
        //用户需要设置参数
        req.setRecNum(shortMessage.getPhoneNumber());//设置电话号码
        req.setSmsTemplateCode(shortMessage.getSmsTemplateCode());//设置模板编号
        req.setSmsFreeSignName(shortMessage.getSmsFreeSignName());//设置模板签名
        req.setSmsParam(shortMessage.getSmsParamJson().toString());//设置模板参数

        //发送请求
        try {
            rsp = client.execute(req);
            resp = rsp.getBody();
        } catch (ApiException e) {
            logger.error("----------Send short message error: " + e + "----------");
        }
        logger.info("********************send the short message end*******************");
        return resp;
    }

    /**
     * 短信是否可以发送判断
     * @param request
     * @param mobilePhone
     * @return
     */
    private boolean checkIfFrequent(HttpServletRequest request, String mobilePhone){
        boolean bRet = false;
        String flag = "sign";
        if(mobilePhone == null || mobilePhone.equals("")){
            return false;
        }else{
            try {
                String ip = IPUtil.getIpAddress(request);
                Date catchIPTime = (Date)redisClientImpl.getFromCache(ip);
                if(catchIPTime == null){
                    redisClientImpl.addToCache(ip, new Date(), 60);
                } else {
                    if((new Date().getTime() - catchIPTime.getTime()) < 60000){     //IP地址发送过于频繁，同一分钟同一IP只允许发一次
                        return true;
                    }
                }

                ShortMessageInfoModel shortMessageInfo = (ShortMessageInfoModel)redisClientImpl.getFromCache(mobilePhone);
                if(shortMessageInfo == null){
                    ShortMessageInfoModel shortMessageInfoModel = new ShortMessageInfoModel();
                    shortMessageInfoModel.setCreateTime(new Date());
                    shortMessageInfoModel.setSendTimes(shortMessageInfoModel.getSendTimes() + 1);
                    redisClientImpl.addToCache(mobilePhone, shortMessageInfoModel, 300);
                } else {
                    if( shortMessageInfo.getSendTimes() < 10 ){
                        shortMessageInfo.setSendTimes(shortMessageInfo.getSendTimes() + 1);
                    } else {
                        return true;
                    }
                }
            } catch (Exception e) {
                logger.error("无法获取IP地址" + e);
            }
        }
        return bRet;
    }



}
