package com.sysongy.poms.card.dao;

import com.sysongy.poms.card.model.GasCardProp;

public interface GasCardPropMapper {
    int deleteByPrimaryKey(String cardPropId);

    int insert(GasCardProp record);

    int insertSelective(GasCardProp record);

    GasCardProp selectByPrimaryKey(String cardPropId);

    int updateByPrimaryKeySelective(GasCardProp record);

    int updateByPrimaryKey(GasCardProp record);
}