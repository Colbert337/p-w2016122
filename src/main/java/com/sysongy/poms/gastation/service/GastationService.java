package com.sysongy.poms.gastation.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.model.Gastation;


public interface GastationService {
	
	public PageInfo<Gastation> queryGastation(Gastation obj) throws Exception;
	
	public Gastation queryGastationByPK(String gastationid) throws Exception;
	
	public String saveGastation(Gastation obj,  String operation) throws Exception;
	
	public Integer delGastation(String gastation) throws Exception;
	
	public List<Gastation> getAllStationByArea(String areacode) throws Exception;


}
