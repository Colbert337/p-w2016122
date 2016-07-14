package com.sysongy.tcms.advance.dao;

import com.sysongy.tcms.advance.model.TcTransferAccount;

import java.util.List;
import java.util.Map;

public interface TcTransferAccountMapper {
    int deleteByPrimaryKey(String tcTransferAccountId);

    int insert(TcTransferAccount record);

    int insertSelective(TcTransferAccount record);

    TcTransferAccount selectByPrimaryKey(String tcTransferAccountId);

    int updateByPrimaryKeySelective(TcTransferAccount record);

    int updateByPrimaryKey(TcTransferAccount record);
    /**
     * 转账报表
     * @param transferAccount 转账对象
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryTransferListPage(TcTransferAccount transferAccount) throws Exception;
}