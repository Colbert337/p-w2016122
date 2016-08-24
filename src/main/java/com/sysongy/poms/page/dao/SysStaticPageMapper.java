package com.sysongy.poms.page.dao;

import com.sysongy.poms.page.model.SysStaticPage;

public interface SysStaticPageMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysStaticPage record);

    int insertSelective(SysStaticPage record);

    SysStaticPage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysStaticPage record);

    int updateByPrimaryKeyWithBLOBs(SysStaticPage record);

    int updateByPrimaryKey(SysStaticPage record);
}