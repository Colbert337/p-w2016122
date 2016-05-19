package com.sysongy.poms.evaluate.dao;

import java.util.List;

import com.sysongy.poms.evaluate.model.BossItemEvaluation;

public interface BossItemEvaluationMapper {
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
	
    int deleteByPrimaryKey(String bossItemEvaluationId);

    int insert(BossItemEvaluation record);

    int insertSelective(BossItemEvaluation record);

    int updateByPrimaryKey(BossItemEvaluation record);
}