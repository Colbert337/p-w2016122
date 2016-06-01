package com.sysongy.poms.card.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.card.dao.GasCardMapper;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;

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
		PageHelper.startPage(cascard.getPageNum(), cascard.getPageSize(), cascard.getOrderby());
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
				
				String []tmp = gascard.getCard_no_arr().split(",");
				
				List recordlist = new ArrayList<GasCard>();
				
				for(int i=0;i<tmp.length;i++){
					GasCard card = new GasCard();
					BeanUtils.copyProperties(gascard, card);
					card.setCard_status("1");
					card.setCard_no(tmp[i]);
					card.setStorage_time(new Date());
					recordlist.add(card);
				}
				
				ret = gasCardMapper.insertBatch(recordlist);
		}
		return ret;
	}

	@Override
	public Integer delGasCard(String cardid) throws Exception{
		if(1==1)
			throw new Exception("我是故意的在里面");
		return gasCardMapper.deleteByPrimaryKey(cardid);
	}

	@Override
	public Boolean checkCardExist(String cardno) throws Exception {
		GasCard card = gasCardMapper.selectByCardNo(cardno);
		if(card == null){
			return false;
		}
		return true;
	}

	@Override
	public String checkMoveCard(String cardno) throws Exception {
		GasCard card = gasCardMapper.selectByCardNo(cardno);
		if(card == null){
			return "";
		}
		return card.getCard_status();
	}

	@Override
	public Integer moveCard(GasCard gascard) throws Exception {
		
		Integer ret = 0;
		
		if(gascard != null && !"".equals(gascard)){
			
				String []tmp = gascard.getCard_no_arr().split(",");
								
				for(int i=0;i<tmp.length;i++){
					GasCard card = new GasCard();
					card.setCard_no(tmp[i]);
					card.setWorkstation(gascard.getWorkstation());
					card.setWorkstation_resp(gascard.getWorkstation_resp());
					card.setRelease_time(new Date());
					
					 gasCardMapper.updateByPrimaryKeySelective(card);
					 
					 ret++;
				}
		}
		return ret;
	}
	
}
