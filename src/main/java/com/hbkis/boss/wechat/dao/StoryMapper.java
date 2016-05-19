package com.hbkis.boss.wechat.dao;

import com.hbkis.boss.wechat.model.Story;

public interface StoryMapper {
    int deleteByPrimaryKey(String wxStoryId);

    int insert(Story record);

    int insertSelective(Story record);

    Story selectByPrimaryKey(String wxStoryId);

    int updateByPrimaryKeySelective(Story record);

    int updateByPrimaryKey(Story record);
}