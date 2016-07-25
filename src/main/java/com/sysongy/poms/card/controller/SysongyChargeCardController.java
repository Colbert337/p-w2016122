package com.sysongy.poms.card.controller;


import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sysongy.poms.card.model.SysongyChargeCard;
import com.sysongy.poms.card.service.SysongyChargeCardService;





@RequestMapping("/web/chargeCard")
@Controller
public class SysongyChargeCardController {
	
	@Autowired
	private SysongyChargeCardService sysongyChargeCardService;	 
	
	private SysongyChargeCard sysongyChargeCard;
	
    /**
     * 遍历数据    
     * @param model
     * @param sysongyChargeCard
     * @return
     * @throws Exception
     */
	@RequestMapping("/chargeCardList")
	public String queryViewList(Model model, SysongyChargeCard sysongyChargeCard) throws Exception{
			List<SysongyChargeCard> list = sysongyChargeCardService.getSysongyChargeCardList(sysongyChargeCard);
			model.addAttribute("list",list);
			model.addAttribute("sysongyChargeCard",sysongyChargeCard);
		return "webpage/poms/card/card_view_list";
		}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String deleteChargeCardId(@RequestParam String id) throws Exception{
		sysongyChargeCardService.delete(id);
		return "redirect:/web/chargeCard/chargeCardList";	
	}
	
	/**
	 * 编辑修改信息回显
	 * @param model
	 * @param ownidvalue
	 * @param sysongyChargeCard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	public String updateChargeCard(Model model,@RequestParam String ownidvalue,SysongyChargeCard sysongyChargeCard) throws Exception{
		SysongyChargeCard sysongyChargeCard1 =  sysongyChargeCardService.querySysongyChargeCard(ownidvalue);
		model.addAttribute("sysongyChargeCard",sysongyChargeCard1);
		return "webpage/poms/card/charge_update";		
	}
	
	/**
	 * 修改
	 * @param model
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveupdate")
	public String saveUpdateSysongyChargeCard(Model model,SysongyChargeCard obj) throws Exception{
		sysongyChargeCardService.updateSysongyChargeCard(obj);
		return "redirect:/web/chargeCard/chargeCardList";		
	}
	
	/**
	 * 保存
	 * @param model
	 * @param obj
	 * @param sysongyChargeCard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String saveSysongyChargeCard(Model model,SysongyChargeCard obj,SysongyChargeCard sysongyChargeCard) throws Exception{
		      //sysongyChargeCardService.save(obj);
		      sysongyChargeCardService.add(obj);
		return "redirect:/web/chargeCard/chargeCardList";
	}
	
	/**
	 * 查询
	 * @param model
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/query")
	public String queryLike(Model model,SysongyChargeCard obj) throws Exception{
		 model.addAttribute("list",sysongyChargeCardService.selectSysongyChargeCard(obj));
		 return "webpage/poms/card/card_view_list";
    }
	
	/**
	 * 判断用户卡号是否存在
	 * @param id
	 * @param ownid
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/isExist")
	@ResponseBody
	public JSONObject queryUserByNameAndType(@RequestParam String ownid) throws Exception{
		JSONObject json = new JSONObject();
		
		SysongyChargeCard sysongyCard = new SysongyChargeCard();
		sysongyCard.setOwnid(ownid);
		SysongyChargeCard sysongy = sysongyChargeCardService.queryCard(sysongyCard);
	
		if(sysongy == null){
			json.put("valid",true);
		}else{
			json.put("valid",false); 
		}
		
		return json;
	}
}