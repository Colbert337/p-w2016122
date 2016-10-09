package com.sysongy.poms.card.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.util.DateTimeHelper;
import com.sysongy.util.DateUtil;
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
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.model.GasCardLog;
import com.sysongy.poms.card.model.GasCardReturn;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.util.ExportUtil;
import com.sysongy.util.GlobalConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RequestMapping("/web/card")
@Controller
public class CardController extends BaseContoller {

	@Autowired
	private GasCardService service;

	/**
	 * 用户卡查询
	 * 
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cardList")
	public String queryAllCardList(ModelMap map, GasCard gascard) throws Exception {

		PageBean bean = new PageBean();
		String ret = "webpage/poms/card/card_list";

		try {
			if (StringUtils.isEmpty(gascard.getOrderby())) {
				gascard.setOrderby("card_no desc");
			}

			PageInfo<GasCard> pageinfo = service.queryGasCard(gascard);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gascard", gascard);
			map.addAttribute("current_module", "webpage/poms/card/card_list");
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 用户卡入库
	 * 
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveCard")
	public String saveCard(ModelMap map, GasCard gascard, HttpServletResponse response) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/card/card_new";
		Integer rowcount = null;

		try {
			rowcount = service.saveGasCard(gascard);
			bean.setRetCode(100);
			bean.setRetMsg("入库成功");
			bean.setRetValue(rowcount.toString());
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg("入库失败，不可重复入库!");
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 根据批次生成报表
	 * 
	 * @param gascard
	 * @param response
	 */
	@RequestMapping("/generateaTable")
	public String generateaTable(GasCardLog gascard, HttpServletResponse response, @RequestParam("batch_no") String a) {
		// TODO Auto-generated method stub
		// 第一列
		try {
			gascard.setBatch_no(a);

			List<GasCardLog> list = service.queryGasCardForList(gascard);
			int cells = 0; // 记录条数

			if (list != null && list.size() > 0) {
				cells += list.size();
			}
			OutputStream os = response.getOutputStream();
			ExportUtil reportExcel = new ExportUtil();
			String downLoadFileName = gascard.getBatch_no() + ".xls";
			downLoadFileName = "_" + downLoadFileName;
			// try {
			// response.setHeader("Content-Disposition",
			// "attachment;filename=" +
			// java.net.URLEncoder.encode(downLoadFileName, "UTF-8"));
			// } catch (UnsupportedEncodingException e1) {
			// response.setHeader("Content-Disposition", "attachment;filename="
			// + downLoadFileName);
			// }
			response.addHeader("Content-Disposition", "attachment; filename=" + response.encodeURL(downLoadFileName));

			String[][] content = new String[cells + 1][9];// [行数][列数]
			content[0] = new String[] { "卡号", "批次", "所属工作站", "工作站领取人", "用户卡类型", "用户卡状态", "操作员", "入库时间", "卡属性", "操作动作" };
			int i = 1;
			if (list != null && list.size() > 0) {
				for (GasCardLog card : list) {
					String card_no = card.getCard_no();
					String operator = card.getOperator();
					String batch_no = card.getBatch_no();
					String card_property = card.getCard_property().equals("1") ? "个人" : "车辆";
					String card_type = card.getMname();
					String action = card.getAction();
					String work = card.getWork();
					String workstation_resp=card.getWorkstation_resp();
					switch (card.getAction()) {
					case "0":
						action = "入库";
						break;
					case "1":
						action = "出库";
						break;
					case "2":
						action = "删除";
						break;

					default:
						break;
					}
					String card_status = card.getCard_status();
					switch (card.getCard_status()) {
					case "0": {
						card_status = "已冻结";
						break;
					}
					case "1": {
						card_status = "已入库";
						break;
					}
					case "2": {
						card_status = "已出库";
						break;
					}
					case "3": {
						card_status = "未发放";
						break;
					}
					case "4": {
						card_status = "使用中";
						break;
					}
					case "5": {
						card_status = "已失效";
						break;
					}

					default:
						break;
					}
					String storage_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(card.getStorage_time());

					content[i] = new String[] { card_no, batch_no, work,workstation_resp, card_type, card_status, operator, storage_time,
							card_property, action };
					i++;
				}
			}

			String[] mergeinfo = new String[] { "0,0,0,0" };
			// 单元格默认宽度
			String sheetName = "轨迹";
			reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, null, 22, null, 0, null, null, false);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return null;
		}

	}

	@RequestMapping("/saveCardMultiple")
	public String saveCardMultiple(ModelMap map, GasCard gascard) throws Exception {
		PageBean bean = new PageBean();
		String ret = "webpage/poms/card/card_new";
		Integer rowcount = null;

		try {
			rowcount = service.saveGasCard(gascard);

			bean.setRetCode(100);
			bean.setRetMsg("入库成功");
			bean.setRetValue(rowcount.toString());
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}
	}

	/**
	 * 用户卡删除
	 * 
	 * @param map
	 * @param cardid
	 * @return
	 */
	@RequestMapping("/deleteCard")
	public String deleteCard(GasCard gascard, ModelMap map, @RequestParam String cardid,
			@ModelAttribute("currUser") CurrUser currUser) {

		PageBean bean = new PageBean();
		String ret = "webpage/poms/card/card_list";
		Integer rowcount = null;

		try {
			if (cardid != null && !"".equals(cardid)) {
				rowcount = service.deleteGasCard(cardid, currUser);
			}

			ret = this.queryAllCardList(map, gascard);

			bean.setRetCode(100);
			bean.setRetMsg("[" + cardid + "]删除成功");
			bean.setRetValue(rowcount.toString());
			map.addAttribute("gascard", gascard);
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);

		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllCardList(map, gascard);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}
	}

	@ResponseBody
	@RequestMapping("/checkCardMultiple")
	public JSONArray checkCardMultiple(ModelMap map, @RequestParam String cardidStart, @RequestParam String cardidEnd)
			throws Exception {

		Integer start = Integer.valueOf(cardidStart);
		Integer end = Integer.valueOf(cardidEnd);
		JSONArray arr = new JSONArray();

		for (int i = start; i <= end; i++) {
			Boolean exist = service.checkCardExist(String.valueOf(i));
			GasCardReturn tmp = new GasCardReturn();
			tmp.setExist(exist ? "1" : "0");
			tmp.setCard_no(String.valueOf(i));
			arr.add(tmp);
		}

		return arr;
	}

	/**
	 * 检查用户卡是否存在于资源库
	 * 
	 * @param map
	 * @param cardid
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/checkCard")
	public String checkCard(ModelMap map, @RequestParam String cardid) throws Exception {

		Boolean exist = service.checkCardExist(cardid);

		return exist ? "1" : "0";
	}

	@ResponseBody
	@RequestMapping("/getCard")
	public JSONObject checkCard(@RequestParam String cardno) throws Exception {

		GasCard gasCard = service.selectByCardNoForCRM(cardno);
		JSONObject json = new JSONObject();

		if (gasCard != null) {
			json.put("status", gasCard.getCard_status());
			json.put("station", gasCard.getWorkstation());
			json.put("card_property", gasCard.getCard_property());
		}

		return json;
	}

	@ResponseBody
	@RequestMapping("/checkMoveCardMultiple")
	public JSONArray checkMoveCardMultiple(ModelMap map, @RequestParam String cardidStart,
			@RequestParam String cardidEnd) throws Exception {

		Integer start = Integer.valueOf(cardidStart);
		Integer end = Integer.valueOf(cardidEnd);
		JSONArray arr = new JSONArray();

		for (int i = start; i <= end; i++) {
			String exist = service.checkMoveCard(String.valueOf(i));
			GasCardReturn tmp = new GasCardReturn();
			tmp.setExist(exist);
			tmp.setCard_no(String.valueOf(i));
			arr.add(tmp);
		}

		return arr;
	}

	/**
	 * 用户卡出库前检验卡状态
	 * 
	 * @param map
	 * @param cardid
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/checkMoveCard")
	public String checkMoveCard(ModelMap map, @RequestParam String cardid) throws Exception {

		return service.checkMoveCard(cardid);
	}

	/**
	 * 用户卡出库
	 * 
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/moveCard")
	public String updateAndMoveCard(ModelMap map, GasCard gascard) throws Exception {

		PageBean bean = new PageBean();
		String ret = "webpage/poms/card/card_move";
		Integer rowcount = null;

		try {
			rowcount = service.updateAndMoveCard(gascard);

			bean.setRetCode(100);
			bean.setRetMsg("出库成功");
			bean.setRetValue(rowcount.toString());
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}
	}

	@ResponseBody
	@RequestMapping("/updateCardStatus")
	public String updateCardStatus(ModelMap map, @RequestParam String cardno, @RequestParam String cardstatus,
			@RequestParam(required = false) String memo) throws Exception {

		GasCard gascard = new GasCard();

		gascard.setCard_no(cardno);
		gascard.setCard_status(cardstatus);
		gascard.setMemo(memo);

		return service.updateGasCardInfo(gascard).toString();
	}

	@RequestMapping("/update/freeze")
	@ResponseBody
	public String updateCardFreeze(ModelMap map, GasCard gascard) throws Exception {
		String result = "succ";
		gascard.setCard_status(GlobalConstant.CardStatus.PAUSED);
		service.updateGasCardInfo(gascard);
		return result;
	}

	/**
	 * 用户卡轨迹查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cardLogList")
	public String queryAllCardLogList(ModelMap map, GasCardLog gascardlog) throws Exception {

		PageBean bean = new PageBean();
		String ret = "webpage/poms/card/cardlog_list";

		try {
			if (StringUtils.isEmpty(gascardlog.getOrderby())) {
				gascardlog.setOrderby("optime desc");
			}

			PageInfo<GasCardLog> pageinfo = service.queryGasCardLog(gascardlog);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gascardlog", gascardlog);
			map.addAttribute("current_module", "webpage/poms/card/cardlog_list");
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}
	}

	@RequestMapping("/cardListReport")
	public String cardListReport(ModelMap map, GasCard gascard, HttpServletRequest request,HttpServletResponse response, @ModelAttribute CurrUser currUser) throws IOException {
		try {

			if(StringUtils.isEmpty(gascard.getOrderby())){
				gascard.setOrderby("order_date desc");
			}

			PageInfo<GasCard> pageinfo = service.queryGasCard(gascard);
			List<GasCard> list = pageinfo.getList();

			int cells = 0 ; // 记录条数

			if(list != null && list.size() > 0){
				cells += list.size();
			}
			OutputStream os = response.getOutputStream();
			ExportUtil reportExcel = new ExportUtil();

			String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(), DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
			downLoadFileName = "用户卡信息_" + downLoadFileName;

			try {
				response.addHeader("Content-Disposition","attachment;filename="+ new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
			}

			String[][] content = new String[cells+1][9];//[行数][列数]
			//第一列
			content[0] = new String[]{"用户卡号","用户卡类型","用户卡状态","用户卡属性","所属工作站","工作站领取人","操作员","入库批次号","入库时间","出库时间"};

			int i = 1;
			if(list != null && list.size() > 0){
				for (GasCard tmpMap:pageinfo.getList()) {

					String card_no = tmpMap.getCard_no()==null?"":tmpMap.getCard_no().toString();

					String card_type = tmpMap.getCard_type()==null?"":tmpMap.getCard_type_info().getMname().toString();
					String card_status = tmpMap.getCard_status()==null?"":tmpMap.getCardStatusInfo().getMname().toString();
					String card_property = tmpMap.getCard_property()==null?"":tmpMap.getCard_property_info().getMname().toString();
					String workstation="";
					if(tmpMap.getTransportion()!=null){
						workstation =	tmpMap.getTransportion().getTransportion_name();
					}
					if(tmpMap.getGasStationInfo()!=null){
						workstation =	tmpMap.getGasStationInfo().getStation_manager();
					}
				//	String workstation = tmpMap.getWorkstation()==null?"":tmpMap.getTransportion().getTransportion_name();

					String workstation_resp = tmpMap.getWorkstation_resp()==null?"":tmpMap.getWorkstation_resp().toString();
					String operator = tmpMap.getOperator()==null?"":tmpMap.getOperator();
					String batch_no = tmpMap.getBatch_no()==null?"":  tmpMap.getBatch_no();
					String storage_time = tmpMap.getStorage_time()==null?"":DateUtil.format(tmpMap.getStorage_time());
					String release_time = tmpMap.getRelease_time()==null?"":DateUtil.format(tmpMap.getRelease_time());

					content[i] = new String[]{card_no,
							card_type,card_status,card_property,workstation,workstation_resp,operator,batch_no,storage_time,release_time};
					i++;
				}
			}

			String [] mergeinfo = new String []{"0,0,0,0"};
			//单元格默认宽度
			String sheetName = "用户卡信息报表";
			reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, null, 22, null, 0, null, null, false);

		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}


}
