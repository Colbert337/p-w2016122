package com.sysongy.poms.liquid.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.liquid.model.SysGasSource;
import com.sysongy.poms.liquid.service.LiquidService;


@RequestMapping("/web/liquid")
@Controller
public class LiquidController extends BaseContoller{

	@Autowired
	private LiquidService service;
	
	SysGasSource gasource;
	
	@RequestMapping("/liquidList")
	public String queryAllGasSourceList(ModelMap map, SysGasSource gasource) throws Exception{
		
		this.gasource = gasource;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/liquid/liquid_list";

		try {
			if(gasource.getPageNum() == null){
				gasource.setPageNum(1);
				gasource.setPageSize(10);
			}
			if(gasource.getConvertPageNum() != null){
				if(gasource.getConvertPageNum() > gasource.getPageNumMax()){
					gasource.setPageNum(gasource.getPageNumMax());
				}else{
					gasource.setPageNum(gasource.getConvertPageNum());
				}
			}
			if(StringUtils.isEmpty(gasource.getOrderby())){
				gasource.setOrderby("created_date desc");
			}

			PageInfo<SysGasSource> pageinfo = service.querySysGasSource(gasource);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gasource", gasource);
			map.addAttribute("current_module", "webpage/poms/liquid/liquid_list");
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
	@RequestMapping("/saveLiquid")
	public String saveLiquid(ModelMap map, SysGasSource gasource) throws Exception{
		PageBean bean = new PageBean();
		String ret = "webpage/poms/liquid/liquid_new";
		String gastationid = null;

		try {
			if(StringUtils.isEmpty(gasource.getSys_gas_source_id())){
				gastationid = service.saveGasSource(gasource,"insert");
				bean.setRetMsg("["+gastationid+"]新增成功");
			}else{
				gastationid = service.saveGasSource(gasource,"update");
				bean.setRetMsg("["+gastationid+"]保存成功");
			}
			
			ret = this.queryAllGasSourceList(map, this.gasource==null?new SysGasSource():this.gasource);
			bean.setRetCode(100);
			
			bean.setRetValue(gastationid);
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			map.addAttribute("liquid", gasource);
			
			logger.error("", e);
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/preUpdate")
	public String preUpdate(ModelMap map, @RequestParam String gasourceid){
		PageBean bean = new PageBean();
		String ret = "webpage/poms/liquid/liquid_update";
		SysGasSource gasource = new SysGasSource();
		
			try {
				if(gasourceid != null && !"".equals(gasourceid)){
					gasource = service.queryGasSourceByPK(gasourceid);
				}
		
				bean.setRetCode(100);
				bean.setRetMsg("根据["+gasourceid+"]查询成功");
				bean.setPageInfo(ret);
	
				map.addAttribute("ret", bean);
				map.addAttribute("gasource", gasource);
	
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
	
			ret = this.queryAllGasSourceList(map, this.gasource==null?new SysGasSource():this.gasource);
	
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

}
