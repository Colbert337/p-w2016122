package com.sysongy.poms.page.dao;

import java.util.List;

import com.sysongy.poms.page.model.SysStaticPage;

public interface SysStaticPageMapper {
	
	List<SysStaticPage> queryForPage(SysStaticPage record);
	
    int deleteByPrimaryKey(String id);

    int insert(SysStaticPage record);

    int insertSelective(SysStaticPage record);

    SysStaticPage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysStaticPage record);

    int updateByPrimaryKeyWithBLOBs(SysStaticPage record);

    int updateByPrimaryKey(SysStaticPage record);

	Integer delForStatus(String id);
}