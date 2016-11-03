package com.sysongy.util;

import com.sysongy.util.pojo.AliShortMessageBean;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/26.
 */
public class AliShortMessage {

    private static Logger logger = LoggerFactory.getLogger(AliShortMessage.class);

    public static final String SHORT_MESSAGE_URL = "http://gw.api.taobao.com/router/rest";

    public static final String APP_KEY = "23281499";

    public static final String SECRET_STRING = "dcd3f7c3f908c3751ca817a34ae7fd83";

    public enum SHORT_MESSAGE_TYPE{
        USER_REGISTER,                //用户注册
        USER_VALIDATION,              //身份验证
        USER_LOGIN_CONFIRM,           //登陆确认
        USER_LOGIN_ERROR,             //登陆异常
        USER_CHANGE_PASSWORD,         //用户修改密码
        USER_CHANGE_PROFILE,          //用户修改信息
        ACCOUNT_RECEIVE_MONEY,         //系统到账通知
        DRIVER_CONSUME,                //司机消费
        DRIVER_CHARGE,                 //司机充值
        DRIVER_CHARGE_BACKCASH,                 //司机充值即返现 11.1 王昭
        DRIVER_HEDGE,                  //司机转账
        CARD_FROZEN,                            //账户卡冻结模板
        CARD_THAW,                            //账户卡解冻模板
        SELF_CHARGE_CONSUME_PREINPUT,           //个人转账消费、预付款充值
        TRANSPORTION_TRANSFER_SELF_CHARGE,      //车队转账，个人充值
        REMIND_BALANCE,                         //预付款余额提醒
        GAS_STATION_FROZEN,                     //加注站平台冻结
        TEAM_LEADER_REMIND,                     //队长日报
        DRIVER_AUDIT_SUCCESS,                   //司机实名认证审核
        DRIVER_REGISTER_SUCCESS,                //司机注册成功
        DRIVER_BATCH_REGISTER,                  //运维平台批量注册司机
        VEHICLE_CREATED,                        //车辆创建
        DRIVER_CHECKCODE_PASSWORD_MODIFY,       //司机手机验证码/支付密码修改
        DRIVER_CONSUME_SUCCESSFUL,
    }

    /**
     * 司集短信接口
     * TODO 后期可能会支持多个平台的短信功能以增加效率，需要重构，使用说明请参考Main方法实现
     * @param aliShortMessageBean
     * @param msgType
     * @return
     */
    public static String sendShortMessage(AliShortMessageBean aliShortMessageBean, SHORT_MESSAGE_TYPE msgType) {
        logger.info("********************send the short message begin: telephone =" + aliShortMessageBean.getSendNumber() +
                "msgType=" + msgType.toString() + "*******************");
        String resp = null;
        TaobaoClient client = new DefaultTaobaoClient(SHORT_MESSAGE_URL, APP_KEY, SECRET_STRING);
        AlibabaAliqinFcSmsNumSendRequest req = createSMSRequest(aliShortMessageBean, msgType);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
            resp = rsp.getBody();
        } catch (ApiException e) {
            logger.error("----------Send short message error: " + e + "----------");
        }
        logger.info("********************send the short message end*******************");
        return resp;
    }

    private static AlibabaAliqinFcSmsNumSendRequest createSMSRequest(AliShortMessageBean aliShortMessageBean, SHORT_MESSAGE_TYPE msgType){
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setRecNum(aliShortMessageBean.getSendNumber());
        if(!StringUtils.isEmpty(aliShortMessageBean.getExtent())){
            req.setExtend(aliShortMessageBean.getExtent());
        }
        switch (msgType){
            case USER_REGISTER:
                req.setSmsTemplateCode("SMS_3030411");
                req.setSmsFreeSignName("注册验证");
                req.setSmsParamString("{\"code\":\"" + aliShortMessageBean.getCode() + "\",\"product\":\""
                        + aliShortMessageBean.getProduct() + "\"}");
                break;
            case USER_VALIDATION:
                req.setSmsTemplateCode("SMS_3030414");
                req.setSmsFreeSignName("身份验证");
                req.setSmsParamString("{\"code\":\"" + aliShortMessageBean.getCode() + "\",\"product\":\""
                        + aliShortMessageBean.getProduct() + "\"}");
                break;
            case USER_LOGIN_CONFIRM:
                req.setSmsTemplateCode("SMS_3030413");
                req.setSmsFreeSignName("登录验证");
                req.setSmsParamString("{\"code\":\"" + aliShortMessageBean.getCode() + "\",\"product\":\""
                        + aliShortMessageBean.getProduct() + "\"}");
                break;
            case USER_LOGIN_ERROR:
                req.setSmsTemplateCode("SMS_3030412");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"code\":\"" + aliShortMessageBean.getCode() + "\",\"product\":\""
                        + aliShortMessageBean.getProduct() + "\"}");
                break;
            case USER_CHANGE_PASSWORD:
                req.setSmsTemplateCode("SMS_3030409");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"code\":\"" + aliShortMessageBean.getCode() + "\",\"product\":\""
                        + aliShortMessageBean.getProduct() + "\"}");
                break;
            case USER_CHANGE_PROFILE:
                req.setSmsTemplateCode("SMS_3030408");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"code\":\"" + aliShortMessageBean.getCode() + "\",\"product\":\""
                        + aliShortMessageBean.getProduct() + "\"}");
                break;
            case DRIVER_CONSUME:
                req.setSmsTemplateCode("SMS_11560787");
                req.setSmsFreeSignName("身份验证");
                req.setSmsParamString("{\"code\":\"" + aliShortMessageBean.getCode() + "\"}");
                break;
            case DRIVER_CONSUME_SUCCESSFUL:
                req.setSmsTemplateCode("SMS_3100513");
                req.setSmsFreeSignName("司集科技");
                String msg = "{\"account\":\"" + aliShortMessageBean.getAccountNumber() + "\",\"time\":\""
                        + aliShortMessageBean.getCreateTime() + "\",\"out_money\":\"" + aliShortMessageBean.getSpentMoney()
                        + "\",\"balance\":\"" + aliShortMessageBean.getBalance()
                        + "\"}";
                req.setSmsParamString(msg);
                break;
            case DRIVER_CHARGE:
                req.setSmsTemplateCode("SMS_4421119");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"account\":\"" + aliShortMessageBean.getAccountNumber() + "\",\"time\":\""
                        + aliShortMessageBean.getCreateTime() + "\",\"money\":\"" + aliShortMessageBean.getSpentMoney()
                        + "\"}");
                break;
            case DRIVER_CHARGE_BACKCASH:
                req.setSmsTemplateCode("SMS_12140738");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"time\":\""+ aliShortMessageBean.getCreateTime() 
                		+ "\",\"string\":\"充值\",\"money\":\"" + aliShortMessageBean.getSpentMoney()
                        + "\",\"backCash\":\"" + aliShortMessageBean.getBackCash()
                        + "\",\"money1\":\"" + aliShortMessageBean.getBalance()
                        + "\"}");
                break;                
            case DRIVER_HEDGE:
                req.setSmsTemplateCode("SMS_3030408");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"code\":\"" + aliShortMessageBean.getCode() + "\",\"product\":\""
                        + aliShortMessageBean.getProduct() + "\"}");
                break;
            case CARD_FROZEN:
                req.setSmsTemplateCode("SMS_11550962");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"string\":\"" + aliShortMessageBean.getString() + "\",\"code\":\""
                        + aliShortMessageBean.getCode() + "\",\"time\":\""
                        + aliShortMessageBean.getTime() + "\"}");
                break;
            case CARD_THAW:
                req.setSmsTemplateCode("SMS_12885750");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"string\":\"" + aliShortMessageBean.getString() + "\",\"code\":\""
                        + aliShortMessageBean.getCode() + "\",\"time\":\""
                        + aliShortMessageBean.getTime() + "\"}");
                break;
            case SELF_CHARGE_CONSUME_PREINPUT:
                req.setSmsTemplateCode("SMS_12200990");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"time\":\"" + aliShortMessageBean.getTime() + "\",\"string\":\""
                        + aliShortMessageBean.getString() + "\",\"money\":\""
                        + aliShortMessageBean.getMoney()  + "\",\"money1\":\""
                        + aliShortMessageBean.getMoney1() + "\"}");
                break;
            case TRANSPORTION_TRANSFER_SELF_CHARGE:
                req.setSmsTemplateCode("SMS_12140738");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"time\":\"" + aliShortMessageBean.getTime() + "\",\"string\":\""
                        + aliShortMessageBean.getString() + "\",\"money\":\""
                        + aliShortMessageBean.getMoney() + "\",\"backCash\":\""
                        + aliShortMessageBean.getBackCash()  + "\",\"money1\":\""
                        + aliShortMessageBean.getMoney1() + "\"}");
                break;
            case REMIND_BALANCE:
                req.setSmsTemplateCode("SMS_11530947");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"money\":\"" + aliShortMessageBean.getName() + "\"}");
                break;
            case GAS_STATION_FROZEN:
                req.setSmsTemplateCode("SMS_12216006");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"name\":\"" + aliShortMessageBean.getName() + "\"}");
                break;
            case DRIVER_AUDIT_SUCCESS:
                req.setSmsTemplateCode("SMS_11550963");
                req.setSmsFreeSignName("变更验证");
                req.setSmsParamString("{\"string\":\"" + aliShortMessageBean.getString() + "\"}");
                break;
            case DRIVER_REGISTER_SUCCESS:
                req.setSmsTemplateCode("SMS_11480893");
                req.setSmsFreeSignName("变更验证");
                break;
            case DRIVER_BATCH_REGISTER:
                req.setSmsTemplateCode("SMS_12151038");
                req.setSmsFreeSignName("变更验证");
                break;
            case VEHICLE_CREATED:
                req.setSmsTemplateCode("SMS_12916082");
                req.setSmsFreeSignName("司集科技");
                req.setSmsParamString("{\"license\":\"" + aliShortMessageBean.getLicense() + "\",\"code\":\""
                        + aliShortMessageBean.getCode() + "\",\"string\":\""
                        + aliShortMessageBean.getString() + "\"}");
                break;
            case ACCOUNT_RECEIVE_MONEY:
                req.setSmsTemplateCode("SMS_4415951");
                req.setSmsFreeSignName("司集科技");
                req.setSmsParamString("{\"account\":\"" + aliShortMessageBean.getAccountNumber() + "\",\"time\":\""
                        + aliShortMessageBean.getCreateTime() + "\"" + ",\"money\":\""
                        + aliShortMessageBean.getTotalPrice() + "\""+ "}");
                break;
            case DRIVER_CHECKCODE_PASSWORD_MODIFY:
                req.setSmsTemplateCode("SMS_12255408");
                req.setSmsFreeSignName("司集科技");
                req.setSmsParamString("\",\"code\":\"" + aliShortMessageBean.getCode() + "\",\"string\":\""
                        + aliShortMessageBean.getString() + "\"}");
                break;
            default:
                break;
        }
        return req;
    }

    /**
     * @paramrec_num
     * 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。示例：18600000000,13911111111,13322222222
     */
    public static void main(String[] args){
        Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);
        AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
        aliShortMessageBean.setSendNumber("18392396707");
        aliShortMessageBean.setExtent("test");   //消息返回时自带参数，暂时不需要
        aliShortMessageBean.setCode(checkCode.toString());
        //身份等各种验证等等
        aliShortMessageBean.setCode(aliShortMessageBean.getCode());
        aliShortMessageBean.setProduct("司集能源科技平台");
        aliShortMessageBean.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        aliShortMessageBean.setString("收到转账：");
        aliShortMessageBean.setMoney("10");
        aliShortMessageBean.setMoney1("20");
        sendShortMessage(aliShortMessageBean, SHORT_MESSAGE_TYPE.SELF_CHARGE_CONSUME_PREINPUT);
        //到账通知
        aliShortMessageBean.setAccountNumber("18392396707");
        aliShortMessageBean.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        aliShortMessageBean.setTotalPrice("2000");
//        sendShortMessage(aliShortMessageBean, SHORT_MESSAGE_TYPE.ACCOUNT_RECEIVE_MONEY);
    }
}
