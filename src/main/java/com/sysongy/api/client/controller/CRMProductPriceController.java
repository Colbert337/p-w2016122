package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.core.interceptors.DateConvertEditor;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.model.ProductPrice;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.gastation.service.ProductPriceService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/14.
 */

@Controller
@RequestMapping("/crmProductPriceService")
public class CRMProductPriceController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductPriceService productPriceService;

    @Autowired
    GsGasPriceService gsGasPriceService;

    @InitBinder
    public void InitBinder(HttpServletRequest request,
                           ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
    }


    @RequestMapping(value = {"/web/queryProductPriceInfo"})
    @ResponseBody
    public AjaxJson queryProductPriceInfo(HttpServletRequest request, HttpServletResponse response, ProductPrice productPrice) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            if((productPrice == null) || !StringUtils.isNotEmpty(productPrice.getProduct_id())){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("录入数据为空或ID为空！！！");
                return ajaxJson;
            }

            PageInfo<ProductPrice> productPriceInfos = productPriceService.queryProductPrice(productPrice);
            if((productPriceInfos == null) || (productPriceInfos.getList().size() == 0)){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("没有查询到对应的数据！");
                return ajaxJson;
            }

            attributes.put("productPricePageInfo", productPriceInfos);
            attributes.put("productPriceInfos", productPriceInfos.getList());
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_PRODUCT_PRICE_ERROR + e.getMessage());
            logger.error("queryGsPriceInfo error： " + e);
            e.printStackTrace();
        }
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/addProductPrice"})
    @ResponseBody
    public AjaxJson addProductPrice(HttpServletRequest request, HttpServletResponse response, ProductPrice productPrice) {
        AjaxJson ajaxJson = new AjaxJson();
        if((productPrice == null) || !StringUtils.isNotEmpty(productPrice.getId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("录入数据为空或ID为空！！！");
            return ajaxJson;
        }

        if(!StringUtils.isNotEmpty(productPrice.getProduct_id())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("关联商品ID为空！！！");
            return ajaxJson;
        }

        if(productPrice.getStartTime() == null){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("生效时间为空！！！");
            return ajaxJson;
        }

        try
        {
            Map<String, Object> attributes = new HashMap<String, Object>();
            int isExistPrice = productPriceService.isExists(productPrice);
            if(isExistPrice > 0){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该价格已被创建，不能重复添加！！！");
                return ajaxJson;
            }

            int updatePrice = productPriceService.updatePriceStatus(productPrice);
            if(updatePrice != 1){
                logger.warn("更新商品价格信息异常，更新记录数："  + updatePrice + ", 商品id为：" + productPrice.getProductPriceId());
            }

            GsGasPrice gsGasPrice = gsGasPriceService.queryGsPriceByPK(productPrice.getProduct_id());
            productPrice.setProductPriceStatus("1");
            productPrice.setProductPriceId(gsGasPrice.getGasNum());
            int renum = productPriceService.saveProductPrice(productPrice, "insert");
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无价格添加！！！");
                return ajaxJson;
            }

            gsGasPrice.setPrice_id(productPrice.getId());
            renum = gsGasPriceService.saveGsPrice(gsGasPrice, "update");
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无商品价格变动！！！");
                return ajaxJson;
            }

            attributes.put("productPrice", productPrice);
            ajaxJson.setAttributes(attributes);
        } catch (Exception e){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_ADD_PRODUCT_PRICE_ERROR + e.getMessage());
            logger.error("addproductPrice error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/updateProductPrice"})
    @ResponseBody
    public AjaxJson updateProductPrice(HttpServletRequest request, HttpServletResponse response, ProductPrice productPrice) {
        AjaxJson ajaxJson = new AjaxJson();

        if(!StringUtils.isNotEmpty(productPrice.getId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("未输入要更新的数据！！！");
            return ajaxJson;
        }

        if(!StringUtils.isNotEmpty(productPrice.getProductPriceId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("关联商品ID为空！！！");
            return ajaxJson;
        }

        try
        {
            Map<String, Object> attributes = new HashMap<String, Object>();
            int renum = productPriceService.saveProductPrice(productPrice, "update");
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无商品更新 ！！！");
                return ajaxJson;
            }
            attributes.put("productPrice", productPrice);
            ajaxJson.setAttributes(attributes);
        }catch (Exception e){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.UPDATE_CRM_PRODUCT_PRICE_ERROR + e.getMessage());
            logger.error("update product price error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/delProductPrice"})
    @ResponseBody
    public AjaxJson delProductPrice(HttpServletRequest request, HttpServletResponse response, ProductPrice productPrice){
        AjaxJson ajaxJson = new AjaxJson();
        if(!StringUtils.isNotEmpty(productPrice.getId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("未输入要删除的数据！！！");
            return ajaxJson;
        }
        try
        {
            int renum = productPriceService.delProductPrice(productPrice.getId());
            if(renum < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无商品价格删除 ！！！");
                return ajaxJson;
            }
        }catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.DELETE_CRM_PRODUCT_PRICE_ERROR + e.getMessage());
            logger.error("del product price error： " + e);
            e.printStackTrace();
        }
        return ajaxJson;
    }
}
