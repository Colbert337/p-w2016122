package com.sysongy.poms.menu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.menu.dao.BossMenuMapper;
import com.sysongy.poms.menu.model.BossMenu;
import com.sysongy.poms.menu.service.BossMenuService;

/**
 * @FileName     :  BossMenuServiceImpl.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.menu.service.impl
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月15日, 下午6:40:45
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Service
public class BossMenuServiceImpl implements BossMenuService{

	@Autowired
	private BossMenuMapper bossMenuMapper;
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @return
	 */
	@Override
	public int deleteByMenuId(String menuId) {
		int flag = 0;
		if(menuId != null && !"".equals(menuId)){
			flag = bossMenuMapper.deleteByMenuId(menuId);
		}
		return flag;
	}

	
	@Override
	public int add(BossMenu bossMenu) {
		return 0;
	}

	/**
     * 添加菜单
     * @param bossMenu
     * @return
     */
	@Override
	public int addBossMenu(BossMenu bossMenu) {
		int flag = 0;
		if(bossMenu != null && !"".equals(bossMenu)){
			flag = bossMenuMapper.addBossMenu(bossMenu);
		}
		return flag;
	}

	/**
     * 根据menuId 查询菜单
     * @param menuId
     * @return
     */
	@Override
	public BossMenu queryByMenuId(String menuId) {
		BossMenu bossMenu = null;
		if(menuId !=  null && !"".equals(menuId)){
			bossMenu = bossMenuMapper.queryByMenuId(menuId);
		}
		return bossMenu;
	}

	/**
     * 修改菜单
     * @param bossMenu
     * @return
     */
	@Override
	public int updateBossMenu(BossMenu bossMenu) {
		int flag = 0;
		if(bossMenu != null && !"".equals(bossMenu)){
			flag = bossMenuMapper.updateBossMenu(bossMenu);
		}
		return flag;
	}

	@Override
	public int update(BossMenu bossMenu) {
		return 0;
	}


	/**
	 * 获取所有菜单
	 */
	@Override
	public List<BossMenu> queryAllBossMenu() {
		List<BossMenu> bossList = new ArrayList<BossMenu>();
		bossList = bossMenuMapper.queryAllBossMenu();
		return bossList;
	}


	   /**
     * 根据  parentId 查询  菜单
     * @param parentId
     * @return
     */
	@Override
	public List<BossMenu> queryMenuByParentId(String parentId) {
		List<BossMenu> bossList = new ArrayList<BossMenu>();
		if(parentId != null && !"".equals(parentId)){
			bossList = bossMenuMapper.queryMenuByParentId(parentId);
		}
		return bossList;
	}


	/**
     * 根据 menuName  查看 有没有重复的菜单
     * @param menuName
     * @return
     */
	@Override
	public List<BossMenu> checkBossMenuByMenuName(String menuName) {
		List<BossMenu>  list = new ArrayList<BossMenu>();
		if(menuName != null && !"".equals(menuName)){
			list = bossMenuMapper.checkBossMenuByMenuName(menuName);
		}
		return list;
	}


    /**
     * 根据 menuCode  查看 有没有重复的菜单简称
     * @param menuName
     * @return
     */
	@Override
	public List<BossMenu> checkBossMenuByMenuCode(String menuCode) {
		List<BossMenu>  list = new ArrayList<BossMenu>();
		if(menuCode != null && !"".equals(menuCode)){
			list = bossMenuMapper.checkBossMenuByMenuCode(menuCode);
		}
		return list;
	}
	
	

}
