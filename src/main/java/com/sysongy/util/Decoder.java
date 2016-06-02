package com.sysongy.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.Key;

/**
 * @FileName : Decoder.java
 * @Encoding : UTF-8
 * @Package : com.hbkis.base.controller
 * @Link : http://www.hbkis.com
 * @Created on : 2015年10月20日, 下午5:53:21
 * @Author : Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright : Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description : 解码（解密）工具
 *
 */
public class Decoder {

	/**
	 * 构造函数.
	 */
	public Decoder() {
	}

	/**
	 * 将16进制字符串转换成字节数组.
	 * 
	 * @param strHex
	 *            16进制字符串
	 * @return 字节数组.
	 */
	public static byte[] hexStringToByteArray(String strHex) {
		byte[] theResult = null;
		byte[] byteArr = new byte[strHex.length() / 2];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = (byte) Integer.parseInt(
					strHex.substring(i * 2, i * 2 + 2), 16);
		}
		theResult = byteArr;
		return theResult;
	}

	/**
	 * 对称解密方法
	 *
	 * @param byteSource
	 *            需要解密的数据
	 * @return 经过解密的数据
	 * @throws Exception
	 */
	public static byte[] symmetricDecrypto(byte[] byteSource) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int mode = Cipher.DECRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			byte[] keyData = { 9, 1, 3, 7, 2, 1, 2, 0 };
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, key);
			int blockSize = cipher.getBlockSize();
			int position = 0;
			int length = byteSource.length;
			boolean more = true;
			while (more) {
				if (position + blockSize <= length) {
					baos.write(cipher.update(byteSource, position, blockSize));
					position += blockSize;
				} else {
					more = false;
				}
			}
			if (position < length) {
				baos.write(cipher.doFinal(byteSource, position, length
						- position));
			} else {
				baos.write(cipher.doFinal());
			}
			return baos.toByteArray();
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
	}

	public static String symmetricDecrypto(String strSource) throws Exception {
		byte[] byteSource = hexStringToByteArray(strSource);
		byte[] byteArr = symmetricDecrypto(byteSource);
		String result = new String(byteArr);
		return result;
	}

	/**
	 * 对称解密
	 * @param strSource 待解密字符串
	 * @param operator	 加密算子
	 * @return
	 * @throws Exception 解密后字符串
     */
	public static String symmetricDecrypto(String strSource, String operator) throws Exception {
		String result = symmetricDecrypto(strSource.trim());
		result = result.replace(operator.trim(),"");
		return result;
	}
	public static void main(String[] args) throws Exception {
		// byte[] byteArr =
		// Decoder.hexStringToByteArray("18e5e907c60ffeaac25d6b38e84baceec86483c7f185899e");
		// try {
		// byte[] byteArr2 = Decoder.symmetricDecrypto(byteArr);
		// System.out.println(new String(byteArr2));
		// } catch (Exception ex) {
		// Logger.getLogger(Decoder.class.getName()).log(Level.SEVERE, null,
		// ex);
		// }
		// System.out.println(new String(Decoder.hexStringToByteArray("ced2")));
		System.out.printf(symmetricDecrypto("974f8ed445d0785a"));
	}
}
