package com.sysongy.poms.mobile.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sysongy.poms.mobile.model.SysRoadCondition;

public interface SysRoadConditionMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(SysRoadCondition record);

    int updateByPrimaryKeySelective(SysRoadCondition record);

    int updateByPrimaryKey(SysRoadCondition record);
    
    List< SysRoadCondition> queryForPage(SysRoadCondition record);
    /**
     * 上报路况 
     */
    int reportSysRoadCondition(SysRoadCondition record);
    /**
     * 获取路况列表 
     */
    List<Map<String, Object>> queryForPageMap(SysRoadCondition record);
    /**
     * 根据ID查询
     */
    SysRoadCondition selectByPrimaryKey(String id);
    /**
     * 取消路况 
     */
    int cancelSysRoadCondition(SysRoadCondition record);

	int updateByPrimaryKeyToCheck(SysRoadCondition road);

	List<SysRoadCondition> queryRoadId();

    /**
     * 获取限高限重路况列表
     * @return
     */
    List<SysRoadCondition> queryHighWeightRoadId();

	List<SysRoadCondition> queryAll();

	List<SysRoadCondition> queryForRedis();

	List<SysRoadCondition> queryForExcel(SysRoadCondition record);
	
	/**
	 * 根据电话和积分周期查询积分数量
	 * @param HashMap
	 * @return
	 */
	List<HashMap<String,String>> queryConditionByPhone(HashMap<String,String> HashMap); 
}