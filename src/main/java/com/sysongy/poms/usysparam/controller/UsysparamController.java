package com.sysongy.poms.usysparam.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sysongy.poms.base.model.CurrUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;


@RequestMapping("/web/usysparam")
@Controller
public class UsysparamController extends BaseContoller{

	@Autowired
	private UsysparamService service;

	@ResponseBody
	@RequestMapping("/query")
	public String query(HttpServletRequest request, ModelMap map, @RequestParam String gcode, @RequestParam(required = false) String scode) throws Exception{
		
		List<Usysparam> list = service.query(gcode, scode);
		String array = JSON.toJSONString(list);

		return array;
	}

	/**
	 * 查询参数信息
	 * @param currUser
	 * @return
	 * @throws Exception
     */
	@ResponseBody
	@RequestMapping("/info")
	public Usysparam queryParamList(@ModelAttribute CurrUser currUser,String mcode) throws Exception{
		String userType = currUser.getUserType()+"";
		if(mcode == null || mcode.equals("")){
			mcode = userType;
		}
		String gcode = "PLF_TYPE";
		Usysparam usysparam = service.queryUsysparamByCode(gcode,mcode);
		return usysparam;
	}
}
