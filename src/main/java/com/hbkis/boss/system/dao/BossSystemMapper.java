package com.hbkis.boss.system.dao;

import java.util.List;

import com.hbkis.boss.system.model.BossSystem;

/**
 * 
 * @FileName     :  BossSystemMapper.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.system.dao
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月17日, 下午4:17:39
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface BossSystemMapper {
	
	/**
	 * 删除系统  根据系统id
	 * @param systemId
	 * @return
	 */
    int deleteByBossSystemId(String systemId);

    int add(BossSystem bossSystem);

    /**
     * 添加系统
     * @param bossSystem
     * @return
     */
    int addBossSystem(BossSystem bossSystem);

    /**
     * 查询系统
     * @param systemId
     * @return
     */
    BossSystem queryByBossSystemId(String systemId);

    /**
     * 修改系统
     * @param bossSystem
     * @return
     */
    int updateBossSystem(BossSystem bossSystem);

    int update(BossSystem bossSystem);
    
    /**
     * 查询所有系统
     * @return
     */
    public List<BossSystem> queryAllBossSystem();
    
    /**
     * 根据 系统名称  查询是否有相同的系统名称
     * @param systemName
     * @return
     */
    public List<BossSystem> checkBySystemName(String systemName);
    
    /**
     * 根据systemId 查询系统信息
     * @param systemId
     * @return
     */
    public BossSystem querySystemBySystemId(String systemId);
}