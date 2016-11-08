package com.sysongy.api.mobile.controller;

import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.util.DESUtil;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.util.JsonTool;
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

    String routeAPI = "http://restapi.amap.com/v3/direction/driving?";

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
        String key = "849930967f293d450246fe6bc906a683";
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
            String token = "token";
            String password = "password";
            String verificationCode = "verificationCode";
            boolean b = JsonTool.checkJson(mainObj, token, password, verificationCode);
            /**
             * 请求接口
             */
            if (b) {
                SysDriver sysDriver = new SysDriver();
                sysDriver.setSysDriverId(mainObj.optString("token"));
                password = mainObj.optString("password");
                if (password != null && !"".equals(password)) {
                    sysDriver.setPassword(password);
                    sysDriver.setSysDriverId(mainObj.optString("token"));

                } else {
                    result.setStatus(MobileReturn.STATUS_FAIL);
                    result.setMsg("密码为空！");
                }
            } else {
                result.setStatus(MobileReturn.STATUS_FAIL);
                result.setMsg("参数有误！");
            }
            resutObj = JSONObject.fromObject(result);
            resutObj.remove("listMap");
            resultStr = resutObj.toString();
            logger.error("修改登录密码信息： " + resultStr);
            resultStr = DESUtil.encode(keyStr, resultStr);// 参数解密
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
}
