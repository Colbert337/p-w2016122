package com.sysongy.poms.crm.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.crm.model.CrmHelp;
import com.sysongy.poms.crm.model.CrmHelpType;
import com.sysongy.poms.crm.service.CrmHelpService;
import com.sysongy.poms.crm.service.CrmHelpTypeService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.service.MbBannerService;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
}
