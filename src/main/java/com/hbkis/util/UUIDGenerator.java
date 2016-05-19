package com.hbkis.util;

import java.util.UUID;

/**
 * @FileName     :  UUIDGenerator.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.util
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年10月27日, 上午10:58:53
 * @Author       :  Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public class UUIDGenerator {

	/** 返回32位UUID值，去除-，并转大写。 */
	public static String getUUID() {
		
		return UUID.randomUUID().toString().replaceAll("-", "");
		
	}
	
	/** Quote metacharacters in HTML. */
	public static String quote(String x) {
		if (x == null)
			return "";
		else {
			// deal with ampersands first so we can ignore the ones we add later
			x = replace(x, "&", "&amp;");
			x = replace(x, "\"", "&quot;");
			x = replace(x, "<", "&lt;");
			x = replace(x, ">", "&gt;");
			return x;
		}
	}
	
	/** 所有字符的替换，和String的replaceAll类似，但避免的正则字符的问题。 */
	public static String replace(String subject, String find, String replace) {
		StringBuffer buf = new StringBuffer();
		int l = find.length();
		int s = 0;
		int i = subject.indexOf(find);
		while (i != -1) {
			buf.append(subject.substring(s, i));
			buf.append(replace);
			s = i + l;
			i = subject.indexOf(find, s);
		}
		buf.append(subject.substring(s));
		return buf.toString();
	}
	
	/**
	 * 主测试方法
	 * @param args
	 */
	public static void main(String[] args) {
		String uuidString = getUUID();
		System.out.println("uuid:"+uuidString);
	}
}
