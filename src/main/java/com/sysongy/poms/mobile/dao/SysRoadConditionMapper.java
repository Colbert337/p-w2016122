package com.sysongy.poms.mobile.dao;

import java.util.List;
import java.util.Map;

import com.sysongy.poms.mobile.model.SysRoadCondition;

public interface SysRoadConditionMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(SysRoadCondition record);

    int updateByPrimaryKeySelective(SysRoadCondition record);

    int updateByPrimaryKey(SysRoadCondition record);
    /**
     * 上报路况 
     */
    int reportSysRoadCondition(SysRoadCondition record);
    /**
     * 获取路况列表 
     */
    List<Map<String, Object>> queryForPage(SysRoadCondition record);
    /**
     * 根据ID查询
     */
    SysRoadCondition selectByPrimaryKey(String id);
    /**
     * 取消路况 
     */
    int cancelSysRoadCondition(SysRoadCondition record);
}