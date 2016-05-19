package com.hbkis.boss.evaluate.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hbkis.boss.evaluate.model.BossItemEvaluation;
import com.hbkis.boss.evaluate.model.BossOverallEvaluation;
import com.hbkis.boss.evaluate.service.EvaluateService;

/**
 * @FileName     :  EvaluateController.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.evaluate.controller
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年3月24日, 下午6:26:08
 * @Author       :  Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@RequestMapping("/web/evaluate")
@Controller
public class EvaluateController {

	@Autowired
	private EvaluateService evaluateService;
	
	/**
	 * 查询单项评论列表
	 * @param map
	 * @return
	 */
	@RequestMapping("/item/list")
	public String queryItemEvaluateList(@RequestParam int itemType,ModelMap map){
		List<BossItemEvaluation> itemEvaluateList = new ArrayList<BossItemEvaluation>();
		itemEvaluateList = evaluateService.queryBossItemEvaluationList(itemType);
		map.addAttribute("itemEvaluateList", itemEvaluateList);
		map.addAttribute("itemType", itemType);
		map.addAttribute("current_module", "webpage/evaluate/item_list");
		return "comm/g_main";
	}
	
	/**
	 * 修改单项评价内容
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/item/update",method = RequestMethod.POST)
	public String updateItemEvaluateById(HttpServletRequest request,ModelMap map){
		List<BossItemEvaluation> itemEvaluateList = new ArrayList<BossItemEvaluation>();
		
		String[] itemEvaluationIds = request.getParameterValues("itemEvaluationId");
		String[] evaluateArray = request.getParameterValues("evaluate");
		String itemType = request.getParameter("itemType").toString();
		
		if(evaluateArray != null && evaluateArray.length > 0){
			for (int i = 0; i < evaluateArray.length; i++) {
				BossItemEvaluation itemEvaluation = new BossItemEvaluation();
				itemEvaluation.setEvaluate(evaluateArray[i]);
				itemEvaluation.setBossItemEvaluationId(itemEvaluationIds[i]);
				
				evaluateService.updateBossItemEvaluationById(itemEvaluation);
			}
		}
		return  "redirect:/web/evaluate/item/list?itemType="+itemType;
	}
	
	/**
	 * 查询综合评论列表
	 * @param map
	 * @return
	 */
	@RequestMapping("/overall/list")
	public String queryOverallEvaluateList(ModelMap map){
		List<BossOverallEvaluation> overallEvaluateList = new ArrayList<BossOverallEvaluation>();
		overallEvaluateList = evaluateService.queryBossOverallEvaluationList();
		map.addAttribute("overallEvaluateList", overallEvaluateList);
		map.addAttribute("current_module", "webpage/evaluate/overall_list");
		return "comm/g_main";
	}
	
	/**
	 * 修改综合评价建议内容
	 * @param map
	 * @return
	 */
	@RequestMapping("/overall/update")
	public String updateOverallEvaluateById(HttpServletRequest request,ModelMap map){
		List<BossOverallEvaluation> overallEvaluationList = new ArrayList<BossOverallEvaluation>();
		
		String[] overallEvaluationIds = request.getParameterValues("overallEvaluationId");
		String[] evaluateArray = request.getParameterValues("evaluate");
		String[] suggestArray = request.getParameterValues("suggest");
		
		if(evaluateArray != null && evaluateArray.length > 0){
			for (int i = 0; i < evaluateArray.length; i++) {
				BossOverallEvaluation overallEvaluation = new BossOverallEvaluation();
				overallEvaluation.setEvaluate(evaluateArray[i]);
				overallEvaluation.setBossOverallEvaluationId(overallEvaluationIds[i]);
				if(overallEvaluationIds[i] != null && !"".equals(overallEvaluationIds[i])){
					overallEvaluation.setSuggest(suggestArray[i]);
				}
				
				evaluateService.updateBossOverallEvaluationById(overallEvaluation);
			}
		}
		return  "redirect:/web/evaluate/overall/list";
	}
	
}
