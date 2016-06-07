package com.sysongy.poms.driver.dao;

import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.gastation.model.Gastation;

import java.util.List;

public interface SysDriverMapper {
    int deleteByPrimaryKey(String sysDriverId);

    int insert(SysDriver record);

    int insertSelective(SysDriver record);

    SysDriver selectByPrimaryKey(String sysDriverId);

    int updateByPrimaryKeySelective(SysDriver record);

    int updateByPrimaryKey(SysDriver record);

    List<SysDriver> queryForPage(SysDriver record);
}