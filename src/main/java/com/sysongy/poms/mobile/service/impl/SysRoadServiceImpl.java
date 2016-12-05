package com.sysongy.poms.mobile.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.integral.model.IntegralHistory;
import com.sysongy.poms.integral.model.IntegralRule;
import com.sysongy.poms.integral.service.IntegralHistoryService;
import com.sysongy.poms.integral.service.IntegralRuleService;
import com.sysongy.poms.mobile.dao.SysRoadConditionMapper;
import com.sysongy.poms.mobile.dao.SysRoadConditionStrMapper;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.model.SysRoadConditionStr;
import com.sysongy.poms.mobile.service.SysRoadService;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.RedisClientImpl;
import com.sysongy.util.RedisClientInterface;

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
public class SysRoadServiceImpl implements SysRoadService {
	@Autowired
	SysRoadConditionMapper sysRoadConditionMapper;

	@Autowired
	private UsysparamService usysparamservice;
	
	@Autowired
	IntegralRuleService integralRuleService;
	
	@Autowired
	DriverService driverService;
	
	@Autowired
	IntegralHistoryService integralHistoryService;
	
	RedisClientInterface redisClientImpl;
	
	@Autowired
	SysRoadConditionStrMapper sysRoadConditionStrMapper;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 路况列表
	 */
	@Override
	public PageInfo<SysRoadCondition> queryRoadList(SysRoadCondition road) {
		// TODO Auto-generated method stub
		PageHelper.startPage(road.getPageNum(), road.getPageSize(), road.getOrderby());
		List<SysRoadCondition> list = sysRoadConditionMapper.queryForPage(road);
		PageInfo<SysRoadCondition> page = new PageInfo<>(list);
		return page;
	}

	@Override
	/**
	 * 保存路况-pc
	 */
	public int saveRoad(SysRoadCondition road) {
		// TODO Auto-generated method stub
		// sysroadMapper.insert(road);
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
	public PageInfo<Map<String, Object>> queryForPageMap(SysRoadCondition record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Map<String, Object>> list = sysRoadConditionMapper.queryForPageMap(record);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<SysRoadCondition> queryForPage(SysRoadCondition record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<SysRoadCondition> list = sysRoadConditionMapper.queryForPage(record);
		PageInfo<SysRoadCondition> pageInfo = new PageInfo<SysRoadCondition>(list);
		return pageInfo;
	}
	@Override
	public PageInfo<SysRoadCondition> queryForExcel(SysRoadCondition record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<SysRoadCondition> list = sysRoadConditionMapper.queryForExcel(record);
		PageInfo<SysRoadCondition> pageInfo = new PageInfo<SysRoadCondition>(list);
		return pageInfo;
	}

	@Override
	public SysRoadCondition selectByPrimaryKey(String id) throws Exception {
		return sysRoadConditionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int cancelSysRoadCondition(SysRoadCondition record) throws Exception {
		int result = sysRoadConditionMapper.cancelSysRoadCondition(record);
		SysRoadCondition sysRoadCondition = sysRoadConditionMapper.selectByPrimaryKey(record.getRoadId());
		if(sysRoadCondition != null){
			int count = Integer.parseInt(sysRoadCondition.getInvalid_count());
			count++;

			SysRoadCondition roadCondition = new SysRoadCondition();
			roadCondition.setId(record.getRoadId());
			roadCondition.setInvalid_count(count+"");

			//修改取消数
			sysRoadConditionMapper.updateByPrimaryKeySelective(roadCondition);
		}
		return result;
	}

	/**
	 * 修改（审核）-pc
	 * @throws Exception 
	 */
	@Override
	public int updateRoad(SysRoadCondition road,String userID) throws Exception {
		// TODO Auto-generated method stub
		int updateNum =  sysRoadConditionMapper.updateByPrimaryKeyToCheck(road);
		if("2".equals(road.getConditionStatus())){
			//判断上报路况审核是否通过，存在则根据条件发放积分
			integralHistoryService.addsblkIntegralHistory(road,userID);	
		}
		return updateNum;
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
	/**
	 * 获取限高限重路况列表
	 * @return
	 */
	@Override
	public List<SysRoadCondition> queryHighWeightRoadId() {
		return sysRoadConditionMapper.queryHighWeightRoadId();
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

	@Override
	public int updateByPrimaryKey(SysRoadCondition record) throws Exception {
		// TODO Auto-generated method stub
		return sysRoadConditionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public boolean restoreRedis(RedisClientInterface redisClientImpl) {
		// TODO Auto-generated method stub
		List<SysRoadCondition>list= sysRoadConditionMapper.queryForRedis();
		for (int i = 0; i < list.size(); i++) {
			Usysparam param=usysparamservice.queryUsysparamByCode("CONDITION_TYPE", list.get(i).getConditionType());
			int time =sumTime(list.get(i).getStartTime(), Integer.valueOf(param.getData()));
			if (time!=0) {
				redisClientImpl.addToCache("Road"+list.get(i).getId(), list.get(i), time);
			}
		}
		return true;
	}
	
	public static int sumTime(Date startTime, Integer h) {

		// TODO Auto-generated method stub

		if (h == null || h == 0) {
			return -1;
		} else {

			long a = startTime.getTime() - new Date().getTime();
			int time = h  * 60 + (int) a / 1000;
			// road.setEndTime(new Date(new Date().getTime() + h * 60 * 60 *
			// 1000 + a));
			if (time <= 0) {
				time = 0;
			}
			return time;
		}
	}

	@Override
	public Map<String, Object> queryRoadCount() {
		return sysRoadConditionMapper.queryRoadCount();
	}
}
	
