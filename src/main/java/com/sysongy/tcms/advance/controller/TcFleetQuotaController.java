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
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import com.sysongy.tcms.advance.model.TcTransferAccount;
import com.sysongy.tcms.advance.service.TcFleetQuotaService;
import com.sysongy.tcms.advance.service.TcTransferAccountService;
import com.sysongy.util.DateTimeHelper;
import com.sysongy.util.ExportUtil;
import com.sysongy.util.GlobalConstant;

import com.sysongy.util.UUIDGenerator;
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
    @Autowired
    private UsysparamService service;

    /**
     * 查询车队额度列表
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
                                mapObj.put("tcFleetQuotaId", UUIDGenerator.getUUID());
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

                        //重置未分配额度的车队额度
                        for(Map<String, Object> quota:list){
                            if(quota.get("isAllot").toString().equals("0")){
                                quota.put("quota",userQuota);
                            }
                        }
                        //添加分配记录
                        tcFleetQuotaService.addFleetQuotaList(list);
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
        if(sysDriver != null && sysDriver.getMobilePhone() != null && !"".equals(sysDriver.getMobilePhone())){
            driver = sysDriver;
            try {
                driver = driverService.queryDriverByMobilePhone(driver);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            driver = null;
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
            if(tcFleet.getConvertPageNum() != null){
    			if(tcFleet.getConvertPageNum() > tcFleet.getPageNumMax()){
    				tcFleet.setPageNum(tcFleet.getPageNumMax());
    			}else if(tcFleet.getConvertPageNum() < 1){
    				tcFleet.setPageNum(1);
    			}else{
    				tcFleet.setPageNum(tcFleet.getConvertPageNum());
    			}
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
     * 额度划拨报表导出
     * @param tcFleet
     * @return
     */
    @RequestMapping("/list/quota/import")
    public String queryQuotaListImport(@ModelAttribute CurrUser currUser, TcFleet tcFleet,
                                       ModelMap map,HttpServletResponse response){
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

        try {
            /*if(tcFleet.getPageNum() == null){*/
                tcFleet.setOrderby("created_date desc");
                tcFleet.setPageNum(1);
                tcFleet.setPageSize(1048576);
            /*}*/
            tcFleet.setStationId(stationId);
            PageInfo<Map<String, Object>> pageInfo = tcFleetQuotaService.queryQuotaList(tcFleet);

            /*生成报表*/
            int cells = 0 ; // 记录条数
            if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
                cells += pageInfo.getList().size();
            }
            OutputStream os = response.getOutputStream();
            ExportUtil reportExcel = new ExportUtil();
            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
            downLoadFileName = "额度划拨报表_" + downLoadFileName;
            try {
                response.setHeader("Content-Disposition","attachment;filename=" + new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
            } catch (UnsupportedEncodingException e1) {
                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
            }
            String[][] content = new String[cells+3][13];//[行数][列数]
            //设置表头
            content[0] = new String[]{transName+"额度划拨报表"};
            content[2] = new String[]{"划拨对象","划拨额度","划拨时间"};
            //设置列宽
            String [] wcell = new String []{"0,26","1,13","2,23"};
            //合并第一行单元格
            String [] mergeinfo = new String []{"0,0,2,0","1,1,2,1"};
            //设置表名
            String sheetName = "额度划拨报表";
            //设置字体
            String [] font = new String []{"0,15","2,13"};
            /*组装报表*/
            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            int i = 3;

            if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
                for (Map<String, Object> quotaMap:pageInfo.getList()) {
                    if(quotaMap.get("quota") != null && !"".equals(quotaMap.get("quota").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("quota").toString()));
                    }

                    //组装表格
                    String fleetName = "";//订单编号
                    if(quotaMap.get("fleetName") != null){
                        fleetName = quotaMap.get("fleetName").toString();
                    }

                    String quota = "";
                    if(quotaMap.get("quota") != null && !"".equals(quotaMap.get("quota").toString())){
                        quota = quotaMap.get("quota").toString();
                    }else{
                        quota = "0.00";
                    }
                    String createdDate = "";
                    if(quotaMap.get("createdDate") != null){
                        createdDate = quotaMap.get("createdDate").toString();
                    }

                    content[i] = new String[]{fleetName,quota,createdDate};
                    i++;
                }
            }
            //合计交易金额和返现金额
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[1] = new String[]{"","导出时间："+sdf.format(new Date())};
            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, wcell, 0, null, 0, font, null, false);

            //累计总划款金额
            map.addAttribute("totalCash",totalCash);

            map.addAttribute("pageInfo", pageInfo);
            map.addAttribute("tcFleet",tcFleet);
        } catch (Exception e) {
            map.addAttribute("ret", bean);
            logger.error("", e);
        }
            return null;
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
            if(transferAccount.getConvertPageNum() != null){
    			if(transferAccount.getConvertPageNum() > transferAccount.getPageNumMax()){
    				transferAccount.setPageNum(transferAccount.getPageNumMax());
    			}else{
    				transferAccount.setPageNum(transferAccount.getConvertPageNum());
    			}
    		}
            transferAccount.setStationId(stationId);
            transferAccount.setSysDriverId(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);//订单类型为转账
            PageInfo<Map<String, Object>> pageinfo = tcTransferAccountService.queryTransferListPage(transferAccount);

            List<Map<String, Object>> list = tcTransferAccountService.queryTransferList(transferAccount);
            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            BigDecimal backCash = new BigDecimal(BigInteger.ZERO);
            if(list != null && list.size() > 0){

                for (Map<String, Object> quotaMap:list) {
                    if(quotaMap.get("cash") != null && !"".equals(quotaMap.get("cash").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("cash").toString()));
                        backCash = backCash.add(new BigDecimal(quotaMap.get("cashBack").toString()));
                    }
                }
            }
            //累计总划款金额
            map.addAttribute("totalCash",totalCash);
            map.addAttribute("backCash",backCash);

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
    @RequestMapping("/list/transfer/import")
    public String queryTransferListImport(@ModelAttribute CurrUser currUser, TcTransferAccount transferAccount,
                                          ModelMap map,HttpServletResponse response) throws IOException {
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
        String ret = "webpage/tcms/advance/transfer_log";
        /*查询数据*/
        try {
//            if(transferAccount.getPageNum() == null){
                transferAccount.setOrderby("deal_date desc");
                transferAccount.setPageNum(1);
                transferAccount.setPageSize(1048576);
//            }
            transferAccount.setStationId(stationId);
            transferAccount.setSysDriverId(GlobalConstant.OrderType.TRANSFER_TRANSPORTION_TO_DRIVER);//订单类型为转账
            PageInfo<Map<String, Object>> pageInfo = tcTransferAccountService.queryTransferListPage(transferAccount);

            /*生成报表*/
            int cells = 0 ; // 记录条数
            if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
                cells += pageInfo.getList().size();
            }
            OutputStream os = response.getOutputStream();
            ExportUtil reportExcel = new ExportUtil();
            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
            downLoadFileName = "转账报表_" + downLoadFileName;
            try {
                response.setHeader("Content-Disposition","attachment;filename=" + new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
            } catch (UnsupportedEncodingException e1) {
                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
            }
            String[][] content = new String[cells+3][9];//[行数][列数]
            //设置表头
            content[0] = new String[]{transName+"转账报表"};
            content[2] = new String[]{"订单编号","交易类型","收款人","手机号码","转账金额","资金用途","返现金额","操作人","交易时间"};
            //设置列宽
            String [] wcell = new String []{"0,26","1,13","2,13","3,13","4,13","5,13","6,13","7,13","8,23",};
            //合并第一行单元格
            String [] mergeinfo = new String []{"0,0,8,0","0,1,1,1","2,1,8,1"};
            //设置表名
            String sheetName = "转账报表";
            //设置字体
            String [] font = new String []{"0,15","2,13"};
            /*组装报表*/
            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            BigDecimal backCash = new BigDecimal(BigInteger.ZERO);
            int i = 3;
            if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
                for (Map<String, Object> quotaMap:pageInfo.getList()) {
                    //累计总金额
                    if(quotaMap.get("cash") != null && !"".equals(quotaMap.get("cash").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("cash").toString()));
                        backCash = backCash.add(new BigDecimal(quotaMap.get("cashBack").toString()));
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
                    String fullName = "";
                    if(quotaMap.get("fullName") != null){
                        fullName = quotaMap.get("fullName").toString();
                    }
                    String mobilePhone = "";
                    if(quotaMap.get("mobilePhone") != null){
                        mobilePhone = quotaMap.get("mobilePhone").toString();
                    }
                    String cash = "";
                    if(quotaMap.get("cash") != null){
                        cash = quotaMap.get("cash").toString();
                    }else{
                        cash = "0.00";
                    }
                    String used = "";
                    if(quotaMap.get("used") != null){
                        used = quotaMap.get("used").toString();
                    }
                    String cashBack = "0.00";
                    if(quotaMap.get("cashBack") != null){
                        cashBack = quotaMap.get("cashBack").toString();
                    }
                    String realName = "";
                    if(quotaMap.get("realName") != null){
                        realName = quotaMap.get("realName").toString();
                    }
                    String dealDate = "";
                    if(quotaMap.get("dealDate") != null){
                        dealDate = quotaMap.get("dealDate").toString();
                    }
                    content[i] = new String[]{orderNumber,orderType,fullName,mobilePhone,cash,used,cashBack,realName,dealDate};
                    i++;
                }

            }
            //合计交易金额和返现金额
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[1] = new String[]{"转账总金额："+totalCash.toString()+"  返现总金额："+backCash.toString(),"","导出时间："+sdf.format(new Date())};
            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, wcell, 0, null, 0, font, null, false);

            //累计总划款金额
            map.addAttribute("totalCash",totalCash);

            bean.setRetCode(100);
            bean.setRetMsg("查询成功");
            bean.setPageInfo(ret);

            map.addAttribute("ret", bean);
            map.addAttribute("pageInfo", pageInfo);
            map.addAttribute("transferAccount",transferAccount);
        } catch (Exception e) {
            bean.setRetCode(5000);
            bean.setRetMsg(e.getMessage());
            map.addAttribute("ret", bean);
            logger.error("", e);
        }

        return null;
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
            if(order.getConvertPageNum() != null){
    			if(order.getConvertPageNum() > order.getPageNumMax()){
    				order.setPageNum(order.getPageNumMax());
    			}else if(order.getConvertPageNum() < 1){
    				order.setPageNum(1);
    			}else{
    				order.setPageNum(order.getConvertPageNum());
    			}
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
     * 运输公司个人消费报表导出
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/report/personal/import")
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
            String[][] content = new String[cells+3][12];//[行数][列数]
            //设置表头
            content[0] = new String[]{transName+"个人消费报表"};
            content[2] = new String[]{"订单编号","订单类型","交易类型","姓名","手机号码","加注站名称",
                    "商品名称","结算单价","消费数量","实收金额","订单金额","交易时间","备注"};
            //设置列宽
            String [] wcell = new String []{"0,26","1,13","2,13","3,13","4,13","5,20","6,13","7,13","8,13","9,13","10,23","11,23"};
            //合并第一行单元格
            String [] mergeinfo = new String []{"0,0,11,0","1,1,11,1"};
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
                    if(quotaMap.get("cash") != null){
                        if(quotaMap.get("is_discharge") != null && quotaMap.get("is_discharge").toString().equals("0")){
                            cash = quotaMap.get("cash").toString();
                        }else{
                            cash = "-"+quotaMap.get("cash").toString();
                        }

                    }else{
                        cash = "0.00";
                    }
                    String should_payment = "";
                    if(quotaMap.get("should_payment") != null){
                        if(quotaMap.get("is_discharge") != null && quotaMap.get("is_discharge").toString().equals("0")){
                        	should_payment = quotaMap.get("should_payment").toString();
                        }else{
                        	should_payment = "-"+quotaMap.get("should_payment").toString();
                        }

                    }else{
                    	should_payment = "0.00";
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
                    content[i] = new String[]{orderNumber,orderType,isDischarge,fullName,mobilePhone,gasStationName,goodsType,price,number,cash,should_payment,orderDate,remark};
                    i++;
                }
            }
            //合计交易金额和返现金额
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[1] = new String[]{"消费总金额："+totalCash.toString(),"导出时间："+sdf.format(new Date())};
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
            if(order.getConvertPageNum() != null){
    			if(order.getConvertPageNum() > order.getPageNumMax()){
    				order.setPageNum(order.getPageNumMax());
    			}else if(order.getConvertPageNum() < 1){
    				order.setPageNum(1);
    			}else{
    				order.setPageNum(order.getConvertPageNum());
    			}
    		}
            order.setDebitAccount(stationId);
            order.setCash(new BigDecimal(BigInteger.ZERO));
            PageInfo<Map<String, Object>> pageinfo = orderService.queryTcFleetReport(order);

            List<Map<String, Object>> list = orderService.queryTcFleetList(order);
            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            BigDecimal chongHong = new BigDecimal(BigInteger.ZERO);
            if(list != null && list.size() > 0){

                /*for (Map<String, Object> quotaMap:list) {
                    if(quotaMap.get("cash") != null && !"".equals(quotaMap.get("cash").toString())){
                        totalCash = totalCash.add(new BigDecimal(quotaMap.get("cash").toString()));
                    }
                }*/
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
     * 运输公司车队消费报表导出
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/report/fleets/import")
    public String queryTcFleetImport(@ModelAttribute CurrUser currUser, ModelMap map,
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

        try {
//            if(order.getPageNum() == null){
                order.setOrderby("deal_date desc");
                order.setPageNum(1);
                order.setPageSize(1048576);
//            }
            order.setDebitAccount(stationId);
            order.setCash(new BigDecimal(BigInteger.ZERO));
            PageInfo<Map<String, Object>> pageInfo = orderService.queryTcFleetReport(order);

            /*生成报表*/
            int cells = 0 ; // 记录条数
            if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
                cells += pageInfo.getList().size();
            }
            OutputStream os = response.getOutputStream();
            ExportUtil reportExcel = new ExportUtil();
            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
            downLoadFileName = "车队消费报表_" + downLoadFileName;
            try {
                response.setHeader("Content-Disposition","attachment;filename=" + new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
            } catch (UnsupportedEncodingException e1) {
                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
            }
            String[][] content = new String[cells+3][13];//[行数][列数]
            //设置表头
            content[0] = new String[]{transName+"车队消费报表"};
            content[2] = new String[]{"订单编号","订单类型","交易类型","车队名称","车牌号","加注站名称",
                    "商品名称","结算单价","消费数量","消费金额","交易时间","备注"};
            //设置列宽
            String [] wcell = new String []{"0,26","1,13","2,13","3,13","4,13","5,13","6,13","7,13","8,13","9,13","10,23","11,30"};
            //合并第一行单元格
            String [] mergeinfo = new String []{"0,0,11,0","1,1,11,1"};
            //设置表名
            String sheetName = "车队消费报表";
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
                    String isDischarge = "";
                    if(quotaMap.get("is_discharge") != null){
                        if(quotaMap.get("is_discharge").toString().equals("0")){
                            isDischarge = "消费";
                        }else if(quotaMap.get("is_discharge").toString().equals("1")){
                            isDischarge = "冲红";
                        }
                    }
                    String orderType = "";
                    if(quotaMap.get("orderType") != null){
                        orderType = quotaMap.get("orderType").toString();
                        orderType = GlobalConstant.getOrderType(orderType);
                    }
                    String fleetName = "";
                    if(quotaMap.get("fleet_name") != null){
                        fleetName = quotaMap.get("fleet_name").toString();
                    }else{
                        fleetName = "其他";
                    }
                    String platesNumber = "";
                    if(quotaMap.get("plates_number") != null){
                        platesNumber = quotaMap.get("plates_number").toString();
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
                    }else{
                        price = "0.00";
                    }
                    String number = "";
                    if(quotaMap.get("number") != null){
                        number = quotaMap.get("number").toString();
                    }else{
                        number = "0.00";
                    }
                    String sumPrice = "";
                    if(quotaMap.get("sumPrice") != null){
                        if(quotaMap.get("is_discharge") != null && quotaMap.get("is_discharge").toString().equals("0")){
                            sumPrice = quotaMap.get("sumPrice").toString();
                        }else{
                            sumPrice = "-"+quotaMap.get("sumPrice").toString();
                        }
                    }else{
                        sumPrice = "0.00";
                    }

                    String dealDate = "";
                    if(quotaMap.get("deal_date") != null){
                        dealDate = quotaMap.get("deal_date").toString();
                    }
                    String remark = "";
                    if(quotaMap.get("remark") != null){
                        remark = quotaMap.get("remark").toString();
                    }
                    content[i] = new String[]{orderNumber,orderType,isDischarge,fleetName,platesNumber,gasStationName,goodsType,price,number,sumPrice,dealDate,remark};
                    i++;
                }
            }
            //合计交易金额和返现金额
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[1] = new String[]{"合计："+totalCash.toString(),"导出时间："+sdf.format(new Date())};
            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, wcell, 0, null, 0, font, null, false);

            //累计总划款金额
            map.addAttribute("totalCash",totalCash);

            map.addAttribute("ret", bean);
            map.addAttribute("pageInfo", pageInfo);
            map.addAttribute("order",order);
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }
        return null;
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
            if(order.getConvertPageNum() != null){
    			if(order.getConvertPageNum() > order.getPageNumMax()){
    				order.setPageNum(order.getPageNumMax());
    			}else if(order.getConvertPageNum() < 1){
    				order.setPageNum(1);
    			}else{
    				order.setPageNum(order.getConvertPageNum());
    			}
    		}
            order.setDebitAccount(stationId);
            order.setCash(new BigDecimal(BigInteger.ZERO));
            PageInfo<Map<String, Object>> pageinfo = orderService.queryTcFleetMgReport(order);

            List<Map<String, Object>> list = orderService.queryTcFleetMgList(order);
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
     * 运输公司队内管理消费报表导出
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/report/fleetMg/import")
    public String queryTcFleetMgImport(@ModelAttribute CurrUser currUser, ModelMap map,
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

        try {
//            if(order.getPageNum() == null){
                order.setOrderby("deal_date desc");
                order.setPageNum(1);
                order.setPageSize(1048576);
//            }
            order.setDebitAccount(stationId);
            order.setCash(new BigDecimal(BigInteger.ZERO));
            PageInfo<Map<String, Object>> pageInfo = orderService.queryTcFleetMgReport(order);

            /*生成报表*/
            int cells = 0 ; // 记录条数
            if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
                cells += pageInfo.getList().size();
            }
            OutputStream os = response.getOutputStream();
            ExportUtil reportExcel = new ExportUtil();
            String downLoadFileName = DateTimeHelper.formatDateTimetoString(new Date(),DateTimeHelper.FMT_yyyyMMdd_noseparator) + ".xls";
            downLoadFileName = "队内管理消费报表_" + downLoadFileName;
            try {
                response.setHeader("Content-Disposition","attachment;filename=" + new String(downLoadFileName.getBytes("GB2312"),"ISO-8859-1"));
            } catch (UnsupportedEncodingException e1) {
                response.setHeader("Content-Disposition","attachment;filename=" + downLoadFileName);
            }
            String[][] content = new String[cells+3][13];//[行数][列数]
            //设置表头
            content[0] = new String[]{transName+"队内管理消费报表"};
            content[2] = new String[]{"订单编号","订单类型","交易类型","车队名称","车牌号","加注站名称",
                    "商品名称","结算单价","消费数量","消费金额","交易时间","备注"};
            //设置列宽
            String [] wcell = new String []{"0,26","1,13","2,13","3,13","4,13","5,13","6,13","7,13","8,13","9,13","10,23","11,30"};
            //合并第一行单元格
            String [] mergeinfo = new String []{"0,0,11,0","1,1,11,1"};
            //设置表名
            String sheetName = "队内管理消费报表";
            //设置字体
            String [] font = new String []{"0,15","2,13"};
            /*组装报表*/
            BigDecimal totalCash = new BigDecimal(BigInteger.ZERO);
            int i = 3;

            if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
                for (Map<String, Object> quotaMap:pageInfo.getList()) {
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
                    String isDischarge = "";
                    if(quotaMap.get("is_discharge") != null){
                        if(quotaMap.get("is_discharge").toString().equals("0")){
                            isDischarge = "消费";
                        }else if(quotaMap.get("is_discharge").toString().equals("1")){
                            isDischarge = "冲红";
                        }
                    }
                    String orderType = "";
                    if(quotaMap.get("orderType") != null){
                        orderType = quotaMap.get("orderType").toString();
                        orderType = GlobalConstant.getOrderType(orderType);
                    }
                    String fleetName = "";
                    if(quotaMap.get("fleet_name") != null){
                        fleetName = quotaMap.get("fleet_name").toString();
                    }else{
                        fleetName = "其他";
                    }

                    String platesNumber = "";
                    if(quotaMap.get("plates_number") != null){
                        platesNumber = quotaMap.get("plates_number").toString();
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
                    }else{
                        price = "0.00";
                    }
                    String number = "";
                    if(quotaMap.get("number") != null && !"".equals(quotaMap.get("number"))){
                        number = quotaMap.get("number").toString();

                    }else{
                        number = "0.00";
                    }
                    String sumPrice = "";
                    if(quotaMap.get("sumPrice") != null){
                        if(quotaMap.get("is_discharge") != null && quotaMap.get("is_discharge").toString().equals("0")){
                            sumPrice = quotaMap.get("sumPrice").toString();
                        }else{
                            sumPrice = "-"+quotaMap.get("sumPrice").toString();
                        }
                    }else{
                        sumPrice = "0.00";
                    }

                    String dealDate = "";
                    if(quotaMap.get("deal_date") != null){
                        dealDate = quotaMap.get("deal_date").toString();
                    }
                    String remark = "";
                    if(quotaMap.get("remark") != null){
                        remark = quotaMap.get("remark").toString();
                    }
                    content[i] = new String[]{orderNumber,orderType,isDischarge,fleetName,platesNumber,gasStationName,goodsType,price,number,sumPrice,dealDate,remark};
                    i++;
                }
            }
            //合计交易金额和返现金额
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[1] = new String[]{"合计："+totalCash.toString(),"导出时间："+sdf.format(new Date())};
            reportExcel.exportFormatExcel(content, sheetName, mergeinfo, os, wcell, 0, null, 0, font, null, false);

            //累计总划款金额
            map.addAttribute("totalCash",totalCash);
            map.addAttribute("pageInfo", pageInfo);
            map.addAttribute("order",order);
        } catch (Exception e) {
            map.addAttribute("ret", bean);
            logger.error("", e);
            throw e;
        }
        return null;
    }
}
