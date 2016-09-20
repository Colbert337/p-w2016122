package com.sysongy.poms.mobile.controller;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.dao.DistCityMapper;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.DistCity;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.service.MbBannerService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.util.Encoder;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.UUIDGenerator;

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
public class MbBannerController extends BaseContoller {
	@Autowired
	MbBannerService mbBannerService;
	@Autowired
	DistCityMapper city;
	MbBanner mbBanner;

	/**
	 * 查询图片列表
	 * 
	 * @return
	 */
	@RequestMapping("/list/page")
	public String queryMbBannerListPage(MbBanner mbBanner, ModelMap map,String resultVal) throws Exception {

		this.mbBanner = mbBanner;
		String ret = "webpage/poms/mobile/banner_list";

		PageBean bean = new PageBean();

		if (mbBanner.getPageNum() == null) {
			mbBanner.setPageNum(GlobalConstant.PAGE_NUM);
			mbBanner.setPageSize(GlobalConstant.PAGE_SIZE);
		}

		if (StringUtils.isEmpty(mbBanner.getImgType())) {
			mbBanner.setImgType(GlobalConstant.ImgType.TOP);
		}
//		if (mbBanner.getImgType() == null || "".equals(mbBanner.getImgType())) {
			mbBanner.setImgType("1");
//		}
		PageInfo<MbBanner> pageinfo = new PageInfo<MbBanner>();

		pageinfo = mbBannerService.queryMbBannerListPage(mbBanner);
		if(resultVal!=null && !"".equals(resultVal)){
			bean.setRetCode(100);
			bean.setRetMsg("删除成功");
			bean.setPageInfo(ret);
		}else{
		bean.setRetCode(100);
		bean.setRetMsg("查询成功");
		bean.setPageInfo(ret);
		}
		Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
		String data = (String) prop.get("version");
		String[] datas = data.split(",");
		List<DistCity> list = city.queryHotCityList(2);
		map.addAttribute("datas", datas);
		map.addAttribute("city", list);
		map.addAttribute("ret", bean);
		map.addAttribute("pageInfo", pageinfo);
		map.addAttribute("mbBanner", mbBanner);

		return ret;
	}

	/**
	 * 跳转添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String addBanner(ModelMap map) {
		return "webpage/poms/mobile/banner_add";
	}

	/**
	 * 查询图片信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/info")
	@ResponseBody
	public MbBanner queryBanner(MbBanner banner, ModelMap map) {
		MbBanner mbBanner = mbBannerService.queryMbBanner(banner);
		return mbBanner;
	}

	/**
	 * 添加图片
	 * 
	 * @return
	 */
	@RequestMapping("/save")
	public String saveBanner(MbBanner mbBanner, ModelMap map, HttpServletRequest request, HttpSession session) throws Exception {
		// String resultVal = "";
		//
		// if(mbBanner != null && mbBanner.getMbBannerId() != null &&
		// !"".equals(mbBanner.getMbBannerId())){
		// //修改图片
		// mbBanner.setUpdatedDate(new Date());
		// mbBanner.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);
		// mbBannerService.updateBanner(mbBanner);
		// resultVal = "修改成功！";
		// resultVal = Encoder.symmetricEncrypto(resultVal);
		// }else{
		// //查询当前图片最大序号
		// MbBanner maxBannerIndex =
		// mbBannerService.queryMaxIndex(GlobalConstant.ImgType.TOP);
		// if(maxBannerIndex != null){
		// mbBanner.setSort(maxBannerIndex.getSort()+1);
		// }else{
		// mbBanner.setSort("0");
		// }
		// //添加图片
		// mbBanner.setMbBannerId(UUIDGenerator.getUUID());
		// mbBanner.setImgType(mbBanner.getImgType());
		// mbBannerService.saveBanner(mbBanner);
		// resultVal = "添加成功！";
		// resultVal = Encoder.symmetricEncrypto(resultVal);
		// }
		// return "redirect:/web/mobile/img/list/page?resultVal="+resultVal;
		// mbBanner.setOperator();
		CurrUser user = (CurrUser) session.getAttribute("currUser");
		SysUser u = user.getUser();
		mbBanner.setOperator(u.getUserName());
		PageBean bean = new PageBean();
		String ret = "webpage/poms/mobile/banner_list";

		try {
			mbBanner.setTargetUrl(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath() + mbBanner.getTargetUrl());
			
			if (mbBanner != null && mbBanner.getMbBannerId() != null && !"".equals(mbBanner.getMbBannerId())) {

				mbBanner.setUpdatedDate(new Date());
				mbBanner.setIsDeleted(GlobalConstant.STATUS_NOTDELETE);

				mbBannerService.updateBanner(mbBanner);

				bean.setRetMsg("保存成功");

			} else {

				// 查询当前图片最大序号
				// MbBanner maxBannerIndex =
				// mbBannerService.queryMaxIndex(GlobalConstant.ImgType.TOP);
				// if(maxBannerIndex != null){
				// mbBanner.setSort(maxBannerIndex.getSort()+1);
				// }else{
				// mbBanner.setSort("0");
				// }

				mbBanner.setMbBannerId(UUIDGenerator.getUUID());
				mbBanner.setImgType(mbBanner.getImgType());

				mbBannerService.saveBanner(mbBanner);

				bean.setRetMsg("新增成功");
			}

			ret = this.queryMbBannerListPage(this.mbBanner == null ? new MbBanner() : this.mbBanner, map,null);

			bean.setRetCode(100);
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);

			logger.error("", e);
		} finally {
			return ret;
		}
	}

	/**
	 * 删除图片
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteBanner(MbBanner mbBanner, ModelMap map) throws Exception {
		mbBanner.setIsDeleted(GlobalConstant.STATUS_DELETE);
		String resultVal = "删除成功！";

		resultVal = Encoder.symmetricEncrypto(resultVal);

		mbBannerService.deleteBanner(mbBanner);

		return "redirect:/web/mobile/img/list/page?resultVal=" + resultVal;
	}

	@RequestMapping("/fondone")
	public String fondone(MbBanner banner, ModelMap map) {
		String ret = "webpage/poms/mobile/bannerAdd";
		Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
		String data = (String) prop.get("version");
		String[] datas = data.split(",");
		List<DistCity> list = city.queryHotCityList(2);
		MbBanner mbBanner = mbBannerService.queryMbBanner(banner);
		

		PageBean bean = new PageBean();
		bean.setRetCode(100);
		bean.setRetMsg("查询成功");
		bean.setPageInfo(ret);
		map.addAttribute("datas", datas);
		map.addAttribute("imgType", banner.getImgType());
		map.addAttribute("ret", bean);
		map.addAttribute("city", list);
		map.addAttribute("mbBanner", mbBanner);
		return ret;
	}

}
