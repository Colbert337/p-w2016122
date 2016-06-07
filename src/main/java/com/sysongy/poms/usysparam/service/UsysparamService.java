package com.sysongy.poms.usysparam.service;

import java.util.List;

import com.sysongy.poms.usysparam.model.Usysparam;

public interface UsysparamService {
	
	public List<Usysparam> query(String gcode, String scode) throws Exception;
	
}
