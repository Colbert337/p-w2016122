package com.sysongy.poms.driver.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.dao.GasCardMapper;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.order.service.SysUserAccountService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;


/**
 * Created by Administrator on 2016/6/7.
 */

@Service
public class DriverServiceImpl implements DriverService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysDriverMapper sysDriverMapper;
    
    @Autowired
    private SysUserAccountMapper sysUserAccountMapper;
    
    @Autowired
    private OrderDealService orderDealService;
    
    @Autowired
    private SysUserAccountService sysUserAccountService;
    
	@Autowired
	private DriverService driverService;

	@Autowired
	private SysCashBackService sysCashBackService;
	
    @Autowired
    private GasCardMapper gasCardMapper;

    @Override
    public PageInfo<SysDriver> queryDrivers(SysDriver record) throws Exception {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
        List<SysDriver> list = sysDriverMapper.queryForPage(record);
        PageInfo<SysDriver> pageInfo = new PageInfo<SysDriver>(list);
        return pageInfo;
    }

    @Override
    public Integer saveDriver(SysDriver record, String operation) throws Exception {
        if("insert".equals(operation)){
            SysUserAccount sysUserAccount = initWalletForDriver();
            record.setSysUserAccountId(sysUserAccount.getSysUserAccountId());
            record.setCreatedDate(new Date());
            return sysDriverMapper.insert(record);
        }else{
            return sysDriverMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public Integer distributeCard(SysDriver record) throws Exception {
        if(StringUtils.isNotEmpty(record.getCardId())){
            GasCard gasCard = gasCardMapper.selectByPrimaryKey(record.getCardId());
            if(!gasCard.getCard_status().equalsIgnoreCase(InterfaceConstants.CARD_STSTUS_ALREADY_SEND)){
                logger.error("此卡状态异常，无法授权给新用户！！！！");
                return 0;
            }
            gasCard.setCard_status(InterfaceConstants.CARD_STSTUS_IN_USE);
            gasCardMapper.updateByPrimaryKeySelective(gasCard);
        }
        return sysDriverMapper.updateByPrimaryKeySelective(record);
    }

    private SysUserAccount initWalletForDriver(){
        SysUserAccount sysUserAccount = new SysUserAccount();       //初始化钱袋信息
        sysUserAccount.setSysUserAccountId(UUIDGenerator.getUUID());
        sysUserAccount.setAccountCode("DR"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        sysUserAccount.setAccountType(GlobalConstant.AccounType.DRIVER);
        sysUserAccount.setAccountBalance("0.0");
        sysUserAccount.setCreatedDate(new Date());
        sysUserAccount.setUpdatedDate(new Date());
        int ret = sysUserAccountMapper.insert(sysUserAccount);
        return sysUserAccount;

    }

    @Override
    public Integer delDriver(String sysDriverId) throws Exception {
        return sysDriverMapper.deleteByPrimaryKey(sysDriverId);
    }


    @Override
    public SysDriver queryDriverByPK(String sysDriverId) throws Exception {
        SysDriver sysDriver =  sysDriverMapper.selectByPrimaryKey(sysDriverId);
        return sysDriver;
    }


    /**
	 * 给司机充钱
	 * @param order
	 * @return
     * @throws Exception 
	 */
	public String chargeCashToDriver(SysOrder order) throws Exception{
		if (order ==null){
			   return GlobalConstant.OrderProcessResult.ORDER_IS_NULL;
		}
		
		String debit_account = order.getDebitAccount();
		if(debit_account==null ||debit_account.equalsIgnoreCase("")){
			return GlobalConstant.OrderProcessResult.DEBIT_ACCOUNT_IS_NULL;
		}
		
		//给账户充钱
		SysDriver driver = this.queryDriverByPK(debit_account);
		String driver_account = driver.getSysUserAccountId();
		BigDecimal cash = order.getCash();
		String cash_success = sysUserAccountService.addCashToAccount(driver_account,cash);
		//记录订单流水
		String remark = "给"+ driver.getFullName()+"的账户，充值"+cash.toPlainString()+"。";
		orderDealService.createOrderDeal(order.getOrderId(), GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CHARGE, remark,cash_success);
		
		return cash_success;
	}

	/**
	 * 给司机返现
	 * @param order
	 * @return
	 */
	@Override
	public String cashBackToDriver(SysOrder order) throws Exception{
		//1.判断是否首次返现，是则调用首次返现规则
		String accountId = order.getDebitAccount();
		SysDriver driver = driverService.queryDriverByPK(accountId);
        Integer is_first_charge = driver.getIsFirstCharge();
        if(is_first_charge.intValue() == GlobalConstant.FIRST_CHAGRE_YES){
        	List<SysCashBack>  cashBackList = sysCashBackService.queryCashBackByNumber(GlobalConstant.CashBackNumber.CASHBACK_FIRST_CHARGE);
        	String accountUserName = driver.getFullName();
        	String cashTo_success = sysCashBackService.cashToAccount(order, cashBackList, accountId, accountUserName, GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CASHBACK);
        	if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(cashTo_success)){
        		//如果出错，直接退出
        		return cashTo_success;
        	}
        }
		//2.根据当前订单类型，调用对应的返现规则
		//TODO
		return "";
	}
	
    public Integer isExists(SysDriver obj) throws Exception {
        return sysDriverMapper.isExists(obj);
    }

    public SysDriver queryDriverByMobilePhone(SysDriver record) throws Exception {
        SysDriver sysDriver =  sysDriverMapper.queryDriverByMobilePhone(record);
        return sysDriver;
    }
}
