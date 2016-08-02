package com.sysongy.poms.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.dao.MbBannerMapper;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.service.MbBannerService;
import com.sysongy.tcms.advance.model.TcFleet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @FileName: MbBannerServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.mobile.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月01日, 14:41
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class MbBannerServiceImpl implements MbBannerService{
    @Autowired
    MbBannerMapper mbBannerMapper;

    /**
     * 查询APP首页图片列表
     * @param mbBanner
     * @return
     */
    @Override
    public PageInfo<MbBanner> queryMbBannerListPage(MbBanner mbBanner) {

        if(mbBanner != null){
            PageHelper.startPage(mbBanner.getPageNum(),mbBanner.getPageSize(),mbBanner.getOrderby());
            List<MbBanner> mbBannerList = mbBannerMapper.queryMbBannerList(mbBanner);
            PageInfo<MbBanner> fleetPageInfo = new PageInfo<>(mbBannerList);
            return fleetPageInfo;
        }else{
            return null;
        }
    }
}
