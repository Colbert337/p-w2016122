package com.sysongy.tcms.advance.dao;

import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcFleetQuota;

import java.util.List;
import java.util.Map;

public interface TcFleetQuotaMapper {

    /*********************************基础方法 start*************************************/

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
    List<TcFleetQuota> queryFleetQuotaList(TcFleetQuota tcFleetQuota);

    /**
     * 查询车队额度信息列表
     * @param tcFleetQuota
     * @return
     */
    List<Map<String,Object>> queryFleetQuotaMapList(TcFleetQuota tcFleetQuota);

    /**
     * 添加车队额度
     * @param tcFleetQuota
     * @return
     */
    int addFleetQuota(TcFleetQuota tcFleetQuota);

    /**
     * 批量添加车队额度
     * @param tcFleetQuotaList
     * @return
     */
    int addFleetQuotaList(List<Map<String, Object>> tcFleetQuotaList);

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

    /*********************************基础方法 end*************************************/

    /**
     * 查询车队额度信息列表
     * @param tcFleet
     * @return
     */
    List<Map<String, Object>> queryQuotaList(TcFleet tcFleet);
}