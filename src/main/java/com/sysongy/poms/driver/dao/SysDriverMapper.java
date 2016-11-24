package com.sysongy.poms.driver.dao;

import java.util.HashMap;
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
    List<SysDriver> queryForPage1(SysDriver record);

    /**
     * 查询司机信息列表，关联公司
     * @param record
     * @return
     */
    List<SysDriver> queryForPageList(SysDriver record);

    List<SysDriver> querySingleDriver(SysDriver record);

    List<SysDriver> ifExistDriver(SysDriver record);

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

    /**
     * 查询当前区域下的最大下标
     * @return
     */
    SysDriver queryMaxIndex(String provinceId);
    
    /**
     * 根據邀請碼查詢用戶  
     */
    SysDriver queryByInvitationCode(String invitationCode);

	List<SysDriver> queryForPage2(List list);

	SysDriver queryByUserNameAndPassword(SysDriver record);
	
	SysDriver queryByUserName(SysDriver record);
	
	SysDriver queryByDeviceToken(String deviceToken);
	
	List<SysDriver> queryAll();
	
	SysDriver queryByPK(String driverId);
	
	//增加司机积分
	int updateDriverByIntegral(SysDriver sysDriver);
	
	/**
	 * 根据邀请码和积分周期查询积分数量
	 * @param HashMap
	 * @return
	 */
	List<HashMap<String,String>> queryInvitationByCode(HashMap<String,String> HashMap); 
	
	
	SysDriver selectByinvitationCode(String invitation_Code);
}