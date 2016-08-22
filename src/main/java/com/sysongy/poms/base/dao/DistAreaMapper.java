package com.sysongy.poms.base.dao;

import com.sysongy.poms.base.model.DistArea;

public interface DistAreaMapper {
    int deleteByPrimaryKey(String areaId);

    int insert(DistArea record);

    int insertSelective(DistArea record);

    DistArea selectByPrimaryKey(String areaId);

    int updateByPrimaryKeySelective(DistArea record);

    int updateByPrimaryKey(DistArea record);
}