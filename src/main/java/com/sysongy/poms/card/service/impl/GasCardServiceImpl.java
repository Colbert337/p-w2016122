package com.sysongy.poms.card.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.card.dao.GasCardLogMapper;
import com.sysongy.poms.card.dao.GasCardMapper;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.model.GasCardLog;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.util.GlobalConstant;

@Service
public class GasCardServiceImpl implements GasCardService{
	
	@Autowired
	private GasCardMapper gasCardMapper;
	@Autowired
	private GasCardLogMapper gasCardLogMapper;

	@Override
	public PageInfo<GasCard> queryGasCard(GasCard cascard) throws Exception{
		
		PageHelper.startPage(cascard.getPageNum(), cascard.getPageSize(), cascard.getOrderby());
		List<GasCard> list = gasCardMapper.queryForPage(cascard);
		PageInfo<GasCard> pageInfo = new PageInfo<GasCard>(list);
		
		return pageInfo;
	}

	@Override
	public Integer saveGasCard(GasCard gascard) throws Exception{
		Integer ret = 0;
		if(gascard != null && !"".equals(gascard)){
			
			if(StringUtils.isEmpty(gascard.getCard_no_arr())){
				throw new Exception("没有可入库的卡号");
			}
				
				String []tmp = gascard.getCard_no_arr().split(",");

				List<GasCard> recordlist = new ArrayList<GasCard>();
				List<GasCardLog> loglist = new ArrayList<GasCardLog>();
								
				for(int i=0;i<tmp.length;i++){
					GasCard card = new GasCard();
					BeanUtils.copyProperties(gascard, card);
					
					card.setCard_status(GlobalConstant.CardStatus.STORAGED);
					card.setCard_no(tmp[i]);
					card.setStorage_time(new Date());
					card.setBatch_no(new SimpleDateFormat("YYYYMMddHHmmss").format(new Date()));
					recordlist.add(card);
					//写日志表
					GasCardLog gascardlog = new GasCardLog();
					BeanUtils.copyProperties(card, gascardlog);
					gascardlog.setAction(GlobalConstant.CardAction.ADD);
					gascardlog.setOptime(new Date());
					loglist.add(gascardlog);
				}
				
				ret = gasCardMapper.insertBatch(recordlist);
				gasCardLogMapper.insertBatch(loglist);
		}
		
		return ret;
	}

	@Override
	public Integer deleteGasCard(String cardno, CurrUser user) throws Exception{
		 GasCard card = gasCardMapper.selectByPrimaryKey(cardno);
		 Integer ret = gasCardMapper.deleteByPrimaryKey(cardno);
		 if(ret > 0){
			//写日志表
			 GasCardLog log = new GasCardLog();
			 BeanUtils.copyProperties(card, log);
			 log.setOperator(user.getUser().getUserName());
			 log.setOptime(new Date());
			 log.setAction(GlobalConstant.CardAction.DELETE);
			 gasCardLogMapper.insert(log);
		 }
		 return ret;
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
		String cardstatus="";
		
		//未查到卡信息或者已经出库了的卡
		if(card == null || !StringUtils.isEmpty(card.getWorkstation())){
			return cardstatus;
		}
				
		switch (card.getCard_status()) {
		case GlobalConstant.CardStatus.STORAGED:
			cardstatus = "已入库";
			break;
		default:
			break;
		}
		
		return cardstatus;
	}

	@Override
	public Integer updateAndMoveCard(GasCard gascard) throws Exception {
		
		Integer ret = 0;
		
		if(gascard != null && !"".equals(gascard)){
			
				String []tmp = gascard.getCard_no_arr().split(",");
								
				for(int i=0;i<tmp.length;i++){
					GasCard card = new GasCard();
					card.setCard_no(tmp[i]);
					card.setCard_status(GlobalConstant.CardStatus.MOVED);
					card.setWorkstation(gascard.getWorkstation());
					card.setWorkstation_resp(gascard.getWorkstation_resp());
					card.setRelease_time(new Date());
					card.setOperator(gascard.getOperator());
					
					Integer count = gasCardMapper.updateByPrimaryKeySelective(card);
					 if(count > 0){
						//写日志表
						 GasCard cardtmp = gasCardMapper.selectByPrimaryKey(card.getCard_no());
						 GasCardLog log = new GasCardLog();
						 BeanUtils.copyProperties(cardtmp, log);
						 log.setOptime(new Date());
						 log.setAction(GlobalConstant.CardAction.UPDATE);
						 gasCardLogMapper.insert(log);
					 }
					 ret++;
				}
		}
		return ret;
	}

	@Override
	public PageInfo<GasCardLog> queryGasCardLog(GasCardLog obj) throws Exception {
		PageHelper.startPage(obj.getPageNum(), obj.getPageSize(), obj.getOrderby());
		List<GasCardLog> list = gasCardLogMapper.queryForPage(obj);
		PageInfo<GasCardLog> pageInfo = new PageInfo<GasCardLog>(list);
		
		return pageInfo;
	}
	
}
