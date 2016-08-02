package com.sysongy.poms.mobile.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.service.MbBannerService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: MbBannerController
 * @Encoding: UTF-8
 * @Package: com.sysongy.poms.mobile.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年08月01日, 14:40
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@RequestMapping("/web/mobile/img")
@Controller
public class MbBannerController extends BaseContoller{
    @Autowired
    MbBannerService mbBannerService;

    /**
     * 查询图片列表
     * @return
     */
    @RequestMapping("/list/page")
    public String queryMbBannerListPage(@ModelAttribute CurrUser currUser, MbBanner mbBanner, @RequestParam(required = false) Integer resultInt,
                                        @RequestParam(required = false) Integer userType, ModelMap map){
        String stationId = currUser.getStationId();
        if(mbBanner.getPageNum() == null){
            mbBanner.setPageNum(GlobalConstant.PAGE_NUM);
            mbBanner.setPageSize(GlobalConstant.PAGE_SIZE);
        }

        //封装分页参数，用于查询分页内容
        PageInfo<MbBanner> mbBannerPageInfo = new PageInfo<MbBanner>();
        mbBannerPageInfo = mbBannerService.queryMbBannerListPage(mbBanner);
        map.addAttribute("mbBannerList",mbBannerPageInfo.getList());
        map.addAttribute("pageInfo",mbBannerPageInfo);
        map.addAttribute("mbBanner",mbBanner);

        if(resultInt != null && resultInt > 0){
            Map<String, Object> resultMap = new HashMap<>();

            if(resultInt == 1){
                resultMap.put("retMsg","新建成功！");
            }else if(resultInt == 2){
                resultMap.put("retMsg","修改成功！");
            }
            map.addAttribute("ret",resultMap);
        }

        return "webpage/poms/mobile/banner_list";
    }

    /**
     * 跳转添加页面
     * @return
     */
    @RequestMapping("/add")
    public String addBanner( ModelMap map){
        return "webpage/poms/mobile/banner_add";
    }

    /**
     * 添加图片
     * @return
     */
    @RequestMapping("/save")
    public String saveBanner(@ModelAttribute CurrUser currUser, MbBanner mbBanner, ModelMap map){
        String stationId = currUser.getStationId();
        if(mbBanner != null && mbBanner.getMbBannerId() !=  null){
            //修改图片
            mbBanner.setUpdatedDate(new Date());
            mbBannerService.updateBanner(mbBanner);
        }else{
            //添加图片
            mbBannerService.saveBanner(mbBanner);
        }
        return "redirect:/web/mobile/img/list/page";
    }

    /**
     * 删除图片
     * @return
     */
    @RequestMapping("/delete")
    public String deleteBanner( MbBanner mbBanner,ModelMap map){
        return "redirect:/web/mobile/img/list/page";
    }

}
