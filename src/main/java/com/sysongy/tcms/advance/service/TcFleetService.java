package com.sysongy.tcms.advance.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.model.TcFleet;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @FileName: TcFleetService
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月22日, 12:10
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface TcFleetService {

    /**
     * 查询车队信息
     * @param tcFleet
     * @return
     */
    TcFleet queryFleet(TcFleet tcFleet);

    /**
     * 查询车队信息列表
     * @param tcFleet
     * @return
     */
    PageInfo<TcFleet> queryFleetList(TcFleet tcFleet);

    /**
     * 查询车队信息列表
     * @param tcFleet
     * @return
     */
    PageInfo<Map<String,Object>> queryFleetMapList(TcFleet tcFleet);

    /**
     * 添加车队
     * @param tcFleet
     * @return
     */
    int addFleet(TcFleet tcFleet);

    /**
     * 删除车队
     * @param tcFleet
     * @return
     */
    int deleteFleet(TcFleet tcFleet);

    /**
     * 修改车队信息
     * @param tcFleet
     * @return
     */
    int updateFleet(TcFleet tcFleet);

    /**
     * 修改车队额度
     * @param transportionId 运输公司ID
     * @param fleetId 车队ID
     * @param cash 修改金额
     * @return
     */
    int updateFleetQuota(String transportionId, String fleetId, BigDecimal cash);
    /**
     * 修改分配资金信息
     * @param fleetMap
     * @return
     */
    int updateFleetMap(Map<String, Object> fleetMap);
    /**
     * 根据用户名称查询车队信息
     *
     * @param fleetName
     * @return
     */
    TcFleet queryFleetByName(String fleetName);
}
