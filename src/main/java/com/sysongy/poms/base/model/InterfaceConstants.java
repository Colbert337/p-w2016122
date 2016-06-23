package com.sysongy.poms.base.model;

/**
 * Created by Administrator on 2016/6/1.
 */
public class InterfaceConstants {

    public static final Integer USER_TYPE_GAS_STATION_USER = 1;    //气站用户

    public static final Integer USER_TYPE_TRANSPORTATION_COMPANY_USER = 2;  //运输公司

    public static final Integer USER_TYPE_CRM_USER = 3;     //CRM用户

    public static final Integer USER_TYPE_BLOC_USER = 4; //集团用户

    public static final Integer USER_TYPE_SYSTEM_USER = 5;  //后台用户

    public static final String ERROR_AUTHORITY = "请求认证失败！";

    public static final String WRONG_USERNAME_PASSWORD = "用户名或密码错误！";

    public static final String QUERY_CARD_ERROR = "查询实体卡失败！";

    public static final String DISTUBUTE_CARD_ERROR = "分发实体卡失败！";

    public static final String PUT_CARD_STORAGE_ERROR = "实体卡入库失败！";

    public static final String QUERY_CRM_USER_ERROR = "查询用户失败！";

    public static final String UPDATE_CRM_SYSUSER_ERROR = "更新用户失败！";

    public static final String QUERY_CRM_STSTION_ERROR = "查询气站失败！";

    public static final String QUERY_CRM_SEND_MSG_ERROR = "发送验证码失败！";

    public static final String QUERY_CRM_ADD_USER_ERROR = "添加用户失败！";

    public static final String DELETE_CRM_USER_ERROR = "删除用户失败！";

    public static final String UPDATE_CRM_USER_ERROR = "更新用户失败！";

    public static final String DISTRIBUTE_CRM_USER_CARD_ERROR = "给司机用户发卡失败！";

    public static final String QUERY_CRM_GAS_PRICE_ERROR = "查询气品失败！";

    public static final String QUERY_CRM_ADD_GAS_PRICE_ERROR = "添加气品失败！";

    public static final String DELETE_CRM_GAS_PRICE_ERROR = "删除气品失败！";

    public static final String DELETE_CRM_PRODUCT_PRICE_ERROR = "删除商品失败！";

    public static final String UPDATE_CRM_GAS_PRICE_ERROR = "更新气品失败！";

    public static final String UPDATE_CRM_PRODUCT_PRICE_ERROR = "更新商品失败！";

    public static final String QUERY_CRM_PRODUCT_PRICE_ERROR = "查询价格失败！";

    public static final String QUERY_CRM_ADD_PRODUCT_PRICE_ERROR = "添加价格失败！";

    public static final String CARD_STSTUS_LOCK = "0";  // 0:已冻结

    public static final String CARD_STSTUS_STORAGE = "1";   //1：已入库

    public static final String CARD_STSTUS_OUT_STORAGE = "2";    //2：已出库；

    public static final String CARD_STSTUS_ALREADY_SEND = "3";   //3:已/未发放

    public static final String CARD_STSTUS_IN_USE = "4";         //4:使用中

    public static final String CARD_STSTUS_INVALID = "5";         //5:已失效
}
