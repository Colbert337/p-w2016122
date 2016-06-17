package com.sysongy.poms.liquid.dao;

import com.sysongy.poms.liquid.model.SysGasSource;

public interface SysGasSourceMapper {
    int deleteByPrimaryKey(String sysGasSourceId);

    int insert(SysGasSource record);

    int insertSelective(SysGasSource record);

    SysGasSource selectByPrimaryKey(String sysGasSourceId);

    int updateByPrimaryKeySelective(SysGasSource record);

    int updateByPrimaryKey(SysGasSource record);
}