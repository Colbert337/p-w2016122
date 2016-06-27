package com.sysongy.tcms.advance.controller;

import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import com.sysongy.tcms.advance.service.TcFleetQuotaService;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * 保存分配资金
     * @param data
     * @param map
     * @return
     */
    @RequestMapping("/save/fenpei")
    @ResponseBody
    public AjaxJson queryFleetQuota(@RequestParam String data, ModelMap map){
        AjaxJson ajaxJson = new AjaxJson();
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
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            ajaxJson.setSuccess(false);
            e.printStackTrace();
        }
            return ajaxJson;
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
