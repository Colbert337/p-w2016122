package com.sysongy.poms.mobile.dao;

import java.util.List;

import com.sysongy.poms.mobile.model.SysRoadCondition;

public interface SysRoadConditionMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRoadCondition record);

    int insertSelective(SysRoadCondition record);

    SysRoadCondition selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRoadCondition record);

    int updateByPrimaryKey(SysRoadCondition record);
    
    List< SysRoadCondition> queryForPage(SysRoadCondition record);
}