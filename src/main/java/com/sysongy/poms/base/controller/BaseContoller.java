package com.sysongy.poms.base.controller;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;

/**
 * @FileName     :  BaseContoller.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.base.controller
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年10月20日, 下午5:53:21
 * @Author       :  Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
@Controller
@SessionAttributes({"currUser","userId"})
public class BaseContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 6357869213649815390L;
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    SysUserService sysUserService;

    /**
     * 进入登录页面
     * @param map
     * @return
     */
    @RequestMapping(value = {"","/"})
    public String index(ModelMap map){  
    	/*ModelAndView result = new ModelAndView();
    	String pmcName = "登录测试成功！";
    	result.addObject("pmcName", pmcName);
    	result.addObject("current_module", "webpage/test");
        result.setViewName("comm/g_main");*/
        
        return "login";
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = {"/web/login"})  
    public String login( HttpServletRequest request,HttpServletResponse response,ModelMap map){ 
    	String userName = request.getParameter("userName");
    	String password = request.getParameter("password");
    	String returnPath = "login";
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        sysUser.setPassword(password);

        sysUser = sysUserService.queryUserByAccount(sysUser);
    	if(sysUser != null && userName != null && password != null){
            CurrUser currUser = new CurrUser();
            currUser.setUserId(sysUser.getSysUserId());
            map.addAttribute("currUser",currUser);

            returnPath = "common/g_main";
    	}

    	return returnPath;
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = {"/web/loginOut"})  
    public String loginOut( HttpServletRequest request,HttpServletResponse response, SessionStatus sessionStatus, ModelMap map ){  
    	sessionStatus.setComplete();
    	return "redirect:/";
    }
    
    /**
     * 跳转控制面板
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = {"/web/panel/list"})
    @ResponseBody
    public String panelList( HttpServletRequest request,HttpServletResponse response,ModelMap map ){  
    	
    	return "爱上当姐夫拉克时间的弗拉斯柯达";
    }
    
    /**
     * 跳转控制面板
     * @param map
     * @return
     */
    @RequestMapping(value = {"/web/menu/update"})  
    @ResponseBody
    public String updateMenu( @RequestParam String menuCode, @RequestParam String menuIndex,ModelMap map ){  
    	String result = "suc";
    	map.put("menuCode", menuCode);
    	map.put("menuIndex", menuIndex);
    	return result;
    }
    
}
