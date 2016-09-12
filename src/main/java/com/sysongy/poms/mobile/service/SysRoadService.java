package com.sysongy.poms.mobile.service;

<<<<<<< HEAD
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.model.Suggest;
import com.sysongy.poms.mobile.model.SysRoadCondition;
=======
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.order.model.SysOrder;
>>>>>>> f08cca1e3cf4b4393d26f8c6ee574d69698f8055

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
<<<<<<< HEAD

	PageInfo<SysRoadCondition> queryRoadList(SysRoadCondition road);

	int saveRoad(SysRoadCondition road);
=======
	/**
	 * 上報路況
	 */
	public int reportSysRoadCondition(SysRoadCondition record) throws Exception;

	/**
	 * 获取路况列表
	 */
	public PageInfo<Map<String,Object>> queryForPage(SysRoadCondition record) throws Exception;
	
	/**
	 * 根据ID查询
	 */
	public SysRoadCondition selectByPrimaryKey(String id) throws Exception;
	
	/**
	 * 取消路況
	 */
	public int cancelSysRoadCondition(SysRoadCondition record) throws Exception;
	
>>>>>>> f08cca1e3cf4b4393d26f8c6ee574d69698f8055
}
