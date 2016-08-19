package com.sysongy.api.mobile.service;

import java.util.Map;

/**
 * @FileName: DealOrderService
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.mobile.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月19日, 16:33
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description: APP交易管理
 */
public interface MbDealOrderService {
    /**
     * 个人对个人转账
     * @param driverMap 转账参数
     * @return
     */
    int transferDriverToDriver(Map<String, Object> driverMap) throws Exception;
}
