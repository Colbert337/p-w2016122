package com.sysongy.poms.system.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.system.model.SysCashBack;

public interface SysCashBackService {
	
	public PageInfo<SysCashBack> queryCashBack(SysCashBack obj) throws Exception;
	
	public SysCashBack queryCashBackByPK(String cashBackid) throws Exception;
	
	public String saveCashBack(SysCashBack obj,  String operation) throws Exception;
	
	public Integer delCashBack(String sysCashBackId) throws Exception;
}
