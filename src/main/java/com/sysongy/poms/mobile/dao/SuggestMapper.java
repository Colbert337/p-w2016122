package com.sysongy.poms.mobile.dao;

import java.util.List;

import com.sysongy.poms.mobile.model.Suggest;

public interface SuggestMapper {
	public List<Suggest> querySuggest(Suggest obj);
}
