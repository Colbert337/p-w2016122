package com.sysongy.poms.order.model;

import java.math.BigDecimal;
import java.util.Date;

public class SysOrder {
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

    private String operatorType;

    private String is_discharge;

    private String discharge_order_id;
    
    public String getIs_discharge() {
		return is_discharge;
	}

	public void setIs_discharge(String is_discharge) {
		this.is_discharge = is_discharge;
	}

	public String getDischarge_order_id() {
		return discharge_order_id;
	}

	public void setDischarge_order_id(String discharge_order_id) {
		this.discharge_order_id = discharge_order_id;
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

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType == null ? null : operatorType.trim();
    }
}