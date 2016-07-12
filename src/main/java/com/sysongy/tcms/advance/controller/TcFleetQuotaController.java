package com.sysongy.tcms.advance.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import com.sysongy.tcms.advance.service.TcFleetQuotaService;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @FileName: TcFleetQuotaController
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月21日, 18:49
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Controller
@RequestMapping("/web/tcms/fleetQuota")
public class TcFleetQuotaController extends BaseContoller {


    @Autowired
    TcFleetQuotaService tcFleetQuotaService;
    @Autowired
    GasCardService gasCardService;
    @Autowired
    DriverService driverService;
    @Autowired
    OrderService orderService;
    @Autowired
    TransportionService transportionService;

    /**
     * 查询车辆列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryFleetQuotaListPage(@ModelAttribute CurrUser currUser, TcFleetQuota fleetQuota, @RequestParam(required = false) Integer resultInt, ModelMap map){
        String stationId = currUser.getStationId();
        List<Map<String, Object>> fleetQuotaList = new ArrayList<>();
        Map<String, Object> fleetQuotaMap = new HashMap<>();
        fleetQuota.setStationId(stationId);
        try {
            fleetQuotaMap = tcFleetQuotaService.queryFleetQuotaMapList(fleetQuota);
        }catch (Exception e){
            e.printStackTrace();
        }

        map.addAttribute("fleetQuotaMap",fleetQuotaMap);
        map.addAttribute("fleetQuota",fleetQuota);
        map.addAttribute("stationId",stationId);
        if(resultInt != null){
            Map<String, Object> resultMap = new HashMap<>();
            if(resultInt == 1){
                resultMap.put("retMsg","转账成功！");
            }else if(resultInt == 0){
                resultMap.put("retMsg","账户余额为负，不能转账！");
            }else if(resultInt < 0){
                resultMap.put("retMsg","账户余额不足，请先充值！");
            }else if(resultInt == 2){
                resultMap.put("retMsg","分配成功！");
            }else if(resultInt == 3){
                resultMap.put("retMsg","余额不足！");
            }else if(resultInt == 4){
                resultMap.put("retMsg","分配失败！");
            }else if(resultInt == 5){
                resultMap.put("retMsg","支付密码修改失败！");
            }else if(resultInt == 6){
                resultMap.put("retMsg","支付密码修改成功！");
            }else if(resultInt == 7){
                resultMap.put("retMsg","邮件发送失败！");
            }else if(resultInt == 8){
                resultMap.put("retMsg","邮件发送成功！");
            }

            map.addAttribute("ret",resultMap);
        }

        return "webpage/tcms/advance/fleet_quota_list";
    }

    /**
     * 保存分配资金
     * @param data
     * @param map
     * @return
     */
    @RequestMapping("/save/fenpei")
    public String saveFenpei(@ModelAttribute CurrUser currUser,@RequestParam String data, ModelMap map){
        int resultInt = 0;
        String stationId = currUser.getStationId();
        try {
            if(data != null && !"".equals(data)) {
                String datas[] = data.split("&");
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//将值分开存在list中
                Map<String, Object> mapObj = new HashMap();
                String dataTemp[];
                //根据运输公司ID查询运输公司信息
                Transportion transportion = transportionService.queryTransportionByPK(stationId);
                if(datas != null && datas.length > 0){
                    for (int i = 0; i < datas.length; i++) {
                        dataTemp = datas[i].split("=");
                        if(i == 0 && dataTemp.length > 1){//将第一位赋值给站点编号
                            stationId = dataTemp[1];
                        }else{
                            if (dataTemp.length > 1) {
                                mapObj.put(dataTemp[0], dataTemp[1]);
                            } else {
                                mapObj.put(dataTemp[0], "0");
                            }
                            if (i % 3 == 0) {   //数字12 为  提交过来每一行  需要修改数据的数量
                                mapObj.put("tcFleetQuotaId",UUIDGenerator.getUUID());
                                mapObj.put("stationId",stationId);
                                list.add(mapObj);
                                mapObj = new HashMap<>();
                            }
                        }

                    }
                }
                //判断未分配额度是否够用
                BigDecimal userQuota = new BigDecimal(BigInteger.ZERO);//使用额度
                if(list != null && list.size() > 0){
                    BigDecimal totalVal = new BigDecimal(BigInteger.ZERO);
                    BigDecimal deposit = transportion.getDeposit();
                    for (Map<String, Object> mapVal:list) {
                        if(mapVal.get("quota").toString().equals("1")){//分配
                            userQuota.add(new BigDecimal(mapVal.get("quota").toString()));
                        }
                    }
                    if(userQuota.compareTo(deposit) > 0){
                        //添加分配记录
                        tcFleetQuotaService.addFleetQuotaList(list);

                        //计算运输公司剩余额度
                        userQuota = transportion.getDeposit().subtract(userQuota);

                        //更新运输公司剩余额度
                        Transportion quotaTrans = new Transportion();
                        quotaTrans.setSys_transportion_id(stationId);
                        quotaTrans.setDeposit(userQuota);

                        transportionService.updatedeposiTransport(quotaTrans);
                        resultInt = 2;//成功
                    }else{
                        resultInt = 3;//余额不足
                    }
                }

            }
        }catch (Exception e){
            resultInt = 4;//失败
            e.printStackTrace();
        }
            return "redirect:/web/tcms/fleetQuota/list/page?resultInt="+resultInt;
    }

    /**
     * 个人转账
     * @param data
     * @param map
     * @return
     */
    @RequestMapping("/save/zhuan")
    public String saveZhuan(@ModelAttribute CurrUser currUser,@RequestParam String data, ModelMap map) throws Exception{
        int resultInt = 1;
        String stationId = currUser.getStationId();
        String userName = currUser.getUser().getUserName();
        try {
            if(data != null && !"".equals(data)) {
                String datas[] = data.split("&");
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//将值分开存在list中
                Map<String, Object> mapObj = new HashMap();
                String dataTemp[];
                if(datas != null && datas.length > 0){
                    for (int i = 0; i < datas.length; i++) {
                        dataTemp = datas[i].split("=");
                        if (dataTemp.length > 1) {
                            mapObj.put(dataTemp[0], dataTemp[1]);
                        } else {
                            mapObj.put(dataTemp[0], "0");
                        }
                        if ((i+1) % 5 == 0 ) {   //数字5 为  提交过来每一行  需要修改数据的数量
                            list.add(mapObj);
                            mapObj = new HashMap<>();
                        }

                    }
                }

                resultInt = tcFleetQuotaService.personalTransfer(list,stationId,userName);
                /*if(list != null && list.size() > 0){
                    for (Map<String, Object> mapDriver:list){
                        SysOrder order = new SysOrder();
                        order.setOrderId(UUIDGenerator.getUUID());

                        order.setOrderType(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);
                        String orderNum = orderService.createOrderNumber(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);
                        order.setOrderDate(new Date());
                        BigDecimal cash = new BigDecimal(mapDriver.get("amount").toString());
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
                    }
                }*/
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/web/tcms/fleetQuota/list/page?resultInt="+resultInt;
    }

    /**
     * 根据手机号码查询司机信息
     * @param sysDriver
     * @param map
     * @return
     */
    @RequestMapping("/info/driver")
    @ResponseBody
    public SysDriver queryDriverInfo(@ModelAttribute CurrUser currUser,SysDriver sysDriver, ModelMap map){
        SysDriver driver = new SysDriver();
        String stationId = currUser.getStationId();
        driver = sysDriver;
        driver.setStationId(stationId);
        try {
            driver = driverService.queryDriverByMobilePhone(driver);
        }catch (Exception e){
            e.printStackTrace();
        }

        return driver;
    }

    /**
     * 额度划拨报表
     * @param tcFleet
     * @return
     */
    @RequestMapping("/list/quota")
    public String queryQuotaList(@ModelAttribute CurrUser currUser, TcFleet tcFleet, ModelMap map){
        String stationId = currUser.getStationId();
        PageBean bean = new PageBean();
        String ret = "webpage/tcms/advance/fleet_quota_log";

        try {
            if(tcFleet.getPageNum() == null){
                tcFleet.setOrderby("created_date desc");
                tcFleet.setPageNum(1);
                tcFleet.setPageSize(10);
            }
            tcFleet.setStationId(stationId);
            PageInfo<Map<String, Object>> pageinfo = tcFleetQuotaService.queryQuotaList(tcFleet);

            if(pageinfo.getList() != null && pageinfo.getList().size() > 0){
                BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
                for (Map<String, Object> quotaMap:pageinfo.getList()) {
                    if(quotaMap.get("quota") != null && !"".equals(quotaMap.get("quota").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("quota").toString()));
                    }
                }
                //累计总划款金额
                map.addAttribute("totalCash",totalCash);
            }
            bean.setRetCode(100);
            bean.setRetMsg("查询成功");
            bean.setPageInfo(ret);

            map.addAttribute("ret", bean);
            map.addAttribute("pageInfo", pageinfo);
            map.addAttribute("tcFleet",tcFleet);
        } catch (Exception e) {
            bean.setRetCode(5000);
            bean.setRetMsg(e.getMessage());

            map.addAttribute("ret", bean);
            logger.error("", e);
            throw e;
        }
        finally {
            return ret;
        }
    }

}
