package com.hbkis.boss.health.service;

import java.util.List;

import com.hbkis.boss.health.model.Health;

/**
 * 
 * @FileName     :  HealthService.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.physique.service
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年1月5日, 下午5:22:54
 * @Author       :  Maloo
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface HealthService {

	List<Health> queryROOTlist(String rootid);

	void addHealth(Health health);
	/**
	 * 查询所有的分类
	 * @return
	 */
	List<Health> queryAllHealthList();

	/**
	 * 查询跟节点分类  categoryId = pcategoryId
	 * @return
	 */
	List<Health> queryRootHealthList();
	
	/**
	 * 查询父类ID下的子类
	 * @param id
	 * @return
	 */
	List<Health> queryHealthListByPcategoryId(String pcategoryId);

	Health queryHealthByCategoryId(String categoryId);

	void updateHealth(Health health);

	void deletehealthByCategoryId(String categoryId);
	
	/**
	 * 根据分类明 查找该分类下的相关说明 以及等级建议
	 * @param categoryName
	 * @param pcategoryName
	 * @return
	 */
	public String [] getReasionAndAdviseByName(String categoryName,String pcategoryName);

}
