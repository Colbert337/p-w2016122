package com.sysongy.poms.transportion.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.transportion.model.Transportion;

public interface TransportionService {
	
	public PageInfo<Transportion> queryTransportion(Transportion obj) throws Exception;
	
	public Transportion queryTransportionByPK(String transportionid) throws Exception;
	
	public String saveTransportion(Transportion obj,  String operation) throws Exception;
	
	public Integer delTransportion(String transportionid) throws Exception;
	
	public List<Transportion> getAllTransportionByArea(String areacode) throws Exception;
}
