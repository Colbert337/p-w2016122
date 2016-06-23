package com.sysongy.tcms.advance.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.service.TcVehicleService;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.RedisClientInterface;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: TcVehicleController
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月21日, 18:49
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Controller
@RequestMapping("/web/tcms/vehicle")
public class TcVehicleController extends BaseContoller {


    @Autowired
    TcVehicleService tcVehicleService;
    @Autowired
    GasCardService gasCardService;

    /**
     * 查询车辆列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryVehicleListPage(@ModelAttribute CurrUser currUser, TcVehicle vehicle, ModelMap map){
        String stationId = currUser.getStationId();
        if(vehicle.getPageNum() == null){
            vehicle.setPageNum(GlobalConstant.PAGE_NUM);
            vehicle.setPageSize(GlobalConstant.PAGE_SIZE);
        }
        vehicle.setStationId(stationId);

        //封装分页参数，用于查询分页内容
        PageInfo<Map<String, Object>> vehiclePageInfo = new PageInfo<Map<String, Object>>();
        try {
            vehiclePageInfo = tcVehicleService.queryVehicleMapList(vehicle);
        }catch (Exception e){
            e.printStackTrace();
        }

        map.addAttribute("vehicleList",vehiclePageInfo.getList());
        map.addAttribute("pageInfo",vehiclePageInfo);
        map.addAttribute("vehicle",vehicle);

        return "webpage/tcms/advance/vehicle_list";
    }

    /**
     * 查询车辆信息
     * @param vehicle
     * @param map
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public Map<String, Object> queryVehicle(TcVehicle vehicle, ModelMap map){
        Map<String, Object> vehicleMap = new HashMap<>();
        GasCard gasCard = new GasCard();
        TcVehicle tcVehicle = tcVehicleService.queryVehicle(vehicle);
        vehicleMap.put("vehicle",tcVehicle);
        if(tcVehicle != null && tcVehicle.getCardNo() != null){
            try {
                gasCard = gasCardService.queryGasCardInfo(tcVehicle.getCardNo());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        vehicleMap.put("gasCard",gasCard);
        return vehicleMap;
    }

    /**
     * 添加车辆
     * @param currUser 当前用户
     * @param vehicle 车辆
     * @param map
     * @return
     */
    @RequestMapping("/save")
    public String saveVehicle(@ModelAttribute("currUser") CurrUser currUser, TcVehicle vehicle, ModelMap map){
        int userType = currUser.getUser().getUserType();
        int result = 0;

        if(vehicle.getTcVehicleId() != null && vehicle.getTcVehicleId() != ""){
            vehicle.setPayCode(null);
            tcVehicleService.updateVehicle(vehicle);
        }else{
            String stationId = currUser.getStationId();
            vehicle.setStationId(stationId);
            vehicle.setTcVehicleId(UUIDGenerator.getUUID());
            String payCode = vehicle.getPayCode();
            vehicle.setPayCode(Encoder.MD5Encode(payCode.getBytes()));
            vehicle.setIsDeleted(GlobalConstant.STATUS_NOTDELETE+"");

            try {
                tcVehicleService.addVehicle(vehicle);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return "redirect:/web/tcms/vehicle/list/page";
    }

}
