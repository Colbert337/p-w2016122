package com.sysongy.tcms.advance.dao;

import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcFleet;

import java.util.List;
import java.util.Map;

public interface TcFleetMapper {

    /*********************************基础方法 start*************************************/

    /**
     * 查询车队信息
     *
     * @param tcFleet
     * @return
     */
    TcFleet queryFleet(TcFleet tcFleet);

    /**
     * 查询车队信息列表
     *
     * @param tcFleet
     * @return
     */
    List<TcFleet> queryFleetList(TcFleet tcFleet);

    /**
     * 查询车队信息列表
     *
     * @param tcFleet
     * @return
     */
    List<Map<String, Object>> queryFleetMapList(TcFleet tcFleet);

    /**
     * 添加车队
     *
     * @param tcFleet
     * @return
     */
    int addFleet(TcFleet tcFleet);

    /**
     * 删除车队
     *
     * @param tcFleet
     * @return
     */
    int deleteFleet(TcFleet tcFleet);


    /**
     * 修改车队信息
     *
     * @param tcFleet
     * @return
     */
    int updateFleet(TcFleet tcFleet);

/*********************************基础方法 end*************************************/
    /**
     * 根据车队查询车辆列表
     * @param fleet
     * @return
     */
    List<Map<String, Object>> queryFleetListByType(TcFleet fleet);
}