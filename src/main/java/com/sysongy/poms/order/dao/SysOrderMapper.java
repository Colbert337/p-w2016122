package com.sysongy.poms.order.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.order.model.OrderLog;
import com.sysongy.poms.order.model.SysOrder;

public interface SysOrderMapper {

    List<SysOrder> queryForPage(SysOrder record);
    
    List<OrderLog> queryOrderLogs(OrderLog record);

    int deleteByPrimaryKey(String orderId);

    int insert(SysOrder record);

    int insertSelective(SysOrder record);

    SysOrder selectByPrimaryKey(String orderId);

    SysOrder selectByOrderGASID(SysOrder record);

    int updateByPrimaryKeySelective(SysOrder record);

    int updateByPrimaryKey(SysOrder record);
    
    int updateOriginalOrderAfterDischarged(Map map);
    
    /**
     * 
     * @param map
     * @return
     */
   public Map querySumChargeByUserId(Map map);
    
    /**
     * 查询某个用户在start_date之后，有没有产生消费
     * 用于在用户充红的时候判断能否充红。
     * @param userId---用户ID
     * @param order_type --- 传过来消费的类型
     * @param start_date ----特定日期
     * @return
     */
    List<SysOrder> queryConsumeOrderByUserId(Map paraMap);
    /********************************************运输公司消费报表接口*********************************/
    /**
     * 运输公司个人消费报表
     * @param record
     * @return
     */
    List<Map<String,Object>> queryTcPersonalReport(SysOrder record);
    /**
     * 运输公司车队消费报表
     * @param record
     * @return
     */
    List<Map<String,Object>> queryTcFleetReport(SysOrder record);
}