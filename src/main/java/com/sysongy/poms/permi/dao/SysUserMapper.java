package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysFunction;
import com.sysongy.poms.permi.model.SysUser;

import java.util.List;

public interface SysUserMapper {
    List<SysUser> queryUserList(SysUser sysUser);

    SysUser queryUserById(String userId);

    SysUser queryUserByAccount(String userName, String password);

    int deleteUserById(String userId);

    int addUser(SysUser record);

    int updateUser(SysUser record);
}