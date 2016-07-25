package com.sysongy.poms.card.service;

import java.util.List;

import com.sysongy.poms.card.model.SysongyChargeCard;
import com.sysongy.poms.usysparam.model.Usysparam;


public interface SysongyChargeCardService {
	
	public Integer save(SysongyChargeCard obj) throws Exception;
	
	public void delete(String id) throws Exception;
	
	public Integer update(SysongyChargeCard obj) throws Exception;
	
	public int add(SysongyChargeCard obj) throws Exception;
	
	public List<SysongyChargeCard> getSysongyChargeCardList(SysongyChargeCard obj) throws Exception;
	
	public String saveChargeCard(SysongyChargeCard obj,  String operation) throws Exception;
	
	public SysongyChargeCard querySysongyChargeCard(String ownid) throws Exception;
	
	public List<SysongyChargeCard> selectSysongyChargeCard(SysongyChargeCard obj) throws Exception;
	
	public int updateSysongyChargeCard(SysongyChargeCard obj) throws Exception;
	
	public SysongyChargeCard queryCard(SysongyChargeCard obj)throws Exception;
	
}
