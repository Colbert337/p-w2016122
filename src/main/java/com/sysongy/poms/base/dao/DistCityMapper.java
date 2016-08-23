package com.sysongy.poms.base.dao;

import com.sysongy.poms.base.model.DistCity;

import java.util.List;

public interface DistCityMapper {
    int deleteByPrimaryKey(String cityId);

    int insert(DistCity record);

    int insertSelective(DistCity record);

    DistCity selectByPrimaryKey(String cityId);

    int updateByPrimaryKeySelective(DistCity record);

    int updateByPrimaryKey(DistCity record);

    /**
     * 查询热门城市列表
     * @return
     */
    List<DistCity> queryHotCityList(Integer isHot);

    /**
     * 查询城市信息
     * @param city 城市信息
     * @return
     */
    DistCity queryCityInfo(DistCity city);
}