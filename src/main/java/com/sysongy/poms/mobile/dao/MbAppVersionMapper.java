package com.sysongy.poms.mobile.dao;

import com.sysongy.poms.mobile.model.MbAppVersion;

public interface MbAppVersionMapper {
    int deleteByPrimaryKey(String appVersionId);

    int insert(MbAppVersion record);

    int insertSelective(MbAppVersion record);

    MbAppVersion selectByPrimaryKey(String appVersionId);

    int updateByPrimaryKeySelective(MbAppVersion record);

    int updateByPrimaryKey(MbAppVersion record);
}