package com.sysongy.poms.base.dao;

import com.sysongy.poms.base.model.DistProvince;

public interface DistProvinceMapper {
    int deleteByPrimaryKey(String provinceId);

    int insert(DistProvince record);

    int insertSelective(DistProvince record);

    DistProvince selectByPrimaryKey(String provinceId);

    int updateByPrimaryKeySelective(DistProvince record);

    int updateByPrimaryKey(DistProvince record);
}