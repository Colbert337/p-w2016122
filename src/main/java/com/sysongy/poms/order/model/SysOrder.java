package com.sysongy.poms.order.model;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;

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

    private String channel;

    private String channelNumber;

    private String operator;

    private String operatorTargetType;
    
    private String operatorSourceType;

    private String dischargeOrderId;
    
	private String is_discharge;
	
	private String been_discharged;
	
	public String getBeen_discharged() {
		return been_discharged;
	}

	public void setBeen_discharged(String been_discharged) {
		this.been_discharged = been_discharged;
	}

	private String discharge_reason;
	
	private String operatorSourceId;

    private String consume_card;

    private GasCard consume_cardInfo;
    
    
    public String getDischarge_reason() {
		return discharge_reason;
	}

	public void setDischarge_reason(String discharge_reason) {
		this.discharge_reason = discharge_reason;
	}

    private List<SysOrderGoods> sysOrderGoods;

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
        return cash;
    }

    public void setCash(BigDecimal cash) {
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
}