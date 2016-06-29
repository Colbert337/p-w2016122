package com.sysongy.poms.driver.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.model.SysDriverReviewStr;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.RedisClientInterface;
import com.sysongy.util.UUIDGenerator;

import net.sf.json.JSONObject;

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
	@Autowired
	SysUserAccountService sysUserAccountService;

	SysDriver driver;
	/**
     * 查询司机列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryDriverListPage(@ModelAttribute CurrUser currUser, SysDriver driver, ModelMap map){
		String stationId = currUser.getStationId();
		if(driver.getPageNum() == null){
            driver.setPageNum(GlobalConstant.PAGE_NUM);
            driver.setPageSize(GlobalConstant.PAGE_SIZE);
        }
		driver.setStationId(stationId);

        //封装分页参数，用于查询分页内容
        PageInfo<SysDriver> driverPageInfo = new PageInfo<SysDriver>();
        try {
            driverPageInfo = driverService.querySearchDriverList(driver);
        }catch (Exception e){
            e.printStackTrace();
        }

        map.addAttribute("driverList",driverPageInfo.getList());
        map.addAttribute("pageInfo",driverPageInfo);
		map.addAttribute("driver",driver);

        return "webpage/tcms/driver/driver_list";
    }

    @RequestMapping("/driverListStr")
    public String queryDriverReviewStr(@ModelAttribute CurrUser currUser, SysDriverReviewStr driver, ModelMap map){
		String stationId = currUser.getStationId();
		if(driver.getPageNum() == null){
            driver.setPageNum(GlobalConstant.PAGE_NUM);
            driver.setPageSize(GlobalConstant.PAGE_SIZE);
        }
		driver.setStationId(stationId);

        //封装分页参数，用于查询分页内容
        PageInfo<SysDriverReviewStr> driverPageInfo = new PageInfo<SysDriverReviewStr>();
        try {
            driverPageInfo = driverService.queryDriversLog(driver);
        }catch (Exception e){
            e.printStackTrace();
        }

        map.addAttribute("driverList",driverPageInfo.getList());
        map.addAttribute("pageInfo",driverPageInfo);
		map.addAttribute("driver",driver);

        return "webpage/poms/system/driver_review_log";
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

		String stationId = currUser.getStationId();
		String operation = "insert";
		String payCode = driver.getPayCode();

		driver.setUserName(null);
		driver.setUserStatus("0");//0 使用中 1 已冻结
		driver.setCheckedStatus("0");//审核状态 0 新注册 1 待审核 2 已通过 3 未通过
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

    @RequestMapping("/driverList")
    public String queryDriverList(SysDriver driver, ModelMap map)throws Exception{
    	PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_review";

		try {
        PageInfo<SysDriver> pageinfo = new PageInfo<SysDriver>();
        
        driver.setNotin_checked_status("0");

        pageinfo = driverService.queryDrivers(driver);
        
        bean.setRetCode(100);
		bean.setRetMsg("查询成功");
		bean.setPageInfo(ret);
		
		map.addAttribute("ret", bean);
		map.addAttribute("pageInfo", pageinfo);
		map.addAttribute("driver",driver);

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

    @RequestMapping("/driverInfoList")
    public String queryDriverInfoList(SysDriver driver, ModelMap map)throws Exception{
    	this.driver = driver;
    	PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_info";

		try {
	        PageInfo<SysDriver> pageinfo = new PageInfo<SysDriver>();

	        if(StringUtils.isEmpty(driver.getOrderby())){
	        	driver.setOrderby("updated_date desc");
	        }

	        pageinfo = driverService.queryDrivers(driver);

	        bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("driver",driver);
			map.addAttribute("current_module", "webpage/poms/system/driver_info");

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

    @RequestMapping("/changeDriverStatus")
    public String changeDriverStatus(@RequestParam String accountid, @RequestParam String status, ModelMap map)throws Exception{
    	PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_info";

		try {
			sysUserAccountService.changeStatus(accountid, status);

			SysDriver driver = new SysDriver();
			driver.setSysUserAccountId(accountid);
			
			ret = this.queryDriverInfoList(this.driver, map);

			bean.setRetCode(100);
			bean.setRetMsg("状态修改成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
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
	public String review(ModelMap map, @RequestParam String driverid,@RequestParam String type,@RequestParam String memo, @ModelAttribute("currUser") CurrUser currUser){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/system/driver_review";
		Integer rowcount = null;

		try {
				if(driverid != null && !"".equals(driverid)){
					rowcount = driverService.updateAndReview(driverid, type, memo, currUser.getUser().getUserName());
				}

				ret = this.queryDriverList(this.driver, map);

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
	/**
	 * 司机离职
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteDriverByIds(HttpServletRequest request,ModelMap map){
		SysDriver driver = new SysDriver();
		String[] ids = request.getParameterValues("pks");
		List<String> idList = new ArrayList<>();
		if(ids != null && ids.length > 0){
			for (int i = 0;i < ids.length;i++) {
				idList.add(ids[i]);
			}
		}

		driverService.deleteDriverByIds(idList);
		return "redirect:/web/driver/list/page";
	}

	@RequestMapping("/info/isExist")
	@ResponseBody
	public JSONObject queryRoleList(HttpServletRequest request, @RequestParam String mobilePhone, ModelMap map){
		JSONObject json = new JSONObject();
		SysDriver sysDriver = new SysDriver();
		sysDriver.setMobilePhone(mobilePhone);
		try {
			SysDriver driver = driverService.queryDriverByMobilePhone(sysDriver);
			if (driver != null){
				json.put("valid",false);
			}else{
				json.put("valid",true);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return json;
	}
}
