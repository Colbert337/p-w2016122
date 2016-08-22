package com.sysongy.poms.message.dao;

import com.sysongy.poms.message.model.SysMessage;

public interface SysMessageMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysMessage record);

    int insertSelective(SysMessage record);

    SysMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysMessage record);

    int updateByPrimaryKey(SysMessage record);
}