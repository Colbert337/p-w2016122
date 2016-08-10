package com.sysongy.poms.ordergoods.service;

import java.util.List;

import com.sysongy.poms.ordergoods.model.SysOrderGoods;

public interface SysOrderGoodsService {
	
	List<SysOrderGoods> selectByOrderID(String orderID);
	
}
