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
		public static final String STORAGED ="0";
		public static final String MOVED ="1";
		public static final String PROVIDE="2";
		public static final String USED="3";
		public static final String PAUSED="4";
		public static final String INVALID="5";
	}
	
	public interface CardAction{
		public static final String ADD="0";
		public static final String UPDATE="1";
		public static final String DELETE="2";
	}
	
	public interface GastationStatus{
		public static final String PAUSE ="0";
		public static final String USED="1";
	}
	
	/*
	 * 	1气站 2 运输公司 3 司机
	 */
	public interface AccounType{
		public static final String GASTATION ="1";
		public static final String TRANSPORTION="2";
		public static final String DRIVER="3";
	}
	
	/*
	 * 	1充值 2 消费 3 转账
	 */
	public interface OrderType{
		public static final String CHARGE ="1";
		public static final String CONSUME="2";
		public static final String TRANSFER="3";
	}
	
	/*
	 * 	1司机 2加气站 3 运输公司
	 */
	public interface OrderOperatorType{
		public static final String DRIVER ="1";
		public static final String GASTATION="2";
		public static final String TRANSPORTION="3";
	}
	
	/*
	 * 是否首次充值 
	 */
	public static int FIRST_CHAGRE_YES = 1;
	public static int FIRST_CHAGRE_NO = 0;
	
	
	/*
	 * 订单处理流程中的类型
	 */
	public interface OrderDealType{
		public static final String CHARGE_TO_DRIVER_CHARGE ="CHARGE_TO_DRIVER_CHARGE";
		public static final String CHARGE_TO_DRIVER_CASHBACK ="CHARGE_TO_DRIVER_CASHBACK";
		public static final String CHARGE_TO_DRIVER_FIRSTCASHBACK ="CHARGE_TO_DRIVER_FIRSTCASHBACK";
	}
	
	/*
	 * 返现规则是否启用 
	 */
	public static String CASHBACK_STATUS_ENABLE = "1";
	public static String CASHBACK_STATUS_DISABLE = "2";
	
	/*
	 * 返现规则类型的编号
	 */
	public interface CashBackNumber{
		public static final String CASHBACK_GAS_STATION_CHARGE ="0";
		public static final String CASHBACK_REGISTER ="1";
		public static final String CASHBACK_FIRST_CHARGE ="2";
		public static final String CASHBACK_INVITE ="3";
		public static final String CASHBACK_WEICHAT_CHARGE ="4";
		public static final String CASHBACK_ALIPAY_CHARGE ="5";
		public static final String CASHBACK_UNIONPAY_CHARGE ="6";
		public static final String CASHBACK_CARD_CHARGE ="7";
		public static final String CASHBACK_POS_CHARGE ="8";
	}
	
	/*
	 * 	订单处理结果标记
	 */
	public interface OrderProcessResult{
		public static final String SUCCESS ="SUCCESS";
		public static final String ORDER_IS_NULL="ORDER_IS_NULL";
		public static final String ORDER_TYPE_IS_NOT_CHARGE="ORDER_TYPE_IS_NOT_CHARGE";
		public static final String ORDER_TYPE_IS_NOT_CONSUME="ORDER_TYPE_IS_NOT_CONSUME";
		public static final String ORDER_TYPE_IS_NOT_TRANSFER="ORDER_TYPE_IS_NOT_TRANSFER";
		public static final String ORDER_TO_IS_NOT_DIRVER="ORDER_TO_IS_NOT_DIRVER";
		public static final String ORDER_TO_IS_NOT_GAS_POSITION="ORDER_TO_IS_NOT_GAS_POSITION";
		public static final String OPERATOR_TYPE_IS_NOT_DRIVER="OPERATOR_TYPE_IS_NOT_DRIVER";
		public static final String OPERATOR_TYPE_IS_NOT_GASTATION="OPERATOR_TYPE_IS_NOT_GASTATION";
		public static final String OPERATOR_TYPE_IS_NOT_TRANSPORTION="OPERATOR_TYPE_IS_NOT_TRANSPORTION";
		
		public static final String DEBIT_ACCOUNT_IS_NULL="DEBIT_ACCOUNT_IS_NULL";
	}
}
