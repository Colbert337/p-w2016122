package com.sysongy.tcms.advance.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.model.TcTransferAccount;

import java.util.List;
import java.util.Map;

/**
 * @FileName: TcTransferAccountService
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月13日, 16:57
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface TcTransferAccountService {
    /**
     * 转账报表
     * @param transferAccount 转账对象
     * @return
     * @throws Exception
     */
    PageInfo<Map<String, Object>> queryTransferListPage(TcTransferAccount transferAccount) throws Exception;
}
