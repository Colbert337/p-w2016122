package com.hbkis.boss.health.service;

import java.util.List;

import com.hbkis.boss.health.model.Healthsub;

/**
 * 
 * @FileName     :  HealthsubService.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.physique.service
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年1月5日, 下午5:23:21
 * @Author       :  Maloo
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface HealthsubService {

	void deleteSubByPcategoryId(String categoryId);

	void addHealthsub(Healthsub healthsub);

	List<Healthsub> querySubListByCategoryId(String categoryId);

	void deleteSubById(String id);

	void updateHealthsub(Healthsub healthsub);

	Healthsub queryHealthsubById(String id);

}
