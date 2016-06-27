package com.sysongy.poms.driver.dao;

import com.sysongy.poms.driver.model.SysDriverReviewStr;

public interface SysDriverReviewStrMapper {
    int deleteByPrimaryKey(String sysDriverId);

    int insert(SysDriverReviewStr record);

    int insertSelective(SysDriverReviewStr record);

    SysDriverReviewStr selectByPrimaryKey(String sysDriverId);

    int updateByPrimaryKeySelective(SysDriverReviewStr record);

    int updateByPrimaryKey(SysDriverReviewStr record);
}