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
    List<SysUser> queryUserListByUserType(SysUser sysUser);
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

    int updateUserByName(SysUser record);

    /**
     * 根据用户名和站点ID重置用户密码
     * @param record
     * @return
     */
    int updateUserByUserName(SysUser record);

    SysUser queryUserForCRM(SysUser user);
    /**
     * 完全匹配
     * @param sysUser
     * @return
     */
    List<SysUser> queryUserListByUserName(SysUser sysUser);
}