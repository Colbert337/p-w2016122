package com.sysongy.poms.system.dao;

import com.sysongy.poms.system.model.SysCashBack;

public interface SysCashBackMapper {
    int deleteByPrimaryKey(String sysCashBackId);

    int insert(SysCashBack record);

    int insertSelective(SysCashBack record);

    SysCashBack selectByPrimaryKey(String sysCashBackId);

    int updateByPrimaryKeySelective(SysCashBack record);

    int updateByPrimaryKey(SysCashBack record);
}