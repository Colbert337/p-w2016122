package com.sysongy.poms.usysparam.service;

import java.util.List;

import com.sysongy.poms.usysparam.model.Usysparam;

public interface UsysparamService {
	
	public List<Usysparam> query(String gcode, String scode) throws Exception;

	/**
	 * 根据gcode查询参数对象
	 * @param gcode
	 * @return
     */
	Usysparam queryUsysparamByCode(String gcode,String mcode);
	
}
