package com.sysongy.tcms.advance.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String changeCard(@RequestParam String tc_vehicle_id, @RequestParam String newcardno) throws Exception{
        String ret = "";
    	try{
        	ret = tcVehicleService.updateAndchangeCard(tc_vehicle_id, newcardno).toString();
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

            if(vehicle1Count != null){
                resultInt = "车牌号已经存在！";
                resultInt = Encoder.symmetricEncrypto(resultInt);
                return "redirect:/web/tcms/vehicle/list/page?resultInt="+resultInt;
            }else{
                if(tcVehicle != null && vehicle1Count.size() > 0){
                    //新密码
                    String password = vehicle.getPayCode();
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
     */
    @RequestMapping("/info/file")
    public String importFile(@RequestParam(value = "fileImport") MultipartFile file ,HttpServletRequest request,@ModelAttribute("currUser") CurrUser currUser, ModelMap map){
        String resultStr = "";
        String resultPath = "redirect:/web/tcms/vehicle/list/page?resultInt=";

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
                            resultStr = Encoder.symmetricEncrypto(resultStr);
                            return resultPath+resultStr;
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
                                resultStr = Encoder.symmetricEncrypto(resultStr);
                                return resultPath+resultStr;
                            }
                            //判断卡是否已经出库
                            GasCard gasCard = gasCardService.queryGasCardInfo(cardNo);
                            if(gasCard != null && !GlobalConstant.CardStatus.PROVIDE.equals(gasCard.getCard_status()) && gasCard.getCard_property().equals(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_TRANSPORTION) ){
                                resultStr = "卡号"+cardNo+GlobalConstant.getCardStatus(gasCard.getCard_status());
                                resultStr = Encoder.symmetricEncrypto(resultStr);
                                return resultPath+resultStr;
                            }else if(gasCard != null && gasCard.getCard_property().equals(GlobalConstant.CARD_PROPERTY.CARD_PROPERTY_DRIVER)){
                                resultStr = "个人卡不能导入！";
                                resultStr = Encoder.symmetricEncrypto(resultStr);
                                return resultPath+resultStr;
                            }else if(gasCard == null){
                                resultStr = "卡号"+cardNo+"不存在！";
                                resultStr = Encoder.symmetricEncrypto(resultStr);
                                return resultPath+resultStr;
                            }

                        }else{
                            resultStr = "卡号不能为空！";
                            resultStr = Encoder.symmetricEncrypto(resultStr);
                            return resultPath+resultStr;
                        }

                        if(sheet.getCell(2, i) != null && !"".equals(sheet.getCell(2, i).getContents())){
                            payCode = sheet.getCell(2, i).getContents().replaceAll(" ", "");
                            tcVehicle.setPayCode(Encoder.MD5Encode(payCode.getBytes()));
                        }else{
                            resultStr = "支付密码不能为空！";
                            resultStr = Encoder.symmetricEncrypto(resultStr);
                            return resultPath+resultStr;
                        }

                        if(sheet.getCell(3, i) != null && !"".equals(sheet.getCell(3, i).getContents())){
                            noticePhone = sheet.getCell(3, i).getContents().replaceAll(" ", "");
                            tcVehicle.setNoticePhone(noticePhone);
                        }else{
                            resultStr = "通知手机不能为空！";
                            resultStr = Encoder.symmetricEncrypto(resultStr);
                            return resultPath+resultStr;
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
                        resultStr = Encoder.symmetricEncrypto(resultStr);
                        return resultPath+resultStr;
                    }

                }
                //添加车辆
                if(vehicleList != null && vehicleList.size() > 0){
                    tcVehicleService.addVehicleList(vehicleList);
                    /*修改卡状态*/
                    for(TcVehicle tcVehicle:vehicleList){
                        GasCard gasCard = new GasCard();
                        gasCard.setCard_no(tcVehicle.getCardNo());
                        gasCard.setCard_status(GlobalConstant.CardStatus.USED);
                        gasCardService.updateByPrimaryKeySelective(gasCard);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultPath;
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



}
