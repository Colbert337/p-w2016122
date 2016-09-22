package com.sysongy.poms.mobile.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.dao.SysRoadConditionMapper;
import com.sysongy.poms.mobile.dao.SysRoadConditionStrMapper;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.model.SysRoadConditionStr;
import com.sysongy.poms.mobile.service.SysRoadService;

/**
 * @FileName: SysRoadServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.mobile.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年09月08日, 17:17
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class SysRoadServiceImpl implements SysRoadService{
	@Autowired
	SysRoadConditionMapper sysRoadConditionMapper;

	@Autowired
	SysRoadConditionStrMapper sysRoadConditionStrMapper;
	/**
	 * 路况列表
	 */
	@Override
	public PageInfo< SysRoadCondition> queryRoadList(SysRoadCondition road) {
		// TODO Auto-generated method stub
		 PageHelper.startPage(road.getPageNum(), road.getPageSize(), road.getOrderby());
		List<SysRoadCondition> list=sysRoadConditionMapper.queryForPage(road);
		PageInfo<SysRoadCondition> page=new PageInfo<>(list);
		return page;
	}
	@Override
	/**
	 * 保存路况-pc
	 */
	public int saveRoad(SysRoadCondition road) {
		// TODO Auto-generated method stub
//		sysroadMapper.insert(road);
		return sysRoadConditionMapper.insertSelective(road);
	}
	/**
	 * 上報路況
	 */
	@Override
	public int reportSysRoadCondition(SysRoadCondition record) {
		return sysRoadConditionMapper.reportSysRoadCondition(record);
	}
	/**
	 * 获取路况列表
	 */
	@Override
	public PageInfo<Map<String, Object>> queryForPage(SysRoadCondition record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Map<String, Object>> list = sysRoadConditionMapper.queryForPageMap(record);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		return pageInfo;
	}
	
	@Override
	public SysRoadCondition selectByPrimaryKey(String id) throws Exception {
		return sysRoadConditionMapper.selectByPrimaryKey(id);
	}
	@Override
	public int cancelSysRoadCondition(SysRoadCondition record) throws Exception {
		return sysRoadConditionMapper.cancelSysRoadCondition(record);
	}
	/**
	 * 修改（审核）-pc
	 */
	@Override
	public int updateRoad(SysRoadCondition road) {
		// TODO Auto-generated method stub
		return sysRoadConditionMapper.updateByPrimaryKeyToCheck(road);
	}
	/**
	 * 删除-pc
	 */
	@Override
	public int deleteRoad(SysRoadCondition road) {
		// TODO Auto-generated method stub
		return sysRoadConditionMapper.deleteByPrimaryKey(road.getId());
	}
	/**
	 * 查看生效id 用于取缓存
	 */
	@Override
	public List<SysRoadCondition> queryRoadIDList() {
		// TODO Auto-generated method stub
		return sysRoadConditionMapper.queryRoadId();
	}
	@Override
	public List<SysRoadCondition> queryAll() throws Exception {
		// TODO Auto-generated method stub
		return sysRoadConditionMapper.queryAll();
	}
	@Override
	public PageInfo<SysRoadConditionStr> queryRoadListStr(SysRoadCondition road) {
		// TODO Auto-generated method stub
		PageHelper.startPage(road.getPageNum(), road.getPageSize(), road.getOrderby());
		List<SysRoadConditionStr> list = sysRoadConditionStrMapper.queryForPageForRoadId(road.getId());
		PageInfo<SysRoadConditionStr> pageInfo = new PageInfo<SysRoadConditionStr>(list);
		return pageInfo;
	}
}
