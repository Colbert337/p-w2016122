package com.sysongy.util;

import java.math.BigDecimal;

/**
 * @FileName : BigDecimalArith.java
 * @Encoding : UTF-8
 * @Package : com.hbkis.pems.util
 * @Link : http://www.hbkis.com
 * @Created on : 2016年3月31日, 下午4:37:34
 * @Author : Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright : Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description :
 *
 */
public class BigDecimalArith {

	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 加法
	 * @param v1
	 * @param v2
     * @return
     */
	public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.add(b2);
	}

	/**
	 * 减法
	 * @param v1
	 * @param v2
     * @return
     */
	public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.subtract(b2);
	}

	/**
	 * 乘法
	 * @param v1
	 * @param v2
     * @return
     */
	public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.multiply(b2);
	}

	/**
	 * 除法
	 * @param v1
	 * @param v2
     * @return
     */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 除法，可设置小数位数
	 * @param v1
	 * @param v2
	 * @param scale
     * @return
     */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static BigDecimal round(BigDecimal v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		return v.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}
}
