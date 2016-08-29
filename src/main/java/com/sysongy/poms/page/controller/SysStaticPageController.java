package com.sysongy.poms.page.controller;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.page.model.SysStaticPage;
import com.sysongy.poms.page.service.SysStaticPageService;

@RequestMapping("/web/page")
@Controller
public class SysStaticPageController extends BaseContoller {
	
	@Autowired
	private SysStaticPageService service;
	
	private SysStaticPage page;
	
	
	@RequestMapping("/pageList")
	public String queryAllPageList(ModelMap map, SysStaticPage page) throws Exception{
		
		this.page = page;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/page/page_list";

		try {
			if(page.getPageNum() == null){
				page.setPageNum(1);
				page.setPageSize(10);
			}
			if(StringUtils.isEmpty(page.getOrderby())){
				page.setOrderby("page_created_time desc");
			}

			PageInfo<SysStaticPage> pageinfo = service.queryForPage(page);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("page",page);
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
	
	@RequestMapping("/savePage")
	public String savePage(ModelMap map, SysStaticPage page) throws Exception{
		
		PageBean bean = new PageBean();
		
		String ret = "webpage/poms/page/page_new";
		String pageid = null;

		try {
			if(StringUtils.isEmpty(page.getId())){
				pageid = service.savePage(page,"insert");
				bean.setRetMsg("新增成功");
				ret = "webpage/poms/page/page_new";
			}

			bean.setRetCode(100);
			
			bean.setRetValue(pageid);
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			map.addAttribute("message", pageid);
			
			logger.error("", e);
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/showPage")
	public String showPage(ModelMap map, @RequestParam String pageid) throws Exception{
		
		PageBean bean = new PageBean();
		
		String ret = "webpage/poms/page/page";

		try {
			SysStaticPage page = service.queryPageByPK(pageid);

			bean.setRetCode(100);
			
			bean.setRetValue(pageid);
			bean.setPageInfo(ret);

			map.addAttribute("page", page);
			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			map.addAttribute("message", pageid);
			
			logger.error("", e);
		}
		finally {
			return ret;
		}
	}
	
	@RequestMapping("/preview")
	public String preview(ModelMap map, SysStaticPage page) throws Exception{
		
		PageBean bean = new PageBean();
		
		String ret = "webpage/poms/page/page";

		try {
			//SysStaticPage page = service.queryPageByPK(pageid);
			//page.setPageHtml(pageHtml);

			bean.setRetCode(100);
			
			//bean.setRetValue(pageid);
			page.setPageCreatedTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(page.getPage_created_time()));
			bean.setPageInfo(ret);

			map.addAttribute("page", page);
			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			//map.addAttribute("message", pageid);
			
			logger.error("", e);
		}
		finally {
			return ret;
		}
	}
	
}
