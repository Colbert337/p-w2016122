package com.sysongy.poms.transportion.controller;

import com.sysongy.poms.base.model.CurrUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.system.service.SysDepositLogService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.util.GlobalConstant;


@RequestMapping("/web/transportion")
@Controller
public class TransportionController extends BaseContoller{
	
	@Autowired
	private TransportionService service;
	@Autowired
	private SysDepositLogService depositLogService;
	/**
	 * 运输公司查询
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transportionList")
	public String queryAllTransportionList(ModelMap map, Transportion transportion) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_list";

		try {
			if(transportion.getPageNum() == null){
				transportion.setPageNum(1);
				transportion.setPageSize(10);
			}
			if(StringUtils.isEmpty(transportion.getOrderby())){
				transportion.setOrderby("created_time desc");
			}

			if(!StringUtils.isEmpty(transportion.getExpiry_date_frompage())){
				String []tmpRange = transportion.getExpiry_date_frompage().split("-");
				if(tmpRange.length==2){
					transportion.setExpiry_date_after(tmpRange[0].trim()+" 00:00:00");
					transportion.setExpiry_date_before(tmpRange[1]+" 23:59:59");
				}
			}

			PageInfo<Transportion> pageinfo = service.queryTransportion(transportion);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("transportion",transportion);
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
	
	@RequestMapping("/transportionList2")
	public String queryAllTransportionList2(ModelMap map, Transportion transportion) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_list2";

		try {
			if(transportion.getPageNum() == null){
				transportion.setPageNum(1);
				transportion.setPageSize(10);
			}
			if(StringUtils.isEmpty(transportion.getOrderby())){
				transportion.setOrderby("created_time desc");
			}

			if(!StringUtils.isEmpty(transportion.getExpiry_date_frompage())){
				String []tmpRange = transportion.getExpiry_date_frompage().split("-");
				if(tmpRange.length==2){
					transportion.setExpiry_date_after(tmpRange[0].trim()+" 00:00:00");
					transportion.setExpiry_date_before(tmpRange[1]+" 23:59:59");
				}
			}

			PageInfo<Transportion> pageinfo = service.queryTransportion(transportion);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("transportion",transportion);
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
	@RequestMapping("/saveTransportion")
	public String saveTransportion(ModelMap map, Transportion transportion) throws Exception{
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_new";
		String transportionid = null;

		try {
			if(StringUtils.isEmpty(transportion.getSys_transportion_id())){
				transportionid = service.saveTransportion(transportion,"insert");
				bean.setRetMsg("["+transportionid+"]新增成功");
				ret = "webpage/poms/transportion/transportion_upload";
			}else{
				ret = "webpage/poms/transportion/transportion_update";
				transportionid = service.saveTransportion(transportion,"update");
				bean.setRetMsg("["+transportionid+"]保存成功");
				ret = this.queryAllTransportionList(map, transportion);
			}

			bean.setRetCode(100);
			bean.setRetValue(transportionid);
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
	public String preUpdate(ModelMap map, @RequestParam String transportionid){
		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_update";
		Transportion station = new Transportion();
		
			try {
				if(transportionid != null && !"".equals(transportionid)){
					station = service.queryTransportionByPK(transportionid);
				}
		
				bean.setRetCode(100);
				bean.setRetMsg("根据["+transportionid+"]查询Transportion成功");
				bean.setPageInfo(ret);
	
				map.addAttribute("ret", bean);
				map.addAttribute("station", station);
	
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
	
			ret = this.queryAllTransportionList(map, new Transportion());
	
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

	/**
	 * 根据主键查询运输公司信息
	 * @param map
	 * @param currUser
     * @return
     */
	@RequestMapping("/info")
	public String queryTransportInfo(ModelMap map, @ModelAttribute CurrUser currUser){

		String transportionId = currUser.getStationId();
		Transportion transportion = new Transportion();
		try {
			transportion = service.queryTransportionByPK(transportionId);
		}catch (Exception e){
			e.printStackTrace();
		}

		map.addAttribute("transportion",transportion);
		return "webpage/poms/transportion/transport_info";
	}
	
	@RequestMapping("/depositList")
	public String querydepositList(ModelMap map, SysDepositLog deposit) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_deposit_log";

		try {
			if(deposit.getPageNum() == null){
				deposit.setPageNum(1);
				deposit.setPageSize(10);
			}
			if(StringUtils.isEmpty(deposit.getOrderby())){
				deposit.setOrderby("optime desc");
			}
			
			deposit.setStation_type(GlobalConstant.OrderOperatorTargetType.TRANSPORTION);
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
	
	@RequestMapping("/deposiTransportion")
	public String deposiTransportion(ModelMap map, SysDepositLog deposit, @ModelAttribute CurrUser currUser){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/transportion/transportion_list";
		Integer rowcount = null;

		try {
				if(deposit.getAccountId() != null && !"".equals(deposit.getAccountId())){
					rowcount = service.updatedeposiTransportion(deposit, currUser.getUserId());
				}

				ret = this.queryAllTransportionList2(map, new Transportion());

				bean.setRetCode(100);
				bean.setRetMsg("保证金设置成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllTransportionList2(map, new Transportion());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

}
