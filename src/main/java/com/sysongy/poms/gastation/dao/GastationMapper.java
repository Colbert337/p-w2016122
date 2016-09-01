package com.sysongy.poms.gastation.dao;

import java.util.List;
import java.util.Map;

import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.order.model.SysOrder;

public interface GastationMapper {

	int deleteByPrimaryKey(String sysGasStationId);

	int insert(Gastation record);

	int insertSelective(Gastation record);

	Gastation selectByPrimaryKey(String sysGasStationId);

	int updateByPrimaryKeySelective(Gastation record);

	int updateByPrimaryKey(Gastation record);
	
	int updatePrepayBalance(Gastation record);
	
	List<Gastation> queryForPage(Gastation record);
	List<Gastation> queryForPage2(Gastation record);
	
	Gastation findGastationid(String province_id);
	
	List<Gastation> getAllStationByArea(String areacode);
	
	List<Map<String,Object>> gastionConsumeReport(SysOrder record);
    
    List<Map<String,Object>> gastionConsumeReportTotal(SysOrder record);
    
    List<Map<String,Object>> gastionRechargeReport(SysOrder record);
    
    List<Map<String,Object>> gastionRechargeReportTotal(SysOrder record);

	List<Gastation> exists(String gas);	
	
	int updateByPrimaryKeySelective2(Gastation gas);

	int delete(String gas);
}