package com.sysongy.poms.page.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.dao.MbBannerMapper;
import com.sysongy.poms.page.dao.SysStaticPageMapper;
import com.sysongy.poms.page.model.SysStaticPage;
import com.sysongy.poms.page.service.SysStaticPageService;
import com.sysongy.util.UUIDGenerator;

@Service
public class SysStaticPageServiceImpl implements SysStaticPageService {
	
	@Autowired
	private SysStaticPageMapper pageMapper;
	@Autowired
	private MbBannerMapper bannerMapper;

	@Override
	public PageInfo<SysStaticPage> queryForPage(SysStaticPage record) throws Exception {
		
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<SysStaticPage> list = pageMapper.queryForPage(record);
		PageInfo<SysStaticPage> pageInfo = new PageInfo<SysStaticPage>(list);
		
		return pageInfo;
	}

	@Override
	public SysStaticPage queryPageByPK(String pageid) throws Exception {
		return pageMapper.selectByPrimaryKey(pageid);
	}

	@Override
	public String savePage(SysStaticPage obj, String operation) throws Exception {
		obj.setId(UUIDGenerator.getUUID());
		obj.setPageStatus("0");
		obj.setPageCreatedTime(new Date());
		return String.valueOf(pageMapper.insert(obj));
	}

	@Override
	public Integer delMessage(String pageid) throws Exception {
		return pageMapper.deleteByPrimaryKey(pageid);
	}

	@Override
	public Integer delForStatus(SysStaticPage page)throws Exception {
		// TODO Auto-generated method stub
		bannerMapper.deleteByPrimaryPageKey(page.getId());
		return pageMapper.delForStatus(page.getId());
	}

	@Override
	public Integer update(SysStaticPage page)throws Exception {
		// TODO Auto-generated method stub
		return pageMapper.updateByPrimaryKey(page);
	}

}
