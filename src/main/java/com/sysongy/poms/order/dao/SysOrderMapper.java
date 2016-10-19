package com.sysongy.poms.order.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.order.model.OrderLog;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.ordergoods.model.SysOrderGoodsForCRMReport;

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
   
   List<Map<String,Object>> calcDriverCashBack(String driverid);
   
   List<Map<String,Object>> queryRechargeReport(SysOrder record);
   
   List<Map<String,Object>> queryRechargeReportTotal(SysOrder record);
   
   List<Map<String,Object>> queryRechargeReportDetail(SysOrder record);
   
   List<Map<String,Object>> queryRechargeReportDetail2(SysOrder record);
   
   List<Map<String,Object>> queryRechargeDriverReport(SysOrder record);
   
   List<Map<String,Object>> queryRechargeDriverReportTotal(SysOrder record);
   
   List<Map<String,Object>> queryTransportionReport(SysOrder record);
   
   List<Map<String,Object>> queryTransportionReportTotal(SysOrder record);
   
   List<Map<String,Object>> queryGastationRechargeReport(SysOrder record);
   
   List<Map<String,Object>> queryGastationRechargeReportTotal(SysOrder record);
   
   List<Map<String,Object>> queryGastationConsumeReport(SysOrder record);
   
   List<Map<String,Object>> queryGastationConsumeReportTotal(SysOrder record);
   
   List<Map<String,Object>> queryGastationConsumeReportDetail(SysOrder record);
   
    /**
     * 查询某个用户在start_date之后，有没有产生消费
     * 用于在用户充红的时候判断能否充红。
     * @param userId---用户ID
     * @param order_type --- 传过来消费的类型
     * @param start_date ----特定日期
     * @return
     */
    List<SysOrder> queryConsumeOrderByUserId(Map paraMap);

    List<SysOrderGoodsForCRMReport> queryGoodsOrderInfos(SysOrderGoodsForCRMReport sysOrderGoodsForCRMReport);
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

    /********************************************APP报表接口*********************************/
    /**
     * 充值记录
     * @param record
     * @return
     */
    List<Map<String,Object>> queryDriverReChargeList(SysOrder record);

    /**
     * 消费记录
     * @param record
     * @return
     */
    List<Map<String,Object>> queryDriverConsumeList(SysOrder record);

    /**
     * 转账记录
     * @param record
     * @return
     */
    List<Map<String,Object>> queryDriverTransferList(SysOrder record);
    /**
     * 消费订单详情
     * @param orderId
     * @return
     */
    SysOrder queryById(String orderId);
}