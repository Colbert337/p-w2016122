package com.sysongy.poms.card.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.card.dao.GasCardMapper;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.util.UUIDGenerator;

@Service
public class GasCardServiceImpl implements GasCardService{
	
	@Autowired
	private GasCardMapper gasCardMapper;

	@Override
	public PageInfo<GasCard> queryGasCard(GasCard cascard) throws Exception{
		
//		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();;
//		SqlSessionFactory sessionFactory = (SqlSessionFactory) wac.getBean("sqlSessionFactory");
//		SqlSession session = sessionFactory.openSession();;
//		SqlMapper sqlMapper = new SqlMapper(session);;
//		String excuteSQL = "SELECT * FROM GAS_CARD";
//		PageHelper.startPage(1, 5);
		PageHelper.startPage(cascard.getPageNum(), cascard.getPageSize());
//		List<GasCard> list = sqlMapper.selectList(excuteSQL, GasCard.class);
//		long total = this.count(excuteSQL);
		
		List<GasCard> list = gasCardMapper.queryForPage(cascard);
		//long total = this.count(excuteSQL);
		
		PageInfo<GasCard> pageInfo = new PageInfo<GasCard>(list);
		
//		PageBean pageBean = new PageBean();
//		pageBean.setList(list);
//		pageBean.setModel(cascard);
//		pageBean.setTotal(total);
		
		return pageInfo;
	}

	@Override
	public Integer saveGasCard(GasCard gascard) throws Exception{
		Integer ret = 0;
		if(gascard != null && !"".equals(gascard)){
			if(gascard.getCard_id() != null && !"".equals(gascard.getCard_id())){
				//修改
				ret = gasCardMapper.updateByPrimaryKey(gascard);
			}else{
				//新增
				gascard.setCard_id(UUIDGenerator.getUUID());
				gascard.setCard_status(1);
				ret = gasCardMapper.insert(gascard);
			}
		}
		return ret;
	}

	@Override
	public Integer delGasCard(String cardid) throws Exception{
		return gasCardMapper.deleteByPrimaryKey(cardid);
	}
	
}
