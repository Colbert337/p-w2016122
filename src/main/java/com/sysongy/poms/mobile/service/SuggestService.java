package com.sysongy.poms.mobile.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.model.Suggest;

public interface SuggestService {

	public PageInfo<Suggest> querySuggest(Suggest obj);
}
