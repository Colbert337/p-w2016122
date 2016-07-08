package com.sysongy.tcms.advance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.tcms.advance.dao.TcFleetMapper;
import com.sysongy.tcms.advance.dao.TcFleetQuotaMapper;
import com.sysongy.tcms.advance.dao.TcTransferAccountMapper;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import com.sysongy.tcms.advance.model.TcTransferAccount;
import com.sysongy.tcms.advance.service.TcFleetQuotaService;
import com.sysongy.util.BigDecimalArith;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

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
    @Autowired
    TcFleetQuotaMapper tcFleetQuotaMapper;
    @Autowired
    SysUserAccountService sysUserAccountService;
    @Autowired
    TcFleetMapper fleetMapper;
    @Autowired
    OrderService orderService;
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
    public Map<String, Object> queryFleetQuotaMapList(TcFleetQuota tcFleetQuota) {
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
    public int personalTransfer(List<Map<String, Object>> list,String stationId ,String userName) throws Exception{
        BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
        try {

            if(list != null && list.size() > 0){
                for (Map<String, Object> mapDriver:list){
                    SysOrder order = new SysOrder();
                    order.setOrderId(UUIDGenerator.getUUID());

                    order.setOrderType(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);
                    String orderNum = orderService.createOrderNumber(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);
                    order.setOrderDate(new Date());
                    BigDecimal cash = new BigDecimal(mapDriver.get("amount").toString());
                    totalCash = BigDecimalArith.add(totalCash,cash);

                    order.setCash(cash);
                    order.setCreditAccount(stationId);
                    order.setDebitAccount(mapDriver.get("sysDriverId").toString());
                    order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_TRANSFER_CHARGE);
                    order.setOperator(userName);
                    order.setOperatorSourceId(stationId);
                    order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.TRANSPORTION);
                    order.setOperatorTargetType(GlobalConstant.OrderOperatorSourceType.DRIVER);
                    order.setOrderNumber(orderNum);

                    //添加订单
                    orderService.insert(order);
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
                    tcTransferAccount.setUpdatedDate(new Date());
                    tcTransferAccountMapper.insertSelective(tcTransferAccount);
                }

                //修改运输公司总金额
                SysUserAccount userAccount = sysUserAccountService.queryUserAccountByStationId(stationId);
                BigDecimal accountTotal = new BigDecimal(BigInteger.ZERO);
                if(userAccount != null){
                    accountTotal = new BigDecimal(userAccount.getAccountBalance());
                    accountTotal = BigDecimalArith.sub(accountTotal,totalCash);

                    sysUserAccountService.addCashToAccount(userAccount.getSysUserAccountId(),accountTotal.multiply(new BigDecimal(-1)),"");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
