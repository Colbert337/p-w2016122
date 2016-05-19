package com.sysongy.poms.health.dao;

import java.util.List;

import com.sysongy.poms.health.model.Health;

public interface HealthMapper {
    int deleteByPrimaryKey(String categoryId);

    int insert(Health record);

    int insertSelective(Health record);

    Health selectByPrimaryKey(String categoryId);

    int updateByPrimaryKeySelective(Health record);

    int updateByPrimaryKey(Health record);

	List<Health> queryROOTlist(String rootid);

	List<Health> queryAllHealthList();

	List<Health> queryRootHealthList();

	List<Health> queryHealthListByPcategoryId(String pcategoryId);

	List<Health> queryHealthListLikeName(String categoryName);
}