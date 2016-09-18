package com.sysongy.poms.message.dao;

import java.util.List;

import com.sysongy.poms.message.model.SysMessage;

public interface SysMessageMapper {
	
	List<SysMessage> queryForPage(SysMessage record);
	
    int deleteByPrimaryKey(String id);

    int insert(SysMessage record);

    int insertSelective(SysMessage record);

    SysMessage selectByPrimaryKey(SysMessage message);

    int updateByPrimaryKeySelective(SysMessage record);

    int updateByPrimaryKey(SysMessage record);
}