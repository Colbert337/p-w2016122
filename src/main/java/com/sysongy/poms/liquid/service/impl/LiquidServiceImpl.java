package com.sysongy.poms.liquid.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.liquid.dao.SysGasSourceMapper;
import com.sysongy.poms.liquid.model.SysGasSource;
import com.sysongy.poms.liquid.service.LiquidService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;

@Service
public class LiquidServiceImpl implements LiquidService {
	
	@Autowired
	private SysGasSourceMapper gasSourceMapper;

	@Override
	public PageInfo<SysGasSource> querySysGasSource(SysGasSource record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<SysGasSource> list = gasSourceMapper.queryForPage(record);
		PageInfo<SysGasSource> pageInfo = new PageInfo<SysGasSource>(list);
		
		return pageInfo;
	}

	@Override
	public SysGasSource queryGasSourceByPK(String sys_gas_source_id) throws Exception {
		return gasSourceMapper.selectByPrimaryKey(sys_gas_source_id);
	}

	@Override
	public String saveGasSource(SysGasSource record, String operation) throws Exception {
		if("insert".equals(operation)){
			record.setSys_gas_source_id(UUIDGenerator.getUUID());
			record.setStatus("1");
			gasSourceMapper.insert(record);
			return record.getSys_gas_source_id();
		}else{
			record.setUpdated_date(new Date());
			gasSourceMapper.updateByPrimaryKeySelective(record);

			return record.getSys_gas_source_id();
		}
	}

	@Override
	public Integer delGasSource(String sys_gas_source_id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
