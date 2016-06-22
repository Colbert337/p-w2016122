package com.sysongy.poms.base.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.permi.model.SysFunction;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysFunctionService;
import com.sysongy.poms.permi.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
@SessionAttributes({"currUser"})
public class BaseContoller {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysFunctionService sysFunctionService;

    @RequestMapping(value = {"","/"})
    public String index(ModelMap map){  
    	/*ModelAndView result = new ModelAndView();
    	String pmcName = "登录测试成功！";
    	result.addObject("pmcName", pmcName);
    	result.addObject("current_module", "webpage/test");
        result.setViewName("comm/g_main");*/
        
        map.addAttribute("current_module", "webpage/demo/panel");
        return "login";
    }

    @RequestMapping(value = {"main"})
    @ResponseBody
    public List<Map<String, Object>> main(@ModelAttribute CurrUser currUser, ModelMap map){
    	/*ModelAndView result = new ModelAndView();
    	String pmcName = "登录测试成功！";
    	result.addObject("pmcName", pmcName);
    	result.addObject("current_module", "webpage/test");
        result.setViewName("comm/g_main");*/
        SysUser user = currUser.getUser();
        List<Map<String, Object>> functionList = sysFunctionService.queryFunctionListByUserId(user.getSysUserId(),user.getUserType());
        map.addAttribute("current_module", "webpage/demo/panel");

        return functionList;
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
        CurrUser currUser = new CurrUser();

        SysUser user = new SysUser();
        user.setUserName(userName);
        user.setPassword(password);

        user = sysUserService.queryUserByAccount(user);

        //判断登录是否成功
    	if(user != null && user.getUserName() != null && user.getPassword() != null){
            //封装用户信息
            int userType = user.getUserType();
            currUser.setUserId(user.getSysUserId());
            currUser.setUser(user);
            currUser.setUserType(userType);//当前用户类型
            currUser.setStationId(user.getStationId());//当前用户站点信息

            //封装用户菜单信息
            List<Map<String, Object>> functionList = sysFunctionService.queryFunctionListByUserId(user.getSysUserId(),user.getUserType());
            currUser.setUserFunctionList(functionList);

    		map.addAttribute("current_module", "webpage/demo/demo");
            map.addAttribute("currUser",currUser);
    		returnPath = "common/g_main";
    	}else{
            returnPath = "login";
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
    @RequestMapping(value = {"/web/panel/"})
    @ResponseBody
    public String panelList( HttpServletRequest request,HttpServletResponse response,ModelMap map ){

        return "panel/panel";
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
