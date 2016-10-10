package com.sysongy.poms.mobile.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.mobile.model.Suggest;
import com.sysongy.poms.mobile.service.SuggestService;
import com.sysongy.util.GlobalConstant;

/**
 * @FileName: MbBannerController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.mobile.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月01日, 14:40
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@RequestMapping("/web/mobile/suggest")
@Controller
public class SuggestController extends BaseContoller {
	@Autowired
	SuggestService suggestService;

	/**
	 * 查询图片列表
	 * 
	 * @return
	 */
	@RequestMapping("/suggestList")
	public String queryMbBannerListPage(Suggest suggest, HttpServletRequest request, ModelMap map) throws Exception {

		String ret = "webpage/poms/mobile/suggest_list";

		PageBean bean = new PageBean();
		try {
			if (suggest.getPageNum() == null) {
				suggest.setPageNum(GlobalConstant.PAGE_NUM);
				suggest.setPageSize(GlobalConstant.PAGE_SIZE);
			}
			if (StringUtils.isEmpty(suggest.getOrderby())) {
				suggest.setOrderby("created_date desc");
			}
			PageInfo<Suggest> pageinfo = new PageInfo<Suggest>();

			pageinfo = suggestService.querySuggest(suggest);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("suggest", suggest);
			map.addAttribute("current_module", "/web/mobile/suggest/suggestList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}

	}
 
}
