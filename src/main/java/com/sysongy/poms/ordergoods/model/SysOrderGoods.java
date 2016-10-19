package com.sysongy.poms.ordergoods.model;

import com.sysongy.poms.usysparam.model.Usysparam;

import java.math.BigDecimal;

public class SysOrderGoods {
    private String orderGoodsId;

    private String orderId;

    private BigDecimal price;

    private Double number;

    private BigDecimal sumPrice;

    private String goodsType;

    private Usysparam goods_type_info;

    private String preferential_type;//优惠类型

    private BigDecimal preferential_cash;//优惠金额

    private BigDecimal discountSumPrice;

    public String getOrderGoodsId() {
    public String getPreferential_type() {
		return preferential_type;
	}

	public void setPreferential_type(String preferential_type) {
		this.preferential_type = preferential_type;
	}

	public BigDecimal getPreferential_cash() {
		return preferential_cash;
	}

	public void setPreferential_cash(BigDecimal preferential_cash) {
		this.preferential_cash = preferential_cash;
	}

	public String getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(String orderGoodsId) {
        this.orderGoodsId = orderGoodsId == null ? null : orderGoodsId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Usysparam getGoods_type_info() {
        return goods_type_info;
    }

    public void setGoods_type_info(Usysparam goods_type_info) {
        this.goods_type_info = goods_type_info;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public BigDecimal getDiscountSumPrice() {
        return discountSumPrice;
    }

    public void setDiscountSumPrice(BigDecimal discountSumPrice) {
        this.discountSumPrice = discountSumPrice;
    }
}