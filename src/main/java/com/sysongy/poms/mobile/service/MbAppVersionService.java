package com.sysongy.poms.mobile.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.model.MbAppVersion;
import com.sysongy.poms.mobile.model.MbBanner;

import java.text.ParseException;


public interface MbAppVersionService {

    int deleteByPrimaryKey(String appVersionId);

    int insert(MbAppVersion record);

    int insertSelective(MbAppVersion record);

    MbAppVersion selectByPrimaryKey(String appVersionId);

    int updateByPrimaryKeySelective(MbAppVersion record);

    int updateByPrimaryKey(MbAppVersion record) throws ParseException;

    PageInfo<MbAppVersion> queryAppVersionListPage(MbAppVersion record) throws Exception;

    }
