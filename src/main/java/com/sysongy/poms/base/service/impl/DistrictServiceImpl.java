package com.sysongy.poms.base.service.impl;

import com.sysongy.poms.base.dao.DistCityMapper;
import com.sysongy.poms.base.model.DistCity;
import com.sysongy.poms.base.service.DistrictService;
import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName: DistrictServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.base.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月18日, 12:07
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:  地区信息管理
 */
@Service
public class DistrictServiceImpl implements DistrictService{
    @Autowired
    DistCityMapper distCityMapper;

    @Override
    public List<DistCity> queryHotCityList() {
        return distCityMapper.queryHotCityList(GlobalConstant.HotCity.HOT);
    }
}
