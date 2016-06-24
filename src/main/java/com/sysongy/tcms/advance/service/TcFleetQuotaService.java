package com.sysongy.tcms.advance.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.model.TcFleetQuota;

import java.util.Map;

/**
 * @FileName: TcFleetQuotaService
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月22日, 12:10
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface TcFleetQuotaService {

    /**
     * 查询车队额度信息
     * @param tcFleetQuota
     * @return
     */
    TcFleetQuota queryFleetQuota(TcFleetQuota tcFleetQuota);

    /**
     * 查询车队额度信息列表
     * @param tcFleetQuota
     * @return
     */
    PageInfo<TcFleetQuota> queryFleetQuotaList(TcFleetQuota tcFleetQuota);

    /**
     * 查询车队额度信息列表
     * @param tcFleetQuota
     * @return
     */
    PageInfo<Map<String,Object>> queryFleetQuotaMapList(TcFleetQuota tcFleetQuota);

    /**
     * 添加车队额度
     * @param tcFleetQuota
     * @return
     */
    int addFleetQuota(TcFleetQuota tcFleetQuota);

    /**
     * 删除车队额度
     * @param tcFleetQuota
     * @return
     */
    int deleteFleetQuota(TcFleetQuota tcFleetQuota);

    /**
     * 修改车队额度信息
     * @param tcFleetQuota
     * @return
     */
    int updateFleetQuota(TcFleetQuota tcFleetQuota);

}
