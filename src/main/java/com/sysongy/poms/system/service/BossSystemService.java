package com.sysongy.poms.system.service;

import java.util.List;

import com.sysongy.poms.system.model.BossSystem;

/**
 * @FileName     :  BossSystemService.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.system.service.impl
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月17日, 下午4:16:24
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface BossSystemService {

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
    int addBossSystem(BossSystem bossSystem , String httpPath);

    /**
     * 查询系统
     * @param systemId
     * @return
     */
    BossSystem queryByBossSystemId(String systemId);

    /**
     * 修改系统
     * @param bossSystem
     * @param httpPath 
     * @return
     */
    int updateBossSystem(BossSystem bossSystem, String httpPath);

    int update(BossSystem bossSystem);
    
    /**
     * 查询所有系统
     * @return
     */
    public List<BossSystem> queryAllBossSystem();
    
    /**
     * 根据系统名称查询   此系统是否重复
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
