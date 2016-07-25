package com.sysongy.poms.card.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.card.dao.SysongyChargeCardMapper;
import com.sysongy.poms.card.model.SysongyChargeCard;
import com.sysongy.poms.card.service.SysongyChargeCardService;
import com.sysongy.poms.usysparam.model.Usysparam;

@Service
public class SysongyChargeCardServiceImpl implements SysongyChargeCardService{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysongyChargeCardMapper sysongyChargeCardMapper;

	@Override
	public Integer save(SysongyChargeCard obj) throws Exception {	
		 return sysongyChargeCardMapper.insert(obj);
		
	}

	@Override
	public void delete(String id) throws Exception {
		sysongyChargeCardMapper.deleteSysongyChargeCard(id);
		
	}

	@Override
	public Integer update(SysongyChargeCard obj) throws Exception {
		return sysongyChargeCardMapper.updateSysongyChargeCard(obj);	
	}

	@Override
	public List<SysongyChargeCard> getSysongyChargeCardList(SysongyChargeCard obj) throws Exception {
		List<SysongyChargeCard> list = sysongyChargeCardMapper.querySysongyChargeCard(obj);
		return list;
	}

	@Override
	public int add(SysongyChargeCard obj) throws Exception {
		return sysongyChargeCardMapper.addSysongyChargeCard(obj);
	}

	@Override
	public String saveChargeCard(SysongyChargeCard obj, String operation)throws Exception {
		if("insert".equals(operation)){
			 return this.save(obj).toString();
		}else{
			return this.update(obj).toString();
		}
	}

	@Override
	public SysongyChargeCard querySysongyChargeCard(String ownid) {
		return sysongyChargeCardMapper.querySysongyChargeCardvalue(ownid);
		
	}

	@Override
	public List<SysongyChargeCard> selectSysongyChargeCard(
			SysongyChargeCard obj) throws Exception {
		return sysongyChargeCardMapper.selectSysongyChargeCardLike(obj);
		
	}

	@Override
	public int updateSysongyChargeCard(SysongyChargeCard obj) throws Exception {
		return sysongyChargeCardMapper.updateSysongyChargeCard(obj);
	}

	@Override
	public SysongyChargeCard queryCard(SysongyChargeCard obj) throws Exception {
		return sysongyChargeCardMapper.queryisExist(obj);
	
	}
}
