package com.sysongy.poms.system.dao;

import java.util.List;
import java.util.Map;

import com.sysongy.poms.system.model.SysCashBack;

public interface SysCashBackMapper {
	
    int deleteByPrimaryKey(String sysCashBackId);

    int insert(SysCashBack record);

    int insertSelective(SysCashBack record);

    SysCashBack selectByPrimaryKey(String sysCashBackId);

    int updateByPrimaryKeySelective(SysCashBack record);

    int updateByPrimaryKey(SysCashBack record);
    
    List<SysCashBack> queryForPage(SysCashBack record);
    /**
     * 查询司机返现规则列表
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryCashBackList();

    List<SysCashBack> queryCashBackForCRM(SysCashBack record);

    List<SysCashBack> checkvalid(SysCashBack record);
    
    List<SysCashBack> queryCashBackByNumber(String sysCashBackNumber);
    
    SysCashBack findCashBackid(String cashBackNo);

    List<SysCashBack> gainProp(String cashBackNo, String level);

    Integer deleteByLevel(String sysCashBackNo, String level);

    List<SysCashBack> queryForCRMPage(SysCashBack record);
    
    /**
     * 最高返现规则列表
     */
    List<SysCashBack> queryMaxCashBack();
}