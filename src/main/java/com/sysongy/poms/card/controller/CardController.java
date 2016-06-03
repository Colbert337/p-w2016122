package com.sysongy.poms.card.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.model.GasCardLog;
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
			if(gascard.getPageNum() == null){
				gascard.setPageNum(1);
				gascard.setPageSize(10);
			}

			if(!StringUtils.isEmpty(gascard.getStorage_time_range())){
				String []tmpRange = gascard.getStorage_time_range().split("-");
				if(tmpRange.length==2){
					gascard.setStorage_time_after(tmpRange[0].trim()+" 00:00:00");
					gascard.setStorage_time_before(tmpRange[1]+" 23:59:59");
				}
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

	/**
	 * 用户卡删除
	 * @param map
	 * @param cardid
	 * @return
	 */
	@RequestMapping("/deleteCard")
	public String deleteCard(ModelMap map, @RequestParam String cardid){

		PageBean bean = new PageBean();
		String ret = "webpage/poms/card/card_list";
		Integer rowcount = null;

		try {
				if(cardid != null && !"".equals(cardid)){
					rowcount = service.delGasCard(cardid);
				}

				ret = this.queryAllCardList(map, new GasCard());

				bean.setRetCode(100);
				bean.setRetMsg("["+cardid+"]删除成功");
				bean.setRetValue(rowcount.toString());
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);


		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAllCardList(map, new GasCard());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
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
			if(gascardlog.getPageNum() == null){
				gascardlog.setPageNum(1);
				gascardlog.setPageSize(10);
			}

			if(!StringUtils.isEmpty(gascardlog.getOptime_range())){
				String []tmpRange = gascardlog.getOptime_range().split("-");
				if(tmpRange.length==2){
					gascardlog.setOptime_after(tmpRange[0].trim()+" 00:00:00");
					gascardlog.setOptime_before(tmpRange[1]+" 23:59:59");
				}
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
