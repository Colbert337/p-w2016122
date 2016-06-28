package com.sysongy.poms.system.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;

@RequestMapping("/web/sysparam")
@Controller
public class SysCashBackController extends BaseContoller {
	
	@Autowired
	private SysCashBackService service;
	
	@RequestMapping("/cashbackList")
	public String queryAllCashBackList(ModelMap map, SysCashBack sysCashBack) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/system/system_cashback";

		try {
			if(sysCashBack.getPageNum() == null){
				sysCashBack.setPageNum(1);
				sysCashBack.setPageSize(10);
			}
			if(sysCashBack.getSys_cash_back_no() == null || sysCashBack.getSys_cash_back_no() == ""){
				sysCashBack.setSys_cash_back_no("101");
			}
			if(StringUtils.isEmpty(sysCashBack.getOrderby())){
				sysCashBack.setOrderby("created_date desc");
			}
			
			PageInfo<SysCashBack> pageinfo = service.queryCashBack(sysCashBack);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("sysCashBack",sysCashBack);
			map.addAttribute("current_module", "webpage/poms/system/system_cashback");
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
	
	@RequestMapping("/saveCashback")
	public String saveCashback(ModelMap map, SysCashBack sysCashBack) throws Exception{
		
		PageBean bean = new PageBean();
		String ret = "webpage/poms/system/system_cashback_new";
		String sysCashBackid = null;
		SysCashBack cashback = new SysCashBack();
		
		try {
			if(StringUtils.isEmpty(sysCashBack.getSys_cash_back_id())){
				sysCashBackid = service.saveCashBack(sysCashBack,"insert");
				bean.setRetMsg("新增成功");
			}else{
				sysCashBackid = service.saveCashBack(sysCashBack,"update");
				bean.setRetMsg("["+sysCashBackid+"]保存成功");
			}
			
			
			cashback.setSys_cash_back_no(sysCashBack.getSys_cash_back_no());
			ret = this.queryAllCashBackList(map, cashback);

			bean.setRetCode(100);
			
			bean.setRetValue(sysCashBackid);
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			map.addAttribute("sysCashBack", sysCashBack);
			
			ret = "webpage/poms/system/system_cashback_new";
			
			logger.error("", e);
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/preUpdate")
	public String preUpdate(ModelMap map, @RequestParam String sysCashBackid){
		PageBean bean = new PageBean();
		String ret = "webpage/poms/system/system_cashback_update";
		SysCashBack sysCashBack = new SysCashBack();
		
			try {
				if(sysCashBackid != null && !"".equals(sysCashBackid)){
					sysCashBack = service.queryCashBackByPK(sysCashBackid);
				}
		
				bean.setRetCode(100);
				bean.setRetMsg("根据["+sysCashBackid+"]查询返现配置成功");
				bean.setPageInfo(ret);
	
				map.addAttribute("ret", bean);
				map.addAttribute("sysCashBack", sysCashBack);
	
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
	
			ret = this.queryAllCashBackList(map, new SysCashBack());
	
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/deleteCashBack")
	public String deleteCashBack(ModelMap map, SysCashBack sysCashBack, @RequestParam String sysCashBackid){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/system/system_cashback";
		Integer rowcount = null;

		try {
				if(sysCashBackid != null && !"".equals(sysCashBackid)){
					rowcount = service.delCashBack(sysCashBackid);
				}

				ret = this.queryAllCashBackList(map, sysCashBack);

				bean.setRetCode(100);
				bean.setRetMsg("删除成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);

		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllCashBackList(map, sysCashBack);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
	
}
