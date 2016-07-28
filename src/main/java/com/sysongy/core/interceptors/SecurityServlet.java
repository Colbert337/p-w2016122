package com.sysongy.core.interceptors;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.abel533.sql.SqlMapper;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.util.taglib.cache.UsysparamVO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SecurityServlet extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;

	@Autowired
	SysUserService sysUserService;

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		
		HttpServletRequest request=(HttpServletRequest)arg0;     
        HttpServletResponse response  =(HttpServletResponse) arg1;      
        HttpSession session = request.getSession(true);       
        CurrUser currUser = (CurrUser) session.getAttribute("currUser");//登录人角色  
        String url=request.getRequestURI();
        String isdownloadreport = request.getParameter("downloadreport");
		if(canAllowCRMUserAccess(request)){
			arg2.doFilter(arg0, arg1);
			return;
		}

        if(currUser == null || "".equals(currUser.getUserId())) {
             //判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转     
             if(url!=null && !url.equals("") && ( url.indexOf("Login")<0 && url.indexOf("login")<0 )) {
            	 if(!"true".equals(isdownloadreport)){
            		 response.setStatus(911);
//            		 throw new ServletException("need login");
            	 }else{
            		 response.sendRedirect(request.getContextPath());
            		 //response.setStatus(911); 
            	 }
            	 return;
             }                
         }    
         arg2.doFilter(request, response);     
         return;
	}

	private boolean canAllowCRMUserAccess(HttpServletRequest request){
		String curUserName = request.getParameter("suserName");
		String curPassword = request.getParameter("spassword");
		SysUser sysUser = new SysUser();
		sysUser.setUserName(curUserName);
		sysUser.setMobilePhone(curUserName);
		sysUser.setPassword(curPassword);

		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		SqlSessionFactory sessionFactory = (SqlSessionFactory) wac.getBean("sqlSessionFactory");
		SqlSession session = sessionFactory.openSession();
		SqlMapper sqlMapper = new SqlMapper(session);
		String excuteSQL = "SELECT * FROM sys_user where user_name=" +
				curUserName +
				" and password=" +
				curPassword +
				" and user_type='3' and status='0' and is_deleted='1'";
		List<SysUser> list = sqlMapper.selectList(excuteSQL, SysUser.class);
		session.close();

		if((list == null) || (list.size() == 0)){
			return false;
		}
		return true;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
