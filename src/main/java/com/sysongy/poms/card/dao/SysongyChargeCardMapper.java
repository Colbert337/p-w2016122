package com.sysongy.poms.card.dao;

import java.util.List;

import com.sysongy.poms.card.model.GasCardLog;
import com.sysongy.poms.card.model.SysongyChargeCard;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.usysparam.model.Usysparam;

public interface SysongyChargeCardMapper {
	
	public SysongyChargeCard querySysongyChargeCardvalue(String ownid);
	
    int deleteByPrimaryKey(String id);

    int insert(SysongyChargeCard record);

    int insertSelective(SysongyChargeCard record);

    SysongyChargeCard selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysongyChargeCard record);

    int updateByPrimaryKey(SysongyChargeCard record);
    
    int deleteSysongyChargeCard(String id);
    
    int updateSysongyChargeCard(SysongyChargeCard record);
    
    int addSysongyChargeCard(SysongyChargeCard record);
    
    List<SysongyChargeCard> querySysongyChargeCard(SysongyChargeCard record);
    
    public List<SysongyChargeCard> selectSysongyChargeCardLike(SysongyChargeCard record);
    
    public SysongyChargeCard queryisExist(SysongyChargeCard record);
    
    
}