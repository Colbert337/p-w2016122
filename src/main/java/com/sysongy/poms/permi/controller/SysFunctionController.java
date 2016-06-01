package com.sysongy.poms.permi.controller;

import com.sysongy.poms.base.controller.BaseContoller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @FileName: SysFunctionController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.permi.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月27日, 9:51
 * @Author: Dongqiang.Wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */

@RequestMapping("/web/permi/function")
@Controller
public class SysFunctionController extends BaseContoller{

	/**
	 * 查询功能列表
	 * @return
	 */
	@RequestMapping("/list/page")
	public String queryRoleListPage(ModelMap map){
	    map.addAttribute("current_module", "webpage/doc/boss_doc_list");
	    return "webpage/poms/permi/function_list";
	}

}
