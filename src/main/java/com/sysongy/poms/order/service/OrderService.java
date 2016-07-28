package com.sysongy.poms.order.service;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.order.model.OrderLog;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.ordergoods.model.SysOrderGoods;
import com.sysongy.poms.ordergoods.model.SysOrderGoodsForCRMReport;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.tcms.advance.model.TcFleet;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhangyt 2016-06-16
 *
 */
public interface OrderService {

    PageInfo<SysOrder> queryOrders(SysOrder obj) throws Exception;

    int deleteByPrimaryKey(String orderId);

    int insert(SysOrder record, List<SysOrderGoods> sysOrderGoods);

    SysOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKey(SysOrder record);
    
    PageInfo<OrderLog> queryOrderLogs(OrderLog obj) throws Exception;

    SysOrder selectByOrderGASID(SysOrder record);
    
    List<Map<String,Object>> calcDriverCashBack(String driverid);
    
    PageInfo<Map<String, Object>> queryRechargeReport(SysOrder record);
    
    PageInfo<Map<String, Object>> queryRechargeReportTotal(SysOrder record);
    
    PageInfo<Map<String, Object>> queryRechargeReportDetail(SysOrder record);
        
    PageInfo<Map<String, Object>> queryRechargeDriverReport(SysOrder record);
    
    PageInfo<Map<String, Object>> queryRechargeDriverReportTotal(SysOrder record);
    
    PageInfo<Map<String, Object>> queryRechargeDriverReportDetail(SysOrder record);
    
    PageInfo<Map<String, Object>> queryTransportionReport(SysOrder record);
    
    PageInfo<Map<String, Object>> queryTransportionReportTotal(SysOrder record);
    
    
    /**
     * 给司机充值
     * @param order
     * @return
     */
    String chargeToDriver(SysOrder record) throws Exception;
    
    /**
     * 给加注站充值
     * @param order
     * @return
     */
    String chargeToTransportion(SysOrder record) throws Exception;
	
    /**
     * 给加注站充值
     * 1.如果现金充值，不能超过预付款
     * 2.充值
     * 3.不返现
     * @paramorder
     * @return
     */
    String chargeToGasStation(SysOrder order) throws Exception;  	
    
    /**
	 * 运输公司给个人转账
	 * 1.扣除运输公司账户
	 * 2.个人账户增加金额
	 * 3.给运输公司返现。
	 * @return
	 */
	String transferTransportionToDriver(SysOrder order) throws Exception;
	
	/**
	 * 个人给个人转账
	 * 1.扣除个人账户credit_account
	 * 2.增加个人账户debit_account
	 * 3.不返现
	 * @return
	 */
	public String transferDriverToDriver(SysOrder order) throws Exception;
	
		
    /**
     * 判断订单能否充红
     * @param order
     * @return
     * @throws Exception
     */
    boolean checkCanDischarge(SysOrder order) throws Exception;
    
    /**
     * 根据原订单，创建充红订单
     * @param order
     * @return
     * @throws Exception
     */
    SysOrder createDischargeOrderByOriginalOrder(SysOrder originalOrder, String operatorId,String discharge_reason) throws Exception;

    /**
     * 生成订单编码 
     * @param order
     * @return
     */
    String createOrderNumber(String order_type) throws Exception;
    
    /**
     * 充红订单
     * @param order
     * @return
     * @throws Exception
     */
    String dischargeOrder(SysOrder originalOrder, SysOrder dischargeOrder) throws Exception;

    /**
     * 个人消费
     */
    String consumeByDriver(SysOrder record) throws Exception;
    
    /**
     * 运输公司消费
     */
    String consumeByTransportion(SysOrder order,Transportion tran, TcFleet tcfleet) throws Exception;

    /**
     * 验证订单
     */
    String validAccount(SysOrder record) throws Exception;

    List<SysOrderGoodsForCRMReport> queryGoodsOrderInfos(SysOrderGoodsForCRMReport sysOrderGoodsForCRMReport) throws Exception;

    /********************************************运输公司消费报表接口*********************************/
    /**
     * 运输公司个人消费报表
     * @param record
     * @return
     */
    PageInfo<Map<String,Object>> queryTcPersonalReport(SysOrder record);
    /**
     * 运输公司个人消费报表
     * @param record
     * @return
     */
    List<Map<String,Object>> queryTcPersonalList(SysOrder record);
    /**
     * 运输公司车队消费报表
     * @param record
     * @return
     */
    PageInfo<Map<String,Object>> queryTcFleetReport(SysOrder record);
    /**
     * 运输公司车队消费报表
     * @param record
     * @return
     */
    List<Map<String,Object>> queryTcFleetList(SysOrder record);
    /**
     * 运输公司队内消费报表
     * @param record
     * @return
     */
    PageInfo<Map<String,Object>> queryTcFleetMgReport(SysOrder record);
    /**
     * 运输公司队内消费报表
     * @param record
     * @return
     */
    List<Map<String,Object>> queryTcFleetMgList(SysOrder record);

}
