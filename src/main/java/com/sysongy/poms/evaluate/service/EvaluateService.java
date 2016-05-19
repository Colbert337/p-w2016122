package com.sysongy.poms.evaluate.service;

import java.util.List;

import com.sysongy.poms.evaluate.model.BossItemEvaluation;
import com.sysongy.poms.evaluate.model.BossOverallEvaluation;

/**
 * @FileName     :  EvaluateService.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.evaluate.service
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年3月24日, 下午6:24:38
 * @Author       :  Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public interface EvaluateService {

	/**
	 * 查询单项评价列表
	 * @return
	 */
	List<BossItemEvaluation> queryBossItemEvaluationList(int itemType);
	
	/**
	 * 根据单项评价ID修改单项评价内容
	 * @param bossItemEvaluation
	 * @return
	 */
	int updateBossItemEvaluationById(BossItemEvaluation bossItemEvaluation);
	
	/**
	 * 查询综合评价列表
	 * @return
	 */
	List<BossOverallEvaluation> queryBossOverallEvaluationList();
	
	/**
	 * 根据综合评价ID修改单项评价内容
	 * @param bossOverallEvaluation
	 * @return
	 */
	int updateBossOverallEvaluationById(BossOverallEvaluation bossOverallEvaluation);
}
