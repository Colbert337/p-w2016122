<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<div class="subnav">
    <div class="inner">
        <ul>
            <li class="home"><a href="index.jsp">首页</a></li>
            <li class="current"><a href="download-crm.jsp">CRM下载</a></li>
            <li><a href="<%=basePath %>/webpage/crm/download-app">APP下载</a></li>
        </ul>
    </div>
</div>

