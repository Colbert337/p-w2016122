package com.sysongy.api.mobile.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.api.mobile.dao.MbUserSuggestMapper;
import com.sysongy.api.mobile.model.feedback.MbUserSuggest;
import com.sysongy.api.mobile.model.feedback.MobileFeedBack;
import com.sysongy.api.mobile.service.MbUserSuggestServices;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.suggest.model.Suggest;
import com.sysongy.util.UUIDGenerator;

@Service
public class MbUserSuggestServicesImpl implements MbUserSuggestServices {

	@Autowired
    private MbUserSuggestMapper mbUserSuggestMapper;
	@Autowired
    private SysDriverMapper sysDriverMapper;
	
	@Override
	public Integer saveSuggester(MobileFeedBack record) throws Exception {
		
		SysDriver driver = new SysDriver();
		driver.setMobilePhone(record.getToken());
		
		driver = sysDriverMapper.queryDriverByMobilePhone(driver);
		
		MbUserSuggest suggest = new MbUserSuggest();
		suggest.setCreatedDate(new Date());
		suggest.setFollowUp("");
		suggest.setMbUserSuggestId(UUIDGenerator.getUUID());
		suggest.setMemo("");
		suggest.setMobilePhone(record.getToken());
		suggest.setSuggestRes(record.getMsg());
		suggest.setSysDriverId(driver.getSysDriverId());
		suggest.setUpdatedDate(new Date());
		
		return mbUserSuggestMapper.insert(suggest);
	}

	@Override
	public List<Suggest> queryForPage(Suggest suggest) {
		// TODO Auto-generated method stub
		return null;
	}

}
