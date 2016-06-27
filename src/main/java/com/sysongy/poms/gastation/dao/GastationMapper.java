package com.sysongy.poms.gastation.dao;

import java.util.List;

import com.sysongy.poms.gastation.model.Gastation;

public interface GastationMapper {

	int deleteByPrimaryKey(String sysGasStationId);

	int insert(Gastation record);

	int insertSelective(Gastation record);

	Gastation selectByPrimaryKey(String sysGasStationId);

	int updateByPrimaryKeySelective(Gastation record);

	int updateByPrimaryKey(Gastation record);
	
	int updatePrepayBalance(Gastation record);
	
	List<Gastation> queryForPage(Gastation record);
	
	Gastation findGastationid(String province_id);
	
	List<Gastation> getAllStationByArea(String areacode);
}