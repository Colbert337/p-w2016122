package com.sysongy.tcms.advance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.tcms.advance.dao.TcFleetMapper;
import com.sysongy.tcms.advance.dao.TcFleetQuotaMapper;
import com.sysongy.tcms.advance.dao.TcTransferAccountMapper;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import com.sysongy.tcms.advance.model.TcTransferAccount;
import com.sysongy.tcms.advance.service.TcFleetQuotaService;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.BigDecimalArith;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import com.sysongy.util.pojo.AliShortMessageBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * @FileName: TcFleetQuotaServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月22日, 12:10
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class TcFleetQuotaServiceImpl implements TcFleetQuotaService{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    TcFleetQuotaMapper tcFleetQuotaMapper;
    @Autowired
    SysUserAccountService sysUserAccountService;
    @Autowired
    TcFleetMapper fleetMapper;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDealService orderDealService;
    @Autowired
    TcTransferAccountMapper tcTransferAccountMapper;


    @Override
    public TcFleetQuota queryFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.queryFleetQuota(tcFleetQuota);
        }else{
            return null;
        }

    }

    @Override
    public PageInfo<TcFleetQuota> queryFleetQuotaList(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            PageHelper.startPage(tcFleetQuota.getPageNum(),tcFleetQuota.getPageSize());

            List<TcFleetQuota> FleetQuotaList = tcFleetQuotaMapper.queryFleetQuotaList(tcFleetQuota);
            PageInfo<TcFleetQuota> FleetQuotaPageInfo = new PageInfo<>(FleetQuotaList);
            return FleetQuotaPageInfo;
        }else{
            return null;
        }
    }

    @Override
    public Map<String, Object> queryFleetQuotaMapList(TcFleetQuota tcFleetQuota) throws Exception {
        if(tcFleetQuota != null){
            Map<String, Object> fleetQuotaMap = new HashMap<>();
            List<Map<String, Object>> fleetQuotaList = tcFleetQuotaMapper.queryFleetQuotaMapList(tcFleetQuota);
            String stationId = tcFleetQuota.getStationId();
            SysUserAccount userAccount = sysUserAccountService.queryUserAccountByStationId(stationId);
            fleetQuotaMap.put("userAccount",userAccount);
            fleetQuotaMap.put("fleetQuotaList",fleetQuotaList);

            //已分配金额
            BigDecimal yifenpei = BigDecimal.ZERO;
            //未分配金额
            BigDecimal weifenpeiVal = BigDecimal.ZERO;

            List<Map<String, Object>> allList = new ArrayList<>();
            if(fleetQuotaList != null && fleetQuotaList.size() > 0){
                List<Map<String, Object>> weifenpeiList = new ArrayList<>();
                List<Map<String, Object>> yifenpeiList = new ArrayList<>();
                for (Map<String, Object> fleetQuota:fleetQuotaList){
                    if(fleetQuota.get("isAllot") != null && fleetQuota.get("isAllot").toString().equals("1")){
                        BigDecimal quota = new BigDecimal(fleetQuota.get("quota").toString());
                        yifenpei = BigDecimalArith.add(yifenpei,quota);
                        yifenpeiList.add(fleetQuota);
                    }else{
                        weifenpeiList.add(fleetQuota);
                    }
                }

                weifenpeiVal = BigDecimalArith.sub(new BigDecimal(userAccount.getAccountBalance()),yifenpei);
                if(weifenpeiList != null && weifenpeiList.size() > 0){
                    for(Map<String, Object> weifenpei:weifenpeiList){
                        weifenpei.put("quota",weifenpeiVal.toString());
                    }
                }
                allList.addAll(weifenpeiList);
                allList.addAll(yifenpeiList);
            }
            fleetQuotaMap.put("fleetQuotaList",allList);
            fleetQuotaMap.put("yifenpei",yifenpei);
            fleetQuotaMap.put("weifenpeiVal",weifenpeiVal);

            return fleetQuotaMap;
        }else{
            return null;
        }
    }

    @Override
    public int addFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.addFleetQuota(tcFleetQuota);
        }else{
            return 0;
        }
    }


    /**
     * 保存资金分配
     * @param tcFleetQuotaList
     * @return
     */
    @Override
    public int addFleetQuotaList(List<Map<String, Object>> tcFleetQuotaList) {
        //更新车队资金分配信息
        if(tcFleetQuotaList != null &&  tcFleetQuotaList.size() > 0){
            for (Map<String, Object> fleetQuota:tcFleetQuotaList) {
                fleetMapper.updateFleetMap(fleetQuota);
            }
        }
        //添加资金分配记录
        return tcFleetQuotaMapper.addFleetQuotaList(tcFleetQuotaList);
    }

    @Override
    public int deleteFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.deleteFleetQuota(tcFleetQuota);
        }else{
            return 0;
        }
    }

    @Override
    public int updateFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.updateFleetQuota(tcFleetQuota);
        }else{
            return 0;
        }
    }

    /**
     * 个人转账
     * @param list 个人转账列表
     * @return
     */
    @Override
    public int personalTransfer(List<Map<String, Object>> list,String stationId ,String userId) throws Exception{
        BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
        int resultVal = 1;
        try {

            if(list != null && list.size() > 0){
                //获取转账总金额
                for (Map<String, Object> map:list){
                    BigDecimal cashVal = new BigDecimal(map.get("amount").toString());
                    totalCash = BigDecimalArith.add(totalCash,cashVal);
                }
                //判断转账金额是否够用
                SysUserAccount userAccount = sysUserAccountService.queryUserAccountByStationId(stationId);
                BigDecimal accountTotal = new BigDecimal(BigInteger.ZERO);
                if(userAccount != null){
                    accountTotal = new BigDecimal(userAccount.getAccountBalance());
                    if(accountTotal.compareTo(BigDecimal.ZERO) < 0){
                        resultVal = 0;
                        throw new Exception("账户余额为负值！");
                    }else if(accountTotal.compareTo(totalCash) < 0 ){
                        resultVal = -1;
                        throw new Exception("账户余额不足，请先充值！");
                    }else{
                        /*循环生成订单*/
                        for (Map<String, Object> mapDriver:list){
                            SysOrder order = new SysOrder();
                            String orderId = UUIDGenerator.getUUID();
                            order.setOrderId(orderId);

                            order.setOrderType(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);
                            String orderNum = orderService.createOrderNumber(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);
                            order.setOrderDate(new Date());
                            BigDecimal cash = new BigDecimal(mapDriver.get("amount").toString());

                            order.setCash(cash);
                            order.setCreditAccount(stationId);
                            order.setDebitAccount(mapDriver.get("sysDriverId").toString());
                            order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_TRANSFER_CHARGE);
                            order.setOperator(userId);
                            order.setOperatorSourceId(stationId);
                            order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.TRANSPORTION);
                            order.setOperatorTargetType(GlobalConstant.OrderOperatorSourceType.DRIVER);
                            order.setOrderNumber(orderNum);

                            //添加订单
                            resultVal = orderService.insert(order);
                            //运输公司往个人转账
                            orderService.transferTransportionToDriver(order);

                            //添加转账记录
                            TcTransferAccount tcTransferAccount = new TcTransferAccount();
                            tcTransferAccount.setTcTransferAccountId(UUIDGenerator.getUUID());
                            tcTransferAccount.setStationId(stationId);
                            tcTransferAccount.setSysDriverId(mapDriver.get("sysDriverId").toString());
                            tcTransferAccount.setAmount(new BigDecimal(mapDriver.get("amount").toString()) );
                            tcTransferAccount.setFullName(mapDriver.get("fullName").toString());
                            tcTransferAccount.setMobilePhone(mapDriver.get("mobilePhone").toString());
                            tcTransferAccount.setUsed(mapDriver.get("remark").toString());
                            tcTransferAccount.setUpdatedDate(new Date());
                            resultVal = tcTransferAccountMapper.insertSelective(tcTransferAccount);

                            /*发送转账通知短信*/
                            AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
                            SimpleDateFormat sfm = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
                            String time = sfm.format(new Date());
                            aliShortMessageBean.setTime(time);
                            aliShortMessageBean.setString("转账");
                            aliShortMessageBean.setMoney(mapDriver.get("amount").toString());
                            aliShortMessageBean.setSendNumber(mapDriver.get("mobilePhone").toString());
                            /*查询返现金额*/
                            List<SysOrderDeal> orderDealList = orderDealService.queryOrderDealByOrderId(orderId);
                            if(orderDealList != null && orderDealList.size() > 0){
                                BigDecimal backCash = new BigDecimal(BigInteger.ZERO);
                                for (SysOrderDeal orderDeal:orderDealList){
                                    backCash.add(orderDeal.getCashBack());
                                }
                                aliShortMessageBean.setBackCash(backCash.toString());
                            }else{
                                aliShortMessageBean.setBackCash("0.00");
                            }
                            /*查询账户余额*/
                            SysUserAccount sysUserAccount = sysUserAccountService.queryUserAccountByDriverId(mapDriver.get("sysDriverId").toString());
                            if(sysUserAccount != null){
                                aliShortMessageBean.setMoney1(sysUserAccount.getAccountBalance());
                            }else{
                                aliShortMessageBean.setMoney1("0.00");
                            }

                            sendMsgApi(aliShortMessageBean);
                        }

                    }
                    /*accountTotal = BigDecimalArith.sub(accountTotal,totalCash);
                    sysUserAccountService.addCashToAccount(userAccount.getSysUserAccountId(),accountTotal.multiply(new BigDecimal(-1)),"");*/
                }
            }
        }catch (Exception e){
            resultVal = -1;
            e.printStackTrace();
        }
        return resultVal;
    }

    /**
     * 查询车队额度信息列表
     * @param tcFleet
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> queryQuotaList(TcFleet tcFleet) {
        if(tcFleet != null){
            PageHelper.startPage(tcFleet.getPageNum(),tcFleet.getPageSize(), tcFleet.getOrderby());

            List<Map<String, Object>> fleetQuotaList = tcFleetQuotaMapper.queryQuotaList(tcFleet);
            PageInfo<Map<String, Object>> fleetQuotaPageInfo = new PageInfo<>(fleetQuotaList);
            return fleetQuotaPageInfo;
        }else{
            return null;
        }
    }

    /**
     * 发送短信
     * @param aliShortMessageBean
     * @return
     */
    public AjaxJson sendMsgApi(AliShortMessageBean aliShortMessageBean){
        AjaxJson ajaxJson = new AjaxJson();

        if(!StringUtils.isNotEmpty(aliShortMessageBean.getSendNumber())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("手机号为空！！！");
            return ajaxJson;
        }

        try
        {
            AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.TRANSPORTION_TRANSFER_SELF_CHARGE);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_SEND_MSG_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }

}
