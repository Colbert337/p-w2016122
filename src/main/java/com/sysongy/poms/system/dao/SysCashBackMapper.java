package com.sysongy.poms.system.dao;

import java.util.List;

import com.sysongy.poms.system.model.SysCashBack;

public interface SysCashBackMapper {
	
    int deleteByPrimaryKey(String sysCashBackId);

    int insert(SysCashBack record);

    int insertSelective(SysCashBack record);

    SysCashBack selectByPrimaryKey(String sysCashBackId);

    int updateByPrimaryKeySelective(SysCashBack record);

    int updateByPrimaryKey(SysCashBack record);
    
    List<SysCashBack> queryForPage(SysCashBack record);
    
    List<SysCashBack> checkvalid(SysCashBack record);
}