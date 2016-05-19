package com.hbkis.boss.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbkis.boss.system.dao.BossSystemMapper;
import com.hbkis.boss.system.model.BossSystem;
import com.hbkis.boss.system.service.BossSystemService;
import com.hbkis.util.HttpClientUtil;

/**
 * @FileName     :  BossSystemServiceImpl.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.system.service
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月17日, 下午4:20:30
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Service
public class BossSystemServiceImpl implements BossSystemService{

	@Autowired
	private BossSystemMapper bossSystemMapper;
	
	/**
	 * 删除系统  根据系统id
	 * @param systemId
	 * @return
	 */
	@Override
	public int deleteByBossSystemId(String systemId) {
		int flag = 0;
		if(systemId != null && !"".equals(systemId)){
			flag = bossSystemMapper.deleteByBossSystemId(systemId);
		}
		return flag;
	}

	@Override
	public int add(BossSystem bossSystem) {
		return 0;
	}


    /**
     * 添加系统
     * @param bossSystem
     * @return
     */
	@Override
	public int addBossSystem(BossSystem bossSystem,String httpPath) {
		int flag = 0;
		if(bossSystem != null && !"".equals(bossSystem)){
			
			
			flag = bossSystemMapper.addBossSystem(bossSystem);
			String url = httpPath+"/pems-web/api/system/createSystem";
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("systemId", bossSystem.getSystemId());
			paramsMap.put("systemMenuIds", bossSystem.getMenuId());
			paramsMap.put("pemsName", bossSystem.getPemsName());
			paramsMap.put("pemsPwd", bossSystem.getPemsPwd());
			paramsMap.put("manager", bossSystem.getManager());
			String resultStr = "";
			try {
				resultStr = HttpClientUtil.post(url, paramsMap ,"UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return flag;
	}

	/**
     * 查询系统
     * @param systemId
     * @return
     */
	@Override
	public BossSystem queryByBossSystemId(String systemId) {
		BossSystem bossSystem = null;
		if(systemId != null && !"".equals(systemId)){
			bossSystem = bossSystemMapper.queryByBossSystemId(systemId);
		}
		return bossSystem;
	}

	 /**
     * 修改系统
     * @param bossSystem
     * @return
     */
	@Override
	public int updateBossSystem(BossSystem bossSystem,String httpPath) {
		int flag = 0;
		if(bossSystem != null && !"".equals(bossSystem)){
			flag = bossSystemMapper.updateBossSystem(bossSystem);
			String url = httpPath+"/pems-web/api/system/updateSystem";
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("systemId", bossSystem.getSystemId());
			paramsMap.put("systemMenuIds", bossSystem.getMenuId());
			paramsMap.put("pemsName", bossSystem.getPemsName());
			paramsMap.put("pemsPwd", bossSystem.getPemsPwd());
			paramsMap.put("userId", bossSystem.getUserId());	//这说明是修改
			String resultStr = "";
			try {
				resultStr = HttpClientUtil.post(url, paramsMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return flag;
	}

	@Override
	public int update(BossSystem bossSystem) {
		return 0;
	}

	/**
     * 查询所有系统
     * @return
     */
	@Override
	public List<BossSystem> queryAllBossSystem() {
		List<BossSystem> list = new ArrayList<BossSystem>();
		list = bossSystemMapper.queryAllBossSystem();
		return list;
	}

	
	/**
	 * 根据系统名称   查询系统名称是否重复
	 */
	@Override
	public List<BossSystem> checkBySystemName(String systemName) {
		 List<BossSystem> list = new ArrayList<BossSystem>();
		 if(systemName != null && !"".equals(systemName)){
			 list = bossSystemMapper.checkBySystemName(systemName);
		 }
		 
		return list;
	}

	/**
     * 根据systemId 查询系统信息
     * @param systemId
     * @return
     */
	@Override
	public BossSystem querySystemBySystemId(String systemId) {
		BossSystem bossSystem  = null;
		if(systemId != null && !"".equals(systemId)){
			bossSystem = bossSystemMapper.querySystemBySystemId(systemId);
		}
		return bossSystem;
	}

}
