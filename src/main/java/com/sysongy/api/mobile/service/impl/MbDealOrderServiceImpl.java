package com.sysongy.api.mobile.service.impl;

import com.sysongy.api.mobile.service.MbDealOrderService;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.driver.service.impl.DriverServiceImpl;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
        String paycode = "";
        int resultVal = 1;
        try {
            if(driverMap.get("token") != null && !"".equals(driverMap.get("token").toString())){
                String account = driverMap.get("account").toString();
                String name = driverMap.get("name").toString();
                SysDriver receiveDriver = new SysDriver();
                receiveDriver.setUserName(account);//账户
                receiveDriver.setFullName(name);//姓名
                //确认账户号码和对方姓名匹配
                List<SysDriver> queryReceiveDriver = sysDriverMapper.querySingleDriver(receiveDriver);
                if(queryReceiveDriver !=null && queryReceiveDriver.size()>0){
                	driverId = driverMap.get("token").toString();
                	paycode = driverMap.get("paycode").toString();
                    SysDriver driver = sysDriverMapper.selectByPrimaryKey(driverId);
                    driver.setSysDriverId(driverId);
                    driver.setPayCode(paycode);
                    //校验支付密码
                    List<SysDriver> queryDriver = sysDriverMapper.querySingleDriver(driver);
                    if(queryDriver!=null && queryDriver.size()>0){
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
                            
                            SysDriver driver1 = new SysDriver();
                            driver1.setUserName(driverMap.get("account").toString());
                            
                            List<SysDriver> list = driverService.querySingleDriver(driver1).getList();
                            if(list.size() != 1){
                            	throw new Exception("找不到对应的唯一司机用户");
                            }
                            
                            driver1 = list.get(0);
                            
                            order.setDebitAccount(driver1.getSysDriverId());
                            order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_TRANSFER_CHARGE);
                            order.setOperator(driverId);
                            order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.DRIVER);
                            order.setOperatorTargetType(GlobalConstant.OrderOperatorSourceType.DRIVER);
                            order.setOrderNumber(orderNum);
                            order.setIs_discharge(GlobalConstant.ORDER_BEEN_DISCHARGED_NO);
                            //添加订单
                            orderService.insert(order, null);
                            //个人往个人转账
                            orderService.transferDriverToDriver(order);
                            resultVal = 1;
                            return resultVal;
                        }else{
                            return resultVal = 2;//账户不存在
                        }
                    }else{
                    	//支付密码错误
                    	return resultVal = 4;
                    }
                }else{
                	//账户和用户名不匹配
                	return resultVal = 5;
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
