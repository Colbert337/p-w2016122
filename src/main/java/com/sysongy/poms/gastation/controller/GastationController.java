package com.sysongy.poms.gastation.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;


@RequestMapping("/web/gastation")
@Controller
public class GastationController extends BaseContoller{

	@Autowired
	private GastationService service;

	/**
	 * 加气站查询
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/gastationList")
	public String queryAllGastationList(ModelMap map, Gastation gastation) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list";

		try {
			if(gastation.getPageNum() == null){
				gastation.setPageNum(1);
				gastation.setPageSize(10);
			}

			PageInfo<Gastation> pageinfo = service.queryGastation(gastation);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gascard",gastation);
			map.addAttribute("current_module", "webpage/poms/gastation/gastation_list");
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
				gastationid = service.saveGastation(gastation,"update");
				bean.setRetMsg("["+gastationid+"]保存成功");
				ret = this.queryAllGastationList(map, new Gastation());
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
	
			ret = this.queryAllGastationList(map, new Gastation());
	
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

	/**
	 * 用户卡删除
	 * @param map
	 * @param cardid
	 * @return
	 */
	@RequestMapping("/deleteCard")
	public String deleteCard(ModelMap map, @RequestParam String gastationid){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_list";
		Integer rowcount = null;

		try {
				if(gastationid != null && !"".equals(gastationid)){
					rowcount = service.delGastation(gastationid);
				}

				ret = this.queryAllGastationList(map, new Gastation());

				bean.setRetCode(100);
				bean.setRetMsg("["+gastationid+"]删除成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllGastationList(map, new Gastation());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

}
