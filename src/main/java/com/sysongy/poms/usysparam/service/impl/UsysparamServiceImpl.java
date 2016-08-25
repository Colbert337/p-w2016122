package com.sysongy.poms.usysparam.service.impl;

import java.util.List;
import java.util.Map;

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
		return usysparamMapper.delete(recode);
	}

	@Override
	public List<Usysparam> queryUsysparamByGcode(String gcode){
		return usysparamMapper.queryUsysparamByGcode(gcode);
	}

	@Override
	public List<Map<String, Object>> queryUsysparamMapByGcode(String gcode) {
		return usysparamMapper.queryUsysparamMapByGcode(gcode);
	}

	@Override
	public List<Usysparam> queryUsysparamChildByGcode(String gcode){
		return usysparamMapper.queryUsysparamChildByGcode(gcode);
	}


	@Override
	public List<Usysparam> queryAll(Usysparam recode) {
		return usysparamMapper.selectAll(recode);
	}
	@Override
	public String saveUsysparam(Usysparam obj, String operation) throws Exception {
		if("insert".equals(operation)){
			return this.saveUsysparam(obj).toString();
		}else{
			return this.updateUsysparam(obj).toString();
		}
	}
	@Override
	public Integer deleteByGcodeAndMcode(Usysparam recode) {
		return usysparamMapper.delete(recode);
	}

	@Override
	public List<Usysparam> queryCardTypeByMcodeAndScode(Usysparam record) throws Exception {
		return usysparamMapper.queryCardTypeByMcodeAndScode(record);
	}
}
