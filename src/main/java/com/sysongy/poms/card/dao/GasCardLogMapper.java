package com.sysongy.poms.card.dao;

import com.sysongy.poms.card.model.GasCardLog;

public interface GasCardLogMapper {
    int deleteByPrimaryKey(String cardLogId);

    int insert(GasCardLog record);

    int insertSelective(GasCardLog record);

    GasCardLog selectByPrimaryKey(String cardLogId);

    int updateByPrimaryKeySelective(GasCardLog record);

    int updateByPrimaryKey(GasCardLog record);
}