package com.sysongy.poms.role.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.role.dao.RoleMapper;
import com.sysongy.poms.role.model.Role;
import com.sysongy.poms.role.service.RoleService;

/**
 * @FileName     :  RoleServiceImpl.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.role.service.impl
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月29日, 下午3:16:52
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public int deleteByRoleId(String roleId) {
		return 0;
	}

	@Override
	public int add(Role role) {
		return 0;
	}

	@Override
	public int addRole(Role role) {
		return 0;
	}

	@Override
	public Role queryByRoleId(String roleId) {
		return null;
	}

	@Override
	public int updateRole(Role role) {
		return 0;
	}

	@Override
	public int update(Role role) {
		return 0;
	}

	/**
	 * 根据系统id  和  管理员状态  查询  roleId
	 */
	@Override
	public Role queryRoleBySystemId(String systemId) {
		Role role = null;
		if(systemId != null && !"".equals(systemId)){
			role = roleMapper.queryRoleBySystemId(systemId);
		}
		return role;
	}

}
