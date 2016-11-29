package com.sysongy.poms.system.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.system.model.SysOperationLog;
import com.sysongy.poms.system.service.SysOperationLogService;

@RequestMapping("/web/sysparam/operation")
@Controller
public class SysOperationLogController extends BaseContoller {
	
	@Autowired
	private SysOperationLogService service;
	
	@RequestMapping("/operationLogList")
	public String queryOperationLogList(ModelMap map, SysOperationLog sysOperationLog) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/system/systemOperation_LogList";

		try {
			if(sysOperationLog.getPageNum() == null){
				sysOperationLog.setPageNum(1);
				sysOperationLog.setPageSize(10);
			}
			if(sysOperationLog.getConvertPageNum() != null){
				if(sysOperationLog.getConvertPageNum() > sysOperationLog.getPageNumMax()){
					sysOperationLog.setPageNum(sysOperationLog.getPageNumMax());
				}else if(sysOperationLog.getConvertPageNum() < 1){
					sysOperationLog.setPageNum(1);
				}else{
					sysOperationLog.setPageNum(sysOperationLog.getConvertPageNum());
				}
			}
			if(StringUtils.isEmpty(sysOperationLog.getOrderby())){
				sysOperationLog.setOrderby("created_date desc");
			}
			
			PageInfo<SysOperationLog> pageinfo = service.queryOperationLog(sysOperationLog);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("sysOperationLog",sysOperationLog);
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
