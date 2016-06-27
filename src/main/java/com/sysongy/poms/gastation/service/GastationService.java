package com.sysongy.poms.gastation.service;

import java.math.BigDecimal;
import java.util.List;

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

	public int updatedepositGastation(SysDepositLog log) throws Exception;

	public String updatePrepayBalance(Gastation obj, BigDecimal addCash) throws Exception;
	
	public String chargeToDriverUpdateGastationPrepay(SysOrder order, String is_discharge) throws Exception;

}
