package com.sysongy.poms.card.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


@RequestMapping("/web/card")
@Controller
public class CardController extends BaseContoller{

	@Autowired
	private GasCardService service;

	/**
	 * 表单查询
	 * @param map
	 * @param gascard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cardList")
	public String queryAllCardList(ModelMap map, GasCard gascard) throws Exception{
		
		if(gascard.getPageNum() == null){
			gascard.setPageNum(1);
			gascard.setPageSize(10);
		}
		
		PageInfo<GasCard> pageinfo = service.queryGasCard(gascard);
		map.addAttribute("pageinfo", pageinfo);
		map.addAttribute("gascard",gascard);
	    map.addAttribute("current_module", "page/card/card_list");
	    return "page/card/card_list";
	}

	@RequestMapping("/saveCard")
	public String saveCard(ModelMap map, GasCard gascard) throws Exception{
		
		Integer ret = service.saveGasCard(gascard);
		map.addAttribute("ret", ret);
		
		return  "page/card/new_card";
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("/deleteCard")
	public String deleteCard(HttpServletResponse response, ModelMap map, @RequestParam String cardid) throws Exception{
		
		PageBean bean = new PageBean();
		HashMap<String, String> b = new HashMap<String, String>();
		String ret = "page/card/card_list";
		Integer rowcount = null;
		
		try {
				if(cardid != null && !"".equals(cardid)){
					rowcount = service.delGasCard(cardid);
				}
				
				ret = this.queryAllCardList(map, new GasCard());
				
				bean.setRetCode(100);
				bean.setRetMsg("["+cardid+"]删除成功");
				bean.setRetValue(rowcount.toString());
				
				b.put("retCode", "100");
				b.put("retMsg", "["+cardid+"]删除成功");
				b.put("retValue", rowcount.toString());
				throw new Exception("我是故意的");
				
		} catch (Exception e) {
			response.setStatus(5000);
			response.getWriter().write(e.getMessage());
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			throw e;
		}
//		finally {
//			map.addAttribute("ret", b);
//			return ret;
//		}
	}
	
}
