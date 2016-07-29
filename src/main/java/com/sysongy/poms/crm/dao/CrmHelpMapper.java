package com.sysongy.poms.crm.dao;

import java.util.List;
import com.sysongy.poms.crm.model.CrmHelp;


public interface CrmHelpMapper {
    int deleteByPrimaryKey(String crmHelpId);

    int insert(CrmHelp record);

    int insertSelective(CrmHelp record);

    CrmHelp selectByPrimaryKey(String crmHelpId);

    int updateByPrimaryKeySelective(CrmHelp record);

    int updateByPrimaryKey(CrmHelp record);
    
    public List<CrmHelp> queryCrmHelp(CrmHelp record);//查询列表
    
    public int deleteCrmHelp(String crmHelpId);//删除
    
    public int updateCrmHelp(CrmHelp record);//更新
    
    public CrmHelp queryCrmHelpValue(String crmHelpId);//回显信息查询
    
    public int add(CrmHelp record);//添加
    
    public List<CrmHelp> queryForPage(CrmHelp record);//分页
    
    public List<CrmHelp> queryQuestiontypeList(CrmHelp record);
    
    //public List<CrmHelp> queryNotice(String record);//公告查询

    /**
     * 查询问题列表
     * @param obj
     * @return
     * @throws Exception
     */
    public List<CrmHelp> queryQuestionListByName(CrmHelp obj) throws Exception;


}