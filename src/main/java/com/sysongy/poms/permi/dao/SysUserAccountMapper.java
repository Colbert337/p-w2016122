package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysUserAccount;

public interface SysUserAccountMapper {
    int deleteByPrimaryKey(String sysUserAccountId);

    int insert(SysUserAccount record);

    int insertSelective(SysUserAccount record);

    SysUserAccount selectByPrimaryKey(String sysUserAccountId);

    int updateByPrimaryKeySelective(SysUserAccount record);

    int updateAccount(SysUserAccount record);
}