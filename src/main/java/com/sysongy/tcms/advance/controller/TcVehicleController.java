package com.sysongy.tcms.advance.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.RedisClientInterface;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
    DriverService driverService;
    @Autowired
    RedisClientInterface redisClientImpl;

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
        PageInfo<SysDriver> driverPageInfo = new PageInfo<SysDriver>();
        try {
//            driverPageInfo = driverService.querySearchDriverList(driver);
        }catch (Exception e){
            e.printStackTrace();
        }

        map.addAttribute("driverList",driverPageInfo.getList());
        map.addAttribute("pageInfo",driverPageInfo);
//        map.addAttribute("driver",driver);

        return "webpage/tcms/driver/driver_list";
    }


    /**
     * 添加车辆
     * @param currUser 当前用户
     * @param driver 司机
     * @param map
     * @return
     */
    @RequestMapping("/save")
    public String saveDriver(@ModelAttribute("currUser") CurrUser currUser, SysDriver driver, ModelMap map){
        int userType = currUser.getUser().getUserType();
        int result = 0;

        String stationId = currUser.getStationId();
        String operation = "insert";
        String payCode = driver.getPayCode();
        String verificationCode = driver.getUserName();
        driver.setUserName(null);
        driver.setUserStatus("0");//0 使用中 1 已冻结
        driver.setCheckedStatus("0");//审核状态 0 新注册 1 待审核 2 已通过 3 未通过
        driver.setCheckedStatus("0");
        driver.setStationId(stationId);//站点编号


        driver.setSysDriverId(UUIDGenerator.getUUID());
        driver.setPayCode(Encoder.MD5Encode(payCode.getBytes()));

        try {
            result = driverService.saveDriver(driver,operation);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/web/driver/list/page";
    }

}
