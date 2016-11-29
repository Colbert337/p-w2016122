package com.sysongy.api.mobile.controller;

import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.util.DESUtil;
import com.sysongy.api.util.DistCnvter;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.service.SysRoadService;
import com.sysongy.poms.mobile.service.impl.SysRoadServiceImpl;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.*;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @FileName: AmapController
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.mobile.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年11月08日, 12:00
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:  高德地图接口调用
 */
@RequestMapping("/api/v1/mobile")
@Controller
public class MapController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
    public final String keyStr = "sysongys";

    public static String  routeAPI = "http://restapi.amap.com/v3/direction/driving?";
    public static String key = "a47ee0175f1d1c119ab53e56ea0ad306";
    public static int step = 0;//路径上坐标过滤级别，step为0，则代表每隔0个坐标取一个点
    public static double radius = 500000;//单位米,默认500公里

    @Autowired
    GastationService gastationService;
    @Autowired
    UsysparamService usysparamService;
    @Autowired
    SysRoadService sysRoadService;
    @Autowired
    RedisClientInterface redisClientImpl;
    /**
     * 获取路径规划方案
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/map/getDirection")
    @ResponseBody
    public String updatePassword(String params) {
        String http_poms_path = (String) prop.get("http_poms_path");
        MobileReturn result = new MobileReturn();
        result.setStatus(MobileReturn.STATUS_SUCCESS);
        result.setMsg("获取规划方案！");
        JSONObject resutObj = new JSONObject();
        String resultStr = "";

        try {
            /**
             * 解析参数
             */
            params = DESUtil.decode(keyStr, params);// 参数解密
            JSONObject paramsObj = JSONObject.fromObject(params);
            JSONObject mainObj = paramsObj.optJSONObject("main");
            /**
             * 必填参数
             */
            String origin = "origin";
            String destination = "destination";
            String strategy = "strategy";
            String output = "json";
            boolean b = JsonTool.checkJson(mainObj, origin, destination, strategy);
            /**
             * 请求接口
             */
            if (b) {
                origin = mainObj.optString("origin");
                destination = mainObj.optString("destination");
                strategy = mainObj.optString("strategy");

                // 获取气站列表
                Gastation gastation = new Gastation();
                List<Gastation> gastationAllList = gastationService.getAllStationList(gastation);
                List<Gastation> gastationList = getDirectionMap(origin, destination, strategy, step, radius, gastationAllList );

                List<Map<String, Object>> gastationArray = new ArrayList<>();
                if (gastationList != null && gastationList.size() > 0) {
                    for (Gastation gastationInfo : gastationList) {
                        Map<String, Object> gastationMap = new HashMap<>();
                        gastationMap.put("stationId", gastationInfo.getSys_gas_station_id());
                        gastationMap.put("name", gastationInfo.getGas_station_name());
                        gastationMap.put("type", gastationInfo.getType());
                        gastationMap.put("longitude", gastationInfo.getLongitude());
                        gastationMap.put("latitude", gastationInfo.getLatitude());
                        Usysparam usysparam = usysparamService.queryUsysparamByCode("STATION_DATA_TYPE",
                                gastationInfo.getType());
                        gastationMap.put("stationType", usysparam.getMname());
                        gastationMap.put("service",
                                gastationInfo.getGas_server() == null ? "暂无" : gastationInfo.getGas_server());// 提供服务
                        gastationMap.put("preferential",
                                gastationInfo.getPromotions() == null ? "暂无" : gastationInfo.getPromotions());// 优惠活动
                        // 获取当前气站价格列表
                        String price = gastationInfo.getLng_price();
                        if (price != null && !"".equals(price)) {
                            price = price.replaceAll("，", ",");
                            price = price.replaceAll("：", ":");
                            if (price.indexOf(":") != -1 && price.indexOf("/") != -1) {
                                String strArray[] = price.split(",");
                                Map[] map = new Map[strArray.length];
                                for (int i = 0; i < strArray.length; i++) {
                                    String strInfo = strArray[i].trim();
                                    String strArray1[] = strInfo.split(":");
                                    String strArray2[] = strArray1[1].split("/");
                                    Map<String, Object> dataMap = new HashMap<>();
                                    dataMap.put("gasName", strArray1[0]);
                                    dataMap.put("gasPrice", strArray2[0]);
                                    dataMap.put("gasUnit", strArray2[1]);
                                    map[i] = dataMap;
                                }
                                gastationMap.put("priceList", map);
                            } else {
                                gastationMap.put("priceList", new ArrayList());
                            }
                        } else {
                            gastationMap.put("priceList", new ArrayList());
                        }
                        gastationMap.put("phone", gastationInfo.getContact_phone());
                        if (gastationInfo.getStatus().equals("0")) {
                            gastationMap.put("state", "开启");
                        } else {
                            gastationMap.put("state", "关闭");
                        }
                        gastationMap.put("address", gastationInfo.getAddress());
                        String infoUrl = http_poms_path + "/portal/crm/help/station?stationId="
                                + gastationInfo.getSys_gas_station_id();
                        gastationMap.put("infoUrl", infoUrl);
                        gastationMap.put("shareUrl", http_poms_path + "/portal/crm/help/share/station?stationId="
                                + gastationInfo.getSys_gas_station_id());
                        gastationArray.add(gastationMap);
                    }
                    result.setListMap(gastationArray);

                } else {
                    result.setStatus(MobileReturn.STATUS_SUCCESS);
                    result.setMsg("暂无数据！");
                    result.setListMap(new ArrayList<Map<String, Object>>());
                }

            } else {
                result.setStatus(MobileReturn.STATUS_FAIL);
                result.setMsg("参数有误！");
            }

            resutObj = JSONObject.fromObject(result);
            resutObj.remove("data");
            resultStr = resutObj.toString();
            logger.error("查询气站信息： " + resultStr);
            resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
        } catch (Exception e) {
            result.setStatus(MobileReturn.STATUS_FAIL);
            result.setMsg("查询气站信息失败！");
            resutObj = JSONObject.fromObject(result);
            logger.error("查询气站信息失败： " + e);
            e.printStackTrace();
            resutObj.remove("data");
            resultStr = resutObj.toString();
            resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
            return resultStr;
        } finally {
            return resultStr;
        }
    }

    /**
     * 获取限高、限重路况列表
     */
    @RequestMapping(value = "/map/getHighWeightRoadList")
    @ResponseBody
    public String getHighWeightRoadList(String params) {
        MobileReturn result = new MobileReturn();
        result.setStatus(MobileReturn.STATUS_SUCCESS);
        result.setMsg("获取限高、限重路况成功！");
        JSONObject resutObj = new JSONObject();
        String resultStr = "";
        try {
            /**
             * 解析参数
             */
            params = DESUtil.decode(keyStr, params);
            JSONObject paramsObj = JSONObject.fromObject(params);
            JSONObject mainObj = paramsObj.optJSONObject("main");
            // 创建对象
            SysRoadCondition roadCondition = new SysRoadCondition();
            boolean b = false;
            if(mainObj != null && !"".equals(mainObj)){
                b=true;
            }
            /**
             * 请求接口
             */
            if (b) {

                List<SysRoadCondition> redisList = sysRoadService.queryHighWeightRoadId();

                List<Map<String, Object>> reChargeList = new ArrayList<>();
                Map<String, Object> reCharge = new HashMap<>();
                SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String http_poms_path = (String) prop.get("http_poms_path");
                if (redisList != null && redisList.size() > 0) {
                    for (SysRoadCondition roadConditionInfo : redisList) {
                        Map<String, Object> reChargeMap = new HashMap<>();
                        reChargeMap.put("roadId", roadConditionInfo.getId());
                        reChargeMap.put("conditionType", roadConditionInfo.getConditionType());
                        reChargeMap.put("longitude", roadConditionInfo.getLongitude());
                        reChargeMap.put("latitude", roadConditionInfo.getLatitude());
                        String url = roadConditionInfo.getConditionImg();
                        //没有上传图片添加默认图片
                        if(url==null || "".equals(url)){
                            url = prop.get("default_img").toString();
                        }
                        reChargeMap.put("conditionImg", http_poms_path + url);
                        reChargeMap.put("address", roadConditionInfo.getAddress());
                        reChargeMap.put("publisherName", roadConditionInfo.getPublisherName());
                        reChargeMap.put("publisherPhone", roadConditionInfo.getPublisherPhone());
                        reChargeMap.put("direction", roadConditionInfo.getDirection());
                        reChargeMap.put("conditionMsg", roadConditionInfo.getConditionMsg());
                        reChargeMap.put("usefulCount", roadConditionInfo.getUsefulCount());
                        reChargeMap.put("contentUrl", http_poms_path + "/portal/crm/help/trafficDetail?trafficId="
                                + roadConditionInfo.getId());
                        reChargeMap.put("shareUrl", http_poms_path + "/portal/crm/help/trafficShare?trafficId="
                                + roadConditionInfo.getId());
                        String publisherTime = "";
                        if (roadConditionInfo.getPublisherTime() != null
                                && !"".equals(roadConditionInfo.getPublisherTime().toString())) {
                            publisherTime = sft.format(roadConditionInfo.getPublisherTime());
                        } else {
                            publisherTime = sft.format(new Date());
                        }
                        reChargeMap.put("publisherTime", publisherTime);
                        reChargeList.add(reChargeMap);
                    }
                    result.setStatus(MobileReturn.STATUS_SUCCESS);
                    reCharge.put("listMap", reChargeList);
                } else {
                    result.setStatus(MobileReturn.STATUS_SUCCESS);
                    reCharge.put("listMap", new ArrayList<>());
                }
                result.setListMap(reChargeList);
            } else {
                result.setStatus(MobileReturn.STATUS_FAIL);
                result.setMsg("参数有误！");
            }
            resutObj = JSONObject.fromObject(result);
            resutObj.remove("data");
            resultStr = resutObj.toString();
            logger.error("获取限高、限重路况信息： " + resultStr);
//            resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
        } catch (Exception e) {
            result.setStatus(MobileReturn.STATUS_FAIL);
            result.setMsg("获取路况失败！");
            resutObj = JSONObject.fromObject(result);
            logger.error("获取路况失败： " + e);
            resutObj.remove("data");
            resultStr = resutObj.toString();
            resultStr = DESUtil.encode(keyStr, resultStr);// 参数加密
            return resultStr;
        } finally {
            return resultStr;
        }
    }

    /**
     * 获取指定位置的路径规划方案
     * @param origin 出发点经纬度
     * @param destination 目的地经纬度
     * @param strategy 驾车选择策略 0~9
     * @return
     */
    public List<Gastation> getDirectionMap(String origin, String destination, String strategy, int step, double radius, List<Gastation> gastationAllList ){
        JSONObject resultObj = new JSONObject();
        List<Gastation> gastationList = new ArrayList<>();
        try {
            //获取路线规划结果
            String returnStr = "";
            String paramStr = "origin="+origin+"&destination="+destination+"&strategy="+strategy+"&key="+key;
            String mapResult = HttpUtil.sendPostUrl(routeAPI,paramStr,"UTF-8");

            resultObj = JSONObject.fromObject(mapResult);
            JSONObject routeObj = resultObj.optJSONObject("route");
            JSONArray pathsArray = routeObj.optJSONArray("paths");
            JSONArray stepsArray = pathsArray.optJSONObject(0).optJSONArray("steps");

            //获取当前线路所有坐标点
            List<String> polylineList = new ArrayList<>();
            if(stepsArray != null && stepsArray.size() > 0){
                for ( int i = 0; i < stepsArray.size();i++){
                    JSONObject stepObj = stepsArray.getJSONObject(i);
                    String polylineStr = stepObj.optString("polyline");

                    if(polylineStr != null && !"".equals(polylineStr)){
                        String[] polylineArray = polylineStr.split(";");
                        if(polylineArray != null && polylineArray.length > 0){
                            for (String poly:polylineArray){
                                polylineList.add(poly);
                            }
                        }
                    }
                }
            }

            //过滤当前路线坐标点（参数，过滤尺度，整型，隔N个取一个）
            List<String> coordinateList = new ArrayList<>();
            if(polylineList != null && polylineList.size() > 0){
                for (int i = 0; i < polylineList.size(); i++){
                    coordinateList.add(polylineList.get(i));
                    i = i + step;

                    if(i >= polylineList.size()){
                        break;
                    }
                }
            }

            //查询当前路线坐标点周边气站（参数，范围半径）
            if(coordinateList != null && coordinateList.size() > 0){
                Set<Gastation> gastationSet = new HashSet<>();
                if (gastationAllList != null && gastationAllList.size() > 0) {
                    for(int j = 0; j < coordinateList.size(); j ++){
                        String coordinate = coordinateList.get(j);
                        if(coordinate != null && !"".equals(coordinate)){
                            String[] coorStr = coordinate.split(",");

                            for (int i = 0; i < gastationAllList.size(); i++) {
                                if (coorStr[0] != null && !"".equals(coorStr[0]) && coorStr[1] != null && !"".equals(coorStr[1])) {
                                    double longitude = new Double(coorStr[0]);
                                    double latitude = new Double(coorStr[1]);
                                    String longStr = gastationAllList.get(i).getLongitude();
                                    String langStr = gastationAllList.get(i).getLatitude();
                                    Double longDb = new Double(0);
                                    Double langDb = new Double(0);
                                    if (longStr != null && !"".equals(longStr) && langStr != null && !"".equals(langStr)) {
                                        longDb = new Double(longStr);
                                        langDb = new Double(langStr);
                                    }
                                    // 计算当前加注站离指定坐标距离
                                    Double dist = DistCnvter.getDistance(longitude, latitude, longDb, langDb);
                                    //将指定范围内的气站存储
                                    if(dist <= radius){
                                        gastationAllList.get(i).setDistance(dist);
                                        gastationSet.add(gastationAllList.get(i));
                                    }
                                }
                            }

                        }

                    }
                }
                //将set转为list
                if(gastationSet != null && gastationSet.size() > 0){
                    gastationList = new ArrayList<>(gastationSet);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return gastationList;
    }

    public static void main(String[] args) {
        /*String resultStr = getDirectionMap("116.481028,39.989643","116.465302,40.004717","0",0,new Double(0));
        System.out.println("resultStr:"+resultStr);*/
    }
}
