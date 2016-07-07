package com.sysongy.tcms.advance.controller;

import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
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

    /**
     * 查询车辆列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryFleetQuotaListPage(@ModelAttribute CurrUser currUser, TcFleetQuota fleetQuota, ModelMap map){
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

        return "webpage/tcms/advance/fleet_quota_list";
    }

    /**
     * 保存分配资金
     * @param data
     * @param map
     * @return
     */
    @RequestMapping("/save/fenpei")
    public String saveFenpei(@RequestParam String data, ModelMap map){
        try {
            if(data != null && !"".equals(data)) {
                String datas[] = data.split("&");
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//将值分开存在list中
                Map<String, Object> mapObj = new HashMap();
                String dataTemp[];
                String stationId = "";
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

                tcFleetQuotaService.addFleetQuotaList(list);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
            return "redirect:/web/tcms/fleetQuota/list/page";
    }

    /**
     * 个人转账
     * @param data
     * @param map
     * @return
     */
    @RequestMapping("/save/zhuan")
    public String saveZhuan(@ModelAttribute CurrUser currUser,@RequestParam String data, ModelMap map){
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

                if(list != null && list.size() > 0){
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
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/web/tcms/fleetQuota/list/page";
    }

    /**
     * 根据手机号码查询司机信息
     * @param sysDriver
     * @param map
     * @return
     */
    @RequestMapping("/info/driver")
    @ResponseBody
    public SysDriver queryDriverInfo(SysDriver sysDriver, ModelMap map){
        SysDriver driver = new SysDriver();
        driver = sysDriver;
        try {
            driver = driverService.queryDriverByMobilePhone(driver);
        }catch (Exception e){
            e.printStackTrace();
        }

        return driver;
    }

}
