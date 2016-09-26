package com.sysongy.poms.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.sysongy.poms.mobile.dao.MbStatisticsMapper;
import com.sysongy.poms.mobile.model.MbStatistics;
import com.sysongy.poms.mobile.service.MbStatisticsService;

public class MbStatisticsServiceImpl implements MbStatisticsService{
    @Autowired
    private MbStatisticsMapper mbStatisticsMapper;
    
	@Override
	public MbStatistics queryMbStatistics(MbStatistics record) {
		return mbStatisticsMapper.queryMbStatistics(record);
	}

	@Override
	public int insertSelective(MbStatistics record) {
		return mbStatisticsMapper.insertSelective(record);
	}

}
