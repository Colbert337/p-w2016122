package com.sysongy.tcms.advance.controller;

import com.alipay.util.httpClient.HttpRequest;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.poms.system.model.SysOperationLog;
import com.sysongy.poms.system.service.SysOperationLogService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.tcms.advance.model.TcFleet;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.model.TcVehicleCard;
import com.sysongy.tcms.advance.service.TcVehicleCardService;
import com.sysongy.tcms.advance.service.TcVehicleService;
import com.sysongy.util.*;
import com.sysongy.util.pojo.AliShortMessageBean;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @FileName: TcVehicleController
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月21日, 18:49
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Controller
@RequestMapping("/web/tcms/vehicle")
public class TcVehicleController extends BaseContoller {
    @Autowired
    TcVehicleService tcVehicleService;
    @Autowired
    GasCardService gasCardService;
    @Autowired
    RedisClientInterface redisClientImpl;
    @Autowired
    GasCardService cardService;
    @Autowired
    TcVehicleCardService tcVehicleCardService;
    @Autowired
    TransportionService transportionService;
	@Autowired
	SysOperationLogService sysOperationLogService;

    /**
     * 查询车辆列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryVehicleListPage(@ModelAttribute CurrUser currUser, TcVehicle vehicle,
                                       @RequestParam(required = false) String resultInt, ModelMap map) throws Exception{
        String stationId = currUser.getStationId();
        if(vehicle.getPageNum() == null){
            vehicle.setPageNum(GlobalConstant.PAGE_NUM);
            vehicle.setPageSize(GlobalConstant.PAGE_SIZE);
        }
        if(vehicle.getConvertPageNum() != null){
			if(vehicle.getConvertPageNum() > vehicle.getPageNumMax()){
				vehicle.setPageNum(vehicle.getPageNumMax());
			}else{
				vehicle.setPageNum(vehicle.getConvertPageNum());
			}
		}
        vehicle.setStationId(stationId);

        //封装分页参数，用于查询分页内容
        PageInfo<Map<String, Object>> vehiclePageInfo = new PageInfo<Map<String, Object>>();
        try {
            vehiclePageInfo = tcVehicleService.queryVehicleMapList(vehicle);
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<>();
        if(resultInt != null && !"".equals(resultInt)){
            resultInt = Decoder.symmetricDecrypto(resultInt);
            resultMap.put("retMsg",resultInt);
        }

        map.addAttribute("vehicleList",vehiclePageInfo.getList());
        map.addAttribute("pageInfo",vehiclePageInfo);
        map.addAttribute("vehicle",vehicle);
        map.addAttribute("ret",resultMap);

        return "webpage/tcms/advance/vehicle_list";
    }

    /**
     * 查询车辆信息
     * @param vehicle
     * @param map
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public Map<String, Object> queryVehicle(TcVehicle vehicle, ModelMap map){
        Map<String, Object> vehicleMap = new HashMap<>();
        GasCard gasCard = new GasCard();
        TcVehicle tcVehicle = tcVehicleService.queryVehicle(vehicle);

        vehicleMap.put("vehicle",tcVehicle);
        if(tcVehicle != null && tcVehicle.getCardNo() != null ){
            try {
                gasCard = gasCardService.queryGasCardInfo(tcVehicle.getCardNo());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        vehicleMap.put("gasCard",gasCard);
        return vehicleMap;
    }

    /**
     * 根据用户名称查询车队信息
     * @param vehicle
     * @param map
     * @return
     */
    @RequestMapping("/info/name")
    @ResponseBody
    public JSONObject queryFleetByName(@ModelAttribute CurrUser currUser, TcVehicle vehicle, ModelMap map){
        JSONObject json = new JSONObject();
        String stationId = currUser.getStationId();
        String platesNumber = "";
        if(vehicle != null && vehicle.getPlatesNumber() != null && !"".equals(vehicle.getPlatesNumber())){

            if(vehicle.getTcVehicleId() != null && !"".equals(vehicle.getTcVehicleId())){
                TcVehicle vh = new TcVehicle();
                vh.setTcVehicleId(vehicle.getTcVehicleId());
                TcVehicle veh = tcVehicleService.queryVehicle(vh);
                vehicle.setUserName(veh.getPlatesNumber());
            }

            platesNumber = vehicle.getPlatesNumber().trim();
            vehicle.setStationId(stationId);
            vehicle.setPlatesNumber(platesNumber);
            List<TcVehicle> vehicleTemp = tcVehicleService.queryVehicleByNumber(vehicle);

            if(vehicleTemp == null || vehicleTemp.size() == 0){
                json.put("valid",true);
            }else{
                json.put("valid",false);
            }
        }else{
            json.put("valid",true);
        }

        return json;
    }
    
    @RequestMapping("/changeCard")
    @ResponseBody
    public String changeCard(@RequestParam String tc_vehicle_id, @RequestParam String newcardno,HttpServletRequest request) throws Exception{
        String ret = "";
		HttpSession session = request.getSession(true);
		CurrUser currUser = (CurrUser) session.getAttribute("currUser");// 登录人角色
		if (null == currUser) {
			currUser = new CurrUser();
		}
    	try{
        	ret = tcVehicleService.updateAndchangeCard(tc_vehicle_id, newcardno).toString();
    		//系统关键日志记录
    		SysOperationLog sysOperationLog = new SysOperationLog();
    		sysOperationLog.setOperation_type("bhk");
    		sysOperationLog.setLog_platform("4");
    		sysOperationLog.setLog_content("网站用户补换卡成功！车辆编号为："+tc_vehicle_id+"，新编号为："+newcardno); 
    		//操作日志
    		sysOperationLogService.saveOperationLog(sysOperationLog,currUser.getUserId());
        }catch(Exception e){
        	ret = e.getMessage();
        	logger.error("",e);
        	throw e;
        }finally{
        	return ret;
        }
    }

    /**
     * 冻结卡
     * @param currUser
     * @param map
     * @return
     */
    @RequestMapping("/update/freeze")
    public String updateFreeze(@ModelAttribute("currUser") CurrUser currUser, GasCard gasCard, ModelMap map)throws Exception{
        GasCard gasCardTemp = new GasCard();
        gasCardTemp = gasCardService.selectByCardNoForCRM(gasCard.getCard_no());
        gasCardTemp.setCard_status(gasCard.getCard_status());

        cardService.updateGasCardInfo(gasCardTemp);
        map.addAttribute("gasCard",gasCardTemp);
        List<TcVehicle> vehicleList = tcVehicleService.queryVehicleByCardNo(gasCard.getCard_no());
        String noticePhone = "";
        if(vehicleList != null && vehicleList.size() > 0){
            noticePhone = vehicleList.get(0).getNoticePhone();
        }

        //发送短信
        AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
        SimpleDateFormat sfm = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String time = sfm.format(new Date());
        aliShortMessageBean.setTime(time);
        aliShortMessageBean.setSendNumber(noticePhone);
        aliShortMessageBean.setString("会员卡");
        aliShortMessageBean.setCode(gasCard.getCard_no());
        if(gasCard.getCard_status().equals(GlobalConstant.CardStatus.PAUSED)){
            //卡冻结
            sendMsgApi(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.CARD_FROZEN);
    		//系统关键日志记录
    		SysOperationLog sysOperationLog = new SysOperationLog();
    		sysOperationLog.setOperation_type("dj");
    		sysOperationLog.setLog_platform("4");
    		sysOperationLog.setLog_content("网站用户冻结卡成功！实体卡编号为："+gasCard.getCard_no());
			//操作日志
			sysOperationLogService.saveOperationLog(sysOperationLog,currUser.getUserId());
        }else{
            //卡解冻
            sendMsgApi(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.CARD_THAW);
    		//系统关键日志记录
    		SysOperationLog sysOperationLog = new SysOperationLog();
    		sysOperationLog.setOperation_type("jd");
    		sysOperationLog.setLog_platform("4");
    		sysOperationLog.setLog_content("网站用户解冻卡成功！实体卡编号为："+gasCard.getCard_no());
			//操作日志
			sysOperationLogService.saveOperationLog(sysOperationLog,currUser.getUserId());
        }
        return "redirect:/web/tcms/vehicle/list/page";
    }

    /**
     * 添加车辆
     * @param currUser 当前用户
     * @param vehicle 车辆
     * @param map
     * @return
     */
    @RequestMapping("/save")
    public String saveVehicle(@ModelAttribute("currUser") CurrUser currUser, TcVehicle vehicle, ModelMap map) throws Exception{
        String stationId = currUser.getStationId();
        String payCode = "";
        String resultInt = "";
        int count = 0;
        Transportion transportion = transportionService.queryTransportionByPK(stationId);
        if(vehicle.getTcVehicleId() != null && vehicle.getTcVehicleId() != ""){
            TcVehicle tcVehicle = new TcVehicle();
            tcVehicle = tcVehicleService.queryVehicle(vehicle);
            //判断车牌号是否重复
            TcVehicle vehicle1Update = new TcVehicle();
            vehicle1Update.setStationId(stationId);
            vehicle1Update.setPlatesNumber(vehicle.getPlatesNumber());
            vehicle1Update.setUserName(tcVehicle.getPlatesNumber());
            List<TcVehicle> vehicle1Count = tcVehicleService.queryVehicleByNumber(vehicle1Update);

            if(vehicle1Count != null && vehicle1Count.size() > 0){
                resultInt = "车牌号已经存在！";
                resultInt = Encoder.symmetricEncrypto(resultInt);
                return "redirect:/web/tcms/vehicle/list/page?resultInt="+resultInt;
            }else{
                if(tcVehicle != null){
                    //新密码
                    String password = vehicle.getPayCode();
                    payCode = password;
                    password = Encoder.MD5Encode(password.getBytes());
                    //新密码和原始密码不一致，则修改密码
                    if(!password.equals(tcVehicle.getPayCode())){
                        vehicle.setPayCode(password);
                    }else{
                        vehicle.setPayCode(null);
                    }
                }

                tcVehicleService.updateVehicle(vehicle);
                resultInt = "修改成功！";
                resultInt = Encoder.symmetricEncrypto(resultInt);
            }

        }else{
            //判断车牌号是否重复
            TcVehicle vehicle1Add = new TcVehicle();
            vehicle1Add.setStationId(stationId);
            vehicle1Add.setPlatesNumber(vehicle.getPlatesNumber());
            List<TcVehicle> vehicle1Count = tcVehicleService.queryVehicleByNumber(vehicle1Add);
            if(vehicle1Count != null && vehicle1Count.size() > 0){
                resultInt = "车牌号已存在！";
                resultInt = Encoder.symmetricEncrypto(resultInt);
                return "redirect:/web/tcms/vehicle/list/page?resultInt="+resultInt;
            }else{
                String newid;
                TcVehicle tcVehicleTemp = tcVehicleService.queryMaxIndex(transportion.getSys_transportion_id());
                if(tcVehicleTemp == null || StringUtils.isEmpty(tcVehicleTemp.getTcVehicleId())){
                    newid = stationId + "0001";
                }else{
                    Integer tmp = Integer.valueOf(tcVehicleTemp.getTcVehicleId().substring(11, 14)) + 1;
                    newid = stationId + StringUtils.leftPad(tmp.toString() , 4, "0");
                }

                vehicle.setStationId(stationId);
                vehicle.setTcVehicleId(newid);
                payCode = vehicle.getPayCode();
                vehicle.setPayCode(Encoder.MD5Encode(payCode.getBytes()));
                vehicle.setIsDeleted(GlobalConstant.STATUS_NOTDELETE+"");

                try {
                    tcVehicleService.addVehicle(vehicle);
                    resultInt = "添加成功！";
                    resultInt = Encoder.symmetricEncrypto(resultInt);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        if(count <= 0){
            String msgType = "user_register";
            AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
            aliShortMessageBean.setCode(payCode);
            aliShortMessageBean.setLicense(vehicle.getPlatesNumber());
            aliShortMessageBean.setString(transportion.getTransportion_name());
            //给通知手机发送短信
            if(vehicle != null && vehicle.getNoticePhone() != null && !vehicle.getNoticePhone().equals("")){
                aliShortMessageBean.setSendNumber(vehicle.getNoticePhone());
                sendMsgApi(vehicle.getNoticePhone(),msgType,aliShortMessageBean);
            }

            //给抄送手机发送短信
            if(vehicle != null && vehicle.getCopyPhone() != null && !vehicle.getCopyPhone().equals("")){
                aliShortMessageBean.setSendNumber(vehicle.getCopyPhone());
                sendMsgApi(vehicle.getCopyPhone(),msgType,aliShortMessageBean);
            }
        }

        return "redirect:/web/tcms/vehicle/list/page?resultInt="+resultInt;
    }

    /**
     * 导入车辆信息
     * @param file
     * @param request
     * @param currUser
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping("/info/file")
    public String importFile(@RequestParam(value = "fileImport") MultipartFile file ,HttpServletRequest request,HttpServletResponse hsr,@ModelAttribute("currUser") CurrUser currUser, ModelMap map) throws IOException{
        String resultStr = "导入成功";
        String resultPath = "redirect:/web/tcms/vehicle/list/page?resultInt=";
        //记录导入数据个数
        int sum = 0;
        //提示消息
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        String stationId = currUser.getStationId();
        //获取参数   参数有 schoolId   gradeId   classId
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        List<TcVehicle> vehicleList = new ArrayList<>();
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        String schoolId=request.getParameter("schoolId");
        String gradeId=request.getParameter("gradeId");
        String classId=request.getParameter("classId");
        try {
            if(file != null && !"".equals(file)){

                Workbook book = Workbook.getWorkbook(file.getInputStream());
                Sheet sheet = book.getSheet(0);
                // 一共有多少行多少列数据
                int rows = sheet.getRows();
                int columns = sheet.getColumns();

                Transportion transportion = transportionService.queryTransportionByPK(stationId);
                TcVehicle tcVehicleTemp = tcVehicleService.queryMaxIndex(transportion.getSys_transportion_id());
                Integer tempVal = 1;
                //车牌号存储
                Map<String, Object> platesNumberMap = new HashMap<>();
                //卡号存储
                Map<String, Object> cardNoMap = new HashMap<>();
                for (int i = 1; i < rows; i++) {
                    //第一个是列数，第二个是行数
                    Integer gender = 0;
                    String platesNumber = "";//默认最左边编号也算一列 所以这里得
                    String cardNo = "";
                    String payCode = "";
                    String noticePhone = "";
                    String copyPhone = "";
                    if(sheet.getCell(0, i) != null && !"".equals(sheet.getCell(0, i).getContents())){
                        TcVehicle tcVehicle = new TcVehicle();
                        String newid;
                        /*当添加第一条数据时*/
                        if(tcVehicleTemp == null || StringUtils.isEmpty(tcVehicleTemp.getTcVehicleId())){
                            newid = stationId + StringUtils.leftPad(tempVal.toString() , 4, "0");
                        }else if(tempVal == 1){
                            Integer tmp = Integer.valueOf(tcVehicleTemp.getTcVehicleId().substring(11, 14)) + 1;
                            tempVal = tmp;
                            newid = stationId + StringUtils.leftPad(tempVal.toString() , 4, "0");
                        }else{//添加非第一条数据
                            newid = stationId + StringUtils.leftPad(tempVal.toString() , 4, "0");
                        }
                        tempVal++;
                        tcVehicle.setTcVehicleId(newid);
                        tcVehicle.setStationId(stationId);
                        platesNumber = sheet.getCell(0, i).getContents().replaceAll(" ", "");
                        tcVehicle.setPlatesNumber(platesNumber);
                        //判断车牌号是否已经存在
                        TcVehicle vehicle1Add = new TcVehicle();
                        vehicle1Add.setStationId(stationId);
                        vehicle1Add.setPlatesNumber(platesNumber);
                        List<TcVehicle> vehicleTemp = tcVehicleService.queryVehicleByNumber(vehicle1Add);
                        if(vehicleTemp != null && vehicleTemp.size() > 0){
                            resultStr = "车牌号"+platesNumber+"已经存在！";
                            break;
                        }
                        //判断车牌号是否在当前excel表中已存在
                        if(platesNumberMap.get(platesNumber) != null && !"".equals(platesNumberMap.get(platesNumber)) ){
                            resultStr = "车牌号"+platesNumber+"在当前EXCEL表中已经存在！";
                            break;
                        }else{
                            platesNumberMap.put(platesNumber,platesNumber);
                        }
                        if(sheet.getCell(1, i) != null && !"".equals(sheet.getCell(1, i).getContents())){
                            cardNo =  sheet.getCell(1, i).getContents().replaceAll(" ", "");
                            tcVehicle.setCardNo(cardNo);
                            //判断卡号是否存在
                            TcVehicle vehicle1Card = new TcVehicle();
                            vehicle1Card.setStationId(stationId);
                            vehicle1Card.setPlatesNumber(cardNo);
                            List<TcVehicle> vehicleCardList = tcVehicleService.queryVehicleByNumber(vehicle1Card);
                            if(vehicleCardList != null && vehicleCardList.size() > 0){
                                resultStr = "卡号"+cardNo+"已经存在！";
                                break;
                            }
                            //判断卡号是否在当前excel表中已存在
                            if(cardNoMap.get(cardNo) != null && !"".equals(cardNoMap.get(cardNo)) ){
                                resultStr = "卡号"+cardNo+"在当前EXCEL表中已经存在！";
                                break;
                            }else{
                                cardNoMap.put(cardNo,cardNo);
                            }
                            //判断卡是否已经出库
                            GasCard gasCard = gasCardService.queryGasCardInfo(cardNo);
                            if(gasCard != null && !GlobalConstant.CardStatus.PROVIDE.equals(gasCard.getCard_status()) && gasCard.getCard_property().equals(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_TRANSPORTION) ){
                                resultStr = "卡号"+cardNo+GlobalConstant.getCardStatus(gasCard.getCard_status());
                                break;
                            }else if(gasCard != null && gasCard.getCard_property().equals(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_DRIVER)){
                                resultStr = "个人卡不能导入！(卡号："+cardNo+")";
                                break;
                            }else if(gasCard == null){
                                resultStr = "卡号"+cardNo+"不存在！";
                                break;
                            }
                        }else{
                            resultStr = "卡号不能为空！";
                            break;
                        }
                        if(sheet.getCell(2, i) != null && !"".equals(sheet.getCell(2, i).getContents())){
                            payCode = sheet.getCell(2, i).getContents().replaceAll(" ", "");
                            tcVehicle.setOnflag(payCode);
                            tcVehicle.setPayCode(Encoder.MD5Encode(payCode.getBytes()));
                        }else{
                            resultStr = "支付密码不能为空！";
                            break;
                        }
                        if(sheet.getCell(3, i) != null && !"".equals(sheet.getCell(3, i).getContents())){
                            noticePhone = sheet.getCell(3, i).getContents().replaceAll(" ", "");
                            tcVehicle.setNoticePhone(noticePhone);
                        }else{
                            resultStr = "通知手机不能为空！";
                            break;
                        }

                        if(sheet.getCell(4, i) != null && !"".equals(sheet.getCell(1, i).getContents())){
                            copyPhone = sheet.getCell(4, i).getContents().replaceAll(" ", "");
                            tcVehicle.setCopyPhone(copyPhone);
                        }
                        logger.info("正在导入车辆数据》》》》》》》》》》》》》");
                        tcVehicle.setIsAllot(0);//是否分配 0 不分配 1 分配
                        vehicleList.add(tcVehicle);
                    }else{
                        resultStr = "车牌号不能为空！";
                        break;
                    }
                }
                //添加车辆
                if(vehicleList != null && vehicleList.size() > 0){
                    tcVehicleService.addVehicleList(vehicleList);
                    /*修改卡状态*/
                    for(int i = 0 ; i < vehicleList.size() ; i++){
                    	sum = sum+1;
                        GasCard gasCard = new GasCard();
                        gasCard.setCard_no(vehicleList.get(i).getCardNo());
                        gasCard.setCard_status(GlobalConstant.CardStatus.USED);
                        gasCardService.updateByPrimaryKeySelective(gasCard);
                        //给通知手机和抄送手机发送短信
                        String msgType = "user_register";
                        AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
                        aliShortMessageBean.setCode(vehicleList.get(i).getOnflag());
                        vehicleList.get(i).setOnflag(null);
                        aliShortMessageBean.setLicense(vehicleList.get(i).getPlatesNumber());
                        aliShortMessageBean.setString(transportion.getTransportion_name());
                        //给通知手机发送短信
                        if(vehicleList.get(i) != null && vehicleList.get(i).getNoticePhone() != null && !vehicleList.get(i).getNoticePhone().equals("")){
                            aliShortMessageBean.setSendNumber(vehicleList.get(i).getNoticePhone());
                            sendMsgApi(vehicleList.get(i).getNoticePhone(),msgType,aliShortMessageBean);
                        }
                        //给抄送手机发送短信
                        if(vehicleList.get(i) != null && vehicleList.get(i).getCopyPhone() != null && !vehicleList.get(i).getCopyPhone().equals("")){
                            aliShortMessageBean.setSendNumber(vehicleList.get(i).getCopyPhone());
                            sendMsgApi(vehicleList.get(i).getCopyPhone(),msgType,aliShortMessageBean);
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	hsr.setContentType("text/xml; charset=utf-8");
        	hsr.setHeader("Content-type", "text/html;charset=UTF-8");
			hsr.getWriter().write("{\"msg\":\""+resultStr+"\",\"sum\":\""+sum+"\"}");
			hsr.getWriter().close();
			return null;
		}
    }



    /**
     * 发送短信
     * @param mobilePhone
     * @param msgType
     * @return
     */
    public AjaxJson sendMsgApi(String mobilePhone,String msgType, AliShortMessageBean aliShortMessageBean){
        AjaxJson ajaxJson = new AjaxJson();

        if(!StringUtils.isNotEmpty(mobilePhone)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("手机号为空！！！");
            return ajaxJson;
        }

        try
        {
            AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.VEHICLE_CREATED);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_SEND_MSG_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 发送短信
     * @param aliShortMessageBean
     * @return
     */
    public AjaxJson sendMsgApi(AliShortMessageBean aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE msgType){
        AjaxJson ajaxJson = new AjaxJson();

        if(!StringUtils.isNotEmpty(aliShortMessageBean.getSendNumber())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("手机号为空！！！");
            return ajaxJson;
        }

        try
        {
            AliShortMessage.sendShortMessage(aliShortMessageBean, msgType);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_SEND_MSG_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }
    /**
     * 修改密码
     * @param tcVehicleId
     * @param payCode
     * @return
     */
    @RequestMapping("changePayCode")
    public String changePayCode(String tcVehicleId,String payCode){
    	String msg;
        TcVehicle tcVehicle = new TcVehicle();
        tcVehicle.setTcVehicleId(tcVehicleId);
        tcVehicle.setPayCode(payCode);
        int rs = tcVehicleService.updateVehicle(tcVehicle);
        if(rs >0 ){
        	msg="修改成功";
        }else{
        	msg="修改失败";
        }
        return "redirect:/web/tcms/vehicle/list/page?msg="+msg;
    }

}
