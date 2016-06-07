package com.sysongy.poms.card.dao;

import java.util.List;

import com.sysongy.poms.card.model.GasCardLog;

public interface GasCardLogMapper {
	
    int deleteByPrimaryKey(String cardLogId);

    int insert(GasCardLog record);
    
    int insertBatch(List<GasCardLog> recordlist);

    int insertSelective(GasCardLog record);

    GasCardLog selectByPrimaryKey(String cardLogId);

    int updateByPrimaryKeySelective(GasCardLog record);

    int updateByPrimaryKey(GasCardLog record);
    
    List<GasCardLog> queryForPage(GasCardLog record);
}