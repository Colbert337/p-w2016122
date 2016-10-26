package com.sysongy.api.client.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.model.ProductPrice;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.gastation.service.GsGasPriceService;
import com.sysongy.poms.gastation.service.ProductPriceService;
import com.sysongy.poms.liquid.model.SysGasSource;
import com.sysongy.poms.liquid.service.LiquidService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.util.DateTimeHelper;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/14.
 */

@Controller
@RequestMapping("/crmInterface/crmGasPriceService")
public class CRMGasPriceController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GsGasPriceService gsGasPriceService;

    @Autowired
    private LiquidService service;

    @Autowired
    ProductPriceService productPriceService;

    @Autowired
    GastationService gastationService;

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

            Map<String, Object> attributes = new HashMap<String, Object>();
            int isExistDriver = gsGasPriceService.isExists(gsGasPrice);
            if(isExistDriver > 0){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该气品已被创建，不能重复添加！！！");
                return ajaxJson;
            }

            double lPrice = Double.valueOf(strPrice);
            Gastation gastation = gastationService.queryGastationByPK(gsGasPrice.getSysGasStationId());
            String effectiveTime = "";
            if(gastation != null){
                effectiveTime = gastation.getPrice_effective_time();
            }
            gsGasPrice = createProductPrice(gsGasPrice, lPrice, effectiveTime);

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

    private GsGasPrice createProductPrice(GsGasPrice gsGasPrice, double lPrice, String effectiveTime){
        ProductPrice productPrice = new ProductPrice();
        productPrice.setId(UUIDGenerator.getUUID());

        String priceStatus = GlobalConstant.PRICE_STATUS.VALID;
        Calendar curr = Calendar.getInstance();
        Date currentTime = curr.getTime();
        Date startTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        /*if(effectiveTime.equals(GlobalConstant.PRICE_EFFECTIVE_TIME.TWELVE)){
            priceStatus = GlobalConstant.PRICE_STATUS.ENACTMENT;//价格状态改为待生效
            startTime = DateTimeHelper.getTwelve(currentTime);//生效时间改为12小时后
        }else if(effectiveTime.equals(GlobalConstant.PRICE_EFFECTIVE_TIME.TWENTY)){
            priceStatus = GlobalConstant.PRICE_STATUS.ENACTMENT;//价格状态改为待生效
            startTime = DateTimeHelper.getTomorrow(currentTime);//生效时间改为24小时后
        }*/
        productPrice.setProductPriceStatus(priceStatus);
        productPrice.setCreateTime(new Date());
        productPrice.setStartTime(startTime);
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
    
    @RequestMapping("/queryAllGasPriceList")
	public String queryAllGasPriceList(ModelMap map, @ModelAttribute GsGasPrice gsGasPrice) throws Exception{

		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastation_price_list";

		try {
			if(gsGasPrice.getPageNum() == null){
				gsGasPrice.setPageNum(1);
				gsGasPrice.setPageSize(10);
			}
			if(StringUtils.isEmpty(gsGasPrice.getOrderby())){
				gsGasPrice.setOrderby("created_date desc");
			}
/*			if(productPrice.getPageNum() == null){
				productPrice.setPageNum(1);
				productPrice.setPageSize(10);
			}
			
			productPrice.setOrderby("create_time desc");*/

			PageInfo<GsGasPrice> pageinfo = gsGasPriceService.queryGsPrice(gsGasPrice);
			
//			PageInfo<ProductPrice> pageinfo2 = productPriceService.queryProductPrice(productPrice);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
//			map.addAttribute("pageInfo2", pageinfo2);
			map.addAttribute("gsGasPrice",gsGasPrice);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		}
		finally {
			return ret;
		}
	}
    
    @RequestMapping("/queryProductPriceList")
   	public String queryProductPriceList(ModelMap map, ProductPrice productPrice) throws Exception{

   		PageBean bean = new PageBean();
   		String ret = "webpage/poms/gastation/gastation_product_price_list";

   		try {
   			if(productPrice.getPageNum() == null){
   				productPrice.setOrderby("create_time desc");
   				productPrice.setPageNum(1);
   				productPrice.setPageSize(10);
   			}
   			
   			PageInfo<ProductPrice> pageinfo = productPriceService.queryProductPrice(productPrice);

   			bean.setRetCode(100);
   			bean.setRetMsg("查询成功");
   			bean.setPageInfo(ret);

   			map.addAttribute("ret", bean);
   			map.addAttribute("pageInfo", pageinfo);
   			map.addAttribute("productPrice",productPrice);
   		} catch (Exception e) {
   			bean.setRetCode(5000);
   			bean.setRetMsg(e.getMessage());

   			map.addAttribute("ret", bean);
   			logger.error("", e);
   			throw e;
   		}
   		finally {
   			return ret;
   		}
   	}

    @RequestMapping("/getUpdateGsGasPricePage")
    public String preUpdate(ModelMap map, @RequestParam String gsGasPriceId,HttpServletRequest request, HttpServletResponse response){
        PageBean bean = new PageBean();
        String ret = "webpage/poms/gastation/gastation_price_update";
        GsGasPrice gsGasPrice = new GsGasPrice();

        try {
            if(gsGasPriceId != null && !"".equals(gsGasPriceId)){
                gsGasPrice = gsGasPriceService.queryGsPriceByPK(gsGasPriceId);
            }

            bean.setRetCode(100);
            bean.setRetMsg("根据["+gsGasPriceId+"]查询gastationpriceId成功");
            bean.setPageInfo(ret);

            map.addAttribute("ret", bean);
            map.addAttribute("gsGasPrice", gsGasPrice);

        } catch (Exception e) {
            bean.setRetCode(5000);
            bean.setRetMsg(e.getMessage());
            map.addAttribute("ret", bean);
            logger.error("", e);
            throw e;
        }
        finally {
            return ret;
        }
    }
    @RequestMapping("/updateGasPrice")
    public String saveGastation(ModelMap map, GsGasPrice gsGasPrice) throws Exception{
        PageBean bean = new PageBean();
        String ret = "webpage/poms/gastation/gastation_new";
        int gastationid ;

        try {
            if (gsGasPrice.getFixed_discount() != null && gsGasPrice.getMinus_money()!=null) {
                return "" ;
            }
                ret = "webpage/poms/gastation/gastation_update";
                gsGasPrice.setGasNum(null);
                gsGasPrice.setGasName(null);
                gastationid = gsGasPriceService.saveGsPrice(gsGasPrice,"update");
                bean.setRetMsg("修改成功");
                ret = this.queryAllGasPriceList(map, gsGasPrice==null?new GsGasPrice():gsGasPrice);


            bean.setRetCode(100);

            bean.setRetValue("");
            bean.setPageInfo(ret);

            map.addAttribute("ret", bean);
        } catch (Exception e) {
            bean.setRetCode(5000);
            bean.setRetMsg(e.getMessage());

            map.addAttribute("ret", bean);
            map.addAttribute("station", gsGasPrice);

            logger.error("", e);
        }
        finally {
            return ret;
        }
    }


}
