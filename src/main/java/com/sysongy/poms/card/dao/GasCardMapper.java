package com.sysongy.poms.card.dao;

import com.sysongy.poms.card.model.GasCard;

public interface GasCardMapper {
    int deleteByPrimaryKey(String cardId);

    int insert(GasCard record);

    int insertSelective(GasCard record);

    GasCard selectByPrimaryKey(String cardId);

    int updateByPrimaryKeySelective(GasCard record);

    int updateByPrimaryKey(GasCard record);
}