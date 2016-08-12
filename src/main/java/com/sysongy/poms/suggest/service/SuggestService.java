package com.sysongy.poms.suggest.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.suggest.model.Suggest;

public interface SuggestService  {
	public PageInfo<GasCard> queryGasCard(GasCard obj) throws Exception;

	public PageInfo<Suggest> queryForPage(Suggest gascard);

}
