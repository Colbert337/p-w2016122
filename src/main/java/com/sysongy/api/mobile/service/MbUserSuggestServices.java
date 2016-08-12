package com.sysongy.api.mobile.service;

import java.util.List;

import com.sysongy.api.mobile.model.feedback.MobileFeedBack;
import com.sysongy.poms.suggest.model.Suggest;

public interface MbUserSuggestServices {
	
	public Integer saveSuggester(MobileFeedBack obj) throws Exception;
	public List<Suggest> queryForPage(Suggest suggest);
}
