package com.sysongy.poms.driver.dao;

import com.sysongy.poms.driver.model.PsCashSet;

public interface PsCashSetMapper {
    int deleteByPrimaryKey(String psCashSetId);

    int insert(PsCashSet record);

    int insertSelective(PsCashSet record);

    PsCashSet selectByPrimaryKey(String psCashSetId);

    int updateByPrimaryKeySelective(PsCashSet record);

    int updateByPrimaryKey(PsCashSet record);
}