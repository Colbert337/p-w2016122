package com.sysongy.util;

/**
 * @FileName : GlobalConstant.java
 * @Encoding : UTF-8
 * @Package : com.hbkis.pems.util
 * @Link : http://www.hbkis.com
 * @Created on : 2015年10月29日, 下午2:45:40
 * @Author : Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright : Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description : 全局常量
 *
 */
public class GlobalConstant {

	/**
	 * 状态-启用
	 */
	public static final int STATUS_ENABLE = 0;
	/**
	 * 状态-禁用
	 */
	public static final int STATUS_DISABLE = 1;
	/**
	 * 状态-删除
	 */
	public static final int STATUS_DELETE = 0;
	/**
	 * 状态-未删除
	 */
	public static final int STATUS_NOTDELETE = 1;
	/**
	 * 登录-成功
	 */
	public static final String LOGIN_SUCCESS = "1";
	/**
	 * 登录-失败
	 */
	public static final String LOGIN_FAIL = "0";

	/**
	 * 登录-多个用户
	 */
	public static final String LOGIN_USER_MORE = "more";
	/**
	 * 登录-单个用户
	 */
	public static final String LOGIN_USER_ONE = "one";

	/**
	 * 分页页数
	 */
	public static final int PAGE_NUM = 1;

	/**
	 * 分页每页记录数
	 */
	public static final int PAGE_SIZE = 10;

	/********************************** 用户类型************************************************/
	public static final int USER_TYPE_STATION = 1;//气站用户
	public static final int USER_TYPE_TRANSPORT = 2;//运输公司用户
	public static final int USER_TYPE_CRM = 3;//CRM客户端用户
	public static final int USER_TYPE_ORG = 4;//集团用户
	public static final int USER_TYPE_MANAGE = 5;//后台管理用户

	public static final String PASSWORD_ENCRYPTION = "sysongy";
	public static final int ADMIN_YES = 0;//是管理员
	public static final int ADMIN_NO = 1;//不是管理员
	/********************************** 路径配置 ************************************************/
	/* 小头像 */
	public static final String CHILD_AVATAR_S_PATH = "/upload/child/avatar/small/";
	/* 大头像 */
	public static final String CHILD_AVATAR_B_PATH = "/upload/child/avatar/big/";

	/* 小头像 */
	public static final String USER_AVATAR_S_PATH = "/upload/user/avatar/small/";
	/* 大头像 */
	public static final String USER_AVATAR_B_PATH = "/upload/user/avatar/big/";

	/********************************** 图片路径 ************************************************/

	public static final String IMG_PATH = "http://localhost:8080/pems-web/";

	/**
	 * 系统文档的路径
	 */
	public static final String DOCUMENT_SYS_PATH = "/upload/document/sys/";

	public static final String MENU_PATH = "/upload/menuImage/";

	/********************************** 字典类型 ************************************************/

	/********************************** 分页参数 ************************************************/
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

	// http请求的地址
	public static final String HTTP_PEMS_PATH = "http_pems_path";

	/* 配置文件路径 */
	public static final String CONF_PATH = "conf/system-conf.properties";
	
	public interface CardStatus{
		public static final String SUSPEND ="0";
		public static final String UN_USED ="1";
		public static final String USED ="2";
	}
	
	public interface CardAction{
		public static final String STORAGE ="0";
		public static final String MOVE ="1";
		public static final String DESTORY="2";
	}

}
