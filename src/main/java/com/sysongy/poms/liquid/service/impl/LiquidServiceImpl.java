package com.sysongy.poms.liquid.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.liquid.dao.SysGasSourceMapper;
import com.sysongy.poms.liquid.model.SysGasSource;
import com.sysongy.poms.liquid.service.LiquidService;
import com.sysongy.poms.transportion.model.Transportion;
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
			SysGasSource gassource = gasSourceMapper.findgasourceid("L"+record.getprovince_id());
			String newid;
			
			if(gassource == null || StringUtils.isEmpty(gassource.getSys_gas_source_id())){
				newid = "L"+record.getprovince_id() + "001";
			}else{
				Integer tmp = Integer.valueOf(gassource.getSys_gas_source_id().substring(3, 7)) + 1;
				newid = "L"+record.getprovince_id() +StringUtils.leftPad(tmp.toString() , 3, "0");
			}
			
			record.setSys_gas_source_id(newid);
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
