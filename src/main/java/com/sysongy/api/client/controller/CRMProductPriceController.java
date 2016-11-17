package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.core.interceptors.DateConvertEditor;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.model.ProductPrice;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.gastation.service.ProductPriceService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.model.SysOperationLog;
import com.sysongy.poms.system.service.SysOperationLogService;
import com.sysongy.util.DateTimeHelper;
import com.sysongy.util.GlobalConstant;
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
@RequestMapping("/crmInterface/crmProductPriceService")
public class CRMProductPriceController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductPriceService productPriceService;

    @Autowired
    GsGasPriceService gsGasPriceService;

    @Autowired
    GastationService gastationService;
	@Autowired
	SysOperationLogService sysOperationLogService;
	
	@Autowired
	SysUserService sysUserService;

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
    	SysUser user = new SysUser();
    	user.setUserName(request.getParameter("suserName"));
    	user.setPassword(request.getParameter("spassword"));
    	SysUser sysUser = sysUserService.queryUserByUserInfo(user);
        try
        {
            Map<String, Object> attributes = new HashMap<String, Object>();
            int isExistPrice = productPriceService.isExists(productPrice);
            if(isExistPrice > 0){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该价格已被创建，不能重复添加！！！");
                return ajaxJson;
            }

            //查询气品名称
            GsGasPrice gsGasPrice = gsGasPriceService.queryGsPriceByPK(productPrice.getProduct_id());
            productPrice.setProductPriceStatus("1");
            productPrice.setProductPriceId(gsGasPrice.getGasName());

            String gastationId = productPrice.getGaStationId();
            Gastation gastation = gastationService.queryGastationByPK(gastationId);
            String effectiveTime = gastation.getPrice_effective_time();
            Date startTime = new Date();

            if(effectiveTime != null && effectiveTime.equals(GlobalConstant.PRICE_EFFECTIVE_TIME.TWELVE+"")){//12小时后生效
                startTime = productPrice.getStartTime();//获取当前时间12小时后的时间
                Date currntTime = new Date();
                double resultVal = DateTimeHelper.getHoursOfHoursTwoDate(startTime,currntTime);
                if(resultVal >= GlobalConstant.PRICE_EFFECTIVE_TIME.TWELVE){
                    productPrice.setProductPriceStatus(GlobalConstant.PRICE_STATUS.ENACTMENT);
                    int renum = productPriceService.saveProductPrice(productPrice, "insert");
        			//系统关键日志记录
        			SysOperationLog aSysOperationLog = new SysOperationLog();
        	    	aSysOperationLog.setUser_name(user.getUserName());
        			aSysOperationLog.setOperation_type("tj");
        			aSysOperationLog.setLog_platform("3");
        			aSysOperationLog.setLog_content("用户添加新价格成功！调价商品为："+productPrice.getProductPriceId()+"，调整价格为："+productPrice.getProductPrice());
        			//操作日志
        			sysOperationLogService.saveOperationLog(aSysOperationLog,sysUser.getSysUserId());
                    if(renum < 1){
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("无价格添加！");
                        return ajaxJson;
                    }
                }else{
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("生效时间必须为12小时之后！");
                    return ajaxJson;
                }
            }else if(effectiveTime != null && effectiveTime.equals(GlobalConstant.PRICE_EFFECTIVE_TIME.TWENTY+"")){//24小时后生效
                startTime = productPrice.getStartTime();
                Date currntTime = new Date();
                double resultVal = DateTimeHelper.getHoursOfHoursTwoDate(startTime,currntTime);
                if(resultVal >= GlobalConstant.PRICE_EFFECTIVE_TIME.TWENTY){
                    productPrice.setProductPriceStatus(GlobalConstant.PRICE_STATUS.ENACTMENT);
                    int renum = productPriceService.saveProductPrice(productPrice, "insert");
        			//系统关键日志记录
        			SysOperationLog aSysOperationLog = new SysOperationLog();
        			aSysOperationLog.setUser_name(user.getUserName());
        			aSysOperationLog.setOperation_type("tj");
        			aSysOperationLog.setLog_platform("3");
        			aSysOperationLog.setLog_content("用户添加新价格成功！调价商品为："+productPrice.getProductPriceId()+"，调整价格为："+productPrice.getProductPrice());
        			//操作日志
        			sysOperationLogService.saveOperationLog(aSysOperationLog,sysUser.getSysUserId());
                    if(renum < 1){
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("无价格添加！");
                        return ajaxJson;
                    }
                }else{
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("生效时间必须为24小时之后！");
                    return ajaxJson;
                }
            }else{//立即生效
                int updatePrice = productPriceService.updatePriceStatus(productPrice);//将所有价格状态改为无效
    			//系统关键日志记录
    			SysOperationLog sysOperationLog = new SysOperationLog();
    			sysOperationLog.setUser_name(user.getUserName());
    			sysOperationLog.setOperation_type("tj");
    			sysOperationLog.setLog_platform("3");
        		sysOperationLog.setLog_content("用户将所有价格状态改为无效成功！调价商品为："+productPrice.getProductPriceId()+"，调整价格为："+productPrice.getProductPrice()); 
    			//操作日志
    			sysOperationLogService.saveOperationLog(sysOperationLog,sysUser.getSysUserId());
                if(updatePrice != 1){
                    logger.warn("更新商品价格信息异常，更新记录数："  + updatePrice + ", 商品id为：" + productPrice.getProductPriceId());
                }

                productPrice.setProductPriceStatus(GlobalConstant.PRICE_STATUS.VALID);
                productPrice.setStartTime(new Date());
                int renum = productPriceService.saveProductPrice(productPrice, "insert");//添加新价格
    			//系统关键日志记录
    			SysOperationLog aSysOperationLog = new SysOperationLog();
    			sysOperationLog.setUser_name(user.getUserName());
    			aSysOperationLog.setOperation_type("tj");
    			aSysOperationLog.setLog_platform("4");
    			aSysOperationLog.setLog_content("用户添加新价格成功！调价商品为："+productPrice.getProductPriceId()+"，调整价格为："+productPrice.getProductPrice()); 
    			//操作日志
    			sysOperationLogService.saveOperationLog(aSysOperationLog,sysUser.getSysUserId());
                if(renum < 1){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("无价格添加！！！");
                    return ajaxJson;
                }

                //更新商品价格关系
                gsGasPrice.setPrice_id(productPrice.getId());
                int renum2 = gsGasPriceService.saveGsPrice(gsGasPrice, "update");
                if(renum2 < 1){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("无商品价格变动！！！");
                    return ajaxJson;
                }

            }

            ProductPrice productPriceInfo = productPriceService.queryProductPriceByPK(productPrice.getId());//查询当前要添加的价格信息
            if(productPriceInfo == null){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("商品价格变动出错！！！");
                return ajaxJson;
            }

            attributes.put("productPrice", productPriceInfo);
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
			//系统关键日志记录
			SysOperationLog sysOperationLog = new SysOperationLog();
	    	SysUser user = new SysUser();
	    	user.setUserName(request.getParameter("suserName"));
	    	user.setPassword(request.getParameter("spassword"));
	    	SysUser sysUser = sysUserService.queryUserByUserInfo(user);
	    	sysOperationLog.setUser_name(user.getUserName());
			sysOperationLog.setOperation_type("tj");
			sysOperationLog.setLog_platform("3");
    		sysOperationLog.setLog_content("用户创建商品价格成功！调价商品为："+productPrice.getProductPriceId()+"，调整价格为："+productPrice.getProductPrice());
			//操作日志
			sysOperationLogService.saveOperationLog(sysOperationLog,sysUser.getSysUserId());
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
