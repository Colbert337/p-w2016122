package com.sysongy.poms.gastation.controller;

import com.sysongy.poms.gastation.service.GastationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.gastation.model.Gastation;


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

			if(!StringUtils.isEmpty(gastation.getExpiry_date_frompage())){
				String []tmpRange = gastation.getExpiry_date_frompage().split("-");
				if(tmpRange.length==2){
					gastation.setExpiry_date_after(tmpRange[0].trim()+" 00:00:00");
					gastation.setExpiry_date_before(tmpRange[1]+" 23:59:59");
				}
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
		Integer rowcount = null;

		try {
			if(StringUtils.isEmpty(gastation.getSys_gas_station_id())){
				rowcount = service.saveGastation(gastation,"insert");
			}else{
				rowcount = service.saveGastation(gastation,"update");
				if(rowcount == 1){
					ret = this.queryAllGastationList(map, new Gastation());
				}
			}
			

			bean.setRetCode(100);
			bean.setRetMsg("保存成功");
			bean.setRetValue(rowcount.toString());
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
			return  ret;
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