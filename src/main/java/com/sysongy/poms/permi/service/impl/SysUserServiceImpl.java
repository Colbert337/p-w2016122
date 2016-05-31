package com.sysongy.poms.permi.service.impl;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserRole;
import com.sysongy.poms.permi.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @FileName: SystemUserServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月30日, 11:23
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class SysUserServiceImpl implements SysUserService{

    /**
     * 查询用户列表（分页）
     * @param conditionMap
     * @return
     */
    @Override
    public PageInfo<SysUser> queryUserListPage(Map<String, Object> conditionMap) {
        return null;
    }
    /**
     * 根据用户ID查询用户信息
     * @param userId 用户编号
     * @return
     */
    @Override
    public SysUser queryUserByUserId(String userId) {
        return null;
    }
    /**
     * 添加用户
     * @param user 用户信息实体类
     * @return
     */
    @Override
    public int addUser(SysUser user) {
        return 0;
    }
    /**
     * 修改用户信息
     * @param user 用户信息
     * @return
     */
    @Override
    public int updateUser(SysUser user) {
        return 0;
    }
    /**
     * 根据用户编号删除用户角色关系
     * @param userId 用户编号
     * @return
     */
    @Override
    public int deleteUserRole(String userId) {
        return 0;
    }
    /**
     * 批量添加用户角色关系
     * @param userRoleList 用户角色关系集合
     * @return
     */
    @Override
    public int addUserRole(List<SysUserRole> userRoleList) {
        return 0;
    }
}
