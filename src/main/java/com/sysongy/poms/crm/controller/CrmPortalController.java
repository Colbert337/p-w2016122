package com.sysongy.poms.crm.controller;

import java.io.File;
import java.util.*;

import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.verification.MobileVerification;
import com.sysongy.api.mobile.tools.verification.MobileVerificationUtils;
import com.sysongy.api.util.DESUtil;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.util.*;
import com.sysongy.util.pojo.AliShortMessageBean;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sysongy.api.mobile.model.feedback.MbUserSuggest;
import com.sysongy.api.mobile.service.MbUserSuggestServices;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.crm.model.CrmHelp;
import com.sysongy.poms.crm.model.CrmHelpType;
import com.sysongy.poms.crm.service.CrmHelpService;
import com.sysongy.poms.crm.service.CrmHelpTypeService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.service.MbBannerService;
import com.sysongy.poms.mobile.service.SysRoadService;
import com.sysongy.poms.page.model.SysStaticPage;
import com.sysongy.poms.page.service.SysStaticPageService;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @FileName: CrmPortalController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.crm.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月05日, 9:53
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@RequestMapping("/portal/crm/help")
@Controller
public class CrmPortalController {
    @Autowired
    private CrmHelpService crmHelpService;
    @Autowired
    private CrmHelpTypeService crmHelpTypeService;
    @Autowired
    GastationService gastationService;
    @Autowired
    GsGasPriceService gsGasPriceService;
    @Autowired
    MbBannerService mbBannerService;
    @Autowired
    SysCashBackService sysCashBackService;
    @Autowired
    SysStaticPageService service;
    @Autowired
    SysRoadService sysRoadService;
    @Autowired
    UsysparamService usysparamService;
    @Autowired
    MbUserSuggestServices mbUserSuggestServices;
    @Autowired
    RedisClientInterface redisClientImpl;
    @Autowired
    DriverService driverService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    /**
     * 问题列表和分类查询
     * @param model
     * @param crmHelp
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/all")
    public String queryAllList(Model model, CrmHelp crmHelp, CrmHelpType crmHelpType) throws Exception{
    	
    	crmHelp.setIsNotice(1);
        List<Map<String, Object>> crmHelpList = crmHelpService.queryCrmHelpList(crmHelp);
        model.addAttribute("crmHelpList", crmHelpList);
        //问题分类列表
        List<CrmHelpType> crmHelpTypeList = crmHelpTypeService.queryCrmHelpTypeList(crmHelpType);
        model.addAttribute("crmHelpTypeList", crmHelpTypeList);

        return "webpage/crm/hp_queston";
    }

    /**
     * 根据问题分类查询相应的问题
     * @param model
     * @param crmHelpTypeId
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/type/list")
    public String queryHelpQuestionType(Model model,@RequestParam(value="crmHelpTypeId",required = false) String crmHelpTypeId )throws Exception{
    	//问题列表
    	List<CrmHelpType> crmHelpTypeList = crmHelpTypeService.queryCrmHelpTypeList(null);
        model.addAttribute("crmHelpTypeList", crmHelpTypeList);
    	//根据不同的问题分类查询问题列表
   	    List<CrmHelp> crmHelpList = crmHelpService.queryCrmHelpServiceList(crmHelpTypeId);
    	model.addAttribute("crmHelpList", crmHelpList);

        model.addAttribute("crmHelpList", crmHelpList);
        return "webpage/crm/hp_queston";
    }
    /**
     * 公告列表
     * @param model
     * @param crmHelp
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/notice")
    public String queryNoticeList(Model model, CrmHelp crmHelp) throws Exception{

    	crmHelp.setIsNotice(2);
        List<Map<String, Object>> crmHelpList = crmHelpService.queryCrmHelpList(crmHelp);       
        model.addAttribute("crmHelpList", crmHelpList);
        return "webpage/crm/hp_notice";
    }
    /**
     * 公告信息列表
     * @param model
     * @param crmHelpId
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/notice/info")
    public String queryNoticeInfoList(Model model,@RequestParam(value="crmHelpId",required = false) String crmHelpId)throws Exception{
    	
    	List<CrmHelp> noticeInfoList = crmHelpService.queryCrmHelpNoticeInfo(crmHelpId);
    	model.addAttribute("noticeInfoList", noticeInfoList);
    	return "webpage/crm/hp_notice_info";
    	
    }


/****************************************APP分享H5页面******************************************/
    /**
     * 图文详情页
     * @return
     */
    @RequestMapping("/content")
    public String queryContentInfo(@RequestParam String contentId,ModelMap map) throws Exception{
        MbBanner mbBanner = new MbBanner();
        mbBanner.setMbBannerId(contentId);
        mbBanner = mbBannerService.queryMbBanner(mbBanner);
        map.addAttribute("mbBanner",mbBanner);

        //统计活动阅读数
        String viewCount = mbBanner.getViewCount();
        viewCount = String.valueOf(Integer.parseInt(viewCount)+1);
        mbBanner.setViewCount(viewCount);
        mbBannerService.updateBanner(mbBanner);
        return "/webpage/crm/webapp-active-detail";
    }

    /**
     * 货源详情页
     * @return
     */
    @RequestMapping("/supply")
    public String querySupplyInfo(@RequestParam String supplyId,ModelMap map) throws Exception{

        return "redirect:/web/mobile/img/list/page?resultVal=";
    }

    /**
     * 站点详情页
     * @return
     */
    @RequestMapping("/station")
    public String queryStationInfo(@RequestParam String stationId,ModelMap map) throws Exception{
        //获取站点信息
        Gastation gastation = gastationService.queryGastationByPK(stationId);
        //获取当前气站价格列表
        List<Map<String, Object>> priceList = gsGasPriceService.queryPriceList(stationId);
        map.addAttribute("gastation",gastation);
        map.addAttribute("priceList",priceList);

        //统计分享数
        String viewCount = gastation.getViewCount();
        viewCount = String.valueOf(Integer.parseInt(viewCount)+1);
        gastation.setViewCount(viewCount);
        gastationService.updateByPrimaryKeySelective(gastation);
        return "/webpage/crm/webapp-station-detail";
    }

    /**
     * 返现规则
     * @return
     */
    @RequestMapping("/deal/backRulePage")
    public String queryBackRulePage(SysCashBack sysCashBack, ModelMap map) throws Exception{

        List<SysCashBack> cashBackList = new ArrayList<>();

        sysCashBack.setPageSize(10);
        sysCashBack.setPageNum(1);
        //查询微信返现规则
        sysCashBack.setSys_cash_back_no(GlobalConstant.CashBackNumber.CASHBACK_WEICHAT_CHARGE);
        sysCashBack.setStatus("0");
        PageInfo<SysCashBack> pageinfo = sysCashBackService.queryCashBack(sysCashBack);
        if(pageinfo != null && pageinfo.getList() != null){
            cashBackList = pageinfo.getList();
        }
        map.addAttribute("wechatCashBack",cashBackList);

        //查询支付宝返现规则
        sysCashBack.setSys_cash_back_no(GlobalConstant.CashBackNumber.CASHBACK_ALIPAY_CHARGE);
        sysCashBack.setStatus("0");
        PageInfo<SysCashBack> pageInfoList = sysCashBackService.queryCashBack(sysCashBack);
        if(pageInfoList != null && pageInfoList.getList() != null){
            cashBackList = pageInfoList.getList();
        }

        map.addAttribute("aliPayCashBack",cashBackList);

        return "/webpage/crm/webapp-bonus-rules";
    }

    @RequestMapping("/showPage")
    public String showPage(ModelMap map, @RequestParam String pageid) throws Exception{
        PageBean bean = new PageBean();
        String ret = "webpage/poms/page/page";

        try {
            SysStaticPage page = service.queryPageByPK(pageid);
            bean.setRetCode(100);
            bean.setRetValue(pageid);
            bean.setPageInfo(ret);
            map.addAttribute("page", page);
            map.addAttribute("ret", bean);
        } catch (Exception e) {
            bean.setRetCode(5000);
            bean.setRetMsg(e.getMessage());
            map.addAttribute("ret", bean);
            map.addAttribute("message", pageid);
            e.printStackTrace();
        }
        finally {
            return ret;
        }
    }
    /**
     * 路况详情
     */
    @RequestMapping("/trafficDetail")
    public String trafficDetail(@RequestParam String trafficId,ModelMap map) throws Exception{
    	SysRoadCondition roadCondition = sysRoadService.selectByPrimaryKey(trafficId);
    	Usysparam usysparam = usysparamService.queryUsysparamByCode("CONDITION_TYPE", roadCondition.getConditionType());
        map.addAttribute("roadCondition", roadCondition);
        map.addAttribute("ConditionType", usysparam.getMname());
        return "/webpage/crm/webapp-traffic-detail";
    }
    /**
     * 反馈信息
     */
    @RequestMapping("/suggest")
    public String suggest(@RequestParam String title,@RequestParam String info) throws Exception{
    	MbUserSuggest ms = new MbUserSuggest ();
    	ms.setMbUserSuggestId(UUIDGenerator.getUUID());
    	ms.setMobilePhone(title);
    	ms.setSuggest(info);
    	ms.setSuggestRes("来自APP");
    	int rs = mbUserSuggestServices.saveSuggester(ms);
    	if(rs > 0){
    		return "/webpage/crm/webapp-download-app";
    	}else{
    		return "/webpage/crm/webapp-download-app";
    	}
    }
    /**
     * 图文分享详情页
     * @return
     */
    @RequestMapping("/share/content")
    public String queryShareContentInfo(@RequestParam String contentId,ModelMap map) throws Exception{
        MbBanner mbBanner = new MbBanner();
        mbBanner.setMbBannerId(contentId);
        mbBanner = mbBannerService.queryMbBanner(mbBanner);
        map.addAttribute("mbBanner",mbBanner);

        //统计活动阅读数
        String viewCount = mbBanner.getViewCount();
        viewCount = String.valueOf(Integer.parseInt(viewCount)+1);
        mbBanner.setViewCount(viewCount);
        mbBannerService.updateBanner(mbBanner);
        return "/webpage/crm/webapp-active-share";
    }

    /**
     * 站点分享详情页
     * @return
     */
    @RequestMapping("/share/station")
    public String queryShareStationInfo(@RequestParam String stationId,ModelMap map) throws Exception{
        //获取站点信息
        Gastation gastation = gastationService.queryGastationByPK(stationId);
        //获取当前气站价格列表
        List<Map<String, Object>> priceList = gsGasPriceService.queryPriceList(stationId);
        map.addAttribute("gastation",gastation);
        map.addAttribute("priceList",priceList);

        //统计分享数
        String viewCount = gastation.getViewCount();
        viewCount = String.valueOf(Integer.parseInt(viewCount)+1);
        gastation.setViewCount(viewCount);
        gastationService.updateByPrimaryKeySelective(gastation);
        return "/webpage/crm/webapp-station-share";
    }

    /**
     * 被邀请用户注册
     * @return
     */
    @RequestMapping("/user/register")
    public String savaUser(@RequestParam String phone,@RequestParam String vcode, @RequestParam String invitationCode,  ModelMap map) throws Exception{

        if(phone != null){
            SysDriver driver = new SysDriver();
            driver.setUserName(phone);
            driver.setMobilePhone(phone);

            String veCode = (String) redisClientImpl.getFromCache(driver.getMobilePhone());
            if(vcode != null && !"".equals(veCode)) {
                List<SysDriver> driverlist = driverService.queryeSingleList(driver);
                if (driverlist != null && driverlist.size() > 0) {
                    logger.info("该手机号已注册！");
                    //throw new Exception(MobileRegisterUtils.RET_DRIVER_MOBILE_REGISTED);
                } else {
                    String sysDriverId = UUIDGenerator.getUUID();
                    driver.setPassword(Encoder.MD5Encode("111111".getBytes()));
                    driver.setSysDriverId(sysDriverId);
                    driver.setRegisSource("APP");
                    driver.setMemo(invitationCode);
                    String encoderContent=phone;
                    //图片路径
                    String rootPath = (String) prop.get("images_upload_path")+ "/driver/";
                    File file =new File(rootPath);
                    //如果根文件夹不存在则创建
                    if  (!file.exists()  && !file.isDirectory()){
                        file.mkdir();
                    }
                    String path = rootPath+phone+"/";
                    File file1 =new File(path);
                    //如果用户文件夹不存在则创建
                    if  (!file1.exists()  && !file1.isDirectory()){
                        file1.mkdir();
                    }
                    //二维码路径
                    String imgPath = path+phone+".jpg";
                    String show_path = (String) prop.get("show_images_path")+ "/driver/"+phone+"/"+phone+".jpg";
                    //生成二维码
                    driver.setDriverQrcode(show_path);

                    Integer tmp = driverService.saveDriver(driver, "insert", null);
                    if(tmp > 0){
                        TwoDimensionCode handler = new TwoDimensionCode();
                        handler.encoderQRCode(encoderContent,imgPath, TwoDimensionCode.imgType,null, TwoDimensionCode.size);
                    }

                }
            }else{
                logger.info("验证码无效！");
            }
        }

        return "/webpage/crm/webapp-download-app";
    }

    /**
     * 邀请用户页面内容
     * @return
     */
    @RequestMapping("/user/invitation")
    public String queryUserInvitation(@RequestParam String token,  ModelMap map) throws Exception{
        SysDriver sysDriver = new SysDriver();
        try {
            if(token != null){
                sysDriver = driverService.queryDriverByPK(token);
                if(sysDriver != null){
                    String fullName = sysDriver.getFullName();
                    if(fullName != null && !"".equals(fullName)){
                        map.addAttribute("name",fullName);
                    }else{
                        String phoneNum = sysDriver.getMobilePhone();
                        if(phoneNum != null && !"".equals(phoneNum)){
                            phoneNum = phoneNum.substring(0,3) + "****" + phoneNum.substring(8,phoneNum.length());
                        }
                        map.addAttribute("name",phoneNum);
                        map.addAttribute("invitationCode",sysDriver.getInvitationCode());
                    }
                }
                map.addAttribute("sysDriver",sysDriver);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/webpage/crm/webapp-share";
    }

    /**
     * 获取短信验证码
     * @return
     */
    @RequestMapping(value = {"/user/getCode"})
    @ResponseBody
    public AjaxJson getVerificationCode(@RequestParam String mobilePhone, @RequestParam String msgType) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            /**
             * 请求接口
             */
            if(mobilePhone != null && msgType != null){
                Integer checkCode = (int) ((Math.random() * 9 + 1) * 100000);
                AliShortMessageBean aliShortMessageBean = new AliShortMessageBean();
                aliShortMessageBean.setSendNumber(mobilePhone);
                aliShortMessageBean.setCode(checkCode.toString());
                aliShortMessageBean.setProduct("司集能源科技平台");
                String key = GlobalConstant.MSG_PREFIX + mobilePhone;
                redisClientImpl.addToCache(key, checkCode.toString(), InterfaceConstants.SHORT_MSEEAGE_CODE_EXPIRE_TIME);

                if(msgType.equalsIgnoreCase("changePassword")){
                    AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_CHANGE_PASSWORD);
                } else {
                    AliShortMessage.sendShortMessage(aliShortMessageBean, AliShortMessage.SHORT_MESSAGE_TYPE.USER_REGISTER);
                }
                ajaxJson.setMsg("验证码发送成功！");
            }else{
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("手机号为空！！！");
                    return ajaxJson;
            }

        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_SEND_MSG_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
        }
        return ajaxJson;
    }
}
