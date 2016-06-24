package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.model.ProductPrice;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.gastation.service.ProductPriceService;
import com.sysongy.poms.liquid.model.SysGasSource;
import com.sysongy.poms.liquid.service.LiquidService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.util.UUIDGenerator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/14.
 */

@Controller
@RequestMapping("/crmGasPriceService")
public class CRMGasPriceController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GsGasPriceService gsGasPriceService;

    @Autowired
    private LiquidService service;

    @Autowired
    ProductPriceService productPriceService;

    @RequestMapping(value = {"/web/queryGsPriceInfo"})
    @ResponseBody
    public AjaxJson queryGsPriceInfo(HttpServletRequest request, HttpServletResponse response, GsGasPrice gsGasPrice) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();

        if(StringUtils.isEmpty(gsGasPrice.getSysGasStationId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("气站ID为空！！！");
            return ajaxJson;
        }

        try
        {
            PageInfo<GsGasPrice> gsGasPrices = gsGasPriceService.queryGsPrice(gsGasPrice);
            attributes.put("gsGasPricePageInfo", gsGasPrices);

            if((gsGasPrices == null) || (gsGasPrices.getList().size() == 0)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("没有查询到对应的数据！");
                return ajaxJson;
            }
            attributes.put("gsGasPrices", gsGasPrices.getList());
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_GAS_PRICE_ERROR + e.getMessage());
            logger.error("queryGsPriceInfo error： " + e);
            e.printStackTrace();
        }
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/addGsGasPrice"})
    @ResponseBody
    public AjaxJson addGsGasPrice(HttpServletRequest request, HttpServletResponse response, GsGasPrice gsGasPrice) {
        AjaxJson ajaxJson = new AjaxJson();
        if((gsGasPrice == null) || !StringUtils.isNotEmpty(gsGasPrice.getGsGasPriceId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("录入数据为空或ID为空！！！");
            return ajaxJson;
        }

        if(StringUtils.isEmpty(gsGasPrice.getSysGasStationId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("气站ID为空！！！");
            return ajaxJson;
        }

        try
        {
            String strPrice = request.getParameter("price");
            if(StringUtils.isEmpty(strPrice)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("输入价格为空！！！");
                return ajaxJson;
            }
            double lPrice = Double.valueOf(strPrice);
            gsGasPrice = createProductPrice(gsGasPrice, lPrice);

            Map<String, Object> attributes = new HashMap<String, Object>();
            int isExistDriver = gsGasPriceService.isExists(gsGasPrice);
            if(isExistDriver > 0){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该气品已被创建，不能重复添加！！！");
                return ajaxJson;
            }

            int renum = gsGasPriceService.saveGsPrice(gsGasPrice, "insert");
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无用户添加！！！");
                return ajaxJson;
            }
            GsGasPrice gsGasPriceInfo = gsGasPriceService.queryGsPriceByPK(gsGasPrice.getGsGasPriceId());
            attributes.put("gsGasPrice", gsGasPriceInfo);
            ajaxJson.setAttributes(attributes);
        } catch (Exception e){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_ADD_GAS_PRICE_ERROR + e.getMessage());
            logger.error("addGsGasPrice error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }

    private GsGasPrice createProductPrice(GsGasPrice gsGasPrice, double lPrice){
        ProductPrice productPrice = new ProductPrice();
        productPrice.setId(UUIDGenerator.getUUID());
        productPrice.setProductPriceStatus("1");
        productPrice.setCreateTime(new Date());
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) + 20);
        Date finishTime = curr.getTime();
        productPrice.setFinishTime(finishTime);
        productPrice.setProductPrice(lPrice);
        productPrice.setProduct_price_type("1");
        productPrice.setProduct_id(gsGasPrice.getGsGasPriceId());
        productPrice.setProductPriceId(gsGasPrice.getGasName());
        productPrice.setProductUnit(gsGasPrice.getUnit());
        try
        {
            productPriceService.saveProductPrice(productPrice, "insert");
        }catch (Exception e){
            logger.error("createProductPrice error： " + e);
        }
        gsGasPrice.setPrice_id(productPrice.getId());
        return gsGasPrice;
    }

    @RequestMapping(value = {"/web/updateGsGasPrice"})
    @ResponseBody
    public AjaxJson updateGsGasPrice(HttpServletRequest request, HttpServletResponse response, GsGasPrice gsGasPrice) {
        AjaxJson ajaxJson = new AjaxJson();
        if(!StringUtils.isNotEmpty(gsGasPrice.getGsGasPriceId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("未输入要更新的数据！！！");
            return ajaxJson;
        }

        if(!StringUtils.isNotEmpty(gsGasPrice.getGsGasPriceId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("气站ID为空！！！");
            return ajaxJson;
        }

        try {
            Map<String, Object> attributes = new HashMap<String, Object>();
            int renum = gsGasPriceService.saveGsPrice(gsGasPrice, "update");
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无气品更新 ！！！");
                return ajaxJson;
            }
            GsGasPrice gsGasPriceInfo = gsGasPriceService.queryGsPriceByPK(gsGasPrice.getGsGasPriceId());
            attributes.put("gsGasPrice", gsGasPriceInfo);
            ajaxJson.setAttributes(attributes);
        }catch (Exception e){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.UPDATE_CRM_GAS_PRICE_ERROR + e.getMessage());
            logger.error("update gsGasPrice error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/delGsGasPrice"})
    @ResponseBody
    public AjaxJson delGsGasPrice(HttpServletRequest request, HttpServletResponse response, GsGasPrice gsGasPrice){
        AjaxJson ajaxJson = new AjaxJson();
        if(!StringUtils.isNotEmpty(gsGasPrice.getGsGasPriceId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("未输入要删除的数据！！！");
            return ajaxJson;
        }
        try
        {
            int renum = gsGasPriceService.delGsPrice(gsGasPrice.getGsGasPriceId());
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无气品删除 ！！！");
                return ajaxJson;
            }
        }catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.DELETE_CRM_GAS_PRICE_ERROR + e.getMessage());
            logger.error("delGsGasPrice error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/queryAllGasSourceList"})
    @ResponseBody
    public AjaxJson queryAllGasSourceList(ModelMap map, SysGasSource gasource) throws Exception{
        AjaxJson ajaxJson = new AjaxJson();
        try {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if(StringUtils.isEmpty(gasource.getOrderby())){
                gasource.setOrderby("created_date desc");
            }
            PageInfo<SysGasSource> pageinfo = service.querySysGasSource(gasource);
            if((pageinfo == null) || (pageinfo.getList().size() == 0)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无气品来源 ！！！");
                return ajaxJson;
            }
            attributes.put("liquidSource", pageinfo.getList());
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.DELETE_CRM_GAS_PRICE_ERROR + e.getMessage());
            logger.error("queryAllGasSourceList error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }
}
