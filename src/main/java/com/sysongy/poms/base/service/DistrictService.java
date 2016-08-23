package com.sysongy.poms.base.service;

import com.sysongy.poms.base.model.DistCity;

import java.util.List;

/**
 * @FileName: DistrictService
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.base.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月18日, 12:06
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description: 地区信息管理
 */
public interface DistrictService {

    /**
     * 查询热门城市列表
     * @return
     */
    List<DistCity> queryHotCityList();

    /**
     * 查询城市信息
     * @param city 城市信息
     * @return
     */
    DistCity queryCityInfo(DistCity city);
}
