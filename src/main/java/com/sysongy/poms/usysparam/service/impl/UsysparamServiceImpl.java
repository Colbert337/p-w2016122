package com.sysongy.poms.usysparam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.usysparam.dao.UsysparamMapper;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;

@Service
public class UsysparamServiceImpl implements UsysparamService {

	@Autowired
	private UsysparamMapper usysparamMapper;
	
	/**
	 * 通过Gcode和scode定位字典表中的配置项
	 */
	@Override
	public List<Usysparam> query(String gcode, String scode) throws Exception {
		
		Usysparam param = new Usysparam();
		param.setGcode(gcode);
		param.setScode(scode);
		
		return usysparamMapper.selectDefault(param);
	}
	/**
	 * 根据gcode查询参数对象
	 * @param gcode
	 * @return
	 */
	@Override
	public Usysparam queryUsysparamByCode(String gcode,String mcode) {
		return usysparamMapper.queryUsysparamByCode(gcode,mcode);
	}
	
	@Override
	public Integer saveUsysparam(Usysparam recode) {
		return usysparamMapper.insert(recode);
	}
	
	@Override
	public Integer updateUsysparam(Usysparam recode) {
		return usysparamMapper.updateByPrimaryKey(recode);
	}
	
	@Override
	public Integer deleteUsysparam(Usysparam recode) {
		return usysparamMapper.deleteByPrimaryKey(recode);
	}

	public List<Usysparam> queryUsysparamByGcode(String gcode){
		return usysparamMapper.queryUsysparamByGcode(gcode);
	}
}
