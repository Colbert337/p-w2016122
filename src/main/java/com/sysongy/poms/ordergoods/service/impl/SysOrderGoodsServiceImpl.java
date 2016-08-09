package com.sysongy.poms.ordergoods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.ordergoods.dao.SysOrderGoodsMapper;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;
import com.sysongy.poms.ordergoods.service.SysOrderGoodsService;

@Service
public class SysOrderGoodsServiceImpl implements SysOrderGoodsService {
	
	@Autowired
	private SysOrderGoodsMapper sysOrderGoodsMapper;
	@Override
	public List<SysOrderGoods> selectByOrderID(String orderID) {
		return sysOrderGoodsMapper.selectByOrderID(orderID);
	}

}
