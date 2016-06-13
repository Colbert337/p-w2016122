package com.sysongy.api.client.controller;

import com.sysongy.poms.base.model.AjaxJson;
import com.sysongy.poms.base.model.InterfaceConstants;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes({"currUser","systemId","userId","menuCode","menuIndex"})
@RequestMapping("/crmUserService")
public class CRMUserContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysUserService sysUserService;

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
            }else if(sysUser.getUserType() != 3){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("用户名类型错误，无权限登录！");
                return ajaxJson;
            }else{
                SysUser user = sysUserService.queryUserMapByAccount(sysUser);
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

            int nRet = sysUserService.updateUser(sysUserInfo);
            if(nRet < 1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg(InterfaceConstants.UPDATE_CRM_SYSUSER_ERROR);
                return ajaxJson;
            }

        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(InterfaceConstants.UPDATE_CRM_SYSUSER_ERROR + e.getMessage());
            logger.error("updateCardInfo error： " + e);
        }
        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }
}
