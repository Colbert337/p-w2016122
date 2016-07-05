package com.sysongy.api.tools.service.model;

/**
 * @FileName: MessageGloble
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.tools.service.model
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月01日, 10:46
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public class MessageGlobal {

    /**
     * 接口调用参数
     */
    public static final String SHORT_MESSAGE_URL = "http://gw.api.taobao.com/router/rest";
    public static final String APP_KEY = "23281499";
    public static final String SECRET_STRING = "dcd3f7c3f908c3751ca817a34ae7fd83";
    public static final String SMS_TYPE = "normal";

    /**
     * 短信签名
     */
    public interface ShortMessageSign {
        public static final String ACTIVITY ="活动验证";
        public static final String CHANGE ="变更验证";
        public static final String LOGIN ="登录验证";
        public static final String REGISTER ="注册验证";
        public static final String AUTHENT ="身份验证";
    }

    /**
     * 模板编号
     */
    public interface TemplateNumber {
        public static final String ACTIVITY ="活动验证";
        public static final String CHANGE ="变更验证";
        public static final String LOGIN ="登录验证";
        public static final String REGISTER ="注册验证";
        public static final String AUTHENT ="身份验证";
    }


}
