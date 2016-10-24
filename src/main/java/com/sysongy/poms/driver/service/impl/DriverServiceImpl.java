package com.sysongy.poms.driver.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.dao.GasCardLogMapper;
import com.sysongy.poms.card.dao.GasCardMapper;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.model.GasCardLog;
import com.sysongy.poms.coupon.model.UserCoupon;
import com.sysongy.poms.coupon.service.CouponService;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.dao.SysDriverReviewStrMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.model.SysDriverReviewStr;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import com.sysongy.util.pojo.AliShortMessageBean;


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
    
    @Autowired
    private CouponService couponService;

    @Override
    public PageInfo<SysDriver> queryDrivers(SysDriver record) throws Exception {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
        List<SysDriver> list = sysDriverMapper.queryForPage(record);
        PageInfo<SysDriver> pageInfo = new PageInfo<SysDriver>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<SysDriver> querySingleDriver(SysDriver record) throws Exception {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
        List<SysDriver> list = sysDriverMapper.querySingleDriver(record);
        PageInfo<SysDriver> pageInfo = new PageInfo<SysDriver>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<SysDriver> ifExistDriver(SysDriver record) throws Exception {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
        List<SysDriver> list = sysDriverMapper.ifExistDriver(record);
        PageInfo<SysDriver> pageInfo = new PageInfo<SysDriver>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<SysDriver> queryForPageSingleList(SysDriver record) throws Exception {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
        List<SysDriver> list = sysDriverMapper.queryForPage(record);
        PageInfo<SysDriver> pageInfo = new PageInfo<SysDriver>(list);
        return pageInfo;
    }
    
    @Override
    public List<SysDriver> queryeSingleList(SysDriver record) throws Exception {
         return sysDriverMapper.queryForPage(record);
    }

    @Override
    public List<SysDriver> queryForPageList(SysDriver record) throws Exception {
        return sysDriverMapper.queryForPageList(record);
    }

    @Override
    public PageInfo<SysDriverReviewStr> queryDriversLog(SysDriverReviewStr record) throws Exception {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
        List<SysDriverReviewStr> list = sysDriverReviewStrMapper.queryForPage(record);
        PageInfo<SysDriverReviewStr> pageInfo = new PageInfo<SysDriverReviewStr>(list);
        return pageInfo;
    }

    @Override
    public Integer saveDriver(SysDriver record, String operation, String invitationCode) throws Exception {
        if("insert".equals(operation)){
            SysUserAccount sysUserAccount = initWalletForDriver();
            record.setSysUserAccountId(sysUserAccount.getSysUserAccountId());
            sysUserAccount.setAccount_status(GlobalConstant.AccountStatus.NORMAL);
            record.setCreatedDate(new Date());
			if(invitationCode != null && !"".equals(invitationCode)){
				record.setRegisCompany(invitationCode);//存储邀请人邀请码
			}

            int count = sysDriverMapper.insertSelective(record);
            //如果有要邀请码，要进行返现，现在先写死，以后走订单
            //返现转移，用户登录后进行返现
            //if(!StringUtils.isEmpty(invitationCode)){
            // this.cashBackForRegister(record, invitationCode);
            //}
            return count;
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
            gasCard.setStation_receive_time(new Date());

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
//        sysUserAccount.setAccountCode("DR"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        sysUserAccount.setAccountCode("DR"+UUIDGenerator.getUUID());
        sysUserAccount.setAccountType(GlobalConstant.AccounType.DRIVER);
        sysUserAccount.setAccountBalance("0.0");
        sysUserAccount.setCreatedDate(new Date());
        sysUserAccount.setUpdatedDate(new Date());
        sysUserAccount.setAccount_status(GlobalConstant.AccountStatus.NORMAL);
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
	 * 给司机充值/充红/转账，增加钱
	 * @param order
	 * @return
     * @throws Exception 
	 */
	public String chargeCashToDriver(SysOrder order, String is_discharge) throws Exception{
		if (order ==null){
			throw new Exception(GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
		}
		
		String debit_account = order.getDebitAccount();
		if(debit_account==null ||debit_account.equalsIgnoreCase("")){
			throw new Exception(GlobalConstant.OrderProcessResult.DEBIT_ACCOUNT_IS_NULL);
		}
		
		//给账户充钱
		SysDriver driver = this.queryDriverByPK(debit_account);
		String driver_account = driver.getSysUserAccountId();
		BigDecimal cash = order.getCash();
		//如果是负值，但是is_discharge却不是充红，则返回错误
		if(cash.compareTo(new BigDecimal("0")) < 0 ){
			if(is_discharge !=null && (!is_discharge.equalsIgnoreCase(GlobalConstant.ORDER_ISCHARGE_YES))){
				throw new Exception( GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_DISCHARGE);
			 }
		}
		//driver_account="abcd";		
		String cash_success = sysUserAccountService.addCashToAccount(driver_account,cash,order.getOrderType());
		//记录订单流水
		String chong = "充值";
		String orderDealType = GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CHARGE;
		if(GlobalConstant.ORDER_ISCHARGE_YES.equalsIgnoreCase(is_discharge)){
			chong ="充红";
			orderDealType = GlobalConstant.OrderDealType.DISCHARGE_TO_DRIVER_CHARGE;
		}
		if(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER.equalsIgnoreCase(order.getOrderType())){
			chong = "转账";
			orderDealType = GlobalConstant.OrderDealType.TRANSFER_TRANSPORTION_TO_DRIVER_INCREASE_DRIVER;
		}
		if(GlobalConstant.OrderType.TRANSFER_DRIVER_TO_DRIVER.equalsIgnoreCase(order.getOrderType())){
			chong = "转账";
			orderDealType = GlobalConstant.OrderDealType.TRANSFER_DRIVER_TO_DRIVER_DEDUCT_DRIVER;
		}

        String remark = null;
        if(StringUtils.isEmpty(driver.getFullName())){
            remark = "给"+ driver.getMobilePhone() +"的账户，"+chong+cash.toString()+"。";
        } else {
            remark = "给"+ driver.getFullName()+"的账户，"+chong+cash.toString()+"。";
        }
		orderDealService.createOrderDeal(order.getOrderId(), orderDealType, remark, cash_success);
		return cash_success;
	}
	
	/**
	 * 扣除司机账户金额
	 * 转账：扣除A司机
	 * 消费：扣除
	 *     消费充红：is_discharge是yes，则
	 * @param order
	 * @return
     * @throws Exception 
	 */
	public String deductCashToDriver(SysOrder order, String is_discharge) throws Exception{
		if (order ==null){
			throw new Exception( GlobalConstant.OrderProcessResult.ORDER_IS_NULL);
		}
		
		String credit_account = order.getCreditAccount();
		if(credit_account==null || credit_account.equalsIgnoreCase("")){
			throw new Exception( GlobalConstant.OrderProcessResult.ORDER_ERROR_CREDIT_ACCOUNT_IS_FROEN);
		}
		
		//给账户减去
		SysDriver driver = this.queryDriverByPK(credit_account);
		String driver_account = driver.getSysUserAccountId();
		BigDecimal cash = order.getCash();
		//因为这个步骤是扣除，订单传过来的cash是正值，则是正常扣除(用于跟人对个人转账的时候，扣除转出账户的钱，还有个人消费的时候也是正值)，如果是负值，则是充红扣除（个人消费的时候充红），负负得正
		BigDecimal addcash = cash.multiply(new BigDecimal(-1));
		//如果是负值，但是is_discharge却不是充红，则返回错误
		if(cash.compareTo(new BigDecimal("0")) < 0 ){
			if(is_discharge !=null && (!is_discharge.equalsIgnoreCase(GlobalConstant.ORDER_ISCHARGE_YES))){
				throw new Exception( GlobalConstant.OrderProcessResult.ORDER_TYPE_IS_NOT_DISCHARGE);
			 }
		}
		String cash_success = sysUserAccountService.addCashToAccount(driver_account,addcash,order.getOrderType());
		//记录订单流水
		String chong = "转账扣钱";
		String orderDealType = GlobalConstant.OrderDealType.TRANSFER_DRIVER_TO_DRIVER_INCREASE_DRIVER;
		
		String orderType = order.getOrderType();
		if(GlobalConstant.OrderType.CONSUME_BY_DRIVER.equalsIgnoreCase(orderType)){
			chong ="消费";
			orderDealType = GlobalConstant.OrderDealType.CONSUME_DRIVER_DEDUCT;
		}
		if(GlobalConstant.OrderType.CONSUME_BY_DRIVER.equalsIgnoreCase(orderType) && GlobalConstant.ORDER_ISCHARGE_YES.equalsIgnoreCase(is_discharge)){
			chong ="消费冲红";
			orderDealType = GlobalConstant.OrderDealType.DISCONSUME_DRIVER_DEDUCT;
		}
		
		String preferential_cash = "";
		if(order.getPreferential_cash().compareTo(BigDecimal.valueOf(0.0)) > 0){
			preferential_cash = "气站优惠" + order.getPreferential_cash() + "元";
		}

        String remark = "";
        if(StringUtils.isEmpty(order.getDischarge_reason())){
            remark = driver.getFullName()+"的账户，"+chong+cash.toString() +","+ preferential_cash + "。";
        } else {
            remark = driver.getFullName()+"的账户，"+chong+cash.toString() +","+ preferential_cash + "。" + order.getDischarge_reason();
        }
        
        UserCoupon usercoupon = couponService.queryUserCouponByNo(order.getCoupon_number(), order.getSysDriver().getSysDriverId());
        
        if(usercoupon == null){
        	order.setCoupon_number("");
        	order.setCoupon_cash(BigDecimal.valueOf(0.0d));
        	logger.info("根据"+order.getCoupon_number()+"找不到对应的优惠劵信息");
        }else{
        	usercoupon.setIsuse(GlobalConstant.COUPON_STATUS.USED);
            couponService.modifyUserCoupon(usercoupon, order.getOperator());
        }
        
        if(!(StringUtils.isEmpty(order.getCoupon_number()) && (StringUtils.isEmpty(order.getCoupon_cash().toString()))) && usercoupon != null){
            remark = remark + "使用优惠劵优惠"+order.getCoupon_cash().toString()+"元。";
        }

		orderDealService.createOrderDeal(order.getOrderId(), orderDealType, remark,cash_success);
        order.setDischarge_reason(remark);
        
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
		String debitUserID = order.getDebitAccount();
		SysDriver driver = sysDriverMapper.selectByPrimaryKey(debitUserID);
        String accountUserName = null;
        if(StringUtils.isEmpty(driver.getFullName())){
            accountUserName = driver.getMobilePhone();
        } else {
            accountUserName = driver.getFullName();
        }
        Integer is_first_charge = driver.getIsFirstCharge();
        String accountId = driver.getSysUserAccountId();
        
        if(is_first_charge.intValue() == GlobalConstant.FIRST_CHAGRE_YES){
        	List<SysCashBack>  cashBackList = sysCashBackService.queryCashBackByNumber(GlobalConstant.CashBackNumber.CASHBACK_FIRST_CHARGE);
        	String cashTo_success = sysCashBackService.cashToAccount(order, cashBackList, accountId, accountUserName, GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK);
        	if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(cashTo_success)){
        		//如果出错，直接退出
        		throw new Exception( cashTo_success);
        	}else{
        		//首次充值返现成功后，将driver的is_first_charge字段修改为NO
        		driver.setIsFirstCharge(GlobalConstant.FIRST_CHAGRE_NO);
        		sysDriverMapper.updateFirstCharge(driver);        		
        	}
        }
		//2.根据当前充值类型，调用对应的返现规则
        String charge_type = order.getChargeType();
    	List<SysCashBack>  cashBackList_specific_type = sysCashBackService.queryCashBackByNumber(charge_type);
    	String driver_accountId = driver.getSysUserAccountId();
    	String cashTo_success_specific_type = sysCashBackService.cashToAccount(order, cashBackList_specific_type, driver_accountId, accountUserName, GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CASHBACK);
    	if(!GlobalConstant.OrderProcessResult.SUCCESS.equalsIgnoreCase(cashTo_success_specific_type)){
    		//如果出错，直接退出
    		throw new Exception( cashTo_success_specific_type);
    	}
		return cashTo_success_specific_type;
	}
	
	public void cashBackForRegister(SysDriver driver, String invitationCode) throws Exception{
		
		SysDriver invitation = new SysDriver();
		invitation.setInvitationCode(invitationCode);
		
		List<SysDriver> invitationList = sysDriverMapper.queryForPage(invitation);
		
		if(invitationList.size() != 1){
			logger.info("通过邀请码找不到对应的司机用户,注册成功，返现失败");
		}else{
			invitation = invitationList.get(0);
	
	    	sysUserAccountService.addCashToAccount(driver.getSysUserAccountId(), BigDecimal.valueOf(10.00), GlobalConstant.OrderType.REGISTER_CASHBACK);
	    	sysUserAccountService.addCashToAccount(invitation.getSysUserAccountId(), BigDecimal.valueOf(10.00), GlobalConstant.OrderType.INVITED_CASHBACK);
		}
	}
	
    public Integer isExists(SysDriver obj) throws Exception {
        return sysDriverMapper.isExists(obj);
    }

    public SysDriver queryDriverByMobilePhone(SysDriver record) throws Exception {
        SysDriver sysDriver = sysDriverMapper.queryDriverByMobilePhone(record);
        return sysDriver;
    }

	@Override
	public Integer updateAndReview(String driverid, String type,String memo, String operator) throws Exception {
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
		log.setOperator(operator);
		
		int retn = sysDriverReviewStrMapper.insert(log);
		
		if(GlobalConstant.DriverStatus.PASSED.equals(type)){
			AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
			aliShortMessageBean.setSendNumber(record.getMobilePhone());
			aliShortMessageBean.setString("已");
			AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.DRIVER_AUDIT_SUCCESS);
		}else if(GlobalConstant.DriverStatus.NOPASS.equals(type)){
			AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
			aliShortMessageBean.setSendNumber(record.getMobilePhone());
			aliShortMessageBean.setString("未");
			AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.DRIVER_AUDIT_SUCCESS);
		}
		
		return retn;
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

    @Override
    public SysDriver queryMaxIndex() {
        String provinceId = "%P%";
        return sysDriverMapper.queryMaxIndex(provinceId);
    }

	@Override
	public SysDriver queryByInvitationCode(String invitationCode) throws Exception {
		return sysDriverMapper.queryByInvitationCode(invitationCode);
	}
	/**
	 * 用户登录
	 */
	@Override
	public SysDriver queryByUserNameAndPassword(SysDriver record) throws Exception {
		return sysDriverMapper.queryByUserNameAndPassword(record);
	}

	@Override
	public SysDriver queryByUserName(SysDriver record) throws Exception {
		return sysDriverMapper.queryByUserName(record);
	}

	@Override
	public SysDriver queryByDeviceToken(String deviceToken) throws Exception {
		return sysDriverMapper.queryByDeviceToken(deviceToken);
	}

	@Override
	public List<SysDriver> queryAll() throws Exception {
		return sysDriverMapper.queryAll();
	}
}
