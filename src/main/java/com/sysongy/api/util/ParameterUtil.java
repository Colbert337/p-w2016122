package com.sysongy.api.util;

import com.sysongy.util.Decoder;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: ParameterUtil
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.util
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月15日, 14:50
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:  解析参数
 */
public class ParameterUtil {

    /**
     * 解析参数
     * @param params
     * @return
     */
   public static Map<String ,Object> DecoderParams(String params){
       Map<String ,Object> paramsMap = new HashMap<>();
       try {
           params = Decoder.symmetricDecrypto(params);
           JSONObject paramsObj = JSONObject.fromObject(params);
           paramsMap.put("main",paramsObj.opt("main"));
           paramsMap.put("extend",paramsObj.opt("extend"));
       }catch (Exception e){
            e.printStackTrace();
       }

       return paramsMap;
    }

    public static String EncoderParams(){
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(DecoderParams("7dd9328b46eeadb8d258ebea4308f073cfaee8a416f7f4d1eee60193ad95f66b5aa0a21a0d6f79540ddd0fdeaae5c57e7d5e1daffdc2e7371e5943e47cd8bba0718f1313ea2bba6d"));
    }
}
