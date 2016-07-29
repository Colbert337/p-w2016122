package com.sysongy.poms.crm.dao;

import java.util.List;

import com.sysongy.poms.crm.model.CrmHelp;
import com.sysongy.poms.crm.model.CrmHelpType;

public interface CrmHelpTypeMapper {
    int deleteByPrimaryKey(String crmHelpTypeId);

    int insert(CrmHelpType record);

    int insertSelective(CrmHelpType record);

    CrmHelpType selectByPrimaryKey(String crmHelpTypeId);

    int updateByPrimaryKeySelective(CrmHelpType record);

    int updateByPrimaryKey(CrmHelpType record);
    
    public List<CrmHelpType> queryForPageList(CrmHelpType record);//分页查询
    
    public int add(CrmHelpType record);//添加

    /**
     * 查询问题分类列表
     * @return
     * @throws Exception
     */
    List<CrmHelpType> queryCrmHelpTypeList() throws Exception;
}