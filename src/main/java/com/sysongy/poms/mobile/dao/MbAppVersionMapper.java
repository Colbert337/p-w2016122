package com.sysongy.poms.mobile.dao;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.model.MbAppVersion;

import java.util.List;

public interface MbAppVersionMapper {
    int deleteByPrimaryKey(String appVersionId);

    int insert(MbAppVersion record);

    int insertSelective(MbAppVersion record);

    MbAppVersion selectByPrimaryKey(String appVersionId);

    int updateByPrimaryKeySelective(MbAppVersion record);

    int updateByPrimaryKey(MbAppVersion record);

    List<MbAppVersion> queryAppVersionList(MbAppVersion record);

    /**
     * 更新APP下载量
     * @return
     */
    int updateDownCount();
}