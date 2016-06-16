package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysFunction;
import com.sysongy.poms.permi.model.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserMapper {
    /**
     * @param sysUser
     * @return
     */
    List<SysUser> queryUserList(SysUser sysUser);

    SysUser queryUserById(String userId);

    Map<String, Object> queryUserMapByAccount(SysUser user);

    Map<String,Object> queryUserMapByUserId(String userId);

    SysUser queryUserByAccount(String userName, String password);
    /**
     * 查询用户
     * @param user
     * @return
     */
    SysUser queryUser(SysUser user);

    int deleteUserById(String userId);

    int addUser(SysUser record);

    int updateUser(SysUser record);
}