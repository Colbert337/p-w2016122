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
    PageInfo<MbBanner> queryMbBannerListPage(MbBanner mbBanner) throws Exception;

    /**
     * 保存图片
     * @param banner
     * @return
     */
    int saveBanner(MbBanner banner) throws Exception;

    /**
     * 修改图片
     * @param banner
     * @return
     */
    int updateBanner(MbBanner banner) throws Exception;
    /**
     * 删除图片
     * @param banner
     * @return
     */
    int deleteBanner(MbBanner banner) throws Exception;

    /**
     * 查询当前类型图片的最大序号
     * @param imgType
     * @return
     */
    MbBanner queryMaxIndex(int imgType);

    /**
     * 查询当前图片信息
     * @param banner
     * @return
     */
    MbBanner queryMbBanner(MbBanner banner);
}
