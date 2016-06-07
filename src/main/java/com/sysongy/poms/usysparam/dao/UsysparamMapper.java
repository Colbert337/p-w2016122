package com.sysongy.poms.usysparam.dao;

import java.util.List;

import com.sysongy.poms.usysparam.model.Usysparam;

public interface UsysparamMapper {
    List<Usysparam> selectDefault(Usysparam usysparam);
}