package com.sysongy.api.mobile.service.impl;

import com.sysongy.api.mobile.service.MbDealOrderService;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @FileName: DealOrderService
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.mobile.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月19日, 16:32
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class MbDealOrderServiceImpl implements MbDealOrderService{
    @Autowired
    SysDriverMapper sysDriverMapper;
    @Autowired
    DriverService driverService;
    @Autowired
    OrderService orderService;

    /**
     * 个人对个人转账
     * @param driverMap 转账参数
     * @return
     */
    @Override
    public int transferDriverToDriver(Map<String, Object> driverMap) throws Exception{

        String driverId = "";
        int resultVal = 1;
        try {
            if(driverMap.get("token") != null && !"".equals(driverMap.get("token").toString())){
                driverId = driverMap.get("token").toString();
                SysDriver driver = sysDriverMapper.selectByPrimaryKey(driverId);
                SysUserAccount sysUserAccount = driver.getAccount();
                String amount = driverMap.get("amount").toString();
                BigDecimal amountBd = new BigDecimal(amount);
                if(sysUserAccount != null && sysUserAccount.getAccountBalance() != null){
                    BigDecimal balance = new BigDecimal(sysUserAccount.getAccountBalance());
                    //账户余额不足 无法转账
                    if( balance.compareTo(amountBd) < 0){
                        return resultVal = -1;
                    }
                    //创建订单并转账
                    SysOrder order = new SysOrder();
                    String orderId = UUIDGenerator.getUUID();
                    order.setOrderId(orderId);

                    order.setOrderType(GlobalConstant.OrderType.TRANSFER_DRIVER_TO_DRIVER);
                    String orderNum = orderService.createOrderNumber(GlobalConstant.OrderType.TRANSFER_DRIVER_TO_DRIVER);
                    order.setOrderDate(new Date());
                    BigDecimal cash = new BigDecimal(driverMap.get("amount").toString());

                    order.setCash(cash);
                    order.setCreditAccount(driverId);
                    order.setDebitAccount(driverMap.get("account").toString());
                    order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_TRANSFER_CHARGE);
                    order.setOperator(driverId);
                    order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.DRIVER);
                    order.setOperatorTargetType(GlobalConstant.OrderOperatorSourceType.DRIVER);
                    order.setOrderNumber(orderNum);
                    order.setIs_discharge(GlobalConstant.ORDER_BEEN_DISCHARGED_NO);

                    //添加订单
                    orderService.insert(order, null);
                    //运输公司往个人转账
                    orderService.transferDriverToDriver(order);
                    resultVal = 1;
                    return resultVal;
                }else{
                    return resultVal = 2;//账户不存在
                }

            }else{
                return resultVal = 3;//司机不存在
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultVal = -1;//账户不存在
        }

    }
}
