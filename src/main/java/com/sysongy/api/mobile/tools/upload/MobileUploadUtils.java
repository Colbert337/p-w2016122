package com.sysongy.api.mobile.tools.upload;

import java.io.File;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.sysongy.api.mobile.model.base.MobileParams;
import com.sysongy.api.mobile.model.base.MobileReturn;
import com.sysongy.api.mobile.model.upload.MobileUpload;
import com.sysongy.api.mobile.tools.MobileUtils;
import com.sysongy.api.mobile.tools.login.MobileLoginUtils;
import com.sysongy.util.FileUtil;

public class MobileUploadUtils extends MobileUtils{
	
	public static final String RET_USERINFO_SUCCESS = "获取司机用户信息成功";
	
	public static final String REQ_TYPE_DRIVER_HEAD = "1";
	
	public static MobileUpload checkUploadParam(MobileParams params, MobileReturn ret) throws Exception{
		
		MobileUpload upload = new MobileUpload();
		
		try {
			upload = (MobileUpload) JSON.parseObject(params.getDetailParam(), MobileUpload.class);
		} catch (Exception e) {
			ret = MobileUtils.packagingMobileReturn(MobileLoginUtils.RET_ERROR, MobileLoginUtils.RET_PARAM_ERROR_MSG, null);
			throw e;
		}

		checkApiKey(params.getApiKey(), ret);
		
		/*if (StringUtils.isEmpty(upload.getReqType()) || StringUtils.isEmpty(upload.getToken()) || params.getFile() == null) {
			ret.setError(RET_ERROR);
			ret.setMsg(RET_PARAM_ERROR_MSG);
			throw new Exception(RET_PARAM_ERROR_MSG);
		}*/
		
		return upload;
	}
	
	public static String upload(HttpServletRequest request, Properties prop, CommonsMultipartFile files, String filePath, String realPath) throws Exception{
		
		FileUtil.createIfNoExist(filePath);
        long timeStamp = System.currentTimeMillis();
        File heaDestFile = new File(filePath + timeStamp + files.getOriginalFilename());
        String contextPath = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + contextPath;
        String headPath = basePath + (String) prop.get("show_images_path") + "/" + realPath + timeStamp + files.getOriginalFilename();
        FileUtils.copyInputStreamToFile(files.getInputStream(), heaDestFile);// 复制临时文件到指定目录下
        return headPath;
	}

}
