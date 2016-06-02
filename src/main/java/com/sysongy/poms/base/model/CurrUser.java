package com.sysongy.poms.base.model;

import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.poms.permi.model.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @FileName: CurrUser
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.base.model
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月30日, 15:14
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public class CurrUser {

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 当前园所ID
	 */
	private String schoolId;
	/**
	 * 当前菜单编号
	 */
	private String menuCode;
	/**
	 * 用户信息
	 */
	private SysUser user;

	/**
	 * 用户菜单列表
	 */
	private List<Map<String, Object>> userMenuList;
	
	/**
	 * 页面头部菜单
	 */
	private List<Map<String, Object>> userHeadMenuList;
	
	/**
	 * 用户角色列表
	 */
	private List<SysRole> roleList;
	/**
	 * 系统信息
	 */
	private String sysMessage;

	/**
	 * @return the sysMessage
	 */
	public String getSysMessage() {
		return sysMessage;
	}

	/**
	 * @param sysMessage the sysMessage to set
	 */
	public void setSysMessage(String sysMessage) {
		this.sysMessage = sysMessage;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userMenuList
	 */
	public List<Map<String, Object>> getUserMenuList() {
		return userMenuList;
	}

	/**
	 * @param userMenuList the userMenuList to set
	 */
	public void setUserMenuList(List<Map<String, Object>> userMenuList) {
		this.userMenuList = userMenuList;
	}

	/**
	 * @return the roleList
	 */
	public List<SysRole> getRoleList() {
		return roleList;
	}

	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}
}
