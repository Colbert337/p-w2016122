<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<header>
        <div class="inner clearfix">
            <a href="index.jsp" class="logo"></a>
            <button class="navbar-toggle" type="button">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <ul class="nav">
                <li>
                    <a class="nav-login" href="javascrip:;">登 录</a>
                    <ul>
                        <li><a href="<%=basePath %>/webpage/crm/login_gs.jsp">加注站</a></li>
                        <li><a href="<%=basePath %>/webpage/crm/login_tc.jsp">运输公司</a></li>
                    </ul>
                </li>
                <li><a href="<%=basePath %>/webpage/crm/download-crm.jsp">产品下载</a></li>
                <li><a href="<%=basePath %>/portal/crm/help/list/all">帮助中心</a></li>
            </ul>
        </div>
    </header>

