package com.sysongy.poms.system.dao;

import java.util.List;

import com.sysongy.poms.system.model.SystemMenuRole;

/**
 * 
 * @FileName     :  SystemMenuRoleMapper.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.system.dao
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月29日, 下午2:49:51
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface SystemMenuRoleMapper {
	
    int deleteBySystemMenuRoleId(String systemMenuRoleId);

    int add(SystemMenuRole systemMenuRole);

    int addSystemMenuRole(SystemMenuRole systemMenuRole);

    SystemMenuRole selectBySystemMenuRoleId(String systemMenuRoleId);

    int updateSystemMenuRole(SystemMenuRole systemMenuRole);

    int update(SystemMenuRole systemMenuRole);
    
    /**
     * 根据 systemMenuId 查询  
     * @param systemMenuId
     * @return
     */
    public List<SystemMenuRole> querySystemMenuRoleListBySystemMenuId(String systemMenuId);
}