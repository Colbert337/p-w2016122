package com.sysongy.poms.permi.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.dao.SysUserAccountStrMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.model.SysUserAccountStr;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.pojo.AliShortMessageBean;

@Service
public class SysUserAccountServiceImpl implements SysUserAccountService {
	
	@Autowired
	private SysUserAccountMapper sysUserAccountMapper;
	@Autowired
	private SysUserAccountStrMapper sysUserAccountStrMapper;
	@Autowired
	private GasCardService gasCardService;
	@Autowired
	private DriverService driverService;

	/**
	 * 冻结账户和卡号
	 * @param accountid
	 * @param status 状态 0 冻结账户 1 冻结卡 2 解除挂失(全部解除挂失)
	 * @param cardno
	 * @return
     * @throws Exception
     */
	@Override
	public int changeStatus(String accountid, String status, String cardno) throws Exception{
		
		SysUserAccount record = new SysUserAccount();
		record.setSysUserAccountId(accountid);
		record.setAccount_status(status);
		
		SysDriver driver = new SysDriver();
		driver.setSysUserAccountId(accountid);
		
		List<SysDriver> driverlist = driverService.queryeSingleList(driver);
		
		if(driverlist.size()!=1){
			throw new Exception(this.getClass().getName()+"找不到唯一对应的司机用户");
		}
		
		driver = driverlist.get(0);
		
		String sendMsg = "用户";
		String sendCode = driver.getMobilePhone();
		
		if(!"0".equals(status)){
			
			GasCard gasCard = new GasCard();
			
			gasCard.setCard_no(cardno);
			if("1".equals(status)){
				gasCard.setCard_status(GlobalConstant.CardStatus.PAUSED);
			}else{
				gasCard.setCard_status(GlobalConstant.CardStatus.USED);
			}
			
			
			gasCardService.updateByPrimaryKeySelective(gasCard);
			
			sendMsg = "卡";
			sendCode = cardno;
		}

		int retValue =sysUserAccountMapper.updateByPrimaryKeySelective(record);
		
		if(!"2".equals(status)){
			AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
			aliShortMessageBean.setSendNumber(driver.getMobilePhone());
			aliShortMessageBean.setString(sendMsg);
			aliShortMessageBean.setCode(sendCode);
			aliShortMessageBean.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.CARD_FROZEN);
		}
		
		 
		return retValue;
	}

	@Override
	public int deleteByPrimaryKey(String sysUserAccountId) {
		return sysUserAccountMapper.deleteByPrimaryKey(sysUserAccountId);
	}

	@Override
	public int insert(SysUserAccount record) {
		return sysUserAccountMapper.insert(record);
	}

	@Override
	public SysUserAccount selectByPrimaryKey(String sysUserAccountId) {
		return sysUserAccountMapper.selectByPrimaryKey(sysUserAccountId);
	}

	@Override
	public int updateAccount(SysUserAccount record) {
		return sysUserAccountMapper.updateAccount(record);
	}

	private String getHaveConsumeFromOrder(String order_type,SysUserAccount sysUserAccount) throws Exception{
		if(GlobalConstant.OrderType.CHARGE_TO_DRIVER.equalsIgnoreCase(order_type)
		   ||GlobalConstant.OrderType.CHARGE_TO_GASTATION.equalsIgnoreCase(order_type)		
		   ||GlobalConstant.OrderType.CHARGE_TO_TRANSPORTION.equalsIgnoreCase(order_type)){
			return GlobalConstant.HAVE_CONSUME_NO;
		}
		
		if(GlobalConstant.OrderType.CONSUME_BY_DRIVER.equalsIgnoreCase(order_type)
		   ||GlobalConstant.OrderType.CONSUME_BY_TRANSPORTION.equalsIgnoreCase(order_type) ){
					return GlobalConstant.HAVE_CONSUME_YES;
		}
		return sysUserAccount.getHave_consume();
	}
	
	/**
	 * 更新现金到此账户，实现乐观锁
	 * Cash -- 正值 则是增加，负值则是减少
	 */
	@Override
	public synchronized String addCashToAccount(String accountId, BigDecimal cash, String order_type) throws Exception {
		SysUserAccount sysUserAccount = sysUserAccountMapper.selectByPrimaryKey(accountId);
		BigDecimal balance = new BigDecimal(sysUserAccount.getAccountBalance()) ;
		//在此增加金额，如果是负值则是冲红或者消费,仍然用add。
		BigDecimal balance_result = balance.add(cash);
		//如果余额变成负值，则抛出错误:余额不足
		if(balance_result.compareTo(new BigDecimal(0))<0){
			throw new Exception( GlobalConstant.OrderProcessResult.ORDER_ERROR_BALANCE_IS_NOT_ENOUGH);
		}
		//保留两位小数，四舍五入---去掉这段逻辑，不要四舍五入了
		//balance_result =  balance_result.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		sysUserAccount.setAccountBalance(balance_result.toString());
		sysUserAccount.setUpdatedDate(new Date());
		String have_consume = getHaveConsumeFromOrder(order_type,sysUserAccount);
		sysUserAccount.setHave_consume(have_consume);
		//对version加1
		int ver = sysUserAccount.getVersion();
		sysUserAccount.setVersion(ver+1);
		//更新此account对象则保存到db中
		int upRow = sysUserAccountMapper.updateAccountBalance(sysUserAccount);
		//添加轨迹信息
		SysUserAccountStr str = new SysUserAccountStr();
		BeanUtils.copyProperties(sysUserAccount, str);
		str.setResouceCode(order_type);
		str.setAccountStatus(sysUserAccount.getAccount_status());
		sysUserAccountStrMapper.insert(str);
		
		if(upRow==1){
			return GlobalConstant.OrderProcessResult.SUCCESS;	
		}else{
			//upRow不是1就是0,0条的原因是version已经改变
			//TODO 乐观所处理
			throw new Exception( GlobalConstant.OrderProcessResult.ORDER_ACCOUNT_VERSION_HAVE_CHANGED);
		}
	}

	@Override
	public SysUserAccount queryUserAccountByStationId(String sysTransportionId) {
		return sysUserAccountMapper.queryUserAccountByStationId(sysTransportionId);
	}

	@Override
	public SysUserAccount queryUserAccountByDriverId(String sysDriverId) {
		return sysUserAccountMapper.queryUserAccountByDriverId(sysDriverId);
	}

	@Override
	public SysUserAccount queryUserAccountByGas(String channelNumber) {
		// TODO Auto-generated method stub
		return sysUserAccountMapper.queryUserAccountByGas(channelNumber);
	}
}
