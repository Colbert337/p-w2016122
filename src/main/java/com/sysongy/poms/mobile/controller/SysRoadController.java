package com.sysongy.poms.mobile.controller;

<<<<<<< .merge_file_a09596
import org.springframework.beans.factory.annotation.Autowired;
=======
>>>>>>> .merge_file_a06228
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

<<<<<<< .merge_file_a09596
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.mobile.model.Suggest;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.service.SysRoadService;
import com.sysongy.util.GlobalConstant;
=======
import com.sysongy.poms.base.controller.BaseContoller;
>>>>>>> .merge_file_a06228

/**
 * @FileName: SysRoadController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.mobile.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年09月08日, 17:18
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */

@RequestMapping("/web/mobile/road")
@Controller
public class SysRoadController extends BaseContoller{
	@Autowired
	SysRoadService sysRoadService;
	
	@RequestMapping("/roadList")
	public  String roadList(SysRoadCondition road,ModelMap map){
		String ret = "webpage/poms/mobile/roadList";

		PageBean bean = new PageBean();
		try {
			if (road.getPageNum() == null) {
				road.setPageNum(GlobalConstant.PAGE_NUM);
				road.setPageSize(GlobalConstant.PAGE_SIZE);
			}

			PageInfo<SysRoadCondition> pageinfo = new PageInfo<SysRoadCondition>();

			pageinfo = sysRoadService.queryRoadList(road);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("suggest", road);
//			map.addAttribute("current_module", "/web/mobile/suggest/suggestList");
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
	@RequestMapping("/saveRoad")
	public String saveRoad(SysRoadCondition road,ModelMap map){
		String ret = "webpage/poms/mobile/roadList";
		PageBean bean = new PageBean();
		try {
			if (road.getPageNum() == null) {
				road.setPageNum(GlobalConstant.PAGE_NUM);
				road.setPageSize(GlobalConstant.PAGE_SIZE);
			}


			int a = sysRoadService.saveRoad(road);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);
			map.addAttribute("ret", bean);
			map.addAttribute("road", road);
			map.addAttribute("suggest", road);
//			map.addAttribute("current_module", "/web/mobile/suggest/suggestList");
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
