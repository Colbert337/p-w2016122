package com.sysongy.api.mobile.controller;

import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.util.DESUtil;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.util.HttpUtil;
import com.sysongy.util.JsonTool;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public final String keyStr = "sysongys";

    public static String  routeAPI = "http://restapi.amap.com/v3/direction/driving?";
    public static String key = "849930967f293d450246fe6bc906a683";

    /**
     * 获取路径规划方案
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/map/getDirection")
    @ResponseBody
    public String updatePassword(String params) {
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
                String paramStr = "origin="+origin+"&destination="+destination+"&strategy="+strategy+"&key="+key;

                String mapResult = HttpUtil.sendPostUrl(routeAPI,paramStr,"UTF-8");
                resultStr = mapResult;
                result.setStatus(MobileReturn.STATUS_SUCCESS);
                result.setMsg("获取规划方案成功！");
            } else {
                result.setStatus(MobileReturn.STATUS_FAIL);
                result.setMsg("参数有误！");
            }

            logger.error("修改登录密码信息： " + resultStr);
        } catch (Exception e) {
            result.setStatus(MobileReturn.STATUS_FAIL);
            result.setMsg("修改登录密码失败！");
            resutObj = JSONObject.fromObject(result);
            logger.error("修改登录密码失败： " + e);
            resutObj.remove("listMap");
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
    public static String getDirectionMap(String origin, String destination, String strategy){
        JSONObject resultObj = new JSONObject();
        try {
            //获取路线规划结果
            String returnStr = "";
            String paramStr = "origin="+origin+"&destination="+destination+"&strategy="+strategy+"&key="+key;
            String mapResult = HttpUtil.sendPostUrl(routeAPI,paramStr,"UTF-8");

            resultObj = JSONObject.fromObject(mapResult);

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    public static void main(String[] args) {
        String resultStr = getDirectionMap("116.481028,39.989643","116.465302,40.004717","0");
        System.out.println("resultStr:"+resultStr);
    }
}
