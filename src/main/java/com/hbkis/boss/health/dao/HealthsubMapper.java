package com.hbkis.boss.health.dao;

import java.util.List;

import com.hbkis.boss.health.model.Healthsub;

public interface HealthsubMapper {
    int deleteByPrimaryKey(String id);

    int insert(Healthsub record);

    int insertSelective(Healthsub record);

    Healthsub selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Healthsub record);

    int updateByPrimaryKey(Healthsub record);

	void deleteSubByPcategoryId(String categoryId);

	List<Healthsub> querySubListByCategoryId(String categoryId);
}