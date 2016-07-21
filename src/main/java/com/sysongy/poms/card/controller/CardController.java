package com.sysongy.poms.card.controller;

import com.sysongy.util.GlobalConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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


@RequestMapping("/web/card")
@Controller
public class CardController extends BaseContoller{

	@Autowired
	private GasCardService service;

	/**
	 * 用户卡查询
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cardList")
	public String queryAllCardList(ModelMap map, GasCard gascard) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/card/card_list";

		try {
			if(StringUtils.isEmpty(gascard.getOrderby())){
				gascard.setOrderby("card_no desc");
			}
			
			PageInfo<GasCard> pageinfo = service.queryGasCard(gascard);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gascard",gascard);
			map.addAttribute("current_module", "webpage/poms/card/card_list");
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
	@RequestMapping("/saveCard")
	public String saveCard(ModelMap map, GasCard gascard) throws Exception{
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
		}
		finally {
			return  ret;
		}
	}
	
	@RequestMapping("/saveCardMultiple")
	public String saveCardMultiple(ModelMap map, GasCard gascard) throws Exception{
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
		}
		finally {
			return  ret;
		}
	}

	/**
	 * 用户卡删除
	 * @param map
	 * @param cardid
	 * @return
	 */
	@RequestMapping("/deleteCard")
	public String deleteCard(GasCard gascard, ModelMap map, @RequestParam String cardid, @ModelAttribute("currUser") CurrUser currUser){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/card/card_list";
		Integer rowcount = null;

		try {
				if(cardid != null && !"".equals(cardid)){
					rowcount = service.deleteGasCard(cardid, currUser);
				}

				ret = this.queryAllCardList(map, gascard);

				bean.setRetCode(100);
				bean.setRetMsg("["+cardid+"]删除成功");
				bean.setRetValue(rowcount.toString());
				map.addAttribute("gascard",gascard);
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllCardList(map, gascard);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}

	
	@ResponseBody
	@RequestMapping("/checkCardMultiple")
	public JSONArray checkCardMultiple(ModelMap map, @RequestParam String cardidStart, @RequestParam String cardidEnd) throws Exception{
		
		Integer start = Integer.valueOf(cardidStart);
		Integer end = Integer.valueOf(cardidEnd);
		JSONArray arr = new JSONArray();
		
		for(int i=start;i<=end;i++){
			Boolean exist = service.checkCardExist(String.valueOf(i));
			GasCardReturn tmp = new GasCardReturn();
			tmp.setExist(exist?"1":"0");
			tmp.setCard_no(String.valueOf(i));
			arr.add(tmp);
		}
		
		return arr;
	}
	
	/**
	 * 检查用户卡是否存在于资源库
	 * @param map
	 * @param cardid
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/checkCard")
	public String checkCard(ModelMap map, @RequestParam String cardid) throws Exception{

		Boolean exist = service.checkCardExist(cardid);

		return  exist?"1":"0";
	}
	
	@ResponseBody
	@RequestMapping("/getCard")
	public JSONObject checkCard(@RequestParam String cardno) throws Exception{

		GasCard gasCard = service.selectByCardNoForCRM(cardno);
		JSONObject json = new JSONObject();
		
		if(gasCard != null){
			json.put("status", gasCard.getCard_status());
			json.put("station", gasCard.getWorkstation());
			json.put("card_property", gasCard.getCard_property());
		}	
		
		return json;
	}

	@ResponseBody
	@RequestMapping("/checkMoveCardMultiple")
	public JSONArray checkMoveCardMultiple(ModelMap map, @RequestParam String cardidStart, @RequestParam String cardidEnd) throws Exception{

		Integer start = Integer.valueOf(cardidStart);
		Integer end = Integer.valueOf(cardidEnd);
		JSONArray arr = new JSONArray();
		
		for(int i=start;i<=end;i++){
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
	 * @param map
	 * @param cardid
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/checkMoveCard")
	public String checkMoveCard(ModelMap map, @RequestParam String cardid) throws Exception{

		return service.checkMoveCard(cardid);
	}

	/**
	 * 用户卡出库
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/moveCard")
	public String updateAndMoveCard(ModelMap map, GasCard gascard) throws Exception{

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
		}
		finally {
			return  ret;
		}
	}
	
	@ResponseBody
	@RequestMapping("/updateCardStatus")
	public String updateCardStatus(ModelMap map, @RequestParam String cardno, @RequestParam String cardstatus,@RequestParam(required=false) String memo) throws Exception{

		GasCard gascard = new GasCard();
		
		gascard.setCard_no(cardno);
		gascard.setCard_status(cardstatus);
		gascard.setMemo(memo);
		
		return service.updateGasCardInfo(gascard).toString();
	}

	@RequestMapping("/update/freeze")
	@ResponseBody
	public String updateCardFreeze(ModelMap map, GasCard gascard) throws Exception{
		String result = "succ";
		gascard.setCard_status(GlobalConstant.CardStatus.PAUSED);
		service.updateGasCardInfo(gascard);
		return result;
	}
	/**
	 * 用户卡轨迹查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cardLogList")
	public String queryAllCardLogList(ModelMap map, GasCardLog gascardlog) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/card/cardlog_list";

		try {
			if(StringUtils.isEmpty(gascardlog.getOrderby())){
				gascardlog.setOrderby("optime desc");
			}

			PageInfo<GasCardLog> pageinfo = service.queryGasCardLog(gascardlog);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gascardlog",gascardlog);
			map.addAttribute("current_module", "webpage/poms/card/cardlog_list");
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
