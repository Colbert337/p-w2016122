package com.sysongy.poms.permi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.permi.dao.SysUserMapper;
import com.sysongy.poms.permi.dao.SysUserRoleMapper;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserRole;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    /**
     * 查询用户列表（分页）
     * @param sysUser
     * @return
     */
    @Override
    public PageInfo<SysUser> queryUserListPage(SysUser sysUser) {
        /**
         * 获取分页参数
         */

        PageHelper.startPage(sysUser.getPageNum(), sysUser.getPageSize());
        List<SysUser> userList = sysUserMapper.queryUserList(sysUser);
        PageInfo<SysUser> userPageInfo = new PageInfo<SysUser>(userList);
        return userPageInfo;
    }
    /**
     * 查询用户列表
     * @param userType
     * @return
     */
    @Override
    public List<SysUser> queryUserListByUserType(int userType) {
        SysUser sysUser = new SysUser();
        sysUser.setUserType(userType);
        sysUser.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
        List<SysUser> userList = sysUserMapper.queryUserList(sysUser);
        return userList;
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public SysUser queryUserByAccount(SysUser user) {
        SysUser sysUser = null;
        if (user != null) {
            String password = user.getPassword();
            String userName = user.getUserName();
            try {
                password = Encoder.MD5Encode(password.getBytes());
                user.setPassword(password);
                sysUser = sysUserMapper.queryUserByAccount(userName, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sysUser;
    }

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户编号
     * @return
     */
    @Override
    public SysUser queryUserByUserId(String userId) {
        return sysUserMapper.queryUserById(userId);
    }

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户编号
     * @return
     */
    @Override
    public Map<String, Object> queryUserMapByUserId(String userId) {
        return sysUserMapper.queryUserMapByUserId(userId);
    }

    /**
     * 添加用户
     * @param user 用户信息实体类
     * @return
     */
    @Override
    public int addUser(SysUser user) {
        //添加用户对应角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setSysUserRoleId(UUIDGenerator.getUUID());
        sysUserRole.setSysUserId(user.getSysUserId());
        sysUserRole.setSysRoleId(user.getAvatarB());
        sysUserRoleMapper.addUserRole(sysUserRole);
        return sysUserMapper.addUser(user);
    }
    /**
     * 修改用户信息
     * @param user 用户信息
     * @return
     */
    @Override
    public int updateUser(SysUser user) {
        //删除用户对应角色
        sysUserRoleMapper.deleteUserRoleByUserId(user.getSysUserId());
        //添加用户对应角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setSysUserRoleId(UUIDGenerator.getUUID());
        sysUserRole.setSysUserId(user.getSysUserId());
        sysUserRole.setSysRoleId(user.getAvatarB());
        sysUserRoleMapper.addUserRole(sysUserRole);
        return sysUserMapper.updateUser(user);
    }

    /**
     * 修改用户状态
     * @param user 用户信息
     * @return
     */
    @Override
    public int updateStatus(SysUser user) {
        return sysUserMapper.updateUser(user);
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
