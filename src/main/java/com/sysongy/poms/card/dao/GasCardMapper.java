package com.sysongy.poms.card.dao;

import java.util.List;

import com.sysongy.api.client.controller.model.CRMCardUpdateInfo;
import com.sysongy.poms.card.model.GasCard;

public interface GasCardMapper {
	
    int deleteByPrimaryKey(String cardNo);

    int insert(GasCard record);

    int insertSelective(GasCard record);
    
    int insertBatch(List<GasCard> recordlist);

    GasCard selectByPrimaryKey(String cardNo);
    
    GasCard selectByCardNo(String cardNo);

    int updateByPrimaryKeySelective(GasCard record);

    int updateByPrimaryKey(GasCard record);
    
    List<GasCard> queryForPage(GasCard record);

    int updateCardStatus(CRMCardUpdateInfo crmCardUpdateInfo);

    List<GasCard> queryForPageUpdate(CRMCardUpdateInfo record);
}