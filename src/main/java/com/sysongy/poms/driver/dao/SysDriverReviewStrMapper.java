package com.sysongy.poms.driver.dao;

import java.util.List;

import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.model.SysDriverReviewStr;

public interface SysDriverReviewStrMapper {
    int deleteByPrimaryKey(String sysDriverId);

    int insert(SysDriverReviewStr record);

    int insertSelective(SysDriverReviewStr record);

    SysDriverReviewStr selectByPrimaryKey(String sysDriverId);

    int updateByPrimaryKeySelective(SysDriverReviewStr record);

    int updateByPrimaryKey(SysDriverReviewStr record);
    
    List<SysDriverReviewStr> queryForPage(SysDriverReviewStr record);
}