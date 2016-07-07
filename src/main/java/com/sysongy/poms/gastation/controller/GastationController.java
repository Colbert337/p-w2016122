package com.sysongy.poms.gastation.controller;

import org.apache.commons.lang.StringUtils;
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
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.system.service.SysDepositLogService;
import com.sysongy.util.GlobalConstant;


@RequestMapping("/web/gastation")
@Controller
public class GastationController extends BaseContoller{

	@Autowired
	private GastationService service;
	@Autowired
	private SysDepositLogService depositLogService;
	@Autowired
	private SysUserService sysUserService;
	
	private Gastation gastation;
	
	
	/**
	 * 加气站查询
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/gastationList")
	public String queryAllGastationList(ModelMap map, Gastation gastation) throws Exception{
		
		this.gastation = gastation;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list";

		try {
			if(gastation.getPageNum() == null){
				gastation.setPageNum(1);
				gastation.setPageSize(10);
			}
			if(StringUtils.isEmpty(gastation.getOrderby())){
				gastation.setOrderby("created_time desc");
			}

			PageInfo<Gastation> pageinfo = service.queryGastation(gastation);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gastation",gastation);
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
	
	@RequestMapping("/gastationList2")
	public String queryAllGastationList2(ModelMap map, Gastation gastation) throws Exception{

		this.gastation = gastation;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list2";

		try {
			if(gastation.getPageNum() == null){
				gastation.setPageNum(1);
				gastation.setPageSize(10);
			}
			if(StringUtils.isEmpty(gastation.getOrderby())){
				gastation.setOrderby("created_time desc");
			}

			PageInfo<Gastation> pageinfo = service.queryGastation(gastation);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gastation",gastation);
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
	
	@RequestMapping("/queryGastationInfo")
	public String queryGastationInfo(ModelMap map, @ModelAttribute CurrUser currUser) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_infomation";

		try {
			Gastation gastation = service.queryGastationByPK(currUser.getStationId());

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("gastation",gastation);
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
	
	@RequestMapping("/depositList")
	public String querydepositList(ModelMap map, SysDepositLog deposit, @ModelAttribute CurrUser currUser) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_deposit_log";

		try {
			if(deposit.getPageNum() == null){
				deposit.setPageNum(1);
				deposit.setPageSize(10);
			}
			if(StringUtils.isEmpty(deposit.getOrderby())){
				deposit.setOrderby("optime desc");
			}
			
			if(GlobalConstant.USER_TYPE_STATION == currUser.getUserType()){
				deposit.setStationId(currUser.getStationId());
			}
			
			deposit.setStation_type(GlobalConstant.OrderOperatorTargetType.GASTATION);
			PageInfo<SysDepositLog> pageinfo = depositLogService.queryDepositLog(deposit);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("deposit",deposit);
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
	/**
	 * 用户卡入库
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveGastation")
	public String saveGastation(ModelMap map, Gastation gastation) throws Exception{
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_new";
		String gastationid = null;

		try {
			if(StringUtils.isEmpty(gastation.getSys_gas_station_id())){
				gastationid = service.saveGastation(gastation,"insert");
				bean.setRetMsg("["+gastationid+"]新增成功");
				ret = "webpage/poms/gastation/gastation_upload";
			}else{
				ret = "webpage/poms/gastation/gastation_update";
				gastationid = service.saveGastation(gastation,"update");
				bean.setRetMsg("["+gastationid+"]保存成功");
				ret = this.queryAllGastationList(map, this.gastation);
			}

			bean.setRetCode(100);
			
			bean.setRetValue(gastationid);
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			map.addAttribute("station", gastation);
			
			logger.error("", e);
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/preUpdate")
	public String preUpdate(ModelMap map, @RequestParam String gastationid){
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_update";
		Gastation station = new Gastation();
		
			try {
				if(gastationid != null && !"".equals(gastationid)){
					station = service.queryGastationByPK(gastationid);
				}
		
				bean.setRetCode(100);
				bean.setRetMsg("根据["+gastationid+"]查询Gastation成功");
				bean.setPageInfo(ret);
	
				map.addAttribute("ret", bean);
				map.addAttribute("station", station);
	
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
	
			ret = this.queryAllGastationList(map, this.gastation);
	
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

	@RequestMapping("/deleteCard")
	public String deleteCard(ModelMap map, @RequestParam String gastationid){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list";
		Integer rowcount = null;

		try {
				if(gastationid != null && !"".equals(gastationid)){
					rowcount = service.delGastation(gastationid);
				}

				ret = this.queryAllGastationList(map, this.gastation);

				bean.setRetCode(100);
				bean.setRetMsg("["+gastationid+"]删除成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllGastationList(map, this.gastation);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/resetPassword")
	public String resetPassword(ModelMap map, @RequestParam String gastationid, @RequestParam String username){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list";
		Integer rowcount = null;

		try {
				if(gastationid != null && !"".equals(gastationid)){
					SysUser user = new SysUser();
					user.setStationId(gastationid);
					user.setUserName(username);
					rowcount = sysUserService.updateUserByUserName(user);
				}

				ret = this.queryAllGastationList(map, this.gastation);

				bean.setRetCode(100);
				bean.setRetMsg("["+gastationid+"]管理员["+username+"]密码已成功重置为 111111");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllGastationList(map, this.gastation);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

	@RequestMapping("/depositGastation")
	public String depositGastation(ModelMap map, SysDepositLog deposit, @ModelAttribute CurrUser currUser){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list";
		Integer rowcount = null;

		try {
				if(deposit.getAccountId() != null && !"".equals(deposit.getAccountId())){
					rowcount = service.updatedepositGastation(deposit, currUser.getUser().getUserName());
				}

				ret = this.queryAllGastationList2(map, this.gastation);

				bean.setRetCode(100);
				bean.setRetMsg("["+deposit.getStationName()+"]充值成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllGastationList2(map, this.gastation);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
	
}
