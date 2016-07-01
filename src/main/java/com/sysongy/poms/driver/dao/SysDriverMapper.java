package com.sysongy.poms.driver.dao;

import java.util.List;

import com.sysongy.poms.driver.model.SysDriver;

public interface SysDriverMapper {
    int deleteByPrimaryKey(String sysDriverId);

    int insert(SysDriver record);

    int insertSelective(SysDriver record);
    
    int updateFirstCharge(SysDriver record);

    SysDriver selectByPrimaryKey(String sysDriverId);

    int updateByPrimaryKeySelective(SysDriver record);

    int updateByPrimaryKey(SysDriver record);

    List<SysDriver> queryForPage(SysDriver record);

    List<SysDriver> querySingleDriver(SysDriver record);

    int isExists(SysDriver record);

    SysDriver queryDriverByMobilePhone(SysDriver record);

    /**
     * 条件查询司机列表
     * @param record
     * @return
     */
    List<SysDriver> querySearchDriverList(SysDriver record);

    /**
     * 批量离职司机
     * @param idList
     * @return
     */
    int deleteDriverByIds(List<String> idList);

    List<SysDriver> queryForPageSingleList(SysDriver record);

    SysDriver selectByAccount(String sysUserAccount);
}