package com.sysongy.poms.mobile.controller;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.dao.DistCityMapper;
import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.DistCity;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.mobile.model.MbAppVersion;
import com.sysongy.poms.mobile.service.MbAppVersionService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@RequestMapping("/web/mobile/appversion")
@Controller
public class MbAppVersionController extends BaseContoller {
	@Autowired
	MbAppVersionService mbAppVersionService;
	@Autowired
	DistCityMapper city;
	MbAppVersion mbAppVersion;


	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
	/**
	 * 查询App版本列表
	 * 
	 * @return
	 */
	@RequestMapping("/list/page")
	public String queryAppVersionListPage(MbAppVersion mbAppVersion, ModelMap map,String resultVal) throws Exception {

		this.mbAppVersion = mbAppVersion;
		String ret = "webpage/poms/mobile/appversion_list";

		PageBean bean = new PageBean();

		if (mbAppVersion.getPageNum() == null) {
			mbAppVersion.setPageNum(GlobalConstant.PAGE_NUM);
			mbAppVersion.setPageSize(GlobalConstant.PAGE_SIZE);
		}

		PageInfo<MbAppVersion> pageinfo = new PageInfo<MbAppVersion>();
		pageinfo = mbAppVersionService.queryAppVersionListPage(mbAppVersion);
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
		map.addAttribute("mbAppVersion", mbAppVersion);

		return ret;
	}


	@RequestMapping("/add")
	public String addAppVersion(ModelMap map) {
		return "webpage/poms/mobile/appVersion_add";
	}



	@RequestMapping("/save")
	public String saveBanner(MbAppVersion mbAppVersion, ModelMap map, HttpServletRequest request, HttpSession session) throws Exception {

		CurrUser user = (CurrUser) session.getAttribute("currUser");
		SysUser u = user.getUser();
		PageBean bean = new PageBean();
		String ret = "webpage/poms/mobile/appVersion_list";

		try {
			String PATH = (String) prop.get("downloadDir");
			String s[]=mbAppVersion.getUrl().split(PATH);
			mbAppVersion.setUrl(PATH+s[1]);

			if (mbAppVersion != null && mbAppVersion.getAppVersionId() != null && !"".equals(mbAppVersion.getAppVersionId())) {

				mbAppVersionService.updateByPrimaryKey(mbAppVersion);

				bean.setRetMsg("修改成功");

			} else {
				mbAppVersion.setAppVersionId(UUIDGenerator.getUUID());
				mbAppVersion.setCreatedDate(new Date());
				mbAppVersionService.insert(mbAppVersion);

				bean.setRetMsg("新增成功");
			}

			ret = this.queryAppVersionListPage(this.mbAppVersion == null ? new MbAppVersion() : this.mbAppVersion, map,null);

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
	 * 删除记录
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteBanner(MbAppVersion mbAppVersion, ModelMap map) throws Exception {
		String resultVal = "删除成功！";
		resultVal = Encoder.symmetricEncrypto(resultVal);
		mbAppVersionService.deleteByPrimaryKey(mbAppVersion.getAppVersionId());
		return "redirect:/web/mobile/appversion/list/page?resultVal=" + resultVal;
	}

	/**
	 * 返回修改页面及新增页面
	 * @param mbAppVersion
	 * @param map
	 * @return
	 */
	@RequestMapping("/findoneForUpdate")
	public String fondone(MbAppVersion mbAppVersion, ModelMap map) {
		String ret = "webpage/poms/mobile/appversion_update";
		Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
		String data = (String) prop.get("version");
		String[] datas = data.split(",");
		MbAppVersion mbAppVersions = mbAppVersionService.selectByPrimaryKey(mbAppVersion.getAppVersionId());
		PageBean bean = new PageBean();
		bean.setRetCode(100);
		bean.setRetMsg("查询成功");
		bean.setPageInfo(ret);
		map.addAttribute("datas", datas);
		map.addAttribute("ret", bean);
		map.addAttribute("mbAppVersion", mbAppVersions);
		return ret;
	}


	/**
	 * apk上传
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("uploadFile")
	@ResponseBody
	public AjaxJson uploadifyStart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
		List<MultipartFile> fileList = multipartRequest.getFiles("Filedata");
		MultipartFile uploadify=fileList.get(0);
		AjaxJson ajaxJson = new AjaxJson();
		if (uploadify.isEmpty()) {
			ajaxJson.setMsg("上传文件为空！！！");
			ajaxJson.setSuccess(false);
			return ajaxJson;
		}
		String savePath = request.getSession().getServletContext().getRealPath("/");
		String PATH = (String) prop.get("downloadDir");
		savePath = savePath + PATH;//保存的服务器目录
		File f1 = new File(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		//String type = uploadify.getOriginalFilename().substring(uploadify.getOriginalFilename().indexOf("."));
		File	destFile = new File(savePath + uploadify.getOriginalFilename());//保存在目录下的文件
		try {
			FileUtils.copyInputStreamToFile(uploadify.getInputStream(), destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String localPath = (String) prop.get("http_poms_path");//当前服务器域名地址
		Map map=new HashMap();
		map.put("downloadUrl",localPath+PATH+uploadify.getOriginalFilename());
		DecimalFormat df = new DecimalFormat("#.00");
		map.put("appSize",df.format((double) uploadify.getSize() / 1048576) + "MB");
		map.put("createDate",DateUtil.getToday());
		ajaxJson.setAttributes(map);
		return ajaxJson;
	}

}
