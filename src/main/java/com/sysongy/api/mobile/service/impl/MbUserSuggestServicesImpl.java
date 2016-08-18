package com.sysongy.api.mobile.service.impl;

import com.sysongy.api.mobile.dao.MbUserSuggestMapper;
import com.sysongy.api.mobile.model.feedback.MbUserSuggest;
import com.sysongy.api.mobile.service.MbUserSuggestServices;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MbUserSuggestServicesImpl implements MbUserSuggestServices {

	@Autowired
    private MbUserSuggestMapper mbUserSuggestMapper;
	@Autowired
    private SysDriverMapper sysDriverMapper;
	
	@Override
	public Integer saveSuggester(MbUserSuggest record) throws Exception {

		return mbUserSuggestMapper.insert(record);
	}

}
