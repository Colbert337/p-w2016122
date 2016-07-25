package com.sysongy.poms.crm.dao;

import com.sysongy.poms.crm.model.CrmHelp;

public interface CrmHelpMapper {
    int deleteByPrimaryKey(String crmHelpId);

    int insert(CrmHelp record);

    int insertSelective(CrmHelp record);

    CrmHelp selectByPrimaryKey(String crmHelpId);

    int updateByPrimaryKeySelective(CrmHelp record);

    int updateByPrimaryKey(CrmHelp record);
}