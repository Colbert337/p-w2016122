package com.sysongy.poms.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.system.dao.SystemMenuMapper;
import com.sysongy.poms.system.model.SystemMenu;
import com.sysongy.poms.system.service.SystemMenuService;

/**
 * @FileName     :  SystemMenuServiceImpl.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.system.service.impl
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月21日, 上午11:43:11
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Service
public class SystemMenuServiceImpl implements SystemMenuService{
	
	@Autowired
	private SystemMenuMapper systemMenuMapper;

	/**
	 * 删除系统菜单
	 */
	@Override
	public int deleteBySystemMenuId(String systemMenuId) {
		int flag = 0;
		if(systemMenuId != null && !"".equals(systemMenuId)){
			flag = systemMenuMapper.deleteBySystemMenuId(systemMenuId);
		}
		return flag;
	}

	@Override
	public int add(SystemMenu systemMenu) {
		return 0;
	}

	/**
	 * 添加系统菜单权限
	 */
	@Override
	public int addSystemMenu(SystemMenu systemMenu) {
		int flag = 0;
		if(systemMenu != null && !"".equals(systemMenu)){
			flag = systemMenuMapper.addSystemMenu(systemMenu);
		}
		return flag;
	}

	/**
	 * 根据systemMenuId 查询系统菜单权限
	 */
	@Override
	public SystemMenu queryBySystemMenuId(String systemMenuId) {
		SystemMenu systemMenu = null;
		if(systemMenuId != null && !"".equals(systemMenuId)){
			systemMenu = systemMenuMapper.queryBySystemMenuId(systemMenuId);
		}
		return systemMenu;
	}

	/**
	 * 修改系统权限
	 */
	@Override
	public int updateSystemMenu(SystemMenu systemMenu) {
		int flag = 0;
		if(systemMenu != null && !"".equals(systemMenu)){
			flag = systemMenuMapper.updateSystemMenu(systemMenu);
		}
		return flag;
	}

	@Override
	public int update(SystemMenu systemMenu) {
		return 0;
	}

	/**
	 * 根据systeMid  查询系统菜单
	 */
	@Override
	public List<SystemMenu> querySystemBySystemId(String systemId) {
		List<SystemMenu> list = new ArrayList<SystemMenu>();
		if(systemId != null && !"".equals(systemId)){
			list = systemMenuMapper.querySystemBySystemId(systemId);
		}
		return list;
	}

}
