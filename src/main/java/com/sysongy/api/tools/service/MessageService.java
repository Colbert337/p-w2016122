package com.sysongy.api.tools.service;

import com.sysongy.api.tools.service.model.ShortMessage;
import com.sysongy.poms.base.model.AjaxJson;

import javax.servlet.http.HttpServletRequest;

/**
 * @FileName: MessageService
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.tools.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月01日, 9:19
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface MessageService {

    /**
     * 发送短信
     * @param shortMessage
     * @return
     */
    AjaxJson sendMessage(ShortMessage shortMessage,HttpServletRequest request);
}
