package com.sysongy.tcms.advance.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import com.sysongy.tcms.advance.service.TcFleetQuotaService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 查询车辆信息
     * @param FleetQuota
     * @param map
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public Map<String, Object> queryFleetQuota(TcFleetQuota FleetQuota, ModelMap map){
        Map<String, Object> FleetQuotaMap = new HashMap<>();
        GasCard gasCard = new GasCard();
        TcFleetQuota tcFleetQuota = tcFleetQuotaService.queryFleetQuota(FleetQuota);
        FleetQuotaMap.put("FleetQuota",tcFleetQuota);
        try {
            /*gasCard = gasCardService.queryGasCardInfo(tcFleetQuota.getCardNo());*/
        }catch (Exception e){
            e.printStackTrace();
        }

        FleetQuotaMap.put("gasCard",gasCard);
        return FleetQuotaMap;
    }

    /**
     * 添加车辆
     * @param currUser 当前用户
     * @param FleetQuota 车辆
     * @param map
     * @return
     */
    @RequestMapping("/save")
    public String saveFleetQuota(@ModelAttribute("currUser") CurrUser currUser, TcFleetQuota FleetQuota, ModelMap map){
        int userType = currUser.getUser().getUserType();
        int result = 0;

        if(FleetQuota.getTcFleetQuotaId() != null && FleetQuota.getTcFleetQuotaId() != ""){
            /*FleetQuota.setPayCode(null);*/
            tcFleetQuotaService.updateFleetQuota(FleetQuota);
        }else{
            String stationId = currUser.getStationId();
            FleetQuota.setStationId(stationId);
            FleetQuota.setTcFleetQuotaId(UUIDGenerator.getUUID());
           /* String payCode = FleetQuota.getPayCode();
            FleetQuota.setPayCode(Encoder.MD5Encode(payCode.getBytes()));
            FleetQuota.setIsDeleted(GlobalConstant.STATUS_NOTDELETE+"");*/

            try {
                tcFleetQuotaService.addFleetQuota(FleetQuota);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return "redirect:/web/tcms/FleetQuota/list/page";
    }

}
