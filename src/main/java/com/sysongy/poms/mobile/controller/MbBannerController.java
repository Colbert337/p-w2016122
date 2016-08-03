package com.sysongy.poms.mobile.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.card.model.GasCard;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.service.MbBannerService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.tcms.advance.model.TcVehicle;
import com.sysongy.util.Decoder;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String queryMbBannerListPage(@ModelAttribute CurrUser currUser, MbBanner mbBanner, @RequestParam(required = false) String resultVal,
                                        ModelMap map) throws Exception{
        String stationId = currUser.getStationId();
        if(mbBanner.getPageNum() == null){
            mbBanner.setPageNum(GlobalConstant.PAGE_NUM);
            mbBanner.setPageSize(GlobalConstant.PAGE_SIZE);
        }

        //封装分页参数，用于查询分页内容
        Map<String, Object> resultMap = new HashMap<>();
        if(resultVal != null && !"".equals(resultVal)){
            resultVal = Decoder.symmetricDecrypto(resultVal);
            resultMap.put("retMsg",resultVal);
        }

        PageInfo<MbBanner> mbBannerPageInfo = new PageInfo<MbBanner>();
        mbBannerPageInfo = mbBannerService.queryMbBannerListPage(mbBanner);
        map.addAttribute("mbBannerList",mbBannerPageInfo.getList());
        map.addAttribute("pageInfo",mbBannerPageInfo);
        map.addAttribute("mbBanner",mbBanner);
        map.addAttribute("ret",resultMap);
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
     * 查询图片信息
     * @param map
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public MbBanner queryBanner(MbBanner banner, ModelMap map){
        MbBanner mbBanner = mbBannerService.queryMbBanner(banner);
        return mbBanner;
    }
    /**
     * 添加图片
     * @return
     */
    @RequestMapping("/save")
    public String saveBanner(@ModelAttribute CurrUser currUser, MbBanner mbBanner, ModelMap map) throws Exception{
        String stationId = currUser.getStationId();
        String resultVal = "";
        if(mbBanner != null && mbBanner.getMbBannerId() !=  null && !"".equals(mbBanner.getMbBannerId())){
            //修改图片
            mbBanner.setUpdatedDate(new Date());
            mbBanner.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
            mbBannerService.updateBanner(mbBanner);
            resultVal = "修改成功！";
            resultVal = Encoder.symmetricEncrypto(resultVal);
        }else{
            //查询当前图片最大序号
            MbBanner maxBannerIndex = mbBannerService.queryMaxIndex(GlobalConstant.ImgType.BANNER);
            if(maxBannerIndex != null){
                mbBanner.setSort(maxBannerIndex.getSort()+1);
            }else{
                mbBanner.setSort(0);
            }
            //添加图片
            mbBanner.setMbBannerId(UUIDGenerator.getUUID());
            mbBanner.setImgType(GlobalConstant.ImgType.BANNER);
            mbBannerService.saveBanner(mbBanner);
            resultVal = "添加成功！";
            resultVal = Encoder.symmetricEncrypto(resultVal);
        }
        return "redirect:/web/mobile/img/list/page?resultVal="+resultVal;
    }

    /**
     * 删除图片
     * @return
     */
    @RequestMapping("/delete")
    public String deleteBanner( MbBanner mbBanner,ModelMap map) throws Exception{
        mbBanner.setIsDeleted(GlobalConstant.STATUS_DELETE);
        String resultVal = "删除成功！";
        resultVal = Encoder.symmetricEncrypto(resultVal);
        mbBannerService.updateBanner(mbBanner);
        return "redirect:/web/mobile/img/list/page?resultVal="+resultVal;
    }

}
