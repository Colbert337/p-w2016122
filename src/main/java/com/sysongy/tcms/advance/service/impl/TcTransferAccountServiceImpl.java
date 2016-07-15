package com.sysongy.tcms.advance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.dao.TcTransferAccountMapper;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcTransferAccount;
import com.sysongy.tcms.advance.service.TcTransferAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @FileName: TcTransferAccountService
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月13日, 16:57
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class TcTransferAccountServiceImpl implements TcTransferAccountService{
    @Autowired
    TcTransferAccountMapper tcTransferAccountMapper;
    /**
     * 转账报表
     * @param transferAccount 转账对象
     * @return
     * @throws Exception
     */
    public PageInfo<Map<String, Object>> queryTransferListPage(TcTransferAccount transferAccount) throws Exception{
        List<Map<String, Object>> transferList = new ArrayList<>();
        if(transferAccount != null ){
            PageHelper.startPage(transferAccount.getPageNum(),transferAccount.getPageSize(),transferAccount.getOrderby());
            transferList = tcTransferAccountMapper.queryTransferListPage(transferAccount);
            PageInfo<Map<String, Object>> transferPageInfo = new PageInfo<>(transferList);

            return transferPageInfo;
        }else{
            return null;
        }

    }
}
