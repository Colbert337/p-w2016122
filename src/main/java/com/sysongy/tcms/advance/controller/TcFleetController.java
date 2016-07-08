package com.sysongy.tcms.advance.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import com.sysongy.tcms.advance.model.TcFleetVehicle;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.tcms.advance.service.TcFleetVehicleService;
import com.sysongy.tcms.advance.service.TcVehicleService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @FileName: TcFleetController
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月22日, 18:49
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Controller
@RequestMapping("/web/tcms/fleet")
public class TcFleetController extends BaseContoller {


    @Autowired
    TcFleetService tcFleetService;
    @Autowired
    TcFleetVehicleService tcFleetVehicleService;
    @Autowired
    TcVehicleService tcVehicleService;

    /**
     * 查询车队列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryFleetListPage(@ModelAttribute CurrUser currUser, TcFleet fleet, ModelMap map) throws Exception{
        String stationId = currUser.getStationId();
        if(fleet.getPageNum() == null){
            fleet.setPageNum(GlobalConstant.PAGE_NUM);
            fleet.setPageSize(GlobalConstant.PAGE_SIZE);
        }
        fleet.setStationId(stationId);
        //封装分页参数，用于查询分页内容
        PageInfo<Map<String, Object>> fleetPageInfo = new PageInfo<Map<String, Object>>();
        try {
            fleetPageInfo = tcFleetService.queryFleetMapList(fleet);
        }catch (Exception e){
            e.printStackTrace();
        }

        map.addAttribute("fleetList",fleetPageInfo.getList());
        map.addAttribute("pageInfo",fleetPageInfo);
        map.addAttribute("fleet",fleet);

        return "webpage/tcms/advance/fleet_list";
    }

    /**
     * 查询车队信息
     * @param fleet
     * @param map
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public TcFleet queryFleet(TcFleet fleet, ModelMap map){
        TcFleet tcFleet = tcFleetService.queryFleet(fleet);

        return tcFleet;
    }

    /**
     * 根据用户名称查询车队信息
     * @param fleet
     * @param map
     * @return
     */
    @RequestMapping("/info/name")
    @ResponseBody
    public JSONObject queryFleetByName( @ModelAttribute CurrUser currUser, TcFleet fleet, ModelMap map){
        JSONObject json = new JSONObject();
        String stationId = currUser.getStationId();
        String fleetName = "";
        if(fleet != null && fleet.getFleetName() != null && !"".equals(fleet.getFleetName())){
            fleetName = fleet.getFleetName().trim();
            TcFleet tcFleet = tcFleetService.queryFleetByName(stationId, fleetName);

            if(tcFleet == null){
                json.put("valid",true);
            }else{
                json.put("valid",false);
            }
        }else{
            json.put("valid",false);
        }

        return json;
    }

    /**
     * 添加车队
     * @param currUser 当前用户
     * @param fleet 车队
     * @param map
     * @return
     */
    @RequestMapping("/save")
    public String saveFleet(@ModelAttribute("currUser") CurrUser currUser, TcFleet fleet, ModelMap map){
        int userType = currUser.getUser().getUserType();
        int result = 0;

        if(fleet.getTcFleetId() != null && fleet.getTcFleetId() != ""){
            tcFleetService.updateFleet(fleet);
            TcFleetQuota fleetQuota = new TcFleetQuota();
        }else{
            String stationId = currUser.getStationId();
            fleet.setStationId(stationId);
            fleet.setTcFleetId(UUIDGenerator.getUUID());
            fleet.setIsDeleted(GlobalConstant.STATUS_NOTDELETE+"");

            try {
                tcFleetService.addFleet(fleet);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return "redirect:/web/tcms/fleet/list/page";
    }


    /**
     * 查询车队下车辆列表
     * @param
     * @param map
     * @return
     */
    @RequestMapping("/list/fv")
    @ResponseBody
    public List<Map<String, Object>> queryFleetVehicleList(@ModelAttribute("currUser") CurrUser currUser,TcFleetVehicle tcFleetVehicle, ModelMap map){
        String stationId = currUser.getStationId();
        tcFleetVehicle.setStationId(stationId);
        List<Map<String, Object>> fleetVehicleMapList = tcFleetVehicleService.queryFleetVehicleMapList(tcFleetVehicle);
        return fleetVehicleMapList;
    }

    /**
     * 分配车队车辆
     * @param currUser 当前用户
     * @param fleet 车队
     * @param map
     * @return
     */
    @RequestMapping("/save/fv")
    public String saveFleetVehicle(HttpServletRequest request, @ModelAttribute("currUser") CurrUser currUser, TcFleet fleet, ModelMap map) throws Exception{
        String selectedVeh = fleet.getSysUserId();//临时存储已选中车辆ID
        String fleetId = "";
        String stationId = currUser.getStationId();
        if(fleet != null ){
            fleetId = fleet.getTcFleetId();
        }

        try {
            List<TcFleetVehicle> fleetVehicleList = new ArrayList<>();
            List<TcVehicle> vehicleList = new ArrayList<>();
            if (selectedVeh != null && !"".equals(selectedVeh)){
                String[] vehicleArray = selectedVeh.split(",");
                for (String vehicleId:vehicleArray){
                    TcFleetVehicle fleetVehicle = new TcFleetVehicle();

                    fleetVehicle.setTcFleetVehicleId(UUIDGenerator.getUUID());
                    fleetVehicle.setStationId(stationId);
                    fleetVehicle.setTcFleetId(fleetId);
                    fleetVehicle.setTcVehicleId(vehicleId);

                    fleetVehicleList.add(fleetVehicle);

                    TcVehicle vehicle = new TcVehicle();
                    vehicle.setTcVehicleId(vehicleId);
                    vehicle.setIsAllot(1);//是否分配 0 不分配 1 分配
                    vehicleList.add(vehicle);

                    tcVehicleService.updateVehicle(vehicle);
                }

                tcFleetVehicleService.addFleetVehicleList(fleetVehicleList,fleetId,stationId);
            }else{
                TcFleetVehicle fleetVehicle = new TcFleetVehicle();
                fleetVehicle.setTcFleetId(fleetId);

                //删除当前车队车辆关系
                tcFleetVehicleService.deleteFleetVehicle(fleetVehicle);

                //查询当前运输公司未分配车辆
                fleetVehicle.setStationId(stationId);
                List<TcVehicle> tcVehicleList = tcVehicleService.queryVehicleByStationId(stationId);
                if(tcVehicleList != null && tcVehicleList.size() > 0){
                    for (TcVehicle vehicleTemp:tcVehicleList) {
                        TcVehicle vehicle = new TcVehicle();
                        vehicle.setTcVehicleId(vehicleTemp.getTcVehicleId());
                        vehicle.setIsAllot(0);//是否分配 0 不分配 1 分配
                        vehicleList.add(vehicle);
                        tcVehicleService.updateVehicle(vehicle);
                    }

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/web/tcms/fleet/list/page";
    }
}
