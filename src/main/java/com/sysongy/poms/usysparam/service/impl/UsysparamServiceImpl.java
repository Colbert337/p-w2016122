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

}
