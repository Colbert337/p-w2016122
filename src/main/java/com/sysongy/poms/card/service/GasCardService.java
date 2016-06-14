package com.sysongy.poms.card.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.model.GasCardLog;

public interface GasCardService {
	
	public PageInfo<GasCard> queryGasCard(GasCard obj) throws Exception;
	
	public Integer saveGasCard(GasCard obj) throws Exception;
	
	public Integer deleteGasCard(String cardid, CurrUser user) throws Exception;
	
	public Boolean checkCardExist(String cardno) throws Exception;
	
	public String checkMoveCard(String cardno) throws Exception;
	
	public Integer updateAndMoveCard(GasCard obj) throws Exception;
	
	public PageInfo<GasCardLog> queryGasCardLog(GasCardLog obj) throws Exception;
}
