package com.sysongy.poms.mobile.service;


import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.model.SysRoadConditionStr;
import com.sysongy.util.RedisClientInterface;

/**
 * @FileName: SysRoadService
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.mobile.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年09月08日, 17:16
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface SysRoadService {

	PageInfo<SysRoadCondition> queryRoadList(SysRoadCondition road);

	int saveRoad(SysRoadCondition road);
	/**
	 * 上報路況
	 */
	public int reportSysRoadCondition(SysRoadCondition record) throws Exception;

	/**
	 * 获取路况列表
	 */
	public PageInfo<SysRoadCondition> queryForPage(SysRoadCondition record) throws Exception;

	/**
	 * 获取路况列表
	 */
	public PageInfo<Map<String, Object>> queryForPageMap(SysRoadCondition record) throws Exception;
	/**
	 * 根据ID查询
	 */
	public SysRoadCondition selectByPrimaryKey(String id) throws Exception;
	
	/**
	 * 取消路況
	 */
	public int cancelSysRoadCondition(SysRoadCondition record) throws Exception;

	int updateRoad(SysRoadCondition road,String userID) throws Exception;

	int deleteRoad(SysRoadCondition road);

	List<SysRoadCondition> queryRoadIDList();
	/**
	 * 获取限高限重路况列表
	 * @return
	 */
	List<SysRoadCondition> queryHighWeightRoadId();

	PageInfo<SysRoadConditionStr> queryRoadListStr(SysRoadCondition road);
	
	List<SysRoadCondition> queryAll() throws Exception;
	public int updateByPrimaryKey(SysRoadCondition record)throws Exception;
  
	boolean restoreRedis(RedisClientInterface redisClientImpl);
 
	PageInfo<SysRoadCondition> queryForExcel(SysRoadCondition record) throws Exception;
	/**
	 * 获取待审核路况数量
	 * @return
	 */
	Map<String,Object> queryRoadCount();
}
