package com.sysongy.poms.ordergoods.model;

import com.sysongy.poms.usysparam.model.Usysparam;

import java.math.BigDecimal;

public class SysOrderGoods {
    private String orderGoodsId;

    private String orderId;

    private BigDecimal price;

    private Integer number;

    private BigDecimal sumPrice;

    private String goodsType;

    private Usysparam goods_type_info;

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
}