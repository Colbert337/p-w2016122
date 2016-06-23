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
	public static final String MSG_PREFIX = "msg_";
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
	
	public interface StationStatus{
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
	 * 	110车队预付款充值  120 加油站预付款充值 130个人充值....
	 */
	public interface OrderType{
		public static final String CHARGE_TO_TRANSPORTION ="110";
		public static final String CHARGE_TO_GASTATION ="120";
		public static final String CHARGE_TO_DRIVER ="130";
		public static final String CONSUME_BY_TRANSPORTION ="210";
		public static final String CONSUME_BY_DRIVER ="220";
		public static final String TRANSFER_TRANSPORTION_TO_DRIVER ="310";
		public static final String TRANSFER_DRIVER_TO_DRIVER ="320";
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
	public static final int FIRST_CHAGRE_YES = 1;
	public static final int FIRST_CHAGRE_NO = 0;
	
	
	/*
	 * 订单处理流程中的类型
	 * Charge ---表示充值
	 * discharge---表示充红
	 */
	public interface OrderDealType{
		public static final String CHARGE_TO_DRIVER_CHARGE ="131";
		public static final String CHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK ="132";
		public static final String CHARGE_TO_DRIVER_CASHBACK ="133";
		public static final String DISCHARGE_TO_DRIVER_CHARGE ="134";
		public static final String DISCHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK ="135";
		public static final String DISCHARGE_TO_DRIVER_CASHBACK ="136";
	}

	/*
	 * 返现规则是否启用 
	 */
	public static final String CASHBACK_STATUS_ENABLE = "1";
	public static final String CASHBACK_STATUS_DISABLE = "2";
	
	/*
	 * 返现类型的编号
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
	 * 订单充值类型,与返现类型里面的充值编码的保持一致，但是不能用一个，因为逻辑上不一样，后面有可能增加
	 * 
	 */
	public interface OrderChargeType{
		public static final String CHARGETYPE_GAS_STATION = GlobalConstant.CashBackNumber.CASHBACK_GAS_STATION_CHARGE;
		public static final String CHARGETYPE_WEICHAT_CHARGE =GlobalConstant.CashBackNumber.CASHBACK_WEICHAT_CHARGE;
		public static final String CHARGETYPE_ALIPAY_CHARGE = GlobalConstant.CashBackNumber.CASHBACK_ALIPAY_CHARGE;
		public static final String CHARGETYPE_UNIONPAY_CHARGE =GlobalConstant.CashBackNumber.CASHBACK_UNIONPAY_CHARGE;
		public static final String CHARGETYPE_CARD_CHARGE =GlobalConstant.CashBackNumber.CASHBACK_CARD_CHARGE;
		public static final String CHARGETYPE_POS_CHARGE =GlobalConstant.CashBackNumber.CASHBACK_POS_CHARGE;
	}
	
	/*
	 * 是否充红 
	 */
	public static final String ORDER_ISCHARGE_YES = "1";
	public static final String ORDER_ISCHARGE_NO = "0";
	
	/*
	 * 	订单处理结果标记
	 */
	public interface OrderProcessResult{
		public static final String SUCCESS ="SUCCESS";
		public static final String ORDER_IS_NULL="ORDER_IS_NULL";
		public static final String ORDER_TYPE_IS_NOT_MATCH="ORDER_TYPE_IS_NOT_MATCH";
		public static final String ORDER_TYPE_IS_NOT_DISCHARGE="ORDER_TYPE_IS_NOT_DISCHARGE";
		public static final String ORDER_TYPE_IS_NOT_CHARGE="ORDER_TYPE_IS_NOT_CHARGE";
		public static final String ORDER_TYPE_IS_NOT_CONSUME="ORDER_TYPE_IS_NOT_CONSUME";
		public static final String ORDER_TYPE_IS_NOT_TRANSFER="ORDER_TYPE_IS_NOT_TRANSFER";
		public static final String ORDER_TO_IS_NOT_DIRVER="ORDER_TO_IS_NOT_DIRVER";
		public static final String ORDER_TO_IS_NOT_GAS_POSITION="ORDER_TO_IS_NOT_GAS_POSITION";
		public static final String OPERATOR_TYPE_IS_NOT_DRIVER="OPERATOR_TYPE_IS_NOT_DRIVER";
		public static final String OPERATOR_TYPE_IS_NOT_GASTATION="OPERATOR_TYPE_IS_NOT_GASTATION";
		public static final String OPERATOR_TYPE_IS_NOT_TRANSPORTION="OPERATOR_TYPE_IS_NOT_TRANSPORTION";

		public static final String DISCHARGE_ORDER_ID_IS_NULL="DISCHARGE_ORDER_ID_IS_NULL";
		public static final String DISCHARGE_ORDER_ORDERDEAL_IS_EMPTY="DISCHARGE_ORDER_ORDERDEAL_IS_EMPTY";
		public static final String DISCHARGE_ORDER_CASH_IS_NOT_NEGATIVE="DISCHARGE_ORDER_CASH_IS_NOT_NEGATIVE";
		public static final String DISCHARGE_ORDER_ORDERDEAL_NOT_RUNSUCCESS="DISCHARGE_ORDER_ORDERDEAL_NOT_RUNSUCCESS";
		public static final String DISCHARGE_ORDER_CASHBACK_IS_NULL = "DISCHARGE_ORDER_CASHBACK_IS_NULL";
		
		public static final String DEBIT_ACCOUNT_IS_NULL="DEBIT_ACCOUNT_IS_NULL";

		public static final String ORDER_ERROR_BALANCE_IS_NOT_ENOUGH = "BALANCE_IS_NOT_ENOUGH";
	}

	/*
	 * 0 新注册 1 待审核 2 已通过 3 未通过
	 */
	public interface DriverStatus{
		public static final String NEWBEE = "0";
		public static final String PENDING = "1";
		public static final String PASSED = "2";
		public static final String NOPASS = "3";
	}
	
	public interface AccountStatus{
		public static final String USER_SUSPEND = "0";
		public static final String CARD_SUSPEND = "1";
		public static final String NORMAL = "2";
	}
}
