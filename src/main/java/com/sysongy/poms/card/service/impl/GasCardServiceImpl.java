package com.sysongy.poms.card.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sysongy.api.client.controller.model.CRMCardUpdateInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.sysongy.util.CommonsUtils;
import com.sysongy.util.GlobalConstant;

@Service
public class GasCardServiceImpl implements GasCardService{

	protected Logger logger = LoggerFactory.getLogger(GasCardServiceImpl.class);

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
	public PageInfo<GasCard> queryCardFor2StatusInfo(GasCard cascard) throws Exception{
		PageHelper.startPage(cascard.getPageNum(), cascard.getPageSize(), cascard.getOrderby());
		List<GasCard> list = gasCardMapper.queryCardFor2StatusInfo(cascard);
		PageInfo<GasCard> pageInfo = new PageInfo<GasCard>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<GasCard> queryGasCardForCRM(CRMCardUpdateInfo crmCardUpdateInfo) throws Exception{
		PageHelper.startPage(crmCardUpdateInfo.getPageNum(), crmCardUpdateInfo.getPageSize(), crmCardUpdateInfo.getOrderby());
		crmCardUpdateInfo.setStartID(crmCardUpdateInfo.getStartID() - 1);
		crmCardUpdateInfo.setEndID(crmCardUpdateInfo.getEndID() + 1);
		List<GasCard> list = gasCardMapper.queryGasCardForCRM(crmCardUpdateInfo);
		PageInfo<GasCard> pageInfo = new PageInfo<GasCard>(list);
		return pageInfo;
	}

	@Override
	public GasCard queryGasCardInfo(String cardNo) throws Exception{
		GasCard gasCard = gasCardMapper.selectByCardNo(cardNo);
		return gasCard;
	}

	@Override
	public GasCard selectByCardNoForCRM(String cardNo) throws Exception{
		GasCard gasCard = gasCardMapper.selectByCardNoForCRM(cardNo);
		return gasCard;
	}

	@Override
	public Integer updateGasCardInfo(GasCard cascard) throws Exception{
		GasCardLog gascardlog = new GasCardLog();
		GasCard cascardall = this.queryGasCardInfo(cascard.getCard_no());
		CommonsUtils.copyPropertiesIgnoreNull(cascard, cascardall);
		CommonsUtils.copyPropertiesIgnoreNull(cascardall, gascardlog);
		gascardlog.setAction(GlobalConstant.CardAction.ADD);
		gascardlog.setOptime(new Date());
		gasCardLogMapper.insert(gascardlog);
		int nRet = gasCardMapper.updateByPrimaryKeySelective(cascard);
		return nRet;
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
		String cardstatus="1";
		
		//未查到卡信息或者已经出库了的卡
		if(card == null || !StringUtils.isEmpty(card.getWorkstation())){
			return cardstatus;
		}
				
		switch (card.getCard_status()) {
		case GlobalConstant.CardStatus.STORAGED:
			cardstatus = "0";
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
					
					card = gasCardMapper.selectByCardNo(card.getCard_no());
					
					if(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_DRIVER.equals(card.getCard_property())){
						card.setCard_status(GlobalConstant.CardStatus.MOVED);
					}else{
						card.setCard_status(GlobalConstant.CardStatus.PROVIDE);
					}
					
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

	@Override
	public Integer updateGasCardStatus(CRMCardUpdateInfo crmCardUpdateInfo) throws Exception{
		crmCardUpdateInfo.setStartID(crmCardUpdateInfo.getStartID() - 1);
		crmCardUpdateInfo.setEndID(crmCardUpdateInfo.getEndID() + 1);
		int nRet = gasCardMapper.updateCardStatus(crmCardUpdateInfo);
		int nLRet =gasCardLogMapper.batchInsertFromCRM(crmCardUpdateInfo);
		if(nLRet < 1){
			logger.error("卡日志生成失败, from: " + crmCardUpdateInfo.getStartID() + "to: " + crmCardUpdateInfo.getEndID());
		}
		return nRet;
	}

	public PageInfo<GasCard> queryGasCardForUpdate(CRMCardUpdateInfo obj) throws Exception{
		obj.setStartID(obj.getStartID() - 1);
		obj.setEndID(obj.getEndID() + 1);
		PageHelper.startPage(obj.getPageNum(), obj.getPageSize(), obj.getOrderby());
		List<GasCard> list = gasCardMapper.queryForPageUpdate(obj);
		PageInfo<GasCard> pageInfo = new PageInfo<GasCard>(list);
		return pageInfo;
	}

	@Override
	public int updateByPrimaryKeySelective(GasCard record) throws Exception {
		return gasCardMapper.updateByPrimaryKeySelective(record);
	}
}
