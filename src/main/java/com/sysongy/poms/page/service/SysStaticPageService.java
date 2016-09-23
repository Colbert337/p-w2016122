package com.sysongy.poms.page.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.page.model.SysStaticPage;

public interface SysStaticPageService {
	
	public PageInfo<SysStaticPage> queryForPage(SysStaticPage obj) throws Exception;
	
	public SysStaticPage queryPageByPK(String pageid) throws Exception;
	
	public String savePage(SysStaticPage obj, String operation) throws Exception;
	
	public Integer delMessage(String pageid) throws Exception;

	public Integer delForStatus(SysStaticPage page);
}
