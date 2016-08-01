package com.sysongy.api.mobile.dao;

import com.sysongy.api.mobile.model.user.MbUserSuggest;

public interface MbUserSuggestMapper {
    int deleteByPrimaryKey(String mbUserSuggestId);

    int insert(MbUserSuggest record);

    int insertSelective(MbUserSuggest record);

    MbUserSuggest selectByPrimaryKey(String mbUserSuggestId);

    int updateByPrimaryKeySelective(MbUserSuggest record);

    int updateByPrimaryKey(MbUserSuggest record);
}