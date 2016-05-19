package com.sysongy.poms.evaluate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.evaluate.dao.BossItemEvaluationMapper;
import com.sysongy.poms.evaluate.dao.BossOverallEvaluationMapper;
import com.sysongy.poms.evaluate.model.BossItemEvaluation;
import com.sysongy.poms.evaluate.model.BossOverallEvaluation;
import com.sysongy.poms.evaluate.service.EvaluateService;

/**
 * @FileName     :  EvaluateServiceImpl.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.evaluate.service.impl
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年3月24日, 下午6:25:20
 * @Author       :  Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Service
public class EvaluateServiceImpl implements EvaluateService {

	@Autowired
	private BossItemEvaluationMapper bossItemEvaluationMapper;
	@Autowired
	private BossOverallEvaluationMapper bossOverallEvaluationMapper;
	
	/**
	 * 查询单项评价列表
	 * @return
	 */
	@Override
	public List<BossItemEvaluation> queryBossItemEvaluationList(int itemType) {
		return bossItemEvaluationMapper.queryBossItemEvaluationList(itemType);
	}

	/**
	 * 根据单项评价ID修改单项评价内容
	 * @param bossItemEvaluation
	 * @return
	 */
	@Override
	public int updateBossItemEvaluationById(BossItemEvaluation bossItemEvaluation) {
		return bossItemEvaluationMapper.updateBossItemEvaluationById(bossItemEvaluation);
	}

	/**
	 * 查询综合评价列表
	 * @return
	 */
	@Override
	public List<BossOverallEvaluation> queryBossOverallEvaluationList() {
		return bossOverallEvaluationMapper.queryBossOverallEvaluationList();
	}

	/**
	 * 根据综合评价ID修改单项评价内容
	 * @param bossOverallEvaluation
	 * @return
	 */
	@Override
	public int updateBossOverallEvaluationById(BossOverallEvaluation bossOverallEvaluation) {
		return bossOverallEvaluationMapper.updateBossOverallEvaluationById(bossOverallEvaluation);
	}

}
