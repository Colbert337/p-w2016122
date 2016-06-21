package com.sysongy.poms.driver.controller;

import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.permi.model.SysRole;
import com.sysongy.util.Encoder;
import com.sysongy.util.RedisClientInterface;
import com.sysongy.util.UUIDGenerator;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.util.GlobalConstant;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
	@Autowired
	RedisClientInterface redisClientImpl;

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

        map.addAttribute("driverList",driverPageInfo.getList());
        map.addAttribute("pageInfo",driverPageInfo);

        return "webpage/tcms/driver/driver_list";
    }


	/**
	 * 添加司机
	 * @param currUser 当前用户
	 * @param driver 司机
	 * @param map
     * @return
     */
	@RequestMapping("/save")
	public String saveDriver(@ModelAttribute("currUser") CurrUser currUser, SysDriver driver, ModelMap map){
		int userType = currUser.getUser().getUserType();
		int result = 0;
		String operation = "insert";
		String payCode = driver.getPayCode();
		String verificationCode = driver.getUserName();
		driver.setUserName(null);
		driver.setUserStatus("0");//0 使用中 1 已冻结
		driver.setChecked_status("0");//审核状态 0 新注册 1 待审核 2 已通过 3 未通过
		driver.setCheckedStatus("0");

		driver.setSysDriverId(UUIDGenerator.getUUID());
		driver.setPayCode(Encoder.MD5Encode(payCode.getBytes()));

		try {
			result = driverService.saveDriver(driver,operation);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "redirect:/web/driver/list/page";
	}

    @RequestMapping("/driverList")
    public String queryDriverList(SysDriver driver, ModelMap map)throws Exception{
    	PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_review";

		try {
        PageInfo<SysDriver> pageinfo = new PageInfo<SysDriver>();
        driver.setIsIdent(0);

        pageinfo = driverService.queryDrivers(driver);
        
        bean.setRetCode(100);
		bean.setRetMsg("查询成功");
		bean.setPageInfo(ret);

		map.addAttribute("ret", bean);
		map.addAttribute("pageInfo", pageinfo);
		map.addAttribute("driver",driver);
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
    
    @RequestMapping("/review")
	public String review(ModelMap map, @RequestParam String driverid,@RequestParam String type){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_review";
		Integer rowcount = null;

		try {
				if(driverid != null && !"".equals(driverid)){
					rowcount = driverService.review(driverid, type);
				}

				ret = this.queryDriverList(new SysDriver(), map);

				bean.setRetCode(100);
				bean.setRetMsg("["+driverid+"]已审核");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryDriverList(new SysDriver(), map);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
}
