package com.sysongy.poms.base.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.permi.model.SysFunction;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysFunctionService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.util.DateTimeHelper;
import com.sysongy.util.DateUtil;
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
    @Autowired
    TransportionService transportionService;
    @Autowired
    GastationService gastationService;

    @RequestMapping(value = {"","/"})
    public String index(ModelMap map){  
        return "redirect:/webpage/crm/index.jsp";
    }

    @RequestMapping("/admin")
    public String adminLogin(ModelMap map){
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
        Integer sysType = 0;

        SysUser user = new SysUser();

        user.setUserName(userName);
        user.setPassword(password);
        try {
            user = sysUserService.queryUserByAccount(user);

            //判断登录是否成功
            if(user != null && user.getUserName() != null && user.getPassword() != null){
                if(user.getIsDeleted() == 0){
                    returnPath = "login";
                    map.addAttribute("erroMsg","当前用户已删除！");
                    return returnPath;
                }else if(user.getStatus() == 1 ){
                    returnPath = "login";
                    map.addAttribute("erroMsg","当前用户已禁用！");
                    return returnPath;
                }else if((user.getStationId() == null || user.getStationId().equals("")) && user.getUserType() < GlobalConstant.USER_TYPE_MANAGE){
                    returnPath = "login";//非运维用户站点ID不能为空
                    map.addAttribute("erroMsg","当前用户无效！");
                    return returnPath;
                }else if(user.getUserType() == GlobalConstant.USER_TYPE_CRM){
                    returnPath = "login";//CRM用户无法登录web站点
                    map.addAttribute("erroMsg","CRM客户端用户禁止登录！");
                    return returnPath;
                }
                //封装用户信息
                int userType = user.getUserType();
                currUser.setUserId(user.getSysUserId());
                currUser.setUser(user);
                currUser.setUserType(userType);//当前用户类型
                currUser.setStationId(user.getStationId());//当前用户站点信息
                sysType = userType;

                //封装用户菜单信息
                List<Map<String, Object>> functionList = sysFunctionService.queryFunctionListByUserId(user.getSysUserId(),user.getUserType());
                currUser.setUserFunctionList(functionList);

                //获取气站或运输公司信息
                String stationId = user.getStationId();
                if(stationId != null){
                    if(user.getUserType().equals(GlobalConstant.USER_TYPE_TRANSPORT)){
                        Transportion transportion = transportionService.queryTransportionByPK(user.getStationId());
                        if(transportion != null ){
                            Date expiryDate = transportion.getExpiry_date();
                            int compareVal = 0;
                            try {
                                compareVal = DateTimeHelper.compareTwoDate(new Date(),expiryDate);
                            }catch (Exception e){
                                e.printStackTrace();
                                returnPath = "login";
                                map.addAttribute("erroMsg","运输公司已过有效期！");
                                return returnPath;
                            }
                            if(transportion.getStatus().equals("0")){
                                returnPath = "login";
                                map.addAttribute("erroMsg","运输公司已关闭！");
                                return returnPath;
                            }else if(compareVal < 0){
                                returnPath = "login";
                                map.addAttribute("erroMsg","运输公司已过有效期！");
                                return returnPath;
                            }
                        }
                    }else if(user.getUserType().equals(GlobalConstant.USER_TYPE_STATION)){
                        Gastation gastation = gastationService.queryGastationByPK(stationId);
                        if(gastation != null ){
                            Date expiryDate = gastation.getExpiry_date();
                            int compareVal = 0;
                            try {
                                compareVal = DateTimeHelper.compareTwoDate(new Date(),expiryDate);
                            }catch (Exception e){
                                e.printStackTrace();
                                returnPath = "login";
                                map.addAttribute("erroMsg","加注站已过有效期！");
                                return returnPath;
                            }
                            if(gastation.getStatus().equals("0")){
                                returnPath = "login";
                                map.addAttribute("erroMsg","加注站已关闭！");
                                return returnPath;
                            }else if(compareVal < 0){
                                returnPath = "login";
                                map.addAttribute("erroMsg","加注站已过有效期！");
                                return returnPath;
                            }
                        }
                    }
                }else{
                    returnPath = "login";
                    map.addAttribute("erroMsg","该用户未关联任何系统！");
                    return returnPath;
                }

                map.addAttribute("current_module", "webpage/demo/demo");
                map.addAttribute("currUser",currUser);
                map.addAttribute("sysType",sysType);
                returnPath = "common/g_main";
            }else{
                returnPath = "login";
                map.addAttribute("erroMsg","账户名或密码错误！");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.addAttribute("erroMsg","账户名或密码错误！");
            return returnPath;
        }
    	return returnPath;
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = {"/web/login/common"})
    @ResponseBody
    public Map<String, Object> loginCommon( HttpServletRequest request,HttpServletResponse response,ModelMap map){
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String returnPath = "login";
        CurrUser currUser = new CurrUser();
        Integer sysType = 0;
        Map<String, Object> resultMap = new HashMap<>();

        SysUser user = new SysUser();

        user.setUserName(userName);
        user.setPassword(password);
        try {
            user = sysUserService.queryUserByAccount(user);

            //判断登录是否成功
            if(user != null && user.getUserName() != null && user.getPassword() != null){
                if(user.getIsDeleted() == 0){
                    returnPath = "login";
                    map.addAttribute("erroMsg","当前用户已删除！");
                    resultMap.put("erroMsg","当前用户已删除！");
                    return resultMap;
                }else if(user.getStatus() == 1 ){
                    returnPath = "login";
                    map.addAttribute("erroMsg","当前用户已禁用！");
                    resultMap.put("erroMsg","当前用户已禁用！");
                    return resultMap;
                }else if((user.getStationId() == null || user.getStationId().equals("")) && user.getUserType() < GlobalConstant.USER_TYPE_MANAGE){
                    returnPath = "login";//非运维用户站点ID不能为空
                    map.addAttribute("erroMsg","当前用户无效！");
                    resultMap.put("erroMsg","当前用户无效！");
                    return resultMap;
                }else if(user.getUserType() == GlobalConstant.USER_TYPE_CRM){
                    returnPath = "login";//CRM用户无法登录web站点
                    map.addAttribute("erroMsg","CRM客户端用户禁止登录！");
                    resultMap.put("erroMsg","CRM客户端用户禁止登录！");
                    return resultMap;
                }
                //封装用户信息
                int userType = user.getUserType();
                currUser.setUserId(user.getSysUserId());
                currUser.setUser(user);
                currUser.setUserType(userType);//当前用户类型
                currUser.setStationId(user.getStationId());//当前用户站点信息
                sysType = userType;

                //封装用户菜单信息
                List<Map<String, Object>> functionList = sysFunctionService.queryFunctionListByUserId(user.getSysUserId(),user.getUserType());
                currUser.setUserFunctionList(functionList);

                //获取气站或运输公司信息
                String stationId = user.getStationId();
                if(stationId != null){
                    if(user.getUserType().equals(GlobalConstant.USER_TYPE_TRANSPORT)){
                        Transportion transportion = transportionService.queryTransportionByPK(user.getStationId());
                        if(transportion != null ){
                            Date expiryDate = transportion.getExpiry_date();
                            int compareVal = 0;
                            try {
                                compareVal = DateTimeHelper.compareTwoDate(new Date(),expiryDate);
                            }catch (Exception e){
                                e.printStackTrace();
                                returnPath = "login";
                                map.addAttribute("erroMsg","运输公司已过有效期！");
                                resultMap.put("erroMsg","运输公司已过有效期！");
                                return resultMap;
                            }
                            if(transportion.getStatus().equals("0")){
                                returnPath = "login";
                                map.addAttribute("erroMsg","运输公司已关闭！");
                                resultMap.put("erroMsg","运输公司已关闭！");
                                return resultMap;
                            }else if(compareVal < 0){
                                returnPath = "login";
                                map.addAttribute("erroMsg","运输公司已过有效期！");
                                resultMap.put("erroMsg","运输公司已过有效期！");
                                return resultMap;
                            }
                        }
                    }else if(user.getUserType().equals(GlobalConstant.USER_TYPE_STATION)){
                        Gastation gastation = gastationService.queryGastationByPK(stationId);
                        if(gastation != null ){
                            Date expiryDate = gastation.getExpiry_date();
                            int compareVal = 0;
                            try {
                                compareVal = DateTimeHelper.compareTwoDate(new Date(),expiryDate);
                            }catch (Exception e){
                                e.printStackTrace();
                                returnPath = "login";
                                map.addAttribute("erroMsg","加注站已过有效期！");
                                resultMap.put("erroMsg","加注站已过有效期！");
                                return resultMap;
                            }
                            if(gastation.getStatus().equals("0")){
                                returnPath = "login";
                                map.addAttribute("erroMsg","加注站已关闭！");
                                resultMap.put("erroMsg","加注站已关闭！");
                                return resultMap;
                            }else if(compareVal < 0){
                                returnPath = "login";
                                map.addAttribute("erroMsg","加注站已过有效期！");
                                resultMap.put("erroMsg","加注站已过有效期！");
                                return resultMap;
                            }
                        }
                    }
                }else{
                    returnPath = "login";
                    map.addAttribute("erroMsg","该用户未关联任何系统！");
                    resultMap.put("erroMsg","该用户未关联任何系统！");
                    return resultMap;
                }

                map.addAttribute("current_module", "webpage/demo/demo");
                map.addAttribute("currUser",currUser);
                map.addAttribute("sysType",sysType);
                returnPath = "common/g_main";
                resultMap.put("erroMsg","suceess");
                return resultMap;
            }else{
                returnPath = "login";
                map.addAttribute("erroMsg","账户名或密码错误！");
                resultMap.put("erroMsg","账户名或密码错误！");
                return resultMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("erroMsg","账户名或密码错误！");
            return resultMap;
        }
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
    	return "redirect:/webpage/crm/index.jsp";
    }
    
    /**
     * 跳转控制面板
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = {"/web/panel/"})
    public String panelList( HttpServletRequest request,HttpServletResponse response,ModelMap map ){
        return "common/g_main";
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

    /**
     * 进入设置密码页面
     * @param currUser
     * @param transportion
     * @param map
     * @return
     */
    @RequestMapping("/msg/info/setPassword")
    public String querySetPassword(@ModelAttribute("currUser") CurrUser currUser,Transportion transportion, ModelMap map){
        if(currUser == null){
            return "redirect:/";
        }else{
            String userName = currUser.getUser().getUserName();
            String stationId = currUser.getStationId();

            map.addAttribute("userName",userName);
            map.addAttribute("stationId",stationId);

            return "webpage/poms/transportion/ps_set";
        }
    }
}
