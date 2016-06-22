package com.sysongy.poms.system.dao;

import com.sysongy.poms.system.model.SysOperationLog;

public interface SysOperationLogMapper {
    int deleteByPrimaryKey(String sysOperationLogId);

    int insert(SysOperationLog record);

    int insertSelective(SysOperationLog record);

    SysOperationLog selectByPrimaryKey(String sysOperationLogId);

    int updateByPrimaryKeySelective(SysOperationLog record);

    int updateByPrimaryKey(SysOperationLog record);
}