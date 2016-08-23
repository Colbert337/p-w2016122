package com.sysongy.api.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @FileName: DESUtil
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.util
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月16日, 10:57
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description: DES加密解密工具包
 */
public class DESUtil {
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    /**
     * DES算法，加密
     *
     * @param data
     *            待加密字符串
     * @param key
     *            加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws InvalidAlgorithmParameterException
     * @throws Exception
     */
    public static String encode(String key, String data) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes("UTF-8"));
            return byte2String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * DES算法，解密
     *
     * @param data
     *            待解密字符串
     * @param key
     *            解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception
     *             异常
     */
    public static String decode(String key, String data) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(byte2hex(data.getBytes("UTF-8"))));
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2String(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase(Locale.CHINA);
    }

    /**
     * 二进制转化成16进制
     *
     * @param b
     * @return
     */
    private static byte[] byte2hex(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    public static void main(String args[]) throws Exception{
//        String str = "王冬强";
        String str = "{\"main\":{\"token\":\"5fbffec4494347199889322c3f488a14\",\"name\":\"asdf\",\"imgUrl\":\"5fbffec4494347199889322c3f488a14\"},\"extend\":{\"version\":\"1.0\",\"terminal\":\"1\"}}";
        String keyStr = "sysongys";
        System.out.println("加密后:"+encode(keyStr,str));
        String resultStr = decode(keyStr,"CDE0C6E42FF0D3D66277E0784D778DEDFBC0D068AF34D0C32394126D9B24A6282098BD202BDF10216C3C7253E795359D08C8484F81C1BE71DAB81833484A33DA18CCFE52349999C061CEC7F6891954F3AAD8C9D463A9CD46BB29531F74CAB8E11DF0BDD5477661B7667F137CA4FCE4C7E52DC1EF52F7ECDE");
//        resultStr = new String(resultStr.getBytes("GB2312"),"ISO-8859-1");
        System.out.println("解密后:"+resultStr);
    }
}
