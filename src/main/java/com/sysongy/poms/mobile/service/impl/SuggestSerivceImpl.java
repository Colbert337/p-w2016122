package com.sysongy.poms.mobile.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.card.dao.GasCardLogMapper;
import com.sysongy.poms.card.service.impl.GasCardServiceImpl;
import com.sysongy.poms.mobile.dao.SuggestMapper;
import com.sysongy.poms.mobile.model.Suggest;
import com.sysongy.poms.mobile.service.SuggestService;

@Service
public class SuggestSerivceImpl implements SuggestService {
	protected Logger logger = LoggerFactory.getLogger(GasCardServiceImpl.class);
	@Autowired
	private SuggestMapper suggestMapper;
//	@Autowired
//	private GasCardLogMapper gasCardLogMapper;

	@Override
	public PageInfo<Suggest> querySuggest(Suggest obj) {
		// TODO Auto-generated method stub
		List<Suggest> list= suggestMapper.querySuggest(obj);
		PageInfo<Suggest> page=new PageInfo<>(list);
		return page;
	}

}
