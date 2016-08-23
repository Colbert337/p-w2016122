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
        String str = "{\"main\":{\"cityId\":\"13474294206\",\"extendType\":\"0\",\"pageNum\":\"1\",\"pageSize\":\"5\"},\"extend\":{\"version\":\"1.0\",\"terminal\":\"1\"}}";
        String keyStr = "sysongys";
        System.out.println("加密后:"+encode(keyStr,str));
        String resultStr = decode(keyStr,"DE5DCAF1F59506F07B57CB0B4682BDE479AE03B35CA75427F1870A33219CE415B80EB0F077599FBF7B821728F6569D7FF3859CDF46CB8FF55201E0FF261289FAC05D29B8448F700F128005990636DBC500F78C6B125C833E967CA7C21CA61B2E297B9072C91EB562B0AD941B4A660BC7BACFEED2421A2F87E4CF666DE6366EEED4810107A2D9F46B2EE3C4B8D9AA6DFD7BE53AA418A5C5AA6A09C046F1994E6D21172B3F3D69DFB669129C526B5453C4F3FA106A85D16DED439485F01788F300781005A581BB283D0F5AB4977B7CF1E4D120B51E72F997DB157C48C960F10E2A8B197AECBB5AC228E130780C23BDDF43F3AA1A5BFBFC4F7FF322DD9189EA450E02474BC7B0EB5D69F5EA67D24C4DAA43233BA81D1D928F58A58B4FBFBCB972107F474AF4F8119B798D9708B8A6BBE52AAB44C633BC5814786E12F97346906E260BE40905F52F9CF34139A89AC134A49DE80B7408F590FA2C593610EBD3AD46385A87FE347FEE13A2D167980643622D68269C6A676577F901C6BF8D566D37E5C5C65C659997A9284E683C69E22136ABE422026D4A7871B88C5AA8273268519AF5D276477355697C03E66C4DDECB7B50D52C4E858106288B2C7EA427575E8826658759AE8F4BA5C3D237DCA8D12F7F4A65");
//        resultStr = new String(resultStr.getBytes("GB2312"),"ISO-8859-1");
        System.out.println("解密后:"+resultStr);
    }
}
