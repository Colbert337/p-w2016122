package com.hbkis.boss.health.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hbkis.boss.base.controller.BaseContoller;
import com.hbkis.boss.health.model.Healthsub;
import com.hbkis.boss.health.service.HealthsubService;
import com.hbkis.util.UUIDGenerator;
/**
 * 
 * @FileName     :  HealthsubController.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.physique.controller
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年1月6日, 下午4:58:26
 * @Author       :  Maloo
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@RequestMapping("/web/healthsub")
@Controller
public class HealthsubController extends BaseContoller{
	@Autowired
	private HealthsubService healthsubService;
	
	/**
	 * 保存保健子表记录，包含新增和更新
	 * @param currUser
	 * @param pid
	 * @param categoryId
	 * @param name
	 * @param order
	 * @param reasion
	 * @param advise
	 * @return
	 */
	@RequestMapping("addsubinfo")
	@ResponseBody
	public String addsubinfo(@RequestParam String pid,@RequestParam String categoryId,
			@RequestParam String name,@RequestParam(required=false) String order ,@RequestParam(required=false) String reasion ,
			@RequestParam(required=false) String advise){
		
		Healthsub healthsub = new Healthsub();
		if("".equals(pid) || pid == null){
			healthsub.setId(UUIDGenerator.getUUID());
		}else{
			healthsub.setId(pid);
		}
		
		healthsub.setCategoryId(categoryId);
		healthsub.setName(name);
		healthsub.setReasion(reasion);
		healthsub.setAdvise(advise);
		healthsub.setListorder(Integer.parseInt(order));
		
		if("".equals(pid) || pid == null){
			healthsub.setCreateDate(new Date());
			healthsubService.addHealthsub(healthsub);
		}else{
			healthsub.setModifyDate(new Date());
			healthsubService.updateHealthsub(healthsub);
		}
		
		return null ;
	}
	
	/**
	 * 根据主键ID 删除 保健子表记录
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteHealthsub")
	@ResponseBody
	public String deleteHealthsub(@RequestParam String id){
		if(!"".equals(id) && id != null){
			healthsubService.deleteSubById(id);
		}
		return null ;
	}
	
	/**
	 * 根据主键ID 查询 保健子表记录
	 * @param id
	 * @return
	 */
	@RequestMapping("queryHealthsub")
	@ResponseBody
	public Healthsub queryHealthsub(@RequestParam String id){
		Healthsub healthsub = healthsubService.queryHealthsubById(id);
		return healthsub;
	}
	
}
