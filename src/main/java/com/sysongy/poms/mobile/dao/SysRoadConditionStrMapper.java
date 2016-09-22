package com.sysongy.poms.mobile.dao;

import java.util.List;

import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.model.SysRoadConditionStr;

public interface SysRoadConditionStrMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRoadConditionStr record);

    int insertSelective(SysRoadConditionStr record);

    SysRoadConditionStr selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRoadConditionStr record);

    int updateByPrimaryKey(SysRoadConditionStr record);

	List<SysRoadConditionStr> queryForPageForRoadId(String id);
}