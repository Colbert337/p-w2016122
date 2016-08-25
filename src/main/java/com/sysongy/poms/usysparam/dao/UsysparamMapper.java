package com.sysongy.poms.usysparam.dao;

import java.util.List;
import java.util.Map;

import com.sysongy.poms.usysparam.model.Usysparam;

public interface UsysparamMapper {
	public List<Usysparam> selectDefault(Usysparam usysparam);
	
	public List<Usysparam> selectAll(Usysparam usysparam);
    /**
     * 根据gcode查询参数对象
     * @param gcode
     * @return
     */
    public Usysparam queryUsysparamByCode(String gcode,String mcode);
    
    public int insert(Usysparam recode);
    
    public int updateByPrimaryKey(Usysparam recode);
    
    public int delete(Usysparam recode);

    public List<Usysparam> queryUsysparamByGcode(String gcode);

    public List<Map<String, Object>> queryUsysparamMapByGcode(String gcode);

    public List<Usysparam> queryUsysparamChildByGcode(String gcode);

    public Usysparam queryCardTypeByMcode(String mcode);

    public Usysparam queryCardStatusByMcode(String mcode);

    public Usysparam queryCheckStatusByMcode(String mcode);

    public Usysparam queryFuelTypeByMcode(String mcode);

    public List<Usysparam> queryByMcode(String mcode);

    public List<Usysparam> queryProductPriceTypeByMcode(String mcode);

    public Usysparam queryGasUnitTypeByMcode(String mcode);

    public Usysparam queryProductStatusByMcode(String mcode);

    public Usysparam queryAccountStatus(String mcode);

    public Usysparam queryCashBackByMcode(String mcode);

    public Usysparam queryOrderTypeByMcode(String mcode);

    public Usysparam queryOrderPropertyByMcode(String mcode);

    public Usysparam queryDealTypeByMcode(String mcode);

    public Usysparam queryChargeTypeByMcode(String mcode);

    public List<Usysparam> queryCardTypeByMcodeAndScode(Usysparam record);
}