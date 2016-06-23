package com.sysongy.tcms.advance.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
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

    /**
     * 查询车队列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryFleetListPage(@ModelAttribute CurrUser currUser, TcFleet fleet, ModelMap map){
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

}
