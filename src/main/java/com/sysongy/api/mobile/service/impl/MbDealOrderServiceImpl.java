package com.sysongy.api.mobile.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysongy.api.mobile.service.MbDealOrderService;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.message.service.SysMessageService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.dao.SysUserMapper;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.system.model.SysOperationLog;
import com.sysongy.poms.system.service.SysOperationLogService;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.AliShortMessage.SHORT_MESSAGE_TYPE;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.RealNameException;
import com.sysongy.util.UUIDGenerator;
import com.sysongy.util.pojo.AliShortMessageBean;

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
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysUserAccountService sysUserAccountService;
	@Autowired
	SysMessageService sysMessageService;

	@Autowired
	SysOperationLogService sysOperationLogService;
    /**
     * 个人对个人转账
     * @param driverMap 转账参数
     * @return
     */
    @Override
    public int transferDriverToDriver(Map<String, Object> driverMap) throws RealNameException,Exception{

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
                            order.setChargeType(GlobalConstant.OrderType.TRANSFER_DRIVER_TO_DRIVER);
                            SysUser user = new SysUser();
                            user.setUserName("13000000000");
                            SysUser usr = sysUserMapper.queryUser(user);
                            order.setOperator(usr.getSysUserId());
                            order.setChannel("APP");
                            order.setChannelNumber("APP");
                            order.setOrderRemark(driverMap.get("remark").toString());
                            order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.DRIVER);
                            order.setOperatorTargetType(GlobalConstant.OrderOperatorSourceType.DRIVER);
                            order.setOrderNumber(orderNum);
                            order.setIs_discharge(GlobalConstant.ORDER_BEEN_DISCHARGED_NO);
                            order.setOrderStatus(GlobalConstant.ORDER_STATUS.ORDER_SUCCESS);
                            //添加订单
                            orderService.insert(order, null);
                            //个人往个人转账
                            orderService.transferDriverToDriver(order);
        					//系统关键日志记录
                			SysOperationLog sysOperationLog = new SysOperationLog();
                			sysOperationLog.setOperation_type("zz");
                			sysOperationLog.setLog_platform("1");
                    		sysOperationLog.setOrder_number(order.getOrderNumber());
                    		sysOperationLog.setLog_content(driver.getFullName()+"对"+driver1.getFullName()+"转账成功！订单号为："+order.getOrderNumber());
                			//操作日志
                			sysOperationLogService.saveOperationLog(sysOperationLog,driverId);
                            resultVal = 1;
                            sendTransferMessage(order,account);
                            //发送短信提醒
                            String fromDriverId = driverId;
                            SysDriver record = new SysDriver();
                            record.setMobilePhone(account);
            				String toDriverId =  driverService.queryDriverByMobilePhone(record).getSysDriverId();
        					//付款人短信提醒
            				SysDriver fromDriver = driverService.queryDriverByPK(fromDriverId);
        					AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
        					aliShortMessageBean.setSendNumber(fromDriver.getMobilePhone());
        					aliShortMessageBean.setAccountNumber(fromDriver.getMobilePhone());
        					aliShortMessageBean.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        					aliShortMessageBean.setMoney(amount);
        					aliShortMessageBean.setBalance(sysUserAccountService.queryUserAccountByDriverId(fromDriver.getSysDriverId()).getAccountBalance());
        					aliShortMessageBean.setString("转出");
        					AliShortMessage.sendShortMessage(aliShortMessageBean, SHORT_MESSAGE_TYPE.PERSONAL_TRANSFER);
        					//APP提示
    						sysMessageService.saveMessageTransaction("转出", order,"2");
        					//收款人短信提醒
    						SysDriver toDriver = driverService.queryDriverByPK(toDriverId);
        					AliShortMessageBean aliShortMessage = new AliShortMessageBean();
        					aliShortMessage.setSendNumber(toDriver.getMobilePhone());
        					aliShortMessage.setAccountNumber(toDriver.getMobilePhone());
        					aliShortMessage.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        					aliShortMessage.setMoney(amount);
        					aliShortMessage.setBalance(sysUserAccountService.queryUserAccountByDriverId(toDriver.getSysDriverId()).getAccountBalance());
        					aliShortMessage.setString("转入");
        					AliShortMessage.sendShortMessage(aliShortMessage, SHORT_MESSAGE_TYPE.PERSONAL_TRANSFER);
        					//APP提示
    						sysMessageService.saveMessageTransaction("收到转账", order,"1");
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
        }catch (RealNameException e){
            e.printStackTrace();
            throw new RealNameException("未实名认证");
        }catch (Exception e){
            e.printStackTrace();
            return resultVal = -1;//账户不存在
        }

    }
	/**
	 * 转账成功后发送短信提醒
	 */
	private void sendTransferMessage(SysOrder order,String receiverNum){
		 /*发送转账通知短信*/
        AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
        SimpleDateFormat sfm = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String time = sfm.format(new Date());
        aliShortMessageBean.setTime(time);
        aliShortMessageBean.setString("转入");
        aliShortMessageBean.setMoney(order.getCash().toString());
        aliShortMessageBean.setSendNumber(receiverNum);
        /*查询账户余额*/
        SysUserAccount sysUserAccount = sysUserAccountService.queryUserAccountByDriverId(order.getDebitAccount());
        if(sysUserAccount != null){
            aliShortMessageBean.setMoney1(sysUserAccount.getAccountBalance());
        }else{
            aliShortMessageBean.setMoney1("0.00");
        }
		AliShortMessage.sendShortMessage(aliShortMessageBean, SHORT_MESSAGE_TYPE.SELF_CHARGE_CONSUME_PREINPUT);
	}
}
