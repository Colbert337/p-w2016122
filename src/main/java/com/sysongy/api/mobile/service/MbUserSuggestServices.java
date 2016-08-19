package com.sysongy.api.mobile.service;

import com.sysongy.api.mobile.model.feedback.MbUserSuggest;
import com.sysongy.poms.mobile.model.Suggest;

public interface MbUserSuggestServices {
	
	public Integer saveSuggester(MbUserSuggest obj) throws Exception;
}
