package com.sysongy.poms.permi.dao;

import com.sysongy.poms.permi.model.SysUserAccount;

public interface SysUserAccountMapper {
	
    int deleteByPrimaryKey(String sysUserAccountId);

    int insert(SysUserAccount record);

    int insertSelective(SysUserAccount record);

    SysUserAccount selectByPrimaryKey(String sysUserAccountId);

    int updateByPrimaryKeySelective(SysUserAccount record);

    int updateAccount(SysUserAccount record);

    int updateAccountBalance(SysUserAccount record);

    /**
     * 根据站点ID查询站点账户信息
     * @param sysTransportionId 运输公司ID
     * @return
     */
    SysUserAccount queryUserAccountByStationId(String sysTransportionId);

    /**
     * 根据司机编号查询司机账户信息
     * @param sysDriverId 运输公司ID
     * @return
     */
    SysUserAccount queryUserAccountByDriverId(String sysDriverId);
}