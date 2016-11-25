package com.sysongy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPhone {
	/**
	 * 手机号验证
	 * @param Mobile
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String Mobile) {
		boolean b = false;
		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		Matcher m = p.matcher(Mobile);
		b = m.matches();
		return b;
	}
	/**
	 * 电话号码验证
	 * @param Phone
	 * @return 验证通过返回true
	 */
	public static boolean isPhoneWithArea(String Phone) {
		boolean b = false;
		if (Phone.indexOf("-")!=-1) {
			String phoneStrArray[] = Phone.split("-");
			String area = phoneStrArray[0];
			String number = phoneStrArray[1];
			if(area.length()==3){
				Pattern p1 = Pattern.compile("^[0][1-9][0-9]$"); // 验证带3位区号的
				Matcher m = p1.matcher(area);
				b = m.matches();
				if(b){
					return isPhoneWithoutAreaNumber(number);
				}
			}
			if(area.length()==4){
				Pattern p1 = Pattern.compile("^[0][1-9][0-9]{2}$"); // 验证带4位区号的
				Matcher m = p1.matcher(area);
				b = m.matches();
				if(b){
					return isPhoneWithoutAreaNumber(number);
				}
			}
		} else {
			b = isPhoneWithoutAreaNumber(Phone);
		}
		return b;
	}
	public static boolean isPhoneWithoutAreaNumber(String Phone) {
		boolean b = false;
		Pattern p2 = Pattern.compile("^[1-9]{1}[1-9]{5,8}$"); // 验证没有区号的
		Matcher m = p2.matcher(Phone);
		b = m.matches();
		return b;
	}
}
