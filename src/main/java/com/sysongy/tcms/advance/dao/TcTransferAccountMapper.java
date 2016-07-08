package com.sysongy.tcms.advance.dao;

import com.sysongy.tcms.advance.model.TcTransferAccount;

public interface TcTransferAccountMapper {
    int deleteByPrimaryKey(String tcTransferAccountId);

    int insert(TcTransferAccount record);

    int insertSelective(TcTransferAccount record);

    TcTransferAccount selectByPrimaryKey(String tcTransferAccountId);

    int updateByPrimaryKeySelective(TcTransferAccount record);

    int updateByPrimaryKey(TcTransferAccount record);
}