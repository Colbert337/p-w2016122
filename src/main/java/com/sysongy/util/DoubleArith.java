package com.sysongy.util;

import java.math.BigDecimal;

/**
 * @FileName : DoubleArith.java
 * @Encoding : UTF-8
 * @Package : com.hbkis.pems.util
 * @Link : http://www.hbkis.com
 * @Created on : 2016年3月31日, 下午4:37:34
 * @Author : Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright : Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description :
 *
 */
public class DoubleArith {

	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 加法
	 * @param v1
	 * @param v2
     * @return
     */
	public static Double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.add(b2).doubleValue();
	}

	/**
	 * 减法
	 * @param v1
	 * @param v2
     * @return
     */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 乘法
	 * @param v1
	 * @param v2
     * @return
     */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 除法
	 * @param v1
	 * @param v2
     * @return
     */
	public static Double div(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	/**
	 * 除法，设置保留小数
	 * @param v1
	 * @param v2
	 * @param scale
     * @return
     */
	public static Double div(Double v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
