package com.hbkis.boss.health.dao;

import java.util.List;

import com.hbkis.boss.health.model.Health;

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