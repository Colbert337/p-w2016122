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
            DESKeySpec dks = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes("UTF-8"));
            /*return new String(bytes,"UTF-8");*/
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
            DESKeySpec dks = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(byte2hex(data.getBytes("UTF-8"))),"UTF-8");
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
        String str = "{\"main\":{\"phoneNum\":\"13474294206\",\"verificationCode\":\"688556\",\"password\":\"96e79218965eb72c92a549dd5a330112\",\"invitationCode\":\"12111111111\"},\"extend\":{\"version\":\"1.0\",\"terminal\":\"1\"}}";
        String keyStr = "sysongys";
        System.out.println("加密后:"+encode(keyStr,str));
        String resultStr;
        resultStr = decode(keyStr,"DE5DCAF1F59506F07B57CB0B4682BDE479AE03B35CA75427F1870A33219CE415B80EB0F077599FBF7B821728F6569D7FF3859CDF46CB8FF55201E0FF261289FAC05D29B8448F700F128005990636DBC500F78C6B125C833E749D6B6D05145D7FCAA366E14E219953B880A3EB453E4CA31A004AABF53A3F40CABEBAFE379EC4EC5DA2129919EF56328EF04ABCE11DF5696E19E6A5B9BA6CAF25196E7CE98DAAEB0AA0FB40F8EFF864A2DBA149C611A2F322CF0D46EBD2555577B49C182E10F3970C63D024EFD257C325B7C832D1ACC34863D2ABB751AD7EA6A33D8B7F400B5EEB76F895B7AF7266AACBC3A00204451F3386B87467B38D9578CE9B87A1B95FBF70FD941E128A63BC8110387C5874F648F1724FF44BF8EE062F082B23C42D5A9390A72A2B33B5340DCD263E4BA652344CE38FF4A6091EB1F125DD1DE97088D26B9C");
//        resultStr = new String(resultStr.getBytes("GB2312"),"ISO-8859-1");
        System.out.println("解密后:"+resultStr);
    }
}
