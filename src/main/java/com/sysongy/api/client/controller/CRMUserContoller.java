package com.sysongy.api.client.controller;

import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.util.FileUtil;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/crmInterface/crmUserService")
public class CRMUserContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    SysUserService sysUserService;

    @Autowired
    private GastationService gastationService;

    @RequestMapping(value = {"/web/queryUserInfo"})
    @ResponseBody
    public AjaxJson queryUserInfo(HttpServletRequest request, HttpServletResponse response, SysUser sysUser){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            if(sysUser == null || sysUser.getUserName() == null || sysUser.getPassword() == null){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("用户名或密码为空，请检查输入参数！");
                return ajaxJson;
            }else if(sysUser.getUserType() != InterfaceConstants.USER_TYPE_CRM_USER){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("用户名类型错误，无权限登录！");
                return ajaxJson;
            }else{
                SysUser user = sysUserService.queryUserMapByAccount(sysUser);
                if(user == null){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("用户名或密码错误，请重新登录！");
                    return ajaxJson;
                }

                if((user.getStatus() == GlobalConstant.USER_STATUS.FROZEN_STATUS) ||
                        (user.getIsDeleted() == GlobalConstant.USER_DELETE.DELETED_STATUS)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("该用户已被禁用或删除！");
                    return ajaxJson;
                }

                Gastation gastation = gastationService.queryGastationByPK(sysUser.getStationId());
                long nTime = gastation.getExpiry_date().getTime() - new Date().getTime();
                if(gastation.getStatus().equalsIgnoreCase(GlobalConstant.StationStatus.PAUSE) || (nTime < 0.0)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("气站已过期或者气站已关闭！");
                    return ajaxJson;
                }

                if(!user.getStationId().equalsIgnoreCase(sysUser.getStationId())){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("该用户无权登录该气站客户端！");
                    return ajaxJson;
                }

                if((user.getSysFunctionList() == null) || (user.getSysFunctionList().size() == 0)
                        || ((user.getSysFunctionList().get(0).get("children")) == null)){
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("该用户还没有授权，请在气站平台授予该用户权限！");
                    return ajaxJson;
                }

                attributes.put("UserInfo",user);
                ajaxJson.setAttributes(attributes);
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("用户登录成功！");
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_USER_ERROR + e.getMessage());
            logger.error("queryCardInfo error： " + e);
        }
    	return ajaxJson;
    }

    @RequestMapping(value = {"/web/updateUserInfo"})
    @ResponseBody
    public AjaxJson updateUserInfo(HttpServletRequest request, HttpServletResponse response, SysUser sysUser){
        AjaxJson ajaxJson = new AjaxJson();
        if(!StringUtils.isNotEmpty(sysUser.getSysUserId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("当前用户为空，请检查输入参数！");
            return ajaxJson;
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            SysUser sysUserInfo = sysUserService.queryUserByUserId(sysUser.getSysUserId());
            if(StringUtils.isNotEmpty(sysUser.getAvatarS())){
                sysUserInfo.setAvatarS(sysUser.getAvatarS());
            }

            if(StringUtils.isNotEmpty(sysUser.getUserName())){
                sysUserInfo.setUserName(sysUser.getUserName());
            }

            if(StringUtils.isNotEmpty(sysUser.getMobilePhone())){
                sysUserInfo.setMobilePhone(sysUser.getMobilePhone());
            }

            if(sysUser.getGender() != null){
                sysUserInfo.setGender(sysUser.getGender());
            }

            if(StringUtils.isNotEmpty(sysUser.getPassword())){
                sysUserInfo.setPassword(sysUser.getPassword());
            }

            if(StringUtils.isNotEmpty(sysUser.getRealName())){
                sysUserInfo.setRealName(sysUser.getRealName());
            }

            if(StringUtils.isNotEmpty(sysUser.getRemark())){
                sysUserInfo.setRemark(sysUser.getRemark());
            }

            int nRet = sysUserService.updateUser(sysUserInfo);
            if(nRet < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg(InterfaceConstants.UPDATE_CRM_SYSUSER_ERROR);
                return ajaxJson;
            }
            attributes.put("UserInfo", sysUserInfo);

        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.UPDATE_CRM_SYSUSER_ERROR + e.getMessage());
            logger.error("updateCardInfo error： " + e);
        }
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    //多文件上传
    @RequestMapping(value = "/web/upload")
    @ResponseBody
    public AjaxJson uploadFileData(@RequestParam("filename")CommonsMultipartFile[] files, HttpServletRequest request, SysUser sysUser) {
        AjaxJson ajaxJson = new AjaxJson();

        String imgTag = request.getParameter("imgTag");

        if(files == null){
            ajaxJson.setMsg("上传文件为空！！！");
            ajaxJson.setSuccess(false);
            return ajaxJson;
        }

        String sysPathID = sysUser.getSysUserId();
        if(!StringUtils.isNotEmpty(sysUser.getSysUserId())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("sysUserId为空！！！");
            return ajaxJson;
        }

        String realPath =  sysPathID + "/" ;
        String filePath = (String) prop.get("images_upload_path") + "/" + realPath;
        FileUtil.createIfNoExist(filePath);
        try {
            Map<String, Object> attributes = new HashMap<String, Object>();
            for (int i = 0; i < files.length; i++) {
                String path = filePath + files[i].getOriginalFilename();
                File destFile = new File(path);
                String contextPath = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + contextPath;
                String fileNum = String.valueOf(i);
                attributes.put(imgTag + fileNum, basePath + (String) prop.get("show_images_path") + "/" + realPath + files[i].getOriginalFilename());
                FileUtils.copyInputStreamToFile(files[i].getInputStream(), destFile);// 复制临时文件到指定目录下
                sysUser.setAvatarS(basePath + (String) prop.get("show_images_path") + "/" + realPath + files[i].getOriginalFilename());
                sysUserService.updateCRMUser(sysUser);
            }
            attributes.put("UserInfo", sysUser);
            ajaxJson.setAttributes(attributes);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("上传文件失败：" + e.getMessage());
            logger.error("uploadFileData Customer error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/queryUsers"})
    @ResponseBody
    public AjaxJson queryUsers(HttpServletRequest request, HttpServletResponse response, SysUser sysUser){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            if(sysUser.getStationId() == null){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("气站为空，请检查输入参数！");
                return ajaxJson;
            }

            List<SysUser> sysUsers = sysUserService.queryUserList(sysUser);
            attributes.put("SysUsers",sysUsers);
            ajaxJson.setAttributes(attributes);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("查询成功！");
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_USER_ERROR + e.getMessage());
            logger.error("queryUsers error： " + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/web/queryUsersForCRMReport"})
    @ResponseBody
    public AjaxJson queryUsersForCRMReport(HttpServletRequest request, HttpServletResponse response, SysUser sysUser){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        try
        {
            if(sysUser.getStationId() == null){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("气站为空，请检查输入参数！");
                return ajaxJson;
            }
            List<SysUser> sysUsersNew = new ArrayList<SysUser>();
            List<SysUser> sysUsers = sysUserService.queryUserList(sysUser);
            SysUser sysUserNew = new SysUser();
            sysUserNew.setSysUserId(GlobalConstant.Query_Condition.QUERY_CONDITION_ALL);
            sysUserNew.setUserName(GlobalConstant.Query_Condition.QUERY_CONDITION_ALL);
            sysUserNew.setMobilePhone(GlobalConstant.Query_Condition.QUERY_CONDITION_ALL);
            sysUserNew.setRealName(GlobalConstant.Query_Condition.QUERY_CONDITION_ALL);
            sysUserNew.setGender(1);
            sysUsersNew.add(sysUserNew);
            sysUsersNew.addAll(sysUsers);
            attributes.put("SysUsers",sysUsersNew);
            ajaxJson.setAttributes(attributes);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("查询成功！");
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.QUERY_CRM_USER_ERROR + e.getMessage());
            logger.error("queryUsersForCRMReport error： " + e);
        }
        return ajaxJson;
    }
}
