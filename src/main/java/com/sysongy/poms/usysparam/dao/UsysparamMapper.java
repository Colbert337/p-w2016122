package com.sysongy.poms.usysparam.dao;

import java.util.List;

import com.sysongy.poms.usysparam.model.Usysparam;

public interface UsysparamMapper {
	public List<Usysparam> selectDefault(Usysparam usysparam);
    /**
     * 根据gcode查询参数对象
     * @param gcode
     * @return
     */
    public Usysparam queryUsysparamByCode(String gcode,String mcode);
    
    public int insert(Usysparam recode);
    
    public int updateByPrimaryKey(Usysparam recode);
    
    public int deleteByPrimaryKey(Usysparam recode);

    public List<Usysparam> queryUsysparamByGcode(String gcode);

    public Usysparam queryCardTypeByMcode(String mcode);

    public Usysparam queryCardStatusByMcode(String mcode);
}