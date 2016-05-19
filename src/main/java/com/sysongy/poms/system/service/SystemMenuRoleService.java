package com.sysongy.poms.system.service;

import java.util.List;

import com.sysongy.poms.system.model.SystemMenuRole;

/**
 * @FileName     :  SystemMenuRoleService.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.system.service
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月29日, 下午2:51:06
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface SystemMenuRoleService {

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
