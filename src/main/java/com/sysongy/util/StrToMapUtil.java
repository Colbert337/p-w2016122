package com.sysongy.util;

import java.util.HashMap;
import java.util.Map;

/**字符串转换Map
 * @FileName     :  StrToMapUtil.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.util
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年4月27日, 下午2:31:46
 * @Author       :  Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public class StrToMapUtil {

	public static Map<String, Object> strToMap(String str) {
		Map<String, Object> strMap = new HashMap<String, Object>();
		String[] strTempArray = {};
		str = str.trim();
//		str = str.replace(" ", "");
		if(str != null && !"".equals(strMap)){
			str = str.substring(1,str.length()-1);
			strTempArray = str.split(",");
			if (strTempArray.length > 0) {
				for (int i = 0; i < strTempArray.length; i++) {
					String strItem = strTempArray[i];
					strItem = strItem.trim();
					String[] strItemArray = strItem.split("=");
					if(strItemArray.length > 1){
						strMap.put(strItemArray[0], strItemArray[1]);
					}else{
						strMap.put(strItemArray[0], "");
					}
				}
			}
		}
		return strMap;
	}
}
