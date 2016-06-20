package com.sysongy.poms.liquid.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.liquid.model.SysGasSource;

public interface LiquidService {
	
	public PageInfo<SysGasSource> querySysGasSource(SysGasSource obj) throws Exception;
	
	public SysGasSource queryGasSourceByPK(String sys_gas_source_id) throws Exception;
	
	public String saveGasSource(SysGasSource obj,  String operation) throws Exception;
	
	public Integer delGasSource(String sys_gas_source_id) throws Exception;

}
