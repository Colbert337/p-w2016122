package com.sysongy.crm.help.dao;

import com.sysongy.crm.help.model.CrmHelp;

public interface CrmHelpMapper {
    int deleteByPrimaryKey(String crmHelpId);

    int insert(CrmHelp record);

    int insertSelective(CrmHelp record);

    CrmHelp selectByPrimaryKey(String crmHelpId);

    int updateByPrimaryKeySelective(CrmHelp record);

    int updateByPrimaryKey(CrmHelp record);
}