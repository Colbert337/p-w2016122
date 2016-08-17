package com.sysongy.api.mobile.model.base;

import java.util.List;
import java.util.Map;

/**
 * @FileName: MobileReturn
 * @Encoding: UTF-8
 * @Package: com.sysongy.api.mobile.model
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月15日, 14:50
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:  接口返回结果集
 */
public class MobileReturn {
	public static final String STATUS_SUCCESS = "100";//成功
	public static final String STATUS_FAIL = "200";//失败

	public static final String STATUS_MSG_SUCCESS = "请求成功";
	public static final String STATUS_MSG_FAIL = "请求失败";

	private String status;//接口请求状态
	
	private String msg;//接口返回提示
	
	private List<Map<String, Object>> listMap;//列表结果集

	private Map<String, Object> data;//对象结果集

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Map<String, Object>> getListMap() {
		return listMap;
	}

	public void setListMap(List<Map<String, Object>> listMap) {
		this.listMap = listMap;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
