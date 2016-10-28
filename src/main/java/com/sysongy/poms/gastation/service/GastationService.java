package com.sysongy.poms.gastation.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.order.model.SysOrder;



public interface GastationService {
	
	public PageInfo<Gastation> queryGastation(Gastation obj) throws Exception;
	
	public Gastation queryGastationByPK(String gastationid) throws Exception;
	
	public String saveGastation(Gastation obj,  String operation) throws Exception;
	
	public Integer delGastation(String gastation) throws Exception;
	
	public List<Gastation> getAllStationByArea(String areacode) throws Exception;

	public List<Gastation> getAllStationList(Gastation record) throws Exception;

	public int updatedepositGastation(SysDepositLog log, String operation) throws Exception;

	public String updatePrepayBalance(Gastation obj, BigDecimal addCash) throws Exception;
	
	public String chargeToDriverUpdateGastationPrepay(SysOrder order, String is_discharge) throws Exception;
	
	public Integer alertPrepayBalance() throws Exception;
	
	public PageInfo<Map<String, Object>> gastionConsumeReport(SysOrder order)throws Exception;
    
	public PageInfo<Map<String, Object>> gastionConsumeReportTotal(SysOrder record) throws Exception;
	
	public PageInfo<Map<String, Object>> gastionRechargeReport(SysOrder order)throws Exception;
    
	public PageInfo<Map<String, Object>> gastionRechargeReportTotal(SysOrder record) throws Exception;

	public PageInfo<Gastation> queryGastation2(Gastation record) throws Exception;
	
	public void insert(Gastation gas)throws Exception;

	public void update(Gastation gas);

	boolean exists(String gas) throws Exception;
	public int delete(String id);
	
	public int updateByPrimaryKeySelective(Gastation record) throws Exception;
	
	public void updateForJob() throws Exception;
}
