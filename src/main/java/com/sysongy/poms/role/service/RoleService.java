package com.sysongy.poms.role.service;

import com.sysongy.poms.role.model.Role;

/**
 * @FileName     :  RoleService.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.role.service
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月29日, 下午3:14:48
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface RoleService {

	int deleteByRoleId(String roleId);

    int add(Role role);

    int addRole(Role role);

    Role queryByRoleId(String roleId);

    int updateRole(Role role);

    int update(Role role);
    
    /**
     * 根据 systemId   和   roleType = 0   查询roleId   
     * @param systemId
     * @return
     */
    public Role queryRoleBySystemId(String systemId);
}
