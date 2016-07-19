package com.sysongy.tcms.advance.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserAccountService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import com.sysongy.tcms.advance.model.TcTransferAccount;
import com.sysongy.tcms.advance.service.TcFleetQuotaService;
import com.sysongy.tcms.advance.service.TcFleetService;
import com.sysongy.tcms.advance.service.TcTransferAccountService;
import com.sysongy.util.DateTimeHelper;
import com.sysongy.util.ExportUtil;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @FileName: TcFleetQuotaController
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月21日, 18:49
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Controller
@RequestMapping("/web/tcms/fleetQuota")
public class TcFleetQuotaController extends BaseContoller {


    @Autowired
    TcFleetQuotaService tcFleetQuotaService;
    @Autowired
    GasCardService gasCardService;
    @Autowired
    DriverService driverService;
    @Autowired
    OrderService orderService;
    @Autowired
    TransportionService transportionService;
    @Autowired
    SysUserAccountService userAccountService;
    @Autowired
    TcTransferAccountService tcTransferAccountService;

    /**
     * 查询车辆列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryFleetQuotaListPage(@ModelAttribute CurrUser currUser, TcFleetQuota fleetQuota,
                                          @RequestParam(required = false) Integer resultInt, ModelMap map) throws Exception{
        String stationId = currUser.getStationId();
        List<Map<String, Object>> fleetQuotaList = new ArrayList<>();
        Map<String, Object> fleetQuotaMap = new HashMap<>();
        fleetQuota.setStationId(stationId);
        Transportion transportion = transportionService.queryTransportionByPK(stationId);
        try {
            fleetQuotaMap = tcFleetQuotaService.queryFleetQuotaMapList(fleetQuota);
        }catch (Exception e){
            e.printStackTrace();
        }

        map.addAttribute("fleetQuotaMap",fleetQuotaMap);
        map.addAttribute("fleetQuota",fleetQuota);
        map.addAttribute("transportion",transportion);
        map.addAttribute("stationId",stationId);
        if(resultInt != null){
            Map<String, Object> resultMap = new HashMap<>();
            if(resultInt == 1){
                resultMap.put("retMsg","转账成功！");
            }else if(resultInt == 0){
                resultMap.put("retMsg","账户余额为负，不能转账！");
            }else if(resultInt < 0){
                resultMap.put("retMsg","账户余额不足，请先充值！");
            }else if(resultInt == 2){
                resultMap.put("retMsg","分配成功！");
            }else if(resultInt == 3){
                resultMap.put("retMsg","余额不足！");
            }else if(resultInt == 4){
                resultMap.put("retMsg","分配失败！");
            }else if(resultInt == 5){
                resultMap.put("retMsg","支付密码修改失败！");
            }else if(resultInt == 6){
                resultMap.put("retMsg","支付密码修改成功！");
            }else if(resultInt == 7){
                resultMap.put("retMsg","邮件发送失败！");
            }else if(resultInt == 8){
                resultMap.put("retMsg","邮件发送成功！");
            }

            map.addAttribute("ret",resultMap);
        }

        return "webpage/tcms/advance/fleet_quota_list";
    }

    /**
     * 保存分配资金
     * @param data
     * @param map
     * @return
     */
    @RequestMapping("/save/fenpei")
    public String saveFenpei(@ModelAttribute CurrUser currUser,@RequestParam String data, ModelMap map){
        int resultInt = 0;
        String stationId = currUser.getStationId();
        try {
            if(data != null && !"".equals(data)) {
                String datas[] = data.split("&");
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//将值分开存在list中
                Map<String, Object> mapObj = new HashMap();
                String dataTemp[];
                //根据运输公司ID查询运输公司信息
                Transportion transportion = transportionService.queryTransportionByPK(stationId);
                SysUserAccount userAccount = userAccountService.queryUserAccountByStationId(stationId);
                if(datas != null && datas.length > 0){
                    for (int i = 0; i < datas.length; i++) {
                        dataTemp = datas[i].split("=");
                        if(i == 0 && dataTemp.length > 1){//将第一位赋值给站点编号
                            stationId = dataTemp[1];
                        }else{
                            if (dataTemp.length > 1) {
                                mapObj.put(dataTemp[0], dataTemp[1]);
                            } else {
                                mapObj.put(dataTemp[0], "0");
                            }
                            if (i % 3 == 0) {   //数字12 为  提交过来每一行  需要修改数据的数量
                                mapObj.put("tcFleetQuotaId",UUIDGenerator.getUUID());
                                mapObj.put("stationId",stationId);
                                list.add(mapObj);
                                mapObj = new HashMap<>();
                            }
                        }

                    }
                }
                //判断未分配额度是否够用
                BigDecimal userQuota = new BigDecimal(BigInteger.ZERO);//使用额度
                if(list != null && list.size() > 0){
                    BigDecimal totalVal = new BigDecimal(BigInteger.ZERO);
                    BigDecimal deposit = transportion.getDeposit();
                    for (Map<String, Object> mapVal:list) {
                        if(mapVal.get("isAllot").toString().equals("1")){//分配
                            BigDecimal quotaBig = new BigDecimal(mapVal.get("quota").toString());
                            userQuota = userQuota.add(quotaBig);
                        }
                    }
                    if(userQuota.compareTo(userAccount.getAccountBalanceBigDecimal()) <= 0){
                        //添加分配记录
                        tcFleetQuotaService.addFleetQuotaList(list);

                        //计算运输公司剩余额度

                        if(userAccount != null){
                            BigDecimal banlance = userAccount.getAccountBalanceBigDecimal();
                            userQuota = banlance.subtract(userQuota);

                            //更新运输公司剩余额度
                            Transportion quotaTrans = new Transportion();
                            quotaTrans.setSys_transportion_id(stationId);
                            quotaTrans.setDeposit(userQuota);

                            transportionService.updateDeposit(quotaTrans);
                            resultInt = 2;//成功
                        }else{
                            resultInt = 4;//失败
                        }
                    }else{
                        resultInt = 3;//余额不足
                    }
                }

            }
        }catch (Exception e){
            resultInt = 4;//失败
            e.printStackTrace();
        }
            return "redirect:/web/tcms/fleetQuota/list/page?resultInt="+resultInt;
    }

    /**
     * 个人转账
     * @param data
     * @param map
     * @return
     */
    @RequestMapping("/save/zhuan")
    public String saveZhuan(@ModelAttribute CurrUser currUser,@RequestParam String data, ModelMap map) throws Exception{
        int resultInt = 1;
        String stationId = currUser.getStationId();
        String userName = currUser.getUser().getUserName();
        String userId = currUser.getUser().getSysUserId();
        try {
            if(data != null && !"".equals(data)) {
                String datas[] = data.split("&");
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//将值分开存在list中
                Map<String, Object> mapObj = new HashMap();
                String dataTemp[];
                if(datas != null && datas.length > 0){
                    for (int i = 0; i < datas.length; i++) {
                        dataTemp = datas[i].split("=");
                        if (dataTemp.length > 1) {
                            mapObj.put(dataTemp[0], dataTemp[1]);
                        } else {
                            mapObj.put(dataTemp[0], "0");
                        }
                        if ((i+1) % 5 == 0 ) {   //数字5 为  提交过来每一行  需要修改数据的数量
                            list.add(mapObj);
                            mapObj = new HashMap<>();
                        }

                    }
                }

                resultInt = tcFleetQuotaService.personalTransfer(list,stationId,userId);
                /*if(list != null && list.size() > 0){
                    for (Map<String, Object> mapDriver:list){
                        SysOrder order = new SysOrder();
                        order.setOrderId(UUIDGenerator.getUUID());

                        order.setOrderType(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);
                        String orderNum = orderService.createOrderNumber(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);
                        order.setOrderDate(new Date());
                        BigDecimal cash = new BigDecimal(mapDriver.get("amount").toString());
                        order.setCash(cash);
                        order.setCreditAccount(stationId);
                        order.setDebitAccount(mapDriver.get("sysDriverId").toString());
                        order.setChargeType(GlobalConstant.OrderChargeType.CHARGETYPE_TRANSFER_CHARGE);
                        order.setOperator(userName);
                        order.setOperatorSourceId(stationId);
                        order.setOperatorSourceType(GlobalConstant.OrderOperatorSourceType.TRANSPORTION);
                        order.setOperatorTargetType(GlobalConstant.OrderOperatorSourceType.DRIVER);
                        order.setOrderNumber(orderNum);

                        //添加订单
                        orderService.insert(order);
                        //运输公司往个人转账
                        orderService.transferTransportionToDriver(order);
                    }
                }*/
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/web/tcms/fleetQuota/list/page?resultInt="+resultInt;
    }

    /**
     * 根据手机号码查询司机信息
     * @param sysDriver
     * @param map
     * @return
     */
    @RequestMapping("/info/driver")
    @ResponseBody
    public SysDriver queryDriverInfo(@ModelAttribute CurrUser currUser,SysDriver sysDriver, ModelMap map){
        SysDriver driver = new SysDriver();
        String stationId = currUser.getStationId();
        driver = sysDriver;
        /*driver.setStationId(stationId);*/
        try {
            driver = driverService.queryDriverByMobilePhone(driver);
        }catch (Exception e){
            e.printStackTrace();
        }

        return driver;
    }
    /********************************************运输公司消费报表接口*********************************/
    /**
     * 额度划拨报表
     * @param tcFleet
     * @return
     */
    @RequestMapping("/list/quota")
    public String queryQuotaList(@ModelAttribute CurrUser currUser, TcFleet tcFleet, ModelMap map){
        String stationId = currUser.getStationId();
        PageBean bean = new PageBean();
        String ret = "webpage/tcms/advance/fleet_quota_log";

        try {
            if(tcFleet.getPageNum() == null){
                tcFleet.setOrderby("created_date desc");
                tcFleet.setPageNum(1);
                tcFleet.setPageSize(10);
            }
            tcFleet.setStationId(stationId);
            PageInfo<Map<String, Object>> pageinfo = tcFleetQuotaService.queryQuotaList(tcFleet);

            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            if(pageinfo.getList() != null && pageinfo.getList().size() > 0){
                for (Map<String, Object> quotaMap:pageinfo.getList()) {
                    if(quotaMap.get("quota") != null && !"".equals(quotaMap.get("quota").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("quota").toString()));
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
            map.addAttribute("tcFleet",tcFleet);
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
     * 转账报表
     * @param transferAccount
     * @return
     */
    @RequestMapping("/list/transfer")
    public String queryTransferListPage(@ModelAttribute CurrUser currUser, TcTransferAccount transferAccount, ModelMap map){
        String stationId = currUser.getStationId();
        PageBean bean = new PageBean();
        String ret = "webpage/tcms/advance/transfer_log";

        try {
            if(transferAccount.getPageNum() == null){
                transferAccount.setOrderby("deal_date desc");
                transferAccount.setPageNum(1);
                transferAccount.setPageSize(10);
            }
            transferAccount.setStationId(stationId);
            transferAccount.setSysDriverId(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);//订单类型为转账
            PageInfo<Map<String, Object>> pageinfo = tcTransferAccountService.queryTransferListPage(transferAccount);

            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            BigDecimal backCash = new BigDecimal(BigInteger.ZERO);
            if(pageinfo.getList() != null && pageinfo.getList().size() > 0){

                for (Map<String, Object> quotaMap:pageinfo.getList()) {
                    if(quotaMap.get("cash") != null && !"".equals(quotaMap.get("cash").toString())
                            && GlobalConstant.OrderDealType.TRANSFER_TRANSPORTION_TO_DRIVER_DEDUCT_TRANSPORTION.equals(quotaMap.get("dealType"))){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("cash").toString()));
                    }else{
                        backCash = backCash.add(new BigDecimal(quotaMap.get("cashBack").toString()));
                    }
                }
                totalCash = totalCash.add(backCash);
            }
            //累计总划款金额
            map.addAttribute("totalCash",totalCash);

            bean.setRetCode(100);
            bean.setRetMsg("查询成功");
            bean.setPageInfo(ret);

            map.addAttribute("ret", bean);
            map.addAttribute("pageInfo", pageinfo);
            map.addAttribute("transferAccount",transferAccount);
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
     * 转账报表导出
     * @param transferAccount
     * @return
     */
    @RequestMapping("/list/transfer/report")
    public String queryTransferListReport(@ModelAttribute CurrUser currUser, TcTransferAccount transferAccount,
                                          ModelMap map,HttpServletResponse response) throws IOException {
        String stationId = currUser.getStationId();
        PageBean bean = new PageBean();
        String ret = "webpage/tcms/advance/transfer_log";
        /*查询数据*/
        try {
            if(transferAccount.getPageNum() == null){
                transferAccount.setOrderby("deal_date desc");
                transferAccount.setPageNum(1);
                transferAccount.setPageSize(10);
            }
            transferAccount.setStationId(stationId);
            transferAccount.setSysDriverId(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);//订单类型为转账
            PageInfo<Map<String, Object>> pageinfo = tcTransferAccountService.queryTransferListPage(transferAccount);

            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            BigDecimal backCash = new BigDecimal(BigInteger.ZERO);
            if(pageinfo.getList() != null && pageinfo.getList().size() > 0){

                for (Map<String, Object> quotaMap:pageinfo.getList()) {
                    if(quotaMap.get("cash") != null && !"".equals(quotaMap.get("cash").toString())
                            && GlobalConstant.OrderDealType.TRANSFER_TRANSPORTION_TO_DRIVER_DEDUCT_TRANSPORTION.equals(quotaMap.get("dealType"))){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("cash").toString()));
                    }else{
                        backCash = backCash.add(new BigDecimal(quotaMap.get("cashBack").toString()));
                    }
                }
                totalCash = totalCash.add(backCash);
            }

            /*生成报表*/
            int cells = 0 ; // 记录条数
            OutputStream os = response.getOutputStream();
            ExportUtil reportExcel = new ExportUtil();
            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
            downLoadFileName = "转账报表_" + downLoadFileName;
            try {
                response.setHeader("Content-Disposition","attachment;filename=" + java.net.URLEncoder.encode(downLoadFileName, "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
            }
            String[][] content = new String[cells+1][35];//[行数][列数]
            //第一列
            content[0] = new String[]{"姓名","班级编码","性别","出生日期","身份证件类型","身份证件号码","血型","国籍/地区","民族","港澳台侨外",
                    "出生所在地","籍贯","户口性质","非农业户口类型","户口所在地","现住址","入园日期","就读方式","是否独生子女","是否留守儿童","否进城务工人员子女",
                    "健康状况","是否残疾幼儿","残疾幼儿类别","是否孤儿","监护人姓名","监护人身份证件类型","监护人身份证件号码","健康档案号","爸爸姓名","爸爸电话","爸爸年龄","妈妈姓名","妈妈电话","妈妈年龄"};
            int i = 1;

            String [] mergeinfo = new String []{"0,0,0,0"};
            //单元格默认宽度
            String sheetName = "全园学生列表";
            //childexcel.exportFormatExcel(content, sheetName,mergeinfo ,os);
            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, null, 0, null, 0, null, null, false);

            //累计总划款金额
            map.addAttribute("totalCash",totalCash);

            bean.setRetCode(100);
            bean.setRetMsg("查询成功");
            bean.setPageInfo(ret);

            map.addAttribute("ret", bean);
            map.addAttribute("pageInfo", pageinfo);
            map.addAttribute("transferAccount",transferAccount);
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
     * 运输公司个人消费报表
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/report/personal")
    public String queryTcPersonalReport(@ModelAttribute CurrUser currUser, ModelMap map, SysOrder order) throws Exception{
        String stationId = currUser.getStationId();
        PageBean bean = new PageBean();
        String ret = "webpage/tcms/advance/personal_log";

        try {
            if(order.getPageNum() == null){
                order.setOrderby("deal_date desc");
                order.setPageNum(1);
                order.setPageSize(10);
            }
            order.setDebitAccount(stationId);
            order.setCash(new BigDecimal(BigInteger.ZERO));
            PageInfo<Map<String, Object>> pageinfo = orderService.queryTcPersonalReport(order);

            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            if(pageinfo.getList() != null && pageinfo.getList().size() > 0){

                for (Map<String, Object> quotaMap:pageinfo.getList()) {
                    if(quotaMap.get("cash") != null && !"".equals(quotaMap.get("cash").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("cash").toString()));
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
     * 运输公司车队消费报表
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/report/fleets")
    public String queryTcFleetReport(@ModelAttribute CurrUser currUser, ModelMap map, SysOrder order) throws Exception{
        String stationId = currUser.getStationId();
        PageBean bean = new PageBean();
        String ret = "webpage/tcms/advance/fleets_log";

        try {
            if(order.getPageNum() == null){
                order.setOrderby("deal_date desc");
                order.setPageNum(1);
                order.setPageSize(10);
            }
            order.setDebitAccount(stationId);
            order.setCash(new BigDecimal(BigInteger.ZERO));
            PageInfo<Map<String, Object>> pageinfo = orderService.queryTcFleetReport(order);

            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            if(pageinfo.getList() != null && pageinfo.getList().size() > 0){

                for (Map<String, Object> quotaMap:pageinfo.getList()) {
                    if(quotaMap.get("sum_price") != null && !"".equals(quotaMap.get("sum_price").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("sum_price").toString()));
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
     * 运输公司队内管理消费报表
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/report/fleetMg")
    public String queryTcFleetMgReport(@ModelAttribute CurrUser currUser, ModelMap map, SysOrder order) throws Exception{
        String stationId = currUser.getStationId();
        PageBean bean = new PageBean();
        String ret = "webpage/tcms/advance/fleetMg_log";

        try {
            if(order.getPageNum() == null){
                order.setOrderby("deal_date desc");
                order.setPageNum(1);
                order.setPageSize(10);
            }
            order.setDebitAccount(stationId);
            order.setCash(new BigDecimal(BigInteger.ZERO));
            PageInfo<Map<String, Object>> pageinfo = orderService.queryTcFleetMgReport(order);

            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            if(pageinfo.getList() != null && pageinfo.getList().size() > 0){

                for (Map<String, Object> quotaMap:pageinfo.getList()) {
                    if(quotaMap.get("sum_price") != null && !"".equals(quotaMap.get("sum_price").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("sum_price").toString()));
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

}
