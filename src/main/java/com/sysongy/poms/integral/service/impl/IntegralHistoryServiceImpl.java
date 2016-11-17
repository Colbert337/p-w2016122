package com.sysongy.poms.integral.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.sysongy.util.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.sysongy.poms.integral.model.IntegralHistory;
import com.sysongy.poms.integral.dao.IntegralHistoryMapper;
import com.sysongy.poms.integral.service.IntegralHistoryService;

@Service
public class IntegralHistoryServiceImpl implements IntegralHistoryService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IntegralHistoryMapper integralHistoryMapper;

	@Override
	public PageInfo<IntegralHistory> queryIntegralHistory(IntegralHistory integralHistory) throws Exception {
		if (integralHistory.getPageNum() == null) {
			integralHistory.setPageNum(1);
			integralHistory.setPageSize(10);
		}
		if (StringUtils.isEmpty(integralHistory.getOrderby())) {
			integralHistory.setOrderby("lastmodify_time desc");
		}
		PageHelper.startPage(integralHistory.getPageNum(), integralHistory.getPageSize(), integralHistory.getOrderby());
		List<IntegralHistory> list = integralHistoryMapper.queryForPage(integralHistory);
		PageInfo<IntegralHistory> pageInfo = new PageInfo<IntegralHistory>(list);
		return pageInfo;
	}

	@Override
	public String addIntegralHistory(IntegralHistory integralHistory, String userID) throws Exception {
		integralHistory.setIntegral_history_id(UUIDGenerator.getUUID());
		integralHistory.setCreate_person_id(userID);
		integralHistory.setCreate_time(new Date());
		integralHistory.setLastmodify_person_id(userID);
		integralHistory.setLastmodify_time(new Date());
		integralHistoryMapper.insert(integralHistory);
		return integralHistory.getIntegral_history_id();
	}

	@Override
	public String modifyIntegralHistory(IntegralHistory integralHistory, String userID) throws Exception {
		integralHistory.setLastmodify_person_id(userID);
		integralHistory.setLastmodify_time(new Date());
		integralHistoryMapper.updateByPrimaryKey(integralHistory);
		return integralHistory.getIntegral_history_id();
	}

	@Override
	public Integer delIntegralHistory(String integral_history_id) throws Exception {
		return integralHistoryMapper.deleteByPrimaryKey(integral_history_id);
	}

	@Override
	public IntegralHistory queryIntegralHistoryByPK(String integral_history_id) throws Exception {
		return integralHistoryMapper.selectByPrimaryKey(integral_history_id);
	}
}
