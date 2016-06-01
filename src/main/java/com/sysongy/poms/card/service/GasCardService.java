package com.sysongy.poms.card.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.card.model.GasCard;

public interface GasCardService {
	
	public PageInfo<GasCard> queryGasCard(GasCard obj) throws Exception;
	
	public Integer saveGasCard(GasCard obj) throws Exception;
	
	public Integer delGasCard(String cardid) throws Exception;
	
	public Boolean checkCardExist(String cardno) throws Exception;
	
	public String checkMoveCard(String cardno) throws Exception;
	
	public Integer moveCard(GasCard obj) throws Exception;
}
