package com.hbkis.boss.system.service;

import java.util.List;

import com.hbkis.boss.system.model.SystemMenu;

/**
 * @FileName     :  SystemMenuService.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.system.service
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月21日, 上午11:42:43
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface SystemMenuService {

	int deleteBySystemMenuId(String systemMenuId);

    int add(SystemMenu systemMenu);

    int addSystemMenu(SystemMenu systemMenu);

    SystemMenu queryBySystemMenuId(String systemMenuId);

    int updateSystemMenu(SystemMenu systemMenu);

    int update(SystemMenu systemMenu);
    
    /**
     * 根据系统  id  查询 systemMenu表  查看系统菜单
     * @param systemId
     * @return
     */
    public List<SystemMenu> querySystemBySystemId(String systemId);
}
