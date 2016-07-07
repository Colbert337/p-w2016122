package com.sysongy.poms.usysparam.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.transportion.model.Transportion;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;


@RequestMapping("/web/usysparam")
@Controller
public class UsysparamController extends BaseContoller{

	@Autowired
	private UsysparamService service;
	
	private Usysparam usysparam;

	@ResponseBody
	@RequestMapping("/query")
	public String query(HttpServletRequest request, ModelMap map, @RequestParam String gcode, @RequestParam(required = false) String scode) throws Exception{
		
		List<Usysparam> list = service.query(gcode, scode);
		String array = JSON.toJSONString(list);

		return array;
	}
	
	@RequestMapping("/queryAll")
	public String queryAll(ModelMap map, Usysparam usysparam) throws Exception{

		this.usysparam = usysparam;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/usysparam/usysparam";

		try {
			usysparam.setOrderby("gcode desc");

			List<Usysparam> list = service.queryAll(usysparam);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("list", list);
			map.addAttribute("usysparam",usysparam);
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
	
	@RequestMapping("/saveUsysparam")
	public String saveUsysparam(ModelMap map, Usysparam usysparam, @RequestParam String operation) throws Exception{
		PageBean bean = new PageBean();
		String ret = "webpage/poms/usysparam/usysparam";
		String transportionid = null;

		try {
			if(StringUtils.isEmpty(operation)){
				transportionid = service.saveUsysparam(usysparam,"insert");
				bean.setRetMsg("新增成功");
			}else{
				transportionid = service.saveUsysparam(usysparam,"update");
				bean.setRetMsg("保存成功");
			}
			
			ret = this.queryAll(map, this.usysparam);

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
			return ret;
		}
	}
	
	@RequestMapping("/deleteUsysparam")
	public String deleteUsysparam(ModelMap map, @RequestParam String gcodevalue, @RequestParam String mcodevalue) throws Exception{
		PageBean bean = new PageBean();
		
		Usysparam usysparam = new Usysparam();
		usysparam.setGcode(gcodevalue);
		usysparam.setMcode(mcodevalue);
		
		String ret = "webpage/poms/usysparam/usysparam";

		try {
			service.deleteByGcodeAndMcode(usysparam);
			bean.setRetMsg("删除成功");
			
			ret = this.queryAll(map, this.usysparam);

			bean.setRetCode(100);
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
			return ret;
		}
	}
	
	@RequestMapping("/preUpdate")
	public String preUpdate(ModelMap map, @RequestParam String gcodevalue, @RequestParam String mcodevalue){
		PageBean bean = new PageBean();
		String ret = "webpage/poms/usysparam/usysparam_update";
		Usysparam usysparam = new Usysparam();

			try {
				usysparam = service.queryUsysparamByCode(gcodevalue, mcodevalue);
				
				bean.setRetCode(100);
				bean.setRetMsg("查询成功");
				bean.setPageInfo(ret);

				map.addAttribute("ret", bean);
				map.addAttribute("usysparam", usysparam);

		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			ret = this.queryAll(map, this.usysparam);

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
}
