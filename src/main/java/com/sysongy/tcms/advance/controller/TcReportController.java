package com.sysongy.tcms.advance.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.DateTimeHelper;
import com.sysongy.util.ExportUtil;
import com.sysongy.util.GlobalConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @FileName: TcReportController
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月08日, 11:43
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description: 汇总报表
 */
@Controller
@RequestMapping("/web/tcms/report")
public class TcReportController extends BaseContoller{
    @Autowired
    OrderService orderService;
    @Autowired
    TransportionService transportionService;
    @Autowired
    private UsysparamService service;

    /**
     * 运输公司个人消费汇总报表
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/count/personal")
    public String queryTcPersonalReport(@ModelAttribute CurrUser currUser, ModelMap map, SysOrder order) throws Exception{
        String stationId = currUser.getStationId();
        PageBean bean = new PageBean();
        String ret = "webpage/tcms/advance/personal_count_log";

        try {
            if(order.getPageNum() == null){
                order.setOrderby("deal_date desc");
                order.setPageNum(1);
                order.setPageSize(10);
            }
            order.setDebitAccount(stationId);
            order.setCash(new BigDecimal(BigInteger.ZERO));
            PageInfo<Map<String, Object>> pageinfo = orderService.queryTcPersonalReport(order);

            List<Map<String, Object>> list = orderService.queryTcPersonalList(order);
            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            if(list != null && list.size() > 0){

                for (Map<String, Object> quotaMap:list) {
                    if(quotaMap.get("sumPrice") != null && !"".equals(quotaMap.get("sumPrice").toString())){
                        if(quotaMap.get("is_discharge") != null && quotaMap.get("is_discharge").toString().equals("0")){
                            totalCash = totalCash.add(new BigDecimal(quotaMap.get("sumPrice").toString()));
                        }else{
                            totalCash = totalCash.subtract(new BigDecimal(quotaMap.get("sumPrice").toString()));
                        }

                    }
                }
            }
            //累计总划款金额
            map.addAttribute("totalCash",totalCash);

            bean.setRetCode(100);
            bean.setRetMsg("查询成功");
            bean.setPageInfo(ret);

            map.addAttribute("ret", bean);
            map.addAttribute("pageInfo", pageinfo);
            map.addAttribute("order",order);
        } catch (Exception e) {
            bean.setRetCode(5000);
            bean.setRetMsg(e.getMessage());

            map.addAttribute("ret", bean);
            logger.error("", e);
            throw e;
        }
        finally {
            return ret;
        }
    }
    /**
     * 运输公司个人消费汇总报表导出
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/count/personal/import")
    public String queryTcPersonalImport(@ModelAttribute CurrUser currUser, ModelMap map,
                                        SysOrder order,HttpServletResponse response) throws Exception{
        String stationId = currUser.getStationId();
        Transportion transportion = new Transportion();
        String transName = "";
        try {
            transportion = transportionService.queryTransportionByPK(stationId);
            if(transportion != null){
                transName = transportion.getTransportion_name();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        PageBean bean = new PageBean();
        String ret = "webpage/tcms/advance/personal_log";

        try {
//            if(order.getPageNum() == null){
            order.setOrderby("deal_date desc");
            order.setPageNum(1);
            order.setPageSize(1048576);
//            }
            order.setDebitAccount(stationId);
            order.setCash(new BigDecimal(BigInteger.ZERO));
            PageInfo<Map<String, Object>> pageInfo = orderService.queryTcPersonalReport(order);

            /*生成报表*/
            int cells = 0 ; // 记录条数
            if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
                cells += pageInfo.getList().size();
            }
            OutputStream os = response.getOutputStream();
            ExportUtil reportExcel = new ExportUtil();
            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
            downLoadFileName = "个人消费报表_" + downLoadFileName;
            try {
                response.setHeader("Content-Disposition","attachment;filename=" + new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
            } catch (UnsupportedEncodingException e1) {
                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
            }
            String[][] content = new String[cells+3][13];//[行数][列数]
            //设置表头
            content[0] = new String[]{transName+"个人消费报表"};
            content[2] = new String[]{"订单编号","订单类型","交易类型","交易金额","姓名","手机号码","加注站名称",
                    "商品名称","结算单价","消费数量","消费金额","交易时间","备注"};
            //设置列宽
            String [] wcell = new String []{"0,26","1,13","2,13","3,13","4,13","5,13","6,13","7,13","8,13","9,13","10,13","11,23","12,23"};
            //合并第一行单元格
            String [] mergeinfo = new String []{"0,0,12,0","1,1,12,1"};
            //设置表名
            String sheetName = "个人消费报表";
            //设置字体
            String [] font = new String []{"0,15","2,13"};
            /*组装报表*/
            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            int i = 3;

            if(pageInfo.getList() != null && pageInfo.getList().size() > 0){

                for (Map<String, Object> quotaMap:pageInfo.getList()) {
                    /*if(quotaMap.get("cash") != null && !"".equals(quotaMap.get("cash").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("cash").toString()));
                    }*/
                    if(quotaMap.get("sumPrice") != null && !"".equals(quotaMap.get("sumPrice").toString())){
                        if(quotaMap.get("is_discharge") != null && quotaMap.get("is_discharge").toString().equals("0")){
                            totalCash = totalCash.add(new BigDecimal(quotaMap.get("sumPrice").toString()));
                        }else{
                            totalCash = totalCash.subtract(new BigDecimal(quotaMap.get("sumPrice").toString()));
                        }

                    }

                    //组装表格
                    String orderNumber = "";//订单编号
                    if(quotaMap.get("orderNumber") != null){
                        orderNumber = quotaMap.get("orderNumber").toString();
                    }
                    String orderType = "";
                    if(quotaMap.get("orderType") != null){
                        orderType = quotaMap.get("orderType").toString();
                        orderType = GlobalConstant.getOrderType(orderType);
                    }
                    String isDischarge = "";
                    if(quotaMap.get("is_discharge") != null){
                        if(quotaMap.get("is_discharge").toString().equals("0")){
                            isDischarge = "消费";
                        }else if(quotaMap.get("is_discharge").toString().equals("1")){
                            isDischarge = "冲红";
                        }
                    }
                    String cash = "";
                    if(quotaMap.get("sumPrice") != null){
                        if(quotaMap.get("is_discharge") != null && quotaMap.get("is_discharge").toString().equals("0")){
                            cash = quotaMap.get("sumPrice").toString();
                        }else{
                            cash = "-"+quotaMap.get("sumPrice").toString();
                        }

                    }else{
                        cash = "0.00";
                    }
                    String fullName = "";
                    if(quotaMap.get("full_name") != null){
                        fullName = quotaMap.get("full_name").toString();
                    }
                    String mobilePhone = "";
                    if(quotaMap.get("mobile_phone") != null){
                        mobilePhone = quotaMap.get("mobile_phone").toString();
                    }
                    String gasStationName = "";
                    if(quotaMap.get("gas_station_name") != null){
                        gasStationName = quotaMap.get("gas_station_name").toString();
                    }
                    String goodsType = "";
                    if(quotaMap.get("goods_type") != null){
                        goodsType = quotaMap.get("goods_type").toString();
                        Usysparam usysparam = service.queryUsysparamByCode("CARDTYPE",goodsType);
                        if(usysparam != null){
                            goodsType = usysparam.getMname();
                        }
                    }
                    String price = "";
                    if(quotaMap.get("price") != null){
                        price = quotaMap.get("price").toString();
                    }
                    String number = "";
                    if(quotaMap.get("number") != null){
                        number = quotaMap.get("number").toString();
                    }
                    String sumPrice = "";
                    /*if(quotaMap.get("cash") != null){
                        sumPrice = quotaMap.get("cash").toString();
                    }*/
                    if(quotaMap.get("sumPrice") != null){
                        if(quotaMap.get("is_discharge") != null && quotaMap.get("is_discharge").toString().equals("0")){
                            sumPrice = quotaMap.get("sumPrice").toString();
                        }else{
                            sumPrice = "-"+quotaMap.get("sumPrice").toString();
                        }
                    }else{
                        sumPrice = "0.00";
                    }
                    String orderDate = "";
                    if(quotaMap.get("orderDate") != null){
                        orderDate = quotaMap.get("orderDate").toString();
                    }
                    String remark = "";
                    if(quotaMap.get("remark") != null){
                        remark = quotaMap.get("remark").toString();
                    }
                    content[i] = new String[]{orderNumber,orderType,isDischarge,cash,fullName,mobilePhone,gasStationName,goodsType,price,number,sumPrice,orderDate,remark};
                    i++;
                }
            }
            //合计交易金额和返现金额
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[1] = new String[]{"合计："+totalCash.toString(),"导出时间："+sdf.format(new Date())};
            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, wcell, 0, null, 0, font, null, false);

            //累计总划款金额
            map.addAttribute("totalCash",totalCash);

            bean.setRetCode(100);
            bean.setRetMsg("查询成功");
            bean.setPageInfo(ret);

            map.addAttribute("ret", bean);
            map.addAttribute("pageInfo", pageInfo);
            map.addAttribute("order",order);
        } catch (Exception e) {
            bean.setRetCode(5000);
            bean.setRetMsg(e.getMessage());

            map.addAttribute("ret", bean);
            logger.error("", e);
            throw e;
        }

        return null;
    }

    /**
     * 运输公司车队消费汇总报表
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/count/fleets")
    public String queryTcFleetReport(@ModelAttribute CurrUser currUser, ModelMap map, SysDepositLog loger) throws Exception{
        String stationId = currUser.getStationId();
        loger.setStationId(stationId);
        PageBean bean = new PageBean();
        String ret = "webpage/tcms/advance/fleets_count_log";

        try {
            if(loger.getPageNum() == null){
                loger.setPageNum(1);
                loger.setPageSize(10);
            }
            if(StringUtils.isEmpty(loger.getOrderby())){
                loger.setOrderby("sys_transportion_id desc");
            }

            PageInfo<Map<String, Object>> pageinfo = transportionService.transportionConsumeReport(loger);
            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            if(pageinfo != null && pageinfo.getList() != null && pageinfo.getList().size() > 0){
                List<Map<String, Object>> list = pageinfo.getList();
                for (Map<String, Object> quotaMap:list) {
                    if(quotaMap.get("cash") != null && !"".equals(quotaMap.get("cash").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("cash").toString()));
                    }
                }
            }

            PageInfo<Map<String, Object>> total = transportionService.transportionConsumeReportTotal(loger);

            bean.setRetCode(100);
            bean.setRetMsg("查询成功");
            bean.setPageInfo(ret);

            map.addAttribute("ret", bean);
            map.addAttribute("pageInfo", pageinfo);
            map.addAttribute("loger", loger);
            map.addAttribute("totalCash",total.getList().get(0)==null?"0":total.getList().get(0).get("total"));
        } catch (Exception e) {
            bean.setRetCode(5000);
            bean.setRetMsg(e.getMessage());

            map.addAttribute("ret", bean);
            logger.error("", e);
            throw e;
        }
        finally {
            return ret;
        }
    }

    /**
     * 运输公司车队消费汇总报表导出
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/count/fleets/import")
    public String queryTcFleetImport(@ModelAttribute CurrUser currUser, ModelMap map,
                                     SysDepositLog loger, HttpServletResponse response) throws Exception{
        String stationId = currUser.getStationId();
        Transportion transportion = new Transportion();
        String transName = "";
        loger.setStationId(stationId);
        try {
            loger.setPageNum(1);
            loger.setPageSize(1048576);

            if(StringUtils.isEmpty(loger.getOrderby())){
                loger.setOrderby("sys_transportion_id desc");
            }

            PageInfo<Map<String, Object>> pageinfo = transportionService.transportionConsumeReport(loger);
            List<Map<String, Object>> list = pageinfo.getList();

            int cells = 0 ; // 记录条数

            if(list != null && list.size() > 0){
                cells += list.size();
            }
            OutputStream os = response.getOutputStream();
            ExportUtil reportExcel = new ExportUtil();

            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
            downLoadFileName = "车队消费汇总报表_" + downLoadFileName;

            try {
                response.addHeader("Content-Disposition","attachment;filename="+ new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
            } catch (UnsupportedEncodingException e1) {
                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
            }

            String[][] content = new String[cells+3][8];//[行数][列数]
            //第一列
            content[0] = new String[]{transName+"车队消费汇总报表"};
            content[2] = new String[]{"运输公司名称","车队编号","车队名称","加注站名称","消费金额","冲红金额","消费量","消费次数"};
            //设置列宽
            String [] wcell = new String []{"0,26","1,13","2,13","3,13","4,13","5,13","6,13","7,13","8,13","9,13","10,23","11,30"};
            //合并第一行单元格
            String [] mergeinfo = new String []{"0,0,7,0","1,1,7,1"};
            //设置表名
            String sheetName = "车队消费汇总报表";
            //设置字体
            String [] font = new String []{"0,15","2,13"};
            /*组装报表*/
            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            int i = 3;
            if(list != null && list.size() > 0){

                for (Map<String, Object> tmpMap:pageinfo.getList()) {
                    if(tmpMap.get("cash") != null && !"".equals(tmpMap.get("cash").toString())){
                        totalCash = totalCash.add(new BigDecimal(tmpMap.get("cash").toString()));
                    }

                    String transportion_name = tmpMap.get("transportion_name")==null?"":tmpMap.get("transportion_name").toString();
                    String tc_fleet_id = tmpMap.get("tc_fleet_id")==null?"":tmpMap.get("tc_fleet_id").toString();
                    String fleet_name = tmpMap.get("fleet_name")==null?"":tmpMap.get("fleet_name").toString();
                    String channel = tmpMap.get("channel")==null?"":tmpMap.get("channel").toString();
                    String cash = tmpMap.get("cash")==null?"":tmpMap.get("cash").toString();
                    String hedgefund = tmpMap.get("hedgefund")==null?"":tmpMap.get("hedgefund").toString();
                    String summit = tmpMap.get("summit")==null?"":tmpMap.get("summit").toString();
                    String consumecount = tmpMap.get("consumecount")==null?"":tmpMap.get("consumecount").toString();
                    content[i] = new String[]{transportion_name,tc_fleet_id,fleet_name,channel,cash,hedgefund,summit,consumecount};
                    i++;
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[1] = new String[]{"合计："+totalCash.toString(),"导出时间："+sdf.format(new Date())};

            //单元格默认宽度
            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, wcell, 0, null, 0, font, null, false);

        } catch (Exception e) {
            logger.error("", e);
        }

        return null;
    }
}
