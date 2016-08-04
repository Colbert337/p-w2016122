package com.sysongy.poms.transportion.dao;

import java.util.List;
import java.util.Map;

import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.transportion.model.Transportion;

public interface TransportionMapper {
	
    int deleteByPrimaryKey(String sysGasStationId);

    int insert(Transportion record);

    int insertSelective(Transportion record);

    Transportion selectByPrimaryKey(String sysGasStationId);

    int updateByPrimaryKeySelective(Transportion record);

    int updateByPrimaryKey(Transportion record);
    
    int updateDeposit(Transportion record);
    
    List<Transportion> queryForPage(Transportion record);
    
    Transportion findTransportationid(String province_id);
    
    List<Map<String,Object>> transportionRechargeReport(SysDepositLog record);
    
    List<Map<String,Object>> transportionRechargeReportTotal(SysDepositLog record);
    
}