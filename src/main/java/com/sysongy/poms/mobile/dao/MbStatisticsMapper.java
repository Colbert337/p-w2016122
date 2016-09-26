package com.sysongy.poms.mobile.dao;

import com.sysongy.poms.mobile.model.MbStatistics;

public interface MbStatisticsMapper {
    int deleteByPrimaryKey(String mbStatisticsId);

    int insert(MbStatistics record);

    int insertSelective(MbStatistics record);

    MbStatistics selectByPrimaryKey(String mbStatisticsId);

    int updateByPrimaryKeySelective(MbStatistics record);

    int updateByPrimaryKey(MbStatistics record);
}