package com.sysongy.poms.ordergoods.model;

import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.usysparam.model.Usysparam;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/18.
 */
public class SysOrderGoodsForCRMReport {

    private String orderGoodsId;

    private BigDecimal price;

    private Integer number;

    private BigDecimal sumPrice;

    private String goodsType;

    private Usysparam goods_type_info;

    private String cashBack;

    private SysDriver sysDriver;

    private GasCard gasCard;

    private SysUser operatorInfo;

    private Usysparam orderTypeInfo;

    private Gastation gastation;

    private Transportion transportion;

    private Usysparam chargeTypeInfo;

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

    private String discharge_reason;

    private String operatorSourceId;

    private String consume_card;

    private GasCard consume_cardInfo;

    private String storage_time_before;

    private String storage_time_after;

    private String realNum;

    private String realPrice;

    public String getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(String orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
        this.creditAccount = creditAccount;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public String getDischargeOrderId() {
        return dischargeOrderId;
    }

    public void setDischargeOrderId(String dischargeOrderId) {
        this.dischargeOrderId = dischargeOrderId;
    }

    public String getIs_discharge() {
        return is_discharge;
    }

    public void setIs_discharge(String is_discharge) {
        this.is_discharge = is_discharge;
    }

    public String getBeen_discharged() {
        return been_discharged;
    }

    public void setBeen_discharged(String been_discharged) {
        this.been_discharged = been_discharged;
    }

    public String getDischarge_reason() {
        return discharge_reason;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Usysparam getGoods_type_info() {
        return goods_type_info;
    }

    public void setGoods_type_info(Usysparam goods_type_info) {
        this.goods_type_info = goods_type_info;
    }

    public String getCashBack() {
        return cashBack;
    }

    public void setCashBack(String cashBack) {
        this.cashBack = cashBack;
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

    public String getStorage_time_before() {
        return storage_time_before;
    }

    public void setStorage_time_before(String storage_time_before) {
        this.storage_time_before = storage_time_before;
    }

    public String getStorage_time_after() {
        return storage_time_after;
    }

    public void setStorage_time_after(String storage_time_after) {
        this.storage_time_after = storage_time_after;
    }

    public String getRealNum() {
        if((this.is_discharge != null) && (this.is_discharge.equalsIgnoreCase("1"))){
            return "-" + this.number;
        }
        return realNum;
    }

    public void setRealNum(String realNum) {
        if((this.is_discharge != null) && (this.is_discharge.equalsIgnoreCase("1"))){
            this.realNum = "-" + this.number;
        }
    }

    public String getRealPrice() {
        if((this.is_discharge != null) && (this.is_discharge.equalsIgnoreCase("1"))){
            return "-" + sumPrice.toString();
        }
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        if((this.is_discharge != null) && (this.is_discharge.equalsIgnoreCase("1"))){
            this.realPrice = "-" + sumPrice.toString();
        }
    }
}
