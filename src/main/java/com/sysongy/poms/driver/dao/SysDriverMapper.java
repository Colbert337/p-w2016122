package com.sysongy.poms.driver.dao;

import com.sysongy.poms.driver.model.SysDriver;

public interface SysDriverMapper {
    int deleteByPrimaryKey(String sysDriverId);

    int insert(SysDriver record);

    int insertSelective(SysDriver record);

    SysDriver selectByPrimaryKey(String sysDriverId);

    int updateByPrimaryKeySelective(SysDriver record);

    int updateByPrimaryKey(SysDriver record);
}