package com.sysongy.tcms.advance.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
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


    /**
     * 查询车辆列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryVehicleListPage(@ModelAttribute CurrUser currUser, TcVehicle vehicle, ModelMap map){
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

        map.addAttribute("vehicleList",vehiclePageInfo.getList());
        map.addAttribute("pageInfo",vehiclePageInfo);
        map.addAttribute("vehicle",vehicle);

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
        TcVehicleCard tcVehicleCardTemp = new TcVehicleCard();
        tcVehicleCardTemp.setTcVehicleId(tcVehicle.getTcVehicleId());
        tcVehicleCardTemp.setStationId(tcVehicle.getStationId());

        TcVehicleCard tcVehicleCard = tcVehicleCardService.queryTcVehicleCardByVecId(tcVehicleCardTemp);
        vehicleMap.put("vehicle",tcVehicle);
        if(tcVehicle != null && tcVehicle.getCardNo() != null && tcVehicleCard != null){
            try {
                gasCard = gasCardService.queryGasCardInfo(tcVehicleCard.getCardNo());
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
            platesNumber = vehicle.getPlatesNumber().trim();
            vehicle.setStationId(stationId);
            vehicle.setPlatesNumber(platesNumber);
            TcVehicle vehicleTemp = tcVehicleService.queryVehicleByNumber(vehicle);

            if(vehicleTemp == null){
                json.put("valid",true);
            }else{
                json.put("valid",false);
            }
        }else{
            json.put("valid",false);
        }

        return json;
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
    public String saveVehicle(@ModelAttribute("currUser") CurrUser currUser, TcVehicle vehicle, ModelMap map){

        if(vehicle.getTcVehicleId() != null && vehicle.getTcVehicleId() != ""){
            TcVehicle tcVehicle = new TcVehicle();
            tcVehicle = tcVehicleService.queryVehicle(vehicle);
            if(tcVehicle != null){
                //新密码
                String password = vehicle.getPayCode();
                password = Encoder.MD5Encode(password.getBytes());
                //新密码何原始密码不一致，则修改密码
                if(!password.equals(tcVehicle.getPayCode())){
                    vehicle.setPayCode(password);
                }
            }

            tcVehicleService.updateVehicle(vehicle);
        }else{
            String stationId = currUser.getStationId();
            vehicle.setStationId(stationId);
            vehicle.setTcVehicleId(UUIDGenerator.getUUID());
            String payCode = vehicle.getPayCode();
            vehicle.setPayCode(Encoder.MD5Encode(payCode.getBytes()));
            vehicle.setIsDeleted(GlobalConstant.STATUS_NOTDELETE+"");

            try {
                tcVehicleService.addVehicle(vehicle);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String msgType = "user_register";
        //给通知手机发送短信
        if(vehicle != null && vehicle.getNoticePhone() != null && !vehicle.getNoticePhone().equals("")){
            sendMsgApi(vehicle.getNoticePhone(),msgType);
        }

        //给抄送手机发送短信
        if(vehicle != null && vehicle.getCopyPhone() != null && !vehicle.getCopyPhone().equals("")){
            sendMsgApi(vehicle.getCopyPhone(),msgType);
        }

        return "redirect:/web/tcms/vehicle/list/page";
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

                for (int i = 1; i < rows; i++) {

                    //第一个是列数，第二个是行数
                    Integer gender = 0;
                    String platesNumber = "";//默认最左边编号也算一列 所以这里得
                    String cardNo = "";
                    String payCode = "";
                    String noticePhone = "";
                    String copyPhone = "";
                    if(sheet.getCell(0, i) != null && !"".equals(sheet.getCell(0, i))){
                        TcVehicle tcVehicle = new TcVehicle();
                        tcVehicle.setTcVehicleId(UUIDGenerator.getUUID());
                        tcVehicle.setPayCode(Encoder.MD5Encode("111111".getBytes()));
                        tcVehicle.setStationId(stationId);

                        platesNumber = sheet.getCell(0, i).getContents().replaceAll(" ", "");
                        tcVehicle.setPlatesNumber(platesNumber);
                        if(sheet.getCell(1, i) != null && !"".equals(sheet.getCell(1, i))){
                            cardNo =  sheet.getCell(1, i).getContents().replaceAll(" ", "");
                            tcVehicle.setCardNo(cardNo);
                        }
                        if(sheet.getCell(1, i) != null && !"".equals(sheet.getCell(1, i))){
                            noticePhone = sheet.getCell(3, i).getContents().replaceAll(" ", "");
                            tcVehicle.setNoticePhone(noticePhone);
                        }
                        if(sheet.getCell(1, i) != null && !"".equals(sheet.getCell(1, i))){
                            copyPhone = sheet.getCell(4, i).getContents().replaceAll(" ", "");
                            tcVehicle.setCopyPhone(copyPhone);
                        }

                        //添加车辆与卡关系
                        TcVehicleCard tcVehicleCard = new TcVehicleCard();
                        tcVehicleCard.setTcVehicleId(tcVehicle.getTcVehicleId());
                        tcVehicleCard.setCardNo(tcVehicle.getCardNo());
                        tcVehicleCard.setTcVehicleCardId(UUIDGenerator.getUUID());
                        tcVehicleCard.setStationId(stationId);
                        tcVehicleService.addVehicleCard(tcVehicleCard);
                        System.out.println("正在导入车辆数据》》》》》》》》》》》》》");

                        tcVehicle.setCardNo(null);
                        vehicleList.add(tcVehicle);
                    }

                }
                //添加车辆及卡关系
                if(vehicleList != null && vehicleList.size() > 0){
                    tcVehicleService.addVehicleList(vehicleList);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/web/tcms/vehicle/list/page";
    }



    /**
     * 发送短信
     * @param mobilePhone
     * @param msgType
     * @return
     */
    public AjaxJson sendMsgApi(@RequestParam(required = false) String mobilePhone, @RequestParam(required = false) String msgType){
        AjaxJson ajaxJson = new AjaxJson();

        if(!StringUtils.isNotEmpty(mobilePhone)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("手机号为空！！！");
            return ajaxJson;
        }

        try
        {
            Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);
            AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
            aliShortMessageBean.setSendNumber(mobilePhone);
            aliShortMessageBean.setCode(checkCode.toString());
            aliShortMessageBean.setProduct("司集能源科技平台");
            String key = GlobalConstant.MSG_PREFIX + mobilePhone;
            redisClientImpl.addToCache(key, checkCode.toString(), 60);

            if(msgType.equalsIgnoreCase("changePassword")){
                AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_CHANGE_PASSWORD);
            } else {
                AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_REGISTER);
            }

        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_SEND_MSG_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }



}
