package com.sysongy.poms.order.model;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.coupon.model.Coupon;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.usysparam.model.Usysparam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SysOrder extends BaseModel{

    private String orderId;

    private String orderNumber;

    private String orderType;

    private Date orderDate;

    private BigDecimal cash;

    private String creditAccount;

    private String debitAccount;

    private String chargeType;
    
    private String spend_type;//消费支付类型

    private String channel;

    private String channelNumber;

    private String operator;

    private String operatorTargetType;
    
    private String operatorSourceType;

    private String dischargeOrderId;
    
	private String is_discharge;
	
	private String been_discharged;

	private String discharge_reason;
	
	private String operatorSourceId;

    private String consume_card;

    private GasCard consume_cardInfo;

    private List<SysOrderGoods> sysOrderGoods;

    private String cashBack;

    private SysDriver sysDriver;

    private GasCard gasCard;

    private SysUser operatorInfo;

    private Usysparam orderTypeInfo;

    private Coupon coupon;

    private Gastation gastation;

    private Transportion transportion;

    private Usysparam chargeTypeInfo;

    private Usysparam orderStatusInfo;
    
    private SysOrderDeal order_deal;

    private String startDate;

    private String endDate;

    private String consumeType;

    private int orderStatus;

    private String thirdPartyOrderID;   //微信或者支付宝的订单号
    
    private String orderRemark; 
    
    private String type;//查询个人转账记录时，转入转出表示列，不对应数据库字段

    private String discountAmount;//优惠金额

    private String amount;//优惠后金额

    private String batch_no;
    
    public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	List<Coupon> couponlist;
    
    private String gas_station_name;//虚拟字段气站名，查询消费订单用
    private BigDecimal preferential_cash;//优惠金额，气站给的优惠

    private BigDecimal should_payment;//订单应付金额

    private String coupon_title;//优惠劵编号

    private String coupon_number;//优惠劵标题

    private String coupon_id;//优惠劵用户关系ID

    private BigDecimal coupon_cash;//优惠劵优惠金额
    
    private String begin;//开始时间，虚拟字段，充值报表时间过滤用
    
    private String end;//结束时间，虚拟字段，充值报表时间过滤用
    
    private String trade_no;//交易号

    private String mobile_phone;//存储退款（充值）司机手机号
    
    private String chk_memo;
    
    private Date chk_time;
    
    private String user_name;
    
    private String plate_number;
    
      
    public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPlate_number() {
		return plate_number;
	}

	public void setPlate_number(String plate_number) {
		this.plate_number = plate_number;
	}

	private String chk_user;
    
    public String getChk_user() {
		return chk_user;
	}

	public void setChk_user(String chk_user) {
		this.chk_user = chk_user;
	}

	public String getChk_memo() {
		return chk_memo;
	}

	public void setChk_memo(String chk_memo) {
		this.chk_memo = chk_memo;
	}

	public Date getChk_time() {
		return chk_time;
	}

	public void setChk_time(Date chk_time) {
		this.chk_time = chk_time;
	}

	public String getCreditPhone() {
		return creditPhone;
	}

	public void setCreditPhone(String creditPhone) {
		this.creditPhone = creditPhone;
	}

	private String creditPhone;//存贮退款时（消费类型）司机的手机号
    
	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public String getSpend_type() {
		return spend_type;
	}

	public void setSpend_type(String spend_type) {
		this.spend_type = spend_type;
	}

	public BigDecimal getShould_payment() {
		return should_payment;
	}

	public void setShould_payment(BigDecimal should_payment) {
		this.should_payment = should_payment;
	}

	public String getCoupon_number() {
		return coupon_number;
	}

	public void setCoupon_number(String coupon_number) {
		this.coupon_number = coupon_number;
	}

	public BigDecimal getCoupon_cash() {
		return coupon_cash;
	}

	public void setCoupon_cash(BigDecimal coupon_cash) {
		this.coupon_cash = coupon_cash;
	}

	public BigDecimal getPreferential_cash() {
		return preferential_cash;
	}

	public void setPreferential_cash(BigDecimal preferential_cash) {
		this.preferential_cash = preferential_cash;
	}

	public SysOrderDeal getOrder_deal() {
		return order_deal;
	}

	public void setOrder_deal(SysOrderDeal order_deal) {
		this.order_deal = order_deal;
	}

	public String getDischarge_reason() {
		return discharge_reason;
	}

    public String getBeen_discharged() {
        return been_discharged;
    }

    public void setBeen_discharged(String been_discharged) {
        this.been_discharged = been_discharged;
    }

	public void setDischarge_reason(String discharge_reason) {
		this.discharge_reason = discharge_reason;
	}

    public String getOperatorSourceId() {
		return operatorSourceId;
	}

	public void setOperatorSourceId(String operatorSourceId) {
		this.operatorSourceId = operatorSourceId;
	}

	public String getDischargeOrderId() {
		return dischargeOrderId;
	}

	public void setDischargeOrderId(String dischargeOrderId) {
		this.dischargeOrderId = dischargeOrderId;
	}

	public String getOperatorTargetType() {
		return operatorTargetType;
	}

	public void setOperatorTargetType(String operatorTargetType) {
		this.operatorTargetType = operatorTargetType;
	}

	public String getOperatorSourceType() {
		return operatorSourceType;
	}

	public void setOperatorSourceType(String operatorSourceType) {
		this.operatorSourceType = operatorSourceType;
	}

    public String getIs_discharge() {
		return is_discharge;
	}

	public void setIs_discharge(String is_discharge) {
		this.is_discharge = is_discharge;
	}

	public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getCash() {
        if(cash != null){
            this.cash = cash.setScale(2, BigDecimal.ROUND_DOWN);
        }else{
            this.cash = cash;
        }
        return cash;
    }

    public void setCash(BigDecimal cash) {
        if(cash != null){
            cash = cash.setScale(2, BigDecimal.ROUND_DOWN);
        }else{
            cash = cash;
        }
        this.cash = cash;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount == null ? null : creditAccount.trim();
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount == null ? null : debitAccount.trim();
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType == null ? null : chargeType.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber == null ? null : channelNumber.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getConsume_card() {
        return consume_card;
    }

    public void setConsume_card(String consume_card) {
        this.consume_card = consume_card;
    }

    public GasCard getConsume_cardInfo() {
        return consume_cardInfo;
    }

    public void setConsume_cardInfo(GasCard consume_cardInfo) {
        this.consume_cardInfo = consume_cardInfo;
    }

    public List<SysOrderGoods> getSysOrderGoods() {
        return sysOrderGoods;
    }

    public void setSysOrderGoods(List<SysOrderGoods> sysOrderGoods) {
        this.sysOrderGoods = sysOrderGoods;
    }


    public SysDriver getSysDriver() {
        return sysDriver;
    }

    public void setSysDriver(SysDriver sysDriver) {
        this.sysDriver = sysDriver;
    }

    public GasCard getGasCard() {
        return gasCard;
    }

    public void setGasCard(GasCard gasCard) {
        this.gasCard = gasCard;
    }

    public SysUser getOperatorInfo() {
        return operatorInfo;
    }

    public void setOperatorInfo(SysUser operatorInfo) {
        this.operatorInfo = operatorInfo;
    }

    public Usysparam getOrderTypeInfo() {
        return orderTypeInfo;
    }

    public void setOrderTypeInfo(Usysparam orderTypeInfo) {
        this.orderTypeInfo = orderTypeInfo;
    }

    public Gastation getGastation() {
        return gastation;
    }

    public void setGastation(Gastation gastation) {
        this.gastation = gastation;
    }

    public String getCashBack() {
        return cashBack;
    }

    public void setCashBack(String cashBack) {
        this.cashBack = cashBack;
    }

    public Transportion getTransportion() {
        return transportion;
    }

    public void setTransportion(Transportion transportion) {
        this.transportion = transportion;
    }

    public Usysparam getChargeTypeInfo() {
        return chargeTypeInfo;
    }

    public void setChargeTypeInfo(Usysparam chargeTypeInfo) {
        this.chargeTypeInfo = chargeTypeInfo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getThirdPartyOrderID() {
        return thirdPartyOrderID;
    }

    public void setThirdPartyOrderID(String thirdPartyOrderID) {
        this.thirdPartyOrderID = thirdPartyOrderID;
    }

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<Coupon> getCouponlist() {
        return couponlist;
    }

    public void setCouponlist(List<Coupon> couponlist) {
        this.couponlist = couponlist;
    }

    public String getGas_station_name() {
        return gas_station_name;
    }

    public void setGas_station_name(String gas_station_name) {
        this.gas_station_name = gas_station_name;
    }

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getCoupon_title() {
        if(coupon != null){
            coupon_title = coupon.getCoupon_title();
        }
        return coupon_title;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }

    public Usysparam getOrderStatusInfo() {
        return orderStatusInfo;
    }

    public void setOrderStatusInfo(Usysparam orderStatusInfo) {
        this.orderStatusInfo = orderStatusInfo;
    }
}