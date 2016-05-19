package com.sysongy.poms.evaluate.dao;

import java.util.List;

import com.sysongy.poms.evaluate.model.BossOverallEvaluation;

public interface BossOverallEvaluationMapper {
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
	
    int deleteByPrimaryKey(String bossOverallEvaluationId);

    int insert(BossOverallEvaluation record);

    int insertSelective(BossOverallEvaluation record);

    int updateByPrimaryKey(BossOverallEvaluation record);
}