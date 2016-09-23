package com.sysongy.util;

import java.util.HashMap;
import java.util.Iterator;

import net.sf.json.JSONObject;
/**
 * @author Administrator
 * 校验Json格式数据中的值是否为空
 */
public class JsonTool {
	/**
	 * 
	 * @param mainObj Json格式字符串
	 * @param strings 要校验的键
	 * @return result
	 */
	public static boolean checkJson(String mainObj, String... strings) {
		boolean result = false;
		HashMap<String, String> map = toHashMap(mainObj);
		for(int i = 0;i<strings.length;i++){
			for (String str : map.keySet()) {
				if(strings[i].equals(str)){
					if(map.get(str)==null ||"".equals(map.get(str))){
						return result;
					}else{
						result = true;
					}
				}
			}
		}
		return result;
	}
	private static HashMap<String, String> toHashMap(String mainObj) {
		HashMap<String, String> data = new HashMap<String, String>();
		JSONObject jsonObject = JSONObject.fromObject(mainObj);
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			String value = (String) jsonObject.get(key);
			data.put(key, value);
		}
		return data;
	}
}
