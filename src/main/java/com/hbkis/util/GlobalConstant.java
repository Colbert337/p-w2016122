package com.hbkis.util;

/**
 * @FileName     :  GlobalConstant.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.util
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年10月29日, 下午2:45:40
 * @Author       :  Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :	全局常量
 *
 */
public class GlobalConstant {

	/**
	 * 状态-启用
	 */
	public static final  int STATUS_ENABLE = 1;
	/**
	 * 状态-禁用
	 */
	public static final  int STATUS_DISABLE = 0;
	/**
	 * 登录-成功
	 */
	public static final  String LOGIN_SUCCESS = "1";
	/**
	 * 登录-失败
	 */
	public static final  String LOGIN_FAIL = "0";
	
	/**
	 * 登录-多个用户
	 */
	public static final  String LOGIN_USER_MORE = "more";
	/**
	 * 登录-单个用户
	 */
	public static final  String LOGIN_USER_ONE = "one";
	
	/**
	 * 分页页数
	 */
	public static final  int PAGE_NUM = 1;
	
	/**
	 * 分页每页记录数
	 */
	public static final  int PAGE_SIZE = 10;
	
	
	/**********************************路径配置************************************************/
	/*幼儿小头像*/
	public static final String CHILD_AVATAR_S_PATH = "/upload/child/avatar/small/";
	/*幼儿大头像*/
	public static final String CHILD_AVATAR_B_PATH = "/upload/child/avatar/big/";
	
	/*员工小头像*/
	public static final String USER_AVATAR_S_PATH = "/upload/user/avatar/small/";
	/*员工大头像*/
	public static final String USER_AVATAR_B_PATH = "/upload/user/avatar/big/";
	
	/**********************************用户类型************************************************/
	
	/**
	 * 系统管理员
	 */
	public static final int USER_ADMIN = 1;
	/**
	 * 员工
	 */
	public static final int USER_EMPLOYEE = 2;
	/**
	 * 家长
	 */
	public static final int USER_PARENT = 3;
	
	
	/**********************************图片路径************************************************/
	
	public static final String IMG_PATH = "http://localhost:8080/pems-web/";
	
	/**
	 * 系统文档的路径
	 */
	public static final String DOCUMENT_SYS_PATH = "/upload/document/sys/";
	
	
	public static final String MENU_PATH = "/upload/menuImage/";
	
	
	/**********************************字典类型************************************************/
	/**
	 * 民族
	 */
	public static final String DIC_NATIONALITY = "dic_nationality";
	/**
	 * 国籍地区
	 */
	public static final String DIC_GJDQ = "dic_gjdq";
	/**
	 * 户口所在地
	 */
	public static final String DIC_HKSZD = "dic_hkszd";
	/**
	 * 户口性质
	 */
	public static final String DIC_HKXZ = "dic_hkxz";
	/**
	 * 非农业户口类型
	 */
	public static final String DIC_FNYHKLX = "dic_fnyhklx";
	/**
	 * 港澳台侨外
	 */
	public static final String DIC_GATQW = "dic_gatqw";
	/**
	 * 出生所在地
	 */
	public static final String DIC_CSSZD = "dic_csszd";
	/**
	 * 就读方式
	 */
	public static final String DIC_JDFS = "dic_jdfs";
	/**
	 * 血型
	 */
	public static final String DIC_BLOOD_TYPE = "dic_blood_type";
	/**
	 * 健康状况
	 */
	public static final String DIC_HEALTH_STATUS = "dic_health_status";
	/**
	 * 残疾幼儿类别
	 */
	public static final String DIC_DEFORMITY_TYPE = "dic_deformity_type";
	/**
	 * 学历
	 */
	public static final String DIC_EDUCATION = "dic_education";
	/**
	 * 政治面貌
	 */
	public static final String DIC_POLITICAL_STATUS = "dic_political_status";
	/**
	 * 教师等级
	 */
	public static final String DIC_LEVEL = "dic_level";
	/**
	 * 证件类型
	 */
	public static final String DIC_CARD_TYPE = "dic_card_type";
	/**
	 * 籍贯
	 */
	public static final String DIC_NATIVE_PLACE = "dic_native_place";
	
	/**
	 * 与幼儿关系
	 */
	public static final String DIC_RELATION = "dic_relation";
	
	/**
	 *  监护人身份证件类型
	 */
	public static final String DIC_GUARDIAN_CARD_TYPE = "guardian_card_type";
	
	/**********************************分页参数************************************************/
	/**
	 * 上一页
	 */
	public static final String PAGE_FIRST = "f";
	/**
	 * 中间页
	 */
	public static final String PAGE_NUMBER = "n";
	/**
	 * 下一页
	 */
	public static final String PAGE_LAST = "l";
	/**
	 * 默认当前菜单编号
	 */
	public static final String MENU_CODE_DEFALT = "zl";
	
	//http请求的地址
	public static final String HTTP_PEMS_PATH = "http_pems_path";
	
	/*配置文件路径*/
	public static final String CONF_PATH = "conf/system-conf.properties";
	
}
