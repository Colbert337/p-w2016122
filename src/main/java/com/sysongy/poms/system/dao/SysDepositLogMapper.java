package com.sysongy.poms.system.dao;

import com.sysongy.poms.system.model.SysDepositLog;

public interface SysDepositLogMapper {
    int deleteByPrimaryKey(String sysDepositLogId);

    int insert(SysDepositLog record);

    int insertSelective(SysDepositLog record);

    SysDepositLog selectByPrimaryKey(String sysDepositLogId);

    int updateByPrimaryKeySelective(SysDepositLog record);

    int updateByPrimaryKey(SysDepositLog record);
}