package com.sysongy.crm.help.dao;

import com.sysongy.crm.help.model.CrmHelpType;

public interface CrmHelpTypeMapper {
    int deleteByPrimaryKey(String crmHelpTypeId);

    int insert(CrmHelpType record);

    int insertSelective(CrmHelpType record);

    CrmHelpType selectByPrimaryKey(String crmHelpTypeId);

    int updateByPrimaryKeySelective(CrmHelpType record);

    int updateByPrimaryKey(CrmHelpType record);
}