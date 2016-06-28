package com.sysongy.poms.driver.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sysongy.poms.card.dao.GasCardLogMapper;
import com.sysongy.poms.card.model.GasCardLog;
import com.sysongy.poms.permi.service.SysUserAccountService;

import org.apache.commons.beanutils.BeanUtils;
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
import com.sysongy.poms.driver.dao.SysDriverReviewStrMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.model.SysDriverReviewStr;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderDealService;
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
	private SysCashBackService sysCashBackService;
	
    @Autowired
    private GasCardMapper gasCardMapper;
    
    @Autowired
    private SysDriverReviewStrMapper sysDriverReviewStrMapper;

    @Autowired
    private GasCardLogMapper gasCardLogMapper;

    @Override
    public PageInfo<SysDriver> queryDrivers(SysDriver record) throws Exception {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
        List<SysDriver> list = sysDriverMapper.queryForPage(record);
        PageInfo<SysDriver> pageInfo = new PageInfo<SysDriver>(list);
        return pageInfo;
    }
    
    @Override
    public PageInfo<SysDriverReviewStr> queryDriversLog(SysDriverReviewStr record) throws Exception {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
        List<SysDriverReviewStr> list = sysDriverReviewStrMapper.queryForPage(record);
        PageInfo<SysDriverReviewStr> pageInfo = new PageInfo<SysDriverReviewStr>(list);
        return pageInfo;
    }

    @Override
    public Integer saveDriver(SysDriver record, String operation) throws Exception {
        if("insert".equals(operation)){
            SysUserAccount sysUserAccount = initWalletForDriver();
            record.setSysUserAccountId(sysUserAccount.getSysUserAccountId());
            sysUserAccount.setAccount_status(GlobalConstant.AccountStatus.NORMAL);
            record.setCreatedDate(new Date());
            return sysDriverMapper.insertSelective(record);
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

            GasCardLog gascardlog = new GasCardLog();
            org.springframework.beans.BeanUtils.copyProperties(gasCard, gascardlog);
            gascardlog.setAction(GlobalConstant.CardAction.ADD);
            gascardlog.setOptime(new Date());
            int nRet = gasCardLogMapper.insert(gascardlog);
            if(nRet < 1){
                logger.error("记录卡轨迹出错， ID：" + gasCard.getCard_no());
            }
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

    @Override
    public PageInfo<SysDriver> querySearchDriverList(SysDriver record) {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
        List<SysDriver> sysDriverList =  sysDriverMapper.querySearchDriverList(record);
        PageInfo<SysDriver> pageInfo = new PageInfo<SysDriver>(sysDriverList);

        return pageInfo;
    }

    /**
	 * 给司机充值/充红
	 * @param order
	 * @return
     * @throws Exception 
	 */
	public String chargeCashToDriver(SysOrder order, String is_discharge) throws Exception{
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
		//如果是负值，但是is_discharge却不是充红，则返回错误
		if(cash.compareTo(new BigDecimal("0")) < 0 ){
			if(is_discharge !=null && (!is_discharge.equalsIgnoreCase(GlobalConstant.ORDER_ISCHARGE_YES))){
				   return GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_DISCHARGE;
			 }
		}
		String cash_success = sysUserAccountService.addCashToAccount(driver_account,cash);
		//记录订单流水
		String chong = "充值";
		String orderDealType = GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CHARGE;
		if(GlobalConstant.ORDER_ISCHARGE_YES.equalsIgnoreCase(is_discharge)){
			chong ="充红";
			orderDealType = GlobalConstant.OrderDealType.DISCHARGE_TO_DRIVER_CHARGE;
		}
		String remark = "给"+ driver.getFullName()+"的账户，"+chong+cash.toString()+"。";
		orderDealService.createOrderDeal(order.getOrderId(), orderDealType, remark,cash_success);
		
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
		SysDriver driver = sysDriverMapper.selectByPrimaryKey(accountId);
		String accountUserName = driver.getFullName();
        Integer is_first_charge = driver.getIsFirstCharge();
        if(is_first_charge.intValue() == GlobalConstant.FIRST_CHAGRE_YES){
        	List<SysCashBack>  cashBackList = sysCashBackService.queryCashBackByNumber(GlobalConstant.CashBackNumber.CASHBACK_FIRST_CHARGE);
        	String cashTo_success = sysCashBackService.cashToAccount(order, cashBackList, accountId, accountUserName, GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK);
        	if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(cashTo_success)){
        		//如果出错，直接退出
        		return cashTo_success;
        	}
        }
		//2.根据当前充值类型，调用对应的返现规则
        String charge_type = order.getChargeType();
    	List<SysCashBack>  cashBackList_specific_type = sysCashBackService.queryCashBackByNumber(charge_type);
    	String cashTo_success_specific_type = sysCashBackService.cashToAccount(order, cashBackList_specific_type, accountId, accountUserName, GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CASHBACK);
    	if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(cashTo_success_specific_type)){
    		//如果出错，直接退出
    		return cashTo_success_specific_type;
    	}
		return cashTo_success_specific_type;
	}
	
    public Integer isExists(SysDriver obj) throws Exception {
        return sysDriverMapper.isExists(obj);
    }

    public SysDriver queryDriverByMobilePhone(SysDriver record) throws Exception {
        SysDriver sysDriver =  sysDriverMapper.queryDriverByMobilePhone(record);
        return sysDriver;
    }

	@Override
	public Integer updateAndReview(String driverid, String type,String memo) throws Exception {
		SysDriver record = new SysDriver();
		record.setSysDriverId(driverid);
		record = sysDriverMapper.selectByPrimaryKey(driverid);
		
		record.setCheckedStatus(type);
		record.setCheckedDate(new Date());
		record.setUpdatedDate(new Date());
		record.setMemo(memo);
		sysDriverMapper.updateByPrimaryKeySelective(record);
			
		SysDriverReviewStr log = new SysDriverReviewStr();
		BeanUtils.copyProperties(log, record);
		return sysDriverReviewStrMapper.insert(log);
	}

    /**
     * 批量离职司机
     * @param idList
     * @return
     */
    @Override
    public int deleteDriverByIds(List<String> idList) {
        return sysDriverMapper.deleteDriverByIds(idList);
    }
}
