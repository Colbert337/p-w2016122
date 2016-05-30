package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(String sysUserId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String sysUserId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}