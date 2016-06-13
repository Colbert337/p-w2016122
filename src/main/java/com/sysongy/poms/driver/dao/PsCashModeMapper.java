package com.sysongy.poms.driver.dao;

import com.sysongy.poms.driver.model.PsCashMode;

public interface PsCashModeMapper {
    int deleteByPrimaryKey(String psCashModeId);

    int insert(PsCashMode record);

    int insertSelective(PsCashMode record);

    PsCashMode selectByPrimaryKey(String psCashModeId);

    int updateByPrimaryKeySelective(PsCashMode record);

    int updateByPrimaryKey(PsCashMode record);
}