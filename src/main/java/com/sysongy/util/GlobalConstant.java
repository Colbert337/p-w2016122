package com.sysongy.util;

import java.math.BigDecimal;
import java.util.HashMap;

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
	
	public static final String appOperatorId = "8aa4ba67855a11e6a356000c291aa9e3";

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

	/********************************** 司机类型 ************************************************/
	public interface DriverType{
		public static final int TRANSPORT = 0;//运输公司关联司机
		public static final int GAS_STATION = 1;//气站关联司机
	}

	//用户卡状态  0:已冻结 1：已入库；2：已出库；3:已/未发放 4:使用中 5:已失效
	public interface CardStatus{
		public static final String PAUSED ="0";
		public static final String STORAGED ="1";
		public static final String MOVED ="2";
		public static final String PROVIDE ="3";
		public static final String USED ="4";
		public static final String INVALID ="5";
	}

	/**
	 * 获取卡状态内容
	 * @param key
	 * @return
	 */
	public static String getCardStatus(String key){
		String value = "";
		switch (key){
			case CardStatus.PAUSED:
				value = "已冻结";
				break;
			case CardStatus.STORAGED:
				value = "已入库";
				break;
			case CardStatus.MOVED:
				value = "已出库";
				break;
			case CardStatus.PROVIDE:
				value = "未发放";
				break;
			case CardStatus.USED:
				value = "使用中";
				break;
			case CardStatus.INVALID:
				value = "已失效";
				break;
		}
		return value;
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
	 * 	司机认证状态
	 */
	public interface DriverCheckedStatus{
		public static final String NOT_CERTIFICATE ="0";
		public static final String CERTIFICATING="1";
		public static final String ALREADY_CERTIFICATED="2";
		public static final String CERTIFICATE_NOT_PASS="3";
	}

	/*
	 * 未实名认证司机累计充值金额 
	 */
	public static final BigDecimal DRIVER_NOT_CERTIFICATE_LIMIT= new BigDecimal("5000"); 
	
	/*
	 * 	110运输公司预付款充值  120 加油站预付款充值 130个人充值 210运输公司消费 220 司机消费 310 运输公司对个人转账 320个人对个人转账
	 * 	410注册返现 420邀请返现
	 */
	public interface OrderType{
		public static final String CHARGE_TO_TRANSPORTION ="110";
		public static final String CHARGE_TO_GASTATION ="120";
		public static final String CHARGE_TO_DRIVER ="130";
		public static final String CONSUME_BY_TRANSPORTION ="210";
		public static final String CONSUME_BY_DRIVER ="220";
		public static final String TRANSFER_TRANSPORTION_TO_DRIVER ="310";
		public static final String TRANSFER_DRIVER_TO_DRIVER ="320";
		public static final String REGISTER_CASHBACK ="410";
		public static final String INVITED_CASHBACK ="420";
	}

	/**
	 * 获取订单类型内容
	 * @param key
	 * @return
     */
	public static String getOrderType(String key){
		String value = "";
		switch (key){
			case OrderType.CHARGE_TO_TRANSPORTION:
				value = "运输公司预付款充值";
				break;
			case OrderType.CHARGE_TO_GASTATION:
				value = "加油站预付款充值";
				break;
			case OrderType.CHARGE_TO_DRIVER:
				value = "个人充值";
				break;
			case OrderType.CONSUME_BY_TRANSPORTION:
				value = "运输公司消费";
				break;
			case OrderType.CONSUME_BY_DRIVER:
				value = "司机消费";
				break;
			case OrderType.TRANSFER_TRANSPORTION_TO_DRIVER:
				value = "运输公司对个人转账";
				break;
			case OrderType.TRANSFER_DRIVER_TO_DRIVER:
				value = "个人对个人转账";
				break;
		}
		return value;
	}

	public static HashMap<String,String> OrderClassify = new HashMap<String,String>();
	static{
		OrderClassify.put("110", "充值");
		OrderClassify.put("120", "充值");
		OrderClassify.put("130", "充值");
		OrderClassify.put("210", "消费");
		OrderClassify.put("220", "消费");
		OrderClassify.put("310", "转账");
		OrderClassify.put("320", "转账");
	}

	/*
	 * 	订单操作对象类型： 1司机 2加气站 3 运输公司
	 */
	public interface OrderOperatorTargetType{
		public static final String DRIVER ="1";
		public static final String GASTATION="2";
		public static final String TRANSPORTION="3";
	}
	
	/*
	 * 	订单操作发起者类型： 1司机 2加气站 3 运输公司
	 */
	public interface OrderOperatorSourceType{
		public static final String DRIVER ="1";
		public static final String GASTATION="2";
		public static final String TRANSPORTION="3";
		public static final String PLATFORM="4";
		public static final String WECHAT = "5";
		public static final String APP = "6";
	}

	/*
	 * 是否首次充值 
	 */
	public static final int FIRST_CHAGRE_YES = 1;
	public static final int FIRST_CHAGRE_NO = 0;
	
	
	/*
	 * 充值后是否产生消费 
	 */
	public static final String HAVE_CONSUME_YES = "1";
	public static final String HAVE_CONSUME_NO = "0";
	
	/*
	 * 车队是否分配额度 
	 */
	public static final int TCFLEET_IS_ALLOT_YES = 1;
	public static final int TCFLEET_IS_ALLOT_NO = 0;
	
	/*
	 * 系统账户（钱袋）状态 
	 */
	public static final String SYS_USER_ACCOUNT_STATUS_FROZEN = "0";
	public static final String SYS_USER_ACCOUNT_STATUS_CARD_FROZEN = "1";
	public static final String SYS_USER_ACCOUNT_STATUS_NORMAL = "2";
	
	
	
	/*
	 * 订单处理流程中的类型
	 * Charge ---表示充值
	 * discharge---表示充红
	 */
	public interface OrderDealType{
		public static final String CHARGE_TO_DRIVER_CHARGE ="131";//个人充值
		public static final String CHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK ="132";//首充返现
		public static final String CHARGE_TO_DRIVER_CASHBACK ="133";//个人返现
		public static final String DISCHARGE_TO_DRIVER_CHARGE ="134";//充值冲红
		public static final String DISCHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK ="135";//首次充值返现冲红（只有个人充值）
		public static final String DISCHARGE_TO_DRIVER_CASHBACK ="136";//返现冲红
		public static final String CHARGE_TO_DRIVER_DEDUCT_GASTATION_PREPAY ="137";//加注站预付款余额扣除
		public static final String DISCHARGE_TO_DRIVER_DEDUCT_GASTATION_PREPAY ="138";//加注站预付款余额冲红
		
		public static final String CHARGE_TO_TRANSPORTION_CHARGE ="111";//车队充值
		public static final String CHARGE_TO_GASTATION_CHARGE ="121";//加注站预付款充值
		
		public static final String DRIVER_REGISTER_CASHBACK = "410";//被邀请人返现
		public static final String DRIVER_INVITE_CASHBACK = "420";//邀请人返现
		
		public static final String CONSUME_DRIVER_DEDUCT ="221";//个人消费
		public static final String DISCONSUME_DRIVER_DEDUCT ="222";//冲红
		public static final String CONSUME_TRANSPORTION_DEDUCT ="211";//车队消费
		public static final String DISCONSUME_TRANSPORTION_DEDUCT ="212";//冲红
		
		public static final String TRANSFER_TRANSPORTION_TO_DRIVER_DEDUCT_TRANSPORTION ="311";//运输公司转出
		public static final String TRANSFER_TRANSPORTION_TO_DRIVER_CASHBACK_TO_TRANSPORTION ="312";//运输公司转出
		public static final String TRANSFER_TRANSPORTION_TO_DRIVER_INCREASE_DRIVER ="313";//个人转入
		public static final String TRANSFER_DRIVER_TO_DRIVER_DEDUCT_DRIVER ="321";//转入
		public static final String TRANSFER_DRIVER_TO_DRIVER_INCREASE_DRIVER ="322";//转出
		
	}

	public static String getOrderDealType(String key){
		String value = "";
		switch (key){
			case OrderDealType.CHARGE_TO_DRIVER_CHARGE:
				value = "个人充值";
				break;
			case OrderDealType.CHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK:
				value = "首充返现";
				break;
			case OrderDealType.CHARGE_TO_DRIVER_CASHBACK:
				value = "个人返现";
				break;
			case OrderDealType.DISCHARGE_TO_DRIVER_CHARGE:
				value = "充值冲红";
				break;
			case OrderDealType.DISCHARGE_TO_DRIVER_FIRSTCHARGE_CASHBACK:
				value = "首次充值返现冲红";
				break;
			case OrderDealType.DISCHARGE_TO_DRIVER_CASHBACK:
				value = "返现冲红";
				break;
			case OrderDealType.CHARGE_TO_DRIVER_DEDUCT_GASTATION_PREPAY:
				value = "加注站预付款余额扣除";
				break;
			case OrderDealType.DISCHARGE_TO_DRIVER_DEDUCT_GASTATION_PREPAY:
				value = "加注站预付款余额冲红";
				break;
			case OrderDealType.CHARGE_TO_TRANSPORTION_CHARGE:
				value = "车队充值";
				break;
			case OrderDealType.CHARGE_TO_GASTATION_CHARGE:
				value = "加注站预付款充值";
				break;
			case OrderDealType.CONSUME_DRIVER_DEDUCT:
				value = "个人消费";
				break;
			case OrderDealType.DISCONSUME_DRIVER_DEDUCT:
				value = "冲红";
				break;
			case OrderDealType.CONSUME_TRANSPORTION_DEDUCT:
				value = "车队消费";
				break;
			case OrderDealType.DISCONSUME_TRANSPORTION_DEDUCT:
				value = "转入";
				break;
			case OrderDealType.TRANSFER_TRANSPORTION_TO_DRIVER_DEDUCT_TRANSPORTION:
				value = "运输公司转出";
				break;
			case OrderDealType.TRANSFER_TRANSPORTION_TO_DRIVER_CASHBACK_TO_TRANSPORTION:
				value = "运输公司转出";
				break;
			case OrderDealType.TRANSFER_TRANSPORTION_TO_DRIVER_INCREASE_DRIVER:
				value = "个人转入";
				break;
			case OrderDealType.TRANSFER_DRIVER_TO_DRIVER_DEDUCT_DRIVER:
				value = "转入";
				break;
			case OrderDealType.TRANSFER_DRIVER_TO_DRIVER_INCREASE_DRIVER:
				value = "转出";
				break;
		}
		return value;
	}

	/*
	 * 加注站预付款操作历史表中的操作类型
	 */
	public interface SysPrepayOperate{
		public static final String CHARGE ="1";
		public static final String CHARGE_TO_DRIVER_DEDUCT ="2";
		public static final String DISCHARGE_TO_DRIVER_DEDUCT ="3";
		
	}
	
	/*
	 * 返现规则是否启用 ----对应 usysparm表的 CASHBACKSTATUS 记录
	 */
	public static final String CASHBACK_STATUS_ENABLE = "0";
	public static final String CASHBACK_STATUS_DISABLE = "1";
	
	/*
	 * 返现类型的编号
	 */
	public interface CashBackNumber{
		public static final String CASHBACK_TRANSFER_CHARGE ="101";//转账返现
		public static final String CASHBACK_CASH_CHARGE ="102";//现金充值
		public static final String CASHBACK_REGISTER ="201";//注册
		public static final String CASHBACK_FIRST_CHARGE ="202";//首次充值
		public static final String CASHBACK_INVITE ="203";//邀请
		public static final String CASHBACK_WEICHAT_CHARGE ="103";//微信充值
		public static final String CASHBACK_ALIPAY_CHARGE ="104";//支付宝充值
		public static final String CASHBACK_UNIONPAY_CHARGE ="105";//银联充值
		public static final String CASHBACK_CARD_CHARGE ="106";//充值卡充值
		public static final String CASHBACK_POS_CHARGE ="107";//POS充值
		public static final String CASHBACK_PLATFORM_CHARGE ="108";//后台充值
		public static final String APP_CONSUME_CHARGE ="220";//司机APP消费
	}

	/**
	 * 获取充值方式/返现类型
	 * @param key
	 * @return
     */
	public static String getCashBackNumber(String key){
		String value = "";
		switch (key){
			case CashBackNumber.CASHBACK_TRANSFER_CHARGE:
				value = "转账返现";
				break;
			case CashBackNumber.CASHBACK_CASH_CHARGE:
				value = "现金充值";
				break;
			case CashBackNumber.CASHBACK_REGISTER:
				value = "注册";
				break;
			case CashBackNumber.CASHBACK_FIRST_CHARGE:
				value = "首次充值";
				break;
			case CashBackNumber.CASHBACK_INVITE:
				value = "邀请";
				break;
			case CashBackNumber.CASHBACK_WEICHAT_CHARGE:
				value = "微信充值";
				break;
			case CashBackNumber.CASHBACK_ALIPAY_CHARGE:
				value = "支付宝充值";
				break;
			case CashBackNumber.CASHBACK_UNIONPAY_CHARGE:
				value = "银联充值";
				break;
			case CashBackNumber.CASHBACK_CARD_CHARGE:
				value = "充值卡充值";
				break;
			case CashBackNumber.CASHBACK_POS_CHARGE:
				value = "POS充值";
				break;
			case CashBackNumber.CASHBACK_PLATFORM_CHARGE:
				value = "后台充值";
				break;
		}
		return value;
	}
	
	/*
	 * 订单充值类型,与返现类型里面的充值编码的保持一致，但是不能用一个，因为逻辑上不一样，后面有可能增加
	 * 
	 */
	public interface OrderChargeType{
		public static final String CHARGETYPE_TRANSFER_CHARGE = GlobalConstant.CashBackNumber.CASHBACK_TRANSFER_CHARGE;
		public static final String CHARGETYPE_CASH_CHARGE = GlobalConstant.CashBackNumber.CASHBACK_CASH_CHARGE;
		public static final String CHARGETYPE_WEICHAT_CHARGE =GlobalConstant.CashBackNumber.CASHBACK_WEICHAT_CHARGE;
		public static final String CHARGETYPE_ALIPAY_CHARGE = GlobalConstant.CashBackNumber.CASHBACK_ALIPAY_CHARGE;
		public static final String CHARGETYPE_UNIONPAY_CHARGE =GlobalConstant.CashBackNumber.CASHBACK_UNIONPAY_CHARGE;
		public static final String CHARGETYPE_CARD_CHARGE =GlobalConstant.CashBackNumber.CASHBACK_CARD_CHARGE;
		public static final String CHARGETYPE_POS_CHARGE =GlobalConstant.CashBackNumber.CASHBACK_POS_CHARGE;
		public static final String CHARGETYPE_PLATFORM_CHARGE =GlobalConstant.CashBackNumber.CASHBACK_PLATFORM_CHARGE;
		public static final String APP_CONSUME_CHARGE =GlobalConstant.CashBackNumber.APP_CONSUME_CHARGE;
	}
	
	/*
	 * 是否充红订单 
	 */
	public static final String ORDER_ISCHARGE_YES = "1";
	public static final String ORDER_ISCHARGE_NO = "0";
	
	/*
	 * 订单是否已经被充红 
	 */
	public static final String ORDER_BEEN_DISCHARGED_YES = "1";
	public static final String ORDER_BEEN_DISCHARGED_NO = "0";
	/*
	 * 	订单处理结果标记
	 */
	public interface OrderProcessResult{
		public static final String SUCCESS ="SUCCESS";
		public static final String ORDER_IS_NULL="ORDER_IS_NULL";
		public static final String TRANSPORTION_IS_NULL="TRANSPORTION_IS_NULL";
		public static final String TCFLEET_IS_NULL="TCFLEET_IS_NULL";		
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
		
		public static final String CHARGE_TO_DRIVER_BY_CASH_OPERATOR_SOURCE_TYPE_IS_NULL="CHARGE_TO_DRIVER_BY_CASH_OPERATOR_SOURCE_TYPE_IS_NULL";
		
		public static final String DISCHARGE_ORDER_CAN_NOT_BE_DISCHARGE="DISCHARGE_ORDER_CAN_NOT_BE_DISCHARGE";
		public static final String DISCHARGE_ORDER_ID_IS_NULL="DISCHARGE_ORDER_ID_IS_NULL";
		public static final String DISCHARGE_ORDER_ORDERDEAL_IS_EMPTY="DISCHARGE_ORDER_ORDERDEAL_IS_EMPTY";
		public static final String DISCHARGE_ORDER_CASH_IS_NOT_NEGATIVE="DISCHARGE_ORDER_CASH_IS_NOT_NEGATIVE";
		public static final String DISCHARGE_ORDER_ORDERDEAL_NOT_RUNSUCCESS="DISCHARGE_ORDER_ORDERDEAL_NOT_RUNSUCCESS";
		public static final String DISCHARGE_ORDER_CASHBACK_IS_NULL = "DISCHARGE_ORDER_CASHBACK_IS_NULL";
		
		public static final String TRANSFER_CREDIT_ACCOUNT_IS_NULL ="TRANSFER_CREDIT_ACCOUNT_IS_NULL";
		public static final String TRANSFER_DEBIT_ACCOUNT_IS_NULL ="TRANSFER_DEBIT_ACCOUNT_IS_NULL";
		
		public static final String ORDER_ERROR_PREPAY_IS_NOT_ENOUGH = "余额不足！";
		public static final String ORDER_ERROR_UPDATE_GASTATION_PREYPAY_ERROR = "ORDER_ERROR_UPDATE_GASTATION_PREYPAY_ERROR";
		
		public static final String DEBIT_ACCOUNT_IS_NULL="DEBIT_ACCOUNT_IS_NULL";
		public static final String CREDIT_ACCOUNT_IS_NULL="CREDIT_ACCOUNT_IS_NULL";

		public static final String ORDER_ERROR_BALANCE_IS_NOT_ENOUGH = "余额不足！";

		public static final String ORDER_ERROR_CREDIT_ACCOUNT_IS_FROEN = "账号已冻结！";
		public static final String ORDER_ERROR_DEBIT_ACCOUNT_IS_FROEN = "账号已冻结！";
		public static final String ORDER_ERROR_CREDIT_ACCOUNT_CARD_IS_FROEN = "该卡已冻结！";
		
		public static final String DRIVER_NOT_CERTIFICATE_AND_CHARGE_SUM_BIG_THAN_LIMIT = "该司机未实名认证，充值金额不能大于"+DRIVER_NOT_CERTIFICATE_LIMIT.toString();
		public static final String DRIVER_NOT_CERTIFICATE = "该司机未实名认证。";
		
		public static final String ORDER_ACCOUNT_VERSION_HAVE_CHANGED = "ORDER_ACCOUNT_VERSION_HAVE_CHANGED";
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

	/*可类型/商品类型*/
	public interface  GasCardType{
		public static final String LNG = "10";
		public static final String CNG = "20";
		public static final String CNG01 = "2001";
		public static final String LNG01 = "1001";
		public static final String LNG02 = "1002";
	}

	/**
	 * 获取卡类型/商品类型
	 * @param key
	 * @return
     */
	public static String getGasCardType(String key){
		String value = "";
		switch (key){
			case GasCardType.LNG:
				value = "LNG";
				break;
			case GasCardType.CNG:
				value = "CNG";
				break;
			case GasCardType.CNG01:
				value = "CNG01";
				break;
			case GasCardType.LNG01:
				value = "LNG01";
				break;
			case GasCardType.LNG02:
				value = "LNG02";
				break;
		}
		return value;
	}
	/*
 * 卡类型 0车辆卡 1个人卡
 *
 */
	public interface CARD_PROPERTY{
		public static final String CARD_PROPERTY_TRANSPORTION = "0";
		public static final String CARD_PROPERTY_DRIVER = "1";
	}

	public interface USER_STATUS{
		public static final int NORMAL_STATUS = 0;
		public static final int FROZEN_STATUS = 1;
	}

	public interface USER_DELETE{
		public static final int NORMAL_STATUS = 1;
		public static final int DELETED_STATUS = 0;
	}

	public interface ConsumeType{
		public static final String CONSUME_TYPE_CARD = "1";
		public static final String CONSUME_TYPE_ACCOUNT = "0";
	}

	public interface Query_Condition {
		public static final String QUERY_CONDITION_ALL = "全部";
	}

//	产品网站问题/公告
	public interface QuestionNotice {
		public static final int NOTICE = 0;
		public static final int QUESTION = 1;
	}

	public interface ImgType {
		public static final String TOP = "0";//banner图
		public static final String TAILS = "1";//活动图
	}

	public interface HotCity{
		public static final Integer HOT=2;
		public static final Integer NOT_HOT=1;
	}
	
	public interface StationType{
		public static final String UNION = "0"; //合作站
		public static final String UN_UNION = "1"; //非合作站
	}

 /*
 * 	路况类型
 */
	public interface ConditionType{
		public static final String  HUAN_XING = "01"; //缓行
		public static final String SHI_GU ="02"; //事故
		public static final String FENG_LU ="03"; //封路
		public static final String ZAI_HAI ="04"; //自然灾害
		public static final String JIAN_CHA ="05"; //检查
		public static final String SHI_GONG ="06"; //施工
		public static final String XIAN_GAO ="07"; //限高
		public static final String XIAN_ZHONG ="08"; //限重
		public static final String ROAD_RADIUS ="500000"; //路况范围半径
		public static final String STATION_RADIUS ="500000"; //气站范围半径
	}

	/**
	 * 获取路况
	 * @param key
	 * @return
	 */
	public static String getConditionType(String key){
		String value = "";
		switch (key){
			case ConditionType.HUAN_XING:
				value = "缓行";
				break;
			case ConditionType.SHI_GU:
				value = "事故";
				break;
			case ConditionType.FENG_LU:
				value = "封路";
				break;
			case ConditionType.ZAI_HAI:
				value = "自然灾害";
				break;
			case ConditionType.JIAN_CHA:
				value = "检查";
				break;
			case ConditionType.SHI_GONG:
				value = "施工";
				break;
			case ConditionType.XIAN_GAO:
				value = "限高";
				break;
			case ConditionType.XIAN_ZHONG:
				value = "限重";
				break;
		}
		return value;
	}

	/*订单状态*/
	public interface ORDER_STATUS{
		public static final int ORDER_INIT = 0; 			//订单初始化
		public static final int ORDER_SUCCESS = 1; 			//订单生成成功
		public static final int ORDER_FAILED = 2; 			//订单失败
		public static final int ORDER_WAIT_FOR_PAY = 3; 	//订单待支付
	}

	/*订单消费类型*/
	public interface ORDER_SPEND_TYPE{
		public static final String CASH_BOX = "C01"; 		//账户余额支付
		public static final String POS = "C02"; 			//POS支付
		public static final String WECHAT = "C03"; 			//微信消费
		public static final String ALIPAY = "C04";          //支付宝消费
	}

	/*折扣优惠类型*/
	public interface PREFERENTIAL_TYPE{
		public static final String MINUS = "0"; 			//立减
		public static final String DISCOUNT = "1"; 		//折扣
	}

	/**
	 * 获取折扣优惠类型
	 * @param key
	 * @return
	 */
	public static String getPreferentialType(String key){
		String value = "";
		switch (key){
			case PREFERENTIAL_TYPE.MINUS:
				value = "立减";
				break;
			case PREFERENTIAL_TYPE.DISCOUNT:
				value = "折扣";
				break;
		}
		return value;
	}

	public interface COUPON_STATUS{
		public static final String UNUSE = "0";
		public static final String USED = "1";
		public static final String SUSPEND = "2";
	}
	
	public interface COUPONGROUP_TYPE{
		public static final String NEW_REGISTER_USER = "1";
		public static final String FIRST_CONSUME = "2";
		public static final String REGISTER_INVITE_FRIEND = "3";
		public static final String REGISTER_INVITED = "4";
	}
}
