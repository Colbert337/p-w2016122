package com.sysongy.tcms.advance.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.card.service.GasCardService;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.tcms.advance.service.TcVehicleService;
import com.sysongy.util.AliShortMessage;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.RedisClientInterface;
import com.sysongy.util.UUIDGenerator;
import com.sysongy.util.pojo.AliShortMessageBean;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

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
        vehicleMap.put("vehicle",tcVehicle);
        if(tcVehicle != null && tcVehicle.getCardNo() != null){
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
     * 添加车辆
     * @param currUser 当前用户
     * @param vehicle 车辆
     * @param map
     * @return
     */
    @RequestMapping("/save")
    public String saveVehicle(@ModelAttribute("currUser") CurrUser currUser, TcVehicle vehicle, ModelMap map){
        int userType = currUser.getUser().getUserType();
        int result = 0;

        if(vehicle.getTcVehicleId() != null && vehicle.getTcVehicleId() != ""){
            vehicle.setPayCode(null);
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

    @RequestMapping("/info/file")
    public String importFile(@RequestParam(value = "fileImport") MultipartFile file ,HttpServletRequest request, HttpServletResponse response, ModelMap map){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        //获取参数   参数有 schoolId   gradeId   classId
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
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
                    String childName = sheet.getCell(0, i).getContents().replaceAll(" ", "");//默认最左边编号也算一列 所以这里得
                    if(childName != null && !"".equals(childName)){
                        String classesCode =  sheet.getCell(1, i).getContents();
                        if(sheet.getCell(2, i).getContents() != null && sheet.getCell(2, i).getContents().equals("男")){
                            gender= 0;
                        }else if(sheet.getCell(2, i).getContents() != null && sheet.getCell(2, i).getContents().equals("女")){
                            gender = 1;
                        }

                        //格式化  日期
                        String birthday = "";
                        String birthdaydesc =  sheet.getCell(3, i).getContents();
                        if(birthdaydesc.length() == 10){
                            if(birthdaydesc != null && !"".equals(birthdaydesc)){
                                String years = birthdaydesc.substring(0,4);
                                String month = birthdaydesc.substring(5,7);
                                Integer month2  = Integer.parseInt(month);
                                String day = birthdaydesc.substring(8,10);
                                Integer day2  = Integer.parseInt(day);
                                birthday = years+"-"+(month2>9?month2:"0"+month2)+"-"+(day2>9?day2:"0"+day2);
                            }
                        }

                        String certificate = "";
                        if(sheet.getCell(4, i).getContents().replaceAll(" ", "") != null && !"".equals(sheet.getCell(4, i).getContents().replaceAll(" ", ""))){
                            //根据身份证件类型  查询 所属的类型
                            /*String type = GlobalConstant.DIC_CARD_TYPE;
                            //根据 类型  查出  typeId
                            DicitType dicitType = dicitTypeService.queryDicitTypeByType(type);
                            String typeId = dicitType.getTypeId();
                            String dataName = sheet.getCell(4, i).getContents().replaceAll(" ", "");
                            DicitData dicitData = dicitDataService.queryDicitDataByNameAndType(dataName, typeId);
                            certificate = dicitData.getDataId();*/
                        }

                        String idCard =  sheet.getCell(5, i).getContents();

                        String bloodType = "";
                        if(sheet.getCell(6, i).getContents().replaceAll(" ", "") != null && !"".equals(sheet.getCell(6, i).getContents().replaceAll(" ", ""))){
                            /*String type = GlobalConstant.DIC_BLOOD_TYPE;//血型
                            //根据 类型  查出  typeId
                            DicitType dicitType = dicitTypeService.queryDicitTypeByType(type);
                            String typeId = dicitType.getTypeId();
                            String dataName = sheet.getCell(6, i).getContents().replaceAll(" ", "");
                            DicitData dicitData = dicitDataService.queryDicitDataByNameAndType(dataName, typeId);
                            bloodType = dicitData.getDataId();*/
                        }

                        //国家地区
                        String gjdq =  "";
                        if(sheet.getCell(7, i).getContents().replaceAll(" ", "") != null && !"".equals(sheet.getCell(7, i).getContents().replaceAll(" ", ""))){
                            /*String type = GlobalConstant.DIC_GJDQ;//国家地区
                            //根据 类型  查出  typeId
                            DicitType dicitType = dicitTypeService.queryDicitTypeByType(type);
                            String typeId = dicitType.getTypeId();
                            String dataName = sheet.getCell(7, i).getContents().replaceAll(" ", "");
                            DicitData dicitData = dicitDataService.queryDicitDataByNameAndType(dataName, typeId);
                            gjdq = dicitData.getDataId();*/
                        }

                        //民族
                        String nationality =  "";
                        if(sheet.getCell(8, i).getContents().replaceAll(" ", "") != null && !"".equals(sheet.getCell(8, i).getContents().replaceAll(" ", ""))){
                            /*String type = GlobalConstant.DIC_NATIONALITY;//民族
                            //根据 类型  查出  typeId
                            DicitType dicitType = dicitTypeService.queryDicitTypeByType(type);
                            String typeId = dicitType.getTypeId();
                            String dataName = sheet.getCell(8, i).getContents().replaceAll(" ", "");
                            DicitData dicitData = dicitDataService.queryDicitDataByNameAndType(dataName, typeId);
                            nationality = dicitData.getDataId();*/
                        }

                        //港澳台侨外
                        String gatqw =  "";
                        if(sheet.getCell(9, i).getContents().replaceAll(" ", "") != null && !"".equals(sheet.getCell(9, i).getContents().replaceAll(" ", ""))){
                            /*String type = GlobalConstant.DIC_GATQW;//港澳台侨外
                            //根据 类型  查出  typeId
                            DicitType dicitType = dicitTypeService.queryDicitTypeByType(type);
                            String typeId = dicitType.getTypeId();
                            String dataName = sheet.getCell(9, i).getContents().replaceAll(" ", "");
                            DicitData dicitData = dicitDataService.queryDicitDataByNameAndType(dataName, typeId);
                            gatqw = dicitData.getDataId();*/
                        }


                        String csszd = sheet.getCell(10, i).getContents();//出生所在地
                        String jiguan = sheet.getCell(11, i).getContents();//籍贯

                        String hkxz = "";
                        if(sheet.getCell(12, i).getContents().replaceAll(" ", "") != null && sheet.getCell(12, i).getContents().replaceAll(" ", "").equals("非农业户口")){
                            hkxz= "0";
                        }else if(sheet.getCell(12, i).getContents().replaceAll(" ", "") != null && sheet.getCell(12, i).getContents().replaceAll(" ", "").equals("农业户口")){
                            hkxz = "1";
                        }

                        String fnyhklx =  sheet.getCell(13, i).getContents();
                        if(sheet.getCell(13, i).getContents().replaceAll(" ", "") != null && sheet.getCell(13, i).getContents().replaceAll(" ", "").equals("城市")){
                            fnyhklx= "0";
                        }else if(sheet.getCell(13, i).getContents().replaceAll(" ", "") != null && sheet.getCell(13, i).getContents().replaceAll(" ", "").equals("县城")){
                            fnyhklx = "1";
                        }else if(sheet.getCell(13, i).getContents().replaceAll(" ", "") != null && sheet.getCell(13, i).getContents().replaceAll(" ", "").equals("乡镇")){
                            fnyhklx = "2";
                        }

                        /*String hkszd = sheet.getCell(14, i).getContents();//户口所在地
                        String address =  sheet.getCell(15, i).getContents();
                        Date inDate = new Date();
                        if(sheet.getCell(16, i).getContents() != null && !"".equals(sheet.getCell(16, i).getContents())){
                            String asd = sheet.getCell(16, i).getContents();
                            inDate = sdf.parse(sheet.getCell(16, i).getContents());
                        }*/

                        String jdfs = "";
                        if(sheet.getCell(17, i).getContents().replaceAll(" ", "") != null && sheet.getCell(17, i).getContents().replaceAll(" ", "").equals("走读")){
                            hkxz= "0";
                        }else if(sheet.getCell(17, i).getContents().replaceAll(" ", "") != null && sheet.getCell(17, i).getContents().replaceAll(" ", "").equals("住校")){
                            hkxz = "1";
                        }

                        Integer isOne = 0;
                        if(sheet.getCell(18, i).getContents().replaceAll(" ", "") != null && sheet.getCell(18, i).getContents().replaceAll(" ", "").equals("否")){
                            isOne= 0;
                        }else if(sheet.getCell(18, i).getContents().replaceAll(" ", "") != null && sheet.getCell(18, i).getContents().replaceAll(" ", "").equals("是")){
                            isOne = 1;
                        }

                        Integer isLset = 0;
                        if(sheet.getCell(19, i).getContents().replaceAll(" ", "") != null && sheet.getCell(19, i).getContents().replaceAll(" ", "").equals("非留守儿童")){
                            isLset= 0;
                        }else if(sheet.getCell(19, i).getContents().replaceAll(" ", "") != null && sheet.getCell(19, i).getContents().replaceAll(" ", "").equals("单亲留守儿童")){
                            isLset = 1;
                        }else if(sheet.getCell(19, i).getContents().replaceAll(" ", "") != null && sheet.getCell(19, i).getContents().replaceAll(" ", "").equals("双亲留守儿童")){
                            isLset = 2;
                        }

                        Integer isJcwgr = 0;
                        if(sheet.getCell(20, i).getContents().replaceAll(" ", "") != null && sheet.getCell(20, i).getContents().replaceAll(" ", "").equals("否")){
                            isJcwgr= 0;
                        }else if(sheet.getCell(20, i).getContents().replaceAll(" ", "") != null && sheet.getCell(20, i).getContents().replaceAll(" ", "").equals("是")){
                            isJcwgr = 1;
                        }

                        String healthStatus = "";
                        if(sheet.getCell(21, i).getContents().replaceAll(" ", "") != null && sheet.getCell(21, i).getContents().replaceAll(" ", "").equals("健康或良好")){
                            healthStatus= "0";
                        }else if(sheet.getCell(21, i).getContents().replaceAll(" ", "") != null && sheet.getCell(21, i).getContents().replaceAll(" ", "").equals("一般或较弱")){
                            healthStatus = "1";
                        }else if(sheet.getCell(21, i).getContents().replaceAll(" ", "") != null && sheet.getCell(21, i).getContents().replaceAll(" ", "").equals("有慢性病")){
                            healthStatus = "2";
                        }else if(sheet.getCell(21, i).getContents().replaceAll(" ", "") != null && sheet.getCell(21, i).getContents().replaceAll(" ", "").equals("残疾")){
                            healthStatus = "3";
                        }

                        Integer isDeformity = 0;
                        if(sheet.getCell(22, i).getContents().replaceAll(" ", "") != null && sheet.getCell(22, i).getContents().replaceAll(" ", "").equals("否")){
                            isDeformity= 0;
                        }else if(sheet.getCell(22, i).getContents().replaceAll(" ", "") != null && sheet.getCell(22, i).getContents().replaceAll(" ", "").equals("是")){
                            isDeformity = 1;
                        }


                        //残疾类型
                       /* String deformityType =  "";
                        if(sheet.getCell(23, i).getContents().replaceAll(" ", "") != null && !"".equals(sheet.getCell(23, i).getContents().replaceAll(" ", ""))){
                            String type = GlobalConstant.DIC_DEFORMITY_TYPE;//残疾类型
                            //根据 类型  查出  typeId
                            DicitType dicitType = dicitTypeService.queryDicitTypeByType(type);
                            String typeId = dicitType.getTypeId();
                            String dataName = sheet.getCell(23, i).getContents().replaceAll(" ", "");
                            DicitData dicitData = dicitDataService.queryDicitDataByNameAndType(dataName, typeId);
                            deformityType = dicitData.getDataId();
                        }

                        Integer isOrphan = 0;
                        if(sheet.getCell(24, i).getContents().replaceAll(" ", "") != null && sheet.getCell(24, i).getContents().replaceAll(" ", "").equals("否")){
                            isOrphan= 0;
                        }else if(sheet.getCell(24, i).getContents().replaceAll(" ", "") != null && sheet.getCell(24, i).getContents().replaceAll(" ", "").equals("是")){
                            isOrphan = 1;
                        }

                        String guardianName =  sheet.getCell(25, i).getContents();

                        String guardianCardType =  "";
                        if(sheet.getCell(26, i).getContents().replaceAll(" ", "") != null && !"".equals(sheet.getCell(26, i).getContents())){
                            String type = GlobalConstant.DIC_GUARDIAN_CARD_TYPE;//监护人证件类型
                            //根据 类型  查出  typeId
                            DicitType dicitType = dicitTypeService.queryDicitTypeByType(type);
                            String typeId = dicitType.getTypeId();
                            String dataName = sheet.getCell(26, i).getContents().replaceAll(" ", "");
                            DicitData dicitData = dicitDataService.queryDicitDataByNameAndType(dataName, typeId);
                            guardianCardType = dicitData.getDataId();
                        }*/

                        String guardianIdCard =  "";
                        if(sheet.getCell(27, i) != null){
                            sheet.getCell(27, i).getContents().replaceAll(" ", "");
                        }

                        String archivesNo = "";
                        if(sheet.getCell(28, i) != null){
                            sheet.getCell(28, i).getContents().replaceAll(" ", "");
                        }

                        String baName = "";
                        if(sheet.getCell(29, i) != null){
                            sheet.getCell(29, i).getContents().replaceAll(" ", "");
                        }


                        String baPhone = "";
                        if(sheet.getCell(30, i) != null){
                            sheet.getCell(30, i).getContents().replaceAll(" ", "");
                        }


                        String baAge = "";
                        if(sheet.getCell(31, i) != null){
                            sheet.getCell(31, i).getContents().replaceAll(" ", "");
                        }

                        String maName = "";
                        if(sheet.getCell(32, i) != null){
                            sheet.getCell(32, i).getContents().replaceAll(" ", "");
                        }

                        String maPhone = "";
                        if(sheet.getCell(33, i) != null){
                            sheet.getCell(33, i).getContents().replaceAll(" ", "");
                        }

                        String maAge = "";
                        if(sheet.getCell(34, i) != null){
                            sheet.getCell(34, i).getContents().replaceAll(" ", "");
                        }

                        /*Child child = new Child();
                        ChildExtend childExtend = new ChildExtend();
                        String childId = UUIDGenerator.getUUID();
                        child.setChildId(childId);
                        child.setChildName(childName);
                        child.setGender(gender);
                        child.setBirthday(birthday);
                        child.setCertificate(certificate);
                        child.setIdCard(idCard);
                        child.setAddress(address);
                        child.setInDate(inDate);
                        child.setSystemId(systemId);
                        child.setClassesId(classId);
                        child.setGradeId(gradeId);
                        child.setSchoolId(schoolId);
                        child.setStatus(1);
                        child.setArchivesNo(archivesNo);
                        childService.addChild(child);

                        String childExtendId = UUIDGenerator.getUUID();
                        childExtend.setChildId(childId);
                        childExtend.setSystemId(systemId);
                        childExtend.setChildExtendId(childExtendId);
                        childExtend.setBloodType(bloodType);
                        childExtend.setGjdq(gjdq);
                        childExtend.setNationality(nationality);
                        childExtend.setGatqw(gatqw);
                        childExtend.setHkxz(hkxz);
                        childExtend.setFnyhklx(fnyhklx);
                        childExtend.setJdfs(jdfs);
                        childExtend.setIsOne(isOne);
                        childExtend.setIsLset(isLset);
                        childExtend.setIsJcwgr(isJcwgr);
                        childExtend.setHealthStatus(healthStatus);
                        childExtend.setIsDeformity(isDeformity);
                        childExtend.setDeformityType(deformityType);
                        childExtend.setIsOrphan(isOrphan);
                        childExtend.setGuardianName(guardianName);
                        childExtend.setGuardianCardType(guardianCardType);
                        childExtend.setGuardianIdCard(guardianIdCard);
                        childExtend.setSchoolId(schoolId);
                        childExtendService.addChildExtend(childExtend);

                        //导入幼儿家长信息
                        if(baName != null && !"".equals(baName)){
                            User baUser = new User();
                            String baUserId = UUIDGenerator.getUUID();
                            baUser.setUserId(baUserId);
                            baUser.setSystemId(systemId);
                            baUser.setSchoolId(schoolId);
                            baUser.setUserType(GlobalConstant.USER_PARENT);
                            baUser.setRealName(baName);
                            userService.addUser(baUser);

                            Parent baParent = new Parent();
                            ChildParent baChildParent = new ChildParent();
                            String baParentId = UUIDGenerator.getUUID();
                            baParent.setSystemId(systemId);
                            baParent.setParentId(baParentId);
                            baParent.setUserId(baUserId);
                            baParent.setParentName(baName);
                            baParent.setChildId(childId);
                            baParent.setSchoolId(schoolId);
                            baParent.setDataId(GlobalConstant.DIC_BA_ID);
                            baParent.setDataName(GlobalConstant.DIC_BA_NAME);
                            baParent.setRelation(GlobalConstant.DIC_BA_ID);
                            baParent.setGender(gender);
                            baParent.setMobilePhone(baPhone);
                            if(baAge != null && !baAge.equals("")){
                                baParent.setAge(Integer.parseInt(baAge));
                            }
                            baChildParent.setChildParentId(UUIDGenerator.getUUID());
                            baChildParent.setChildId(childId);
                            baChildParent.setParentId(baUserId);

                            parentService.addParent(baParent);
                            childParentService.addChildParent(baChildParent);
                        }

                        if(maName != null && !"".equals(maName)){
                            User maUser = new User();
                            String maUserId = UUIDGenerator.getUUID();
                            maUser.setUserId(maUserId);
                            maUser.setSystemId(systemId);
                            maUser.setSchoolId(schoolId);
                            maUser.setUserType(GlobalConstant.USER_PARENT);
                            maUser.setRealName(maName);
                            userService.addUser(maUser);

                            Parent maParent = new Parent();
                            ChildParent maChildParent = new ChildParent();
                            String maParentId = UUIDGenerator.getUUID();
                            maParent.setSystemId(systemId);
                            maParent.setParentId(maParentId);
                            maParent.setUserId(maUserId);
                            maParent.setParentName(maName);
                            maParent.setChildId(childId);
                            maParent.setSchoolId(schoolId);
                            maParent.setDataId(GlobalConstant.DIC_BA_ID);
                            maParent.setDataName(GlobalConstant.DIC_BA_NAME);
                            maParent.setRelation(GlobalConstant.DIC_BA_ID);
                            maParent.setGender(gender);
                            maParent.setMobilePhone(maPhone);
                            if(maAge != null && !"".equals(maParent)){
                                maParent.setAge(Integer.parseInt(maAge));
                            }
                            maChildParent.setChildParentId(UUIDGenerator.getUUID());
                            maChildParent.setChildId(childId);
                            maChildParent.setParentId(maUserId);
                            parentService.addParent(maParent);
                            childParentService.addChildParent(maChildParent);
                        }*/

                        System.out.println("正在导入幼儿数据》》》》》》》》》》》》》");
                    }

                }

            }
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "webpage/tcms/advance/vehicle_list";
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

//            String msgType = request.getParameter("msgType");
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
