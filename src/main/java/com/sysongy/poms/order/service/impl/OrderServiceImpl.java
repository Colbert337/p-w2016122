package com.sysongy.poms.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.poms.order.dao.SysOrderMapper;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;

/**
 * 
 * @author zhangyt 2016-06-16
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private SysOrderMapper sysOrderMapper;
	
	@Override
	public int deleteByPrimaryKey(String orderId) {
		return sysOrderMapper.deleteByPrimaryKey(orderId);
	}

	@Override
	public int insert(SysOrder record) {
		return sysOrderMapper.insert(record);
	}

	@Override
	public SysOrder selectByPrimaryKey(String orderId) {
		return sysOrderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public int updateByPrimaryKey(SysOrder record) {
		return sysOrderMapper.updateByPrimaryKey(record);
	}

	@Override
	public int charge(SysOrder order){
	   if (order ==null){
		   return 0;
	   }
	   
	   String orderType = order.getOrderType();
	   return 1;	
	}
}
