package com.hbkis.boss.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbkis.boss.health.dao.HealthsubMapper;
import com.hbkis.boss.health.model.Healthsub;
import com.hbkis.boss.health.service.HealthsubService;

/**
 * 
 * @FileName     :  HealthsubServiceImpl.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.physique.service.impl
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年1月5日, 下午5:24:29
 * @Author       :  Maloo
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Service
public class HealthsubServiceImpl implements HealthsubService{
	@Autowired
	private HealthsubMapper healthsubMapper;
	
	@Override
	public void deleteSubByPcategoryId(String categoryId) {
		// TODO Auto-generated method stub
		healthsubMapper.deleteSubByPcategoryId(categoryId);
	}

	@Override
	public void addHealthsub(Healthsub healthsub) {
		// TODO Auto-generated method stub
		healthsubMapper.insertSelective(healthsub);
	}

	@Override
	public List<Healthsub> querySubListByCategoryId(String categoryId) {
		// TODO Auto-generated method stub
		return healthsubMapper.querySubListByCategoryId(categoryId);
	}

	@Override
	public void deleteSubById(String id) {
		// TODO Auto-generated method stub
		healthsubMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateHealthsub(Healthsub healthsub) {
		// TODO Auto-generated method stub
		healthsubMapper.updateByPrimaryKeySelective(healthsub);
	}

	@Override
	public Healthsub queryHealthsubById(String id) {
		// TODO Auto-generated method stub
		return healthsubMapper.selectByPrimaryKey(id);
	}

}
