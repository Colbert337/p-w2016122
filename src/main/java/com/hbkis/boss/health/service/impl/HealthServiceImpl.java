package com.hbkis.boss.health.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbkis.boss.health.dao.HealthMapper;
import com.hbkis.boss.health.dao.HealthsubMapper;
import com.hbkis.boss.health.model.Health;
import com.hbkis.boss.health.model.Healthsub;
import com.hbkis.boss.health.service.HealthService;
/**
 * 
 * @FileName     :  HealthServiceImpl.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.physique.service.impl
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年1月5日, 下午5:23:51
 * @Author       :  Maloo
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Service
public class HealthServiceImpl implements HealthService{
	@Autowired
	private HealthMapper healthMapper;
	@Autowired
	private HealthsubMapper healthsubMapper;
	
	@Override
	public List<Health> queryROOTlist(String rootid) {
		// TODO Auto-generated method stub
		return healthMapper.queryROOTlist(rootid);
	}
	@Override
	public void addHealth(Health health) {
		// TODO Auto-generated method stub
		healthMapper.insertSelective(health);
	}
	
	/**
	 * 查询所有的分类
	 * @return
	 */
	@Override
	public List<Health> queryAllHealthList() {
		// TODO Auto-generated method stub
		return healthMapper.queryAllHealthList();
	}
	
	/**
	 * 查询跟节点分类  categoryId = pcategoryId
	 * @return
	 */
	@Override
	public List<Health> queryRootHealthList() {
		// TODO Auto-generated method stub
		return healthMapper.queryRootHealthList();
	}
	/**
	 * 查询父类ID下的子类
	 * @param id
	 * @return
	 */
	@Override
	public List<Health> queryHealthListByPcategoryId(String pcategoryId) {
		// TODO Auto-generated method stub
		return healthMapper.queryHealthListByPcategoryId(pcategoryId);
	}
	@Override
	public Health queryHealthByCategoryId(String categoryId) {
		// TODO Auto-generated method stub
		return healthMapper.selectByPrimaryKey(categoryId);
	}
	@Override
	public void updateHealth(Health health) {
		// TODO Auto-generated method stub
		healthMapper.updateByPrimaryKeySelective(health);
	}
	@Override
	public void deletehealthByCategoryId(String categoryId) {
		// TODO Auto-generated method stub
		healthMapper.deleteByPrimaryKey(categoryId);
	}
	
	/**
	 * 根据分类明 查找该分类下的相关说明 以及等级建议
	 * @param categoryName
	 * @param pcategoryName
	 * @return
	 */
	@Override
	public String[] getReasionAndAdviseByName(String categoryName,String pcategoryName) {
		// TODO Auto-generated method stub
		String [] val = new String [2];
		//先根据分类name找子类，如果子类找不到，则找概括描述的父类信息
		List<Health> hlist = new ArrayList<Health>();
		hlist = healthMapper.queryHealthListLikeName("%"+categoryName+"%");
		if(hlist.isEmpty()){
			hlist = healthMapper.queryHealthListLikeName("%"+pcategoryName+"%");
		}
		
		if(hlist.isEmpty()){
			val[0] = "" ;
			val[1] = "" ;
		}else{
			Iterator it = hlist.iterator();
			if(it.hasNext()){ //暂时取一个来拼装
				Health health = (Health)it.next();
				if("".equals(health.getSm()) || health.getSm() == null){
					val[0] = "无" ;
				}else{
					val[0] = health.getSm();
				}
				
				//拼装建议
				List<Healthsub> hslist = new ArrayList<Healthsub>();
				hslist = healthsubMapper.querySubListByCategoryId(health.getCategoryId());
				String advise = "" ;
				for (int i = 0; i < hslist.size(); i++) {
					advise += hslist.get(i).getAdvise() + ";/n" ;
				}
				val[1] = advise ;
			}
		}
		
		
		return val;
	}

}
