package com.sysongy.poms.card.dao;

import java.util.List;

import com.sysongy.poms.card.model.GasCard;

public interface GasCardMapper {
	
    int deleteByPrimaryKey(String cardId);

    int insert(GasCard record);

    int insertSelective(GasCard record);
    
    int insertBatch(List<GasCard> recordlist);

    GasCard selectByPrimaryKey(String cardId);
    
    GasCard selectByCardNo(String cardNo);

    int updateByPrimaryKeySelective(GasCard record);

    int updateByPrimaryKey(GasCard record);
    
    List<GasCard> queryForPage(GasCard record);
}