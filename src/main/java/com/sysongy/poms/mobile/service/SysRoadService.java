package com.sysongy.poms.mobile.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.model.Suggest;
import com.sysongy.poms.mobile.model.SysRoadCondition;

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

	PageInfo<SysRoadCondition> queryRoadList(SysRoadCondition road);

	int saveRoad(SysRoadCondition road);
}
