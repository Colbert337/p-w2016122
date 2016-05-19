package com.sysongy.poms.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.system.dao.SystemMenuRoleMapper;
import com.sysongy.poms.system.model.SystemMenuRole;
import com.sysongy.poms.system.service.SystemMenuRoleService;

/**
 * @FileName     :  SystemMenuRoleServiceImpl.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.system.service.impl
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月29日, 下午2:51:31
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Service
public class SystemMenuRoleServiceImpl implements SystemMenuRoleService{

	@Autowired
	private SystemMenuRoleMapper systemMenuRoleMapper;
	
	/**
	 * 删除 系统角色菜单
	 */
	@Override
	public int deleteBySystemMenuRoleId(String systemMenuRoleId) {
		int flag = 0;
		if(systemMenuRoleId != null && !"".equals(systemMenuRoleId)){
			systemMenuRoleMapper.deleteBySystemMenuRoleId(systemMenuRoleId);
		}
		return flag;
	}

	@Override
	public int add(SystemMenuRole systemMenuRole) {
		return 0;
	}

	/**
	 * 添加系统角色菜单
	 */
	@Override
	public int addSystemMenuRole(SystemMenuRole systemMenuRole) {
		int flag = 0;
		if(systemMenuRole != null && !"".equals(systemMenuRole)){
			flag = systemMenuRoleMapper.addSystemMenuRole(systemMenuRole);
		}
		return flag;
	}

	/**
	 * 查询系统角色菜单
	 */
	@Override
	public SystemMenuRole selectBySystemMenuRoleId(String systemMenuRoleId) {
		SystemMenuRole systemMenuRole = null;
		if(systemMenuRoleId != null && !"".equals(systemMenuRoleId)){
			systemMenuRole = systemMenuRoleMapper.selectBySystemMenuRoleId(systemMenuRoleId);
		}
		return systemMenuRole;
	}

	/**
	 * 修改系统角色菜单
	 */
	@Override
	public int updateSystemMenuRole(SystemMenuRole systemMenuRole) {
		int flag = 0;
		if(systemMenuRole != null && !"".equals(systemMenuRole)){
			flag = systemMenuRoleMapper.updateSystemMenuRole(systemMenuRole);
		}
		return flag;
	}

	@Override
	public int update(SystemMenuRole systemMenuRole) {
		return 0;
	}

	 /**
     * 根据 systemMenuId 查询  
     * @param systemMenuId
     * @return
     */
	@Override
	public List<SystemMenuRole> querySystemMenuRoleListBySystemMenuId(String systemMenuId) {
		List<SystemMenuRole> list = new ArrayList<SystemMenuRole>();
		if(systemMenuId != null && !"".equals(systemMenuId)){
			list = systemMenuRoleMapper.querySystemMenuRoleListBySystemMenuId(systemMenuId);
		}
		return list;
	}

}
