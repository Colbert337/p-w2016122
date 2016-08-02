package com.sysongy.poms.mobile.dao;

import com.sysongy.poms.mobile.model.MbBanner;

import java.util.List;

public interface MbBannerMapper {
    int deleteByPrimaryKey(String mbBannerId);

    int insert(MbBanner record);

    int insertSelective(MbBanner record);

    MbBanner selectByPrimaryKey(String mbBannerId);

    int updateByPrimaryKeySelective(MbBanner record);

    int updateByPrimaryKey(MbBanner record);
    /**
     * 查询APP首页图片列表
     * @param mbBanner
     * @return
     */
    List<MbBanner> queryMbBannerList(MbBanner mbBanner);
}