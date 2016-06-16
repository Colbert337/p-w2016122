package com.sysongy.poms.system.dao;

import com.sysongy.poms.system.model.SysGasPosition;

public interface SysGasPositionMapper {
    int deleteByPrimaryKey(String sysGasPositionId);

    int insert(SysGasPosition record);

    int insertSelective(SysGasPosition record);

    SysGasPosition selectByPrimaryKey(String sysGasPositionId);

    int updateByPrimaryKeySelective(SysGasPosition record);

    int updateByPrimaryKey(SysGasPosition record);
}