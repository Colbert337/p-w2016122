package com.sysongy.poms.suggest.dao;

import java.util.List;

import com.sysongy.api.client.controller.model.CRMCardUpdateInfo;
import com.sysongy.poms.suggest.model.Suggest;

public interface SuggestMapper {
	
    int deleteByPrimaryKey(String cardNo);

    int insert(Suggest record);

    int insertSelective(Suggest record);
    
    int insertBatch(List<Suggest> recordlist);

    Suggest selectByPrimaryKey(String cardNo);
    
    Suggest selectByCardNo(String cardNo);

    Suggest selectByCardNoForCRM(String cardNo);

    int updateByPrimaryKeySelective(Suggest record);

    int updateByPrimaryKey(Suggest record);
    
    List<Suggest> queryForPage(Suggest record);
    
    List<Suggest> queryForList(Suggest record);

    List<Suggest> queryCardFor2StatusInfo(Suggest record);

    List<Suggest> querySuggestForCRM(CRMCardUpdateInfo crmCardUpdateInfo);

    int updateCardStatus(CRMCardUpdateInfo crmCardUpdateInfo);

    List<Suggest> queryForPageUpdate(CRMCardUpdateInfo record);
}