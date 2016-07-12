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
	public Usysparam queryUsysparamByCode(String gcode,String mcode);
	
	public Integer saveUsysparam(Usysparam recode);
	
	public Integer updateUsysparam(Usysparam recode);
	
	public Integer deleteUsysparam(Usysparam recode);

	public List<Usysparam> queryUsysparamByGcode(String gcode);

	public List<Usysparam> queryUsysparamChildByGcode(String gcode);

	public List<Usysparam> queryAll(Usysparam recode);
	
	public Integer deleteByGcodeAndMcode(Usysparam recode);
	
	public String saveUsysparam(Usysparam obj,  String operation) throws Exception;
}
