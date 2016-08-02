package com.sysongy.poms.mobile.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.model.MbBanner;

/**
 * @FileName: MbBannerService
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.mobile.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月01日, 14:40
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface MbBannerService {
    /**
     * 查询APP首页图片列表
     * @param mbBanner
     * @return
     */
    PageInfo<MbBanner> queryMbBannerListPage(MbBanner mbBanner);
}
