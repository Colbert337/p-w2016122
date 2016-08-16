package com.sysongy.api.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * @FileName: Md5Util
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.util
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月16日, 10:18
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:  BASE64加密解密
 */
public class Md5Util {
    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder decoder = new BASE64Decoder();

    /**
     * 加密
     * @param s 被加密字符串
     * @return
     */
    public static String base64encode(String s){
        try{
            String encodeStr = encoder.encode(s.getBytes());

            return encodeStr;
        }catch(Exception e){
            return s;
        }
    }

    /**
     * 解密
     * @param s 被解密字符串
     * @return
     */
    public static String base64decode(String s){
        try{
            String decodeStr = new String(decoder.decodeBuffer(s));

            return decodeStr;
        }catch(Exception e){
            return s;
        }
    }

    /**
     * md5加密
     * @param input 被加密字符串
     * @return
     */
    public static byte[] md5encode(byte[] input){
        byte[] digestedValue = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input);
            digestedValue = md.digest();
        }catch(Exception e){
            e.printStackTrace();
        }
        return digestedValue;
    }

    /**
     * 测试方法
     * @param args
     */
    public static void main(String args[]){
        //测试BASE64加密
        System.out.println("------------------------------------");
        String str = "{\"main\":{\"username\":\"12111111111\",\"password\":\"96e79218965eb72c92a549dd5a330112\"},\"extend\":{\"version\":\"1.0\",\"terminal\":\"1\"}}";
        String ret = null;
        ret = base64encode(str);
        System.out.println("加密前:"+str+" \n加密后:"+ret);

        //测试BASE64解密
        System.out.println("------------------------------------");
        String str1 = ret;
        ret = base64decode(ret);
        System.out.println("解密前:"+str1+"\n 解密后:"+ret);


    }
}
