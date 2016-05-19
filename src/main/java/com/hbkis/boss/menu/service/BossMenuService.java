package com.hbkis.boss.menu.service;

import java.util.List;

import com.hbkis.boss.menu.model.BossMenu;

/**
 * @FileName     :  BossMenuService.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.menu.service
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月15日, 下午6:40:08
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface BossMenuService {

	/**
	 * 删除菜单
	 * @param menuId
	 * @return
	 */
    int deleteByMenuId(String menuId);

    int add(BossMenu bossMenu);

    /**
     * 添加菜单
     * @param bossMenu
     * @return
     */
    int addBossMenu(BossMenu bossMenu);

    /**
     * 根据menuId 查询菜单
     * @param menuId
     * @return
     */
    BossMenu queryByMenuId(String menuId);

    /**
     * 修改菜单
     * @param bossMenu
     * @return
     */
    int updateBossMenu(BossMenu bossMenu);

    int update(BossMenu bossMenu);
    
    /**
     * 获取所有菜单
     * @return
     */
    public List<BossMenu> queryAllBossMenu();
    
    /**
     * 根据  parentId 查询  菜单
     * @param parentId
     * @return
     */
    public List<BossMenu> queryMenuByParentId(String parentId);
    
    /**
     * 根据 menuName  查看 有没有重复的菜单
     * @param menuName
     * @return
     */
    public List<BossMenu> checkBossMenuByMenuName(String menuName);
    
    /**
     * 根据 menuCode  查看 有没有重复的菜单简称
     * @param menuName
     * @return
     */
    public List<BossMenu> checkBossMenuByMenuCode(String menuCode);
    
    
}
