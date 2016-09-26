package com.sysongy.poms.mobile.service;

import com.sysongy.poms.mobile.model.MbStatistics;

public interface MbStatisticsService {
	//查询
	MbStatistics queryMbStatistics(MbStatistics record);
	//添加记录
	int insertSelective(MbStatistics record);
}
