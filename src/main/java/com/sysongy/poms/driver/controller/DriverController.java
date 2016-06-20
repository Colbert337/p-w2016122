package com.sysongy.poms.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.util.GlobalConstant;

/**
 * @FileName: DriverController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.driver
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月20日, 9:14
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */

@RequestMapping("/web/driver")
@Controller
public class DriverController extends BaseContoller{

    @Autowired
    DriverService driverService;
    /**
     * 查询司机列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryDriverListPage(@ModelAttribute CurrUser currUser, SysDriver driver, ModelMap map){
        if(driver.getPageNum() == null){
            driver.setPageNum(GlobalConstant.PAGE_NUM);
            driver.setPageSize(GlobalConstant.PAGE_SIZE);
        }

        //封装分页参数，用于查询分页内容
        PageInfo<SysDriver> driverPageInfo = new PageInfo<SysDriver>();
        try {
            driverPageInfo = driverService.queryDrivers(driver);
        }catch (Exception e){
            e.printStackTrace();
        }

        map.addAttribute("userList",driverPageInfo.getList());
        map.addAttribute("pageInfo",driverPageInfo);

        return "webpage/poms/driver/driver_list";
    }
    
    @RequestMapping("/driverList")
    public String queryDriverList(@ModelAttribute CurrUser currUser, SysDriver driver, ModelMap map)throws Exception{
    	PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_review";

		try {
        PageInfo<SysDriver> pageinfo = new PageInfo<SysDriver>();
  
        pageinfo = driverService.queryDrivers(driver);
        
        bean.setRetCode(100);
		bean.setRetMsg("查询成功");
		bean.setPageInfo(ret);

		map.addAttribute("ret", bean);
		map.addAttribute("pageInfo", pageinfo);
		map.addAttribute("current_module", "webpage/poms/system/driver_review");

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
